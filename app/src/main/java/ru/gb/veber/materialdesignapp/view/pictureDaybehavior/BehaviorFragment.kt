package ru.gb.veber.materialdesignapp.view.pictureDaybehavior

import PictureState
import PictureViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.DateDialogBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentBehaviorBinding
import ru.gb.veber.materialdesignapp.utils.dataFromString
import ru.gb.veber.materialdesignapp.utils.formatDate
import ru.gb.veber.materialdesignapp.utils.getDateFromDatePicker
import ru.gb.veber.materialdesignapp.utils.initDatePicker
import show
import java.util.*


class BehaviorFragment : Fragment() {

    private var flagImage = false

    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
    private lateinit var bSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: FragmentBehaviorBinding? = null

    private val youTubePlayerView by lazy {
        binding.youtubePlayer
    }

    private val binding get() = _binding!!

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
        init()
        bSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContainer)
        lifecycle.addObserver(youTubePlayerView!!)
    }

    private fun init() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerRequest(Date().formatDate())

        binding.inputEditText.setText(Date().formatDate())
        binding.inputLayout.setEndIconOnClickListener { showDialogDate() }
        binding.inputEditText.setOnClickListener { showDialogDate() }

        binding.closePlayer.setOnClickListener {
            binding.youtubePlayer.hide()
            binding.closePlayer.hide()
            binding.imageView.show()
        }

        binding.imageView.setOnClickListener {
            changeBoundsTransitionImage()
        }
    }

    private fun renderData(appState: PictureState) {
        bSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        slideTransitionTextDay()

        with(binding)
        {
            when (appState) {
                is PictureState.Error -> {
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                    title.show()
                    explanation.show()
                }
                is PictureState.Loading -> {
                    imageView.load(R.drawable.loading1)
                }
                is PictureState.Success -> {
                    title.show()
                    explanation.show()

                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    datePicture.text = appState.pictureDTO.date

                    if (appState.pictureDTO.mediaType == "video") {
                        imageView.load(R.drawable.nasa_api)
                        showNasaVideo(appState.pictureDTO.url!!)
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

    private fun changeBoundsTransitionImage() {
        TransitionSet().also { transition ->
            transition.duration = 1000L
            transition.addTransition(ChangeBounds())
            transition.addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.root, transition)
        }
        flagImage = !flagImage
        with(binding.imageView) {
            if (flagImage) {
                scaleType = ImageView.ScaleType.CENTER_CROP
                (layoutParams as CoordinatorLayout.LayoutParams).height =
                    CoordinatorLayout.LayoutParams.MATCH_PARENT
            } else {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                (layoutParams as CoordinatorLayout.LayoutParams).height =
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT
            }
        }
    }

    private fun slideTransitionTextDay() {
        TransitionSet().also { transition ->
            transition.addTransition(Slide(Gravity.START))
            transition.duration = 500L
            TransitionManager.beginDelayedTransition(binding.bottomSheetContainer, transition)
        }
        binding.title.hide()
        binding.explanation.hide()
    }

    private fun showNasaVideo(url: String) {
        binding.youtubePlayer.show()
        binding.closePlayer.show()
        binding.imageView.hide()
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(findVideoId(url), 0f)
            }
        })
    }

    private fun findVideoId(url: String): String {
        return url.substringAfterLast('/').substringBefore('?')
    }

    private fun showDialogDate() {

        val flag = binding.inputLayout.alpha > 0.5F
        val bindingDialog = DateDialogBinding.inflate(LayoutInflater.from(requireContext()))

        initDatePicker(
            dataFromString(binding.inputEditText.text.toString()) as Date,
            bindingDialog.inputDate
        )

        Dialog(requireContext()).apply {
            setContentView(bindingDialog.root)
            bindingDialog.PositiveButtonDate.setOnClickListener {
                //Чтобы по красивей было для анимации
                binding.explanation.text = ""
                binding.title.text = ""
                binding.inputEditText.setText(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                viewModel.sendServerRequest(getDateFromDatePicker(bindingDialog.inputDate).formatDate())
                dismiss()
            }
            if (flag) {
                show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BehaviorFragment()
    }
}