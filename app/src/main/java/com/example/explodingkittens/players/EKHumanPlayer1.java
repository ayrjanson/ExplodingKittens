package com.example.explodingkittens.players;

//consistent with ttt
import android.graphics.Color;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Point;

import com.example.gameframework.GameMainActivity;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameHumanPlayer;
import com.example.gameframework.R;
import com.example.gameframework.infoMessage.IllegalMoveInfo;
import com.example.gameframework.infoMessage.NotYourTurnInfo;
import com.example.gameframework.utilities.Logger;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.ekActionMessage.EKMoveAction;
import com.example.explodingkittens.views.EKSurfaceView;
import android.view.View;
import android.widget.ImageButton;


//todo alex EKSurfaceView, methods in here
public class EKHumanPlayer1 extends GameHumanPlayer  {
    private static final String TAG = "EKHumanPlayer1";
    //DO WE NEED EKSURFACEVIEW?
    private int layoutId;

    private EKSurfaceView surfaceView;
    /**
     * constructor
     *consistent with TTT
     * @param name the name of the player
     */

    public EKHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof EKState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((EKState)info);
            surfaceView.invalidate();
            Logger.log(TAG, "receiving");
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        activity.setContentView(layoutId);
        //TODO ask ben about necessity of EKSURFACEVIEW
        surfaceView = (EKSurfaceView)myActivity.findViewById(R.layout.activity_main);
    }
}
