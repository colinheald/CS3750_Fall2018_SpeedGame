package resources;

import java.util.*;

public class GameManager {
	
	public Deck d;
	public ArrayList<Card> p1;
	public ArrayList<Card> p2;
	
	public GameManager()
	{
		d = new Deck();
	}
	
	public boolean getDeck(int pIndex)
	{
		return d.canDeckDraw(pIndex);
	}
	
	public String[] getPiles()
	{
		String[] s = new String[4];
		
		for (int i = 0; i < d.GetTopCards().size(); ++i)
		{
			s[i] = getImg(d.GetTopCards().get(i), i, "Pile");
		}
		
		return s;
	}
	
	public String[] getPlayerHand(int pIndex)
	{
		String[] s = new String[5];
		
		for (int i = 0; i < d.getPlayerHand(pIndex).size() ; ++i)
		{
			s[i] = getImg(d.getPlayerHand(pIndex).get(i), i, "pHand");
		}
		
		return s;
	}
	
	public String getImg(Card c, int index, String suffix)
	{
		String s = "";
		
		if(c.getValue() < 11)
		{
			s += c.getValue();
		}
		else
		{
			s += c.getName().substring(0, 1);
		}
		
		s += c.getSuit().substring(0, 1);
		
		String drag = "draggable='true' ondragstart='drag(event)'";
		
		if(suffix.equalsIgnoreCase("Pile")) {
			drag = "draggable='false' ondrop='drop(event)' ondragover='allowDrop(event)'";
		}
		return "<img id='" + index + "_" + suffix +"' src='images/" + s + ".jpg' height='10%' width='10%' " + drag + ">";
	}
	
	public boolean isStalemate()
	{
		ArrayList<Card> p1 = d.getPlayerHand(0);
		ArrayList<Card> p2 = d.getPlayerHand(1);
		ArrayList<Card> piles = d.GetTopCards();
		
		for (int i = 0; i < piles.size(); ++i)
		{
			for (int j = 0; j < p1.size(); ++j)
			{
				if(Math.abs(piles.get(i).getValue() - p1.get(j).getValue()) == 1)
				{
					return false;
				}
				else if((piles.get(i).getValue() == 2 && p1.get(j).getValue() == 14) || 
						(piles.get(i).getValue() == 14 && p1.get(j).getValue() == 2))
				{
					return false;
				}
			}
			
			for (int j = 0; j < p2.size(); ++j)
			{
				if(Math.abs(piles.get(i).getValue() - p2.get(j).getValue()) == 1)
				{
					return false;
				}
				else if((piles.get(i).getValue() == 2 && p2.get(j).getValue() == 14) || 
						(piles.get(i).getValue() == 14 && p2.get(j).getValue() == 2))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void play(int pIndex, int cardIndex, int pileIndex)
	{
		
	}
	
	public String[] readJSON()
	{
		String[] s = {""};
		
		
		
		return s;
	}

}