package game.localization;

import java.util.HashMap;

public class Localization {
    public static HashMap<Language, String> WELCOME_TEXT = new HashMap<>() {{
        put(Language.English, "Welcome to Bomberman!");
        put(Language.Italian, "Benvenuto su Bomberman!");
    }};

    public static HashMap<Language, String> STARTING_LEVEL = new HashMap<>() {{
        put(Language.English, "STARTING %level%!");
        put(Language.Italian, "%level% INIZIATO!");
    }};

    public static HashMap<Language, String> ISLAND = new HashMap<>() {{
        put(Language.English, "ISLAND");
        put(Language.Italian, "ISOLA");
    }};

    public static HashMap<Language, String> LOADING = new HashMap<>() {{
        put(Language.English, "LOADING");
        put(Language.Italian, "CARICAMENTO");
    }};

    public static HashMap<Language, String> CONTINUE = new HashMap<>() {{
        put(Language.English, "CONTINUE");
        put(Language.Italian, "CONTINUA");
    }};

    public static HashMap<Language, String> MAIN_MENU = new HashMap<>() {{
        put(Language.English, "MAIN MENU");
        put(Language.Italian, "MENU PRINCIPALE");
    }};

    public static HashMap<Language, String> PLAY = new HashMap<>() {{
        put(Language.English, "SELECT LEVEL");
        put(Language.Italian, "SELEZIONA LIVELLO");
    }};

    public static HashMap<Language, String> PROFILE = new HashMap<>() {{
        put(Language.English, "SHOW PROFILE");
        put(Language.Italian, "VISUALIZZA PROFILO");
    }};

    public static HashMap<Language, String> QUIT = new HashMap<>() {{
        put(Language.English, "EXIT THE GAME");
        put(Language.Italian, "ESCI DAL GIOCO");
    }};

    public static String get(HashMap<Language, String> trans){
        return trans.get(Language.getLanguage());
    }
}
