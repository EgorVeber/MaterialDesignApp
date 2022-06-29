package ru.gb.veber.materialdesignapp.view.behavior

import PictureState
import PictureViewModel
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_behavior.view.*
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentBehaviorBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.dataFromString
import ru.gb.veber.materialdesignapp.utils.formatDate
import java.util.*


class BehaviorFragment : Fragment() {


    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
    private lateinit var bSheetB: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentBehaviorBinding? = null
    private val binding: FragmentBehaviorBinding
        get() = _binding!!

    var flag = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBehaviorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerRequest(Date().formatDate())

        binding.inputEditText.setText(Date().formatDate())
        binding.inputLayout.setEndIconOnClickListener { showDialogDate() }
        binding.inputEditText.setOnClickListener { showDialogDate() }



        bSheetB = BottomSheetBehavior.from(binding.bottomSheetContainer).apply {
            addBottomSheetCallback(callBackBehavior)
        }

//        var params = (binding.bottomSheetContainer.layoutParams as CoordinatorLayout.LayoutParams)
//        val behavior = (params.behavior as BottomSheetBehavior).apply {
//            addBottomSheetCallback(callBackBehavior)
//        }
    }


    private val callBackBehavior = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_COLLAPSED"
                    )
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_EXPANDED"
                    )
                }
                BottomSheetBehavior.STATE_DRAGGING -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_DRAGGING"
                    )
                }
                BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_HALF_EXPANDED"
                    )
                }
                BottomSheetBehavior.STATE_HIDDEN -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_HIDDEN"
                    )
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                    Log.d(
                        "BottomSheetBehavior", "STATE_SETTLING"
                    )
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.d("BottomSheetBehavior","behavior.state = "+ bSheetB.state)
        }
    }


    private fun showDialogDate() {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.date_dialog)
        val body = dialog.findViewById<DatePicker>(R.id.inputDate)
        val yesBtn = dialog.findViewById<MaterialButton>(R.id.PositiveButtonDate)
        initDatePicker(dataFromString(binding.inputEditText.text.toString()), body)
        yesBtn.setOnClickListener {
            Log.d("TAG", getDateFromDatePicker(body).toString())
            binding.inputEditText.setText(getDateFromDatePicker(body)?.formatDate())
            viewModel.sendServerRequest(getDateFromDatePicker(body)!!.formatDate())
            dialog.dismiss()
            bSheetB.state=BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        dialog.show()
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

    private fun getDateFromDatePicker(datePicker: DatePicker): Date? {
        return Calendar.getInstance().apply {
            this[Calendar.YEAR] = datePicker.getYear()
            this[Calendar.MONTH] = datePicker.getMonth()
            this[Calendar.DAY_OF_MONTH] = datePicker.getDayOfMonth()
        }.time
    }

    private fun renderData(appState: PictureState) {
        with(binding)
        {
            when (appState) {
                is PictureState.Error -> {
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is PictureState.Loading -> {
                    imageView.load(R.drawable.loading1)
                }
                is PictureState.Success -> {
                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    datePicture.text = appState.pictureDTO.date
                    if (appState.pictureDTO.mediaType == "video") {
                        imageView.load(R.drawable.nasa_api)
                    } else {
                        imageView.load(appState.pictureDTO.hdurl) {
                            placeholder(R.drawable.loading1)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BehaviorFragment()
    }
}
