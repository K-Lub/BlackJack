import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;


/**
 * This is the GUI for the Blackjack game.
 * 
 * @author Kailab Bowler
 * @author Kristofer Hoadley
 * @author Connor Premuda
 * @author Edward Woelke
 *
 */
public class BlackJackGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private boolean hasHit;
	private boolean hasStayed;
	//private boolean wantsSplit;
	private boolean wantsDouble;
	private boolean wantsIncrease;
	private boolean wantsDecrease;
	private boolean wantsDeal;
	private boolean firstHand;
	private boolean newGame;
	
	private GridBagConstraints gbcDealer = new GridBagConstraints();
	private GridBagConstraints gbcPlayer = new GridBagConstraints();
	private GridBagConstraints gbc = new GridBagConstraints();
	private GridBagConstraints gbcHeader = new GridBagConstraints();
	private JFrame canvas = new JFrame();
	private JPanel mainPanel = new JPanel(new GridBagLayout());
	
	private JPanel jpHeader = new JPanel(new GridBagLayout());
		private JLabel jlHeader = new JLabel("BLACKJACK");
		Font font = new Font("Sans Serif", Font.BOLD,36);
	
	private JPanel jpDealer = new JPanel(new GridBagLayout());
		private JPanel jpDealerLeft = new JPanel(new GridBagLayout());
			private JLabel jlDealerName = new JLabel("Dealer");
			private JLabel jlDealerCards = new JLabel("Cards");
		private JPanel jpDealerRight = new JPanel(new GridBagLayout());
			private JLabel jlShuffleString = new JLabel("Until Shuffle");
			private JLabel jlShuffleAmount = new JLabel("Amount");
			private JLabel jlChipsString = new JLabel("Chips");
			private JLabel jlChipsCount = new JLabel("Count");
	
	private JPanel jpPlayer = new JPanel(new GridBagLayout());
		private JPanel jpPlayerLeft = new JPanel(new GridBagLayout());
			private JLabel jlPlayerName = new JLabel("Player");
			private JLabel jlPlayerCards = new JLabel("Cards");
		private JPanel jpPlayerRight = new JPanel(new GridBagLayout());
			private JLabel jlBetString = new JLabel("Bet");
			private JLabel jlBetAmount = new JLabel("Amount");
	
	private JPanel jpButtons = new JPanel(new GridBagLayout());
		private JButton jbtHit = new JButton("Hit");
		private JButton jbtStay = new JButton("Stay");
		//private JButton jbtSplit = new JButton("Split");
		private JButton jbtDouble = new JButton("Double");
		private JButton jbtIncreaseBet = new JButton("Increase Bet");
		private JButton jbtDecreaseBet = new JButton("Decrease Bet");
		private JButton jbtDeal = new JButton("Deal");
		private JButton jbtNewGame = new JButton("New Game");
		
	private JPanel jpEmpty = new JPanel();	
	private JPanel jpBottom = new JPanel();
	private JLabel jlInfo = new JLabel("Hello");
	
	public BlackJackGUI(){
		
		jlHeader.setFont(font);
		
		// Initializes all buttons but newGame to disabled.
		DisableHit();
		DisableStay();
		DisableDouble();
		//DisableSplit();
		DisableDecrease();
		DisableIncrease();
		DisablePlayer();
		DisableDeal();
	
		gbcHeader.insets = new Insets(5,5,5,5);
		gbc.insets = new Insets(15,15,15,15);
		gbcDealer.insets = new Insets(5,5,5,5);
		gbcPlayer.insets = new Insets(5,10,5,10);
		
		canvas.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		canvas.setSize(400,600);
		canvas.setVisible(true);
		
		gbcHeader.gridy = 0;
		jpHeader.add(jlHeader, gbcHeader);
		
		gbcDealer.gridy = 0;
		jpDealerLeft.add(jlDealerName,gbcDealer);
		gbcDealer.gridy = 1;
		jpDealerLeft.add(jlDealerCards,gbcDealer);
		
		gbcDealer.gridy = 0;
		jpDealerRight.add(jlShuffleString,gbcDealer);
		
		gbcDealer.gridy = 1;
		jpDealerRight.add(jlShuffleAmount,gbcDealer);
		
		jpDealer.add(jpDealerLeft);
		jpDealer.add(jpEmpty);
		jpDealer.add(jpDealerRight);
		
		gbcPlayer.gridy = 0;
		jpPlayerLeft.add(jlPlayerName,gbcPlayer);
		gbcPlayer.gridy = 1;
		jpPlayerLeft.add(jlPlayerCards,gbcPlayer);
		
		gbcPlayer.gridy = 0;
		jpPlayerRight.add(jlBetString,gbcPlayer);
		jpPlayerRight.add(jlChipsString,gbcPlayer);
		gbcPlayer.gridy = 1;
		jpPlayerRight.add(jlBetAmount,gbcPlayer);
		jpPlayerRight.add(jlChipsCount,gbcPlayer);
		
		
		jpPlayer.add(jpPlayerLeft);
		jpPlayer.add(jpPlayerRight);
		
		
		gbc.gridy = 0;
		jpButtons.add(jbtHit,gbc);
		gbc.gridy = 0;
		jpButtons.add(jbtDouble,gbc);
		//gbc.gridy = 1;
		//jpButtons.add(jbtSplit,gbc);
		gbc.gridy = 0;
		jpButtons.add(jbtIncreaseBet,gbc);
		gbc.gridy = 1;
		jpButtons.add(jbtStay,gbc);
		gbc.gridy = 1;
		jpButtons.add(jbtDeal,gbc);
		gbc.gridy = 1;
		jpButtons.add(jbtDecreaseBet,gbc);
		gbc.gridy = 2;
		jpButtons.add(jbtNewGame,gbc);
		
		gbc.gridy = 0;
		mainPanel.add(jpHeader, gbc);		
		gbc.gridy = 1;
		mainPanel.add(jpDealer,gbc);
		gbc.gridy = 2;
		mainPanel.add(jpPlayer,gbc);
		gbc.gridy = 3;
		mainPanel.add(jpButtons, gbc);
		gbc.gridy = 4;
		jpBottom.add(jlInfo);
		mainPanel.add(jpBottom,gbc);
		
		Color dGreen = new Color(0,155,0);
		jpHeader.setBackground(dGreen);
		jpButtons.setBackground(dGreen);
		mainPanel.setBackground(dGreen);
		
		canvas.add(mainPanel);
		
		// Listeners for the buttons
		jbtHit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				hasHit = true;
			}
		});
		
		jbtStay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				hasStayed = true;
			}
		});
		
		jbtDouble.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wantsDouble = true;
			}
		});
		
		jbtIncreaseBet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wantsIncrease = true;
			}
		});
		
		jbtDecreaseBet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wantsDecrease = true;
			}
		});
		
		jbtDeal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				wantsDeal = true;
				firstHand = true;
			}
		});
		
		jbtNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newGame = true;
			}
		});
	}
	
	// Set output text fields.
	public void setChips(String in){jlChipsCount.setText(in);}
	public void setBetAmount(String in){jlBetAmount.setText(in);}
	public void setPlayerName(String in){jlPlayerName.setText(in);}
	public void setShuffleAmount(String in){jlShuffleAmount.setText(in);}
	public void setPlayerCards(String in){jlPlayerCards.setText(in);}
	public void setDealerCards(String in){jlDealerCards.setText(in);}
	public void setFirstHand(Boolean b){firstHand = b;}
	
	public void setInfo(String in){
		jlInfo.setText(in);
	}
	
	// Reset button values to false
	public void resetHit(){hasHit = false;}
	public void resetStay(){hasStayed = false;}
	public void resetDouble(){wantsDouble = false;}
	public void resetIncrease(){wantsIncrease = false;}
	public void resetDecrease(){wantsDecrease = false;}
	public void resetDeal(){wantsDeal = false;}
	public void resetNewGame(){newGame = false;}
	
	// Disable buttons
	public void DisableHit(){jbtHit.setEnabled(false);}
	public void DisableStay(){jbtStay.setEnabled(false);}
	public void DisableDouble(){jbtDouble.setEnabled(false);}
	//public void DisableSplit(){jbtSplit.setEnabled(false);}
	public void DisableDecrease(){jbtDecreaseBet.setEnabled(false);}
	public void DisableIncrease(){jbtIncreaseBet.setEnabled(false);}
	public void DisablePlayer(){jbtHit.setEnabled(false); jbtStay.setEnabled(false);}
	public void DisableDeal(){jbtDeal.setEnabled(false);}
	public void DisableNewGame(){jbtNewGame.setEnabled(false);}
	
	
	public void EnableHit(){jbtHit.setEnabled(true);}
	public void EnableStay(){jbtStay.setEnabled(true);}
	public void EnableDouble(){jbtDouble.setEnabled(true);}
	//public void EnableSplit(){jbtSplit.setEnabled(true);}
	public void EnableDecrease(){jbtDecreaseBet.setEnabled(true);}
	public void EnableIncrease(){jbtIncreaseBet.setEnabled(true);}
	public void EnableDeal(){jbtDeal.setEnabled(true);}
	public void EnablePlayAgain(){jbtNewGame.setEnabled(true);}
	public void EnablePlayer(){jbtHit.setEnabled(true); jbtStay.setEnabled(true);}
	
	
	public boolean getHit(){return hasHit;}
	public boolean getStay(){return hasStayed;}
	//public boolean getSplit(){return wantsSplit;}
	public boolean getDouble(){return wantsDouble;}
	public boolean getIncrease(){return wantsIncrease;}
	public boolean getDecrease(){return wantsDecrease;}
	public boolean getDeal(){return wantsDeal;}
	public boolean getPlayAgain(){return newGame;}
	public boolean getFirstHand(){return firstHand;}
}
