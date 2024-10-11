package game.domain.level.levels.level_editor

import game.JBomb
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.LevelInfo
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.localization.Localization
import game.utils.dev.FileUtils.chooseDestinationFile
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.DocumentBuilder
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
data class LevelEditorData(
    val data: Map<String, List<Coordinates>>
)

class LevelEditor(private val levelData: LevelEditorData?): Level() {
    override val info: LevelInfo = LevelEditorLevelInfo(this)
    override val gameHandler: GameHandler = LevelEditorGameHandler(this)

    override fun endLevel() {

    }

    override fun onStartLevel() {
        levelData?.data?.forEach { (entityName, coordinates) ->
            val entityClass = EntityIds[entityName] ?: return@forEach

            coordinates.forEach { coordinate ->
                val entity = entityClass.getConstructor(Long::class.java).newInstance(UUID.randomUUID().mostSignificantBits)
                entity.logic.spawn(coordinate)
            }
        }
    }
}