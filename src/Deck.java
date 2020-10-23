import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a deck of cards
 * @author Chuyang Zhou
 *
 */
public class Deck {
	private ArrayList<Card> remain;
	public Deck()
	{
		remain=new ArrayList<>();
		shuffle();
	}
	/**
	 * If the deck is used up, create a new deck of cards and return the last card
	 * @return the last card in the deck
	 */
	public Card getACard()
	{
		if (remain.size()==0)
		{
			shuffle();
			return getACard();
		}
		else
			return remain.remove(remain.size()-1);
	}
	/**
	 * create a new deck of cards and shuffle it
	 */
	public void shuffle()
	{
		String[] names= {"1","2","3","4","5","6","7","8","9","10","A","J","Q","K"};
		String[] colors= {"Club","Spade","Diamond","Heart"};
		for(int i=0;i<names.length;i++)
		{
			for(int j=0;j<colors.length;j++)
				remain.add(new Card(names[i],colors[j]));
		}
		Collections.shuffle(remain);
	}
	public String toString()
	{
		String view="";
		for(Card c:remain)
			view+=c.toString()+"\n";
		return view;
	}
	
}
