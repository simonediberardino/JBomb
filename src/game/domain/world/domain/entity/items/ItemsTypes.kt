package game.domain.world.domain.entity.items

enum class ItemsTypes {
    BombItem,
    PistolItem,
    BlockPlacer;

    fun toInt(): Int = ordinal
    fun toItem() : UsableItem = ItemsFactory.instance.toItem(this)
}