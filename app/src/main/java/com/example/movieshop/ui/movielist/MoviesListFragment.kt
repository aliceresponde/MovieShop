package com.example.movieshop.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieshop.databinding.FragmentMoviesListBinding
import com.example.movieshop.ui.common.BaseFragment
import com.example.movieshop.ui.common.gone
import com.example.movieshop.ui.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {

    private val viewModel: MoviesListViewModel by viewModels()
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(
            addItemToCart = ::addItemToCart,
            removeItemFromCart = ::removeItemFromCard,
            showItem = ::showItemDetail,
            isCart = false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews() {
        binding.viewModel = viewModel
        binding.moviesList.adapter = adapter
        binding.loading.visibility = GONE
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            binding.loading.gone()
            adapter.update(it) })
        viewModel.loadingVisibility.observe(viewLifecycleOwner, {
            binding.loading.visibility = it
        })
    }

    private fun addItemToCart(id: Int) {
        viewModel.addItemToCart(id)
    }

    private fun removeItemFromCard(id: Int) {
        viewModel.removeItemFromCard(id)
    }

    private fun showItemDetail(item: MovieItem) {
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailActivity(item.id)
        findNavController().navigate(action)
    }
}