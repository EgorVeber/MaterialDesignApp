package ru.gb.veber.materialdesignapp.view.pictureDay

import PictureState
import PictureViewModel
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import hide
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureListBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureViewPagerBinding
import ru.gb.veber.materialdesignapp.utils.*
import ru.gb.veber.materialdesignapp.view.BaseFragment
import show
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import java.util.*


class PictureDayFragment :
    BaseFragment<FragmentPictureViewPagerBinding>(FragmentPictureViewPagerBinding::inflate) {


    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    private var appStateSave: PictureState.Success? = null
    private var checkState: Boolean = false
    private var flagImage = false
    private var flag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inti()
    }

    private fun inti() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }

        arguments?.let {
            getPictureChipsClick(it.getInt(BUNDLE_KEY))
        }

        binding.imageView.setOnClickListener {
            changeBoundsTransitionImage()
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        flag = menuVisible
        if (menuVisible) {
            appStateSave?.let { videoSuccess(it) }
        }
    }

    private fun getPictureChipsClick(key: Int) {
        when (key) {
            KEY_TODAY -> viewModel.sendServerRequest(Date().formatDate())
            KEY_YESTERDAY -> viewModel.sendServerRequest(takeDate(KEY_YESTERDAY_DATA))
            KEY_BEFORE_YESTERDAY -> viewModel.sendServerRequest(takeDate(KEY_BEFORE_YESTERDAY_DATA))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_STATE_BASE_FRAGMENT, true)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        checkState = savedInstanceState?.getBoolean(KEY_STATE_BASE_FRAGMENT, false) == true
    }

    private fun renderData(appState: PictureState) {
        transitionText()
        transitionImage()
        transitionStateOne()
        with(binding) {
            when (appState) {
                is PictureState.Error -> {
                    transitionStateTwo()
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is PictureState.Loading -> {
                    imageView.load(R.drawable.loading1)
                }
                is PictureState.Success -> {
                    appStateSave = appState
                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    datePicture.text = appState.pictureDTO.date

                    if (appState.pictureDTO.mediaType == "video") {
                        if (!checkState) {
                            if (flag) videoSuccess(appState)
                        }
                        imageView.load(R.drawable.nasa_api)
                    } else {
                        imageView.load(appState.pictureDTO.hdurl) {
                            placeholder(R.drawable.loading1)
                            error(R.drawable.nasa_api)
                        }
                    }
                    transitionStateTwo()
                }
                else -> {}
            }
        }
    }

    private fun transitionImage() {
        TransitionSet().apply {
            addTransition(Slide(Gravity.TOP))
            duration = 2000L
            TransitionManager.beginDelayedTransition(binding.mainImageConstrain, this)
        }
    }

    private fun transitionText() {
        TransitionSet().apply {
            addTransition(Slide(Gravity.BOTTOM).apply { duration = 1000 })
            addTransition(ChangeBounds().apply { duration = 1500L })
            TransitionManager.beginDelayedTransition(binding.constraintText, this)
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
                (layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.MATCH_PARENT

            } else {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                (layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            }
        }
    }


    private fun transitionStateOne() {
        binding.title.hide()
        binding.explanation.hide()
        binding.imageView.hide()
    }

    private fun transitionStateTwo() {
        binding.title.show()
        binding.explanation.show()
        binding.imageView.show()
    }

    private fun videoSuccess(appState: PictureState.Success) {
        if (appState.pictureDTO.mediaType == "video") {
            AlertDialog.Builder(context).setTitle(getString(R.string.videoMediaType))
                .setMessage(getString(R.string.mediaType))
                .setPositiveButton(getString(R.string.Open)) { _, _ ->
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(appState.pictureDTO.url)
                    })
                }.setNegativeButton(getString(R.string.Close)) { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }
    }

    companion object {
        private const val BUNDLE_KEY = "BUNDLE_KEY"

        @JvmStatic
        fun newInstance(type: Int) = PictureDayFragment().apply {
            arguments = Bundle().apply {
                putInt(BUNDLE_KEY, type)
            }
        }
    }
}
