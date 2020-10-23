/**
 * This class represents a single card
 * @author Chuyang Zhou
 * 
 */
public class Card {

	private String name;
	private String color;
	private int value;
	private boolean hidden;  //is this card hide from players
	/**
	 * 
	 * @param name 1~10 A J Q K
	 * @param color club spade diamond heart
	 * @param value 1~11
	 */
	public Card(String name,String color)
	{
		this.name=name;
		this.color=color;
		hidden=false;
		if(name.equals("A"))
			value=11;
		else if(name.equals("J")||name.equals("K")||name.equals("Q"))
			value=10;
		else
			value=Integer.parseInt(name);
	}
	public Card()
	{
		name="";
		color="";
		value=0;
	}
	public String getName()
	{
		return name;
	}
	public String getColor()
	{
		return color;
	}
	public int getValue()
	{
		return value;
	}
	public String toString()
	{
		String view="  "+name+"-"+color;
		if(hidden==true)
			return "Secret-Card";
		else
			return view;
	}
	public void setHidden()
	{
		hidden=true;
	}
	public void setVisible()
	{
		hidden=false;
	}
}
