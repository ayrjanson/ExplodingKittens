package com.example.explodingkittens.infoMessage;


import com.example.gameframework.infoMessage.GameState;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ExplodingKittensGameState: Creates the decks and assigns and moves cards according to player
 * actions
 * @author Anna Yrjanson
 * @author Audrey Sauter
 * @author Claire Porter
 * @author Alex Nastase
 */

public class EKState extends GameState {


    /*
    Create an arrayList of imageButtons, (in what class?) that can link to every button in the gui
    set a static image for back of card, that we can use to "hide" a card value for draw, discard etc
     */
    public ArrayList<ArrayList<Card>> deck;
    public ArrayList<Card> discard;
    public ArrayList<Card> draw;
    public boolean[] playerStatus;
    public STATE gameState;
    public static final int NUM_PLAYERS = 4;
    public int playerTurn;
    public boolean justPlayedSeeFuture;
    public boolean justDemandedACard;
    public String lastMessage;
    public int stealFromPlayer = -1;

    public static final int DRAWCARD = 4000;
    public static final int SKIPTURN = 4001;
    public static final int ATTACKPLAYER = 4002;
    public static final int LOST = 4003;

    /**
     * ExplodingKittensGameState: creates the various decks for the players, draw, and discard piles
     * @param size: the number of players to declare hands for
     */
    public EKState(int size) {
        super();
        this.deck = new ArrayList<>(NUM_PLAYERS);
        this.draw = new ArrayList<>(52);
        this.discard = new ArrayList<>(52);
        this.playerTurn = 0;
        this.playerStatus = new boolean[] {true, true, true, true};
        justPlayedSeeFuture = false;
        for(int i = 0; i< size; i++){
            this.deck.add(new ArrayList<>(7));
        }
        gameState = STATE.INIT_ARRAYS;
        lastMessage = "";
        this.prepareGame();

    }

    /**
     * ExplodingKittensGameState Deep Copy
     * @param state: the target instance of EKGS to deep copy
     */
    public EKState(EKState state){
        if(state == null){
            return;
        }
        this.playerTurn = state.playerTurn;
        draw = new ArrayList<>();
        for (int i = 0; i < state.draw.size(); i++) {
            draw.add(state.draw.get(i));
        }

        discard = new ArrayList<>();
        for (int i = 0; i < state.discard.size(); i++) {
            discard.add(state.discard.get(i));
        }

        deck = new ArrayList<>(NUM_PLAYERS);
        for (int i = 0; i < state.deck.size(); i++) {
            deck.add(new ArrayList<>(7));
            for (int j = 0; j < state.deck.get(i).size(); j++ ) {
                deck.get(i).add(new Card(state.deck.get(i).get(j)));
            }
        }
        this.gameState = state.gameState;
        this.lastMessage = state.lastMessage;
        this.justPlayedSeeFuture = state.justPlayedSeeFuture;
        this.playerStatus = new boolean[state.playerStatus.length];
        for (int i = 0; i < state.playerStatus.length; i++) {
            playerStatus[i] = state.playerStatus[i];
        }

    }

    /**
     * Ends the current player's turn, and executes specific commands based on the circumstances
     * under which the turn should be ended
     * @param playerTurn - ID of the player whose turn is being ended
     * @param reason - calls in static variables to represent all the manners in which a player can
     *               end their turn, such as drawing a card, playing a skip, attack, or losing
     */

    public void endTurn(int playerTurn, int reason){
        switch(reason){
            case DRAWCARD:
                try {
                    CARDTYPE temp = this.draw.get(0).cardType;
                    this.deck.get(this.getPlayerTurn()).add(takeFromDraw());
                    this.draw.remove(0);
                    if(temp == CARDTYPE.EXPLODE){
                        playCard(playerTurn, CARDTYPE.DEFUSE, this.deck.get(playerTurn));
                    }
                    for(Card card: deck.get(playerTurn)) {
                        card.isPlayable = false;
                        card.isSelected = false;
                    }
                    this.nextPlayer(this.playerTurn);
                } catch (IndexOutOfBoundsException e) {
                    return;
                }
                break;
            case SKIPTURN:
                for( Card card: this.deck.get(playerTurn)){
                    card.isPlayable = false;
                    card.isSelected = false;
                }
                this.nextPlayer(this.playerTurn);
                break;
            case ATTACKPLAYER:
                this.nextPlayer(this.playerTurn);
                CARDTYPE temp2 = draw.get(0).getType();
                this.deck.get(this.getPlayerTurn()).add(takeFromDraw());
                draw.remove(0);

                if(temp2 == CARDTYPE.EXPLODE){
                    playCard(playerTurn, CARDTYPE.DEFUSE, deck.get(playerTurn));
                }
                for(Card card: deck.get(playerTurn)) {
                    card.isPlayable = false;
                    card.isSelected = false;
                }
                break;
            case LOST:
                if(deck.get(playerTurn)!=null) {
                    moveToDiscard(deck.get(playerTurn), discard);
                }
                playerStatus[this.playerTurn] = false;
                this.nextPlayer(this.playerTurn);
                break;
        }
        this.lastMessage = "It is now Player " + this.playerTurn + "'s turn.";
    }

    /**
     * moveToDiscard
     * @param src
     * @param dest
     */
    public void moveToDiscard(ArrayList<Card> src, ArrayList<Card> dest) {
        // Easy Case: Already done
        if (src.size() == 0) return;
        else {
            dest.add(src.get(0));
            src.remove(0);
            moveToDiscard(src, dest);
        }
    }

    /**
     * The helper function to move a card obj from src to dest arrayLists iff it contains that card
     * @param card - card object to be moved
     * @param src - source ArrayList
     * @param dest - destination ArrayLIst
     * @return - returns true if it was able to move, false if not
     */
    public boolean moveStart(Card card, ArrayList<Card> src, ArrayList<Card> dest){
        if(src.contains(card)){
            dest.add(card);
            src.remove(card);
            return true;
        }
        return false;
    }

    /**
     * Draws a card from the draw pile arrayList and removes it from there
     * @return - returns the card object that was drawn
     */
    public Card takeFromDraw() {
        Card fromDraw = draw.get(0);
        //draw.remove(0);
        return fromDraw;
    }


    /**
     * playCard:
     * @param playerTurn - the index of the player who is playing the card
     * @param card - the card being played
     * @param src - the source array for the card being played
     * @return boolean - whether play card executed or nor
     */
    //TODO test each playcard
    public boolean playCard(int playerTurn, CARDTYPE card, ArrayList<Card> src){
        switch(card){
            case MELON:
                if (src.equals(deck.get(playerTurn))) {
                    int moveMelon = getCardIndex(CARDTYPE.MELON, deck.get(playerTurn));
                    Card moveCardMelon = getCard(CARDTYPE.MELON, deck.get(playerTurn));
                    if (moveMelon != -1) {
                        moveCardMelon.isSelected = false;
                        discard.add(moveCardMelon);
                        deck.get(playerTurn).remove(moveMelon);
                        return true;
                    }
                }
                break;
            case BEARD:
                if (src.equals(deck.get(playerTurn))) {
                    int moveBeard = getCardIndex(CARDTYPE.BEARD, deck.get(playerTurn));
                    Card moveCardBeard = getCard(CARDTYPE.BEARD, deck.get(playerTurn));
                    if (moveBeard != -1) {
                        moveCardBeard.isSelected = false;
                        discard.add(moveCardBeard);
                        deck.get(playerTurn).remove(moveBeard);
                        return true;
                    }
                }
                break;
            case POTATO:
                if (src.equals(deck.get(playerTurn))) {
                    int movePotato = getCardIndex(CARDTYPE.POTATO, deck.get(playerTurn));
                    Card moveCardPotato = getCard(CARDTYPE.POTATO, deck.get(playerTurn));
                    if (movePotato != -1) {
                        moveCardPotato.isSelected = false;
                        discard.add(moveCardPotato);
                        deck.get(playerTurn).remove(movePotato);
                        return true;
                    }
                }
                break;
            case TACO:
                if (src.equals(deck.get(playerTurn))) {
                    int moveTaco = getCardIndex(CARDTYPE.TACO, deck.get(playerTurn));
                    Card moveCardTaco = getCard(CARDTYPE.TACO, deck.get(playerTurn));
                    if (moveTaco != -1) {
                        moveCardTaco.isSelected = false;
                        discard.add(moveCardTaco);
                        deck.get(playerTurn).remove(moveTaco);
                        return true;
                    }
                }
                break;
            case ATTACK:
                if (src.equals(deck.get(playerTurn))) {
                    int moveAttack = getCardIndex(CARDTYPE.ATTACK, deck.get(playerTurn));
                    Card moveCardAttack = getCard(CARDTYPE.ATTACK, deck.get(playerTurn));
                    if (moveAttack != -1) {
                        discard.add(moveCardAttack);
                        deck.get(playerTurn).remove(moveAttack);
                        endTurn(playerTurn, ATTACKPLAYER);
                        return true;
                    }
                }
                break;
            case SHUFFLE:
                if(src.equals(deck.get(playerTurn))) {
                    int moveShuffle = getCardIndex(CARDTYPE.SHUFFLE, deck.get(playerTurn));
                    Card moveCardShuffle = getCard(CARDTYPE.SHUFFLE, deck.get(playerTurn));
                    if (moveShuffle != -1) {
                        discard.add(moveCardShuffle);
                        deck.get(playerTurn).remove(moveShuffle);
                        Collections.shuffle(draw);
                        return true;
                    }
                }
                break;
            case FAVOR:
                if(src.equals(deck.get(playerTurn))) {
                    int moveFavor = getCardIndex(CARDTYPE.FAVOR, deck.get(playerTurn));
                    Card moveCardFavor = getCard(CARDTYPE.FAVOR, deck.get(playerTurn));
                    if (moveFavor != -1) {
                        //TODO: Make so able to take another player's card
                        discard.add(moveCardFavor);
                        deck.get(playerTurn).remove(moveFavor);
                        //this.justDemandedACard = true;
                        stealACard((int)(Math.random()* 3));
                        return true;
                    }
                }
                break;
            case SKIP:
                if(src.equals(deck.get(playerTurn))) {
                    int moveSkip = getCardIndex(CARDTYPE.SKIP, deck.get(playerTurn));
                    Card moveCardSkip = getCard(CARDTYPE.SKIP, deck.get(playerTurn));
                    if (moveSkip != -1) {
                        discard.add(moveCardSkip);
                        deck.get(playerTurn).remove(moveSkip);
                        endTurn(playerTurn,SKIPTURN);
                        return true;
                    }
                }
                return false;
            case SEEFUTURE:
                if(src.equals(deck.get(playerTurn))) {
                    int moveSeeFuture = getCardIndex(CARDTYPE.SEEFUTURE, deck.get(playerTurn));
                    Card moveCardSeeFuture = getCard(CARDTYPE.SEEFUTURE, deck.get(playerTurn));
                    if (moveSeeFuture != -1) {
                        discard.add(moveCardSeeFuture);
                        deck.get(playerTurn).remove(moveSeeFuture);
                        this.justPlayedSeeFuture = true;

                        return true;
                    }
                }
                break;
            case NOPE:
                if(src.equals(deck.get(playerTurn))) {
                    int moveNope = getCardIndex(CARDTYPE.NOPE, deck.get(playerTurn));
                    Card moveCardNope = getCard(CARDTYPE.NOPE, deck.get(playerTurn));
                    if (moveNope != -1) {
                        discard.add(moveCardNope);
                        deck.get(playerTurn).remove(moveNope);
                        return true;
                    }
                }
                break;
            case DEFUSE:
                //TODO check indexes for behavior
                int moveExplode = getCardIndex(CARDTYPE.EXPLODE, deck.get(playerTurn));
                int moveDefuse = getCardIndex(CARDTYPE.DEFUSE, deck.get(playerTurn));
                Card moveCardExplode = getCard(CARDTYPE.EXPLODE, deck.get(playerTurn));
                Card moveCardDefuse = getCard(CARDTYPE.DEFUSE, deck.get(playerTurn));
                if(moveExplode != -1 && moveDefuse != -1){
                    discard.add(moveCardExplode);
                    discard.add(moveCardDefuse);
                    deck.get(playerTurn).remove(moveExplode);
                    deck.get(playerTurn).remove(moveDefuse);
                    return true;
                }
                // This condition would ideally identify that the player wants to play the defuse card
                else if(moveDefuse != -1 && moveExplode == -1) {
                    return false;
                }
                //FIXME playing a defuse makes this else look execute????
                else{
                    endTurn(playerTurn, LOST);
                    return false;
                }
            case EXPLODE:
                moveExplode = getCardIndex(CARDTYPE.EXPLODE, deck.get(playerTurn));
                moveDefuse = getCardIndex(CARDTYPE.DEFUSE, deck.get(playerTurn));
                moveCardExplode = getCard(CARDTYPE.EXPLODE, deck.get(playerTurn));
                moveCardDefuse = getCard(CARDTYPE.DEFUSE, deck.get(playerTurn));
                if(moveExplode != -1 && moveDefuse != -1) {
                    discard.add(moveCardExplode);
                    discard.add(moveCardDefuse);
                    deck.get(playerTurn).remove(moveExplode);
                    deck.get(playerTurn).remove(moveDefuse);
                    ArrayList<Card> reference = deck.get(playerTurn);
                    return true;
                }else{
                    endTurn(playerTurn,LOST);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + card);
        }
        return false;
    }

    /**
     * getter method to get the index of a card in an arrayList
     * @param type - the cardtype in mind to search for an object for
     * @param src - the source arrayList to look in
     * @return - returns the index that the card object is located at, or -1 if it is not found in src
     */
    public int getCardIndex(CARDTYPE type, ArrayList<Card> src) {
        for(int index = 0; index < src.size(); index++) {
            if(src.get(index).getType() == type) {
                return index;
            }
        }
        return -1;
    }

    /**
     * gets a card object of a certain type from a source arrayLIst
     * @param type - cardtype to search for
     * @param src - the source arrayList to search in
     * @return - retruns the card Object if it exists, null if it doesn't exist
     */
    public Card getCard(CARDTYPE type, ArrayList<Card> src){
        for(Card card: src){
            if(card.getType() == type){
                return card;
            }
        }
        return null;
    }

    /**
     * computes the integer number of the next player in a turn sequence (loops around through NUM PLAYERS
     * @param currentPlayer - current player index whose turn it is
     * @return - returns the integer of the next player that is in the game still
     */
    public int nextPlayer(int currentPlayer) {
        if(endGame(this.playerStatus) == -1) {
            if (currentPlayer > 2) {
                currentPlayer = 0;
            } else currentPlayer++;

            if (playerStatus[currentPlayer] != false) {
                playerTurn = currentPlayer;
                return currentPlayer;
            } else return nextPlayer(currentPlayer);
        }else{
            lastMessage = "Player " + gameOver() + " has won.";
           }
        return currentPlayer;
    }

    /**
     * Checks if the current player index is valid or not (in the game or out)
     * @param currentPlayer - index of current player
     * @return - true if player is still in the game, false if not
     */
    public boolean checkIfValid(int currentPlayer) {
        if (deck.get(currentPlayer) != null) return true;
        else return false;
    }

    /**
     * Creats a string of the big info about the EKgamestate, like current player, the contents of
     * all the ArrayList<Card>,
     * @return - String - the info string
     */
    @Override
    public String toString(){
        String string ="****************\n" + "Current Player: " + this.playerTurn + "\n\n DECK: \n"  + " Size: " + deck.size() + "\n"+ this.deck.toString() +
                "\n\n DRAW: \n" + " Size: " + draw.size() + "\n"+ this.draw.toString() + "\n\n DISCARD: \n" + this.discard.toString()
                + "\n\n";
        return string;
    }

    /**
     * PrepareGame: calls createCards(), and then shuffles the draw pile. Iterates through the 4 player hands in
     * deck, and adds the first 6 cards into a player hand iff it isnt an exploding kitten using move()
     * and finally sets the gameState to GAME_SETUP
     * @return - true if it executed properly, false if gameState is not init_objects, probably
     *           because function was called in incorrect order
     */
    public boolean prepareGame() {
        //hopefully init object state means there are not exploding kittens created yet
        if (createCards()) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 6; j++) {
                    moveStart(this.draw.get(j), this.draw, this.deck.get(i));
                }
            }
            //for testing comment out
            //this.deck.get(0).add(new Card(CARDTYPE.SEEFUTURE));
            //this.deck.get(0).add(new Card(CARDTYPE.SEEFUTURE));
            this.draw.add(new Card(CARDTYPE.EXPLODE));
            this.draw.add(new Card(CARDTYPE.EXPLODE));
            this.draw.add(new Card(CARDTYPE.EXPLODE));

            //SHUFFLE HANDS AND DRAW + DIScARD
            for (int i = 0; i < 4; i++) {
                Collections.shuffle(deck.get(i));
            }
            Collections.shuffle(draw);
            Collections.shuffle(discard);
            gameState = STATE.GAME_SETUP;
            return true;
        }
        return false;
    }

    /**
     * createCards: creates a hashtable with the card types and their enum values, creates card
     * objects for the number of that type of card in the deck for a four-player game
     * @return - true if actions were executed, false if gamestate is not INIT_ARRAYS,
     *           probs bc it is called out of order
     */
    public boolean createCards() {
        //sets the hash table keys and strings to the card description, and the card ID.
        if (gameState == STATE.INIT_ARRAYS){
            for (int i = 0; i < 4; i++) {
                this.draw.add(new Card(CARDTYPE.ATTACK));
                this.draw.add(new Card(CARDTYPE.FAVOR));
                this.draw.add(new Card(CARDTYPE.NOPE));
                this.draw.add(new Card(CARDTYPE.SHUFFLE));
                this.draw.add(new Card(CARDTYPE.SKIP));
                this.draw.add(new Card(CARDTYPE.SEEFUTURE));
                this.draw.add(new Card(CARDTYPE.MELON));
                this.draw.add(new Card(CARDTYPE.BEARD));
                this.draw.add(new Card(CARDTYPE.TACO));
                this.draw.add(new Card(CARDTYPE.POTATO));
            }

            for (int i = 0; i < 4; i++) {
                deck.get(i).add(new Card(CARDTYPE.DEFUSE));
            }

            this.draw.add(new Card(CARDTYPE.DEFUSE));
            this.draw.add(new Card(CARDTYPE.DEFUSE));
            this.draw.add(new Card(CARDTYPE.NOPE));
            this.draw.add(new Card(CARDTYPE.SEEFUTURE));
            gameState = STATE.INIT_OBJECTS;
            return true;
        }
        return false;
    }

    /**
     * Tests if the game is over
     * @param playerStatus - an array indicated which players are in the game (true) and which are
     *                     out (false)
     * @return - index # of winner if there is only one in (true) player in playerStatus, -1 if there are more
     * than one players still playing
     */
    public int endGame(boolean[] playerStatus){
        int out = 0;
        for(int i = 0; i < playerStatus.length; i++){
            if(playerStatus[i] == false) {
                out++;
            }
        }
        if(out == 3){
            for (int i = 0; i < playerStatus.length; i++) {
                if (playerStatus[i] == true) {
                    gameState = STATE.GAME_END;
                    return i;
                }
            }
        }
        return -1;
    }

    public int getPlayerTurn() { return playerTurn; }

    public int gameOver() {
        return endGame(playerStatus);
    }

    public boolean[] getPlayerStatus() { return playerStatus; }


    public boolean equals(EKState state){
        if(!(this.justPlayedSeeFuture == state.justPlayedSeeFuture)) return false;

        if(!(this.lastMessage.equals(state.lastMessage))) return false;

        //Check the STATE gameState variables
        if (!(this.gameState == state.gameState)) return false;

        //Checks the player turn
        if (!(this.playerTurn == state.playerTurn)) return false;

        //Check the player status arrays to determine eligible players
        for (int i = 0; i < this.playerStatus.length; i++) {
            if (!(this.playerStatus[i] == state.playerStatus[i])) return false;
        }

        //Checks the draw pile
        for (int i = 0; i < this.draw.size(); i++) {
            if(!(this.draw.get(i).equals(state.draw.get(i)))) return false;
        }

        //Checks the discard pile
        for (int i = 0; i < this.discard.size(); i++) {
            if(!(this.discard.get(i).equals(state.discard.get(i)))) return false;
        }

        //Checks the player hands
        for (int i = 0; i < this.deck.size(); i++) {
            for (int j = 0; j < this.deck.get(i).size(); j++) {
                if(!(this.deck.get(i).get(j).equals(state.deck.get(i).get(j)))) return false;
            }
        }
        return true;
    }

    public void stealACard(int playerIdx){
        int targetSize = deck.get(playerIdx).size();
        int stealIdx = (int) (Math.random()*targetSize);
        Card stolenCard = deck.get(playerIdx).get(stealIdx);
        deck.get(playerIdx).remove(stealIdx);
        deck.get(playerTurn).add(stolenCard);
    }

    public boolean compareArray (ArrayList<Card> draw){
        for (int i = 0; i < draw.size()-1; i++) {
            Card card = draw.get(i);
            for (int j = i + 1; j < draw.size(); j++) {
                Card card1 = draw.get(j);
                if (!card1.equals(card)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean compareList (ArrayList<Card> draw, ArrayList<Card> shuffle){
        if(draw.size() != shuffle.size()){
            return false;
        }else {
            for (int i = 0; i < draw.size(); i++) {
                if(!draw.get(i).equals(shuffle.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}


