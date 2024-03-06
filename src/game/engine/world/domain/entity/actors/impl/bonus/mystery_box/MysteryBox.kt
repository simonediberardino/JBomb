package game.engine.world.domain.entity.actors.impl.bonus.mystery_box

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.level.levels.Level
import game.localization.Localization
import game.engine.ui.panels.game.PitchPanel
import game.engine.ui.viewelements.misc.ToastHandler
import game.utils.file_system.Paths
import game.utils.time.now
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import javax.swing.Timer

abstract class MysteryBox(
        protected var level: Level,
        private var buyer: Entity?
) : HardBlock(Coordinates(0, 0)) {
    private var status = Status.CLOSED
    private var lastClickInteraction: Long = 0
    abstract val price: Int

    abstract fun onPurchaseConfirm()

    override fun onMouseClickInteraction() {
        if (buyer == null)
            buyer = Bomberman.getMatch().player

        if (status == Status.OPEN) {
            return
        }

        if (!buyer!!.isSpawned) {
            return
        }

        val distanceToUser = info.position.distanceTo(buyer!!.info.position)
        if (distanceToUser >= PitchPanel.GRID_SIZE * 1.5) {
            return
        }

        val currentPoints = DataInputOutput.getInstance().score
        if (currentPoints < price) {
            showErrorMessage()
            return
        }

        if (isConfirmDelayExpired) {
            lastClickInteraction = now()
            showConfirmMessage()
            return
        }

        buy()
    }

    private fun showErrorMessage() {
        val errorMessage = Localization.get(Localization.MYSTERY_BOX_ERROR_MONEY).replace("%price%", price.toString())
        ToastHandler.getInstance().show(errorMessage)
    }

    private fun showConfirmMessage() {
        val confirmMessage = Localization.get(Localization.MYSTERY_BOX_CONFIRM).replace("%price%", price.toString())
        ToastHandler.getInstance().show(confirmMessage, false)
    }

    private val isConfirmDelayExpired: Boolean
        get() = now() - lastClickInteraction > CONFIRM_DELAY_MS

    private fun buy() {
        val currentPoints = DataInputOutput.getInstance().score
        if (currentPoints < price) {
            return
        }
        onPurchaseConfirm()
        Bomberman.getMatch().currentLevel!!.eventHandler.onPurchaseItem(price)
        openBox()
    }

    private fun openBox() {
        status = Status.OPEN
        val t = Timer(OPEN_BOX_TIME) { _: ActionEvent? -> closeBox() }
        t.isRepeats = false
        t.start()
    }

    private fun closeBox() {
        status = Status.CLOSED
    }

    override fun doInteract(e: Entity?) {}

    override val size: Int
        get() = PitchPanel.GRID_SIZE

    override fun getImage(): BufferedImage {
        return loadAndSetImage("${Paths.powerUpsFolder}/box_$status.png")
    }

    private enum class Status {
        CLOSED,
        OPEN
    }

    override val type: EntityTypes
        get() =  EntityTypes.MysteryBoxPerk

    companion object {
        private const val OPEN_BOX_TIME = 5000
        private const val CONFIRM_DELAY_MS: Long = 5000
    }
}
