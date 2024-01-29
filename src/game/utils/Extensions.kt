package game.utils

import kotlin.reflect.full.memberProperties

object Extensions {
    fun <T : Any> T.toMap(): Map<String, String> {
        return this::class.memberProperties
                .associateBy { it.name }
                .mapValues { prop ->
                    prop.value.call(this).toString()
                }
    }

    fun Map<String, String>.getOrTrim(key: String) : String? {
        return get(keys.find { it.trim() == key.trim() })
    }
}