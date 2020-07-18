package com.example.busmap.view.saved

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busmap.R
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.view.adapters.FavouriteRecycleView
import com.example.busmap.view.viewmodel.FavouriteViewModel
import com.example.busmap.view.viewmodelfactory.FavouriteViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_map.*

class SavedFragment : Fragment(), FavouriteRecycleView.OnClickListener,
    FavouriteRecycleView.OnDeleteClickListener {
    //Recycler view adapter
    private val favouriteRecycleView = FavouriteRecycleView(
        arrayListOf(),
        this,
        this
    )

    //View model and factory
    private lateinit var viewModel : FavouriteViewModel
    private lateinit var viewModelFactory: FavouriteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        setUpViewModel()
    }

    private fun setUpList() {
        saved_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteRecycleView
        }
    }

    private fun setUpViewModel() {
        viewModelFactory = FavouriteViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(FavouriteViewModel::class.java)

        //Observe
        viewModel.stops.observe(viewLifecycleOwner,
            Observer<List<BusStop>> { stops ->
                favouriteRecycleView.update(stops)
            })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                saved_loading_icon.visibility = View.VISIBLE
            } else {
                saved_loading_icon.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it.isError) {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        //Get data
        viewModel.getFavourites()
    }

    override fun onClick(busStop: BusStop) {
        //Navigate to details
        val action = SavedFragmentDirections.actionMenuListToBusStopDetails(busStop)
        findNavController().navigate(action)
    }

    override fun onDelete(busStop: BusStop) {
        //Delete bus stop
        viewModel.removeFavourite(busStop)
    }
}