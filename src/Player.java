import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a player(not dealer)
 * @author Chuyang Zhou
 *
 */
public class Player {
	private int money;
	private ArrayList<Hand> hands;
	private int bet;
	private boolean quit;
	private int ID;
	/**
	 * money is the remaining money the play has
	 * hands are all hands the play split
	 * bet is the initial bet
	 * quit check whether the player has cashed out
	 * 
	 */
	public Player(int money,int ID)
	{
		this.money=money;
		hands=new ArrayList<>();
		quit=false;
		this.ID=ID;
		this.bet=0;
	}
	public Player()
	{
		this.money=10000;
		hands=new ArrayList<>();
		quit=false;
		this.ID=0;
		this.bet=0;
	}
	public void setMoney(int money)
	{
		this.money=money;
	}
	public int getMoney()
	{
		return money;
	}
	public void setBet(int bet)
	{
		this.bet=bet;
		hands.add(new Hand(bet,hands.size()+1));
		this.money-=bet;
	}
	public int getBet()
	{
		return bet;
	}
	public int getID()
	{
		return ID;
	}
	/**
	 * check if the player will split the hand
	 */
	public boolean checkSplit()
	{
		for(Hand hand:hands)
		{
			if(hand.splitable())
			{
				System.out.print("The No."+ID+" can split. Do you want to split another hand: (Y|N)");
				Scanner in =new Scanner(System.in);
				String decide;
				decide=in.next();
				if(decide.equals("Y")||decide.equals("y"))
				{
					split(hand);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @param hand the specific hand to split
	 */
	public void split(Hand hand)
	{
		if(money>hand.getBet())
		{
			Hand newHand=new Hand(hand.split(),hand.getBet(),hands.size()+1);
			hands.add(newHand);
			money-=hand.getBet();
		}
		else
			System.out.println("The money is not enough to split.");
	}
	public String toString()
	{
		String des="";
		des+="Player "+ID+":\n";
		for(Hand hand:hands)
			des+=hand.toString();
		des+="\n";
		return des;
	}
	public boolean isQuit()
	{
		return quit;
	}
	public void quit()
	{
		quit=true;
	}
	public ArrayList<Hand> getHands()
	{
		return hands;
	}
	public void checkMoney()
	{
		if(money<=0)
		{
			System.out.println("Player "+ID+" has lost all his money and quits");
			quit=true;
		}
	}
}
