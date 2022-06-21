package ru.gb.veber.materialdesignapp.view.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.ViewPagerLayoutBinding
import ru.gb.veber.materialdesignapp.utils.KEY_TODAY
import ru.gb.veber.materialdesignapp.utils.KEY_YESTERDAY
import ru.gb.veber.materialdesignapp.utils.KEY_BEFORE_YESTERDAY

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

        binding.viewPager.adapter = ViewPager2Adapter(requireActivity())
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                KEY_TODAY -> getString(R.string.today)
                KEY_YESTERDAY -> getString(R.string.yesterday)
                KEY_BEFORE_YESTERDAY -> getString(R.string.before_yesterday)
                else -> getString(R.string.today)
            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ViewPagerFragment()
    }
}