package pl.kfert.movie.ui.mainlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.kfert.movie.data.DataResult.Status.*
import pl.kfert.movie.databinding.MainFragmentBinding

class MainListFragment : Fragment() {

    private val viewModel: MainListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MovieAdapter(callbackFavoriteListener = { viewModel.updateMovie(it)
        }, callbackItemClick = { findNavController().navigate(MainListFragmentDirections.actionMainListFragmentToDetailFragment(it)) })
        setupRecycleView(binding, adapter)
        subscribeUi(binding, adapter)

        return binding.root
    }

    private fun setupRecycleView(binding: MainFragmentBinding, adapter: MovieAdapter) {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribeUi(binding: MainFragmentBinding, adapter: MovieAdapter) {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
                result -> when (result.status) {
                SUCCESS -> {
                    result.data?.let{ adapter.submitList(it!!) } }
                ERROR -> {
                    Snackbar.make(binding.recyclerView, result.message!!, Snackbar.LENGTH_LONG).show()
                } else -> {} }
        })
    }

}
