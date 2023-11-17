package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * DarkTheme is an implementation of the Theme interface, providing a dark color scheme.
 * It defines the appearance of cells, chips, and team-related elements with lighter hues.
 */
public class DarkTheme implements Theme {
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;
    private Paint darkChip;
    private Paint lightChip;

    /**
     * Constructs a new DarkTheme with predefined color settings for cells and chips.
     */
    public DarkTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(212, 173, 252));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint();
        darkCell.setColor(Color.rgb(12, 19, 79));
        darkCell.setStyle(Paint.Style.FILL);
        neutralColor = new Paint();
        neutralColor.setColor(Color.rgb(177, 94, 255));
        neutralColor.setStyle(Paint.Style.FILL);
        darkChip = new Paint();
        darkChip.setColor(Color.rgb(22, 64, 214));
        darkChip.setStyle(Paint.Style.FILL);
        lightChip = new Paint();
        lightChip.setColor(Color.rgb(255, 144, 194));
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
        return "Blue";
    }
    /**
     * Returns the name of the light team.
     *
     * @return String name of the light team.
     */
    @Override
    public String getLightTeam() {
        return "Pink";
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

