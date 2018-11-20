package resources;

public class Card {
    private String name;
    private Suit suit;
    private int value;
    private String image;

    Card(String _name, Suit _suit, int _value){
        this.name = _name;
        this.suit = _suit;
        this.value = _value;
        this.image = generateImageName();
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSuit() {
        return suit.name();
    }
    public String getImage() { return this.image;}

    public int getValue(){
        return this.value;
    }

    private String generateImageName(){
        String val;
        String st;
        st = this.suit.name().substring(0,1);
        if (this.value < 11){
            val = String.valueOf(this.value);
        }
        else{
            val = this.name.substring(0,1);
        }

        return val + st + ".jpg";
    }
}
