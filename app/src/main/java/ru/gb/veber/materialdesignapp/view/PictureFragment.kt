package ru.gb.veber.materialdesignapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.gb.m_1975_3.viewmodel.AppState
import com.gb.m_1975_3.viewmodel.PictureVM
import ru.gb.veber.materialdesignapp.databinding.FragmentPictureBinding

class PictureFragment : Fragment() {


    private var _binding: FragmentPictureBinding? = null
    private val binding: FragmentPictureBinding
        get() {
            return _binding!!
        }

    val viewModel: PictureVM by lazy {
        ViewModelProvider(this).get(PictureVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendServerRequest()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {/*TODO HW*/
            }
            is AppState.Loading -> {/*TODO HW*/
            }
            is AppState.Success -> {
                with(appState.pictureDTO)
                {
                    Log.d("@@@","pictureDTO:")
                    Log.d("@@@",date.toString())
                    Log.d("@@@",explanation.toString())
                    Log.d("@@@",hdurl.toString())
                    Log.d("@@@",media_type.toString())
                    Log.d("@@@",service_version.toString())
                    Log.d("@@@",title.toString())
                    Log.d("@@@",url.toString())
                }
                binding.imageView.load(appState.pictureDTO.url)
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
            PictureFragment()
    }
}