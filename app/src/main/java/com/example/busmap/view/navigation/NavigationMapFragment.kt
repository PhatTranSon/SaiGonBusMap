package com.example.busmap.view.navigation

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import com.example.busmap.R
import com.example.busmap.core.domain.models.Coordinate
import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_finder_map.*

class NavigationMapFragment : Fragment() {
    //Map instance
    private var mapInstance : GoogleMap? = null

    //Save starting point and destination
    private var startLocation : Coordinate? = null
    private var endLocation : Coordinate? = null

    //Markers
    private var fromMarker : Marker? = null
    private var toMarker : Marker? = null
    private var polyline : Polyline? = null

    private val callback = OnMapReadyCallback { googleMap ->
        mapInstance = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finder_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayOrHideFindButton()
        setUpButton()
        setUpMap()
        setUpAutoComplete()
    }

    private fun setUpButton() {
        finder_find_button.setOnClickListener {
            //Get the start and end coordinates
            val action =
                NavigationMapFragmentDirections.actionMenuFinderMapToNavigationFragment(
                    startLocation!!.lat.toFloat(),
                    startLocation!!.lng.toFloat(),
                    endLocation!!.lat.toFloat(),
                    endLocation!!.lng.toFloat()
                )

            //Navigate to list
            findNavController().navigate(action)
        }
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.finder_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setUpAutoComplete() {
        //Get instance
        val fromAutoComplete = childFragmentManager.findFragmentById(R.id.finder_from_text) as AutocompleteSupportFragment
        val toAutoComplete = childFragmentManager.findFragmentById(R.id.finder_to_text) as AutocompleteSupportFragment

        //Set text
        fromAutoComplete.setHint("Find starting location")
        toAutoComplete.setHint("Find destination")

        //Set place format
        fromAutoComplete.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        ))
        toAutoComplete.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        ))

        //Set chosen listener
        fromAutoComplete.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                //Choose starting point
                place.latLng?.let {
                    startLocation = Coordinate(it.latitude, it.longitude)
                    displayOrHideFindButton()
                    manageFromMarker()
                    managePolyline()
                }
            }

            override fun onError(status: Status) {
                //Display message
                Log.e("FROMERROR", status.statusMessage)
            }

        })

        toAutoComplete.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                //Choose destination point
                place.latLng?.let {
                    endLocation = Coordinate(it.latitude, it.longitude)
                    displayOrHideFindButton()
                    manageToMarker()
                    managePolyline()
                }
            }

            override fun onError(status: Status) {
                //Display message
                Log.e("TOERROR", status.statusMessage)
            }

        })

        //Set delete listener
        fromAutoComplete.view?.findViewById<AppCompatImageButton>(R.id.places_autocomplete_clear_button)
            ?.setOnClickListener {
                fromAutoComplete.setText("")
                startLocation = null
                displayOrHideFindButton()
                manageFromMarker()
                managePolyline()
            }

        toAutoComplete.view?.findViewById<AppCompatImageButton>(R.id.places_autocomplete_clear_button)
            ?.setOnClickListener {
                toAutoComplete.setText("")
                endLocation = null
                displayOrHideFindButton()
                manageToMarker()
                managePolyline()
            }
    }

    private fun manageFromMarker() {
        //Remove first
        if (fromMarker != null) {
            fromMarker!!.remove()
        }

        if (startLocation != null) {
            //Create lat lng
            val startLatLng = LatLng(startLocation!!.lat, startLocation!!.lng)

            //Add marker and zoom
            fromMarker = mapInstance!!.addMarker(
                MarkerOptions().position(startLatLng)
            )
            mapInstance!!.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 17f))
        }
    }

    private fun manageToMarker() {
        if (toMarker != null) {
            toMarker!!.remove()
        }

        if (endLocation != null) {
            //Create lat lng
            val endLatLng = LatLng(endLocation!!.lat, endLocation!!.lng)

            //Add marker and zoom
            toMarker = mapInstance!!.addMarker(
                MarkerOptions().position(endLatLng)
            )
            mapInstance!!.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng, 10f))
        }
    }

    private fun displayOrHideFindButton() {
        finder_find_button.visibility = if (startLocation != null && endLocation != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun managePolyline() {
        //Connect the marker
        if (polyline != null) {
            polyline!!.remove()
        }

        if (startLocation != null && endLocation != null && mapInstance != null) {
            //Create latlng
            val startLatLng = LatLng(startLocation!!.lat, startLocation!!.lng)
            val endLatLng = LatLng(endLocation!!.lat, endLocation!!.lng)

            //Build lat lng bounds
            val builder = LatLngBounds.Builder()
            builder.include(startLatLng)
            builder.include(endLatLng)
            val bounds = builder.build()

            //Zoom camera
            mapInstance!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))

            //Draw polyline
            val polylineOptions = PolylineOptions()
                .color(R.color.title_color)
                .addAll(
                    listOf(startLatLng, endLatLng)
                )
            polyline = mapInstance!!.addPolyline(polylineOptions)
        }
    }
}