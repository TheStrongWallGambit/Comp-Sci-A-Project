import java.util.Scanner;
public class main{
    private static final int POTIONS_PER_BATTLE = 4;
    private static final int POTION_HEAL = 40;
    private static final int BAR_LENGTH = 20;
    public static void main(String[] args){
        //Intro to the game 
        Scanner input = new Scanner(System.in);
        System.out.println("Hello there! Welcome to the world of Javamon!\n My name is Broke! People call me the Javamon Prof! This world is inhabited by creatures called Javamon!\n For some people, Javamon are used for coding. Others use them for fights.\n Myself. . .  I study Javamon as a profession. First, what is your name?");
        // Player input's name
        String name = input.nextLine().trim();
        //Should the name be returned as empty it will request name again
        while (name.isEmpty()) {
            System.out.println("Come on, everyone has a name. What's yours?");
            name = input.nextLine().trim();
        }
        System.out.println(name+"? That's a great name! Your very own Javamon legend is about to unfold!\n A world of dreams and adventures with Javamon awaits! Let's go!");
        // Rival Intro
        String rivalName = "Null";
        System.out.println();
        System.out.println("Broke: Ah, and this is my grandson, " + rivalName+ ". He's been waiting all morning.");
        System.out.println(rivalName + ": Took you long enough, " +name+ ". I was starting to think you'd crashed.");
        // Starter Javamon selection
        System.out.println();
        System.out.println("Broke: Three Javamon, three types. Each one beats another, so choose carefully.");
        System.out.println(" 1) Strirpent - String type");
        System.out.println(" 2) Intsect - int type");
        System.out.println(" 3) Baboolean - boolean type");
        
        String choice = input.nextLine().trim();
        while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.println("Broke: Just type 1, 2, or 3.");
            choice = input.nextLine().trim();
        }
        // Rival always picks the Javamon that beats yours. String > boolean > int > String
        javamon starter;
        javamon rivalMon;
        if (choice.equals("1")) {
            starter = new javamon("Strirpent", "String");
            rivalMon = new javamon("Intsect", "int", 5);
        } else if (choice.equals("2")) {
            starter = new javamon("Intsect", "int");
            rivalMon = new javamon("Baboolean", "boolean", 5);
        } else {
            starter = new javamon("Baboolean", "boolean");
            rivalMon = new javamon("Strirpent", "String", 5);
        }
        System.out.println("Give your " + starter.getSpecies() + " a nickname? (press Enter to skip)");
        String nickname = input.nextLine().trim();
        if (nickname.length() > 12) {
            nickname = nickname.substring(0, 12);   // keeps the HP display lined up
        }
        if (!nickname.isEmpty()) {
            starter.setNickname(nickname);
        }

        System.out.println();
        System.out.println(name + " chose " + starter.getNickname() + "!");
        System.out.println(rivalName + ": Then I'll take " + rivalMon.getNickname() + ". Let's see what yours can do!");

        // First rival battle
        System.out.println();
        System.out.println(rivalName + " wants to battle!");
        if (battle(input, starter, rivalMon)) {
            System.out.println(rivalName + ": What? I picked the type that beats yours!");
        } else {
            System.out.println(rivalName + ": Ha! Told you.");
        }
        
    }
    public static boolean battle(Scanner input, javamon player, javamon opponent) {
        int potions = POTIONS_PER_BATTLE;
        boolean forfeited = false;
        int turn = 1;
 
        // Faster Javamon attacks first; ties go to the player
        boolean playerFirst = player.getSpeed() >= opponent.getSpeed();
        if (playerFirst) {
            System.out.println(player.getNickname() + " is faster and will attack first!");
        } else {
            System.out.println(opponent.getNickname() + " is faster and will attack first!");
        }
 
        while (!player.isFainted() && !opponent.isFainted() && !forfeited) {
            System.out.println();
            System.out.println("===== Turn " + turn + " =====");
            printStatus(player);
            printStatus(opponent);
            System.out.println("1) Attack   2) Heal (" + potions + " left)   3) Forfeit");
            String action = input.nextLine().trim();
 
            if (action.equals("1")) {
                // Both attack; speed decides the order
                if (playerFirst) {
                    attack(player, opponent);
                    if (!opponent.isFainted()) {
                        attack(opponent, player);
                    }
                } else {
                    attack(opponent, player);
                    if (!player.isFainted()) {
                        attack(player, opponent);
                    }
                }
            } else if (action.equals("2")) {
                // Healing always happens before the rival attacks
                if (potions > 0 && player.getHp() < player.getMaxHp()) {
                    int before = player.getHp();
                    player.heal(POTION_HEAL);
                    potions -= 1;
                    System.out.println(player.getNickname() + " healed " + (player.getHp() - before) + " HP!");
                    attack(opponent, player);
                } else if (potions == 0) {
                    System.out.println("No potions left!");
                    continue;   // turn not used
                } else {
                    System.out.println(player.getNickname() + " is already at full health!");
                    continue;   // turn not used
                }
            } else if (action.equals("3")) {
                forfeited = true;
                continue;       // ends the battle right away
            } else {
                System.out.println("Type 1, 2, or 3.");
                continue;       // turn not used
            }
            turn++;
        }
 
        System.out.println();
        if (forfeited) {
            System.out.println("You forfeited the battle.");
            return false;
        } else if (opponent.isFainted()) {
            System.out.println(opponent.getNickname() + " fainted! You win!");
            return true;
        } else {
            System.out.println(player.getNickname() + " fainted! You lose.");
            return false;
        }
    }
 
    // One attack: deals damage and prints what happened
    public static void attack(javamon attacker, javamon defender) {
        int damage = attacker.calculateDamage(defender);
        defender.takeDamage(damage);
        System.out.println(attacker.getNickname() + " attacks for " + damage + " damage!");
 
        if (attacker.wasLastHitCritical()) {
            System.out.println("A critical hit!");
        }
        double multiplier = attacker.typeMultiplier(defender.getType());
        if (multiplier > 1.0) {
            System.out.println("It's super effective!");
        } else if (multiplier < 1.0) {
            System.out.println("It's not very effective...");
        }
    }
 
    // Prints name, level, HP, and an HP bar
    public static void printStatus(javamon m) {
        System.out.printf("%-12s Lv%-3d HP %3d/%-3d DEF %2d [", m.getNickname(), m.getLevel(), m.getHp(), m.getMaxHp(), m.getDefense());
 
        int filled = (int) (m.hpFraction() * BAR_LENGTH);
        if (m.getHp() > 0 && filled == 0) {
            filled = 1;   // show a sliver
        }
        for (int i = 0; i < BAR_LENGTH; i++) {
            if (i < filled) {
                System.out.print("#");
            } else {
                System.out.print("-");
            }
        }
        System.out.println("]");
    }
}

