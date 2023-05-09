package game.localization;

import java.util.HashMap;

public class Localization {
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
