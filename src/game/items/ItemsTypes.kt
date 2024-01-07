package game.items

import game.entity.factory.EntityFactory

enum class ItemsTypes {
    BombItem,
    PistolItem;

    fun toInt(): Int = ordinal
    fun toItem() : UsableItem = ItemsFactory.instance.toItem(this)
}