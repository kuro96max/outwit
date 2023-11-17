package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import edu.byuh.cis.cs203.outwit203_preferences.R;

public class SpecialTheme implements Theme{
    private Paint lightCell;
    private Paint darkCell;
    private Paint neutralColor;

    public SpecialTheme(){
        lightCell = new Paint();
        lightCell.setColor(Color.rgb(210, 222, 50));
        lightCell.setStyle(Paint.Style.FILL);
        darkCell = new Paint(lightCell);
        darkCell.setColor(Color.rgb(97, 163, 186));
        neutralColor = new Paint(lightCell);
        neutralColor.setColor(Color.rgb(255, 255, 221));
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
        return null;
    }
    @Override
    public Paint getLightChip() {
        return null;
    }

    @Override
    public String getDarkTeam() {
        return "Villain";
    }

    @Override
    public String getLightTeam() {
        return "Hero";
    }

    @Override
    public int getDarkPictureID() {
        return R.drawable.baikinman;
    }

    @Override
    public int getLightPictureID() {
        return R.drawable.anpanman;
    }

    @Override
    public int getDarkPowerID() {
        return R.drawable.dokinchan;
    }

    @Override
    public int getLightPowerID() {
        return R.drawable.meronpana;
    }
}
