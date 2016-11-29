/**
 * First player added must be "Dealer".
 * This is the logical backend to the Blackjack game. Needs a user interface to work for player controlled characters.
 * Currently simulates NPC's playing until everyone runs out of chips.
 * 
 * 
 * @author Kristofer
 * @author Kailab
 *
 */
public class BlackjackLogic {
	// Initialize class variables.
	Player[] players;
	private static int top;
	private static int turn;
	private static int rounds;
	private StringBuilder endResults;
	public Deck deck;
	/**
	 * Default constructor contains 2 players.
	 */
	public BlackjackLogic(){
		top = 0;
		this.players = new Player[2];
		rounds = 1;
		turn = -1;
		this.endResults = new StringBuilder("Players Added\n");
		deck = Deck.getInstance();
		deck.shuffle();
	}
	/**
	 * Constructor has input for number of players.
	 * @param i number of players
	 */
	public BlackjackLogic(int players){
		top = 0;
		this.players = new Player[players];
		rounds = 1;
		turn = -1;
		this.endResults = new StringBuilder("This is the end results\n");
		deck = Deck.getInstance();
		for(int i = 3; players>=i;i+=2)
			deck.addDeck();
		deck.shuffle();
	}
	/**
	 * Adds a player to the game.
	 * @param player
	 */
	public void addPlayer(Player player){
		players[top++] = player;
		turn++;
	}
	/**
	 * Resets the game and makes the players as default players.
	 */
	public void resetGame(){
		top = 0;
		rounds = 1;
		turn = -1;
		deck.shuffle();
		this.endResults = new StringBuilder("Game Over");
		for(int i = 1; i < players.length; i++)
			endResults.append(players[i].toString());
		Player.ResetPlayers();
		for(int i = 0; i < players.length; i ++)
			this.addPlayer(new Player());
	}
	/**
	 * Return the default player when one player.
	 * @param i place in array
	 * @return Player
	 */
	public Player getPlayer(){
		return this.players[1];
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	public Player getDealer(){
		return this.players[0];
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	public Player getPlayer(int i){
		if(i<top)
			return this.players[i];
		return null;
	}
	/**
	 * Returns the number of players.
	 * @return number of players
	 */
	public int getPlayers(){
		return top;
	}
	/**
	 * Deals 1 card from the deck to each player.
	 * @param deck deals card to each player
	 */
	public void dealHand(Deck deck){
		for(int i = 0; i < top; i ++){
			players[i].receiveCard(deck.dealCard());
		}
	}
	/**
	 * Run at the end of a round before resetting the hands of all players.
	 * Will check each player hand against the dealer and pay out accordingly.
	 * Also sets the results based on winning and payout.
	 */
	public void payTheWinners(){
		if(getDealer().hasBlackjack()){
			for(int i = 1; i<getPlayers();i++){
				if(getPlayer(i).hasBlackjack()){
					this.endResults = new StringBuilder("You and the house have Blackjack, " + getPlayer(i).getBetOnTable() + " chips returned");
					getPlayer(i).addChips(getPlayer(i).getBetOnTable());
				}
				else
					this.endResults = new StringBuilder("You lost" + getPlayer(i).getBetOnTable() + " chips, the house has Blackjack");
			}
			// Start next round.
			return;
		}
		for(int i = top-1; i >0;i--){
			if(players[i].getHandValue()<=21){
				//player wins
				if(players[i].hasBlackjack()){
					this.endResults = new StringBuilder("You got a Black Jack with winnings of: " + (int)(getPlayer(i).getBetOnTable()*2.5));
					players[i].addChips((int)(getPlayer(i).getBetOnTable()*2.5));
				}
				else if(getPlayer(i).getHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21){
					this.endResults = new StringBuilder("You won: " + getPlayer(i).getBetOnTable()*2 );
					getPlayer(i).addChips(getPlayer(i).getBetOnTable()*2);
				}
				//push
				else if(getPlayer(i).getHandValue() == getDealer().getHandValue()){
					this.endResults = new StringBuilder("You tied with the house, bet of " + getPlayer(i).getBetOnTable() + " chips was returned");
					getPlayer(i).addChips(getPlayer(i).getBetOnTable());
				}
				else{
					this.endResults = new StringBuilder("You lost " + getPlayer(i).getBetOnTable() + " chips.");
				}
				
			}
			else{
				this.endResults = new StringBuilder("You lost " + getPlayer(i).getBetOnTable() + " chips.");
				
			}/*
			if(getPlayer(i).getSplitHandValue()<=21 && getPlayer(i).hasSplitHand()){
				//player wins
				if(getPlayer(i).hasSplitHandBlackjack())
					getPlayer(i).addChips((int)(getPlayer(i).getBet()*2.5));
				else if(getPlayer(i).getSplitHandValue() > getDealer().getHandValue() || getDealer().getHandValue()>21)
					getPlayer(i).addChips(getPlayer(i).getBet()*2);
				//push
				else if(getPlayer(i).getSplitHandValue() == getDealer().getHandValue())
					getPlayer(i).addChips(getPlayer(i).getBet());
			}*/
		}
	}
	/**
	 * Has each player discard their current hand.
	 */
	public void discardHands(){
		for(int i = 0; i < top; i ++){
			players[i].discardHand();
		}
	}
	/**
	 * Ends the current player's turn. Moves to next player. After dealer moves back to first player.
	 */
	public void endTurn(){
		if(turn == 0){
			turn = top-1;
			rounds++;
		}
		else
			turn = 0;
	}
	/**
	 * Returns the player whose turn it is.
	 * @return
	 */
	public Player getTurn(){
		return this.getPlayer(turn);
	}
	/**
	 * Returns the number of hands played.
	 * @return number of hands played
	 */
	public int getRounds(){
		return rounds;
	}
	/**
	 * Builds a String representation of each player's hand.
	 * @return
	 */
	public String allHandstoString(){
		StringBuilder s = new StringBuilder("");
		s.append("This is hand: " + rounds+" There are "+"\n");
		for(int i = 0; i < top; i ++){
			if(players[i].getName().equals("Dealer"))
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue() +"\t"+"\n");
			else{
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue());
				if(players[1].hasSplitHand())
					s.append("\tSplit Hand Value:"+ players[i].getSplitHandValue());
				s.append("\tChips:"+ players[i].getChips()+"\n");
			}
		}
		return s.toString();
	}
	/**
	 * Return string of current game state.
	 */
	public String toString(){
		return this.endResults.toString();
	}
	/**
	 * Removes any players that do not have enough chips to place their bet.
	 */
	public void removeDeadPlayers(){
		for(int player = top-1; player>0; player--){
			if(players[player].getChips()-players[player].getBetOnTable() <= 0 && players[player].getHasStayed()){
				this.endResults.append(players[player].toString() + "\n");
				top--;
				for(int i=player; i < top; i++){
					players[i] = players[i+1];
				}
			}
		}
	}
	/**
	 * Checks to see if at least 1 player has enough chips for the next hand.
	 * @return
	 */
	public boolean someoneHasChips(){
		if(this.getPlayers()>1)
			return true;
		return false;
	}
	/*
	 * Checks current player to see if they are npc. If so, takes their turn.
	 * Does not end the turn.
	 */
	public void npcTurn(){
		if(getTurn().getHandValue()<21){
			//if(getTurn().canSplit() && getTurn() != getDealer())
			//	getTurn().splitHand();
			while(getTurn().getHandValue()<17)
				getTurn().receiveCard(deck.dealCard());
			//while(getTurn().getSplitHandValue()<17 && getTurn().hasSplitHand())
				//getTurn().receiveSplitHandCard(deck.dealCard());
		}
	}
}
