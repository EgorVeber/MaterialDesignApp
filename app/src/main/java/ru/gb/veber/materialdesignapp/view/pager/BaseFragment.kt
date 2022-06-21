package ru.gb.veber.materialdesignapp.view.pager

import AppState
import PictureViewModel
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureViewPagerBinding
import ru.gb.veber.materialdesignapp.utils.*
import java.util.*


class BaseFragment : Fragment() {

    private var _binding: FragmentPictureViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
    private var appStateSave: AppState.Success? = null

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
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
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

    private fun renderData(appState: AppState) {
        with(binding)
        {
            when (appState) {
                is AppState.Error -> {
                    title.text = getString(R.string.Error)
                    explanation.text = appState.errorMessage
                    imageView.load(R.drawable.nasa_api)
                }
                is AppState.Loading -> {
                    title.text = getString(R.string.loading)
                    imageView.load(R.drawable.loading1) {
                        crossfade(CROSS_FADE_500)
                    }
                }
                is AppState.Success -> {
                    appStateSave = appState
                    title.text = appState.pictureDTO.title
                    explanation.text = appState.pictureDTO.explanation
                    if (appState.pictureDTO.mediaType == "video") {
                        videoSuccess(appState)
                        imageView.load(R.drawable.nasa_api)
                    } else {
                        imageView.load(appState.pictureDTO.hdurl) {
                            placeholder(R.drawable.loading1)
                            crossfade(CROSS_FADE_500)
                            error(R.drawable.nasa_api)
                        }
                    }
                    datePicture.text = appState.pictureDTO.date
                }
                else -> {}
            }
        }
    }

    private fun videoSuccess(appState: AppState.Success) {

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
        fun newInstance(type: Int): Fragment {
            return BaseFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_KEY, type)
                }
            }
        }
    }
}
