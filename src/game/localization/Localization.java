package game.localization;

import game.JBomb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public class Localization {
    public static final HashMap<Locale, String> MY_BOMBERMAN = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "MY JBOMBER");
        put(Locale.ITALY, "IL MIO JBOMBER");
    }};

    public static final HashMap<Locale, String> KEY_RIGHT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "RIGHT");
        put(Locale.ITALY, "DESTRA");
    }};

    public static final HashMap<Locale, String> KEY_LEFT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LEFT");
        put(Locale.ITALY, "SINISTRA");
    }};

    public static final HashMap<Locale, String> KEY_FORWARD = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "FORWARD");
        put(Locale.ITALY, "AVANTI");
    }};

    public static final HashMap<Locale, String> KEY_BACK = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "BACK");
        put(Locale.ITALY, "INDIETRO");
    }};

    public static final HashMap<Locale, String> KEY_BOMB = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "BOMB");
        put(Locale.ITALY, "BOMBA");
    }};

    public static final HashMap<Locale, String> KEY_INTERACT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "INTERACT");
        put(Locale.ITALY, "INTERAGISCI");
    }};

    public static final HashMap<Locale, String> DEATHS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "DEATHS");
        put(Locale.ITALY, "MORTI");
    }};

    public static final HashMap<Locale, String> PLAYER_STATS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAYER STATS");
        put(Locale.ITALY, "STATISTICHE");
    }};

    public static final HashMap<Locale, String> LOST_GAMES = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LOST GAMES");
        put(Locale.ITALY, "PARTITE PERSE");
    }};

    public static final HashMap<Locale, String> KILLS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "KILLS");
        put(Locale.ITALY, "UCCISIONI");
    }};

    public static final HashMap<Locale, String> ROUNDS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "ROUNDS PASSED");
        put(Locale.ITALY, "ROUND VINTI");
    }};

    public static final HashMap<Locale, String> POINTS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "POINTS");
        put(Locale.ITALY, "PUNTI");
    }};

    public static final HashMap<Locale, String> LIVES = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LIVES");
        put(Locale.ITALY, "VITE");
    }};

    public static final HashMap<Locale, String> WELCOME_TEXT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Welcome %user% to JBOMB!");
        put(Locale.ITALY, "Benvenuto %user% su JBOMB!");
    }};

    public static final HashMap<Locale, String> WELCOME_TEXT_ANONYMOUS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Welcome to JBOMB!");
        put(Locale.ITALY, "Benvenuto su JBOMB!");
    }};

    public static final HashMap<Locale, String> REGISTRATION_USERNAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Choose your username");
        put(Locale.ITALY, "Scegli il tuo nome");
    }};

    public static final HashMap<Locale, String> REGISTRATION_AVATAR = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Choose your avatar");
        put(Locale.ITALY, "Scegli il tuo avatar");
    }};
    public static final HashMap<Locale, String> NEEDS_UPDATE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "JBomb needs an update!");
        put(Locale.ITALY, "JBomb ha bisogno di un aggiornamento!");
    }};

    public static final HashMap<Locale, String> MYSTERY_BOX_ERROR_MONEY = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU DON'T HAVE ENOUGH COINS [%price%]");
        put(Locale.ITALY, "NON HAI ABBASTANZA MONETE [%price%]");
    }};


    public static final HashMap<Locale, String> MYSTERY_BOX_CONFIRM = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "INTERACT TO PURCHASE [%price%]");
        put(Locale.ITALY, "INTERAGISCI PER COMPRARE [%price%]");
    }};


    public static final HashMap<Locale, String> PRESS_ESC_CONTINUE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PRESS ESCAPE TO RESUME");
        put(Locale.ITALY, "PREMI ESCAPE PER CONTINUARE");
    }};

    public static final HashMap<Locale, String> STARTING_LEVEL = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "STARTING %level%!");
        put(Locale.ITALY, "%level% INIZIATO!");
    }};

    public static final HashMap<Locale, String> WORLD_NAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "WORLD %world_id% LEVEL %level_id%");
        put(Locale.ITALY, "MONDO %world_id% LIVELLO %level_id%");
    }};

    public static final HashMap<Locale, String> LEVEL_EDITOR_NAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LEVEL EDITOR");
        put(Locale.ITALY, "EDITOR LIVELLI");
    }};

    public static final HashMap<Locale, String> ARENA_NAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "ARENA WORLD %world_id%");
        put(Locale.ITALY, "ARENA MONDO %world_id%");
    }};

    public static final HashMap<Locale, String> ISLAND = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "ISLAND");
        put(Locale.ITALY, "ISOLA");
    }};

    public static final HashMap<Locale, String> MP_DRACULAS_CASTLE_UI_NAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "DRACULA'S CASTLE");
        put(Locale.ITALY, "CASTELLO DI DRACULA");
    }};

    public static final HashMap<Locale, String> YOU_DIED_BASE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU DIED!");
        put(Locale.ITALY, "SEI MORTO!");
    }};

    public static final HashMap<Locale, String> YOU_KILLED = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU KILLED %name%!");
        put(Locale.ITALY, "HAI UCCISO %name%!");
    }};

    public static final HashMap<Locale, String> YOU_DIED = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU DIED! YOU HAVE %lives% LIVES LEFT.");
        put(Locale.ITALY, "SEI MORTO! HAI %lives% VITE RIMANENTI.");
    }};

    public static final HashMap<Locale, String> ARENA_DIED = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU DIED! YOU SURVIVED %rounds% ROUNDS.");
        put(Locale.ITALY, "SEI MORTO! SEI SOPRAVVISSUTO %rounds% ROUND.");
    }};

    public static final HashMap<Locale, String> RESET_WORLD = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "RESET WORLD");
        put(Locale.ITALY, "RESETTA MONDO");
    }};

    public static final HashMap<Locale, String> LOADING = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LOADING");
        put(Locale.ITALY, "CARICAMENTO");
    }};

    public static final HashMap<Locale, String> LOADING_INIT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "LOADING GAME...");
        put(Locale.ITALY, "STO CARICANDO IL GIOCO...");
    }};

    public static final HashMap<Locale, String> PLAY_AGAIN = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAY AGAIN");
        put(Locale.ITALY, "GIOCA DI NUOVO");
    }};

    public static final HashMap<Locale, String> MAIN_MENU = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "MAIN MENU");
        put(Locale.ITALY, "MENU PRINCIPALE");
    }};

    public static final HashMap<Locale, String> BACK = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "BACK");
        put(Locale.ITALY, "INDIETRO");
    }};

    public static final HashMap<Locale, String> CONNECT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CONNECT");
        put(Locale.ITALY, "CONNETTITI");
    }};


    public static final HashMap<Locale, String> PLAY = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAY");
        put(Locale.ITALY, "GIOCA");
    }};

    public static final HashMap<Locale, String> PLAY_CAMPAIGN = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAY STORYMODE");
        put(Locale.ITALY, "GIOCA CAMPAGNA");
    }};

    public static final HashMap<Locale, String> PLAY_MP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAY MULTIPLAYER");
        put(Locale.ITALY, "GIOCA MULTIPLAYER");
    }};

    public static final HashMap<Locale, String> PLAY_PVP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CREATE DEATHMATCH");
        put(Locale.ITALY, "CREA DEATHMATCH");
    }};

    public static final HashMap<Locale, String> START_ARENA = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CREATE ARENA GAME");
        put(Locale.ITALY, "CREA PARTITA ARENA");
    }};

    public static final HashMap<Locale, String> SERVERS_LIST_INPUT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "IP ADDRESS");
        put(Locale.ITALY, "INDIRIZZO IP");
    }};

    public static final HashMap<Locale, String> CONNECT_DIRECTLY = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "DIRECT CONNECT");
        put(Locale.ITALY, "CONNESSIONE DIRETTA");
    }};

    public static final HashMap<Locale, String> SERVERS_BROWSER = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "SERVER BROWSER");
        put(Locale.ITALY, "LISTA SERVER");
    }};

    public static final HashMap<Locale, String> INSERT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "INSERT…");
        put(Locale.ITALY, "INSERISCI…");
    }};

    public static final HashMap<Locale, String> PLAY_ONLINE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "SERVER BROWSER");
        put(Locale.ITALY, "LISTA SERVER");
    }};
    public static final HashMap<Locale, String> PROFILE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "SHOW PROFILE");
        put(Locale.ITALY, "VISUALIZZA PROFILO");
    }};

    public static final HashMap<Locale, String> QUIT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "EXIT THE GAME");
        put(Locale.ITALY, "ESCI DAL GIOCO");
    }};

    public static final HashMap<Locale, String> UPDATE_TUTORIAL = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOUR GAME IS NOT UP TO DATE. CLOSE JBOMB AND RUN THE JBOMB UPDATER PROGRAM.");
        put(Locale.ITALY, "PER AGGIORNARE IL GIOCO, ESEGUI IL SOFTWARE DI AGGIORNAMENTO DI JBOMB.");
    }};

    public static final HashMap<Locale, String> SERVERS_LIST_TITLE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CONNECT TO A GAME");
        put(Locale.ITALY, "PARTECIPA A UNA PARTITA");
    }};

    public static final HashMap<Locale, String> RESUME_GAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "RESUME GAME");
        put(Locale.ITALY, "CONTINUA LA PARTITA");
    }};

    public static final HashMap<Locale, String> QUIT_GAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "QUIT THE MATCH");
        put(Locale.ITALY, "ESCI DALLA PARTITA");
    }};

    public static final HashMap<Locale, String> USERNAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "USERNAME");
        put(Locale.ITALY, "USERNAME");
    }};

    public static final HashMap<Locale, String> GAME_ENDED_MP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "GAME OVER!");
        put(Locale.ITALY, "PARTITA TERMINATA!");
    }};

    public static final HashMap<Locale, String> CONTINUE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CONTINUE");
        put(Locale.ITALY, "CONTINUA");
    }};

    public static final HashMap<Locale, String> SETTINGS = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "SETTINGS");
        put(Locale.ITALY, "IMPOSTAZIONI");
    }};

    public static final HashMap<Locale, String> UPDATE = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "UPDATE GAME");
        put(Locale.ITALY, "AGGIORNA GIOCO");
    }};

    public static final HashMap<Locale, String> PLAYER = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "PLAYER");
        put(Locale.ITALY, "GIOCATORE");
    }};

    public static final HashMap<Locale, String> CONNECTING = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "CONNECTING...");
        put(Locale.ITALY, "CONNESSIONE...");
    }};

    public static final HashMap<Locale, String> APP_NAME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "JBOMB");
        put(Locale.ITALY, "JBOMB");
    }};

    public static final HashMap<Locale, String> STARTING_ROUND = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "STARTING ROUND %round%");
        put(Locale.ITALY, "INIZIA IL ROUND %round%");
    }};

    public static final HashMap<Locale, String> AUDIO_VOLUME = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "AUDIO");
        put(Locale.ITALY, "AUDIO");
    }};

    public static final HashMap<Locale, String> ARENA_WORLD_1 = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "ARENA WORLD 1");
        put(Locale.ITALY, "ARENA MONDO 1");
    }};

    public static final HashMap<Locale, String> ARENA_WORLD_2 = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "ARENA WORLD 2");
        put(Locale.ITALY, "ARENA MONDO 2");
    }};

    public static final HashMap<Locale, String> AVATAR = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "AVATAR");
        put(Locale.ITALY, "AVATAR");
    }};

    public static final HashMap<Locale, String> WAITING_ROOM = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "WAITING ROOM");
        put(Locale.ITALY, "SALA D'ATTESA");
    }};

    public static final HashMap<Locale, String> GAME_ENDED = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "GAME ENDED");
        put(Locale.ITALY, "PARTITA TERMINATA");
    }};

    public static final HashMap<Locale, String> ERROR_NETWORK_DEFAULT = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "YOU HAVE BEEN DISCONNECTED.\n\nERROR: %error%.");
        put(Locale.ITALY, "SEI STATO DISCONNESSO.\n\nERRORE: %error%.");
    }};

    public static final HashMap<Locale, String> ARMOR_POWER_UP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "invulnerability");
        put(Locale.ITALY, "invincibilità");
    }};

    public static final HashMap<Locale, String> BLOCK_MOVER_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "block-mover");
        put(Locale.ITALY, "spostablocchi");
    }};

    public static final HashMap<Locale, String> FIRE_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "fire power");
        put(Locale.ITALY, "potere di fuoco");
    }};

    public static final HashMap<Locale, String> HAMMER_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "hammer");
        put(Locale.ITALY, "martello");
    }};

    public static final HashMap<Locale, String> BOMBS_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "bomb");
        put(Locale.ITALY, "bomba");
    }};

    public static final HashMap<Locale, String> HP_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "health");
        put(Locale.ITALY, "salute");
    }};

    public static final HashMap<Locale, String> PISTOL_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "pistol");
        put(Locale.ITALY, "pistola");
    }};

    public static final HashMap<Locale, String> RC_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "bomb remote control");
        put(Locale.ITALY, "telecomando bombe");
    }};

    public static final HashMap<Locale, String> SPEED_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "speed");
        put(Locale.ITALY, "velocità");
    }};

    public static final HashMap<Locale, String> TRANSPARENT_BOMBS_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "bomb passage");
        put(Locale.ITALY, "passabomba");
    }};

    public static final HashMap<Locale, String> TRANSPARENT_BLOCKS_POWERUP = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "block passage");
        put(Locale.ITALY, "passablocchi");
    }};

    public static final HashMap<Locale, String> POWERUP_FOUND = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "New power up: %powerup%");
        put(Locale.ITALY, "Nuovo power up: %powerup%");
    }};

    public static final HashMap<Locale, String> SELECT_LEVEL_EDITOR = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Import a level file");
        put(Locale.ITALY, "Importa un file di un livello");
    }};

    public static final HashMap<Locale, String> EXPORT_LEVEL_EDITOR = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Export a level file");
        put(Locale.ITALY, "Esporta un file di un livello");
    }};

    public static final HashMap<Locale, String> WAIT_FOR_HOST = new HashMap<Locale, String>() {{
        put(Locale.ENGLISH, "Wait for host");
        put(Locale.ITALY, "Attendi l'host");
    }};

    public static Locale[] getSupportedLanguages() {
        return new Locale[]{
                Locale.ENGLISH,
                Locale.ITALY
        };
    }

    public static String get(HashMap<Locale, String> localizationMap) {
        Optional<Locale> locale = Arrays.stream(getSupportedLanguages()).filter(l -> l.getLanguage().equals(Locale.getDefault().getLanguage())).findFirst();
        return localizationMap.get(locale.orElse(Locale.ENGLISH));
    }
}
