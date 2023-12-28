package game.entity.player

import game.Bomberman
import game.data.DataInputOutput
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.bomb.AbstractExplosion
import game.entity.bomb.Bomb
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
    constructor(coordinates: Coordinates?, id: Long) : this(coordinates) {
        this.id = id
    }

    constructor() : this(null)

    override fun getEntitiesAssetsPath(): String = "$entitiesFolder/player/${DataInputOutput.getInstance().skin}"

    override fun getDeathSound(): SoundModel = SoundModel.PLAYER_DEATH

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

    // TODO!!!
    override fun onEndedDeathAnimation() {
        val t = Timer(SHOW_DEATH_PAGE_DELAY_MS.toInt()) { _: ActionEvent? -> showDeathPage() }
        t.isRepeats = false
        t.start()
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

    private fun showDeathPage() {
        Bomberman.destroyLevel()
        Bomberman.showActivity(GameOverPanel::class.java)
    }

    private fun updateBombs() {
        val maxBombs = DataInputOutput.getInstance().obtainedBombs
        println(maxBombs)
        UpdateCurrentAvailableItemsEvent().invoke(maxBombs)
        UpdateCurrentBombsLengthEvent().invoke(DataInputOutput.getInstance().explosionLength)
    }
}