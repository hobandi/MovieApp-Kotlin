package pl.kfert.movie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import pl.kfert.movie.data.DataResult.Status.ERROR
import pl.kfert.movie.data.DataResult.Status.SUCCESS

/**
 * [DataResult.Status.SUCCESS] - with data from database
 * [DataResult.Status.ERROR] - if error has occurred from any source
 * [DataResult.Status.LOADING]
 */
fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>, networkCall: suspend () -> DataResult<A>,
    saveCallResult: suspend (A, T?) -> Unit
): LiveData<DataResult<T>> =
    liveData(Dispatchers.IO) {

        emit(DataResult.loading())

        var item: T? = null

        val source = databaseQuery.invoke().map {
            item = it
            DataResult.success(it)
        }
        val responseStatus = networkCall.invoke()

        if (responseStatus.status == SUCCESS) {
            emitSource(source)
            saveCallResult(responseStatus.data!!, item)
        } else if (responseStatus.status == ERROR) {
            emit(DataResult.error(responseStatus.message!!))
            emitSource(source)
        }
    }
