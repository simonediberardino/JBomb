package game.entity

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.InvisibleBlock
import game.entity.blocks.StoneBlock
import game.entity.bomb.Bomb
import game.entity.bomb.ConfettiExplosion
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.boss.clown.Hat
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.*
import game.entity.player.Player
import game.entity.player.RemotePlayer
import game.level.online.ClientGameHandler
import game.powerups.*
import game.powerups.portal.EndLevelPortal
import game.powerups.portal.World1Portal
import game.powerups.portal.World2Portal

enum class EntityTypes {
    Player,
    Clown,
    Hat,
    GhostBoss,
    ClownNose,
    Eagle,
    FastEnemy,
    GhostEnemy,
    Helicopter,
    TankEnemy,
    YellowBall,
    DestroyableBlock,
    InvisibleBlock,
    StoneBlock,
    Zombie,
    MysteryBoxPerk,
    RemotePlayer,
    Bomb,
    ConfettiExplosion,
    FireExplosion,
    PistolExplosion,
    BomberEntity,
    ArmorPowerUp,
    BlockMoverPowerUp,
    EmptyPowerup,
    HammerPowerUp,
    FirePowerUp,
    IncreaseMaxBombsPowerUp,
    LivesPowerUp,
    PistolPowerUp,
    RemoteControlPowerUp,
    SpeedPowerUp,
    TransparentBombsPowerUp,
    TransparentDestroyableBlocksPowerUp,
    EndLevelPortal,
    World1Portal,
    World2Portal;

    fun toInt(): Int {
        return ordinal
    }

    fun toEntity(id: Long) : game.entity.models.Entity? {
        return when(this) {
            BomberEntity, RemotePlayer, Player -> {
                val isServer = Bomberman.getMatch().isServer
                val isClient = Bomberman.getMatch().isClient
                if (isServer && Bomberman.getMatch().player == null) {
                    return Player(id)
                } else if (isClient && id == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler?)?.id) {
                    return Player(id)
                }
                return RemotePlayer(id)
            }
            Clown -> Clown(null)
            Hat -> Hat(id)
            GhostBoss -> GhostBoss(id)
            ClownNose -> ClownNose(id)
            Eagle -> Eagle(id)
            FastEnemy -> FastEnemy(id)
            GhostEnemy -> GhostEnemy(id)
            Helicopter -> Helicopter(id)
            TankEnemy -> TankEnemy(id)
            YellowBall -> YellowBall(id)
            Zombie -> Zombie(id)
            DestroyableBlock -> DestroyableBlock(id)
            InvisibleBlock -> InvisibleBlock(id)
            StoneBlock -> StoneBlock(id)
            MysteryBoxPerk -> MysteryBoxPerk(id)
            Bomb -> Bomb(id)
            ConfettiExplosion -> null
            FireExplosion -> null
            PistolExplosion -> null
            ArmorPowerUp -> ArmorPowerUp(id)
            BlockMoverPowerUp -> BlockMoverPowerUp(id)
            EmptyPowerup -> EmptyPowerup(id)
            HammerPowerUp -> HammerPowerUp(id)
            FirePowerUp -> FirePowerUp(id)
            IncreaseMaxBombsPowerUp -> IncreaseMaxBombsPowerUp(id)
            LivesPowerUp -> LivesPowerUp(id)
            PistolPowerUp -> PistolPowerUp(id)
            RemoteControlPowerUp -> RemoteControlPowerUp(id)
            SpeedPowerUp -> SpeedPowerUp(id)
            TransparentBombsPowerUp -> TransparentBombsPowerUp(id)
            TransparentDestroyableBlocksPowerUp -> TransparentDestroyableBlocksPowerUp(id)
            EndLevelPortal -> EndLevelPortal(id)
            World1Portal -> World1Portal(id)
            World2Portal -> World2Portal(id)
        }
    }
}