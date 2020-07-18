package com.example.busmap.view.routes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.R
import com.example.busmap.view.util.BusStringUtil
import com.example.busmap.view.viewmodel.RouteViewModel
import com.example.busmap.view.viewmodelfactory.RouteViewModelFactory
import kotlinx.android.synthetic.main.fragment_info_additional.*

class InfoAdditionalFragment : Fragment() {
    //View model and factory
    private lateinit var routeInfoViewModel: RouteViewModel
    private val routeInfoViewModelFactory = RouteViewModelFactory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_additional, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set up
        setUpViewModel()
    }

    private fun setUpViewModel() {
        //Get view model
        routeInfoViewModel = ViewModelProvider(requireActivity(), routeInfoViewModelFactory)
            .get(RouteViewModel::class.java)

        //Set observer
        routeInfoViewModel.routeInfo.observe(viewLifecycleOwner, Observer {
            routeinfo_type_text.text = it.type
            routeinfo_org_text.text = BusStringUtil.formatOrganizationString(it.company)
        })
    }
}