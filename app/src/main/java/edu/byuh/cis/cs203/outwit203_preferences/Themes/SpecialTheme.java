package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import edu.byuh.cis.cs203.outwit203_preferences.R;

/**
 * SpecialTheme is an implementation of the Theme interface, providing a special color scheme.
 * It defines the appearance of cells, chips, and team-related elements with lighter hues.
 */
public class SpecialTheme implements Theme{
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;

    /**
     * Constructs a new SpecialTheme with predefined color settings for cells and chips.
     */
    public SpecialTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(255,233,0));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint(lightCell);
        darkCell.setColor(Color.rgb(75,54,95));
        neutralColor = new Paint(lightCell);
        neutralColor.setColor(Color.rgb(255, 255, 255));
    }
    /**
     * Returns the Paint object for light cells.
     *
     * @return Paint object for light cells.
     */
    @Override
    public Paint getLightCell() {
        return lightCell;
    }
    /**
     * Returns the Paint object for dark cells.
     *
     * @return Paint object for dark cells.
     */
    @Override
    public Paint getDarkCell() {
        return darkCell;
    }
    /**
     * Returns the Paint object for neutral cells.
     *
     * @return Paint object for neutral cells.
     */
    @Override
    public Paint getNeutralCell() {
        return neutralColor;
    }
    /**
     * Returns the Paint object for dark chips.
     *
     * @return Paint object for dark chips.
     */
    @Override
    public Paint getDarkChip() {
        return null;
    }
    /**
     * Returns the Paint object for light chips.
     *
     * @return Paint object for light chips.
     */
    @Override
    public Paint getLightChip() {
        return null;
    }
    /**
     * Returns the name of the dark team.
     *
     * @return String name of the dark team.
     */
    @Override
    public int getDarkTeam() {
        return R.string.special_dark;
    }
    /**
     * Returns the name of the light team.
     *
     * @return String name of the light team.
     */
    @Override
    public int getLightTeam() {
        return R.string.special_light;
    }
    /**
     * Returns the picture ID for the dark team.
     *
     * @return int picture ID for the dark team.
     */
    @Override
    public int getDarkPictureID() {
        return R.drawable.baikinman;
    }
    /**
     * Returns the picture ID for the light team.
     *
     * @return int picture ID for the light team.
     */
    @Override
    public int getLightPictureID() {
        return R.drawable.anpanman;
    }
    /**
     * Returns the power ID for the dark team.
     *
     * @return int power ID for the dark team.
     */
    @Override
    public int getDarkPowerID() {
        return R.drawable.dokinchan;
    }
    /**
     * Returns the power ID for the light team.
     *
     * @return int power ID for the light team.
     */
    @Override
    public int getLightPowerID() {
        return R.drawable.meronpana;
    }
}
