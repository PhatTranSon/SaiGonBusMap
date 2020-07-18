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
import com.example.busmap.view.adapters.RouteInfoTabAdapter
import com.example.busmap.view.customview.WorkaroundMapFragment
import com.example.busmap.view.util.ImageUtils
import com.example.busmap.view.viewmodel.RouteViewModel
import com.example.busmap.view.viewmodelfactory.RouteViewModelFactory
import com.example.busmap.core.domain.models.BusRoute
import com.example.busmap.core.domain.models.BusRoutePolyline
import com.example.busmap.core.domain.models.BusStop
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_route_info.*

class RouteInfoFragment : Fragment(), OnMapReadyCallback {
    //Bus route
    private lateinit var busRoute : BusRoute

    //Map instance
    private var mapInstance : GoogleMap? = null

    //Adapter
    private lateinit var routeInfoTabAdapter: RouteInfoTabAdapter

    //ViewModel and Factory
    private val routeInfoViewModelFactory = RouteViewModelFactory()
    private lateinit var routeInfoViewModel : RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBusRoute()
        setUpMap()
        setUpTab()
        setUpViewModel()
    }

    private fun getBusRoute() {
        arguments?.let {
            val routeId = it.getString("routeId", "None")
            busRoute = BusRoute(
                "","","", "", routeId, emptyList(), "", ""
            )
        }
    }

    private fun setUpViewModel() {
        //Get instances
        routeInfoViewModel = ViewModelProvider(requireActivity(), routeInfoViewModelFactory)
            .get(RouteViewModel::class.java)

        //Observe
        routeInfoViewModel.routeInfo.observe(viewLifecycleOwner, Observer {
            //Update name
            routeinfo_name_text.text = it.name
            routeinfo_name_text.visibility = View.VISIBLE
            //Update tab layout
            routeinfo_tab_layout.visibility = View.VISIBLE
        })

        routeInfoViewModel.loading.observe(viewLifecycleOwner, Observer {
            //Update layout
            if (it) {
                routeinfo_progress_bar.visibility = View.VISIBLE
                routeinfo_name_text.visibility = View.GONE
                routeinfo_tab_layout.visibility = View.GONE
            } else {
                routeinfo_progress_bar.visibility = View.GONE
            }
        })

        routeInfoViewModel.stops.observe(viewLifecycleOwner, Observer {
            Log.i("SIZE", busRoute.id)
            drawStops(it)
        })

        routeInfoViewModel.path.observe(viewLifecycleOwner, Observer {
            //Render path
            drawRoutePolygon(it)
        })

        //Get info
        if (busRoute.id != "None") {
            routeInfoViewModel.getInfo(busRoute)
            routeInfoViewModel.getPolyline(busRoute)
            routeInfoViewModel.getStops(busRoute)
        }
    }

    private fun setUpMap() {
        //Prepare the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.routeinfo_map_fragment) as
                WorkaroundMapFragment

        //Set up to enable map vertical move
        mapFragment.setListener(object : WorkaroundMapFragment.OnTouchListener {
            override fun onTouch() {
                custom_scrollview.requestDisallowInterceptTouchEvent(true)
            }
        })

        //Set up map
        mapFragment.getMapAsync(this)
    }

    private fun setUpTab() {
        //Set up view pager
        routeInfoTabAdapter = RouteInfoTabAdapter(this)
        routeinfo_viewpager.adapter = routeInfoTabAdapter

        //Attach to view pager
        TabLayoutMediator(routeinfo_tab_layout, routeinfo_viewpager) { tab, position ->
            tab.text = when(position) {
                0 -> "Statistics"
                1 -> "Routes"
                else -> "Additional"
            }
        }.attach()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //Create map
        mapInstance = googleMap

        //Set up map
        mapInstance?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.isZoomControlsEnabled = true
        }

        //Draw stops polygons
        routeInfoViewModel.getStops(busRoute)
    }

    private fun drawRoutePolygon(busRoutePolyline: BusRoutePolyline) {
        if (mapInstance != null && busRoutePolyline.coordinates.isNotEmpty()) {
            //Draw route polygon using stops' coordinates
            val polylineOptions = PolylineOptions().clickable(true)
                .color(R.color.title_color)

            //Add coordinates to polygons
            polylineOptions.add(
                *busRoutePolyline.coordinates.map {
                    LatLng(it.lat, it.lng)
                }.toTypedArray()
            )

            //Show polyline
            val polyline = mapInstance?.addPolyline(polylineOptions)

            //Move camera to first position
            val firstPos = LatLng(busRoutePolyline.coordinates[0].lat,
                busRoutePolyline.coordinates[0].lng)
            mapInstance?.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPos, 17f))
        }
    }

    private fun drawStops(stops : List<BusStop>) {
        //Draw bus stops
        stops.forEach {
            //Add marker
            val latLng = LatLng(it.lat, it.lng)
            val markerOptions = MarkerOptions()
                .position(latLng)
                .icon(ImageUtils.bitmapDescriptorFromVector(requireContext(),
                    R.drawable.ic_marker
                ))
                .title(it.name)

            //Add if exist
            mapInstance?.addMarker(markerOptions)
        }
    }
}