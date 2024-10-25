package game.repository

import game.domain.level.levels.multiplayer.MultiplayerLevel
import game.domain.level.levels.multiplayer.MultiplayerLevelMap1
import game.localization.Localization

object RepositoryMaps {
    val maps: Map<String, Class<out MultiplayerLevel>> = mapOf(
        Localization.get(Localization.MP_CASTLE_UI_NAME) to MultiplayerLevelMap1::class.java)
}