package game.match

import game.Bomberman
import game.data.DataInputOutput
import game.data.SortedLinkedList
import game.entity.player.Player
import game.entity.placeable.Bomb
import game.entity.player.BomberEntity
import game.entity.models.Entity
import game.hardwareinput.ControllerManager
import game.hardwareinput.ControllerManager.Companion.setDefaultCommandDelay
import game.hardwareinput.MouseControllerManager
import game.items.BombItem
import game.items.UsableItem
import game.level.levels.Level
import game.actorbehavior.PlayLevelSoundTrackBehavior
import game.http.events.forward.UseItemHttpEventForwarder
import game.level.online.ClientGameHandler
import game.level.online.OnlineGameHandler
import game.level.online.ServerGameHandler
import game.tasks.GamePausedObserver
import game.tasks.GameTickerObservable
import game.ui.pages.PausePanel
import game.ui.panels.game.MatchPanel
import game.utils.Utility.timePassed
import game.viewcontrollers.*
import java.util.*

class BomberManMatch(var currentLevel: Level?, val onlineGameHandler: OnlineGameHandler?) {
    // Timestamp of the last game pause state
    private var lastGamePauseStateTime = System.currentTimeMillis()

    // List of entities sorted by a linked list
    private val _entities: SortedLinkedList<Entity> = SortedLinkedList()

    // Manager for mouse controllers
    val mouseControllerManager: MouseControllerManager = MouseControllerManager()

    // Manager for general controllers (nullable)
    var controllerManager: ControllerManager? = ControllerManager()
        private set

    // Observable for game tick events (nullable)
    var gameTickerObservable: GameTickerObservable? = GameTickerObservable()
        private set

    // Controllers for inventory elements (lateinit and nullable)
    lateinit var inventoryElementControllerPoints: InventoryElementController
        private set
    lateinit var inventoryElementControllerBombs: InventoryElementController
        private set
    lateinit var inventoryElementControllerLives: InventoryElementController
        private set
    var inventoryElementControllerRounds: InventoryElementController? = null
        private set

    // Player information (nullable)
    var player: Player? = null

    // List of bombs in the game
    val bombs = ArrayList<Bomb>()

    // Current game state (default: false)
    var gameState = false

    // Number of enemies currently alive (read-only)
    var enemiesAlive = 0
        private set

    private constructor() : this(null, null)

    init {
        controllerManager?.register(GamePausedObserver())
        setupViewControllers()
        setDefaultCommandDelay()
        println("${javaClass.simpleName}, onlineGameHandler: $onlineGameHandler, ${onlineGameHandler?.isRunning()}")

        if (onlineGameHandler?.isRunning() != true) {
            onlineGameHandler?.onStart()
        }
    }

    fun assignPlayerToControllerManager() {
        controllerManager!!.player = player
    }

    /**
     * Sets up various view controllers based on the current game state.
     */
    private fun setupViewControllers() {
        // Set up points and bombs controllers
        setupPointsController()
        setupBombsController()

        // Set up rounds or lives controller based on the game level type
        if (currentLevel!!.info.isArenaLevel) {
            setupRoundsController()
        } else {
            setupLivesController()
        }

        // Update the inventory weapon controller with the current player's weapon information
        updateInventoryWeaponController()
    }

    /**
     * Sets up the points controller with the current score from DataInputOutput.
     */
    private fun setupPointsController() {
        inventoryElementControllerPoints = InventoryElementControllerPoints()
        inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    /**
     * Sets up the bombs controller.
     */
    private fun setupBombsController() {
        inventoryElementControllerBombs = InventoryElementControllerBombs()
    }

    /**
     * Sets up the rounds controller for arena levels.
     */
    private fun setupRoundsController() {
        inventoryElementControllerRounds = InventoryElementControllerRounds()
    }

    /**
     * Sets up the lives controller for non-arena levels.
     */
    private fun setupLivesController() {
        inventoryElementControllerLives = InventoryElementControllerLives()
        inventoryElementControllerLives.setNumItems(DataInputOutput.getInstance().lives)
    }


    /**
     * Gives an item to the specified owner (BomberEntity).
     *
     * @param owner The BomberEntity receiving the item.
     * @param item The UsableItem to be given.
     * @param combineSameItem Flag indicating whether to combine items of the same type.
     */
    fun give(owner: BomberEntity, item: UsableItem, combineSameItem: Boolean = false) {
        if (combineSameItem && owner.weapon.javaClass == item.javaClass) {
            // Combine items if requested and owner already has the same type of item
            owner.weapon.combineItems(item)
        } else {
            // Set the new item as the owner's weapon and update related components
            owner.weapon = item
            owner.weapon.owner = owner
            updateInventoryWeaponController()
        }
    }

    fun useItem(owner: BomberEntity) {
        owner.weapon.use()
        UseItemHttpEventForwarder().invoke(owner.toDao(), owner.weapon.type)
    }


    /**
     * Removes the current item from the specified owner (BomberEntity) and replaces it with a BombItem.
     *
     * @param owner The BomberEntity from which to remove the item.
     */
    fun removeItem(owner: BomberEntity) {
        // Replace the current item with a BombItem and update related components
        owner.weapon = BombItem()
        owner.weapon.owner = owner
        updateInventoryWeaponController()
    }

    /**
     * Updates the inventory weapon controller with the current player's weapon information.
     * Does nothing if the player is not set.
     */
    fun updateInventoryWeaponController() {
        player ?: return
        val playerItem = player!!.weapon ?: return
        inventoryElementControllerBombs.setImagePath(playerItem.imagePath)
        inventoryElementControllerBombs.setNumItems(playerItem.count)
    }

    /**
     * Checks if the game is running in client mode.
     *
     * @return True if the client game handler is not null and connected, false otherwise.
     */
    val isClient: Boolean
        get() = onlineGameHandler is ClientGameHandler /*&& onlineGameHandler.connected*/

    /**
     * Checks if the game is running in server mode.
     *
     * @return True if the server game handler is not null and running, false otherwise.
     */
    val isServer: Boolean
        get() = onlineGameHandler is ServerGameHandler || onlineGameHandler == null /*&& onlineGameHandler.running*/

    /**
     * Adds a bomb to the list of bombs in the game.
     *
     * @param bomb The Bomb to be added.
     */
    fun addBomb(bomb: Bomb) {
        bombs.add(bomb)
    }

    /**
     * Removes a bomb from the list of bombs in the game.
     *
     * @param bomb The Bomb to be removed.
     */
    fun removeBomb(bomb: Bomb) {
        bombs.remove(bomb)
    }

    /**
     * Returns a copy of the list of _entities to prevent external modifications.
     */
    fun getEntities(): List<Entity> = synchronized(_entities) { LinkedList(_entities) }

    fun getEntityById(entityId: Long): Entity? {
        return getEntities().firstOrNull { it.id == entityId }
    }

    fun addEntity(entity: Entity) {
        synchronized(_entities) {
            _entities.add(entity)
        }
        System.gc()
    }

    fun removeEntity(entity: Entity) {
        synchronized(_entities) {
            _entities.removeIf { it.id == entity.id }
        }
        System.gc()
    }

    /**
     * Toggles the game state between paused and resumed.
     */
    fun toggleGameState() {
        if (timePassed(lastGamePauseStateTime) < 500) {
            return
        }

        lastGamePauseStateTime = System.currentTimeMillis()

        if (gameState) {
            pauseGame()
        } else {
            resumeGame()
        }
    }


    /**
     * Pauses the game by stopping the game ticker, setting the game state to false,
     * and displaying the pause panel.
     */
    private fun pauseGame() {
        // Stop the game ticker to pause game events
        gameTickerObservable?.stop()

        // Update the game state to indicate it is paused
        gameState = false

        // Show the pause panel to the player
        Bomberman.showActivity(PausePanel::class.java)
    }

    /**
     * Resumes the game by resuming the game ticker, setting the game state to true,
     * showing the match panel, and playing the level soundtrack.
     */
    private fun resumeGame() {
        // Resume the game ticker to continue game events
        gameTickerObservable?.resume()

        // Update the game state to indicate it is resumed
        gameState = true

        // Show the match panel to the player
        Bomberman.showActivity(MatchPanel::class.java)

        // Play the level soundtrack
        PlayLevelSoundTrackBehavior(currentLevel ?: return).invoke()
    }

    /**
     * Decreases the count of enemies currently alive in the game.
     */
    fun decreaseEnemiesAlive() {
        enemiesAlive--
    }

    /**
     * Increases the count of enemies currently alive in the game.
     */
    fun increaseEnemiesAlive() {
        enemiesAlive++
    }

    /**
     * Performs cleanup operations and releases resources associated with the game.
     */
    fun destroy() {
        // Pause the game to ensure safe destruction
        pauseGame()

        // Destroy all _entities in the game
        destroy_entities()

        // Clear graphics callback in the Bomberman frame's pitch panel
        clearGraphicsCallback()

        // Stop the sound associated with the current game level
        stopLevelSound()

        // Reset the game state variables
        resetState()

        // Stop the movement task for mouse controllers
        stopMovementTask()

        // Unregister all observables and controllers
        unregisterAllObservablesAndControllers()

        // Perform garbage collection to release memory
        performGarbageCollection()
    }

    /**
     * Destroys all _entities in the game by despawning them.
     */
    private fun destroy_entities() {
        getEntities().forEach { it.despawnAndNotify() }
    }

    /**
     * Clears the graphics callback in the Bomberman frame's pitch panel.
     */
    private fun clearGraphicsCallback() {
        Bomberman.getBombermanFrame().pitchPanel.clearGraphicsCallback()
    }

    /**
     * Stops the sound associated with the current game level.
     */
    private fun stopLevelSound() {
        currentLevel?.currentLevelSound?.stop()
    }

    /**
     * Resets the game state variables, setting them to default values or null.
     */
    private fun resetState() {
        player = null
        currentLevel = null
        enemiesAlive = 0
        _entities.clear()
    }

    /**
     * Stops the movement task for mouse controllers.
     */
    private fun stopMovementTask() {
        mouseControllerManager.stopMovementTask()
    }

    /**
     * Unregisters all observables and controllers associated with the game.
     */
    private fun unregisterAllObservablesAndControllers() {
        gameTickerObservable?.unregisterAll()
        gameTickerObservable = null

        controllerManager?.unregisterAll()
        controllerManager = null
    }

    /**
     * Initiates the garbage collection process to release unused memory.
     */
    private fun performGarbageCollection() {
        System.gc()
    }
}
