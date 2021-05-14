package ru.btelepov.moviemix.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.CombinedLoadStates

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import ru.btelepov.moviemix.adapters.SerialAdapterPaging
import ru.btelepov.moviemix.adapters.SerialListAdapter
import ru.btelepov.moviemix.databinding.FragmentSerialsMainBinding
import ru.btelepov.moviemix.utils.Functions.Companion.observeOnce
import ru.btelepov.moviemix.utils.Functions.Companion.showSnackBar
import ru.btelepov.moviemix.utils.NetworkResult
import ru.btelepov.moviemix.utils.PagingLoadStateAdapter
import ru.btelepov.moviemix.viewmodels.SerialsMainViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SerialsMainFragment : Fragment() {

    private var _binding: FragmentSerialsMainBinding? = null
    private val binding get() = _binding!!


    private val serialsMainViewModel: SerialsMainViewModel by viewModels()
    //   private val serialAdapterPaging: SerialAdapterPaging by lazy { SerialAdapterPaging() }

    private val serialListAdapter: SerialListAdapter by lazy { SerialListAdapter() }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSerialsMainBinding.inflate(inflater, container, false)

        readDatabase()
        setupRv()
        fetchSerials()


        return binding.root
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

                    isLoading = false
                    binding.paginationProgressBar.visibility = View.INVISIBLE

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
                    loadFromCache()
                    binding.paginationProgressBar.visibility = View.INVISIBLE
                    showSnackBar(response.message.toString(), binding.root)

                }
                is NetworkResult.Loading -> {

                    isLoading = true

                    binding.paginationProgressBar.visibility = View.VISIBLE


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
                    binding.paginationProgressBar.visibility = View.INVISIBLE
                    serialListAdapter.differ.submitList(database[0].serialResponse.serialItems.toList())
                }
            }
        }
    }


}