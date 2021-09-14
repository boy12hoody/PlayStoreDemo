package uz.boywonder.playstoredemo.ui.tabs

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import uz.boywonder.playstoredemo.R
import uz.boywonder.playstoredemo.databinding.FragmentListTabBinding

@AndroidEntryPoint
class ListTabFragment : Fragment(R.layout.fragment_list_tab) {

    private val binding: FragmentListTabBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}