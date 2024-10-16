package game.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import game.presentation.ui.pages.server_browser.ServerInfo

// Moshi instance with Kotlin support
val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val serversListAdapter: JsonAdapter<List<ServerInfo>> = moshi.adapter(
    Types.newParameterizedType(List::class.java, ServerInfo::class.java)
)
