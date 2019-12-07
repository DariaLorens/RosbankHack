package ru.lorens.rosbankhack.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.lorens.rosbankhack.fragments.ScreenFragment
import ru.lorens.rosbankhack.rest.Screen

class CardPageAdapter(fm: FragmentManager, val list: List<Screen>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return ScreenFragment(list[position])
    }
}