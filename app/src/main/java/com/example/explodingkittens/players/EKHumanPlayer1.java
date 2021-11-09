package com.example.explodingkittens.players;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.explodingkittens.ekActionMessage.EKDrawAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.GameMainActivity;
import com.example.gameframework.LocalGame;
import com.example.gameframework.R;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.infoMessage.IllegalMoveInfo;
import com.example.gameframework.infoMessage.NotYourTurnInfo;
import com.example.gameframework.players.GameHumanPlayer;
import com.example.gameframework.utilities.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class EKHumanPlayer1 extends GameHumanPlayer implements View.OnClickListener {
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
    private ArrayList<ImageButton> playerCards;
    private Button handLeft = null;
    private Button handRight = null;

    private static final String TAG = "EKHumanPlayer1";

    private int layoutId;
    private GameMainActivity myActivity;
    public HashMap<ImageButton, Card> buttonMap = new HashMap();
    private ArrayList<ImageButton> cards = new ArrayList<>(5);
    private EKState state;

    static Hashtable<CARDTYPE, Integer> imageTable = new Hashtable()
    {{
        put(CARDTYPE.MELON, R.drawable.melon);
        put(CARDTYPE.BEARD,R.drawable.beard);
        put(CARDTYPE.POTATO,R.drawable.potato);
        put(CARDTYPE.TACO,R.drawable.taco);
        put(CARDTYPE.ATTACK,R.drawable.attack);
        put(CARDTYPE.SHUFFLE,R.drawable.shuffle);
        put(CARDTYPE.FAVOR,R.drawable.favor);
        put(CARDTYPE.SKIP,R.drawable.skip);
        put(CARDTYPE.SEEFUTURE,R.drawable.seefuture);
        put(CARDTYPE.NOPE,R.drawable.nope);
        put(CARDTYPE.DEFUSE,R.drawable.defuse);
        put(CARDTYPE.EXPLODE,R.drawable.explode);

    }};

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
        }
        else if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
        }
        else if (!(info instanceof EKState))
            // if we do not have a EKState, ignore
            return;

        else {
            //Update every image button to match what is in the gamestate
            this.state = new EKState((EKState) info);

            int myPlayerNum = ((LocalGame) game).getPlayerIdx(this);

            /*Card firstCard = state.deck.get(state.getPlayerTurn()).get(0);
            Card secondCard = state.deck.get(state.getPlayerTurn()).get(1);
            Card thirdCard = state.deck.get(state.getPlayerTurn()).get(2);
            Card fourthCard = state.deck.get(state.getPlayerTurn()).get(3);
            Card fifthCard = state.deck.get(state.getPlayerTurn()).get(4);
*/
            int lastDiscard = state.discard.size();

            Card discardCard = null;
            if (lastDiscard > 0) {
                discardCard = state.discard.get(lastDiscard - 1);
                discardPile.setImageResource(imageTable.get(discardCard.getType()));
            } else {
                discardPile.setImageResource(R.drawable.exploading_kitten_back);
            }

            int numCardsDisplay;
            if (state.deck.get(myPlayerNum).size() < playerCards.size()) {
                numCardsDisplay = state.deck.get(myPlayerNum).size();
            }
            else numCardsDisplay = playerCards.size();

            for (int i = 0; i < numCardsDisplay; i++) {
                playerCards.get(i).setImageResource(imageTable.get(state.deck.get(
                                            myPlayerNum).get(i).getType()));
            }

            for (int i = state.deck.get(myPlayerNum).size(); i < playerCards.size(); i++) {
                playerCards.get(i).setImageResource(R.drawable.exploading_kitten_back);
            }


            /*
            buttonMap.put(playerCard1,state.deck.get(0).get(0));
            buttonMap.put(playerCard2,state.deck.get(0).get(1));
            buttonMap.put(playerCard3,state.deck.get(0).get(2));
            buttonMap.put(playerCard4,state.deck.get(0).get(3));
            buttonMap.put(playerCard5,state.deck.get(0).get(4));
             */

            /*
            setGuiImage(playerCard1,buttonMap);
            setGuiImage(playerCard2,buttonMap);
            setGuiImage(playerCard3,buttonMap);
            setGuiImage(playerCard4,buttonMap);
            setGuiImage(playerCard5,buttonMap);
            */

            Logger.log(TAG, "receiving");
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        this.myActivity = activity;

        activity.setContentView(R.layout.activity_main); //May cause issue with layoutId above

        //Create the buttons that are in the GUI
        this.player1 = (ImageButton)activity.findViewById(R.id.player1);
        this.player2 = (ImageButton)activity.findViewById(R.id.player2);
        this.player3 = (ImageButton)activity.findViewById(R.id.player3);
        this.discardPile = (ImageButton)activity.findViewById(R.id.discardPile);
        this.drawPile = (ImageButton)activity.findViewById(R.id.drawPile);
        this.playerCard1 = (ImageButton)activity.findViewById(R.id.playerCard1);
        this.playerCard2 = (ImageButton)activity.findViewById(R.id.playerCard2);
        this.playerCard3 = (ImageButton)activity.findViewById(R.id.playerCard3);
        this.playerCard4 = (ImageButton)activity.findViewById(R.id.playerCard4);
        this.playerCard5 = (ImageButton)activity.findViewById(R.id.playerCard5);
        this.handLeft = (Button)activity.findViewById(R.id.handLeft);
        this.handRight = (Button)activity.findViewById(R.id.handRight);

        playerCards = new ArrayList<>();
        playerCards.add(playerCard1);
        playerCards.add(playerCard2);
        playerCards.add(playerCard3);
        playerCards.add(playerCard4);
        playerCards.add(playerCard5);

        //Create Button Listeners
        player1.setOnClickListener(this);
        player2.setOnClickListener(this);
        player3.setOnClickListener(this);
        discardPile.setOnClickListener(this);
        drawPile.setOnClickListener(this);

        for (ImageButton playerCard : playerCards) {
            playerCard.setOnClickListener(this);
        }

        handLeft.setOnClickListener(this);
        handRight.setOnClickListener(this);
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
            EKDrawAction draw = new EKDrawAction(this);
            game.sendAction(draw);

            //Probably need end turn action

            //EKEndTurnAction end = new EKEndTurnAction(this);
            //game.sendAction(end);

            //Don't care about it right now

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
