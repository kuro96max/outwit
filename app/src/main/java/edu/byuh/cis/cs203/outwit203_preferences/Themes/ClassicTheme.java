package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Color;
import android.graphics.Paint;

public class ClassicTheme implements Theme {
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;
    private Paint darkChip;
    private Paint lightChip;

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
        return "Dark Brown";
    }

    @Override
    public String getLightTeam() {
        return "Light Brown";
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
