package ru.gb.veber.materialdesignapp.view.pager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val ADAPTER_SIZE = 3

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return ADAPTER_SIZE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            BaseFragment.EARTH_FRAGMENT -> "Today"
            BaseFragment.MARS_FRAGMENT -> "Today-1"
            BaseFragment.SYSTEM_FRAGMENT -> "Today-2"
            else -> "Earth"
        }
    }


    override fun getItem(position: Int): Fragment {
        return BaseFragment.newInstance(position)
    }
}
class ViewPager2Adapter(private val fragmentManager: FragmentActivity): FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return ADAPTER_SIZE
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }

    override fun createFragment(position: Int): Fragment {
        return BaseFragment.newInstance(position)
    }
}