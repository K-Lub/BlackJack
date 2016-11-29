/**
 * Player class for Blackjack game. Players hold a hand of cards, and have chips that can be bet.
 * The first player created should be the dealer. No other player should have name "Dealer".
 * 
 * @author Kristofer
 * @author Kailab
 *
 */
public class Player {
	private static final String DEALER = "Dealer";
	private String name;
	private boolean aces;
	private boolean splitHandAces;
	private boolean hasSplitHand;
	private boolean doubleDown;
	private Card[] hand;
	private Card[] splitHand;
	private int top;
	private int splitTop;
	private int chips;
	private int nextBet;
	private int betOnTable;
	private int maxChips;
	private int roundsPlayed;
	private static int players = 0;
	private boolean npc;
	private boolean firstHandShowing;
	private boolean hasStayed;
	/**
	 * Default constructor. If it is creating the first player it creates the dealer.
	 * Use for dealer creation.
	 */
	public static void ResetPlayers(){
		players = 0;
	}
	
	
	public Player(){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.nextBet = 0;
			this.npc = true;
		}
		else{
			this.name = "Player " + players;
			this.chips = 5000;
			this.nextBet = 200;
			this.npc = false;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
		this.doubleDown = false;
		this.betOnTable = 0;
	}
	/**
	 * Creates a player. If it is the first player, it creates the dealer.
	 * @param name of player
	 * @param npc is Non-Player Character
	 */
	public Player(String name, boolean npc){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.nextBet = 0;
			this.npc = true;
		}
		else{
			if(DEALER == name)
				this.name = "Player " + players;
			this.name = name;
			this.chips = 5000;
			this.nextBet = 200;
			this.npc = npc;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.npc=true;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
		this.doubleDown = false;
		this.betOnTable = 0;
	}
	/**
	 * Creates a player. If it is the first player, it creates the dealer.
	 * @param name of player
	 * @param npc is Non-Player Character
	 */
	public Player(String name, boolean npc, int startingChips){
		if(players == 0){
			this.name = DEALER;
			this.chips = 0;
			this.nextBet = 0;
			this.npc = true;
		}
		else{
			if(DEALER == name)
				this.name = "Player " + players;
			this.name = name;
			this.chips = startingChips;
			this.nextBet = 200;
			this.npc = npc;
		}
		players++;
		this.hand = new Card[24];
		this.splitHand = new Card[24];
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.hasSplitHand = false;
		this.npc=true;
		this.firstHandShowing = true;
		this.roundsPlayed = 0;
		this.maxChips = this.chips;
		this.doubleDown = false;
		this.betOnTable = 0;
	}
	/**
	 * Return name of player.
	 * @return name of player
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Return currently held chips of player.
	 * @return chips of player
	 */
	public int getChips(){
		return this.chips;
	}
	/**
	 * Return if the player has stayed in the hand
	 * @return hasStayed
	 */
	public Boolean getHasStayed(){
		return this.hasStayed;
	}
	/**
	 * Decreases player chips by chips entered.
	 * @param chips removed from player
	 */
	public void removeChips(int chips){
		this.chips -= chips;
	}
	/**
	 * Increases player chips by chips entered.
	 * @param chips given to player
	 */
	public void addChips(int chips){
		this.chips += chips;
		if(this.chips > this.maxChips)
			this.maxChips = this.chips;
	}
	/**
	 * Changes the amount bet by player.
	 * @param bet by player
	 */
	public void setBet(int bet){
		this.nextBet = bet;
	}
	
	/**
	 * Sets values needed on stay of a hand by the player
	 * @param
	 */
	public void setStay(){
		this.hasStayed = true;
		this.firstHandShowing = false;
	}
	/**
	 * Sets values needed on stay of a hand by the player
	 * @param
	 */
	public boolean getDoubleDown(){
		return doubleDown;
	}
	public void setDoubleDown(boolean doubleDown){
		this.doubleDown = doubleDown;
	}
	public void resetStay(){
		this.hasStayed = false;
		this.firstHandShowing = false;
	}
	/**
	 * Changes the amount bet by player.
	 * @param bet by player
	 */
	public void decreaseBet(int bet){
		this.nextBet -= bet;
		this.betOnTable = this.nextBet;
	}
	/**
	 * Changes the amount bet by player.
	 * @param bet by player
	 */
	public void increaseBet(int bet){
		this.nextBet += bet;
		this.betOnTable = this.nextBet;
	}
	public void setBetOnTable(int bet){
		this.betOnTable = bet;
	}
	public int getBetOnTable(){
		return this.betOnTable;
	}
	/**
	 * Returns the current bet of this player.
	 * @return bet of this player
	 */
	public int getBet(){
		return this.nextBet;
	}
	public boolean firstHandShowing(){
		return firstHandShowing;
	}
	/**
	 * Returns true if this player is Non-Player Character.
	 * @return true if this player is Non-Player Character
	 */
	public boolean isNPC(){
		return this.npc;
	}
	/**
	 * Return true if the current hand can be split.
	 * @return true if the current hand can be split
	 */
	public boolean canSplit(){
		//Must have only two cards, faces must match, and have enough chips to split.
		if(hasSplitHand)
			return false;
		if(top==2 && hand[0].face == hand[1].face && this.chips-this.getBet()>=0 && this.getBet() > 0 && this.name != DEALER)
			return true;
		return false;
	}
	
	public boolean canDouble(){
		//Must have two cards and enough to bet
		if(top==2 && this.chips-this.getBetOnTable()>=0 && this.getBetOnTable() > 0 && this.name != DEALER && !this.hasStayed && !doubleDown)
			return true;
		return false;
	}
	
	/**
	 * Splits this player's hand into two. The second card in the hand is put as the first card in the split hand.
	 * Two cards are dealt from the deck, one to the hand and one to the split hand.
	 * Should only be called after an if(player.canSplit())
	 */
	public void splitHand(){
		firstHandShowing = false;
		this.hasSplitHand = true;
		this.receiveSplitHandCard(this.hand[--top]);
		this.splitHandAces = this.aces;
		this.removeChips(this.getBet());
		// Having a Singleton deck class just came in handy.
		this.receiveCard(Deck.getInstance().dealCard());
		this.receiveSplitHandCard(Deck.getInstance().dealCard());
		this.setBetOnTable((this.getBetOnTable()*2));
	}
	/**
	 * Return true if the current hand is Blackjack.
	 * @return true if the current hand is Blackjack
	 */
	public boolean hasBlackjack(){
		if(top==2){
			if(hand[0].face == Card.Face.ACE && hand[1].getValue()==10){
				return true;
			}
			if(hand[1].face == Card.Face.ACE && hand[0].getValue()==10){
				return true;
			}
		}
		return false;
	}
	/**
	 * Return true if the current split hand is Blackjack.
	 * Should only be called after an if(player.hasSplitHAnd())
	 * @return true if the current split hand is Blackjack
	 */
	public boolean hasSplitHandBlackjack(){
		if(splitTop==2){
			if(splitHand[0].face == Card.Face.ACE && splitHand[1].getValue()==10){
				return true;
			}
			if(splitHand[1].face == Card.Face.ACE && splitHand[0].getValue()==10){
				return true;
			}
		}
		return false;
	}
	/**
	 * Passes a card to the player that is stored in the Card array at position top.
	 * @param card added to player Card array
	 */
	public void receiveCard(Card card){
		hand[top++] = card;
		if(top > 2)
			this.firstHandShowing = false;
		if(card.face == Card.Face.ACE){
			this.aces = true;
		}
	}
	/**
	 * Returns true if this player has a split hand
	 * @return true if this  player has a split hand
	 */
	public boolean hasSplitHand(){
		return this.hasSplitHand;
	}
	/**
	 * Passes a card to this player that is stored in the split hand Card array at position splitTop.
	 * Should only be called after an if(player.hasSplitHAnd())
	 * @param card added to player split hand Card array
	 */
	public void receiveSplitHandCard(Card card){
		splitHand[splitTop++] = card;
		if(card.face == Card.Face.ACE){
			splitHandAces = true;
		}
	}
	/**
	 * Returns a string representation of this player's hand.
	 * If this player is named "Dealer", the second card is hidden until the end of the hand.
	 * @return String player's hand.
	 */
	public String getHand(){
		StringBuilder s = new StringBuilder("");
		// next line for testing. Displays the hand value. (Dealer value is always shown too)
		//s.append(getHandValue() + " ");
		for(int i = 0; i < top; i++){
			if(this.name.equals(DEALER)&& i==1 &&firstHandShowing){
				s.append("?? ");
				firstHandShowing = false;
			}
			else
				s.append(hand[i].toString() + " ");
		}
		if(this.hasSplitHand){
			s.append(" || ");
			for(int i = 0; i < splitTop;i++){
				s.append(splitHand[i].toString() + " ");
			}
		}
		return s.toString();
	}
	/**
	 * Returns the current value of this player's hand.
	 * If there are any Aces in the hand, it will return the highest value under 22.
	 * @return
	 */
	public int getHandValue(){
		int i = 0;
		for(int j = 0; j < top; j++){
			i += hand[j].getValue();
		}
		if(this.aces && i + 10 <= 21){
			i += 10;
		}
		return i;
	}
	/**
	 * Returns the current value of this player's split hand.
	 * If there are any Aces in the hand, it will return the highest value under 22.
	 * Should only be called after an if(player.hasSplitHAnd())
	 * @return
	 */
	public int getSplitHandValue(){
		int i = 0;
		for(int j = 0; j < splitTop; j++){
			i += splitHand[j].getValue();
		}
		if(splitHandAces && i + 10 <= 21){
			i += 10;
		}
		return i;
	}
	/**
	 * Resets this players hand so they have no cards.
	 */
	public void discardHand(){
		this.top = 0;
		this.splitTop = 0;
		this.aces = false;
		this.splitHandAces = false;
		this.firstHandShowing = true;
		this.hasSplitHand = false;
		this.roundsPlayed++;
	}
	public String toString(){
		StringBuilder s = new StringBuilder("");
		s.append("Name: " + this.name + " Max Chips: " + this.maxChips + " Rounds Played: " + this.roundsPlayed);
		return s.toString();
		
	}
}
