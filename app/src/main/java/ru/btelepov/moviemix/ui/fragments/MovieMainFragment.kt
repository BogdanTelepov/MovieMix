package ru.btelepov.moviemix.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.btelepov.moviemix.adapters.MovieAdapter
import ru.btelepov.moviemix.adapters.ViewPagerAdapter
import ru.btelepov.moviemix.databinding.FragmentMovieMainBinding
import ru.btelepov.moviemix.utils.Functions.Companion.observeOnce
import ru.btelepov.moviemix.utils.Functions.Companion.showSnackBar
import ru.btelepov.moviemix.utils.NetworkResult
import ru.btelepov.moviemix.viewmodels.MovieMainViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieMainFragment : Fragment() {

    private var _binding: FragmentMovieMainBinding? = null
    private val binding get() = _binding!!

    private val movieMainViewModel: MovieMainViewModel by viewModels()
    private val popularMoviesAdapter by lazy { MovieAdapter() }
    private val topRatedMoviesAdapter by lazy { MovieAdapter() }

    private val viewPagerAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieMainBinding.inflate(inflater, container, false)


        return binding.root
    }


    private fun setupRv() {
        showShimmerEffect()
        binding.rvPopularMovies.adapter = popularMoviesAdapter
        binding.rvPopularMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvTopRatedMovies.adapter = topRatedMoviesAdapter
        binding.rvTopRatedMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchNowPlayingMovies()
        setupRv()
        readPopularMoviesDb()
        readTopRatedMoviesDb()
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = binding.wormDotsIndicator
        indicator.setViewPager2(binding.viewPager)
    }


    private fun readPopularMoviesDb() {
        lifecycleScope.launch {
            movieMainViewModel.readPopularMovies.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.i("MainFragment", "Read Database called!")
                    popularMoviesAdapter.setData(database[0].movieResponse)

                    hideShimmerEffect()
                } else {
                    fetchPopularMovies()

                }
            })
        }
    }

    private fun readTopRatedMoviesDb() {
        lifecycleScope.launch {
            movieMainViewModel.readTopRatedMovies.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    Log.i("MainFragment", "Read Database called!")
                    topRatedMoviesAdapter.setData(database[0].movieResponse)

                    hideShimmerEffect()
                } else {
                    fetchTopRatedMovies()

                }
            })
        }
    }

    private fun loadPopularMoviesFromCache() {
        lifecycleScope.launch {
            movieMainViewModel.readPopularMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    popularMoviesAdapter.setData(database[0].movieResponse)

                }
            }
        }
    }

    private fun loadTopRatedMoviesFromCache() {
        lifecycleScope.launch {
            movieMainViewModel.readTopRatedMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    topRatedMoviesAdapter.setData(database[0].movieResponse)

                }
            }
        }
    }



    private fun fetchNowPlayingMovies() {
        Log.i("MainFragment", "Api request! Now Playing Movies")
        movieMainViewModel.getNowPlayingMovies()
        movieMainViewModel.nowPlayingMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        viewPagerAdapter.fetchData(it.results)
                    }
                }

                is NetworkResult.Error -> {
                    showSnackBar(response.message.toString(), binding.root)
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun fetchTopRatedMovies() {
        Log.i("MainFragment", "Api request! Top Rated Movies")
        movieMainViewModel.getTopRatedMovies()
        movieMainViewModel.topRatedMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    hideShimmerEffect()
                    response.data?.let {
                        topRatedMoviesAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadTopRatedMoviesFromCache()
                    showSnackBar(response.message.toString(), binding.root)
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        Log.i("MainFragment", "Api request! Popular Movies")
        movieMainViewModel.getPopularMovies()
        movieMainViewModel.popularMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    hideShimmerEffect()
                    response.data?.let {
                        popularMoviesAdapter.setData(it)

                    }


                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadPopularMoviesFromCache()
                    showSnackBar(response.message.toString(), binding.root)

                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.rvPopularMovies.showShimmer()
        binding.rvTopRatedMovies.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.rvPopularMovies.hideShimmer()
        binding.rvTopRatedMovies.hideShimmer()
    }

}