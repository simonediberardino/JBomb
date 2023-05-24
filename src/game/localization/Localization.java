package game.localization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public class Localization {
    public static final HashMap<Locale, String> WELCOME_TEXT = new HashMap<>() {{
        put(Locale.ENGLISH, "Welcome to Bomberman!");
        put(Locale.ITALY, "Benvenuto su Bomberman!");
    }};

    public static final HashMap<Locale, String> PRESS_ESC_CONTINUE = new HashMap<>() {{
        put(Locale.ENGLISH, "PRESS ESCAPE TO RESUME");
        put(Locale.ITALY, "PREMI ESCAPE PER CONTINUARE");
    }};
    
    public static final HashMap<Locale, String> STARTING_LEVEL = new HashMap<>() {{
        put(Locale.ENGLISH, "STARTING %level%!");
        put(Locale.ITALY, "%level% INIZIATO!");
    }};

    public static final HashMap<Locale, String> ISLAND = new HashMap<>() {{
        put(Locale.ENGLISH, "ISLAND");
        put(Locale.ITALY, "ISOLA");
    }};

    public static final HashMap<Locale, String> YOU_DIED = new HashMap<>() {{
        put(Locale.ENGLISH, "YOU DIED! YOU HAVE %lives% LIVES LEFT.");
        put(Locale.ITALY, "SEI MORTO! HAI %lives% VITE RIMANENTI.");
    }};

    public static final HashMap<Locale, String> RESET_WORLD = new HashMap<>() {{
        put(Locale.ENGLISH, "RESET WORLD");
        put(Locale.ITALY, "RESETTA MONDO");
    }};

    public static final HashMap<Locale, String> LOADING = new HashMap<>() {{
        put(Locale.ENGLISH, "LOADING");
        put(Locale.ITALY, "CARICAMENTO");
    }};

    public static final HashMap<Locale, String> PLAY_AGAIN = new HashMap<>() {{
        put(Locale.ENGLISH, "PLAY AGAIN");
        put(Locale.ITALY, "GIOCA DI NUOVO");
    }};

    public static final HashMap<Locale, String> MAIN_MENU = new HashMap<>() {{
        put(Locale.ENGLISH, "MAIN MENU");
        put(Locale.ITALY, "MENU PRINCIPALE");
    }};

    public static final HashMap<Locale, String> PLAY = new HashMap<>() {{
        put(Locale.ENGLISH, "SELECT LEVEL");
        put(Locale.ITALY, "SELEZIONA LIVELLO");
    }};

    public static final HashMap<Locale, String> PROFILE = new HashMap<>() {{
        put(Locale.ENGLISH, "SHOW PROFILE");
        put(Locale.ITALY, "VISUALIZZA PROFILO");
    }};

    public static final HashMap<Locale, String> QUIT = new HashMap<>() {{
        put(Locale.ENGLISH, "EXIT THE GAME");
        put(Locale.ITALY, "ESCI DAL GIOCO");
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
