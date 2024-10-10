package game.input.terminal.commands

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.geo.Coordinates
import game.input.terminal.TerminalCommand
import java.lang.RuntimeException
import java.util.*

class SpawnCommand: TerminalCommand {
    override fun execute(args: List<String>) {
        if (!JBomb.match.gameState)
            return

        if (args.size < 3)
            throw RuntimeException("Wrong arguments")

        val entity = args[0]
        val x = args[1].toInt()
        val y = args[2].toInt()

        val hasToFreeze = args.contains("freeze=true")
        val hasToForceCenter = !args.contains("center=false")

        val entityClass = EntityIds[entity] ?: throw RuntimeException("Entity does not exist")

        val entityToSpawn = entityClass.getConstructor(Long::class.java).newInstance(UUID.randomUUID().mostSignificantBits)
        entityToSpawn.info.position = Coordinates(x, y)


        entityToSpawn.logic.spawn(forceSpawn = true, forceCentering = hasToForceCenter)

        if (entityToSpawn is Character && hasToFreeze) {
            entityToSpawn.state.canMove = false
        }

        val actualPosition = entityToSpawn.info.position
        println("Spawning $entityClass at ${actualPosition.x}, ${actualPosition.y}")
    }
}