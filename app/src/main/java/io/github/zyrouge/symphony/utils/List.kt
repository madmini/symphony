package io.github.zyrouge.symphony.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

fun <T> List<T>.subListNonStrict(length: Int, start: Int = 0) =
    subList(start, min(start + length, size))

fun <T> List<T>.randomSubList(length: Int): List<T> {
    val mut = toMutableList()
    val out = mutableListOf<T>()
    val possibleLength = max(0, min(length, mut.size))
    for (i in 0 until possibleLength) {
        val index = Random.nextInt(mut.size)
        out.add(mut.removeAt(index))
    }
    return out
}

fun <T> List<T>.mutate(fn: MutableList<T>.() -> Unit): List<T> {
    val out = toMutableList()
    fn.invoke(out)
    return out
}

fun <T> concurrentListOf(): MutableList<T> = CopyOnWriteArrayList(mutableListOf<T>())

fun <T, R> Iterable<T>.chunkedParallelMap(
    chunkSize: Int = 100,
    context: CoroutineContext = Dispatchers.Default,
    transform: suspend (T) -> R,
): List<R> = runBlocking {
    coroutineScope {
        chunked(chunkSize).map { chunk ->
            async(context) {
                chunk.map { transform(it) }
            }
        }.awaitAll().flatten()
    }
}
