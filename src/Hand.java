import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a hand of the player
 * @author Chuyang Zhou
 *
 */
public class Hand extends CardHolder {
	public int bet;
	private boolean stand;
	private int ID;   //the ID of the specific hand
	public Hand()
	{
		cards=new ArrayList<>();
		bet=0;
		stand=false;
		ID=1;
	}
	/**
	 * 
	 * @param card the initial card when splitting
	 * @param bet the bet when splitting the hand
	 */
	public Hand(int bet,int ID)
	{
		cards=new ArrayList<>();
		this.bet=bet;
		stand=false;
		this.ID=ID;
	}
	public Hand(Card card,int bet,int ID)
	{
		cards=new ArrayList<>();
		cards.add(card);
		this.bet=bet;
		stand=false;
		this.ID=ID;
	}
	public void hit(Card card)
	{
		//if(!stand)
		cards.add(card);
		//else
			//System.out.println("This hand has stood, it cannot hit");
	}
	public String check()
	{
		if(getValues()<21)
			return "ok";
		else if(getValues()>21)
			return "bust";
		else if(cards.size()>2&&getValues()==21)
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
	public int getBet()
	{
		return bet;
	}
	public void setBet(int bet)
	{
		this.bet=bet;
	}
	/**
	 * 
	 * @return if the hand stands
	 */
	public boolean isStand()
	{
		return stand;
	}
	/**
	 * 
	 * @return if the hand can be split
	 */
	public boolean splitable()
	{
		return cards.size()==2&&cards.get(0).getName().equals(cards.get(1).getName());
	}
	public int getID()
	{
		return ID;
	}
	public void doubleUP()
	{
		bet*=2;
		stand=true;
	}
	/**
	 * 
	 * @return return the last card to split
	 */
	public Card split()
	{
		return cards.remove(1);
	}
	public String toString()
	{
		String intent="     ";
		String des=intent+"Hand "+ID+":\n"+intent+intent;
		for(Card card:cards)
			des+=card.toString()+"   ";
		des+="\n";
		return des;
	}
	public void stand()
	{
		stand=true;
	}
}
