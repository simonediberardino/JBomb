package game.http.repo

import game.http.serializing.HttpParserSerializer

class HttpRepository private constructor() {
    companion object {
        val instance: HttpRepository by lazy { HttpRepository() }
    }
}