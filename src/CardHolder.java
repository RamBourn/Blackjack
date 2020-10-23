import java.util.ArrayList;

/**
 * This class represents a hand of a player or a dealer
 * because both of them can hold cards
 * @author Chuyang Zhou
 *
 */
public abstract class CardHolder {
	protected ArrayList<Card> cards;
	/**
	 * receive another card
	 */
	public abstract void hit(Card card);
	/**
	 * 
	 * @return current state of one hand
	 */
	public abstract String check();
	/**
	 *
	 * @return total values in one hand
	 */
	public abstract int getValues();
	public ArrayList<Card> getCards()
	{
		return cards;
	}
}
