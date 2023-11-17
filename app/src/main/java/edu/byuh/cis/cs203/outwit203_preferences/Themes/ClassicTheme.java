package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * ClassicTheme is an implementation of the Theme interface, providing a classic color scheme.
 * It defines the appearance of cells, chips, and team-related elements with lighter hues.
 */

public class ClassicTheme implements Theme {
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;
    private Paint darkChip;
    private Paint lightChip;

    /**
     * Constructs a new ClassicTheme with predefined color settings for cells and chips.
     */
    public ClassicTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(217, 198, 149));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint(lightCell);
        darkCell.setColor(Color.rgb(133, 98, 6));
        neutralColor = new Paint(lightCell);
        neutralColor.setColor(Color.rgb(231,175,28));
        darkChip = new Paint();
        darkChip.setColor(Color.rgb(98,78, 26));
        darkChip.setStyle(Paint.Style.FILL);
        lightChip = new Paint();
        lightChip.setColor(Color.rgb(250,233, 188));
        lightChip.setStyle(Paint.Style.FILL);
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
        return darkChip;
    }
    /**
     * Returns the Paint object for light chips.
     *
     * @return Paint object for light chips.
     */
    @Override
    public Paint getLightChip() {
        return lightChip;
    }
    /**
     * Returns the name of the dark team.
     *
     * @return String name of the dark team.
     */
    @Override
    public String getDarkTeam() {
        return "Dark Brown";
    }
    /**
     * Returns the name of the light team.
     *
     * @return String name of the light team.
     */
    @Override
    public String getLightTeam() {
        return "Light Brown";
    }
    /**
     * Returns the picture ID for the dark team.
     *
     * @return int picture ID for the dark team.
     */
    @Override
    public int getDarkPictureID() {
        return 0;
    }
    /**
     * Returns the picture ID for the light team.
     *
     * @return int picture ID for the light team.
     */
    @Override
    public int getLightPictureID() {
        return 0;
    }
    /**
     * Returns the power ID for the dark team.
     *
     * @return int power ID for the dark team.
     */
    @Override
    public int getDarkPowerID() {
        return 0;
    }
    /**
     * Returns the power ID for the light team.
     *
     * @return int power ID for the light team.
     */
    @Override
    public int getLightPowerID() {
        return 0;
    }
}
