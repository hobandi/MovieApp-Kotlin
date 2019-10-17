package pl.kfert.movie.ui.main

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


internal class QueryTextListener(lifecycle: Lifecycle, private val queryTextChange: (String) -> Unit) : TextWatcher {


    var timeToDelay: Long = 500

    private val scope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun afterTextChanged(p0: Editable?) {
        //nop
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //nop
    }

    override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        searchJob?.cancel()
        searchJob = scope.launch {
            newText?.let {
                delay(timeToDelay)
                queryTextChange(newText.toString())
            }
        }
    }

}