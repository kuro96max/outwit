package edu.byuh.cis.cs203.outwit203_preferences.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import edu.byuh.cis.cs203.outwit203_preferences.Team;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.ClassicTheme;
import edu.byuh.cis.cs203.outwit203_preferences.R;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.DarkTheme;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.LightTheme;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.SpecialTheme;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.Theme;

public class Prefs extends AppCompatActivity {

    public static final String MUSIC_PREFS_KEY = "MUSIC_PREFS_KEY";
    public static final String ANIMATION_PREFS_KEY = "ANIMATION_PREFS_KEY";
    public static final String WHO_GOES_FIRST_PREFS_KEY = "WHO_GOES_FIRST_PREFS_KEY";
    public static final String ANIMATION_SPEED_PREFS_KEY = "ANIMATION_SPEED_PREFS_KEY";
    public static final String CHIPSET_PREFS_KEY = "CHIPSET_PREFS_KEY";
    public static final String CHIP_LAYOUT_PREFS_KEY = "CHIP_LAYOUT_PREFS_KEY";
    public static final String THEME_LAYOUT_PREFS_KEY = "THEME_LAYOUT_PREFS_KEY";
    public static final String PLAYER_LAYOUT_PREFS_KEY = "PLAYER_LAYOUT_PREFS_KEY";

    public static final int DARK_FIRST = 1;
    public static final int LIGHT_FIRST = 2;
    public static final int RANDOM_FIRST = 3;

    public static final int STANDARD_CHIPSET = 1;
    public static final int ALL_POWER = 2;
    public static final int ALL_REGULAR = 3;

    public static final int STANDARD_LAYOUT = 1;
    public static final int CENTRALIZED_LAYOUT = 2;
    public static final int RANDOMIZED_LAYOUT = 3;
    public static final int CLASSIC_THEME = 1;
    public static final int DARK_THEME = 2;
    public static final int LIGHT_THEME = 3;
    public static final int SPECIAL_THEME = 4;
    public static final int TWO_PLAYER = 1;
    public static final int AI_LIGHT = 2;
    public static final int AI_DARK = 3;


    /**
     * Auto-generated code. I take no responsibility for it.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return true if music is desired; false otherwise
     */
    public static boolean getMusicPrefs(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(MUSIC_PREFS_KEY, false);
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return true if animation is desired; false otherwise
     */
    public static boolean getAnimationPrefs(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(ANIMATION_PREFS_KEY, true);
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return DARK_FIRST, LIGHT_FIRST, or RANDOM_FIRST
     */
    public static int whoGoesFirst(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(WHO_GOES_FIRST_PREFS_KEY, ""+RANDOM_FIRST);
        return Integer.parseInt(tmp);
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return number of milliseconds between animation frames: 10 (fast), 50 (medium), or 100 (slow)
     */
    public static int animationSpeed(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(ANIMATION_SPEED_PREFS_KEY, "10");
        return Integer.parseInt(tmp);
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return STANDARD_CHIPSET, ALL_POWER, or ALL_REGULAR
     */
    public static int chipset(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(CHIPSET_PREFS_KEY, ""+STANDARD_CHIPSET);
        return Integer.parseInt(tmp);
    }

    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return STANDARD_LAYOUT, CENTRALIZED_LAYOUT, or RANDOMIZED_LAYOUT
     */
    public static int chipLayout(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(CHIP_LAYOUT_PREFS_KEY, "1");
        return Integer.parseInt(tmp);
    }
    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return ClassicTheme, DarkTheme, LightTheme, or SpecialTheme
     */
    public static Theme getTheme(Context c){
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(THEME_LAYOUT_PREFS_KEY, ""+CLASSIC_THEME);
        return switch (tmp){
            case ""+DARK_THEME -> new DarkTheme();
            case ""+LIGHT_THEME -> new LightTheme();
            case ""+SPECIAL_THEME -> new SpecialTheme();
            default -> new ClassicTheme();
        };
    }
    /**
     * Helper "façade" method to simplify access to Android's obtuse Preferences API
     * @param c a context object, typically the main activity
     * @return , Team.LIGHT, Team.Dark, Team.NEUTRAL
     */
    public static Team aiComp(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(PLAYER_LAYOUT_PREFS_KEY, ""+TWO_PLAYER);
        return switch (tmp){
            case ""+AI_LIGHT -> Team.LIGHT;
            case ""+AI_DARK -> Team.DARK;
            default -> Team.NEUTRAL;
        };
    }

    /**
     * Auto-generated static nested class.
     * Our job is to fill out the onCreatePreferences method.
     */
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle b, String s) {
            var context = getPreferenceManager().getContext();
            var screen = getPreferenceManager().createPreferenceScreen(context);

            //add preference widgets here
            var music = new SwitchPreference(context);
            music.setTitle(R.string.music_title);
            music.setSummaryOn(getString(R.string.music_summary_on));
            music.setSummaryOff(getString(R.string.music_summary_off));
            music.setDefaultValue(false);
            music.setKey(MUSIC_PREFS_KEY);
            screen.addPreference(music);

            var animation = new SwitchPreference(context);
            animation.setTitle(getString(R.string.animation_title));
            animation.setSummaryOn(getString(R.string.animation_summary_on));
            animation.setSummaryOff(R.string.animation_summary_off);
            animation.setKey(ANIMATION_PREFS_KEY);
            animation.setDefaultValue(true);
            screen.addPreference(animation);

            var whosOnFirst = new ListPreference(context);
            whosOnFirst.setTitle(getString(R.string.first_player_title));
            whosOnFirst.setSummary(getString(R.string.first_player_summary));
            String[] entries = {getString(R.string.first_player_dark), getString(R.string.first_player_light), getString(R.string.first_player_random)};
            String[] values = {""+DARK_FIRST, ""+LIGHT_FIRST, ""+RANDOM_FIRST};
            whosOnFirst.setEntries(entries);
            whosOnFirst.setEntryValues(values);
            whosOnFirst.setDefaultValue(""+RANDOM_FIRST);
            whosOnFirst.setKey(WHO_GOES_FIRST_PREFS_KEY);
            screen.addPreference(whosOnFirst);

            var speed = new ListPreference(context);
            speed.setTitle(getString(R.string.speed_title));
            speed.setSummary(getString(R.string.speed_summary));
            entries = new String[]{getString(R.string.speed_fast), getString(R.string.speed_medium), getString(R.string.speed_slow)};
            values = new String[]{"10", "50", "100"};
            speed.setEntries(entries);
            speed.setEntryValues(values);
            speed.setDefaultValue("10");
            speed.setKey(ANIMATION_SPEED_PREFS_KEY);
            screen.addPreference(speed);

            var chipset = new ListPreference(context);
            chipset.setTitle(getString(R.string.chipset_title));
            chipset.setSummary(getString(R.string.chipset_summary));
            entries = new String[]{getString(R.string.chipset_standard), getString(R.string.chipset_power), getString(R.string.chipset_regular)};
            values = new String[]{""+STANDARD_CHIPSET, ""+ALL_POWER, ""+ALL_REGULAR};
            chipset.setEntries(entries);
            chipset.setEntryValues(values);
            chipset.setDefaultValue(""+STANDARD_CHIPSET);
            chipset.setKey(CHIPSET_PREFS_KEY);
            screen.addPreference(chipset);

            var chiplayout = new ListPreference(context);
            chiplayout.setTitle(getString(R.string.chiplayout_title));
            chiplayout.setSummary(getString(R.string.chiplayout_summary));
            entries = new String[]{getString(R.string.chiplayout_standard), getString(R.string.chiplayout_center), getString(R.string.chiplayout_random)};
            values = new String[]{""+STANDARD_LAYOUT, ""+CENTRALIZED_LAYOUT, ""+RANDOMIZED_LAYOUT};
            chiplayout.setEntries(entries);
            chiplayout.setEntryValues(values);
            chiplayout.setDefaultValue(""+STANDARD_LAYOUT);
            chiplayout.setKey(CHIP_LAYOUT_PREFS_KEY);
            screen.addPreference(chiplayout);

            var theme = new ListPreference(context);
            theme.setTitle(getString(R.string.theme_title));
            theme.setSummary(getString(R.string.theme_summary));
            entries = new String[]{getString(R.string.theme_classic), getString(R.string.theme_dark), getString(R.string.theme_light), getString(R.string.theme_special)};
            values = new String[]{""+CLASSIC_THEME, ""+DARK_THEME, ""+LIGHT_THEME, ""+SPECIAL_THEME};
            theme.setEntries(entries);
            theme.setEntryValues(values);
            theme.setDefaultValue(""+CLASSIC_THEME);
            theme.setKey(THEME_LAYOUT_PREFS_KEY);
            screen.addPreference(theme);

            var ai = new ListPreference(context);
            ai.setTitle(getString(R.string.computer_title));
            ai.setSummary(getString(R.string.computer_summary));
            entries = new String[]{getString(R.string.computer_no), getString(R.string.computer_light), getString(R.string.computer_dark)};
            values = new String[]{ ""+TWO_PLAYER, ""+AI_LIGHT, ""+AI_DARK};
            ai.setEntries(entries);
            ai.setEntryValues(values);
            ai.setDefaultValue(""+TWO_PLAYER);
            ai.setKey(PLAYER_LAYOUT_PREFS_KEY);
            screen.addPreference(ai);

            setPreferenceScreen(screen);
        }


    }
}