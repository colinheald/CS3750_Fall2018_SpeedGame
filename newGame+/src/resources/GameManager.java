package resources;

import java.io.StringReader;
import java.util.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class GameManager {
	
	public Deck d;
	public String json;
	public JsonObject jsonObj;
	
	public GameManager()
	{
		d = new Deck();
		json = d.GameState();
		jsonObj = this.jsonFromString(json);
	}

	public JsonObject getGameState()
	{
		return jsonObj;
	}
	
	/*public boolean getDeck(int pIndex)
	{
		//return d.canDeckDraw(pIndex);
		return false;
	}*/
	
	public String[] getPiles()
	{
		String[] s = new String[4];
		
		for (int i = 0; i < d.GetTopCards().size(); ++i)
		{
			s[i] = getImg(jsonObj.getString("Pile" + i), "Pile", i);
		}
		
		return s;
		/*s[0] = getImg(jsonObj.getString("Pile0"), "Pile" ,0);
		s[1] = getImg(jsonObj.getString("Pile1"), "Pile" ,1);
		s[2] = getImg(jsonObj.getString("Pile2"), "Pile" ,2);
		s[3] = getImg(jsonObj.getString("Pile3"), "Pile" ,3);
		
		return s;*/
	}
	
	private JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }
	
	public String[] getPlayerHand(int pIndex)
	{
		String[] s = new String[5];
		
		for (int i = 0; i < d.getPlayerHand(pIndex).size() ; ++i)
		{
			s[i] = getImg(jsonObj.getString("P1Hand" + i), "pHand", i);
		}
		
		return s;
	}
	
	public String getImg(String filename, String suffix, int index)
	{		
		String drag = "draggable='true' ondragstart='drag(event)'";
		
		if(suffix.equalsIgnoreCase("Pile")) {
			drag = "draggable='false' ondrop='drop(event)' ondragover='allowDrop(event)'";
		}
		return "<img id='" + index + "_" + suffix +".jpg' src='images/"  + filename + "' height='10%' width='10%' " + drag + ">";		
	}
	
	/*public boolean isStalemate()
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
	}*/
	
	public void play(int pIndex, int cardIndex, int pileIndex)
	{

	}
}