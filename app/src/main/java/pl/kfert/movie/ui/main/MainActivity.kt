package pl.kfert.movie.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.main_activity.*
import pl.kfert.movie.R
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation.findNavController
import pl.kfert.movie.data.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.kfert.movie.ui.detail.DetailFragmentDirections
import pl.kfert.movie.ui.mainlist.MainListFragmentDirections


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        setupAutoComplete()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            if (autoComplete.visibility == View.VISIBLE) {
                toolbar.hideOverflowMenu()
                autoComplete.visibility = View.GONE
            } else {
                toolbar.showOverflowMenu()
                autoComplete.visibility = View.VISIBLE
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupAutoComplete() {
        autoComplete.addTextChangedListener(QueryTextListener(lifecycle) {
            mainViewModel.getListSearchForQuery(it)
        })

        autoComplete.setOnFocusChangeListener { view, hasFocus -> if(!hasFocus) autoComplete.setText("") }
        val adapter = AutoCompleteAdapter(this, android.R.layout.select_dialog_item)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            autoComplete.clearFocus()
            goToDetailFragment(mainViewModel.getMovieId(adapter.getItem(position)))
        }
        autoComplete.setAdapter(adapter)
        mainViewModel.querySearch.observe(this, Observer { data -> updateAdapter(adapter, data) })
    }

    private fun goToDetailFragment(movie : Movie?) {
        movie?.let {
            findNavController(this, R.id.navHostFragment).currentDestination?.let {
                when (it.id){
                    R.id.mainListFragment -> {
                        findNavController(this, R.id.navHostFragment).navigate(MainListFragmentDirections.actionMainListFragmentToDetailFragment(movie))
                    }
                    else ->
                    {
                        findNavController(this, R.id.navHostFragment).navigate(DetailFragmentDirections.actionDetailFragmentSelf(movie))
                    }
                }
            }
        }
    }

    private fun updateAdapter(adapter: AutoCompleteAdapter, list: List<String>) {
        adapter.setData(list)
    }

}