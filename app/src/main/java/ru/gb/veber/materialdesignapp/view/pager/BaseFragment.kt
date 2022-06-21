package ru.gb.veber.materialdesignapp.view.pager

import AppState
import PictureViewModel
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

        Log.d(
            "TAG",
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )
        Log.d(
            "TAG",
            arguments?.getInt(BUNDLE_KEY).toString()
        )
        arguments?.let {
            getPictureChipsClick(it.getInt(BUNDLE_KEY))
        }
    }


    private fun getPictureChipsClick(key: Int) {
        when (key) {
            0 -> viewModel.sendServerRequest(Date().formatDate())
            1 -> viewModel.sendServerRequest(takeDate(KEY_CHIP_YESTERDAY))
            2 -> viewModel.sendServerRequest(takeDate(KEY_CHIP_BEFORE_YD))
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.d("TAG", "setUserVisibleHint() called with: isVisibleToUser = $isVisibleToUser")
        }else{
            Log.d("TAG", "setUserVisibleHint() called with: isVisibleToUser = $isVisibleToUser")
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
                    title.text = appState.pictureDTO.title
                    appState.pictureDTO.mediaType
                    explanation.text = appState.pictureDTO.explanation
                    if (appState.pictureDTO.mediaType == "video") {
                        videoSuccess(appState)
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

    companion object {
        internal const val EARTH_FRAGMENT = 0
        internal const val MARS_FRAGMENT = 1
        internal const val SYSTEM_FRAGMENT = 2
        private const val BUNDLE_KEY = "key"

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
