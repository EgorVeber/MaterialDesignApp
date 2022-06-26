package ru.gb.veber.materialdesignapp.view.planets

import MarsState
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPlanetsBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.viewmodel.MarsViewModel

class MarsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private val marsViewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marsViewModel.getLiveData().observe(viewLifecycleOwner) { render(it) }
        marsViewModel.getMarsPicture()
    }

    private fun render(appState: MarsState) {
        when (appState) {
            is MarsState.Error -> {
                binding.title.text = getString(R.string.Error)
            }
            is MarsState.Loading -> {
                binding.title.text = getString(R.string.loading)
                binding.imageView.load(R.drawable.loading1) {
                    crossfade(CROSS_FADE_500)
                }
            }
            is MarsState.Success -> {

                if (appState.serverResponseData.photos.isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.curiosity),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    val url = appState.serverResponseData.photos.first().imgSrc
                    binding.imageView.load(url){
                        placeholder(R.drawable.loading1)
                        crossfade(CROSS_FADE_500)
                        error(R.drawable.nasa_api)
                    }
                    binding.title.text = getString(R.string.NOAA)
                    binding.date.text = appState.serverResponseData.photos.first().earth_date
                }
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
            MarsFragment()
    }
}