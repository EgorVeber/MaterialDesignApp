package ru.gb.veber.materialdesignapp.view.pictureDay

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val ADAPTER_SIZE = 3

class ViewPager2Adapter(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return ADAPTER_SIZE
    }

    override fun createFragment(position: Int): Fragment {
        return PictureDayFragment.newInstance(position)
    }
}