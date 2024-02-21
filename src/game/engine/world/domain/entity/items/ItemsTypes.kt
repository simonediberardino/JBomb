package game.engine.world.domain.entity.items

enum class ItemsTypes {
    BombItem,
    PistolItem;

    fun toInt(): Int = ordinal
    fun toItem() : UsableItem = ItemsFactory.instance.toItem(this)
}