package ru.gb.veber.materialdesignapp.view.pictureDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.gb.veber.materialdesignapp.R
import ru.gb.veber.materialdesignapp.databinding.PictureDayLayoutBinding
import ru.gb.veber.materialdesignapp.utils.KEY_BEFORE_YESTERDAY
import ru.gb.veber.materialdesignapp.utils.KEY_TODAY
import ru.gb.veber.materialdesignapp.utils.KEY_YESTERDAY

class PictureDayMainFragment : Fragment() {

    private var _binding: PictureDayLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PictureDayLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


    }


    private fun init() {
        binding.viewPager.adapter = ViewPager2Adapter(requireActivity())
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                KEY_TODAY -> {
                    tab.text = getString(R.string.today)
                    tab.orCreateBadge.apply {
                        backgroundColor = resources.getColor(R.color.b100)
                    }
                }
                KEY_YESTERDAY -> {
                    tab.text = getString(R.string.yesterday)
                }
                KEY_BEFORE_YESTERDAY -> {
                    tab.text = getString(R.string.before_yesterday)
                }
            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PictureDayMainFragment()
    }
}