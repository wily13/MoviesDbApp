package com.wily.moviesdbapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wily.moviesdbapp.databinding.FragmentFavouritesBinding

class FavoritesFragment : Fragment() {

    private var _fragmentFavouritesBinding: FragmentFavouritesBinding? = null
    private val binding get() = _fragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavouritesBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val sectionsPagerAdapter = TabSectionsPagerAdapter(requireContext(), childFragmentManager)
            binding?.apply {
                viewPagerFavourites.adapter = sectionsPagerAdapter
                tabsFavourite.setupWithViewPager(viewPagerFavourites)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFavouritesBinding = null
    }
}