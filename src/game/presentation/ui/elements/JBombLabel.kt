package game.presentation.ui.elements

import game.presentation.ui.fonts.CustomFonts
import game.values.Dimensions
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel

class JBombLabel(
        text: String,
        constants: Int = CENTER,
        fontSize: Int = Dimensions.FONT_SIZE_MID
) : JLabel(
        text,
        constants
) {
    constructor(text: String) : this(text, CENTER)

    init {
        isOpaque = false
        foreground = Color.WHITE
        font = CustomFonts.getFont(CustomFonts.FONT_PIXELATED, fontSize.toFloat())
        border = javax.swing.BorderFactory.createEmptyBorder()
    }
}