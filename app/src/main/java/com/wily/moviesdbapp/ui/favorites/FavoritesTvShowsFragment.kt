package com.wily.moviesdbapp.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.databinding.FragmentTvShowsBinding
import com.wily.moviesdbapp.ui.tvshows.TvShowsPageListAdapter
import com.wily.moviesdbapp.valueobject.Status
import com.wily.moviesdbapp.viewmodel.FavouritesViewModel
import com.wily.moviesdbapp.viewmodel.ViewModelFactory

class FavoritesTvShowsFragment : Fragment() {

    companion object {
        const val TAG = "FavouritesFragment"
    }

    private var _fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val binding get() = _fragmentTvShowsBinding

    private lateinit var progressDialog: SweetAlertDialog
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var favouritesAdapter: TvShowsPageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvTvShow)

        // set loading dialog
        setProgressDialog()

        if (activity != null) {
            getDataTvShows()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentTvShowsBinding = null
    }

    private fun getDataTvShows() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory).get(FavouritesViewModel::class.java)

        favouritesAdapter = TvShowsPageListAdapter()

        viewModel.getFavoritesTvShows().observe(viewLifecycleOwner, Observer { tvShows ->
            Log.d(TAG, "FavoritesTvShowsFragment --> Cek: ${tvShows.status} - ${hashCode()}")

            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> progressDialog.show()
                    Status.SUCCESS -> {
                        progressDialog.dismissWithAnimation()
                        showTvShowsList(tvShows.data as PagedList<MoviesEntity>, favouritesAdapter)
                    }
                    Status.ERROR -> {
                        progressDialog.dismissWithAnimation()
                        showErrorDialog()
                    }
                }
            }
        })

        with(binding?.rvTvShow) {
            this?.setHasFixedSize(true)
            this?.layoutManager = LinearLayoutManager(activity)
            this?.adapter = favouritesAdapter
        }
    }

    private fun showTvShowsList(
        listMovies: PagedList<MoviesEntity>,
        favouritesAdapter: TvShowsPageListAdapter
    ) {
        if (listMovies.size > 0) {
            binding?.rvTvShow?.visibility = View.VISIBLE
            binding?.emptyView?.layoutEmpty?.visibility = View.GONE

            favouritesAdapter.submitList(listMovies)
            favouritesAdapter.notifyDataSetChanged()
        } else {
            binding?.rvTvShow?.visibility = View.GONE
            binding?.emptyView?.layoutEmpty?.visibility = View.VISIBLE
            binding?.emptyView?.imgEmpty?.setImageResource(R.drawable.ic_empty_data)
        }
    }

    private fun setProgressDialog() {
        progressDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.loading_alert))
    }

    private fun showErrorDialog() {
        val errorDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.msg_error_title))
            .setContentText(getString(R.string.msg_error_text))
        errorDialog.show()
    }


    // function for item swipe
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition

                val moviesEntity = favouritesAdapter.getSwipedData(swipedPosition)

                moviesEntity?.let { viewModel.setFavouritesMovies(it) }

                val snackbar =
                    Snackbar.make(
                        view as View,
                        getString(R.string.message_undo),
                        Snackbar.LENGTH_LONG
                    )

                snackbar.setAction(getString(R.string.message_ok)) {
                    moviesEntity?.let { viewModel.setFavouritesMovies(it) }
                }

                snackbar.show()
            }
        }
    })

}