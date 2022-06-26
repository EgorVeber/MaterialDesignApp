package ru.gb.veber.materialdesignapp.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PlanetsLayoutBinding


class PlanetsMainFragment : Fragment() {

    private var _binding: PlanetsLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanetsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_image_24)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlanetsMainFragment()
    }
}