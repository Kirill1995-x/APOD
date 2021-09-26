package com.rusdevapp.apod.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rusdevapp.apod.MainActivity
import com.rusdevapp.apod.Model.ModelNASA
import com.rusdevapp.apod.R

class APODAdapter(private val elementFromList: List<ModelNASA>, private val context: Context):
    RecyclerView.Adapter<APODAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View, context: Context, elementFromList: List<ModelNASA>):
        RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        private val context:Context = context
        private val elementFromList = elementFromList

        fun bind(element: ModelNASA)
        {
            var clElement:ConstraintLayout = itemView.findViewById(R.id.clElement)
            var tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
            var tvDate:TextView = itemView.findViewById(R.id.tvDate)
            tvTitle.text = ((absoluteAdapterPosition+1).toString()+". "+element.title)
            tvDate.text = element.getConvertDate()

            clElement.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            var position = absoluteAdapterPosition
            if(position!=RecyclerView.NO_POSITION)
            {
                when(v.id) {
                    R.id.clElement -> sendInformationAboutAPOD(elementFromList[position])
                }
            }
        }

        private fun sendInformationAboutAPOD(modelNASA: ModelNASA)
        {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("title", modelNASA.title)
            intent.putExtra("url", modelNASA.url)
            intent.putExtra("explanation", modelNASA.explanation)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                         .inflate(R.layout.data, parent, false),
                         context, elementFromList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(elementFromList[position])
    }

    override fun getItemCount(): Int = elementFromList.size
}