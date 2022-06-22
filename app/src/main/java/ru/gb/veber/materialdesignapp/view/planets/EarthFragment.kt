package ru.gb.veber.materialdesignapp.view.planets

import AppState
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.gb.veber.materialdesignapp.BuildConfig
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.FragmentPlanetsBinding
import ru.gb.veber.materialdesignapp.utils.CROSS_FADE_500
import ru.gb.veber.materialdesignapp.viewmodel.EarthViewModel

class EarthFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private val planetsViewModel: EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
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
        planetsViewModel.getLiveData().observe(viewLifecycleOwner) { render(it) }
        planetsViewModel.getEpic()
    }

    private fun render(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.title.text = getString(R.string.Error)
            }
            is AppState.Loading -> {
                binding.title.text = getString(R.string.loading)
                binding.imageView.load(R.drawable.loading1) {
                    crossfade(CROSS_FADE_500)
                }
            }
            is AppState.SuccessEarthEpic -> {
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image = appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageView.load(url) {
                    placeholder(R.drawable.loading1)
                    crossfade(CROSS_FADE_500)
                    error(R.drawable.nasa_api)
                }
                binding.title.text = appState.serverResponseData.last().caption
                binding.date.text = appState.serverResponseData.last().date
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
            EarthFragment()
    }
}