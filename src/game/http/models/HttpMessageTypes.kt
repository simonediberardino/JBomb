package game.http.models

enum class HttpMessageTypes {
    LOCATION,
    PLAYER_JOIN_REQUEST,
    SPAWNED_ENTITY,
    DESPAWNED_ENTITY,
    NEW_LEVEL,
    ASSIGN_ID,
    USE_ITEM,
    ENTITY_ATTACKED,
    LEVEL_INFO
}