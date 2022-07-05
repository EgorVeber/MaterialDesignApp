package ru.gb.veber.materialdesignapp.view.listPicture

import ListPictureState
import ListPictureViewModel
import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.DateDialogBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureListBinding
import ru.gb.veber.materialdesignapp.utils.*
import show
import java.util.*

class ListPictureDayFragment : Fragment() {

    private var _binding: FragmentPictureListBinding? = null
    private val binding get() = _binding!!
    private var flag = false

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
        init()
    }

    private fun init() {
        binding.pictureListRecycler.adapter = adapter

        val listPictureViewModel = ViewModelProvider(this).get(ListPictureViewModel::class.java)

        with(listPictureViewModel) {
            getLiveData().observe(viewLifecycleOwner) { render(it) }
            sendServerRequest(takeDate(START_DATE), takeDate(END_DATE))
        }

        with(binding) {
            textEditStart.setText(takeDate(START_DATE))
            textEditEnd.setText(takeDate(END_DATE))

            textEditStart.setOnClickListener {
                showDialogDate(textEditStart.text.toString(), 0)
            }
            textEditEnd.setOnClickListener {
                showDialogDate(textEditEnd.text.toString(), 1)
            }

            datePikerFab.setOnClickListener { animationCalendar() }

            binding.selectButton.setOnClickListener {
                sendServerRequest(listPictureViewModel)
            }
        }
    }

    private fun sendServerRequest(listPictureViewModel: ListPictureViewModel) {
        listPictureViewModel.sendServerRequest(
            binding.textEditStart.text.toString(),
            binding.textEditEnd.text.toString()
        )
        binding.datePikerFab.performClick()
    }

    private fun animationCalendar() {

        val heightTextInput = binding.textInputStartDate.layoutParams.height
        val datePikerFabY = binding.datePikerFab.y
        val inputStartY = -datePikerFabY - heightTextInput.toFloat()
        val selectButtonY = -datePikerFabY - heightTextInput.toFloat() / 3
        val inputEndY = -datePikerFabY - heightTextInput.toFloat() / 2
        val durationRotation = 2000L
        val durationAlpha = 1000L
        val viewRotation = 480F

        flag = !flag

        if (flag) {
            ObjectAnimator.ofFloat(binding.datePikerFab, View.ROTATION, viewRotation)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.datePikerFab, View.TRANSLATION_X, -100F)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.textInputStartDate, View.TRANSLATION_Y, inputStartY)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.selectButton, View.TRANSLATION_Y, selectButtonY)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.textInputEndDate, View.TRANSLATION_Y, inputEndY)
                .setDuration(durationRotation).start()

            with(binding) {
                pictureListRecycler.animate().alpha(0F).duration = durationAlpha
                textInputStartDate.animate().alpha(1F).duration = durationAlpha
                textInputEndDate.animate().alpha(1F).duration = durationAlpha
                selectButton.animate().alpha(1F).duration = durationAlpha
            }

        } else {
            ObjectAnimator.ofFloat(binding.datePikerFab, View.ROTATION, 0F)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.datePikerFab, View.TRANSLATION_X, 0F)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.textInputStartDate, View.TRANSLATION_Y, 0F)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.textInputEndDate, View.TRANSLATION_Y, 0F)
                .setDuration(durationRotation).start()

            ObjectAnimator.ofFloat(binding.selectButton, View.TRANSLATION_Y, 0F)
                .setDuration(durationRotation).start()

            with(binding) {
                pictureListRecycler.animate().alpha(1F).duration = durationAlpha
                textInputStartDate.animate().alpha(0.5F).duration = durationAlpha
                textInputEndDate.animate().alpha(0.5F).duration = durationAlpha
                selectButton.animate().alpha(0.5F).duration = durationAlpha
            }
        }
    }

    private fun render(appState: ListPictureState) {
        when (appState) {
            is ListPictureState.Error -> {
                binding.loadingImage.show()
                binding.loadingImage.load(R.drawable.nasa_api)
            }
            is ListPictureState.Loading -> {
                //binding.loadingImage.show()
                binding.loadingImage.load(R.drawable.loading1)
            }
            is ListPictureState.Success -> {
                //binding.loadingImage.hide()
                adapter.setList(appState.pictureList)
            }
        }
    }

    private fun showDialogDate(date: String, type: Int) {

        val bindingDialog = DateDialogBinding.inflate(LayoutInflater.from(requireContext()))
        initDatePicker(dataFromString(date) as Date, bindingDialog.inputDate)

        Dialog(requireContext()).apply {
            setContentView(bindingDialog.root)
            bindingDialog.PositiveButtonDate.setOnClickListener {
                if (type == 0) {
                    binding.textEditStart.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                } else {
                    binding.textEditEnd.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                }
                dismiss()
            }
        }.show()
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