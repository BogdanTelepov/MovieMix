package ru.btelepov.moviemix.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.View

import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.launch
import ru.btelepov.moviemix.R

import ru.btelepov.moviemix.adapters.SerialListAdapter
import ru.btelepov.moviemix.databinding.FragmentSerialsMainBinding
import ru.btelepov.moviemix.utils.Functions.Companion.observeOnce
import ru.btelepov.moviemix.utils.Functions.Companion.showSnackBar
import ru.btelepov.moviemix.utils.NetworkResult

import ru.btelepov.moviemix.utils.viewBinding
import ru.btelepov.moviemix.viewmodels.SerialsMainViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SerialsMainFragment : Fragment(R.layout.fragment_serials_main) {

    private val binding by viewBinding<FragmentSerialsMainBinding>()


    private val serialsMainViewModel: SerialsMainViewModel by viewModels()
    //   private val serialAdapterPaging: SerialAdapterPaging by lazy { SerialAdapterPaging() }

    private val serialListAdapter: SerialListAdapter by lazy { SerialListAdapter() }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDatabase()
        setupRv()
        fetchSerials()
    }


    private fun fetchSerials() {
//        lifecycleScope.launch {
//            serialsMainViewModel.getTvSerials().collectLatest {
//                serialAdapterPaging.submitData(it)
//            }
//        }

        serialsMainViewModel.getSerialList()
        serialsMainViewModel.serialListResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.i("SerialFragment", "Read API!")
                    isLoading = false
                    showLoading(isLoading)
                    binding.linearLayout.visibility = View.GONE
                    binding.animationLoading.visibility = View.GONE



                    response.data?.let {
                        serialListAdapter.differ.submitList(it.serialItems.toList())
                        val totalPages = it.totalResults / 20 + 2
                        isLastPage = serialsMainViewModel.pages == totalPages
                        if (isLastPage) {
                            binding.rvSerials.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is NetworkResult.Error -> {

                    isLoading = false
                    showLoading(isLoading)
                    loadFromCache()
                    binding.linearLayout.visibility = View.GONE
                    binding.animationLoading.visibility = View.GONE
                    showSnackBar(response.message.toString(), binding.root)

                }
                is NetworkResult.Loading -> {

                    isLoading = true
                    showLoading(isLoading = isLoading)
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.animationLoading.visibility = View.VISIBLE


                }
            }
        }
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                serialsMainViewModel.getSerialList()
                isScrolling = false
            }
        }
    }

    private fun setupRv() {

//        binding.rvSerials.adapter = serialAdapterPaging.withLoadStateHeaderAndFooter(
//            header = PagingLoadStateAdapter(serialAdapterPaging),
//            footer = PagingLoadStateAdapter(serialAdapterPaging)
//        )
//        serialAdapterPaging.addLoadStateListener { combinedLoadStates: CombinedLoadStates ->
//
//        }


        binding.rvSerials.adapter = serialListAdapter
        binding.rvSerials.addOnScrollListener(scrollListener)

    }

    private fun readDatabase() {
        lifecycleScope.launch {
            serialsMainViewModel.readSerialItems.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.i("SerialFragment", "Read Database called!")
                    serialListAdapter.differ.submitList(database[0].serialResponse.serialItems.toList())
                } else {
                    fetchSerials()
                }
            }
        }
    }


    private fun loadFromCache() {
        lifecycleScope.launch {
            serialsMainViewModel.readSerialItems.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    binding.animationLoading.visibility = View.INVISIBLE
                    serialListAdapter.differ.submitList(database[0].serialResponse.serialItems.toList())
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> showAnimation(R.raw.loading_animation)
        }
    }

    private fun showAnimation(animationRes: Int) {
        binding.animationLoading.visibility = View.VISIBLE
        binding.animationLoading.setAnimation(animationRes)
        binding.animationLoading.playAnimation()


    }


}