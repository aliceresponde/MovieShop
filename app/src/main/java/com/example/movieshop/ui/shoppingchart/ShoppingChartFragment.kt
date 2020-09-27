package com.example.movieshop.ui.shoppingchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieshop.databinding.FragmentShoppingChartBinding
import com.example.movieshop.ui.common.BaseFragment
import com.example.movieshop.ui.common.gone
import com.example.movieshop.ui.common.showIf
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
        binding = FragmentShoppingChartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()

    }

    private fun initViews() {
        binding.viewModel = viewModel
        binding.shoppingChartList.adapter = adapter
        binding.loading.visibility = View.GONE
        binding.button.setOnClickListener { viewModel.removeAllItemInCart() }
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setObservers() {
        viewModel.moviesInCart.observe(viewLifecycleOwner, { items ->
            adapter.update(items)
            binding.button.showIf { items.isNotEmpty()}
            binding.loading.gone()
        })
        viewModel.loadingVisibility.observe(viewLifecycleOwner, { binding.loading.visibility = it })
        viewModel.deleteItemsVisibility.observe(viewLifecycleOwner, {
            binding.button.visibility = it
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
            ShoppingChartFragmentDirections.actionShoppingChartFragmentToMovieDetailActivity(item.id)
        findNavController().navigate(action)
    }
}