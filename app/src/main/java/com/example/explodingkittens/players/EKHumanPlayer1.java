package com.example.explodingkittens.players;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.explodingkittens.EKLocalGame;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.GameMainActivity;
import com.example.gameframework.R;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.infoMessage.IllegalMoveInfo;
import com.example.gameframework.infoMessage.NotYourTurnInfo;
import com.example.gameframework.players.GameHumanPlayer;
import com.example.gameframework.utilities.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class EKHumanPlayer1 extends GameHumanPlayer implements View.OnClickListener {
    private static final String TAG = "EKHumanPlayer1";
    //DO WE NEED EKSURFACEVIEW?
    private int layoutId;

    public HashMap<ImageButton, Card> buttonMap = new HashMap();

    //Variables will reference widgets that will be modified during play
    private ImageButton player1 = null;
    private ImageButton player2 = null;
    private ImageButton player3 = null;
    private ImageButton discardPile = null;
    private ImageButton drawPile = null;
    private ImageButton playerCard1 = null;
    private ImageButton playerCard2 = null;
    private ImageButton playerCard3 = null;
    private ImageButton playerCard4 = null;
    private ImageButton playerCard5 = null;
    private Button handLeft = null;
    private Button handRight = null;

    private GameMainActivity myActivity;

    private ArrayList<ImageButton> cards = new ArrayList<>(5);

    private EKState state;

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
    if(info == null){
        return;
    }else if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
        }
        else if (!(info instanceof EKState))
            // if we do not have a EKState, ignore
            return;

        else {
            //Update every image button to match what is in the gamestate
            EKState state = new EKState((EKState) info);
            // TODO: THIS IS WHERE WE UPDATE THE GUI
            //Set the player's cards

            //Get the IDs of the first five cards in the hand
            /*
            Card firstCard = state.deck.get(state.getPlayerTurn()).get(0);
            Card secondCard = state.deck.get(state.getPlayerTurn()).get(1);
            Card thirdCard = state.deck.get(state.getPlayerTurn()).get(2);
            Card fourthCard = state.deck.get(state.getPlayerTurn()).get(3);
            Card fifthCard = state.deck.get(state.getPlayerTurn()).get(4);

            playerCard1.setImageResource(firstCard.image);
            playerCard2.setImageResource(secondCard.image);
            playerCard3.setImageResource(thirdCard.image);
            playerCard4.setImageResource(fourthCard.image);
            playerCard5.setImageResource(fifthCard.image);
             */
            Logger.log(TAG, "receiving");
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        myActivity = activity;
//TODO how to access EKState from here

        activity.setContentView(R.layout.activity_main); //May cause issue with layoutId above

        //Create the buttons that are in the GUI
        ImageButton player1 = activity.findViewById(R.id.player1);
        ImageButton player2 = activity.findViewById(R.id.player2);
        ImageButton player3 = activity.findViewById(R.id.player3);
        ImageButton discardPile = activity.findViewById(R.id.discardPile);
        ImageButton drawPile = activity.findViewById(R.id.drawPile);
        ImageButton playerCard1 = activity.findViewById(R.id.playerCard1);
        ImageButton playerCard2 = activity.findViewById(R.id.playerCard2);
        ImageButton playerCard3 = activity.findViewById(R.id.playerCard3);
        ImageButton playerCard4 = activity.findViewById(R.id.playerCard4);
        ImageButton playerCard5 = activity.findViewById(R.id.playerCard5);
        Button handLeft = activity.findViewById(R.id.handLeft);
        Button handRight = activity.findViewById(R.id.handRight);

/*
        buttonMap.put(playerCard1,state.deck.get(0).get(0));
        buttonMap.put(playerCard2,state.deck.get(0).get(1));
        buttonMap.put(playerCard3,state.deck.get(0).get(2));
        buttonMap.put(playerCard4,state.deck.get(0).get(3));
        buttonMap.put(playerCard5,state.deck.get(0).get(4));



        setGuiImage(playerCard1,buttonMap);
        setGuiImage(playerCard2,buttonMap);
        setGuiImage(playerCard3,buttonMap);
        setGuiImage(playerCard4,buttonMap);
        setGuiImage(playerCard5,buttonMap);
*/
        //Create Button Listeners
        player1.setOnClickListener(this);
        player2.setOnClickListener(this);
        player3.setOnClickListener(this);
        discardPile.setOnClickListener(this);
        drawPile.setOnClickListener(this);
        playerCard1.setOnClickListener(this);
        playerCard2.setOnClickListener(this);
        playerCard3.setOnClickListener(this);
        playerCard4.setOnClickListener(this);
        playerCard5.setOnClickListener(this);
        handLeft.setOnClickListener(this);
        handRight.setOnClickListener(this);

        //Create card deck array
        cards.add(playerCard1);
        cards.add(playerCard2);
        cards.add(playerCard3);
        cards.add(playerCard4);
        cards.add(playerCard5);
    }

    @Override
    public void onClick(View v) {
        // Checks if the id matches the object
        // TODO: Move to this way if others don't work
        if (v.getId() == R.id.player1) {
            // Determine if an action was selected that allows that user to be included
        }
        else if (v.getId() == R.id.player2) {
            // Determine if an action was selected that allows that user to be included
        }
        else if (v.getId() == R.id.player3) {
            // Determine if an action was selected that allows that user to be included
        }
        else if (v.getId() == R.id.discardPile) {
            // Add the selected card to the discard pile
        }
        else if (v.getId() == R.id.drawPile) {
            // Create EKDrawAction
            // Send to localGame
        }
        else if (v.getId() == R.id.playerCard1) {
            // Determine which action was called
        }
        else if (v.getId() == R.id.playerCard2) {
            // Determine which action was called
        }
        else if (v.getId() == R.id.playerCard3) {
            // Determine which action was called
        }
        else if (v.getId() == R.id.playerCard4) {
            // Determine which action was called
        }
        else if (v.getId() == R.id.playerCard5) {
            // Determine which action was called
        }
        else if (v.getId() == R.id.handLeft) {
            // Shift all the cards -1 in the array

        }
        else if (v.getId() == R.id.handRight) {
            // Shift all the cards +1 in the array
        }
        else {}
    }

    public void setGuiImage(ImageButton button, HashMap<ImageButton,Card> map){
        button.setImageResource(map.get(button).image);
    }

}
