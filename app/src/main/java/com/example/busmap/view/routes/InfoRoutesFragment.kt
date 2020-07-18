package com.example.busmap.view.routes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.R
import com.example.busmap.view.viewmodel.RouteViewModel
import com.example.busmap.view.viewmodelfactory.RouteViewModelFactory
import kotlinx.android.synthetic.main.fragment_info_routes.*

class InfoRoutesFragment : Fragment() {
    //View model and view model factory
    private lateinit var routeInfoViewModel : RouteViewModel
    private val routeInfoViewModelFactory = RouteViewModelFactory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set up ViewModel
        setUpViewModel()
    }

    private fun setUpViewModel() {
        //Get view model instance
        routeInfoViewModel = ViewModelProvider(requireActivity(), routeInfoViewModelFactory)
            .get(RouteViewModel::class.java)

        //Set observer
        routeInfoViewModel.routeInfo.observe(viewLifecycleOwner, Observer {
            routeinfo_inbound_text.text = it.inBound
            routeinfo_outbound_text.text = it.outBound
        })
    }
}