package com.rusdevapp.apod.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.apod.PhotoActivity
import com.rusdevapp.apod.Model.APODModel
import com.rusdevapp.apod.R
import com.rusdevapp.apod.ViewHolder.APODViewHolder

class APODAdapter(private val arrayList: ArrayList<APODModel>, private val context: Context):
    RecyclerView.Adapter<APODViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder
    {
        var view: View = LayoutInflater.from(parent.context)
                                       .inflate(R.layout.item_list, parent, false)
        var viewHolder:APODViewHolder = APODViewHolder(view)
        view.setOnClickListener(View.OnClickListener {
            var position:Int = viewHolder.absoluteAdapterPosition
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra("title", arrayList[position].title)
            intent.putExtra("url", arrayList[position].url)
            intent.putExtra("explanation", arrayList[position].explanation)
            context.startActivity(intent)
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: APODViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int = arrayList.size
}