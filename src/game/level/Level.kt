package game.level

import game.Bomberman
import game.data.DataInputOutput
import game.entity.blocks.DestroyableBlock
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.enemies.boss.Boss
import game.entity.models.BomberEntity
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.entity.models.Entity
import game.events.game.AllEnemiesEliminatedGameEvent
import game.events.game.UpdateCurrentAvailableBombsEvent
import game.level.functionalities.*
import game.level.world1.*
import game.level.world2.*
import game.powerups.PowerUp
import game.powerups.portal.EndLevelPortal
import game.sound.AudioManager
import game.sound.SoundModel
import game.ui.panels.game.PitchPanel
import game.utils.Paths.currentLevelFolder
import game.utils.Paths.currentWorldCommonFolder
import game.utils.Paths.worldsFolder
import game.utils.Utility.loadImage
import java.awt.Image
import java.lang.reflect.InvocationTargetException
import java.util.*
import javax.sound.sampled.Clip
import javax.swing.JPanel

/**
 * The abstract Level class represents the general structure and properties of a game level.
 * It includes methods that allow concrete implementations to define the type of blocks and
 * terrain that the level is composed of, as well as the length of the explosion that occurs
 * when bombs are detonated, and the background image of the level.
 */
abstract class Level {
    private var currentLevelSound: Clip? = null
    abstract val boss: Boss?
    open val bossMaxHealth: Int = 1000

    abstract val startEnemiesCount: Int
    abstract val maxDestroyableBlocks: Int
    abstract val isArenaLevel: Boolean
    abstract val diedMessage: String?
    abstract val nextLevel: Class<out Level?>?
    abstract val availableEnemies: Array<Class<out Enemy>>
    // This method returns the ID of the world.
    abstract val worldId: Int

    // This method returns the ID of the level.
    abstract val levelId: Int
    open val maxBombs: Int
        // This method returns the maximum number of bombs that a player can have at one time.
        get() = BomberEntity.MAX_BOMB_CAN_HOLD
    private val levelSoundtrack: String
        get() = getSoundForCurrentLevel("soundtrack.wav")

    private val levelBackgroundSound: String?
        get() = getSoundForCurrentLevel("background_sound.wav")

    val explosionLength: Int
        get() = DataInputOutput.getInstance().explosionLength

    val stoneBlockImagePath: String
        get() = getImageForCurrentLevel("stone.png")

    val pitchImagePath: String
        get() = getImageForCurrentLevel("pitch.png")

    val destroyableBlockImagePath: String
        get() = getImageForCurrentLevel("destroyable_block.png")

    val borderImages: Array<Image?>
        get() {
            val SIDES = 4
            val pitch = arrayOfNulls<Image>(SIDES)
            for (i in 0 until SIDES) {
                val path = getImageForCurrentLevel(String.format("border_%d.png", i))
                pitch[i] = loadImage(path)
            }
            return pitch
        }

    private val allowedPerks: Array<Class<out PowerUp>>
        get() {
            return PowerUp.POWER_UPS.filterNot { it in restrictedPerks }.toTypedArray()
        }

    open val restrictedPerks: Array<Class<out PowerUp>> = arrayOf()

    val randomPowerUpClass: Class<out PowerUp>
        get() = allowedPerks.random()

    open fun onAllEnemiesEliminated() {}
    abstract fun endLevel()
    abstract fun onDeathGameEvent()

    private fun updateLastLevel() {
        if (this !is WorldSelectorLevel)
            currLevel = this
    }

    /**
     * Starts the game level by generating the terrain and adding the player character to the game panel.
     *
     * @param jPanel the panel on which to start the game level
     */
    open fun start(jPanel: JPanel) {
        updateLastLevel()
        playSoundTrack()
        Bomberman.getMatch().gameState = true
        DataInputOutput.getInstance().resetLivesIfNecessary()
        generateEntities(jPanel)
        playLevelSound()
    }

    fun playSoundTrack() {
        AudioManager.getInstance().stopBackgroundSong()
        AudioManager.getInstance().playBackgroundSong(levelSoundtrack)
    }

    open fun generateEntities(jPanel: JPanel) {
        generateStone(jPanel)
        generatePlayer()
        startLevel()
    }

    open fun startLevel() {
        generateDestroyableBlock()
        spawnBoss()
        spawnEnemies()
    }

    private fun generateStone(jPanel: JPanel) {
        GenerateStoneUseCase(jPanel).invoke()
    }

    private fun generatePlayer() {
        GeneratePlayerUseCase().invoke()
    }

    protected open fun spawnBoss() {
        SpawnBossUseCase(boss ?: return).invoke()
    }

    // This method spawns enemies in the game.
    protected open fun spawnEnemies() {
        spawnEnemies(availableEnemies)
    }

    protected fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>) {
        SpawnEnemiesUseCase(startEnemiesCount, availableEnemies).invoke()
    }

    private fun playLevelSound() {
        val soundPath = levelBackgroundSound
        val classLoader = Thread.currentThread().contextClassLoader
        if (soundPath == null)
            return

        // Attempt to load the resource as an InputStream
        val soundStream = classLoader.getResourceAsStream(soundPath)
        if (soundStream != null) {
            // If the resource exists, play it using AudioManager
            currentLevelSound = AudioManager.getInstance().play(soundPath, true)
        }
    }

    fun stopLevelSound() {
        if (currentLevelSound == null) {
            return
        }
        currentLevelSound!!.stop()
    }

    fun spawnMisteryBox() {
        val player = Bomberman.getMatch().player
        val c = Coordinates.generateCoordinatesAwayFrom(player.coords, PitchPanel.GRID_SIZE * 2)
        val mysteryBox: Entity = MysteryBoxPerk(this, player)
        mysteryBox.coords = c
        mysteryBox.spawn()
    }

    // This method generates destroyable blocks in the game board.
    open fun generateDestroyableBlock() {
        // Despawn all the previous destroyable blocks;
        DespawnDestroyableBlocks().invoke()
        GenerateDestroyableBlocksUseCase(this).invoke()
    }

    private fun getImageForCurrentLevel(path: String?): String = getFileForCurrentLevel("images/$path")

    private fun getSoundForCurrentLevel(path: String?): String {
        return getFileForCurrentLevel("sound/$path")
    }

    /**
     * @return returns the path to the file: if a specific instance of the file exists for the current level, then return it, else return the current world instance;
     */
    private fun getFileForCurrentLevel(path: String): String {
        val classLoader = Thread.currentThread().contextClassLoader

        fun getResourcePath(folder: String): String? {
            val stream = classLoader.getResourceAsStream("$folder/$path")
            return stream?.let { "$folder/$path" }
        }

        return getResourcePath(currentLevelFolder)
                ?: getResourcePath(currentWorldCommonFolder)
                ?: getResourcePath("common")
                ?: "$worldsFolder/common/$path"
    }

    open val isLastLevelOfWorld: Boolean = false

    fun onDefeatGameEvent() {
        DataInputOutput.getInstance().increaseLost()
    }

    fun onEnemyDespawned() {
        if (Bomberman.getMatch().enemiesAlive == 0) {
            AllEnemiesEliminatedGameEvent().invoke(null)
        }
    }

    fun onKilledEnemy() {
        DataInputOutput.getInstance().increaseKills()
    }

    open fun onRoundPassedGameEvent() {
        DataInputOutput.getInstance().increaseRounds()
    }

    fun onScoreGameEvent(arg: Int) {
        DataInputOutput.getInstance().increaseScore(arg)
        Bomberman.getMatch().inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    fun onPurchaseItem(price: Int) {
        AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        DataInputOutput.getInstance().decreaseScore(price)
        Bomberman.getMatch().inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    fun onUpdateCurrentAvailableBombsEvent(arg: Int) {
        Bomberman.getMatch().player.currentBombs = arg
    }

    open fun onUpdateMaxBombsGameEvent(arg: Int) {
        DataInputOutput.getInstance().increaseObtainedBombs()
        UpdateCurrentAvailableBombsEvent().invoke(arg)
    }

    open fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int) {
        entity.currExplosionLength = arg
        DataInputOutput.getInstance().explosionLength = arg
    }

    override fun toString(): String {
        return "Level $levelId, World $worldId"
    }

    companion object {
        val ID_TO_FIRST_LEVEL_MAP: Map<Int, Class<out Level>> = mapOf(
                1 to World1Level1::class.java,
                2 to World2Level1::class.java
        )

        val ID_TO_LEVEL: Map<Array<Int>, Class<out Level>> = mapOf(
                arrayOf(1, 1) to World1Level1::class.java,
                arrayOf(1, 2) to World1Level2::class.java,
                arrayOf(1, 3) to World1Level3::class.java,
                arrayOf(1, 4) to World1Level4::class.java,
                arrayOf(1, 5) to World1Level5::class.java,
                arrayOf(2, 1) to World2Level1::class.java,
                arrayOf(2, 2) to World2Level2::class.java,
                arrayOf(2, 3) to World2Level3::class.java,
                arrayOf(2, 4) to World2Level4::class.java,
                arrayOf(2, 5) to World2Level5::class.java
        )

        var currLevel: Level? = null
            private set
    }
}