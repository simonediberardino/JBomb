package game.storage.cache

class Cache  // Private constructor to prevent direct instantiation
private constructor() {
    private val cache = HashMap<String, Any?>()
    fun <T> queryCache(key: String): T? {
        val element = cache.getOrDefault(key, null)
        return element as T?
    }

    fun hasInCache(key: String): Boolean {
        return cache.containsKey(key)
    }

    fun saveInCache(key: String, o: Any?) {
        cache[key] = o
    }

    companion object {
        val instance: Cache by lazy { Cache() }
    }
}
