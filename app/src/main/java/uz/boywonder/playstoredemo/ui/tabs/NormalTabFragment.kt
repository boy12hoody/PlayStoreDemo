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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.adapter.ParentRecyclerViewAdapter
import uz.boywonder.playstoredemo.databinding.FragmentNormalTabBinding
import uz.boywonder.playstoredemo.model.CategoryItem
import uz.boywonder.playstoredemo.ui.main.MainViewModel
import uz.boywonder.playstoredemo.util.NetworkResult

@AndroidEntryPoint
class NormalTabFragment : Fragment(R.layout.fragment_normal_tab) {

    private val binding: FragmentNormalTabBinding by viewBinding()
    private val mainViewModel: MainViewModel by viewModels()
    private val parentRvAdapter by lazy { ParentRecyclerViewAdapter() }

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

        /* DUMMY CATEGORIES SINCE API DOESN'T PROVIDE ONE */
        val categories = mutableListOf<CategoryItem>()

        mainViewModel.getResponse()
        mainViewModel.moviesLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    hideShimmerEffect()

                    categories.add(CategoryItem("Tech", response.data!!))
                    categories.add(CategoryItem("Nature", response.data))
                    categories.add(CategoryItem("People", response.data))
                    categories.add(CategoryItem("City", response.data))
                    categories.add(CategoryItem("Trees", response.data))
                    categories.add(CategoryItem("IDK", response.data))

                    parentRvAdapter.setNewData(categories)
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
        binding.parentRecyclerView.apply {
            adapter = parentRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            parentRecyclerView.visibility = View.GONE
        }
    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            parentRecyclerView.visibility = View.VISIBLE
        }
    }
}