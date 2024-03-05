package game.mappers

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.blocks.invisible_block.InvisibleBlock
import game.engine.world.domain.entity.actors.impl.blocks.stone_block.StoneBlock
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.MysteryBoxPerk
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.engine.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.world.domain.entity.actors.impl.bomber_entity.remote_player.RemotePlayer
import game.engine.level.online.ClientGameHandler
import game.engine.world.domain.entity.actors.impl.enemies.npcs.clown_nose.ClownNose
import game.engine.world.domain.entity.actors.impl.enemies.npcs.eagle.Eagle
import game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.GhostEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.engine.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.engine.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.engine.world.domain.entity.pickups.portals.EndLevelPortal
import game.engine.world.domain.entity.pickups.portals.World1Portal
import game.engine.world.domain.entity.pickups.portals.World2Portal
import game.engine.world.domain.entity.pickups.powerups.*
import game.engine.world.types.EntityTypes

class EntityFactory {
    fun toEntity(entityTypes: EntityTypes, id: Long): Entity? {
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
            else -> { null }
        }
    }

    private fun createPlayerEntity(id: Long): Entity {
        val isServer = Bomberman.getMatch().isServer
        val isClient = Bomberman.getMatch().isClient

        return when {
            isServer && Bomberman.getMatch().player == null -> Player(id)
            isClient && id == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler?)?.id -> Player(id)
            else -> RemotePlayer(id)
        }
    }

    companion object {
        val instance: EntityFactory by lazy { EntityFactory() }
    }
}
