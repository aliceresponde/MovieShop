package com.example.movieshop.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshop.R
import com.example.movieshop.databinding.ShoppingItemLayoutBindingImpl
import com.example.movieshop.ui.common.gone
import com.example.movieshop.ui.common.loadFromUrl
import com.example.movieshop.ui.common.visible
import com.example.movieshop.ui.model.MovieItem

class MoviesAdapter(
    private var data: MutableList<MovieItem> = mutableListOf(),
    private val addItemToCart: (MovieItem) -> Unit,
    private val removeItemFromCart: (MovieItem) -> Unit,
    private val showItem: (MovieItem) -> Unit,
    private val isCart: Boolean
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.shopping_item_layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, isCart, addItemToCart, removeItemFromCart, showItem)
    }

    override fun getItemCount(): Int = data.size

    fun update(newItemList: List<MovieItem>) {
        val oldList = data
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ShowItemDiffCallback(oldList, newItemList)
        )
        data.clear()
        data.addAll(newItemList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ShoppingItemLayoutBindingImpl.bind(itemView)

    fun bind(
        item: MovieItem,
        isCart: Boolean,
        addItemToCart: (MovieItem) -> Unit,
        removeItemFromCart: (MovieItem) -> Unit,
        showItem: (MovieItem) -> Unit
    ) {
        binding.movieImage.loadFromUrl(item.imageUrl)
        binding.movieTitle.text = item.name
        binding.movieQuantity.text =  itemView.context.getString(R.string.item_quantity, item.quantity)
        binding.removeMovie.isEnabled = item.quantity > 0

        if (isCart) binding.movieQuantity.visible()
        else binding.movieQuantity.gone()

        setListeners(showItem, item, addItemToCart, removeItemFromCart)
    }

    private fun setListeners(
        showItem: (MovieItem) -> Unit,
        item: MovieItem,
        addItemToCart: (MovieItem) -> Unit,
        removeItemFromCart: (MovieItem) -> Unit
    ) {
        with(binding) {
            root.setOnClickListener { showItem.invoke(item) }

            addMovie.setOnClickListener {
                addItemToCart.invoke(item)
            }
            removeMovie.setOnClickListener {
                removeItemFromCart.invoke(item)
            }
        }
    }

}

class ShowItemDiffCallback(
    private var oldItemList: List<MovieItem>,
    private var newItemList: List<MovieItem>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemList[oldItemPosition].id == newItemList[newItemPosition].id

    override fun getOldListSize(): Int = oldItemList.size

    override fun getNewListSize(): Int = newItemList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItemList[oldItemPosition] == newItemList[newItemPosition]
}
