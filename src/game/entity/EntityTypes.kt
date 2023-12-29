package game.entity

import game.entity.blocks.DestroyableBlock
import game.entity.blocks.InvisibleBlock
import game.entity.blocks.StoneBlock
import game.entity.bonus.mysterybox.MysteryBoxPerk
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
    DestroyableBlock,
    InvisibleBlock,
    StoneBlock,
    Zombie,
    MysteryBoxPerk;

    fun toInt(): Int {
        return ordinal
    }

    fun toEntity(id: Long) : game.entity.models.Entity {
        return when(this) {
            Player -> Player(id)
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
        }
    }
}