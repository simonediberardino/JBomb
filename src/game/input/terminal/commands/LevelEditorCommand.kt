package game.input.terminal.commands

import game.JBomb
import game.JBomb.startLevel
import game.domain.level.levels.level_editor.LevelEditor
import game.domain.level.levels.level_editor.LevelEditorData
import game.domain.match.JBombMatch.Companion.defaultPort
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.input.terminal.TerminalCommand
import game.localization.Localization
import game.network.gamehandler.ServerGameHandler
import game.utils.dev.FileUtils
import game.utils.dev.XMLUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * The {@code LevelEditorCommand} class provides functionality for starting and saving levels
 * in the game's level editor. This command allows users to load level data from an XML file
 * or start a new empty level. It also provides the ability to export the current level state
 * to an XML file, saving entity types and their positions.
 *
 * <p>
 * Available commands:
 * <ul>
 *   <li>{leveleditor start} - Starts the level editor. If the {@code -new} argument is provided, it starts a new empty level. Otherwise, it prompts the user to load an existing level from an XML file.
 *   <li>{leveleditor save} - Exports the current level state to an XML file. Entities and their coordinates are saved.
 * </ul>
 * </p>
 *
 * <p>
 * Example XML structure for exported or loaded level data:
 * <pre>{@code
 * <root>
 *   <element key="block_destroyable" x="100" y="200"/>
 *   <element key="block_destroyable" x="150" y="250"/>
 *   <element key="block_stone" x="300" y="400"/>
 * </root>
 * }</pre>
 * </p>
 *
 * <p>
 * This example shows how the XML contains entity types (e.g., block_destroyable) and their
 * respective positions on the level grid (x, y coordinates).
 * </p>
 */
class LevelEditorCommand: TerminalCommand {
    override fun execute(args: List<String>) {
        when (args.getOrNull(0)) {
            "start" -> {
                try {
                    if (JBomb.match.gameState)
                        return
                } catch (ignored: Exception) {}

                val isNew = args.contains("-new")
                val inputXml = if (isNew) null else FileUtils.selectFile(Localization.get(Localization.SELECT_LEVEL_EDITOR))

                val levelEditorData = inputXml?.let {
                    parseLevelEditorData(it)
                }

                startLevel(LevelEditor(levelEditorData), ServerGameHandler(defaultPort))
            }

            "save" -> {
                exportLevel()
            }
        }
    }

    /**
     * Exports the current level by serializing the entities and their positions to an XML file.
     * This method gathers all the entities in the level, maps them to their corresponding entity
     * keys, and saves their coordinates.
     */
    private fun exportLevel() {
        val currentLevel = JBomb.match.currentLevel

        if (currentLevel.info.worldId != -1 || currentLevel.info.levelId != 0)
            return

        val saveData: MutableMap<String, MutableList<Coordinates>> = mutableMapOf()

        JBomb.match.getEntities()
            .forEach { entity ->
                if (entity !is BomberEntity) {
                    val key = EntityIds.filterKeys {
                        EntityIds[it] == entity::class.java
                    }.keys.firstOrNull()

                    if (key != null) {
                        val coordinate = entity.info.position

                        if (key in saveData) {
                            saveData[key]?.add(coordinate)
                        } else {
                            saveData[key] = mutableListOf(coordinate)
                        }
                    }
                }
            }

        val levelEditorData = LevelEditorData(data = saveData)
        serializeLevelEditorData(levelEditorData)
    }

    /**
     * Parses the level editor data from an XML file and converts it into a {@link LevelEditorData} object.
     * The XML file is expected to contain elements with keys, and x, y coordinates representing the entity positions.
     *
     * @param filePath the path to the XML file
     * @return a {@code LevelEditorData} object containing the parsed data, or {@code null} if the file could not be parsed
     */
    private fun parseLevelEditorData(filePath: String?): LevelEditorData? {
        val document = XMLUtils.readXmlExternal(filePath)

        if (document == null) {
            System.err.println("Could not parse XML file.")
            return null
        }

        val root = document.documentElement
        val elements = root.getElementsByTagName("element") // assuming <element> tags contain the data
        val dataMap: MutableMap<String, MutableList<Coordinates>> = HashMap() // Support list of coordinates

        for (i in 0 until elements.length) {
            val element = elements.item(i) as Element
            // Assuming the XML structure is like <element key="stringKey" x="1" y="2"/>
            val key = element.getAttribute("key")
            val x = element.getAttribute("x").toInt()
            val y = element.getAttribute("y").toInt()
            val coordinates = Coordinates(x, y)

            // If key exists, add to the list; otherwise, create a new list for this key
            dataMap.computeIfAbsent(key) { mutableListOf() }.add(coordinates)
        }

        // Return LevelEditorData with the updated map structure
        return LevelEditorData(dataMap)
    }

    /**
     * Serializes the provided {@link LevelEditorData} object into an XML file.
     * The method prompts the user to choose the destination file and writes the level data
     * (entity keys and their positions) to that file.
     *
     * @param levelEditorData the {@code LevelEditorData} object containing the data to serialize
     */
    private fun serializeLevelEditorData(levelEditorData: LevelEditorData) {
        // Let the user choose the destination file
        val filePath = FileUtils.chooseDestinationFile(title = Localization.get(Localization.EXPORT_LEVEL_EDITOR))

        if (filePath == null) {
            println("No file selected. Serialization canceled.")
            return
        }

        try {
            // Create a new Document
            val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document: Document = docBuilder.newDocument()

            // Create root element
            val rootElement: Element = document.createElement("root")
            document.appendChild(rootElement)

            // Iterate over the map in LevelEditorData
            for ((key, coordinatesList) in levelEditorData.data) {
                // Iterate through the list of coordinates for each key
                for (coordinates in coordinatesList) {
                    // Create an <element> node for each coordinate
                    val elementNode: Element = document.createElement("element")

                    // Set attributes key, x, and y
                    elementNode.setAttribute("key", key)
                    elementNode.setAttribute("x", coordinates.x.toString())
                    elementNode.setAttribute("y", coordinates.y.toString())

                    // Append the element to the root node
                    rootElement.appendChild(elementNode)
                }
            }

            // Write the content into XML file
            val transformerFactory: TransformerFactory = TransformerFactory.newInstance()
            val transformer: Transformer = transformerFactory.newTransformer()
            val source = DOMSource(document)
            val result = StreamResult(File(filePath))

            transformer.transform(source, result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}