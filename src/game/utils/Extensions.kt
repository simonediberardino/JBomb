package game.utils

object Extensions {
    fun Int.test() {

    }
    fun Map<String, String>.getOrTrim(key: String) : String? {
        return get(keys.find { it.trim() == key.trim() })
    }
}