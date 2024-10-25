package game.presentation.ui.viewelements.bombermanbutton

import game.values.BomberColors
import java.awt.Color


class GrayButton : BombermanButton {
    constructor(text: String) : super(text)
    constructor(text: String, fontSize: Int) : super(text, fontSize)

    override fun getBorderColor(): Color {
        return BomberColors.GRAY_LIGHT
    }

    override fun getMouseHoverBackgroundColor(): Color {
        return BomberColors.GRAY_DARK
    }
}

