package com.example.busmap.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busmap.R
import com.example.busmap.view.util.BusStringUtil
import com.example.busmap.core.domain.models.BusNavigation

class NavigationRecyclerAdapter(
    private val paths : MutableList<BusNavigation>,
    private val onNavigationChosenListener: OnNavigationChosenListener) :
    RecyclerView.Adapter<NavigationRecyclerAdapter.NavigationViewHolder>() {
    class NavigationViewHolder(view : View) : RecyclerView.ViewHolder(view) {}

    interface OnNavigationChosenListener { fun onChosen(busNavigation: BusNavigation) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        //Create view and return
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.navigation_recycle_item, parent, false)

        //Return view holder
        return NavigationViewHolder(view)
    }

    override fun getItemCount() = paths.size

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        //Bind
        val item = paths[position]

        //Set value
        val title = holder.itemView.findViewById<TextView>(R.id.navigation_item_title)
        val fare = holder.itemView.findViewById<TextView>(R.id.navigation_item_fare)
        val desc = holder.itemView.findViewById<TextView>(R.id.navigation_item_desc)

        title.text = item.title
        fare.text = item.fare.toString() + " VND"
        desc.text = BusStringUtil.formatPathDescription(item.description)

        //Set click event listener
        holder.itemView.setOnClickListener {
            onNavigationChosenListener.onChosen(item)
        }
    }

    fun update(paths : List<BusNavigation>) {
        this.paths.clear()
        this.paths.addAll(paths)
        notifyDataSetChanged()
    }
}