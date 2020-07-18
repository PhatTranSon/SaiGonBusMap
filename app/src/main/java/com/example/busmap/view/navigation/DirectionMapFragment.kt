package com.example.busmap.view.navigation

import android.graphics.Color
import android.media.Image
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busmap.R
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.BusNavigationDetails
import com.example.busmap.core.domain.models.BusNavigationPolyline
import com.example.busmap.view.adapters.DirectionRecyclerAdapter
import com.example.busmap.view.util.ImageUtils
import com.example.busmap.view.viewmodel.DirectionViewModel
import com.example.busmap.view.viewmodelfactory.DirectionViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_direction_map.*

class DirectionMapFragment : Fragment() {
    //Map Instance
    private var mapInstance : GoogleMap? = null

    //BusNavigation
    private var busNavigation : BusNavigation? = null

    //Recycler view
    private val directionRecyclerAdapter = DirectionRecyclerAdapter(arrayListOf())

    //ViewModel and Factory
    private val directionViewModelFactory = DirectionViewModelFactory()
    private lateinit var directionViewModel: DirectionViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        mapInstance = googleMap
        //Render bus stops
        busNavigation?.let {
            renderStops(it)
            renderBottomDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_direction_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        setUpMap()
        setUpViewModel()
    }

    private fun setUpNavigation() {
        arguments?.let {
            //Get bus
            busNavigation = it.getParcelable<BusNavigation>("navigation")
        }
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.direction_map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setUpViewModel() {
        //Create instance
        directionViewModel = ViewModelProviders.of(requireActivity(), directionViewModelFactory)
            .get(DirectionViewModel::class.java)

        //Observer
        directionViewModel.direction.observe(viewLifecycleOwner, Observer {
            renderPolyline(it)
        })

        directionViewModel.loading.observe(viewLifecycleOwner, Observer {
            //TODO
        })

        directionViewModel.error.observe(viewLifecycleOwner, Observer {
            //TODO
        })

        //Get data
        busNavigation?.let {
            directionViewModel.getPolyline(it)
        }
    }

    private fun renderPolyline(busNavigationPolyline: BusNavigationPolyline) {
        if (mapInstance != null) {
            //Draw pre
            val preOptions = PolylineOptions()
                .clickable(true)
                .width(10f)
                .color(Color.GREEN)
                .add(
                    *busNavigationPolyline.pre.coordinates
                        .map { LatLng(it.lat, it.lng) }
                        .toTypedArray()
                )

            //Draw main
            val mainOptions = PolylineOptions()
                .clickable(true)
                .width(10f)
                .color(R.color.path_purple)
                .add(
                    *busNavigationPolyline.main.coordinates
                        .map { LatLng(it.lat, it.lng) }
                        .toTypedArray()
                )

            //Draw post
            val postOptions = PolylineOptions()
                .clickable(true)
                .width(10f)
                .color(Color.GREEN)
                .add(
                    *busNavigationPolyline.post.coordinates
                        .map { LatLng(it.lat, it.lng) }
                        .toTypedArray()
                )

            //Add
            val prePolyline = mapInstance!!.addPolyline(preOptions)
            val mainPolyline = mapInstance!!.addPolyline(mainOptions)
            val postPolyline = mapInstance!!.addPolyline(postOptions)

            //Focus
            if (busNavigationPolyline.pre.coordinates.isNotEmpty()) {
                val focalPoint = busNavigationPolyline.pre.coordinates.first()
                mapInstance!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(focalPoint.lat, focalPoint.lng),
                        19f
                    )
                )
            } else {
                val focalPoint = busNavigationPolyline.main.coordinates.first()
                mapInstance!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(focalPoint.lat, focalPoint.lng),
                        19f
                    )
                )
            }
        }
    }

    private fun renderStops(busNavigation: BusNavigation) {
        if (mapInstance != null) {
            busNavigation.stops.forEach {
                //Render each stops
                val markerOptions = MarkerOptions()
                    .title(it.name)
                    .position(LatLng(it.lat, it.lng))

                //Set the bus stop icon only if it is not your destination and starting point
                if (it.name.trim() != "[Tọa độ điểm xuất phát]" && it.name.trim() != "[Tọa độ điểm đến]") {
                    markerOptions.icon(ImageUtils.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_bus_stop))
                } else {
                    markerOptions.icon(ImageUtils.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_marker))
                }

                //Place marker
                mapInstance!!.addMarker(markerOptions)
            }
        }
    }

    private fun renderBottomDialog() {
        //Set up recycler view
        direction_list_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = directionRecyclerAdapter
        }
        directionRecyclerAdapter.update(busNavigation!!.details)

        //Set up bottom sheet
        val bottomSheetView = bottom_dialog
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        //Set height
        bottomSheetBehavior.peekHeight = 300
        //bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}