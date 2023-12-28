package game.entity

import game.entity.enemies.boss.clown.Clown
import game.entity.enemies.boss.clown.Hat
import game.entity.enemies.boss.ghost.GhostBoss
import game.entity.enemies.npcs.*
import game.entity.player.Player

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
    Zombie;

    fun toInt(): Int {
        return ordinal
    }

    fun toEntity() : game.entity.models.Entity {
        return when(this) {
            Player -> Player()
            Clown -> Clown(null)
            Hat -> Hat()
            GhostBoss -> GhostBoss()
            ClownNose -> ClownNose()
            Eagle -> Eagle()
            FastEnemy -> FastEnemy()
            GhostEnemy -> GhostEnemy()
            Helicopter -> Helicopter()
            TankEnemy -> TankEnemy()
            YellowBall -> YellowBall()
            Zombie -> Zombie()
        }
    }
}