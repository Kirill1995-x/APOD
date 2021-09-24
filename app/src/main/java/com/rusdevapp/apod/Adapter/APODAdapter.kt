package com.rusdevapp.apod.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.apod.Model.ModelNASA
import com.rusdevapp.apod.R

class APODAdapter(private val data:List<ModelNASA>): RecyclerView.Adapter<APODAdapter.ViewHolder>()
{

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var tvTitle:TextView?=null
        var tvDate:TextView?=null

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDate = itemView.findViewById(R.id.tvDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                             .inflate(R.layout.data, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle?.text = data[position].title
        holder.tvDate?.text = data[position].date
    }

    override fun getItemCount(): Int = data.size
}