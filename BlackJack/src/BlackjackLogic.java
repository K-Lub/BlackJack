/**
 * Ideas of features to add:
 * 		Add automated card counter?(up if high card, down if low card?)
 * 		Store player max chips?
 * 		Store player rounds lasted?
 * 		Second Player array for dead players?
 * 		NPC difficulty and randomness built into Player class if NPC?
 * 
 * First player added must be "Dealer".
 * This is the logical backend to the Blackjack game. Needs a user interface to work for player controlled characters.
 * Currently simulates NPC's playing until everyone runs out of chips.
 * 
 * 
 * @author Kristofer
 *
 */
public class BlackjackLogic {
	// Initialize class variables.
	Player[] players;
	int top;
	int rounds;
	/**
	 * Default constructor contains 2 players.
	 */
	private BlackjackLogic(){
		top = 0;
		players = new Player[2];
		rounds = 1;
	}
	/**
	 * Constructor has input for number of players.
	 * @param i number of players
	 */
	private BlackjackLogic(int i){
		top = 0;
		players = new Player[i];
		rounds = 1;
	}
	/**
	 * Adds a player to the game.
	 * @param player
	 */
	private void addPlayer(Player player){
		players[top++] = player;
	}
	/**
	 * Return the player in the array at spot i.
	 * @param i place in array
	 * @return Player
	 */
	private Player getPlayer(int i){
		if(i<top)
			return this.players[i];
		return null;
	}
	/**
	 * Returns the number of players.
	 * @return number of players
	 */
	private int getPlayers(){
		return top;
	}
	/**
	 * Deals 1 card from the deck to each player.
	 * @param deck deals card to each player
	 */
	private void dealHand(Deck deck){
		for(int i = 0; i < top; i ++){
			players[i].receiveCard(deck.dealCard());
		}
	}
	/**
	 * Has each player discard their current hand.
	 */
	private void discardHands(){
		for(int i = 0; i < top; i ++){
			players[i].discardHand();
		}
		this.incrementRounds();
	}
	/**
	 * Returns the number of hands played.
	 * @return number of hands played
	 */
	public int getRounds(){
		return this.rounds;
	}
	/**
	 * Increases hands by 1.
	 */
	private void incrementRounds(){
		this.rounds++;
	}
	/**
	 * Builds a String representation of each player's hand.
	 * @return
	 */
	private String allHandstoString(){
		StringBuilder s = new StringBuilder("");
		s.append("This is hand: " + this.rounds+" There are "+Deck.getInstance().cardsLeft()+" cards left in deck." + "\n");
		for(int i = 0; i < top; i ++){
			if(players[i].getName().equals("Dealer"))
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue() +"\t"+"\n");
			else
				s.append(players[i].getName() +"\t"+ players[i].getHand() +"\tHand Value:"+ players[i].getHandValue() +"\tChips:"+ players[i].getChips()+"\n");
		}
		return s.toString();
	}
	/**
	 * Removes any players that do not have enough chips to place their bet.
	 */
	public void removeDeadPlayers(){
		int player = top-1;
		while(player>0){
			if(players[player].getChips()-players[player].getBet() < 0){
				if(player < top-1){
					for(int i=player; i < top-1; i++){
						players[i] = players[i+1];
					}
				}
				top--;
			}
			player--;
		}
	}
	/**
	 * Checks to see if at least 1 player has enough chips for the next hand.
	 * @return
	 */
	private boolean someoneHasChips(){
		if(this.getPlayers()>1)
			return true;
		return false;
	}
	// Runs the game.
	public static void main(String args[]){
		// Initialize deck and players.
		Deck deck = Deck.getInstance();
		BlackjackLogic thisGame = new BlackjackLogic(5);
		thisGame.addPlayer(new Player());
		thisGame.addPlayer(new Player("Ted",true));
		thisGame.getPlayer(1).setBet(200);
		thisGame.addPlayer(new Player("Kris",true));
		thisGame.getPlayer(2).setBet(300);
		thisGame.addPlayer(new Player("Connor",true));
		thisGame.addPlayer(new Player("Kailab",true));
		thisGame.getPlayer(4).setBet(500);
		// Sets number of decks to increase by 1 for every 3 players.
		for(int i = 3; thisGame.getPlayers()>=i;i+=2){
			deck.addDeck();
		}
		deck.shuffle();
		// Tracks minimum number of cards in deck at any point.
		int deckMinimum = 52*deck.getDecks();
		// Game loop.
		do{
			// Resets everyone's hands.
			thisGame.discardHands();
			//Reshuffle deck every 5 hands.
			if(thisGame.getRounds()%5 == 0){
				deck.shuffle();
			}
			// Here is where the GUI should ask to increase or decrease bet. Disable increase bet button if it will reduce chips below 0.
			//Check bet for each player and remove chips.
			for(int i = 1; i<thisGame.getPlayers();i++){
				thisGame.getPlayer(i).removeChips(thisGame.getPlayer(i).getBet());
			}
			// Deal two cards to each player.
			thisGame.dealHand(deck);
			thisGame.dealHand(deck);
			// Initial printout of everyone's cards.
			System.out.println(thisGame.allHandstoString());
			// If dealer has Blackjack, ends round. Pushes if player has Blackjack.
			if(thisGame.getPlayer(0).hasBlackjack()){
				for(int i = 1; i<thisGame.getPlayers();i++){
					if(thisGame.getPlayer(i).hasBlackjack())
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet());
				}
				// Start next round.
				continue;
			}
			// Each player plays their round.
			// Starts with last player and stops at dealer.
			// Every player has to be NPC for now. No inputs yet.
			for(int i = thisGame.getPlayers()-1; i >=0;i--){
				// Prevents hitting if hand value is 21 (or higher).
				if(thisGame.getPlayer(i).getHandValue()<21){
					// NPC logic.
					if(thisGame.getPlayer(i).isNPC())
						while(thisGame.getPlayer(i).getHandValue()<17){
							thisGame.getPlayer(i).receiveCard(deck.dealCard());
							deckMinimum = deckMinimum>deck.cardsLeft() ? deck.cardsLeft() : deckMinimum;
						}
					// First check if can split, implement split logic.
					// Check if can double down, implement double down logic.
					// This is where the interaction with the GUI hit / stay should go.
				}
			}
			
			//check each player's hand against the dealer's hand and give out chips if they won.
			for(int i = thisGame.getPlayers()-1; i >0;i--){
				if(thisGame.getPlayer(i).getHandValue()<=21){
					//player wins
					if(thisGame.getPlayer(i).hasBlackjack())
						thisGame.getPlayer(i).addChips((int)(thisGame.getPlayer(i).getBet()*2.5));
					else if(thisGame.getPlayer(i).getHandValue() > thisGame.getPlayer(0).getHandValue() || thisGame.getPlayer(0).getHandValue()>21)
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet()*2);
					//push
					else if(thisGame.getPlayer(i).getHandValue() == thisGame.getPlayer(0).getHandValue())
						thisGame.getPlayer(i).addChips(thisGame.getPlayer(i).getBet());
				}
			}
			// After everyone plays their hand, prints the hands.
			System.out.println(thisGame.allHandstoString());
			//Remove players without enough chips.
			thisGame.removeDeadPlayers();			
		}while(thisGame.someoneHasChips());
		System.out.println("Deck minimum cards at any point: " + deckMinimum);
	}
}