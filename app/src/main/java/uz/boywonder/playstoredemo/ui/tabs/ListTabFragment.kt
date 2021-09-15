package uz.boywonder.playstoredemo.ui.tabs

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.adapter.ChildRecyclerViewAdapter
import uz.boywonder.playstoredemo.databinding.FragmentListTabBinding
import uz.boywonder.playstoredemo.ui.main.MainViewModel
import uz.boywonder.playstoredemo.util.NetworkResult

@AndroidEntryPoint
class ListTabFragment : Fragment(R.layout.fragment_list_tab) {

    private val binding: FragmentListTabBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private val rvAdapter by lazy { ChildRecyclerViewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                requestApiData()
            }
        }
    }

    private fun requestApiData() {

        mainViewModel.getResponse()
        mainViewModel.moviesLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { rvAdapter.setNewData(it) }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerview.apply {
            adapter = rvAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }
    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }
    }
}