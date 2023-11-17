package edu.byuh.cis.cs203.outwit203_preferences.Themes;

import android.graphics.Bitmap;
import android.graphics.Paint;

public interface Theme {
    Paint getLightCell();
    Paint getDarkCell();
    Paint getNeutralCell();
    Paint getDarkChip();
    Paint getLightChip();
    String getDarkTeam();
    String getLightTeam();
    int getDarkPictureID();
    int getLightPictureID();
    int getDarkPowerID();
    int getLightPowerID();

}
