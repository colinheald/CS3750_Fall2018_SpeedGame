package resources;

public class Card {
	private String name;
    private Suit suit;
    private int value;

    Card(String _name, Suit _suit, int _value){
        this.name = _name;
        this.suit = _suit;
        this.value = _value;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSuit() {
        return suit.name();
    }

    public int getValue(){
        return this.value;
    }
}
