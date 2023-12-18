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
import game.events.game.UpdateCurrentAvailableBombsEvent
import game.hardwareinput.Command
import game.powerups.PowerUp
import game.sound.SoundModel
import game.ui.pages.GameOverPanel
import game.ui.panels.game.PitchPanel
import game.utils.Paths.entitiesFolder
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.Timer

class Player(coordinates: Coordinates?) : BomberEntity(coordinates) {
    init {
        hitboxSizeToHeightRatio = 0.733f
    }

    constructor() : this(null)

    private fun updateBombs() {
        val maxBombs = DataInputOutput.getInstance().obtainedBombs
        UpdateCurrentAvailableBombsEvent().invoke(maxBombs)
    }

    override fun doInteract(e: Entity?) {}
    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return HashSet()
    }

    override fun getBasePath(): String {
        return entitiesFolder + "/player/" + DataInputOutput.getInstance().skin
    }

    override fun getCharacterOrientedImages(): Array<String> {
        return arrayOf(String.format("%s/player_%s_%d.png", basePath, imageDirection.toString().lowercase(Locale.getDefault()), 0), String.format("%s/player_%s_%d.png", basePath, imageDirection.toString().lowercase(Locale.getDefault()), 1), String.format("%s/player_%s_%d.png", basePath, imageDirection.toString().lowercase(Locale.getDefault()), 2), String.format("%s/player_%s_%d.png", basePath, imageDirection.toString().lowercase(Locale.getDefault()), 3))
    }

    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Bomberman.getMatch().controllerManager.register(this)
        Bomberman.getBombermanFrame().matchPanel.refreshPowerUps(activePowerUps)
    }

    override fun onEndedDeathAnimation() {
        val t = Timer(SHOW_DEATH_PAGE_DELAY_MS.toInt()) { e: ActionEvent? -> showDeathPage() }
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

    public override fun getSpawnOffset(): Coordinates {
        return SPAWN_OFFSET
    }

    // Handle the command entered by the player;
    override fun update(arg: Any?) {
        super.update(arg)
        handleAction(arg as Command)
    }

    override fun getDeathSound(): SoundModel {
        return SoundModel.PLAYER_DEATH
    }

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(Arrays.asList(AbstractExplosion::class.java, Enemy::class.java, PowerUp::class.java))
    }

    override fun getStepSound(): SoundModel {
        return SoundModel.STEP_SOUND
    }

    //4 methods for pistolPowerUp only
    override val explosionObstacles: Set<Class<out Entity>>
        get() {
            return setOf(
                    HardBlock::class.java,
                    DestroyableBlock::class.java
            )
        }

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() {
            return setOf(
                    DestroyableBlock::class.java,
                    Enemy::class.java,
                    Bomb::class.java
            )
        }

    override val maxExplosionDistance: Int
        get() {
            return Bomberman.getMatch().player.currExplosionLength
        }

    override fun doAttack() {
        weapon.use()
        Bomberman.getMatch().updateInventoryWeaponController()
    }

    companion object {
        val SPAWN_OFFSET = Coordinates((PitchPanel.GRID_SIZE - SIZE) / 2, PitchPanel.GRID_SIZE - SIZE)
    }
}