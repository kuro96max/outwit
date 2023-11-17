package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Color;
import android.graphics.Paint;

public class LightTheme implements Theme{
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;
    private Paint darkChip;
    private Paint lightChip;

    public LightTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(210, 222, 50));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint(lightCell);
        darkCell.setColor(Color.rgb(97, 163, 186));
        neutralColor = new Paint(lightCell);
        neutralColor.setColor(Color.rgb(255, 255, 221));
        darkChip = new Paint();
        darkChip.setColor(Color.rgb(25, 4, 130));
        darkChip.setStyle(Paint.Style.FILL);
        lightChip = new Paint();
        lightChip.setColor(Color.rgb(162, 197, 121));
        lightChip.setStyle(Paint.Style.FILL);
    }
    @Override
    public Paint getLightCell() {
        return lightCell;
    }
    @Override
    public Paint getDarkCell() {
        return darkCell;
    }
    @Override
    public Paint getNeutralCell() {
        return neutralColor;
    }
    @Override
    public Paint getDarkChip() {
        return darkChip;
    }
    @Override
    public Paint getLightChip() {
        return lightChip;
    }

    @Override
    public String getDarkTeam() {
        return "Dark Blue";
    }

    @Override
    public String getLightTeam() {
        return "Light Green";
    }

    @Override
    public int getDarkPictureID() {
        return 0;
    }

    @Override
    public int getLightPictureID() {
        return 0;
    }

    @Override
    public int getDarkPowerID() {
        return 0;
    }

    @Override
    public int getLightPowerID() {
        return 0;
    }
}
