package game.entity

import game.Bomberman
import game.data.DataInputOutput
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.bomb.AbstractExplosion
import game.entity.bomb.Bomb
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.entity.models.Entity
import game.events.game.DeathGameEvent
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.events.game.UpdateCurrentBombsLengthEvent
import game.hardwareinput.Command
import game.powerups.PowerUp
import game.sound.SoundModel
import game.ui.pages.GameOverPanel
import game.ui.panels.game.PitchPanel
import game.utils.Paths.entitiesFolder
import java.awt.event.ActionEvent
import javax.swing.Timer

class Player(coordinates: Coordinates?) : BomberEntity(coordinates) {
    init {
        hitboxSizeToHeightRatio = 0.733f
    }

    constructor(coordinates: Coordinates?, id: Long) : this(coordinates) {
        this.id = id
    }

    constructor() : this(null)

    private fun updateBombs() {
        val maxBombs = DataInputOutput.getInstance().obtainedBombs
        UpdateCurrentAvailableItemsEvent().invoke(maxBombs)
        UpdateCurrentBombsLengthEvent().invoke(DataInputOutput.getInstance().explosionLength)
    }

    override fun doInteract(e: Entity?) {}

    override fun getInteractionsEntities(): Set<Class<out Entity>> = HashSet()

    override fun getEntitiesAssetsPath(): String = "$entitiesFolder/player/${DataInputOutput.getInstance().skin}"


    override fun getDeathSound(): SoundModel = SoundModel.PLAYER_DEATH

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> =
            hashSetOf(AbstractExplosion::class.java, Enemy::class.java, PowerUp::class.java)

    override fun getStepSound(): SoundModel = SoundModel.STEP_SOUND

    public override fun getSpawnOffset(): Coordinates = SPAWN_OFFSET

    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(
                HardBlock::class.java,
                DestroyableBlock::class.java
        )

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                DestroyableBlock::class.java,
                Enemy::class.java,
                Bomb::class.java
        )

    override val maxExplosionDistance: Int
        get() = currExplosionLength


    override fun getCharacterOrientedImages(): Array<String> {
        return Array(4) { index ->
            "$entitiesAssetsPath/player_${imageDirection.toString().lowercase()}_${index}.png"
        }
    }

    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Bomberman.getMatch().controllerManager?.register(this)
        Bomberman.getBombermanFrame().matchPanel.refreshPowerUps(activePowerUps)
    }

    override fun onEndedDeathAnimation() {
        val t = Timer(SHOW_DEATH_PAGE_DELAY_MS.toInt()) { _: ActionEvent? -> showDeathPage() }
        t.isRepeats = false
        t.start()
    }

    private fun showDeathPage() {
        Bomberman.destroyLevel()
        Bomberman.showActivity(GameOverPanel::class.java)
    }

    override fun onEliminated() {
        super.onEliminated()
        DeathGameEvent().invoke(null)
    }

    // Handle the command entered by the player;
    override fun update(arg: Any?) {
        super.update(arg)
        handleAction(arg as Command)
    }

    override fun doAttack() {
        weapon.use()
        Bomberman.getMatch().updateInventoryWeaponController()
    }

    companion object {
        val SPAWN_OFFSET = Coordinates((PitchPanel.GRID_SIZE - SIZE) / 2, PitchPanel.GRID_SIZE - SIZE)
    }
}