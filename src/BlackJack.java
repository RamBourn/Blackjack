import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents the procedure of BlackJack game
 * @author Chuyang Zhou
 *
 */
public class BlackJack {
	private Dealer dealer;
	private int playerNum;  //number of players
	private int mode;    //mode: player or computer
	private int initMoney;  //initial money for every player
	private int round;  
	public BlackJack()
	{
		round=0;
		Scanner in=new Scanner(System.in);
		System.out.print("Choose the mode(Player:1 Computer:2) :");
		mode=in.nextInt();
		System.out.print("Enter the number of players:");
		playerNum=in.nextInt();
		System.out.print("Enter the initial amount of money for each player:");
		initMoney=in.nextInt();
		start();
	}
	/**
	 * start the game
	 */
	public void start()
	{
		Scanner in=new Scanner(System.in);
		if(mode==2)
		{
			dealer=new Dealer(mode);
			for(int i=1;i<=playerNum;i++)
			{
				dealer.addPlayer(initMoney, i);
			}
				
		}
		else
		{
			int chosen=new Random().nextInt(playerNum)+1;
			System.out.println("The Player "+chosen+" has been chosen to be the dealer");
			dealer=new Dealer(mode,chosen);
			for(int i=1;i<=playerNum;i++)
			{
				if(i==chosen)
					continue;
				else
				{
					dealer.addPlayer(initMoney, i);
				}
			}
		}
		startRound();
	}
	/**
	 * start another round
	 */
	public void startRound()
	{
		Scanner in =new Scanner(System.in);
		round++;
		for(Player player:dealer.getPlayers())
			player.getHands().clear();
		for(Player player:dealer.getPlayers())
			player.checkMoney();
		System.out.println("Round "+round+":");
		if(mode==2)
		{
			for(int i=1;i<=playerNum;i++)
			{
				int bet;
				System.out.print("Player "+i+", enter you bet money:");
				do
				{
					bet=in.nextInt();
					if(bet>initMoney)
						System.out.print("The bet you enter exceeds the initial money, enter again:");
				}while(bet>initMoney);
				dealer.getPlayers().get(i-1).setBet(bet);
			}
		}
		else
		{
			int chosen=new Random().nextInt(playerNum)+1;
			System.out.println("The Player "+chosen+" has been chosen to be the dealer");
			dealer=new Dealer(mode,chosen);
			for(int i=1;i<=playerNum;i++)
			{
				int bet;
				if(i==chosen)
					continue;
				else
				{
					System.out.print("Player "+i+", enter you bet money:");
					do
					{
						bet=in.nextInt();
						if(bet>initMoney)
							System.out.print("The bet you enter exceeds the initial money, enter again:");
					}while(bet>initMoney);
				}
				dealer.getPlayers().get(i-1).setBet(bet);
			}
		}
		initCards();
		printCurrent();
		for(Player player:dealer.getPlayers())
			bet(player);
		dealer.bet();
		calculateMoney();
		System.out.print("Do you want to play another round (Y|N):");
		String decide;
		decide=in.next();
		if(decide.equals("Y")||decide.equals("y"))
			startRound();
		else
		{
			System.out.println("Game Over! Thank you for playing");
			System.exit(0);
		}
	}
	/**
	 * distribute initial two cards to players and dealer
	 */
	public void initCards()
	{
		for(Player player:dealer.getPlayers())
			if(!player.isQuit())
				dealer.distribute(player.getHands().get(0));
		dealer.distribute(dealer);
		for(Player player:dealer.getPlayers())
			if(!player.isQuit())
				dealer.distribute(player.getHands().get(0));
		dealer.distribute(dealer);
		dealer.getCards().get(1).setHidden(); //set the second card of the dealer to be invisible to players
	}
	/**
	 * each player bets in turns
	 */
	public void bet(Player player)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Time for Player "+player.getID()+" to bet");
		System.out.println(player);
		String result;
		while(player.checkSplit())
		{
			for(Hand hand:player.getHands())
			{
				if(hand.getCards().size()<2)
					dealer.distribute(hand);
			}
		}
		for(Hand hand:player.getHands())
		{
			while(hand.check().equals("ok"))
			{
				System.out.println("Bet for every hand:");
				System.out.println(hand);
				System.out.print("Choose your options: (Hit 1  Stand 2  DoubleUp 3:");
				int choose=in.nextInt();
				if(choose==1)
					dealer.distribute(hand);
				else if(choose==2)
				{
					hand.stand();
					break;
				}
				else if(choose==3)
				{
					hand.doubleUP();
					player.setMoney(player.getMoney()-hand.getBet()/2);
					dealer.distribute(hand);
				}
			}
			result=hand.check();
			if(result.equals("bust"))
			{
				System.out.println("Hand "+hand.getID()+" of player "+player.getID()+" is bust"
						+ ", lose $"+hand.getBet());
			}
			else if(result.equals("BlackJack"))
			{
				System.out.println("Hand "+hand.getID()+" of player "+player.getID()+" has BlackJack,"
						+ " it has to wait for the dealer");
			}
			else if (result.equals("naturalBJ"))
			{
				System.out.println("Hand "+hand.getID()+" of player "+player.getID()+" has natural BlackJack,"
						+ " it has to wait for the dealer");
			}
		}
	
	}
	/**
	 * print the cards of all players and the dealer
	 */
	public void printCurrent()
	{
		System.out.println(dealer);
		for(Player player:dealer.getPlayers())
			System.out.println(player);
	}
	/**
	 * calculate each player's winning or losing money after every round
	 */
	public void calculateMoney()
	{
		System.out.println("This round is over, now shows the result:");
		printCurrent();
		if(dealer.check().equals("bust"))
		{
			for(Player player:dealer.getPlayers())
			{
				for(Hand hand:player.getHands())
				{
					if(!hand.check().equals("bust"))
					{
						player.setMoney(player.getMoney()+hand.getBet()*2);
						dealer.setProfit(dealer.getProfit()-hand.getBet());
					}
					else
						dealer.setProfit(dealer.getProfit()+hand.getBet());
				}
			}
		}
		else if(dealer.check().equals("stop"))
		{
			for(Player player:dealer.getPlayers())
			{
				for(Hand hand:player.getHands())
				{
					if(hand.check().equals("bust"))
						dealer.setProfit(dealer.getProfit()+hand.getBet());
					else if(hand.getValues()>dealer.getValues())
					{
						player.setMoney(player.getMoney()+hand.getBet()*2);
						dealer.setProfit(dealer.getProfit()-hand.getBet());
					}
					else if(hand.getValues()==dealer.getValues())
						player.setMoney(player.getMoney()+hand.getBet());
					else if(hand.getValues()<dealer.getValues())
						dealer.setProfit(dealer.getProfit()+hand.getBet());
				}
			}
		}
		else if(dealer.check().equals("BlackJack"))
		{
			for(Player player:dealer.getPlayers())
			{
				for(Hand hand:player.getHands())
				{
					if(hand.check().equals("bust"))
						dealer.setProfit(dealer.getProfit()+hand.getBet());
					else if(hand.check().equals("naturalBJ"))
					{
						player.setMoney(player.getMoney()+hand.getBet()*2);
						dealer.setProfit(dealer.getProfit()-hand.getBet());
					}
					else if(hand.check().equals("BlackJack"))
						player.setMoney(player.getMoney()+hand.getBet());
					else 
						dealer.setProfit(dealer.getProfit()+hand.getBet());
				}
			}
		}
		printMoney();
	}
	public void printMoney()
	{
		System.out.println("Number     Left");
		for(Player player:dealer.getPlayers())
		{
			System.out.println("Player "+player.getID()+": "+player.getMoney());
		}
		System.out.println("Pure profit of dealer is "+dealer.getProfit());
	}
}
