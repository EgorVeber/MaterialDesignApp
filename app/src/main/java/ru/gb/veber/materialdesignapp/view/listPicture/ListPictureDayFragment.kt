package ru.gb.veber.materialdesignapp.view.listPicture

import ListPictureState
import ListPictureViewModel
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.DateDialogBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureListBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.dataFromString
import ru.gb.veber.materialdesignapp.utils.formatDate
import ru.gb.veber.materialdesignapp.utils.takeDate
import show
import java.util.*

class ListPictureDayFragment : Fragment() {

    private var _binding: FragmentPictureListBinding? = null
    private val binding get() = _binding!!
    private var flag = false
    private val listPictureViewModel: ListPictureViewModel by lazy {
        ViewModelProvider(this).get(ListPictureViewModel::class.java)
    }
    private val adapter: PictureAdapterRecycler by lazy {
        PictureAdapterRecycler()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pictureRecyclerView.adapter = adapter
        listPictureViewModel.getLiveData().observe(viewLifecycleOwner) { render(it) }
        listPictureViewModel.sendServerRequest(
            takeDate(-30),
            takeDate(0)
        )


        binding.selectButton.setOnClickListener {

            listPictureViewModel.sendServerRequest(
                binding.inputEditTextStartDate.text.toString(),
                binding.inputEditTextEndDate.text.toString()
            )
            binding.fab.performClick()
        }

        binding.inputEditTextStartDate.setText(takeDate(-30))
        binding.inputEditTextStartDate.setOnClickListener {
            showDialogDate(binding.inputEditTextStartDate.text.toString(), 0)
        }

        binding.inputEditTextEndDate.setText(Date().formatDate())
        binding.inputEditTextEndDate.setOnClickListener {
            showDialogDate(binding.inputEditTextEndDate.text.toString(), 1)
        }

        binding.fab.setOnClickListener {
            flag = !flag
            if (flag) {
                var s = binding.inputLayoutStartDate.layoutParams.height

                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 480F).setDuration(2000L).start()
                ObjectAnimator.ofFloat(
                    binding.inputLayoutStartDate,
                    View.TRANSLATION_Y,
                    -binding.fab.y - s.toFloat()
                ).setDuration(2000L).start()

                ObjectAnimator.ofFloat(
                    binding.selectButton,
                    View.TRANSLATION_Y,
                    -binding.fab.y - s.toFloat() / 3
                ).setDuration(2000L).start()

                ObjectAnimator.ofFloat(
                    binding.inputLayoutEndDate,
                    View.TRANSLATION_Y,
                    -binding.fab.y - s.toFloat() / 2
                ).setDuration(2000L).start()
                binding.pictureRecyclerView.animate().alpha(0F).duration = 1000L
                binding.inputLayoutStartDate.animate().alpha(1F).duration = 1000L
                binding.inputLayoutEndDate.animate().alpha(1F).duration = 1000L
                binding.selectButton.animate().alpha(1F).duration = 1000L

            } else {
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0F).setDuration(2000L).start()
                ObjectAnimator.ofFloat(binding.inputLayoutStartDate, View.TRANSLATION_Y, 0F)
                    .setDuration(2000L).start()
                ObjectAnimator.ofFloat(binding.inputLayoutEndDate, View.TRANSLATION_Y, 0F)
                    .setDuration(2000L).start()
                ObjectAnimator.ofFloat(binding.selectButton, View.TRANSLATION_Y, 0F)
                    .setDuration(2000L).start()
                binding.pictureRecyclerView.animate().alpha(1F).duration = 1000L
                binding.inputLayoutStartDate.animate().alpha(0.5F).duration = 1000L
                binding.inputLayoutEndDate.animate().alpha(0.5F).duration = 1000L
                binding.selectButton.animate().alpha(0.5F).duration = 1000L
            }
        }
    }

    private fun render(appState: ListPictureState) {
        when (appState) {
            is ListPictureState.Error -> {
                binding.imageView.show()
                binding.imageView.load(R.drawable.nasa_api) {
                }
            }
            is ListPictureState.Loading -> {
                binding.imageView.show()
                binding.imageView.load(R.drawable.loading1) {
                    crossfade(CROSS_FADE_500)
                }
            }
            is ListPictureState.Success -> {
                binding.imageView.hide()
                adapter.setList(appState.pictureList)
            }
        }
    }


    private fun initDatePicker(date: Date, datePicker: DatePicker) {
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            null
        )
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        return Calendar.getInstance().apply {
            this[Calendar.YEAR] = datePicker.getYear()
            this[Calendar.MONTH] = datePicker.getMonth()
            this[Calendar.DAY_OF_MONTH] = datePicker.getDayOfMonth()
        }.time
    }


    private fun showDialogDate(date: String, type: Int) {
        val bindingDialog = DateDialogBinding.inflate(LayoutInflater.from(requireContext()))

        initDatePicker(
            dataFromString(date) as Date,
            bindingDialog.inputDate
        )

        Dialog(requireContext()).apply {
            setContentView(bindingDialog.root)
            bindingDialog.PositiveButtonDate.setOnClickListener {
                if (type == 0) {
                    binding.inputEditTextStartDate.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                } else {
                    binding.inputEditTextEndDate.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                }
                dismiss()
            }
            if (flag) {
                show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListPictureDayFragment()
    }
}