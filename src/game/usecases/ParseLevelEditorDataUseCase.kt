package game.usecases

import game.domain.level.levels.level_editor.LevelGenerationData
import game.domain.world.domain.entity.geo.Coordinates
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.awt.Dimension

class ParseLevelEditorDataUseCase(private val document: Document): UseCase<LevelGenerationData?> {
    override suspend fun invoke(): LevelGenerationData? {
        return parseLevelEditorData(document)
    }

    /**
    * Parses the level editor data from an XML file and converts it into a {@link LevelEditorData} object.
    * The XML file is expected to contain elements with keys, and x, y coordinates representing the entity positions.
    *
    * @param filePath the path to the XML file
    * @return a {@code LevelEditorData} object containing the parsed data, or {@code null} if the file could not be parsed
    */
    private fun parseLevelEditorData(document: Document): LevelGenerationData? {

        if (document == null) {
            System.err.println("Could not parse XML file.")
            return null
        }

        val root = document.documentElement
        val elements = root.getElementsByTagName("entity") // assuming <element> tags contain the data
        val dataMap: MutableMap<String, MutableList<Coordinates>> = HashMap() // Support list of coordinates

        val size = root.getElementsByTagName("size").item(0) as Element?
        val x = size?.getAttribute("x")?.toIntOrNull()
        val y = size?.getAttribute("y")?.toIntOrNull()

        val mapDimension = if (x != null && y != null ) Dimension(x, y) else null

        for (i in 0 until elements.length) {
            val element = elements.item(i) as Element
            // Assuming the XML structure is like <element key="stringKey" x="1" y="2"/>
            val key = element.getAttribute("key")
            val coordinates = Coordinates(
                /* x = */ element.getAttribute("x").toInt(),
                /* y = */ element.getAttribute("y").toInt()
            )

            // If key exists, add to the list; otherwise, create a new list for this key
            dataMap.computeIfAbsent(key) { mutableListOf() }.add(coordinates)
        }

        // Return LevelEditorData with the updated map structure
        return LevelGenerationData(data = dataMap, mapDimension = mapDimension)
    }
}