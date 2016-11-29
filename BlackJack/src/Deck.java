import java.util.*;
/**
 * A Deck class that holds playing cards of the Card class. When created it adds 52 shuffled cards.
 * It is a singleton class, there can only be 1 Deck and it must be referenced with getInstance.
 * If the deck runs out of cards, it shuffles all the cards before dealing a card.
 * 
 * @author Kristofer
 *
 */
public class Deck {
	private static Deck obj;
	private static Card[] deck;
	private static int top;
	/**
	 * Constructor of deck. Creates deck with 52 cards and initializes top of deck.
	 * This is a Singleton class.
	 */
	public static Deck getInstance(){
		if(obj == null){
			obj = new Deck();
		}
		return obj;
	}
	/**
	 * Private constructor. This means in order to create an object of Deck class
	 * you must call the getInstance() method which first checks to see if an
	 * instance of Deck already exist.
	 */
	private Deck(){
		deck = new Card[0];
		top = 0;
		addDeck();
	}
	/**
	 * Adds 52 unique playing cards to the deck.
	 */
	protected void addDeck(){
		//create new deck with 52 more cards than previous deck.
		Card[] temp = new Card[deck.length + 52];
		for(int i = 0; i< deck.length; i++){
			temp[i] = deck[i];
		}
		deck = temp;
		//adds 52 cards to end of deck.
		int where = deck.length - 52;
		for(Card.Suit suit: Card.Suit.values()){
			for(Card.Face face: Card.Face.values()){
				deck[where++] = new Card(suit, face);
			}
		}
	}
	/**
	 * Shuffles the cards in the deck and resets top of deck.
	 * 
	 */
	public void shuffle(){
		Random random = new Random();
		int[] numbersUsed = new int[deck.length];
		
		//put elements in a different part of the array
		for (int i = 0; i < deck.length; i++) {
			int newPostition = 0; 
			//make sure same position is not used twice
			for (int x = 0; x < numbersUsed.length; x++){
				boolean breaker = false;
				while (breaker != true){
					newPostition = random.nextInt(deck.length);
					if (newPostition != numbersUsed[x]){
						breaker = true;
					}
				}
			}
			//swap cards
			Card holder = deck[i];
			deck[i] = deck[newPostition];
			deck[newPostition] = holder;
		}
		top = 0;
	}
	/**
	 * Deals the next card from the deck. Shuffles deck first if out of cards.
	 * @return card from deck.
	 */
	public Card dealCard(){
		if(top>=deck.length){
			shuffle();
		}
		return deck[top++];
	}
}
