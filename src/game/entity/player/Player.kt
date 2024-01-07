package game.entity.player

import game.Bomberman
import game.data.DataInputOutput
import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.events.game.DeathGameEvent
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.events.game.UpdateCurrentBombsLengthEvent
import game.hardwareinput.Command
import game.sound.SoundModel
import game.ui.pages.GameOverPanel
import game.utils.Paths.entitiesFolder
import java.awt.event.ActionEvent
import javax.swing.Timer

class Player(coordinates: Coordinates?) : BomberEntity(coordinates) {
    constructor() : this(null)

    constructor(id : Long) : this() {
        this.id = id
    }

    override val entitiesAssetsPath: String get() ="$entitiesFolder/player/${DataInputOutput.getInstance().skin}"

    override val type: EntityTypes
        get() = EntityTypes.BomberEntity

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
        Bomberman.getMatch().useItem(this)
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