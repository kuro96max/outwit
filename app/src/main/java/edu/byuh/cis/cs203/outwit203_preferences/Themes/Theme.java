package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Bitmap;
import android.graphics.Paint;

/**
 * The Theme interface represents a customizable theme for a game or application.
 * It allows for the definition of various aesthetic elements like cell colors,
 * chip colors, and image or power IDs for different teams.
 */
public interface Theme {
    /**
     * Gets the color for light cells.
     *
     * @return Paint object representing the color of light cells.
     */
    Paint getLightCell();
    /**
     * Gets the color for dark cells.
     *
     * @return Paint object representing the color of dark cells.
     */
    Paint getDarkCell();
    /**
     * Gets the color for neutral cells.
     *
     * @return Paint object representing the color of neutral cells.
     */
    Paint getNeutralCell();
    /**
     * Gets the color for dark chips.
     *
     * @return Paint object representing the color of dark chips.
     */
    Paint getDarkChip();
    /**
     * Gets the color for light chips.
     *
     * @return Paint object representing the color of light chips.
     */
    Paint getLightChip();
    /**
     * Gets the name of the dark team.
     *
     * @return String representing the name of the dark team.
     */
    int getDarkTeam();
    /**
     * Gets the name of the light team.
     *
     * @return String representing the name of the light team.
     */
    int getLightTeam();
    /**
     * Gets the picture ID for the dark team.
     *
     * @return int representing the picture ID for the dark team.
     */
    int getDarkPictureID();
    /**
     * Gets the picture ID for the light team.
     *
     * @return int representing the picture ID for the light team.
     */
    int getLightPictureID();
    /**
     * Gets the power ID for the dark team.
     *
     * @return int representing the power ID for the dark team.
     */
    int getDarkPowerID();
    /**
     * Gets the power ID for the light team.
     *
     * @return int representing the power ID for the light team.
     */
    int getLightPowerID();
}
