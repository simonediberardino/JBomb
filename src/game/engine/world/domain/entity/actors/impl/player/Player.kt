package game.engine.world.domain.entity.actors.impl.player

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.events.game.DeathGameEvent
import game.engine.events.game.UpdateCurrentAvailableItemsEvent
import game.engine.events.game.UpdateCurrentBombsLengthEvent
import game.engine.hardwareinput.Command
import game.engine.sound.SoundModel
import game.engine.ui.pages.GameOverPanel
import game.utils.Log
import game.utils.Paths.entitiesFolder
import java.awt.event.ActionEvent
import javax.swing.Timer

class Player : BomberEntity {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

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
        Log.i(maxBombs)
        UpdateCurrentAvailableItemsEvent().invoke(maxBombs)
        UpdateCurrentBombsLengthEvent().invoke(DataInputOutput.getInstance().explosionLength)
    }
}