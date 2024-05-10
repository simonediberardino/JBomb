package game.utils.skin

object SkinUtilities {
    fun getSkinName(id: String): String {
        return "skin$id"
    }

    fun getSkinId(name: String): Int {
        return name.replace("skin", "").toInt()
    }
}