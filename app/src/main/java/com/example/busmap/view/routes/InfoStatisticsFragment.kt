package com.example.busmap.view.routes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.R
import com.example.busmap.view.viewmodel.RouteViewModel
import com.example.busmap.view.viewmodelfactory.RouteViewModelFactory
import kotlinx.android.synthetic.main.fragment_info_statistics.*

class InfoStatisticsFragment : Fragment() {
    //ViewModel and Factory
    private val routeInfoViewModelFactory: RouteViewModelFactory = RouteViewModelFactory()
    private lateinit var routeInfoViewModel: RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set up view models and factory
        setUpViewModel()
    }

    private fun setUpViewModel() {
        routeInfoViewModel = ViewModelProvider(requireActivity(), routeInfoViewModelFactory).get(RouteViewModel::class.java)

        //Set on listener
        routeInfoViewModel.routeInfo.observe(viewLifecycleOwner, Observer {
            //Display
            Log.i("UPDATED", "View model updated")
            statistics_distance_text.text = it.distance.toString() + " m"
            statistics_operation_time_text.text = it.operationTime
            statistics_seat_text.text = it.numberOfSeat
            statistics_waiting_time_text.text = it.timeOfWait + " minutes"
            statistics_trip_time_text.text = it.timeOfTrip + " minutes"
        })
    }
}