package com.ansgar.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_holder_model.view.*

class RvAdapter : RecyclerView.Adapter<RvAdapter.ModelHolder>() {

    var models = ArrayList<Model>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_model, parent, false)
        return ModelHolder(view)
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: ModelHolder, position: Int) {
        val model = models[position]
        holder.bindViews(model)
    }

    class ModelHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindViews(model: Model) = with(itemView) {
            avatar.setImageResource(model.avatar)
            name.text = model.name
            age.text = "${model.age}"
        }

    }
}