package pl.kfert.movie.data.base

import androidx.lifecycle.*
import kotlinx.coroutines.*
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.DataResult.Status.ERROR
import pl.kfert.movie.data.DataResult.Status.SUCCESS

fun <T, A> resultLiveData(
    databaseQuery: suspend () -> T,
    networkCall: suspend () -> DataResult<A>,
    beforeInsert: suspend (T?, DataResult<A>) -> T,
    insertQuery: suspend (T) -> Unit
): LiveData<DataResult<T>> = liveData(Dispatchers.IO) {

    val item = MutableLiveData<DataResult<T>>(DataResult.loading())

    databaseQuery.invoke().let {
        item.postValue(DataResult.success(it))
    }
    emitSource(item)

    val api = networkCall.invoke()

    if (api.status == SUCCESS) {
        databaseQuery.invoke().let { databaseOld ->
            beforeInsert.invoke(
                databaseOld,
                DataResult.success(api.data!!)
            ).let {
                insertQuery.invoke(it)
                item.postValue(DataResult.success(it))
            }
        }
    } else if (api.status == ERROR) {
        item.postValue(DataResult.error(api.message!!))
        emitSource(item)
    }
}

fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> DataResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<DataResult<T>> =
    liveData(Dispatchers.IO) {
        emit(DataResult.loading<T>())
        val source = databaseQuery.invoke().map { DataResult.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(DataResult.error<T>(responseStatus.message!!))
            emitSource(source)
        }
    }