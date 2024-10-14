package game.domain.world.domain.entity.actors.impl

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.LavaBlock
import game.domain.world.domain.entity.actors.impl.blocks.misc_blocks.Block1
import game.domain.world.domain.entity.actors.impl.blocks.stone_block.StoneBlock
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.Clown
import game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.GhostBoss
import game.domain.world.domain.entity.actors.impl.enemies.npcs.clown_nose.ClownNose
import game.domain.world.domain.entity.actors.impl.enemies.npcs.eagle.Eagle
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.FastPurpleBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.GhostEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.helicopter.Helicopter
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.SkeletonEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.YellowBall
import game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie
import game.domain.world.domain.entity.actors.impl.explosion.ConfettiExplosion
import game.domain.world.domain.entity.actors.impl.explosion.FireExplosion
import game.domain.world.domain.entity.actors.impl.explosion.PistolExplosion

val EntityIds: Map<String, Class<out Entity>> = mapOf(
    // Enemies
    "enemy_confetti_explosion" to ConfettiExplosion::class.java,
    "enemy_fire_explosion" to FireExplosion::class.java,
    "enemy_pistol_explosion" to PistolExplosion::class.java,
    "enemy_clown" to Clown::class.java,
    "enemy_ghost" to GhostBoss::class.java,
    "enemy_clown_nose" to ClownNose::class.java,
    "enemy_eagle" to Eagle::class.java,
    "enemy_fast_purple_ball" to FastPurpleBall::class.java,
    "enemy_ghost_enemy" to GhostEnemy::class.java,
    "enemy_helicopter" to Helicopter::class.java,
    "enemy_skeleton" to SkeletonEnemy::class.java,
    "enemy_tank" to TankEnemy::class.java,
    "enemy_yellow_ball" to YellowBall::class.java,
    "enemy_zombie" to Zombie::class.java,

    // Blocks
    "block_destroyable" to DestroyableBlock::class.java,
    "block_stone" to StoneBlock::class.java,
    "block_lava" to LavaBlock::class.java,
    "block_1" to Block1::class.java
)