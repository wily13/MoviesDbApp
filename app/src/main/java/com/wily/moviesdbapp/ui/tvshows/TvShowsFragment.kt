package com.wily.moviesdbapp.ui.tvshows

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.wily.moviesdbapp.R
import com.wily.moviesdbapp.databinding.FragmentTvShowsBinding
import com.wily.moviesdbapp.ui.search.SearchActivity
import com.wily.moviesdbapp.utils.SortUtils
import com.wily.moviesdbapp.valueobject.Status
import com.wily.moviesdbapp.viewmodel.TvShowViewModel
import com.wily.moviesdbapp.viewmodel.ViewModelFactory

class TvShowsFragment : Fragment() {

    companion object {
        const val TAG = "TvShowsFragment"
    }

    private var _fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val binding get() = _fragmentTvShowsBinding

    private lateinit var viewModel: TvShowViewModel
    private lateinit var tvShowsAdapter: TvShowsPageListAdapter
    private lateinit var progressDialog: SweetAlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show menu search
        setHasOptionsMenu(true)

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


    private fun getDataTvShows(){
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory).get(TvShowViewModel::class.java)
        tvShowsAdapter = TvShowsPageListAdapter()

        viewModel.getAllTvShowsSortBy(SortUtils.POPULAR).observe(viewLifecycleOwner, Observer { tvShows ->
            Log.d(TAG, "Cek: ${tvShows.status} - ${hashCode()}")

            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> {
                        progressDialog.show()
                    }
                    Status.SUCCESS -> {
                        progressDialog.dismissWithAnimation()
                        tvShowsAdapter.submitList(tvShows.data)
                        tvShowsAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        progressDialog.dismissWithAnimation()
                        showErrorDialog()
                    }
                }
            }
        })

        with(binding?.rvTvShow) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowsAdapter
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        val menuSearch = menu.findItem(R.id.action_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                val intent = Intent(requireContext(), SearchActivity::class.java)
                intent.putExtra(SearchActivity.EXTRA_QUERY, query)
                intent.putExtra(SearchActivity.EXTRA_STATUS, "tvshows")
                startActivity(intent)

                //tutup keyboard ketika tombol diklik
                val methodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.hideSoftInputFromWindow(searchView.windowToken, 0)

                // clear focus text0
                searchView.clearFocus()
                menuSearch.collapseActionView()

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.getItemId()) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_title_asc -> sort = SortUtils.NAMEASC
            R.id.action_title_dsc -> sort = SortUtils.NAMEDESC
            R.id.action_popularity -> sort = SortUtils.POPULAR
        }
        viewModel.getAllTvShowsSortBy(sort).observe(viewLifecycleOwner, Observer { tvShows ->

            Log.d(TAG,"tes " + tvShows.status + hashCode())
            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = View.GONE
                        tvShowsAdapter.submitList(tvShows.data)
                        tvShowsAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(context, getString(R.string.msg_error_text), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        item.setChecked(true)
        return super.onOptionsItemSelected(item)
    }

}