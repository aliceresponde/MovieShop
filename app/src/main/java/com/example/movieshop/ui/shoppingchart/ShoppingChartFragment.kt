package com.example.movieshop.ui.shoppingchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieshop.databinding.FragmentShoppingChartBinding
import com.example.movieshop.ui.common.BaseFragment
import com.example.movieshop.ui.model.MovieItem
import com.example.movieshop.ui.movielist.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingChartFragment : BaseFragment<FragmentShoppingChartBinding>() {
    private val viewModel: ShoppingChartViewModel by viewModels()
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(
            addItemToCart = ::addItemToCart,
            removeItemFromCart = ::removeItemFromCard,
            showItem = ::showItemDetail,
            isCart = true
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShoppingChartBinding.inflate(inflater)
        initViews()
        setObservers()
        return binding.root
    }

    private fun initViews() {
        binding.viewModel = viewModel
        binding.shoppingChartList.adapter = adapter
        binding.loading.visibility = View.GONE
        binding.button.setOnClickListener { viewModel.removeAllItemInCart() }
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setObservers() {
        viewModel.moviesInCart.observe(viewLifecycleOwner, { adapter.update(it) })
    }

    private fun addItemToCart(item: MovieItem) {
        viewModel.addItemToCart(item)
    }

    private fun removeItemFromCard(item: MovieItem) {
        viewModel.removeItemFromCard(item)
    }

    private fun showItemDetail(item: MovieItem) {
        val action =
            ShoppingChartFragmentDirections.actionShoppingChartFragmentToMovieDetailActivity(item)
        findNavController().navigate(action)
    }
}