package game.usecases

import kotlinx.coroutines.runBlocking

interface UseCase<T> {
    suspend fun invoke(): T
    fun invokeBlocking(): T {
        return runBlocking { invoke() }
    }
}