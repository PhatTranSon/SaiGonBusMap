package com.example.busmap.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.busmap.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set click listener
        browse_card.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMenuHomeToMenuMap())
        }

        navigate_card.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMenuHomeToMenuFinderMap())
        }

        personalize_card.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMenuHomeToMenuList())
        }
    }
}