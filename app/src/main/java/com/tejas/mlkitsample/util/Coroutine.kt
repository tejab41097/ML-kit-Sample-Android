package com.tejas.mlkitsample.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Coroutine {

    fun <T : Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }


    fun <T : Any> ioThenIO(work: suspend (() -> T?), callback: suspend ((T?) -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }

    fun <T : Any> io(work: suspend (() -> T?)) = CoroutineScope(Dispatchers.IO).launch {
        work()
    }

    fun <T : Any> main(work: (() -> T?)) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }

}