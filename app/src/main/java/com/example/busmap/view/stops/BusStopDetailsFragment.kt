package com.example.busmap.view.stops

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busmap.R
import com.example.busmap.view.adapters.OnViewButtonClickedListener
import com.example.busmap.view.adapters.RouteRecyclerViewAdapter
import com.example.busmap.view.viewmodel.StopViewModel
import com.example.busmap.view.viewmodelfactory.StopViewModelFactory
import com.example.busmap.core.domain.models.BusRoute
import com.example.busmap.core.domain.models.BusStop
import kotlinx.android.synthetic.main.fragment_bus_stop_details.*

class BusStopDetailsFragment : Fragment(), OnViewButtonClickedListener {
    //Bus object
    private lateinit var busStop : BusStop
    
    //View Model
    private lateinit var busStopDetailsViewModel: StopViewModel
    private lateinit var busStopDetailsViewModelFactory: StopViewModelFactory

    //Recycler view adapter
    private lateinit var routeAdapter : RouteRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bus_stop_details, container, false)

        //Get the argument
        //If there are arguments
        arguments?.let {
            //Create bus stop
            busStop = it.getParcelable<BusStop>("stop")!!
        }

        //Return the view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set the view
        setUpViews()
        setViewModel()
    }

    private fun setUpViews() {
        //Set the text
        details_name_text.text = getString(
            R.string.details_name_placeholder,
            busStop.name,
            busStop.code)

        details_address_text.text = getString(
            R.string.details_address_placeholder,
            busStop.address,
            busStop.street,
            busStop.zone)

        details_status_text.text = busStop.status

        details_type_text.text = busStop.type

        //Set back button
        back_button.setOnClickListener {
            //Return to the map fragment
            requireActivity().onBackPressed()
        }

        //Set save button
        bus_stop_save_button.setOnClickListener {
            busStopDetailsViewModel.addRoute(busStop)
        }

        //Set list view
        routeAdapter = RouteRecyclerViewAdapter(arrayListOf(), this)
        route_list_view.run {
            adapter = routeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    
    private fun setViewModel() {
        //Initialize view model and factory
        busStopDetailsViewModelFactory = StopViewModelFactory(requireContext())
        busStopDetailsViewModel = ViewModelProviders.of(requireActivity(), busStopDetailsViewModelFactory)
            .get(StopViewModel::class.java)

        //Observe data flow
        busStopDetailsViewModel.routes.observe(viewLifecycleOwner, Observer {
            route_list_view.visibility = View.VISIBLE
            routeAdapter.update(it)
        })

        busStopDetailsViewModel.loadingApi.observe(viewLifecycleOwner, Observer {
            if (it) {
                route_list_view.visibility = View.GONE
                route_loading_icon.visibility = View.VISIBLE
            } else {
                route_loading_icon.visibility = View.GONE
            }
        })

        busStopDetailsViewModel.errorApi.observe(viewLifecycleOwner, Observer {
            if (it) {
                route_loading_icon.visibility = View.GONE
                route_list_view.visibility = View.GONE
                route_error_icon.visibility = View.VISIBLE
                route_error_text.visibility = View.VISIBLE
            }
        })

        busStopDetailsViewModel.loadingRoom.observe(viewLifecycleOwner, Observer {
            if (it) {
                //TODO: Start loading
            } else {
                //TODO: End loading
            }
        })

        busStopDetailsViewModel.errorRoom.observe(viewLifecycleOwner, Observer {
            Log.i("ERRRRRo", "${it.isError} ${it.message}")
            if (it.isError) {
                //TODO: Display error message
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            } else {
                //TODO: Display success message
                Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show()
            }
        })

        //Get routes info
        busStopDetailsViewModel.getBusRoutes(busStop)
    }

    //Handle view button clicked
    override fun onClick(busRoute: BusRoute) {
        //Navigate to route info fragment
        val action =
            BusStopDetailsFragmentDirections.actionBusStopDetailsToRouteInfoFragment(
                busRoute.id
            )
        findNavController().navigate(action)
    }
}