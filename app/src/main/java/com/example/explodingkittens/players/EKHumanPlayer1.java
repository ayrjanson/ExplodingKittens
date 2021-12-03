package com.example.explodingkittens.players;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.explodingkittens.ekActionMessage.EKPlayCardAction;
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

/**
 * EKHumanPlayer1 - Updates the GUI based on the game state and sends actions based on human player
 * interaction
 * @author Anna Yrjanson
 * @author Audrey Sauter
 * @author Alex Nastase
 */

public class EKHumanPlayer1 extends GameHumanPlayer implements View.OnClickListener {
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
    private TextView logView = null;

    private static final String TAG = "EKHumanPlayer1";

    private int layoutId;
    private GameMainActivity myActivity;
    private ArrayList<ImageButton> cards = new ArrayList<>(5);
    private EKState state;
    private int currIdx = 0;
    int myPlayerNum;
    int numCardsDisplay;

    //Hashtable of CardType image resources
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
        put(CARDTYPE.BEARD_SELECTED,R.drawable.beard_selected);
        put(CARDTYPE.MELON_SELECTED,R.drawable.melon_selected);
        put(CARDTYPE.POTATO_SELECTED,R.drawable.potato_selected);
        put(CARDTYPE.TACO_SELECTED,R.drawable.taco_selected);
    }};

    //HashMap of the human player hand button display
    HashMap<Integer,CARDTYPE> buttonCardMap = new HashMap()
    {{
       put(R.id.playerCard1,null);
       put(R.id.playerCard2,null);
       put(R.id.playerCard3,null);
       put(R.id.playerCard4,null);
       put(R.id.playerCard5,null);

    }};

    /**
     * EKHumanPlayer1 - creates a human player object
     * @param name - the name of the player
     * @param layoutId - ID of the layout
     */

    public EKHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public View getTopView() {
        return null;
    }

    /**
     * receiveInfo - receives the state and takes human GUI input to determine actions to send to
     * the LocalGame class
     * @param info - information which contains the game state
     */
    //FIXME: the recently drawn card does not show up when scrolling through deck
    @Override
    public void receiveInfo(GameInfo info) {
        //If the GameInfo (and game state) have no info, return
        if(info == null){
        return;
        }

        // if the move was out of turn or otherwise illegal, flash the screen
        else if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {

        }
        else if (!(info instanceof EKState))
            // if we do not have a EKState, ignore
            return;

        else {
            //Update every image button to match what is in the gamestate
            this.state = new EKState((EKState) info);
            logView.setText(this.state.lastMessage);
            if(state.lastMessage.length() == 0){
                logView.setText("No Message");
            }
            myPlayerNum = ((LocalGame) game).getPlayerIdx(this);

            int lastDiscard = state.discard.size();
            Card discardCard;
            if (lastDiscard > 0) {
                discardCard = state.discard.get(lastDiscard-1);
                discardPile.setImageResource(imageTable.get(discardCard.getType()));
            } else {
                discardPile.setImageResource(R.drawable.back);
            }
            numCardsDisplay = playerCards.size();
            //DISPLAY SEE FUTURE
            /*FIXME: see future doesnt display sometimes, only seems to call if the player tabs all the
            way to the right, idk why, the thread may call recursively unintentionally because
            it kept trying to "update cards for seefuture" so maybe it isnt updating
            FIXME also doesnt stop displaying first three cards, and crashes if i click how do i delay without ruining
*/
            if(state.justPlayedSeeFuture){
                logView.setText("Updating card images for seefuture");
                state.justPlayedSeeFuture = false;
                if(state.draw.size() >= 3) {

                    playerCards.get(0).setImageResource(R.drawable.back);
                    playerCards.get(1).setImageResource(imageTable.get(state.draw.get(0).cardType));
                    playerCards.get(2).setImageResource(imageTable.get(state.draw.get(1).cardType));
                    playerCards.get(3).setImageResource(imageTable.get(state.draw.get(2).cardType));
                    playerCards.get(4).setImageResource(R.drawable.back);

                    }else{
                    //state.justPlayedSeeFuture = false;
                    playerCard1.setImageResource(R.drawable.back);
                    playerCard5.setImageResource(R.drawable.back);
                    for(int i = 0; i < state.draw.size(); i ++){
                        playerCards.get(i+1).setImageResource(imageTable.get(state.draw.get(i).cardType));
                    }
                    for(int i = state.draw.size()+1; i < 5; i++){
                        playerCards.get(i).setImageResource(R.drawable.back);
                    }
                }
            } else if(currIdx >= 0 && (currIdx+numCardsDisplay) <= state.deck.get(myPlayerNum).size())
                for (int i = 0; i < numCardsDisplay; i++) {
                    if(state.deck.get(myPlayerNum).get(i+currIdx).isSelected == true ){
                        CARDTYPE type = state.deck.get(myPlayerNum).get(i+currIdx).getType();
                        switch(type){
                            case TACO:
                                playerCards.get(i).setImageResource(imageTable.get(CARDTYPE.TACO_SELECTED));
                                break;
                            case BEARD:
                                playerCards.get(i).setImageResource(imageTable.get(CARDTYPE.BEARD_SELECTED));
                                break;
                            case MELON:
                                playerCards.get(i).setImageResource(imageTable.get(CARDTYPE.MELON_SELECTED));
                                break;
                            case POTATO:
                                playerCards.get(i).setImageResource(imageTable.get(CARDTYPE.POTATO_SELECTED));
                                break;
                        }
                    }else {
                        playerCards.get(i).setImageResource(imageTable.get(state.deck.get(
                                myPlayerNum).get(i + currIdx).getType()));
                    }
                    buttonCardMap.put(playerCards.get(i).getId(),state.deck.get(
                            myPlayerNum).get(i+currIdx).getType());
                }
            for (int i = state.deck.get(myPlayerNum).size(); i < playerCards.size(); i++) {
                playerCards.get(i).setImageResource(R.drawable.back);
            }
           Logger.log(TAG, "receiving");
        }

    }


    /**
     * onClick - determines which action to take based on the selected button's display
     * @param v - the view (button) ID
     */

    @Override
    public void onClick(View v) {
        if (state.playerTurn == this.myPlayerNum) {
            // Checks if the id matches the object
            // TODO: Move to this way if others don't work
            if (state.justPlayedSeeFuture) {
                return;
            }
            //if stops working add back in else below
            else if (v.getId() == R.id.discardPile) {
                ArrayList<Card> selectedCards = findSelected(state.deck.get(state.playerTurn));
                int numCardsToPlay;
                try {
                    numCardsToPlay = selectedMatching(selectedCards);
                }catch(IndexOutOfBoundsException e){
                    return;
                }
                switch (numCardsToPlay) {
                    case 1:
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                    case 2:
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                        break;
                    case 3:
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                        state.playCard(state.playerTurn, selectedCards.get(0).getType(), state.deck.get(state.playerTurn));
                        break;
                    case -1:
                        break;
                    default:
                        break;
                }
            } else if (v.getId() == R.id.drawPile) {
                EKPlayCardAction draw = new EKPlayCardAction(this, CARDTYPE.ENDTURN);
                game.sendAction(draw);
                receiveInfo(state);

            } else if (v.getId() == R.id.playerCard1) {
                if (buttonCardMap.get(R.id.playerCard1) == CARDTYPE.MELON ||
                        buttonCardMap.get(R.id.playerCard1) == CARDTYPE.BEARD ||
                        buttonCardMap.get(R.id.playerCard1) == CARDTYPE.POTATO ||
                        buttonCardMap.get(R.id.playerCard1) == CARDTYPE.TACO) {
                    if (state.deck.get(state.playerTurn).get(currIdx).isSelected == true) {
                        state.deck.get(state.playerTurn).get(currIdx).isSelected = false;
                    } else {
                        state.deck.get(state.playerTurn).get(currIdx).isSelected = true;
                    }
                    logView.setText("Player " + state.playerTurn + " selected a " + state.deck.get(state.playerTurn).get(currIdx).getType().name());
                } else {
                    CARDTYPE type = buttonCardMap.get(R.id.playerCard1);
                    EKPlayCardAction action = new EKPlayCardAction(this, type);
                    game.sendAction(action);
                    logView.setText("Player " + state.playerTurn + " played a " + type.name() + " card.");

                }
            } else if (v.getId() == R.id.playerCard2) {
                if (buttonCardMap.get(R.id.playerCard2) == CARDTYPE.MELON ||
                        buttonCardMap.get(R.id.playerCard2) == CARDTYPE.BEARD ||
                        buttonCardMap.get(R.id.playerCard2) == CARDTYPE.POTATO ||
                        buttonCardMap.get(R.id.playerCard2) == CARDTYPE.TACO) {
                    if (state.deck.get(state.playerTurn).get(currIdx + 1).isSelected == true) {
                        state.deck.get(state.playerTurn).get(currIdx + 1).isSelected = false;
                    } else {
                        state.deck.get(state.playerTurn).get(currIdx + 1).isSelected = true;
                    }
                    logView.setText("Player " + state.playerTurn + " selected a " + state.deck.get(state.playerTurn).get(currIdx + 1).getType().name());
                } else {
                    CARDTYPE type = buttonCardMap.get(R.id.playerCard2);
                    EKPlayCardAction action = new EKPlayCardAction(this, type);
                    game.sendAction(action);
                    logView.setText("Player " + state.playerTurn + " played a " + type.name() + " card.");
                    // Determine which action was called
                }
            } else if (v.getId() == R.id.playerCard3) {
                if (buttonCardMap.get(R.id.playerCard3) == CARDTYPE.MELON ||
                        buttonCardMap.get(R.id.playerCard3) == CARDTYPE.BEARD ||
                        buttonCardMap.get(R.id.playerCard3) == CARDTYPE.POTATO ||
                        buttonCardMap.get(R.id.playerCard3) == CARDTYPE.TACO) {
                    if (state.deck.get(state.playerTurn).get(currIdx + 2).isSelected == true) {
                        state.deck.get(state.playerTurn).get(currIdx + 2).isSelected = false;
                    } else {
                        state.deck.get(state.playerTurn).get(currIdx + 2).isSelected = true;
                    }
                    logView.setText("Player " + state.playerTurn + " selected a " + state.deck.get(state.playerTurn).get(currIdx + 2).getType().name());
                } else {
                    CARDTYPE type = buttonCardMap.get(R.id.playerCard3);
                    EKPlayCardAction action = new EKPlayCardAction(this, type);
                    game.sendAction(action);
                    logView.setText("Player " + state.playerTurn + " played a " + type.name() + " card.");
                    // Determine which action was called
                }
            } else if (v.getId() == R.id.playerCard4) {
                if (buttonCardMap.get(R.id.playerCard4) == CARDTYPE.MELON ||
                        buttonCardMap.get(R.id.playerCard4) == CARDTYPE.BEARD ||
                        buttonCardMap.get(R.id.playerCard4) == CARDTYPE.POTATO ||
                        buttonCardMap.get(R.id.playerCard4) == CARDTYPE.TACO) {
                    if (state.deck.get(state.playerTurn).get(currIdx + 3).isSelected == true) {
                        state.deck.get(state.playerTurn).get(currIdx + 3).isSelected = false;
                    } else {
                        state.deck.get(state.playerTurn).get(currIdx + 3).isSelected = true;
                    }
                    logView.setText("Player " + state.playerTurn + " selected a " + state.deck.get(state.playerTurn).get(currIdx + 3).getType().name());
                } else {
                    CARDTYPE type = buttonCardMap.get(R.id.playerCard4);
                    EKPlayCardAction action = new EKPlayCardAction(this, type);
                    game.sendAction(action);
                    logView.setText("Player " + state.playerTurn + " played a " + type.name() + " card.");
                    // Determine which action was called
                }
            } else if (v.getId() == R.id.playerCard5) {
                if (buttonCardMap.get(R.id.playerCard5) == CARDTYPE.MELON ||
                        buttonCardMap.get(R.id.playerCard5) == CARDTYPE.BEARD ||
                        buttonCardMap.get(R.id.playerCard5) == CARDTYPE.POTATO ||
                        buttonCardMap.get(R.id.playerCard5) == CARDTYPE.TACO) {
                    if (state.deck.get(state.playerTurn).get(currIdx + 4).isSelected == true) {
                        state.deck.get(state.playerTurn).get(currIdx + 4).isSelected = false;
                    } else {
                        state.deck.get(state.playerTurn).get(currIdx + 4).isSelected = true;
                    }
                    logView.setText("Player " + state.playerTurn + " selected a " + state.deck.get(state.playerTurn).get(currIdx + 4).getType().name());
                } else {
                    CARDTYPE type = buttonCardMap.get(R.id.playerCard5);
                    EKPlayCardAction action = new EKPlayCardAction(this, type);
                    game.sendAction(action);
                    logView.setText("Player " + state.playerTurn + " played a " + type.name() + " card.");
                    // Determine which action was called
                }
            } else if (v.getId() == R.id.handLeft) {
                //bounds checking, increments currIdx, calls recieve info to redraw
                if (currIdx - 1 >= 0 && ((currIdx - 1) + numCardsDisplay) <= state.deck.get(myPlayerNum).size()) {
                    currIdx--;
                    logView.setText("Tab Left.");
                } else {
                    logView.setText("Cannot tab left, already at beginning.");
                }
                receiveInfo(state);
            } else if (v.getId() == R.id.handRight) {
                if (currIdx >= 0 && (currIdx + numCardsDisplay) < state.deck.get(myPlayerNum).size()) {
                    currIdx++;
                    logView.setText("Tab Right");
                } else {
                    logView.setText("Cannot tab right, already at beginning.");
                }
            }
            state.justPlayedSeeFuture = false;
            state.justDemandedACard = false;
            receiveInfo(state);
        }
    }

    /**
     * setAsGui - based on GameState information, display the correct image resources to ImageButton
     * objects to then be seen by the Human Player
     * @param activity - the gameActivity
     */

    @Override
    public void setAsGui(GameMainActivity activity) {

        this.myActivity = activity;

        activity.setContentView(R.layout.activity_main); //May cause issue with layoutId above

        //Create the buttons that are in the GUI
        this.discardPile = (ImageButton)activity.findViewById(R.id.discardPile);
        this.drawPile = (ImageButton)activity.findViewById(R.id.drawPile);
        this.playerCard1 = (ImageButton)activity.findViewById(R.id.playerCard1);
        this.playerCard2 = (ImageButton)activity.findViewById(R.id.playerCard2);
        this.playerCard3 = (ImageButton)activity.findViewById(R.id.playerCard3);
        this.playerCard4 = (ImageButton)activity.findViewById(R.id.playerCard4);
        this.playerCard5 = (ImageButton)activity.findViewById(R.id.playerCard5);
        this.handLeft = (Button)activity.findViewById(R.id.handLeft);
        this.handRight = (Button)activity.findViewById(R.id.handRight);
        this.logView = (TextView)activity.findViewById(R.id.logView);

        playerCards = new ArrayList<>();
        playerCards.add(playerCard1);
        playerCards.add(playerCard2);
        playerCards.add(playerCard3);
        playerCards.add(playerCard4);
        playerCards.add(playerCard5);

        discardPile.setOnClickListener(this);
        drawPile.setOnClickListener(this);
        for (ImageButton playerCard : playerCards) {
            playerCard.setOnClickListener(this);
        }
        handLeft.setOnClickListener(this);
        handRight.setOnClickListener(this);
    }

    /**
     * findSelected - find the selected cards and then displays the "selected card" image resource
     * @param playerHand - the GUI player's hand to then be displayed
     * @return returns an ArrayList<Card> with the selected cards listed
     */

    public ArrayList<Card> findSelected(ArrayList<Card> playerHand) {
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card cards: playerHand) {
            if(cards.isSelected == true){
                selectedCards.add(cards);
            }
        }
        return selectedCards;
    }

    /**
     * selectedMatching - uses ArrayList<Card> and determines if there are matching card objects
     * @param selectedCards - the array with the selectedCards
     * @return the number of matching cards in the matchingCards array
     */

    public int selectedMatching(ArrayList<Card> selectedCards) {
        int matchNumber = 0;
        CARDTYPE c1 = selectedCards.get(0).getType();
        for(Card cards: selectedCards){
            if(c1 == cards.getType()){
                matchNumber++;
            }else{
                return -1;
            }
        }
        return matchNumber;
    }
}
