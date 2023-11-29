package edu.byuh.cis.cs203.outwit203_preferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import edu.byuh.cis.cs203.outwit203_preferences.Activities.MainActivity;
import edu.byuh.cis.cs203.outwit203_preferences.Activities.Prefs;
import edu.byuh.cis.cs203.outwit203_preferences.Themes.Theme;

public class GameView extends View {
    private Paint blackLine;
    private Paint rojo;
    private Cell[][] cellz;
    private List<Chip> chipz;
    private List<Cell> legalMoves;
    private Chip selected;
    private boolean initialized = false;
    private Timer tim;
    private RectF undoButton;
    private Bitmap undoIcon;
    private Stack<Move> undoStack;
    private Team currentPlayer;
    private Paint textFont;
    private Theme theme;
    private Team computerPlayer = Prefs.aiComp(getContext());
    Move scoreData;

    private class Timer extends Handler {

        private boolean paused;
        private int delay;

        /**
         * Start the timer
         */
        public void resume() {
            paused = false;
            sendMessageDelayed(obtainMessage(), delay);
        }

        /**
         * pause the timer
         */
        public void pause() {
            paused = true;
            removeCallbacksAndMessages(null);
        }

        /**
         * Instantiate the timer and start it running
         */
        public Timer() {
            delay = Prefs.animationSpeed(getContext());
            resume();
        }

        /**
         * The most important method in the Timer class.
         * Here, we put all the code that needs to happen at each clock-tick
         * @param m the Message object (unused)
         */
        @Override
        public void handleMessage(Message m) {
            for (var c : chipz) {
                if (c.animate() == true) {
                    checkForWinner();
                    break;
                }
            }
            if (currentPlayer == computerPlayer){
                ArrayList<Move> possibleMoves = new ArrayList<>();
                for(var chips: chipz){
                    if(chips.getColor()==computerPlayer){
                        List<Cell> findMoves = new ArrayList<>();
                        findMoves.addAll(chips.findPossibleMoves(cellz));
                        for (var goThrough: findMoves){
                            possibleMoves.add(new Move(chips.getHostCell(), goThrough));
                        }
                    }
                }
//                Collections.shuffle(possibleMoves);
                shuffle(possibleMoves);
                Move aiMove = possibleMoves.get(0);
                Chip a = findChipByCell(aiMove.src());

                selected = a;
                undoStack.push(aiMove);
                selected.setDestination(aiMove.dest(), Prefs.getAnimationPrefs(getContext()));
                selected = null;
                swapPlayers();
            }
            invalidate();
            if (!paused) {
                sendMessageDelayed(obtainMessage(), delay);
            }
        }
    }


    /**
     * Our constructor. This is where we initialize our fields.
     * @param c Context is the superclass of Activity. Thus, this
     *          parameter is basically a polymorphic reference to
     *          whatever Activity created this View... in this case,
     *          it's our MainActivity.
     */
    public GameView(Context c) {
        super(c);
        theme = Prefs.getTheme(c);
        blackLine = new Paint();
        blackLine.setColor(Color.BLACK);
        blackLine.setStyle(Paint.Style.STROKE);
        rojo = new Paint();
        rojo.setColor(Color.RED);
        rojo.setStyle(Paint.Style.FILL);
        cellz = new Cell[9][10]; //[x][y]
        chipz = new ArrayList<>();
        legalMoves = new ArrayList<>();
        selected = null;
        undoIcon = BitmapFactory.decodeResource(getResources(), R.drawable.undo);
        undoStack = new Stack<>();
        textFont = new Paint();
        textFont.setColor(Color.BLACK);
        textFont.setStyle(Paint.Style.FILL);
    }

    /**
     * onDraw is roughly equivalent to the paintComponent method in the
     * JPanel class from the standard Java API. Override it to perform
     * custom drawing for the user interface
     * @param c the Canvas object, which contains the methods we need for
     *          drawing basic shapes. Similar to the Graphics class in
     *          the standard Java API.
     */
    @Override
    public void onDraw(Canvas c) {
        final var w = c.getWidth();
        final var h = c.getHeight();
        final float cellSize = w/9f;
        if (! initialized) {
            blackLine.setStrokeWidth(cellSize * 0.03f);

            //create all the cells
            for (var i=0; i<10; i++) {
                for (var j=0; j<9; j++) {
                    var color = Team.NEUTRAL;
                    if (i<3 && j>5) {
                        color = Team.LIGHT;
                    } else if (i>6 && j<3) {
                        color = Team.DARK;
                    }
                    cellz[j][i] = new Cell(j,i,color,cellSize);
                }
            }

            //this creates all the chips and puts them in their starting positions
            resetGameboard();

            //create the undo button
            undoButton = new RectF(w-cellSize, h-cellSize, w, h);

            //set the "current player" text size to something reasonable
            textFont.setTextSize(MainActivity.findThePerfectFontSize(cellSize/2));

            //instantiate the timer
            tim = new Timer();

            initialized = true;
        }

        //draw the orange background
        c.drawRect(0,0,cellSize*9,cellSize*10,theme.getNeutralCell());

        //draw the light brown corner
        c.drawRect(cellSize*6,0, cellSize*10, cellSize*3, theme.getLightCell());

        //draw the dark brown color
        c.drawRect(0, cellSize*7, cellSize*3, cellSize*10, theme.getDarkCell());

        //draw a nice solid border around the whole thing
        c.drawRect(0,0,cellSize*9,cellSize*10, blackLine);

        //draw horizontal black lines
        for (int i=1; i<=9; i++) {
            c.drawLine(0, i*cellSize, cellSize*9, cellSize*i, blackLine);
        }
        //draw vertical black lines
        for (int i=1; i<=8; i++) {
            c.drawLine(i*cellSize, 0, i*cellSize, cellSize*10, blackLine);
        }

        chipz.forEach(ch -> ch.draw(c, blackLine));

        legalMoves.forEach(lm -> lm.drawHighlight(c, rojo));

        //draw the undo button
        c.drawRoundRect(undoButton, undoButton.width()*0.1f, undoButton.width()*0.1f, blackLine);
        c.drawBitmap(undoIcon, null, undoButton, null);

        //draw the "current player" text
        String reminder;
        if (currentPlayer == Team.LIGHT) {
            reminder = getResources().getString(theme.getLightTeam());
        } else {
            reminder = getResources().getString(theme.getDarkTeam());
//            reminder = "Dark Brown's Turn";
        }
        c.drawText(reminder, cellSize/2, cellSize*11, textFont);
//        if (Prefs.aiComp(getContext())==Team.LIGHT){
//            computerPlayer = Team.LIGHT;
//        } else if (Prefs.aiComp(getContext())==Team.DARK){
//            computerPlayer = Team.DARK;
//        } else {
//            computerPlayer = null;
//        }
    }

    /**
     * This method gets called anytime the user touches the screen
     * @param m the object that holds information about the touch event
     * @return true (to prevent the touch event from getting passed to other objects. Please refer to the Chain of Responsibility design pattern.
     */
    @Override
    public boolean onTouchEvent(MotionEvent m) {
        final var x = m.getX();
        final var y = m.getY();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {

            //ignore touch events while a chip is moving
            if (anyMovingChips()) {
                return true;
            }

            //did the user tap the undo button?
            if (undoButton.contains(x,y)) {
                undoLastMove();
            }

            //did the user tap one of the "legal move" cells?
            for (var cell : legalMoves) {
                if (cell.contains(x,y)) {
                    final var moov = new Move(selected.getHostCell(), cell);
                    undoStack.push(moov);
                    selected.setDestination(cell, Prefs.getAnimationPrefs(getContext()));
                    selected = null;
                    swapPlayers();
                    break;
                }
            }

            //first, clear old selections
//            for (var chippy : chipz) {
//                chippy.unselect();
//            }
            chipz.forEach(ch -> ch.unselect());
            legalMoves.clear();

            //now, check which chip got tapped
            for (var chippy : chipz) {
                if (chippy.contains(x, y) && currentPlayer == chippy.getColor()) {
                    //if user taps the selected chip, unselect it
                    if (selected == chippy) {
                        selected.unselect();
                        selected = null;
                        legalMoves.clear();
                        break;
                    }
                    selected = chippy;
                    chippy.select();
                    legalMoves.addAll(selected.findPossibleMoves(cellz));
                    break;
                }
            }
            invalidate();
        }
        return true;
    }

    /**
     * change the current player after each move
     */
    private void swapPlayers() {
        if (currentPlayer == Team.LIGHT) {
            currentPlayer = Team.DARK;
        } else {
            currentPlayer = Team.LIGHT;
        }
        //if animation is disabled, check for winners now, instead of
        //when the animation finishes.
        if (Prefs.getAnimationPrefs(getContext()) == false) {
            checkForWinner();
        }
    }

    /**
     * Determine if any player has won yet. If there is a winner,
     * find out who it is, and initiate the endgame dialog.
     */
    private void checkForWinner() {
        int lightCount = 0;
        int darkCount = 0;
        for (var chippy : chipz) {
            if (chippy.isHome()) {
                if (chippy.getColor() == Team.LIGHT) {
                    lightCount++;
                } else {
                    darkCount++;
                }
            }
        }
        if (lightCount == 9 || darkCount == 9) {
            String winner;
            if (lightCount > darkCount) {
                winner = getResources().getString(theme.getLightTeam());
            } else {
                winner = getResources().getString(theme.getDarkTeam());
            }
            showVictoryMessage(winner);
        }
    }

    /**
     * Pop up a dialog box announcing the winner, and give the player(s) the option to play again
     * @param winner the name of the winning team
     */
    private void showVictoryMessage(String winner) {
        tim.pause();
        AlertDialog.Builder ab = new AlertDialog.Builder((getContext()));
        ab.setTitle(getResources().getString(R.string.vict_message))
            .setMessage(winner + getResources().getString(R.string.vict_win))
            .setPositiveButton(getResources().getString(R.string.vict_again), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface d, int i) {
                    resetGameboard();
                    tim.resume();
                }
            })
            .setNegativeButton(getResources().getString(R.string.vict_quit), (d,i) -> ((Activity)getContext()).finish())
            .setCancelable(false);
        AlertDialog box = ab.create();
        box.show();
    }

    /**
     * Initializes the gameboard back to the starting state
     */
    private void resetGameboard() {
        undoStack.clear();
        legalMoves.clear();
        chipz.clear();
        selected = null;

        //clear all the cells
        for (var i=0; i<10; i++) {
            for (var j=0; j<9; j++) {
                cellz[j][i].liberate();
            }
        }

        //refactored this to a helper method since the logic was
        //getting pretty hairy, what with all the preferences...
        createChips();

        final int firstly = Prefs.whoGoesFirst(getContext());
        if (firstly == Prefs.DARK_FIRST) {
            currentPlayer = Team.DARK;
        } else if (firstly == Prefs.LIGHT_FIRST) {
            currentPlayer = Team.LIGHT;
        } else {
            if (Math.random() < 0.5) {
                currentPlayer = Team.DARK;
            } else {
                currentPlayer = Team.LIGHT;
            }
        }
    }

    /**
     * Helper method for resetGameboard()
     */
    private void createChips() {
        int layout = Prefs.chipLayout(getContext());
        int chipset = Prefs.chipset(getContext());
        for (var i=0; i<9; i++) {
            Chip dark = null;
            Chip light = null;

            //first, instantiate a dark and a light chip
            if (chipset == Prefs.ALL_POWER || (i==4 && chipset == Prefs.STANDARD_CHIPSET)) {
                dark = Chip.power(Team.DARK, getContext());
                light = Chip.power(Team.LIGHT, getContext());
            } else {
                dark = Chip.normal(Team.DARK, getContext());
                light = Chip.normal(Team.LIGHT, getContext());
            }

            //second, figure out where to put them.
            //this depends on which layout algorithm the user chose.
            if (layout == Prefs.STANDARD_LAYOUT) {
                dark.setCell(cellz[i][i]);
                light.setCell(cellz[i][i + 1]);
            } else if (layout == Prefs.RANDOMIZED_LAYOUT) {
                for (Chip ch : new Chip[]{dark, light}) {
                    while (true) {
                        int x = (int) (Math.random() * 9);
                        int y = (int) (Math.random() * 10);
                        Cell candidate = cellz[x][y];
                        if (candidate.isFree() && candidate.color() == Team.NEUTRAL) {
                            ch.setCell(candidate);
                            break;
                        }
                    }
                }
            } else {
                //CENTRALIZED LAYOUT
                //(Yes, I know it's terribly inefficient to instantiate these arrays
                //inside a loop. But since this section of code runs so infrequently,
                //I decided to sacrifice efficiency for simplicity.)
                int[] darkX = {2,3,4,5,6,6,6,5,5};
                int[] liteX = {6,5,4,3,2,2,2,3,3};
                int[] darkY = {3,3,3,3,3,4,5,5,4};
                int[] liteY = {6,6,6,6,6,5,4,4,5};
                dark.setCell(cellz[darkX[i]][darkY[i]]);
                light.setCell(cellz[liteX[i]][liteY[i]]);
            }

            //third, add the two chips to their respective arraylists
            chipz.add(dark);
            chipz.add(light);
        }

    }

    /**
     * Pop the most recent move off the stack
     * and execute it in reverse.
     * Show a toast if the stack is empty.
     */
    public void undoLastMove() {

        if (anyMovingChips()) {
            return;
        }

        if (selected != null) {
            //un-highlight the selected chip before undo-ing
            selected.unselect();
            legalMoves.clear();
        }

        if (undoStack.isEmpty()) {
            var toasty = Toast.makeText(getContext(), "No moves to undo!", Toast.LENGTH_LONG);
            toasty.show();
        } else {
            Move move = undoStack.pop();
            Cell current = move.dest();
            Cell moveTo = move.src();
            selected = getChipAt(current);
            selected.setDestination(moveTo, Prefs.getAnimationPrefs(getContext()));
            selected.unselect();
            selected = null;
            swapPlayers();
        }

        if(computerPlayer != Team.NEUTRAL){
            if (undoStack.isEmpty()) {
                var toasty = Toast.makeText(getContext(), "No moves to undo!", Toast.LENGTH_LONG);
                toasty.show();
            } else {
                Move move = undoStack.pop();
                Cell current = move.dest();
                Cell moveTo = move.src();
                selected = getChipAt(current);
                selected.setDestination(moveTo, Prefs.getAnimationPrefs(getContext()));
                selected.unselect();
                selected = null;
                swapPlayers();
            }
        }
    }

    private Chip getChipAt_original_version(Cell cel) {
        for (Chip ch : chipz) {
            if (ch.areYouHere(cel)) {
                return ch;
            }
        }
        //realistically, this should NEVER return null. (famous last words!)
        return null;
    }

    /**
     * Given a cell, find the chip that's on it.
     * @param cel the Cell we're investigating
     * @return the Chip currently sitting on that Cell, or null if the cell is vacant.
     */
    private Chip getChipAt(Cell cel) {
        return chipz.stream().filter(ch -> ch.areYouHere(cel)).findFirst().get();
    }

//    /**
//     * Populates the legalMoves arraylist with all possible moves
//     * for the currently-selected chip
//     */
//    private void findPossibleMoves() {
//        legalMoves.clear();
//        int newX, newY;
//        final Cell currentCell = selected.getHostCell();
//        if (selected.isPowerChip()) {
//            //can we go right?
//            for (newX = currentCell.x()+1; newX < 9; newX++) {
//                Cell candidate = cellz[newX][currentCell.y()];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                } else {
//                    break;
//                }
//            }
//            //can we go left?
//            for (newX = currentCell.x()-1; newX >= 0; newX--) {
//                Cell candidate = cellz[newX][currentCell.y()];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                } else {
//                    break;
//                }
//            }
//            //can we go up?
//            for (newY = currentCell.y()-1; newY >= 0; newY--) {
//                Cell candidate = cellz[currentCell.x()][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                } else {
//                    break;
//                }
//            }
//            //can we go down?
//            for (newY = currentCell.y()+1; newY < 10; newY++) {
//                Cell candidate = cellz[currentCell.x()][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                } else {
//                    break;
//                }
//            }
//            //can we go up/right diagonal?
//            newX = currentCell.x()+1;
//            newY = currentCell.y()-1;
//            while (newX < 9 && newY >= 0) {
//                Cell candidate = cellz[newX][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                    newX++;
//                    newY--;
//                } else {
//                    break;
//                }
//            }
//            //can we go up/left diagonal?
//            newX = currentCell.x()-1;
//            newY = currentCell.y()-1;
//            while (newX >= 0 && newY >= 0) {
//                Cell candidate = cellz[newX][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                    newX--;
//                    newY--;
//                } else {
//                    break;
//                }
//            }
//            //can we go down/right diagonal?
//            newX = currentCell.x()+1;
//            newY = currentCell.y()+1;
//            while (newX < 9 && newY < 10) {
//                Cell candidate = cellz[newX][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                    newX++;
//                    newY++;
//                } else {
//                    break;
//                }
//            }
//            //can we go down/left diagonal?
//            newX = currentCell.x()-1;
//            newY = currentCell.y()+1;
//            while (newX >= 0 && newY < 10) {
//                Cell candidate = cellz[newX][newY];
//                if (candidate.isLegalMove(selected)) {
//                    legalMoves.add(candidate);
//                    newX--;
//                    newY++;
//                } else {
//                    break;
//                }
//            }
//
//            //REGULAR CHIPS (not power chips)
//        } else {
//            //can we go right?
//            Cell vettedCandidate = null;
//            for (newX = currentCell.x()+1; newX < 9; newX++) {
//                Cell candidate = cellz[newX][currentCell.y()];
//                if (candidate.isLegalMove(selected)) {
//                    vettedCandidate = candidate;
//                } else {
//                    break;
//                }
//            }
//            if (vettedCandidate != null) {
//                legalMoves.add(vettedCandidate);
//            }
//            //can we go left?
//            vettedCandidate = null;
//            for (newX = currentCell.x()-1; newX >= 0; newX--) {
//                Cell candidate = cellz[newX][currentCell.y()];
//                if (candidate.isLegalMove(selected)) {
//                    vettedCandidate = candidate;
//                } else {
//                    break;
//                }
//            }
//            if (vettedCandidate != null) {
//                legalMoves.add(vettedCandidate);
//            }
//
//            //can we go up?
//            vettedCandidate = null;
//            for (newY = currentCell.y()-1; newY >= 0; newY--) {
//                Cell candidate = cellz[currentCell.x()][newY];
//                if (candidate.isLegalMove(selected)) {
//                    vettedCandidate = candidate;
//                } else {
//                    break;
//                }
//            }
//            if (vettedCandidate != null) {
//                legalMoves.add(vettedCandidate);
//            }
//
//            //can we go down?
//            vettedCandidate = null;
//            for (newY = currentCell.y()+1; newY < 10; newY++) {
//                Cell candidate = cellz[currentCell.x()][newY];
//                if (candidate.isLegalMove(selected)) {
//                    vettedCandidate = candidate;
//                } else {
//                    break;
//                }
//            }
//            if (vettedCandidate != null) {
//                legalMoves.add(vettedCandidate);
//            }
//        }
//    }

    private boolean anyMovingChips_original_version() {
        boolean result = false;
        for (var c : chipz) {
            if (c.isMoving()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * checks if a chip is moving
     * @return true if a chip is currently moving; false otherwise.
     */
    private boolean anyMovingChips() {
        return chipz.stream().anyMatch(c -> c.isMoving());
    }

    /**
     * Finds the chip located at a specified cell.
     *
     * @param cell The cell for which to find the corresponding chip.
     * @return The chip that is located at the given cell. Returns {@code null} if no chip is found at the cell.
     */
    private Chip findChipByCell(Cell cell) {
        for (Chip chip : chipz) {
            if (chip.getHostCell().equals(cell)) {
                return chip;
            }
        }
        return null;
    }

    /**
     * Shuffles the list of moves. The method will repeatedly shuffle the list if the first move in the list
     * corresponds to a power chip and there are more than 30 moves in the list.
     *
     * @param shf The list of moves to be shuffled.
     */
    private void shuffle(ArrayList<Move> shf) {
        // Repeatedly shuffle if specific conditions are met
        boolean shouldReshuffle = false;
        do {
            Collections.shuffle(shf);
            Move aiMove = shf.get(0);
            Chip a = findChipByCell(aiMove.src());

            // Check for conditions to reshuffle
            shouldReshuffle = a.isPowerChip() && shf.size() > 10;
            if(shf.size()<3){
                shouldReshuffle =false;
            }

        } while (shouldReshuffle);
    }


/*
private void evaluateChips (ArrayList<Move> moveList) {
    int highestScore = Integer.MIN_VALUE;

    for (Move move : moveList) {
        Chip movingChip = findChipByCell(move.src());
        if (movingChip != null) {
            int score = calculateMoveScore(movingChip, move);
            if (score > highestScore) {
                highestScore = score;
                scoreData =new Move(move.src(),move.dest());
                // You can also store this move as the best move if needed
            }
        }
    }
}

    private Chip findChipByCell(Cell cell) {
        for (Chip chip : chipz) {
            if (chip.getHostCell().equals(cell)) {
                return chip;
            }
        }
        return null;
    }

    private int calculateMoveScore(Chip chip, Move move) {
        int score = 0;
        int xDiff = Math.abs(chip.getHostCell().x() - move.dest().x());
        int yDiff = Math.abs(chip.getHostCell().y() - move.dest().y());

        // Deduct points if it's a power chip (as per your existing logic)
        if (chip.isPowerChip()) {
            score -= 1;
        }

        // Add points based on proximity to the corner
        score += calculateProximityScore(xDiff, yDiff);

        return score;
    }

    private int calculateProximityScore(int xDiff, int yDiff) {
        int proximityScore = 0;

        if (xDiff < 5 || yDiff < 5) {
            proximityScore += 3;
        }
        if (xDiff < 3 || yDiff < 3) {
            proximityScore += 5;
        }

        return proximityScore;
    }

 */
}