package com.wily.moviesdbapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import cn.pedant.SweetAlert.SweetAlertDialog
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.databinding.ActivitySearchBinding
import com.wily.moviesdbapp.ui.movies.MoviesPageListAdapter
import com.wily.moviesdbapp.ui.tvshows.TvShowsPageListAdapter
import com.wily.moviesdbapp.valueobject.Status
import com.wily.moviesdbapp.viewmodel.SearchViewModel
import com.wily.moviesdbapp.viewmodel.ViewModelFactory

class SearchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_TITLE_NAV = "movies"
    }

    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var progressDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setProgressDialog()

        val factory = ViewModelFactory.getInstance(this)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {

            val query = extras.getString(EXTRA_QUERY)
            val navigationStatus = extras.getString(EXTRA_STATUS)


            if (query != null) {
                if (navigationStatus == EXTRA_TITLE_NAV) {
                    supportActionBar?.title = getString(R.string.title_search_movies)
                    getDataSearchMovies(query)

                } else {
                    supportActionBar?.title = getString(R.string.title_search_tvshows)
                    getDataSearchTvShows(query)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getDataSearchMovies(query: String) {
        val searchAdapter = MoviesPageListAdapter()
        searchViewModel.getSearchMovies(query).observe(this, Observer { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        progressDialog.dismissWithAnimation()
                        showMoviesList(movies.data as PagedList<MoviesEntity>, searchAdapter)
                    }
                    Status.ERROR -> {
                        progressDialog.dismissWithAnimation()
                        showErrorDialog()
                    }
                }
            }
        })

        with(activitySearchBinding.rvSearch) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    private fun getDataSearchTvShows(query: String) {
        val searchAdapter = TvShowsPageListAdapter()
        searchViewModel.getSearchTvShows(query).observe(this, Observer { tvshows ->
            if (tvshows != null) {
                when (tvshows.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        progressDialog.dismissWithAnimation()
                        showTvShowsList(tvshows.data as PagedList<MoviesEntity>, searchAdapter)
                    }
                    Status.ERROR -> {
                        progressDialog.dismissWithAnimation()
                        showErrorDialog()
                    }
                }
            }
        })

        with(activitySearchBinding.rvSearch) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }


    private fun showMoviesList(
        listMovies: PagedList<MoviesEntity>,
        searchAdapter: MoviesPageListAdapter
    ) {
        if (listMovies.size > 0) {
            activitySearchBinding.rvSearch.visibility = View.VISIBLE
            activitySearchBinding.emptyView.layoutEmpty.visibility = View.GONE
            searchAdapter.submitList(listMovies)
            searchAdapter.notifyDataSetChanged()
        } else {
            activitySearchBinding.rvSearch.visibility = View.GONE
            activitySearchBinding.emptyView.layoutEmpty.visibility = View.VISIBLE
        }
    }

    private fun showTvShowsList(
        listTvShows: PagedList<MoviesEntity>,
        searchAdapter: TvShowsPageListAdapter
    ) {
        if (listTvShows.size > 0) {
            activitySearchBinding.rvSearch.visibility = View.VISIBLE
            activitySearchBinding.emptyView.layoutEmpty.visibility = View.GONE
            searchAdapter.submitList(listTvShows)
        } else {
            activitySearchBinding.rvSearch.visibility = View.GONE
            activitySearchBinding.emptyView.layoutEmpty.visibility = View.VISIBLE
        }
    }

    private fun setProgressDialog() {
        progressDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.loading_alert))
    }

    private fun showErrorDialog() {
        val errorDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.msg_error_title))
            .setContentText(getString(R.string.msg_error_text))
        errorDialog.show()
    }
}