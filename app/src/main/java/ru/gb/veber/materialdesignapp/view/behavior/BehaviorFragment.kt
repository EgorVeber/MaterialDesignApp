package ru.gb.veber.materialdesignapp.view.behavior

import PictureState
import PictureViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentBehaviorBinding
import ru.gb.veber.materialdesignapp.databinding.FragmentCoordinatorOldBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.utils.formatDate
import java.util.*


class BehaviorFragment : Fragment() {


    private val viewModel: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }
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
    }

    private fun renderData(appState: PictureState) {
        with(binding)
        {
            when (appState) {
                is PictureState.Error -> {
//                    title.text = getString(R.string.Error)
//                    explanation.text = appState.errorMessage
//                    imageView.load(R.drawable.nasa_api)
                }
                is PictureState.Loading -> {
//                    title.text = getString(R.string.loading)
//                    imageView.load(R.drawable.loading1) {
//                        crossfade(CROSS_FADE_500)
//                    }
                }
                is PictureState.Success -> {

                    binding.imageView.load(appState.pictureDTO.hdurl)
//                    appStateSave = appState
//                    title.text = appState.pictureDTO.title
//                    explanation.text = appState.pictureDTO.explanation
//                    if (appState.pictureDTO.mediaType == "video") {
//                        if (!checkState) {
//                            if (flag) videoSuccess(appState)
//                        }
//                        imageView.load(R.drawable.nasa_api)
//                    } else {
//                        imageView.load(appState.pictureDTO.hdurl) {
//                            placeholder(R.drawable.loading1)
//                            crossfade(CROSS_FADE_500)
//                            error(R.drawable.nasa_api)
//                        }
//                    }
//                    datePicture.text = appState.pictureDTO.date
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
