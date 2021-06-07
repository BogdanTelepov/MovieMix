package ru.btelepov.moviemix.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.adapters.CastMovieAdapter
import ru.btelepov.moviemix.adapters.GenreAdapter
import ru.btelepov.moviemix.adapters.MovieAdapter

import ru.btelepov.moviemix.databinding.FragmentDetailsBinding
import ru.btelepov.moviemix.utils.Constants.Companion.BACKDROP_PATH_URL
import ru.btelepov.moviemix.utils.Functions.Companion.showSnackBar
import ru.btelepov.moviemix.utils.Functions.Companion.showToast
import ru.btelepov.moviemix.utils.NetworkResult
import ru.btelepov.moviemix.utils.viewBinding
import ru.btelepov.moviemix.viewmodels.DetailsViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding: FragmentDetailsBinding by viewBinding()

    private val args: DetailsFragmentArgs by navArgs()

    private val detailsViewModel: DetailsViewModel by viewModels()

    private val castAdapter: CastMovieAdapter by lazy { CastMovieAdapter() }
    private val moviesAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val genreAdapter: GenreAdapter by lazy { GenreAdapter() }

    private var isLoading = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()


    }

    private fun initAdapter() {
        binding.rvCastList.adapter = castAdapter
        binding.rvSimilarMovies.adapter = moviesAdapter
        binding.rvGenreList.adapter = genreAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val movie = args.movieData

        binding.movieTitle.text = movie.title.toString().trim()
        binding.movieRating.rating = movie.voteAverage!!.toFloat() / 2
        binding.movieVoteCount.text = movie.voteCount.toString().trim()
        binding.movieDescription.text = movie.overview.toString().trim()
        Picasso.get().load(BACKDROP_PATH_URL + movie.backdropPath)
            .into(binding.movieBackdropImage)

        if (movie.adult == true) {
            binding.moviePegiRating.text = "16+".trim()
        } else {
            binding.moviePegiRating.text = "6+".trim()
        }

        movie.id?.let { getMovieDetails(it) }
        movie.id?.let { fetchCastList(it) }
        movie.id?.let { fetchSimilarMovies(it) }
    }


    private fun getMovieDetails(movieId: Int) {
        detailsViewModel.getMovieDetails(movieId)
        detailsViewModel.getMovieDetailsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
//                    binding.movieTitle.text = response.data?.originalTitle.toString().trim()
//                    binding.movieRating.rating = response.data?.voteAverage!!.toFloat() / 2
//                    binding.movieVoteCount.text = response.data.voteCount.toString().trim()
//                    binding.movieDescription.text = response.data.overview.toString().trim()
//                    Picasso.get().load(BACKDROP_PATH_URL + response.data.backdropPath)
//                        .into(binding.movieBackdropImage)

                    response.data?.genres?.let { genreAdapter.setData(it.toList()) }


                }
                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun fetchSimilarMovies(movieId: Int) {
        detailsViewModel.getSimilarMovies(movieId)
        detailsViewModel.similarMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    isLoading = false
                    showLoading(isLoading)
                    binding.rvSimilarMovies.visibility = View.VISIBLE
                    binding.tvMoviesNotFound.visibility = View.GONE
                    response.data?.let {
                        moviesAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    isLoading = true
                    showLoading(isLoading)
                    binding.rvSimilarMovies.visibility = View.GONE
                    binding.tvMoviesNotFound.visibility = View.VISIBLE
                }

                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun fetchCastList(movieId: Int) {
        detailsViewModel.getMovieCredits(movieId)
        detailsViewModel.creditsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        castAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }

            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.anim_not_found)
        }
    }

    private fun showAnimation(animationRes: Int) {
        binding.animationNotFound.visibility = View.VISIBLE
        binding.animationNotFound.setAnimation(animationRes)
        binding.animationNotFound.playAnimation()


    }


}