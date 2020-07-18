package com.example.busmap.view.stops

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.busmap.R
import com.example.busmap.view.util.ImageUtils
import com.example.busmap.view.viewmodel.MapViewModel
import com.example.busmap.view.viewmodelfactory.MapViewModelFactory
import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(),
    OnMapReadyCallback,
    PlaceSelectionListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMarkerClickListener{
    //View model and factory
    private lateinit var mapFragmentViewModel : MapViewModel
    private lateinit var mapFragmentViewModelFactory: MapViewModelFactory

    //Map components
    private lateinit var mapInstance : GoogleMap
    private val previousMarkers = ArrayList<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set up map and autocomplete
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        val autoCompleteFragment = childFragmentManager.findFragmentById(R.id.map_autocomplete) as AutocompleteSupportFragment

        //Listeners
        mapFragment.getMapAsync(this)
        autoCompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS))
        autoCompleteFragment.setOnPlaceSelectedListener(this)

        //Set up view models
        mapFragmentViewModelFactory = MapViewModelFactory(requireContext())
        mapFragmentViewModel = ViewModelProviders.of(this, mapFragmentViewModelFactory)
            .get(MapViewModel::class.java)

        setUpViewModel()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //Set up google map [on event]
        mapInstance = googleMap
        with (mapInstance) {
            setOnCameraIdleListener(this@MapFragment)
            setOnMarkerClickListener(this@MapFragment)
        }

        //Focus on HCM city
        val latLng = LatLng(10.763675794838123, 106.70292234420776)
        mapInstance.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

        //Stop loading
        loading_icon.visibility = View.GONE
    }

    override fun onPlaceSelected(place: Place) {
        //Get the lat and long
        val coords = place.latLng
        //If there is a coordinates
        if (coords != null) {
            //Place marker and move camera
            mapInstance.addMarker(MarkerOptions().position(coords).title("Matched Location"))
            mapInstance.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 17f))
        }
    }

    override fun onError(p0: Status) {
        //Place selection Error -> Not implemented
    }

    override fun onCameraIdle() {
        //On camera stop moving -> Get all bus stops
        val bounds = mapInstance.projection.visibleRegion.latLngBounds

        //Get the busStops
        mapFragmentViewModel.getBusStops(bounds)
    }

    private fun setUpViewModel() {
        mapFragmentViewModel.busStops.observe(viewLifecycleOwner, Observer {
            //Remove previous marker
            clearAllMarkers()

            //Redraw
            it.forEachIndexed { index, stop ->
                //Place marker for each bus stops
                val markerOptions = MarkerOptions()
                    .position(LatLng(stop.lat, stop.lng))
                    .icon(ImageUtils.bitmapDescriptorFromVector(requireContext(),
                        R.drawable.ic_bus_stop
                    ))
                    .title(stop.address + " " + stop.name)

                //Create marker and set index
                val marker = mapInstance.addMarker(markerOptions)
                marker.tag = index

                //Add to previous markers
                previousMarkers.add(marker)
            }
        })
    }

    private fun clearAllMarkers() {
        previousMarkers.forEach {
            it.remove()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //Get the bus stop value
        val id = marker.tag as Int

        //Get the bus stop and [...]
        mapFragmentViewModel.busStops.value?.let {
            //Get the stop
            val busStop = it[id]

            //Navigate
            val navController = findNavController()
            val action =
                MapFragmentDirections.actionMenuMapToBusStopDetails(busStop)
            navController.navigate(action)
        }
        return true
    }
}