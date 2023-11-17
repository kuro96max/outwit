package edu.byuh.cis.cs203.outwit203_preferences;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import edu.byuh.cis.cs203.outwit203_preferences.Activities.Prefs;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.Theme;

public class Chip {

    private int color;
    private boolean power;
    private RectF currentLocation;
    private Cell currentCell;
    private boolean selected;
    private final static Paint GOLD_LEAF;
    private PointF velocity;
    private Cell destination;
    private Theme theme;
    private Context context;
    private Bitmap darkPic;
    private Bitmap lightPic;
    private Bitmap darkPower;
    private Bitmap lightPower;

    /**
     * Static initializer for the Paint fields that are shared by all chip objects
     */
    static {
        GOLD_LEAF = new Paint();
        GOLD_LEAF.setColor(Color.rgb(202,192,6));
        GOLD_LEAF.setStyle(Paint.Style.FILL);
    }

    /**
     * Constructor for the Chip class
     * @param t the team (light or dark) that the chip belongs to
     * @param p true if a power chip, false if normal
     */
    private Chip(int t, boolean p, Context c) {
        color = t;
        power = p;
        currentLocation = new RectF();
        selected = false;
        velocity = new PointF();
        destination = null;
        theme = Prefs.getTheme(c);
        context = c;

        //Load pictures using Bitmap
        darkPic = BitmapFactory.decodeResource(context.getResources(), theme.getDarkPictureID());
        lightPic = BitmapFactory.decodeResource(context.getResources(), theme.getLightPictureID());
        darkPower = BitmapFactory.decodeResource(context.getResources(), theme.getDarkPowerID());
        lightPower = BitmapFactory.decodeResource(context.getResources(), theme.getLightPowerID());
    }

    /**
     * A "simple factory" for instantiating normal (non-power) chips
     * @param t the chip's color, light or dark
     * @return a new normal chip
     */
    public static Chip normal(int t, Context c) {
        return new Chip(t, false, c);
    }

    /**
     * A "simple factory" for instantiating power chips
     * @param t the chip's color, light or dark
     * @return a new power chip
     */
    public static Chip power(int t, Context c) {
        return new Chip(t, true, c);
    }

    /**
     * Basic getter for the chip's location
     * @return
     */
    public RectF getBounds() {
        return currentLocation;
    }

    /**
     * Assign this chip to a particular cell
     * @param c the cell that this chip will reside in
     */
    public void setCell(Cell c) {
        if (currentCell != null) {  //kludgy
            currentCell.liberate();
        }
        currentCell = c;
        currentCell.setOccupied();
        velocity.set(0,0);
        currentLocation.set(currentCell.bounds());
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    /**
     * Checks whether a given cell is the same cell that the chip resides in
     * @param cel the cell to test
     * @return true if the given cell is this chip's current cell, false otherwise
     */
    public boolean areYouHere(Cell cel) {
        return (currentCell == cel);
    }

    /**
     * Draws the chip on the screen.
     * This method handles the rendering of chips based on their current state, color, and power status.
     * It uses different rendering styles depending on the availability of theme elements.
     *
     * @param c the Canvas object on which the chip is drawn.
     * @param blackLine a Paint object used for outlining the chip. It is passed in for convenience.
     */
    public void draw(Canvas c, Paint blackLine) {

        if (selected) {
            if(theme.getDarkChip()!=null){
                c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                        currentLocation.width()*0.6f, GOLD_LEAF);
            } else {
                c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                        currentLocation.width()*0.5f, GOLD_LEAF);
            }
        }
        if(theme.getDarkChip()!=null&theme.getLightChip()!=null) {
            if (getColor() == Team.DARK) {
                c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                        currentLocation.width() * 0.45f, theme.getDarkChip());
            } else {
                c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                        currentLocation.width() * 0.45f, theme.getLightChip());
            }
            c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                    currentLocation.width() * 0.45f, blackLine);

            if (power) {
                c.drawCircle(currentLocation.centerX(), currentLocation.centerY(),
                        currentLocation.width() * 0.2f, GOLD_LEAF);
            }
        } else {
            float diameter = currentLocation.width() * 0.8f;

            float x = currentLocation.centerX() - (diameter / 2);
            float y = currentLocation.centerY() - (diameter / 2);

            Bitmap darkBitmap = Bitmap.createScaledBitmap(darkPic, (int)diameter, (int)diameter, true);
            Bitmap lightBitmap = Bitmap.createScaledBitmap(lightPic, (int)diameter, (int)diameter, true);
            Bitmap dp = Bitmap.createScaledBitmap(darkPower, (int)diameter, (int)diameter, true);
            Bitmap lp = Bitmap.createScaledBitmap(lightPower, (int)diameter, (int)diameter, true);

            if (getColor() == Team.DARK&!power){
                c.drawBitmap(darkBitmap, x, y, null);
            } else if (getColor() == Team.LIGHT&!power){
                c.drawBitmap(lightBitmap, x, y, null);
            } else if (getColor() == Team.DARK&power) {
                c.drawBitmap(dp, x, y, null);
            } else {
                c.drawBitmap(lp, x, y, null);
            }
        }
    }

    /**
     * Tests whether the given (x,y) coordinate is within this chip's bounding box
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if (x,y) is inside this chip's bounding box, false otherwise
     */
    public boolean contains(float x, float y) {
        return currentLocation.contains(x,y);
    }

    /**
     * Basic getter method for the chip's color (light or dark)
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * is the chip in its "home corner"?
     * @return true if the chip is in its home corner; false otherwise
     */
    public boolean isHome() {
        return this.color == currentCell.color();
    }

    /**
     * simple getter method for the power field
     * @return true if this is a power chip; false otherwise.
     */
    public boolean isPowerChip() {
        return power;
    }

    /**
     * simple getter for the current cell
     * @return the current cell
     */
    public Cell getHostCell() {
        return currentCell;
    }

    /**
     * Is animation currently happening?
     * @return true if the token is currently moving (i.e. has a non-zero velocity); false otherwise.
     */
    public boolean isMoving() {
        return (velocity.x != 0 || velocity.y != 0);
    }

    /**
     * Assign a destination location to the token
     * @param c the cell where the token should stop
     */
    public void setDestination(Cell c, boolean animated) {
        destination = c;
        if (animated) {
            PointF dir = currentCell.directionTo(destination);
            velocity.x = dir.x * currentLocation.width() * 0.333f;
            velocity.y = dir.y * currentLocation.width() * 0.333f;
        } else {
            setCell(destination);
        }
    }

    /**
     * Move the token by its current velocity.
     * Stop when it reaches its destination location.
     */
    public boolean animate() {
        boolean justFinished = false;
        if (isMoving()) {
            float dx = destination.bounds().left - currentLocation.left;
            float dy = destination.bounds().top - currentLocation.top;
            if (PointF.length(dx, dy) < currentLocation.width() / 2) {
                setCell(destination);
                justFinished = true;
            }
            currentLocation.offset(velocity.x, velocity.y);
        }
        return justFinished;
    }

    /**
     * Returns an arraylist with all possible moves
     * for the currently-selected chip
     * @param cellz all the cells
     * @return a new arraylist, containing the cells this chip could move to.
     */
    public List<Cell> findPossibleMoves(Cell[][] cellz) {
        List<Cell> legalMoves =  new ArrayList<>();
        int newX, newY;
        //final Cell currentCell = selected.getHostCell();
        if (power) {
            //can we go right?
            for (newX = currentCell.x()+1; newX < 9; newX++) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go left?
            for (newX = currentCell.x()-1; newX >= 0; newX--) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go up?
            for (newY = currentCell.y()-1; newY >= 0; newY--) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go down?
            for (newY = currentCell.y()+1; newY < 10; newY++) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                } else {
                    break;
                }
            }
            //can we go up/right diagonal?
            newX = currentCell.x()+1;
            newY = currentCell.y()-1;
            while (newX < 9 && newY >= 0) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                    newX++;
                    newY--;
                } else {
                    break;
                }
            }
            //can we go up/left diagonal?
            newX = currentCell.x()-1;
            newY = currentCell.y()-1;
            while (newX >= 0 && newY >= 0) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                    newX--;
                    newY--;
                } else {
                    break;
                }
            }
            //can we go down/right diagonal?
            newX = currentCell.x()+1;
            newY = currentCell.y()+1;
            while (newX < 9 && newY < 10) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                    newX++;
                    newY++;
                } else {
                    break;
                }
            }
            //can we go down/left diagonal?
            newX = currentCell.x()-1;
            newY = currentCell.y()+1;
            while (newX >= 0 && newY < 10) {
                Cell candidate = cellz[newX][newY];
                if (candidate.isLegalMove(this)) {
                    legalMoves.add(candidate);
                    newX--;
                    newY++;
                } else {
                    break;
                }
            }

            //REGULAR CHIPS (not power chips)
        } else {
            //can we go right?
            Cell vettedCandidate = null;
            for (newX = currentCell.x()+1; newX < 9; newX++) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(this)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }
            //can we go left?
            vettedCandidate = null;
            for (newX = currentCell.x()-1; newX >= 0; newX--) {
                Cell candidate = cellz[newX][currentCell.y()];
                if (candidate.isLegalMove(this)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }

            //can we go up?
            vettedCandidate = null;
            for (newY = currentCell.y()-1; newY >= 0; newY--) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(this)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }

            //can we go down?
            vettedCandidate = null;
            for (newY = currentCell.y()+1; newY < 10; newY++) {
                Cell candidate = cellz[currentCell.x()][newY];
                if (candidate.isLegalMove(this)) {
                    vettedCandidate = candidate;
                } else {
                    break;
                }
            }
            if (vettedCandidate != null) {
                legalMoves.add(vettedCandidate);
            }
        }
        return legalMoves;
    }

}