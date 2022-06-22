package ru.gb.veber.materialdesignapp.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.veber.materialdesignapp.databinding.FragmentPlanetsBinding

class PictureDayCustomFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PictureDayCustomFragment()
    }
}