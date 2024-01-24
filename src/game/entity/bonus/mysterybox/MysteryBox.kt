package game.entity.bonus.mysterybox

import game.Bomberman
import game.data.DataInputOutput
import game.entity.EntityTypes
import game.entity.blocks.HardBlock
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.level.levels.Level
import game.localization.Localization
import game.ui.panels.game.PitchPanel
import game.ui.viewelements.misc.ToastHandler
import game.utils.Paths
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

        val distanceToUser = entityInfo.position.distanceTo(buyer!!.entityInfo.position)
        if (distanceToUser >= PitchPanel.GRID_SIZE * 1.5) {
            return
        }

        val currentPoints = DataInputOutput.getInstance().score
        if (currentPoints < price) {
            showErrorMessage()
            return
        }

        if (isConfirmDelayExpired) {
            lastClickInteraction = System.currentTimeMillis()
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
        get() = System.currentTimeMillis() - lastClickInteraction > CONFIRM_DELAY_MS

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
