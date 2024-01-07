package game.items

import game.Bomberman
import game.entity.EntityTypes
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.InvisibleBlock
import game.entity.blocks.StoneBlock
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.boss.clown.Hat
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.*
import game.entity.factory.EntityFactory
import game.entity.models.Character
import game.entity.placeable.Bomb
import game.entity.player.Player
import game.entity.player.RemotePlayer
import game.level.online.ClientGameHandler
import game.powerups.*
import game.powerups.portal.EndLevelPortal
import game.powerups.portal.World1Portal
import game.powerups.portal.World2Portal
import game.utils.Extensions.getOrTrim

class ItemsFactory {
    fun toItem(itemsTypes: ItemsTypes): UsableItem {
        return when (itemsTypes) {
            ItemsTypes.BombItem -> BombItem()
            ItemsTypes.PistolItem -> PistolItem()
        }
    }

    companion object {
        val instance: ItemsFactory by lazy { ItemsFactory() }
    }
}