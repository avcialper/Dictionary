package com.avcialper.dictionary.ui.adapter

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.dictionary.databinding.WordCardBinding
import com.avcialper.dictionary.domain.model.Meaning
import com.avcialper.dictionary.domain.model.WordInfo

class WordInfoAdapter(private val wordInfoList: List<WordInfo>) :
    RecyclerView.Adapter<WordInfoAdapter.ViewHolder>() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var context: Context

    class ViewHolder(val binding: WordCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setMeaningAdapter(meaning: List<Meaning>) {
            val adapter = MeaningAdapter(meaning)
            binding.rv.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rv.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        context = parent.context
        val binding = WordCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordInfo = wordInfoList[position]
        holder.binding.apply {
            word.text = wordInfo.word
            phonetic.text = wordInfo.phonetic
            audio.setOnClickListener {
                val isOnline = checkInternetConnection()
                if (isOnline)
                    playAudio(wordInfo.audio!!)
                else
                    Toast.makeText(
                        context,
                        "No internet connection!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
        wordInfo.meanings?.let { holder.setMeaningAdapter(it) }
    }

    override fun getItemCount() = wordInfoList.size

    private fun playAudio(url: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            Toast.makeText(context, "Audio not found", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}