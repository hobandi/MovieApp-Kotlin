package pl.kfert.movie.ui.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.kfert.movie.data.model.Movie
import pl.kfert.movie.databinding.MainListItemBinding

class MovieAdapter(private var callbackFavoriteListener: (Movie) -> Unit, private var callbackItemClick:
    (Movie) -> Unit) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        movie?.let {
            holder.apply {
                bind(createOnClickListener(movie), movie)
                itemView.tag = movie
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MainListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), callbackFavoriteListener
        )
    }

    private fun createOnClickListener(movie: Movie): View.OnClickListener {
        return View.OnClickListener {
            callbackItemClick.invoke(movie)
        }
    }

    class ViewHolder(private val binding: MainListItemBinding, private val callbackFavoriteListener: (Movie) -> Unit?)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, movie: Movie) {
            binding?.apply {

                this.favorite.apply {
                    setOnClickListener{
                        it.isSelected = !it.isSelected
                        movie.isFavorite = it.isSelected
                        callbackFavoriteListener.invoke(movie)
                    }

                    movie.isFavorite.let {
                        isSelected = it != null && it
                    }
                }
                binding.root.setOnClickListener(listener)

                movieItem = movie
                executePendingBindings()
            }
        }
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}