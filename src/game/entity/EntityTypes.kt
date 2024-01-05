package game.entity

import game.entity.factory.EntityFactory

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

    fun toEntity(extras: Map<String, String>) : game.entity.models.Entity? {
        return EntityFactory.instance.toEntity(this, extras)
    }
}