package com.example.explodingkittens.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.utilities.FlashSurfaceView;

public class EKSurfaceView  extends FlashSurfaceView {
    private static final String TAG = "EKSurfaceView";

    // some constants, which are percentages with respect to the minimum
    // of the height and the width. All drawing will be done in the "middle
    // square".
    //
    // The divisions both horizontally and vertically within the
    // playing square are:
    // - first square starts at 5% and goes to 33%
    // - second square starts at 36% and goes to 64%
    // - third square starts at 67& and goes to 95%
    // There is therefore a 5% border around the edges; each square
    // is 28% high/wide, and the lines between squares are 3%
    private final static float BORDER_PERCENT = 5; // size of the border
    private final static float SQUARE_SIZE_PERCENT = 28; // size of each of our 9 squares
    private final static float LINE_WIDTH_PERCENT = 3; // width of a tic-tac-toe line
    private final static float SQUARE_DELTA_PERCENT = SQUARE_SIZE_PERCENT
            + LINE_WIDTH_PERCENT; // distance from left (or top) edge of square to the next one

    /*
     * Instance variables
     */

    // the game's state
    protected EKState state;

    // the offset from the left and top to the beginning of our "middle square"; one
    // of these will always be zero
    protected float hBase;
    protected float vBase;

    // the size of one edge of our "middle square", or -1 if we have not determined
    // size
    protected float fullSquare;

    /**
     * Constructor for the TTTSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public EKSurfaceView(Context context) {
        super(context);
        init();
    }// ctor

    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout.
     *
     * @param context - a reference to the activity this animation is run under
     * @param attrs   - set of attributes passed from system
     */
    public EKSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    /**
     * Helper-method for the constructors
     */
    private void init() {
        setBackgroundColor(backgroundColor());
    }// init


    public void setState(EKState state) {
        this.state = state;
    }

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int foregroundColor() {
        return Color.YELLOW;
    }

    /**
     * @return
     * 		the color to paint the tic-tac-toe lines, and the X's and O's
     */
    public int backgroundColor() {
        return Color.BLUE;
    }

    /**
     * callback method, called whenever it's time to redraw
     * frame
     *
     * @param g
     * 		the canvas to draw on
     */
    public void onDraw(Canvas g) {

        // update the variables that relate
        // to the dimensions of the animation surface
        updateDimensions(g);
/*
        // paint the TTT-board's horizontal and vertical lines
        Paint p = new Paint();
        p.setColor(foregroundColor());
        for (int i = 0; i <= 1; i++) {
            float variable1 = BORDER_PERCENT + SQUARE_SIZE_PERCENT
                    + (i * SQUARE_DELTA_PERCENT);
            float variable2 = variable1 + LINE_WIDTH_PERCENT;
            float fixed1 = BORDER_PERCENT;
            float fixed2 = 100 - BORDER_PERCENT;
            g.drawRect(h(variable1), v(fixed1), h(variable2), v(fixed2), p);
            g.drawRect(h(fixed1), v(variable1), h(fixed2), v(variable2), p);
        }

        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }

        // for each square that has an X or O, draw it on the appropriate
        // place on the canvas
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char result = state.getPiece(row, col); // get piece
                drawSymbol(g, result, col, row);
            }
        }

 */
    }

    /**
     * update the instance variables that relate to the drawing surface
     *
     * @param g
     * 		an object that references the drawing surface
     */

    private void updateDimensions(Canvas g) {

        // initially, set the height and width to be that of the
        // drawing surface
        int width = g.getWidth();
        int height = g.getHeight();

        // Set the "full square" size to be the minimum of the height and
        // the width. Depending on which is greater, set either the
        // horizontal or vertical base to be partway across the screen,
        // so that the "playing square" is in the middle of the screen on
        // its long dimension
        if (width > height) {
            fullSquare = height;
            vBase = 0;
            hBase = (width - height) / (float) 2.0;
        } else {
            fullSquare = width;
            hBase = 0;
            vBase = (height - width) / (float) 2.0;
        }

    }

    // x- and y-percentage-coordinates for a polygon that displays the X's
    // first slash
    private static float[] xPoints1 = { 6.25f, 12.5f, 87.5f, 93.75f };
    private static float[] yPoints1 = { 12.5f, 6.25f, 93.75f, 87.5f };

    // x- and y-percentage-coordinates for a polygon that displays the X's
    // second slash
    private static float[] xPoints2 = { 87.5f, 6.25f, 93.75f, 12.5f };
    private static float[] yPoints2 = { 6.25f, 87.5f, 12.5f, 93.75f };


    /**
     * helper-method to convert from a percentage to a horizontal pixel location
     *
     * @param percent
     * 		the percentage across the drawing square
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float h(float percent) {
        return hBase + percent * fullSquare / 100;
    }

    /**
     * helper-method to convert from a percentage to a vertical pixel location
     *
     * @param percent
     * 		the percentage down the drawing square
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float v(float percent) {
        return vBase + percent * fullSquare / 100;
    }


    //only display X ammounts of cards- one button adds the other subtracts from some varible
    //that tells you which cards to start drawing 


}
