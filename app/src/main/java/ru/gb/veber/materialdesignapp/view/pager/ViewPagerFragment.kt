package ru.gb.veber.materialdesignapp.view.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.gb.veber.materialdesignapp.databinding.ViewPagerLayoutBinding

class ViewPagerFragment : Fragment() {

    private var _binding: ViewPagerLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewPagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)


//        binding.viewPager.adapter = ViewPager2Adapter(requireActivity())
//        TabLayoutMediator(binding.tabLayout,binding.viewPager
//        ) { tab, position ->
//            tab.text = when (position) {
//                BaseFragment.EARTH_FRAGMENT -> "Earth"
//                BaseFragment.MARS_FRAGMENT -> "Mars"
//                BaseFragment.SYSTEM_FRAGMENT -> "System"
//                else -> "Earth"
//            }
//        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ViewPagerFragment()
    }
}