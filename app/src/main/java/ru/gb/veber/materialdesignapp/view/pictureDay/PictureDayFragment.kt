package ru.gb.veber.materialdesignapp.view.pictureDay

import PictureState
import PictureViewModel
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.transition.MaterialSharedAxis
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureViewPagerBinding
import ru.gb.veber.materialdesignapp.utils.*
import java.util.*


class PictureDayFragment : Fragment() {

    private var _binding: FragmentPictureViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
    private var appStateSave: PictureState.Success? = null
    private var checkState: Boolean = false
    private var flagImage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        arguments?.let {
            getPictureChipsClick(it.getInt(BUNDLE_KEY))
        }

        binding.imageView.setOnClickListener {
            flagImage = !flagImage
            if (flagImage) {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                (binding.imageView.layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.MATCH_PARENT
            } else {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                (binding.imageView.layoutParams as ConstraintLayout.LayoutParams).height =
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            }
        }
    }

    var flag = false
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
        with(binding)
        {
            when (appState) {
                is PictureState.Error -> {
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is PictureState.Loading -> {
                    title.text = getString(R.string.loading)
                    imageView.load(R.drawable.loading1) {
                        crossfade(CROSS_FADE_500)
                    }
                }
                is PictureState.Success -> {
                    appStateSave = appState
                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    if (appState.pictureDTO.mediaType == "video") {
                        if (!checkState) {
                            if (flag) videoSuccess(appState)
                        }
                        imageView.load(R.drawable.nasa_api)
                    } else {
                        imageView.load(appState.pictureDTO.hdurl) {
                            placeholder(R.drawable.loading1)
                            crossfade(CROSS_FADE_500)
                            error(R.drawable.nasa_api)
                            transformations(CircleCropTransformation())
                        }
                    }
                    datePicture.text = appState.pictureDTO.date
                }
                else -> {}
            }
        }
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
