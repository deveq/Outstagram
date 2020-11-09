package com.soldemom.outstagram.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return SettingFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "내 포스팅"
            2 -> "업로드"
            3 -> "설정"
            else -> "전체 포스팅"
        }


    }
}