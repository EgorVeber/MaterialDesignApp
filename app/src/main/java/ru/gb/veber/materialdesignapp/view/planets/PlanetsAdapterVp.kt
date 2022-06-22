package ru.gb.veber.materialdesignapp.view.planets

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


private const val ADAPTER_SIZE = 3

class PlanetsAdapterVp(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return ADAPTER_SIZE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Earth"
            1 -> "Mars"
            2 -> "PictureOfTheDay+"
            else -> "Earth"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> EarthFragment()
            1 -> MarsFragment()
            2 -> PictureDayCustomFragment()
            else -> EarthFragment()
        }
    }
}
