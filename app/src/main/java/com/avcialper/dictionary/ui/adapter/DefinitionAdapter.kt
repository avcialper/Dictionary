package com.avcialper.dictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.dictionary.databinding.DefinitionCardBinding
import com.avcialper.dictionary.domain.model.Definition

class DefinitionAdapter(private val definitionList: List<Definition?>) :
    RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    class ViewHolder(val binding: DefinitionCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DefinitionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = definitionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val definition = definitionList[position]
        holder.binding.apply {
            this.definition.text = definition!!.definition

            if (definition.example.isNullOrBlank()) {
                this.example.visibility = View.GONE
            } else {
                this.example.text = definition.example
            }
        }
    }
}