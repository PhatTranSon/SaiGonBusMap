package com.example.busmap.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.busmap.view.routes.InfoAdditionalFragment
import com.example.busmap.view.routes.InfoRoutesFragment
import com.example.busmap.view.routes.InfoStatisticsFragment

class RouteInfoTabAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoStatisticsFragment()
            1 -> InfoRoutesFragment()
            else -> InfoAdditionalFragment()
        }
    }
}