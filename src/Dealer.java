import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the dealer
 * @author Chuyang Zhou
 *
 */
public class Dealer extends CardHolder {
	private Deck deck;
	private int profit;   //pure profit of the casino
	private int mode;  //player or computer
	private int num;   //the num-th of players is chosen to be the dealer
	private ArrayList<Player> players;
	/**
	 *  profit: the pure profit of the casino
	 *  players: support the mode in which multiple players play at the same time
	 *  cards: the cards of the dealer
	 */
	public Dealer()
	{
		mode=2;
		deck=new Deck();
		profit=0;
		cards=new ArrayList<>();
		players=new ArrayList<>();
	}
	public Dealer(int mode)
	{
		this.mode=mode;
		deck=new Deck();
		profit=0;
		cards=new ArrayList<>();
		players=new ArrayList<>();
	}
	public Dealer(int mode,int num)
	{
		this.num=num;
		this.mode=mode;
		deck=new Deck();
		profit=0;
		cards=new ArrayList<>();
		players=new ArrayList<>();
	}
	public void addPlayer(int initMoney,int ID)
	{
		players.add(new Player(initMoney,ID));
	}
	public void hit(Card card)
	{
		cards.add(card);
	}
	public String check()
	{
		if(getValues()<17)
			return "ok";
		else if (getValues()>21)
			return "bust";
		else if(getValues()>=17&&getValues()<21)
			return "stop";
		else if (cards.size()==2&&getValues()==21)
			return "naturalBJ";
		else 
			return "BlackJack";
	}
	public int getValues()
	{
		int total=0;
		for(Card c:cards)
			if(!c.getName().equals("A"))
				total+=c.getValue();
			else
				if(total+11>21)
					total+=1;
				else
					total+=11;
		return total;
	}
	/**
	 * 
	 * @param holder the person to whom the dealer will distribute a card to
	 */
	public void distribute(CardHolder holder)
	{
		holder.hit(deck.getACard());
	}
	public void setProfit(int profit)
	{
		this.profit=profit;
	}
	public int getProfit()
	{
		return profit;
	}
	public String toString()
	{
		String des="";
		if(mode==2)
		{
			des+="Dearler:\n     ";
			for(Card card:cards)
					des+=card.toString()+"   ";
		}
		else
		{
			des+="Dealer(Player "+num+"):\n     ";
			for(Card card:cards)
				des+=card.toString()+"   ";
		}
		return des;
	}
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	/**
	 * After all players have finished their actions
	 * the dealer can continue to hit until bust or over 16
	 * In the player vs player mode, dealer can be manipulated by player
	 * but they have only one choice: hit
	 */
	public void bet()
	{
		cards.get(1).setVisible();
		if(mode==2)
		{
			while(check().equals("ok"))
			{
				distribute(this);
			}
		}
		else
		{
			System.out.println("Time for the dealer to proceed:");
			while(check().equals("ok"))
			{
				Scanner in =new Scanner(System.in);
				System.out.print("You can still hit, enter anything to hit:");
				String m=in.next();
				distribute(this);
				System.out.println(this);
			}
		}
	}
}
