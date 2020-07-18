package com.example.busmap.view.navigation

import android.os.Bundle
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
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.view.adapters.NavigationRecyclerAdapter
import com.example.busmap.view.viewmodel.NavigationViewModel
import com.example.busmap.view.viewmodelfactory.NavigationViewModelFactory
import com.example.busmap.core.domain.models.Coordinate
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationListFragment : Fragment(), NavigationRecyclerAdapter.OnNavigationChosenListener {
    //ViewModel
    private lateinit var viewModel : NavigationViewModel
    private val viewModelFactory = NavigationViewModelFactory()

    //Coordinate
    private lateinit var startLocation : Coordinate
    private lateinit var endLocation : Coordinate

    //Recycler view
    private val recycleAdapter = NavigationRecyclerAdapter(
        arrayListOf(),
        this
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialize the variable
        arguments?.let {
            startLocation = Coordinate(
                it.getFloat("startLat").toDouble(),
                it.getFloat("startLng").toDouble()
            )

            endLocation = Coordinate(
                it.getFloat("endLat").toDouble(),
                it.getFloat("endLng").toDouble()
            )
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButton()
        setUpRecycleView()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        //Create
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(NavigationViewModel::class.java)

        //Populate
        viewModel.getPath(startLocation, endLocation)

        //Observe
        viewModel.paths.observe(viewLifecycleOwner, Observer {
            recycleAdapter.update(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            navigation_progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpRecycleView() {
        navigation_list_view.apply {
            adapter = recycleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setBackButton() {
        navigation_back_button.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onChosen(busNavigation: BusNavigation) {
        //Navigation
        val action = NavigationListFragmentDirections.actionNavigationListToDirectionMap(
            busNavigation
        )
        findNavController().navigate(action)
    }
}