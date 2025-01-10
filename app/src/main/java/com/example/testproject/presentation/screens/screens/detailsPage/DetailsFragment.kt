package com.example.testproject.presentation.screens.screens.detailsPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.testproject.databinding.FragmentDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attach lifecycle observer to YouTubePlayerView
        lifecycle.addObserver(binding.youtubeVideo)

        // Add a YouTubePlayerListener
        binding.youtubeVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                loadVideo(youTubePlayer)
            }
        })
    }

    private fun loadVideo(youTubePlayer: YouTubePlayer) {
        val videoId = "Fnsb3VuBms8" // Replace with your YouTube video ID
        youTubePlayer.loadVideo(videoId, 0f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(binding.youtubeVideo) // Detach lifecycle observer
    }
}