package game.entity.factory

import game.entity.EntityTypes
import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.InvisibleBlock
import game.entity.blocks.StoneBlock
import game.entity.placeable.Bomb
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.boss.clown.Hat
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.*
import game.entity.models.Character
import game.entity.player.Player
import game.entity.player.RemotePlayer
import game.level.online.ClientGameHandler
import game.powerups.*
import game.powerups.portal.EndLevelPortal
import game.powerups.portal.World1Portal
import game.powerups.portal.World2Portal
import game.utils.Extensions.getOrTrim

class EntityFactory {
    fun toEntity(entityTypes: EntityTypes, extras: Map<String, String>): game.entity.models.Entity? {
        val id = extras.getOrTrim("entityId")?.toLong() ?: return null

        return when (entityTypes) {
            EntityTypes.BomberEntity, EntityTypes.RemotePlayer, EntityTypes.Player -> createPlayerEntity(id)
            EntityTypes.Clown -> Clown(null)
            EntityTypes.Hat -> Hat(id)
            EntityTypes.GhostBoss -> GhostBoss(id)
            EntityTypes.ClownNose -> ClownNose(id)
            EntityTypes.Eagle -> Eagle(id)
            EntityTypes.FastEnemy -> FastEnemy(id)
            EntityTypes.GhostEnemy -> GhostEnemy(id)
            EntityTypes.Helicopter -> Helicopter(id)
            EntityTypes.TankEnemy -> TankEnemy(id)
            EntityTypes.YellowBall -> YellowBall(id)
            EntityTypes.Zombie -> Zombie(id)
            EntityTypes.DestroyableBlock -> DestroyableBlock(id)
            EntityTypes.InvisibleBlock -> InvisibleBlock(id)
            EntityTypes.StoneBlock -> StoneBlock(id)
            EntityTypes.MysteryBoxPerk -> MysteryBoxPerk(id)
            EntityTypes.Bomb -> createBombEntity(id, extras)
            EntityTypes.ConfettiExplosion, EntityTypes.FireExplosion, EntityTypes.PistolExplosion -> null
            EntityTypes.ArmorPowerUp -> ArmorPowerUp(id)
            EntityTypes.BlockMoverPowerUp -> BlockMoverPowerUp(id)
            EntityTypes.EmptyPowerup -> EmptyPowerup(id)
            EntityTypes.HammerPowerUp -> HammerPowerUp(id)
            EntityTypes.FirePowerUp -> FirePowerUp(id)
            EntityTypes.IncreaseMaxBombsPowerUp -> IncreaseMaxBombsPowerUp(id)
            EntityTypes.LivesPowerUp -> LivesPowerUp(id)
            EntityTypes.PistolPowerUp -> PistolPowerUp(id)
            EntityTypes.RemoteControlPowerUp -> RemoteControlPowerUp(id)
            EntityTypes.SpeedPowerUp -> SpeedPowerUp(id)
            EntityTypes.TransparentBombsPowerUp -> TransparentBombsPowerUp(id)
            EntityTypes.TransparentDestroyableBlocksPowerUp -> TransparentDestroyableBlocksPowerUp(id)
            EntityTypes.EndLevelPortal -> EndLevelPortal(id)
            EntityTypes.World1Portal -> World1Portal(id)
            EntityTypes.World2Portal -> World2Portal(id)
        }
    }

    private fun createPlayerEntity(id: Long): game.entity.models.Entity {
        val isServer = Bomberman.getMatch().isServer
        val isClient = Bomberman.getMatch().isClient

        return when {
            isServer && Bomberman.getMatch().player == null -> Player(id)
            isClient && id == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler?)?.id -> Player(id)
            else -> RemotePlayer(id)
        }
    }

    private fun createBombEntity(id: Long, extras: Map<String, String>): game.entity.models.Entity? {
        val callerId = extras.getOrTrim("callerId")?.toLong() ?: return null
        val character = Bomberman.getMatch().getEntityById(callerId) as Character?

        val bombEntity = Bomb(id, character ?: return null)
        // bombEntity.trigger() // temporary, only server should implement this
        return bombEntity
    }

    companion object {
        val instance: EntityFactory by lazy { EntityFactory() }
    }
}
