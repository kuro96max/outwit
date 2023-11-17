package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Color;
import android.graphics.Paint;

public class DarkTheme implements Theme {
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralCell;
    private Paint darkChip;
    private Paint lightChip;

    public DarkTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(212, 173, 252));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint();
        darkCell.setColor(Color.rgb(12, 19, 79));
        darkCell.setStyle(Paint.Style.FILL);
        neutralCell = new Paint();
        neutralCell.setColor(Color.rgb(177, 94, 255));
        neutralCell.setStyle(Paint.Style.FILL);
        darkChip = new Paint();
        darkChip.setColor(Color.rgb(22, 64, 214));
        darkChip.setStyle(Paint.Style.FILL);
        lightChip = new Paint();
        lightChip.setColor(Color.rgb(255, 144, 194));
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
        return neutralCell;
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
        return "Blue";
    }

    @Override
    public String getLightTeam() {
        return "Pink";
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
