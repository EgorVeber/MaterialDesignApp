package ru.gb.veber.materialdesignapp.view.planets

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


private const val ADAPTER_SIZE = 3
const val FRAGMENT_EARTH = "Earth"
const val FRAGMENT_EARTH_KEY = 0
const val FRAGMENT_MARS = "Mars"
const val FRAGMENT_MARS_KEY = 1
const val FRAGMENT_PICTURE = "Picture+"
const val FRAGMENT_PICTURE_KEY = 2


class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return ADAPTER_SIZE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FRAGMENT_EARTH_KEY -> FRAGMENT_EARTH
            FRAGMENT_MARS_KEY -> FRAGMENT_MARS
            FRAGMENT_PICTURE_KEY -> FRAGMENT_PICTURE
            else -> FRAGMENT_EARTH
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FRAGMENT_EARTH_KEY -> EarthFragment.newInstance()
            FRAGMENT_MARS_KEY -> MarsFragment.newInstance()
            FRAGMENT_PICTURE_KEY -> ListPictureDayFragment.newInstance()
            else -> EarthFragment.newInstance()
        }
    }
}
