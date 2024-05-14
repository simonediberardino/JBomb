package game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.logic

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.localization.Localization
import game.presentation.ui.panels.game.PitchPanel
import game.presentation.ui.viewelements.misc.ToastHandler
import game.utils.time.now
import java.awt.event.ActionEvent
import javax.swing.Timer

abstract class MysteryBoxLogic(override val entity: MysteryBox) : BlockEntityLogic(entity = entity) {
    override fun onCollision(e: Entity) {
        super.onCollision(e)

        if (e == entity.state.buyer() && e == JBomb.match.player)
            showInteractMessage()
    }

    override fun onExitCollision(e: Entity) {
        super.onExitCollision(e)

        if (e == entity.state.buyer() && e == JBomb.match.player)
            ToastHandler.getInstance().cancel()
    }

    override fun onTalk(entity: Entity) {
        super.onTalk(entity)

        attemptToBuy()
    }

    override fun onMouseClickInteraction() {
        val distanceToUser = entity.info.position.distanceTo(entity.state.buyer()!!.info.position)
        if (distanceToUser >= PitchPanel.GRID_SIZE * 1.5) {
            return
        }

        attemptToBuy()
    }


    private fun attemptToBuy() {
        if (entity.state.status == MysteryBox.Status.OPEN) {
            return
        }

        if (!entity.state.buyer()!!.state.isSpawned) {
            return
        }

        val currentPoints = DataInputOutput.getInstance().score
        if (currentPoints < entity.state.price) {
            showErrorMessage()
            return
        }

        buy()
    }

    abstract fun onPurchaseConfirm()

    private fun showErrorMessage() {
        val errorMessage = Localization.get(Localization.MYSTERY_BOX_ERROR_MONEY).replace("%price%", entity.state.price.toString())
        ToastHandler.getInstance().show(errorMessage)
    }

    private fun showInteractMessage() {
        val confirmMessage = Localization.get(Localization.MYSTERY_BOX_CONFIRM).replace("%price%", entity.state.price.toString())
        ToastHandler.getInstance().show(
                confirmMessage,
                true,
                false
        )
    }

    private fun showConfirmMessage() {
        val confirmMessage = Localization.get(Localization.MYSTERY_BOX_CONFIRM).replace("%price%", entity.state.price.toString())
        ToastHandler.getInstance().show(confirmMessage, false)
    }

    private val isConfirmDelayExpired: Boolean
        get() = now() - entity.state.lastClickInteraction > MysteryBox.CONFIRM_DELAY_MS

    private fun buy() {
        val currentPoints = DataInputOutput.getInstance().score
        if (currentPoints < entity.state.price) {
            return
        }

        onPurchaseConfirm()
        JBomb.match.currentLevel.eventHandler.onPurchaseItem(entity.state.price)
        openBox()
    }

    private fun openBox() {
        entity.state.status = MysteryBox.Status.OPEN
        val t = Timer(MysteryBox.OPEN_BOX_TIME) { _: ActionEvent? -> closeBox() }
        t.isRepeats = false
        t.start()
    }

    private fun closeBox() {
        entity.state.status = MysteryBox.Status.CLOSED
    }
}