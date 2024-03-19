package com.avcialper.dictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.avcialper.dictionary.R
import com.avcialper.dictionary.databinding.ActivityMainBinding
import com.avcialper.dictionary.domain.model.WordInfo
import com.avcialper.dictionary.ui.adapter.WordInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // TODO fix ui, theme and color

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DictionaryViewModel by viewModels()
    private lateinit var adapter: WordInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.black)
        initUI()
    }

    private fun initUI() {
        viewModel.apply {
            data.observe(this@MainActivity) {
                it.run {
                    val wordData = if (binding.search.text.isEmpty()) {
                        emptyList()
                    } else {
                        it
                    }
                    adapter = WordInfoAdapter(wordData)
                    binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rv.adapter = adapter
                }
            }

            binding.search.addTextChangedListener {
                val query = it.toString()
                onSearch(query)

            }

            errorMessage.observe(this@MainActivity) { message ->
                if (binding.search.text.isNotEmpty() && !message.isNullOrBlank())
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }

            loading.observe(this@MainActivity) {
                it.run {
                    binding.apply {
                        if (it)
                            progressBar.visibility = View.VISIBLE
                        else
                            progressBar.visibility = View.GONE
                    }
                }
            }

        }
    }
}