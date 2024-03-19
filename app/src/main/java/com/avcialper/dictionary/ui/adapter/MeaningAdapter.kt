package com.avcialper.dictionary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.dictionary.databinding.MeaningCardBinding
import com.avcialper.dictionary.domain.model.Definition
import com.avcialper.dictionary.domain.model.Meaning

class MeaningAdapter(private val meaningList: List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: MeaningCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setDefinitionAdapter(definition: List<Definition?>) {
            val adapter = DefinitionAdapter(definition)
            binding.rv.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rv.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = MeaningCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = meaningList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meaning = meaningList[position]
        holder.binding.apply {
            partOfSpeech.text = meaning.partOfSpeech
        }
        holder.setDefinitionAdapter(meaning.definitions!!)
    }
}