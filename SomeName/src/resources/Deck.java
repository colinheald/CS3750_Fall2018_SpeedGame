package resources;

import java.util.*;

public class Deck {

    private Stack<Card> cards;
    //private List<Card> player1Hand;
    //private List<Card> player2Hand;
    //private Stack<Card> player1Pile;
    //private Stack<Card> player2Pile;
    private Stack<Card> leftStalemate;
    private Stack<Card> rightStalemate;
    //private Stack<Card> playPile1;
    //private Stack<Card> playPile2;
    //private Stack<Card> playPile3;
    // private Stack<Card> playPile4;
    private List<Stack<Card>> playPiles;
    private List<ArrayList<Card>> playerHands;
    private List<Stack<Card>> playerDecks;


    /**
     * Default constructor.  Initializes variables, creates a full 52 card deck.
     * Shuffles the deck and deals to both players.  Game is ready for start of play.
     */
    public Deck()
    {
        //initialize all the collections
        cards = new Stack<>();
        //player1Hand = new ArrayList<Card>();
        //player2Hand = new ArrayList<Card>();
        //Stack<Card> player1Pile = new Stack<Card>();
        //Stack<Card> player2Pile = new Stack<Card>();
        leftStalemate = new Stack<>();
        rightStalemate = new Stack<>();
        //Stack<Card> playPile1 = new Stack<Card>();
        //Stack<Card> playPile2 = new Stack<Card>();
        //Stack<Card> playPile3 = new Stack<Card>();
        //Stack<Card> playPile4 = new Stack<Card>();
        playPiles = new ArrayList<>();
        playerHands = new ArrayList<>();
        playerDecks = new ArrayList<>();

        //initialize the play piles
        for(int i = 0; i < 4; i++){
            playPiles.add(new Stack<>());

        }

        //initialize the player decks and hands
        for(int i = 0; i < 2; i++){
            playerHands.add(new ArrayList<>());
            playerDecks.add(new Stack<>());
        }

        //create an ordered deck
        for(CardValue cv : CardValue.values()){
            cards.push(new Card(cv.name(),Suit.CLUB,cv.getValue()));
            cards.push(new Card(cv.name(),Suit.HEART,cv.getValue()));
            cards.push(new Card(cv.name(),Suit.SPADE,cv.getValue()));
            cards.push(new Card(cv.name(),Suit.DIAMOND,cv.getValue()));
        }


        //shuffle the deck
        Shuffle();

        //Deal cards to each player
        DealCards();
    }

    //public Card getCard(int index){

      //  return cards.get(index);
    //}

    /**
     * Method to access the visible cards in a player's hand
     * @param playerIndex 0 or 1 for the players in a two player game
     * @return List This returns a list of cards.
     */
    public ArrayList<Card> getPlayerHand(int playerIndex){
        return playerHands.get(playerIndex);
    }


    /**
     * Method to execute when the game is in stalemate.  Accepts no parameters.  If
     * there are cards left on the stalemate piles, they are flipped over per game rules.
     * Otherwise, the game piles are collected, shuffled, and redealt.
     *
     */
    public void Stalemate(){
        //if there are still cards in the stalemate piles, flip them
        if(!leftStalemate.empty() && !rightStalemate.empty()){
            playPiles.get(0).push(leftStalemate.pop());
            playPiles.get(3).push(rightStalemate.pop());
        }
        //if the stalemate piles are empty
        else{
            // clear the deck list
            cards.clear();

            //take all the cards from the play piles and put them back in the deck
            for(int i = 0; i < 4; i++){
                while(!playPiles.get(i).empty()){
                    cards.add(playPiles.get(i).pop());
                }
            }

            //reshuffle the deck
            Shuffle();

            //redeal the cards
            SetPiles();
        }
    }

    /**
     * Method to get a list of the visible top cards from each of
     * the play piles
     * @return ArrayList This returns the 4 cards in an ArrayList
     */
    public ArrayList<Card> GetTopCards(){
        ArrayList<Card> topCards = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            topCards.add(playPiles.get(i).peek());
        }
        return topCards;
    }

    /**
     * Method called when a card is played by a player
     * @param playerIndex int The index number of the player who played the card (0 or 1).
     * @param cardIndex int The index of the card played (0-4)
     * @param playPileIndex The index of the pile upon which the card was played ( 0-3)
     * @return True if the move is legal.  False, if the move is not legal.  If the move is legal, the card is
     * removed from the players hand and added to the play pile.  The player's hand is NOT replenished automatically.
     */
    public boolean PlayACard(int playerIndex, int cardIndex, int playPileIndex){
        int sourceCardValue = playerHands.get(playerIndex).get(cardIndex).getValue();
        int destinationCardValue = playPiles.get(playPileIndex).peek().getValue();
        boolean legalMove = false;
        //Determine if the card can be legally played on that pile

        if(Math.abs(sourceCardValue - destinationCardValue) == 1){
            legalMove = true;
        }
        else if ((sourceCardValue == 2 && destinationCardValue == 14) ||
                (sourceCardValue == 14 && destinationCardValue == 12)){
            legalMove = true;
        }
        else {
            legalMove = false;
        }

        if(legalMove){
            //add the card to the play pile
            playPiles.get(playPileIndex).push(playerHands.get(playerIndex).get(cardIndex));
            //remove the card from the player hand
            playerHands.get(playerIndex).remove(cardIndex);
        }

        return legalMove;
    }
    
    /**
     * simple method to see if there are cards left in the deck
     */
    public boolean canDeckDraw(int pIndex)
	{
		if(playerDecks.get(pIndex).empty())
		{
			return false;
		}
		
		return true;
	}

    /**
     * Method to draw a card from the player's pile, into the player's hand
     * @param playerIndex index number for the player (0 or 1
     * @return true, if there was a card available to draw, or false, if the pile is empty.
     */
    public boolean DrawCard(int playerIndex){
        boolean success;
        if(playerDecks.get(playerIndex).empty()){
            success = false;
        }
        else{
            playerHands.get(playerIndex).add(playerDecks.get(playerIndex).pop());
            success = true;
        }
        return success;
    }

    private void DealCards(){
        //20 cards in each personal deck
        //5 cards in each hand
        //4 playable stacks
        //2 piles of 4 cards each for stalemates

        //deal to each player's deck
        for(int i = 0; i < 20; i++){
            playerDecks.get(0).push(cards.pop());
            playerDecks.get(1).push(cards.pop());
        }

        //set each players hand
        for ( int i = 0; i < 5; i++  ){
            playerHands.get(0).add(playerDecks.get(0).pop());
            playerHands.get(1).add(playerDecks.get(1).pop());
        }

        //start the play piles
        //playPile1.push(cards.pop());
        //playPile2.push(cards.pop());
        //playPile3.push(cards.pop());
        //playPile4.push(cards.pop());

        SetPiles();
    }



    private void SetPiles(){

        for(int i = 0; i < 4; i++){
            playPiles.get(i).push(cards.pop());
        }

        //deal to the stalemate piles

        while(!cards.empty()){
            leftStalemate.push(cards.pop());
            rightStalemate.push(cards.pop());
        }
    }

    private void Shuffle(){
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
    }
}