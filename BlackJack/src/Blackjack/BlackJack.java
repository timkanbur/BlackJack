package Blackjack;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BlackJack {

    public static int dealerscore;
    public static int playerscore;
    public static boolean active;
    public static boolean gameover;
    public static boolean dealer_active;

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(System.in);

        ArrayList<String> player = new ArrayList<>();
        ArrayList<String> dealer = new ArrayList<>();
        ArrayList<String> mcards = new ArrayList<>();

        String[] cards = {"Kreuz 2", "Kreuz 3", "Kreuz 4", "Kreuz 5", "Kreuz 6", "Kreuz 7", "Kreuz 8", "Kreuz 9",
                "Kreuz 10", "Kreuz Bube", "Kreuz Dame", "Kreuz König", "Kreuz Ass", "Pik 2", "Pik 3",
                "Pik 4", "Pik 5", "Pik 6", "Pik 7", "Pik 8", "Pik 9", "Pik 10", "Pik Bube", "Pik Dame",
                "Pik König", "Pik Ass", "Herz 2", "Herz 3", "Herz 4", "Herz 5", "Herz 6", "Herz 7", "Herz 8",
                "Herz 9", "Herz 10", "Herz Bube", "Herz Dame", "Herz König", "Herz Ass", "Karo 2", "Karo 3",
                "Karo 4", "Karo 5", "Karo 6", "Karo 7", "Karo 8", "Karo 9", "Karo 10", "Karo Bube",
                "Karo Dame", "Karo König", "Karo Ass"};

        mix(cards, mcards);
        active = true;
        gameover = false;

//-------------------Start--------------------------
        System.out.println("----------------------------------");
        System.out.println(" Willkommen zum Blackjack Spiel. ");
        System.out.println("----------------------------------");

        draw(mcards, player, dealer, "d");
        checkcards(player, dealer, "d");

        draw(mcards, player, dealer, "p");
        draw(mcards, player, dealer, "p");
        checkcards(player, dealer, "p");
        System.out.println("Deine Karten:" + player);
        System.out.println("Kartenwert: " + playerscore);

//---------------------------------------------------

        do {
            try {
                System.out.println();
                System.out.println();
                System.out.println("Noch eine Karte ziehen? (y/n)");
                String input = in.readLine();

                if (input.equals("y")) {

                    draw(mcards, player, dealer, "p");
                    checkcards(player, dealer, "p");
                    System.out.println();
                    System.out.println();
                    System.out.println("Deine Karten:" + player);
                    System.out.println("Kartenwert: " + playerscore);

                } else if (input.equals("n")) {
                    dealer(mcards, player, dealer);
                } else {
                    throw new InvalidArgumentException("Fehler: Bitte nur [y] oder [n] angeben");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());

                if (e instanceof InvalidArgumentException) {
                    System.out.println("Fehler bei der Eingabe");
                    continue;
                }
                if (e instanceof OutOfRuntimeException) {
                    System.out.println("Fehler beim Beenden des Spiels");
                    System.exit(0);
                }
            }
        } while (active);
    }
//-------------------------------------------------------

public static void draw(ArrayList<String> mcards, ArrayList<String> player, ArrayList<String> dealer, String turn){
    if(turn.equals("p")){
        player.add(mcards.get(1));
        mcards.remove(1);
    }
    if(turn.equals("d")){
        dealer.add(mcards.get(1));
        mcards.remove(1);
    }
}

public static void checkcards(ArrayList<String> player, ArrayList<String> dealer, String turn) {

ArrayList<String> tmp = new ArrayList<>();
int tmpscore = 0;
if(turn.equals("p")){tmp.addAll(player);}
else{tmp.addAll(dealer);}

    for(int i = 0; i < tmp.size(); i++) {
        if(tmp.get(i).contains("2")) {
            tmpscore += 2;
        } else if(tmp.get(i).contains("3")) {
            tmpscore += 3;
        } else if(tmp.get(i).contains("4")) {
            tmpscore += 4;
        } else if(tmp.get(i).contains("5")) {
            tmpscore += 5;
        } else if(tmp.get(i).contains("6")) {
            tmpscore += 6;
        } else if(tmp.get(i).contains("7")) {
            tmpscore += 7;
        } else if(tmp.get(i).contains("8")) {
            tmpscore += 8;
        } else if(tmp.get(i).contains("9")) {
            tmpscore += 9;
        } else if(tmp.get(i).contains("10")) {
            tmpscore += 10;
        } else if(tmp.get(i).contains("Bube")) {
            tmpscore += 10;
        } else if(tmp.get(i).contains("Dame")) {
            tmpscore += 10;
        } else if(tmp.get(i).contains("König")) {
            tmpscore += 10;
        } else if(tmp.get(i).contains("Ass")) {
            tmpscore += 11;
        }
    }
    if(turn.equals("p")){playerscore = tmpscore;}
    else{dealerscore = tmpscore;}
    tmp.clear();
    rules();
}

public static void rules(){
    if (!gameover){
        if(playerscore == 21){
            active = false;
            System.out.println("Du hast mit einem Blackjack gewonnen.");
        }
        else if(dealerscore == 21){
            active = false;
            System.out.println("Der Dealer hat leider mit einem Blackjack gewonnen.");
        }
        else if(playerscore >= 22){
            active = false;
            System.out.println("Du hast dich mit " + (playerscore - 21) + " Punkt(en) überworfen.");
        }
        else if(dealerscore >= 22){
            active = false;
            System.out.println("Der Dealer hat sich Überworfen");
        }
    } else {
        throw new OutOfRuntimeException("Error: Regeln wurden nach Spielende aufgerufen");
    }
}

public static void dealer(ArrayList<String> mcards, ArrayList<String> player, ArrayList<String> dealer){
    dealer_active = true;
    while (dealer_active){
        if(dealerscore <= 16){
            draw(mcards,player,dealer,"d");
            checkcards(player,dealer,"d");
        } else{
            if(dealerscore >= playerscore && dealerscore <= 21){
                System.out.println("Der Dealer hat " + dealerscore);
                System.out.println(dealer);
                System.out.println();
                System.out.println("Du hast verloren.");
                System.exit(0);
            } else{
                System.out.println("Der Dealer hat " + dealerscore);
                System.out.println(dealer);
                System.out.println();
                System.out.println("Du hast gewonnen.");
                System.exit(0);
            }
        }
    }
}

public static void mix(String[] cards, ArrayList<String> mcards){
    String tmp;
    int rand;
    Random r = new Random();
        for (int i = 0; i < cards.length; i++) {
            rand = r.nextInt(cards.length);
            tmp = cards[i];
            cards[i] = cards[rand];
            cards[rand] = tmp;
        }
    for (int i = 0; i < cards.length; i++){mcards.add(i, cards[i]);}
    }
}
