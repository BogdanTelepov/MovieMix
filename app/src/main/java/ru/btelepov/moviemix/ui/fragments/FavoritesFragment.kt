package ru.btelepov.moviemix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.btelepov.moviemix.R
import ru.btelepov.moviemix.databinding.FragmentFavoritesBinding
import ru.btelepov.moviemix.utils.viewBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding: FragmentFavoritesBinding by viewBinding()

    private var isLoading = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(isLoading)
        binding.tvIsEmpty.visibility = View.VISIBLE

    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.empty_state)
        }
    }

    private fun showAnimation(animationRes: Int) {
        binding.animationEmptyState.visibility = View.VISIBLE
        binding.animationEmptyState.setAnimation(animationRes)
        binding.animationEmptyState.playAnimation()


    }


}