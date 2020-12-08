package com.vd.movies.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.vd.movies.data.model.Movie
import com.vd.movies.databinding.ItemMovieRecentBinding
import com.vd.movies.databinding.ItemMovieResultBinding

class MoviesAdapter(
    val context: Context,
    var list: List<Movie>,
    val onItemClicked: (Movie) -> Unit,
    val layoutType: LayoutType = LayoutType.RESULT_ITEM
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = when (layoutType) {
            LayoutType.RESULT_ITEM -> ItemMovieResultBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            LayoutType.RECENT_ITEM -> ItemMovieRecentBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        }
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onItemClicked(list[position]) }
    }


    class MovieViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            if (binding is ItemMovieResultBinding) {
                binding.movie = movie
            } else if (binding is ItemMovieRecentBinding) {
                binding.movie = movie
            }
        }
    }

    enum class LayoutType {
        RESULT_ITEM,
        RECENT_ITEM
    }
}
