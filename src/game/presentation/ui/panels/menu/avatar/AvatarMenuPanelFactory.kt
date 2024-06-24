package game.presentation.ui.panels.menu.avatar

import game.data.data.DataInputOutput
import game.domain.events.models.RunnablePar
import game.utils.dev.XMLUtils
import game.utils.file_system.Paths.entitiesFolder
import game.utils.file_system.Paths.skinsXml
import java.util.function.Consumer

class AvatarMenuPanelFactory {
    fun build(): AvatarMenuPanel {
        val avatarPaths = XMLUtils.parseXmlArray(skinsXml, "skins")!!

        val getSkinRunnable: RunnablePar = object : RunnablePar {
            override fun <T> execute(par: T): Any {
                return entitiesFolder + "/player/" + DataInputOutput.getInstance().skin
            }
        }

        // Define a Consumer to handle the skin change

        // Define a Consumer to handle the skin change
        val skinChangeConsumer = Consumer { indexAdder: Int ->
            val currAvatar = getSkinRunnable.execute<Any?>(null) as String?
            var newIndex = 0
            for (i in avatarPaths.indices) {
                val avatarPath = avatarPaths[i]
                if (currAvatar!!.endsWith("/$avatarPath")) {
                    newIndex = i + indexAdder
                    break
                }
            }
            if (newIndex >= avatarPaths.size) {
                newIndex = 0
            } else if (newIndex < 0) {
                newIndex = avatarPaths.size - 1
            }
            DataInputOutput.getInstance().skin = avatarPaths[newIndex]
        }

        return AvatarMenuPanel(getSkinRunnable, skinChangeConsumer)
    }
}