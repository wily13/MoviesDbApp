package com.wily.moviesdbapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.wily.moviesdbapp.BuildConfig.POSTER_URL
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.data.source.local.entity.GenresEntity
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.databinding.ActivityDetailMoviesBinding
import com.wily.moviesdbapp.databinding.ContentDetailMoviesBinding
import com.wily.moviesdbapp.utils.FormatedMethod
import com.wily.moviesdbapp.valueobject.Status
import com.wily.moviesdbapp.viewmodel.DetailMoviesViewModel
import com.wily.moviesdbapp.viewmodel.ViewModelFactory

class DetailMoviesActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailMoviesActivity"
        const val EXTRA_MOVIES = "extra_movies"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_TITLE_NAV = "movies"
    }

    private lateinit var contentDetailMoviesBinding: ContentDetailMoviesBinding
    private lateinit var activityDetailMoviesBinding: ActivityDetailMoviesBinding
    private lateinit var viewModel: DetailMoviesViewModel

    private lateinit var genresAdapterPage: GenresAdapterPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailMoviesBinding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        contentDetailMoviesBinding = activityDetailMoviesBinding.moviesContent
        setContentView(activityDetailMoviesBinding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(
            DetailMoviesViewModel::class.java
        )

        //List adapter genre
        genresAdapterPage = GenresAdapterPage()

        //get inten extras
        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(EXTRA_MOVIES)
            val movieStatus = extras.getString(EXTRA_STATUS)

            Log.d(TAG, "Movies id is: $movieId")

            viewModel.setSelectedMovies(movieId)

            if (movieStatus == EXTRA_TITLE_NAV) {
                showDetailMovies()
            } else {
                showDetailTvShows()
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // to set progress bar visible and content hide
    private fun progressAndContentNotVisible() {
        activityDetailMoviesBinding.progressBar.visibility = View.VISIBLE
        contentDetailMoviesBinding.clContentMovies.visibility = View.GONE
        activityDetailMoviesBinding.fab.visibility = View.GONE
    }

    // to set progress bar hide and content visible
    private fun progressAndContentVisible() {
        activityDetailMoviesBinding.progressBar.visibility = View.GONE
        contentDetailMoviesBinding.clContentMovies.visibility = View.VISIBLE
        activityDetailMoviesBinding.fab.visibility = View.VISIBLE
    }

    private fun showDetailMovies() {
        supportActionBar?.title = getString(R.string.title_detail_movies)
        // load detai movies
        viewModel.moviesDetail.observe(
            this@DetailMoviesActivity,
            Observer { moviesWithGenres ->
                Log.d(TAG, "cek mMovies: " + moviesWithGenres.data?.mMovies)
                Log.d(TAG, "cek mGenres: " + moviesWithGenres.data?.mGenres)
                Log.d(TAG, "cek status: " + moviesWithGenres.status + hashCode())

                if (moviesWithGenres != null) {
                    when (moviesWithGenres.status) {
                        Status.LOADING -> progressAndContentNotVisible()
                        Status.SUCCESS -> {
                            if (moviesWithGenres.data != null) {
                                progressAndContentVisible()

                                // set detail
                                populateMoviesDetail(moviesWithGenres.data.mMovies)

                                // set genre movies
                                getGenresMovies(moviesWithGenres.data.mGenres)
                            }
                        }
                        Status.ERROR -> {
                            progressAndContentVisible()
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.msg_error_status),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
    }

    private fun showDetailTvShows() {
        supportActionBar?.title = getString(R.string.title_detail_tvshows)
        // load detai tv shows
        viewModel.tvShowsDetail.observe(
            this@DetailMoviesActivity,
            Observer { moviesWithGenres ->
                Log.d(TAG, "cek mMovies: " + moviesWithGenres.data?.mMovies)
                Log.d(TAG, "cek mGenres: " + moviesWithGenres.data?.mGenres)
                Log.d(TAG, "cek status: " + moviesWithGenres.status + hashCode())

                if (moviesWithGenres != null) {
                    when (moviesWithGenres.status) {
                        Status.LOADING -> progressAndContentNotVisible()
                        Status.SUCCESS -> {
                            if (moviesWithGenres.data != null) {
                                progressAndContentVisible()

                                // set detail
                                populateTvShowsDetail(moviesWithGenres.data.mMovies)

                                // set genre movies
                                getGenresMovies(moviesWithGenres.data.mGenres)
                            }
                        }
                        Status.ERROR -> {
                            progressAndContentVisible()

                            Toast.makeText(
                                applicationContext,
                                getString(R.string.msg_error_status),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
    }

    // get genres movies
    private fun getGenresMovies(mGenres: List<GenresEntity>) {
        if (mGenres.isNotEmpty()) {
            genresAdapterPage.setGenres(mGenres)
            genresAdapterPage.notifyDataSetChanged()
        }

        with(contentDetailMoviesBinding.rvGenre) {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
            adapter = genresAdapterPage
        }
    }

    // get director movies
    private fun getDirectorMovies() {
        with(contentDetailMoviesBinding) {
            viewModel.moviesDirector.observe(
                this@DetailMoviesActivity,
                Observer { directorMovies ->
                    Log.d(TAG, "cek director " + directorMovies.status + hashCode())
                    Log.d(TAG, "cek data director: " + directorMovies.data?.mDirector)

                    if (directorMovies != null) {
                        when (directorMovies.status) {
                            Status.LOADING -> tvDirector.text = getString(R.string.loading_text)
                            Status.SUCCESS -> {
                                if (directorMovies.data?.mDirector.isNullOrEmpty()) {
                                    tvDirector.text = "-"
                                } else {
                                    tvDirector.text =
                                        directorMovies.data?.mDirector?.get(0)?.name
                                }
                            }
                            Status.ERROR -> {
                                tvDirector.text = "-"
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.msg_error_status),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
        }

    }

    // get created by tv shows
    private fun getCreatedByTvShows() {
        with(contentDetailMoviesBinding) {
            viewModel.tvShowsCreatedBy.observe(
                this@DetailMoviesActivity,
                Observer { tvShowsCreatedBy ->
                    Log.d(TAG, "cek created " + tvShowsCreatedBy.status + hashCode())
                    Log.d(TAG, "cek data created: " + tvShowsCreatedBy.data?.mCreatedBy)

                    if (tvShowsCreatedBy != null) {
                        when (tvShowsCreatedBy.status) {
                            Status.LOADING -> {
                                tvCreator.text = getString(R.string.loading_text)
                            }
                            Status.SUCCESS -> {
                                if (tvShowsCreatedBy.data?.mCreatedBy.isNullOrEmpty()) {
                                    tvCreator.text = "-"
                                } else {
                                    tvCreator.text =
                                        tvShowsCreatedBy.data?.mCreatedBy?.get(0)?.name
                                }
                            }
                            Status.ERROR -> {
                                tvCreator.text = "-"
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.msg_error_status),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }


                })
        }
    }

    private fun populateMoviesDetail(moviesEntity: MoviesEntity) {
        moviesEntity.apply {
            with(contentDetailMoviesBinding) {
                val yearRelease =
                    if (releaseDate?.isEmpty()!!) "-" else releaseDate?.let {
                        FormatedMethod.getYearRelease(
                            it
                        )
                    }

                tvTitle.text = StringBuilder("$title ($yearRelease)")
                tvDescription.text = overview
                tvStatus.text = status
                tvPopularity.text = popularity?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteAverage.text = voteAverage?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteCount.text = voteCount.toString()
                tvBudget.text = budget?.let { FormatedMethod.roundOffInt(it) }
                tvRevenue.text = revenue?.let { FormatedMethod.roundOffInt(it) }
                tvLanguage.text = language
                tvDaterelease.text = if (releaseDate?.isEmpty()!!) "-" else releaseDate?.let {
                    FormatedMethod.getDateFormat(it)
                }

                if (tagline != null) {
                    if (tagline?.isNotEmpty()!!) {
                        tvTagline.text = tagline
                    } else {
                        tvTagline.isVisible = false
                    }
                }

                //set director
                getDirectorMovies()

                // set image poster
                val imagePath = StringBuilder("${POSTER_URL}${posterPath}").toString()
                Glide.with(this@DetailMoviesActivity)
                    .load(imagePath)
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                // set visibility textview
                tvFirstairDate.isVisible = false
                tvFirstairdateTitle.isVisible = false
                tvLastairDate.isVisible = false
                tvLastairdateTitle.isVisible = false
                tvCreator.isVisible = false
                tvCreatorTitle.isVisible = false
                tvType.isVisible = false
                tvTypeTitle.isVisible = false

                // fab button share
                activityDetailMoviesBinding.fab.setOnClickListener {
                    title?.let { shareApp(it) }
                }

                // btn favourites
                favouriteBtn(moviesEntity)
            }
        }
    }

    private fun populateTvShowsDetail(moviesEntity: MoviesEntity) {
        moviesEntity.apply {
            with(contentDetailMoviesBinding) {
                val yearRelease =
                    if (firstAirDate?.isEmpty()!!) "-" else firstAirDate?.let {
                        FormatedMethod.getYearRelease(
                            it
                        )
                    }
                tvTitle.text = StringBuilder("$name ($yearRelease)")
                tvFirstairDate.text =
                    if (firstAirDate?.isEmpty()!!) "-" else firstAirDate?.let {
                        FormatedMethod.getDateFormat(
                            it
                        )
                    }
                tvLastairDate.text =
                    if (lastAirDate?.isEmpty()!!) "-" else lastAirDate?.let {
                        FormatedMethod.getDateFormat(
                            it
                        )
                    }
                tvDescription.text = overview
                tvType.text = type
                tvStatus.text = status
                tvPopularity.text = popularity?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteAverage.text = voteAverage?.let { FormatedMethod.roundOffDecimal(it) }
                tvVoteCount.text = voteCount.toString()
                tvLanguage.text = language

                if (tagline?.isNotEmpty()!!) {
                    tvTagline.text = tagline
                } else {
                    tvTagline.isVisible = false
                }

                //set created by
                getCreatedByTvShows()


                // set image poster
                val imagePath = StringBuilder("${POSTER_URL}${posterPath}").toString()
                Glide.with(this@DetailMoviesActivity)
                    .load(imagePath)
                    .transform(RoundedCorners(20))
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)

                // set visibility textview
                tvDaterelease.isVisible = false
                tvDatereleaseTitle.isVisible = false
                tvDirector.isVisible = false
                tvDirectorTitle.isVisible = false
                tvBudget.isVisible = false
                tvBudgetTitle.isVisible = false
                tvRevenue.isVisible = false
                tvRevenueTitle.isVisible = false

                // fab button share
                activityDetailMoviesBinding.fab.setOnClickListener {
                    name?.let { shareApp(it) }
                }

                // btn favourites
                favouriteBtn(moviesEntity)
            }
        }
    }

    private fun shareApp(title: String) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this@DetailMoviesActivity)
            .setType(mimeType)
            .setChooserTitle("Bagikan Film: \"${title}\", sekarang.")
            .startChooser()
    }

    private fun favouriteBtn(moviesEntity: MoviesEntity) {
        var statusFavorite = moviesEntity.isFavorite
        setStatusFavorites(statusFavorite)
        contentDetailMoviesBinding.imgBtnfavourite.setOnClickListener {
            statusFavorite = !statusFavorite
            viewModel.setFavoriteMovie(moviesEntity, statusFavorite)

            if (statusFavorite) {
                showSnackbarMessage(getString(R.string.text_add_favorite))
            } else {
                showSnackbarMessage(getString(R.string.text_delete_favorite))
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setStatusFavorites(statusFavorites: Boolean) {
        if (statusFavorites) {
            contentDetailMoviesBinding.imgBtnfavourite.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_nav_favourites,
                    theme
                )
            )

        } else {
            contentDetailMoviesBinding.imgBtnfavourite.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_favourites_full,
                    theme
                )
            )
        }
    }

    // show snack bar message
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(contentDetailMoviesBinding.clContentMovies, message, Snackbar.LENGTH_SHORT)
            .show()
    }

}