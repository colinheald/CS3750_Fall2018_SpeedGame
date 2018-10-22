public class Main {

    public static void main(String[] args) {

        Deck cm = new Deck();
        Card card = cm.getCard(6);
        System.out.println(card.getName());
        System.out.println(card.getSuit());
        System.out.println(card.getValue());

        card = cm.getCard(44);
        System.out.println(card.getName());
        System.out.println(card.getSuit());
        System.out.println(card.getValue());

        card = cm.getCard(21);
        System.out.println(card.getName());
        System.out.println(card.getSuit());
        System.out.println(card.getValue());

        card = cm.getCard(51);
        System.out.println(card.getName());
        System.out.println(card.getSuit());
        System.out.println(card.getValue());

    }
}
