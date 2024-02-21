package game.engine.world.dto

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
    World2Portal,
    Entity;

    fun toInt(): Int {
        return ordinal
    }

    fun toEntity(id: Long) : game.engine.world.domain.entity.actors.abstracts.base.Entity? {
        return EntityFactory.instance.toEntity(this, id)
    }
}