package com.example.busmap.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busmap.R
import com.example.busmap.core.domain.models.BusRoute

interface OnViewButtonClickedListener { fun onClick(busRoute: BusRoute) }

class RouteRecyclerViewAdapter(private val routes : MutableList<BusRoute>,
                               private val listener: OnViewButtonClickedListener)
    : RecyclerView.Adapter<RouteRecyclerViewAdapter.RouteViewHolder>() {
    class RouteViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        //Inflate a view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.route_recycle_item, parent, false)

        //Return the view holder
        return RouteViewHolder(view)
    }

    override fun getItemCount() = routes.size

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        //View inflated -> Set the item values
        val route = routes[position]

        //Set the view values
        val nameText = holder.itemView.findViewById<TextView>(R.id.route_name_text)
        val timeText = holder.itemView.findViewById<TextView>(R.id.route_time_text)
        val viewButton = holder.itemView.findViewById<Button>(R.id.route_view_button)

        //Set text
        nameText.text = route.name
        timeText.text = if (route.schedules.isNotEmpty()) {
            route.schedules[0].timeToDestination.toString() + " seconds"
        } else {
            "No time frame"
        }

        //Set button
        viewButton.setOnClickListener {
            listener.onClick(route)
        }
    }

    fun update(routes : List<BusRoute>) {
        this.routes.let {
            it.clear()
            it.addAll(routes)
        }
    }
}