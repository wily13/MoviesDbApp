package com.wily.moviesdbapp.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wily.moviesdbapp.BuildConfig.POSTER_URL
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.databinding.ItemsMoviesBinding
import com.wily.moviesdbapp.ui.detail.DetailMoviesActivity
import com.wily.moviesdbapp.utils.FormatedMethod
import java.lang.StringBuilder

class MoviesPageListAdapter : PagedListAdapter<MoviesEntity, MoviesPageListAdapter.MoviesViewHolder>(DIFF_CALLBACK) {

    //add pagination adapter for paging
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesEntity>() {
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem.moviesId == newItem.moviesId
            }

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MoviesEntity? = getItem(swipedPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsMoviesBinding =
            ItemsMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position) as MoviesEntity)
    }

    inner class MoviesViewHolder(private val binding: ItemsMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesEntity: MoviesEntity) {
            with(binding) {
                moviesEntity.apply {
                    tvItemTitle.text = title
                    if (releaseDate != null) {
                        tvItemDate.text = if (releaseDate?.isEmpty()!!) "-" else releaseDate?.let { FormatedMethod.getDateFormat(it) }
                    }
                    tvItemPopularity.text = voteAverage?.let { FormatedMethod.getPopular(it) }

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DetailMoviesActivity::class.java)
                        intent.putExtra(DetailMoviesActivity.EXTRA_MOVIES, moviesId)
                        intent.putExtra(DetailMoviesActivity.EXTRA_STATUS, "movies")
                        itemView.context.startActivity(intent)
                    }

                    val imagePath = StringBuilder("${POSTER_URL}${posterPath}").toString()
                    Glide.with(itemView.context)
                        .load(imagePath)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                        )
                        .into(imgPoster)
                }
            }
        }
    }

}