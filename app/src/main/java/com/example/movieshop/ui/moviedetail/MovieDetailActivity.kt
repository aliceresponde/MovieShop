package com.example.movieshop.ui.moviedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.movieshop.databinding.ActivityMovieDetailBinding
import com.example.movieshop.ui.common.gone
import com.example.movieshop.ui.common.loadFromUrl
import com.example.movieshop.ui.common.visible
import com.example.movieshop.ui.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val args: MovieDetailActivityArgs by navArgs()
    private val movieId: Int by lazy { args.id }
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.addMovie.setOnClickListener { viewModel.addItemToCart(movieId) }
        binding.removeMovie.setOnClickListener { viewModel.removeToCart(movieId) }
    }

    private fun setupObservers() {
        viewModel.getMovieDetailFlow(movieId).observe(this, { movieItem ->
            movieItem?.let {
                showMovieDetail(it)
            }
        })

        viewModel.loadingVisibility.observe(this,{
            binding.loading.visibility = it
        })

        viewModel.itemNotExistVisibility.observe(this, {
            binding.noData.visibility =it
        })
    }

    private fun showMovieDetail(item: MovieItem) {
        with(binding) {
            cardHolder.visible()
            noData.gone()
            loading.gone()
            movieDate.text = item.date
            movieOverview.text = item.overview
            movieImage.loadFromUrl(item.imageUrl)
            movieTitle.text = item.name
            removeMovie.isEnabled = item.quantity > 0
        }
    }
}