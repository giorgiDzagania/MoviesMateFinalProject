package com.example.testproject.presentation.screens.screens.detailsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.testproject.databinding.FragmentDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel>()

    // Use a nullable binding to avoid memory leaks
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs() // Retrieve arguments passed via navigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using view binding
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attach YouTubePlayerView to lifecycle
        lifecycle.addObserver(binding.youtubeVideo)


        getMovieDetails()
        setCollectors()

    }

    private fun getMovieDetails() {
        viewModel.fetchMovieDetailsAndTrailer(args.movieId.toString())
    }

    private fun setCollectors() {
        // details
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetails.collect {
                binding.titleTv.text = it?.original_title
                binding.raitingTv.text = it?.vote_average.toString()
            }
        }

        // Trailer
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videoTrailer.collect { videos ->
                val trailer = videos?.results?.firstOrNull()?.key

                binding.youtubeVideo.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        if (trailer != null) {
                            youTubePlayer.loadVideo(trailer, 0f)
                        } // Load the trailer by its key
                    }
                })

            }
        }
    }


    // ------------------------------- Youtube ---------------------------------
    // Load the video into the YouTube player
    private fun loadVideo(youTubePlayer: YouTubePlayer) {
        val videoId = "Fnsb3VuBms8" // Use argument or fallback to default video ID
        youTubePlayer.loadVideo(videoId, 0f) // Start video at 0 seconds
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove YouTubePlayerView from lifecycle observer and clear binding
        lifecycle.removeObserver(binding.youtubeVideo)
        _binding = null
    }

}