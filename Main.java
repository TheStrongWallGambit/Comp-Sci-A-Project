import java.util.Scanner;
// no errors as far as I can tell and it fulfills all the requirements, good job - soha

// Parker Comments:
//  Code looks to abl1
// e functioning, kinda hard to read though
//  Lookin good

//Wendy Wang
//Great game, great code, pretty fun
//be more specific with your instuctions  


public class Main{
    private static final int POTIONS_PER_BATTLE = 4;
    private static final int POTION_HEAL = 40;
    private static final int BAR_LENGTH = 20;
    private static final int STARTING_BALLS = 5;

    // the 3 Javamon and their types, same index = same Javamon
    private static final String[] SPECIES = {"Strirpent", "Intsect", "Baboolean"};
    private static final String[] TYPES = {"String", "int", "boolean"};
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
        System.out.println(rivalName + ": Took you long enough, " + name + ". I was starting to think you'd crashed.");
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
        Javamon starter;
        Javamon rivalMon;
        if (choice.equals("1")) {
            starter = new Javamon("Strirpent", "String");
            rivalMon = new Javamon("Intsect", "int", 5);
        } else if (choice.equals("2")) {
            starter = new Javamon("Intsect", "int");
            rivalMon = new Javamon("Baboolean", "boolean", 5);
        } else {
            starter = new Javamon("Baboolean", "boolean");
            rivalMon = new Javamon("Strirpent", "String", 5);
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
        System.out.println("===== How to battle =====");
        System.out.println("1) Attack  - deal damage. The faster Javamon hits first; type advantage does 1.5x damage.");
        System.out.println("2) Heal    - restore " + POTION_HEAL + " HP, up to " + POTIONS_PER_BATTLE + " times. The rival still attacks that turn.");
        System.out.println("3) Forfeit - give up the battle.");
        System.out.println();
        System.out.println(rivalName + " wants to battle!");
        if (battle(input, starter, rivalMon)) {
            System.out.println(rivalName + ": What? I picked the type that beats yours!");
        } else {
            System.out.println(rivalName + ": Ha! Told you.");
        }

        // Broke gives you the Javadex and javaballs
        starter.restore();
        System.out.println();
        System.out.println("Broke: What a battle! I healed " + starter.getNickname() + " for you.");
        System.out.println("Broke: Take this Javadex. It records every Javamon you catch, and here are " + STARTING_BALLS + " Javaballs.");
        Javadex dex = new Javadex(name, STARTING_BALLS);
        dex.register(starter);

        // Catching instructions
        System.out.println();
        System.out.println("===== How to catch =====");
        System.out.println("1) Attack        - weaken the wild Javamon. If it faints, it can't be caught.");
        System.out.println("2) Throw Javaball - lower HP means a better chance: 20% at full HP, almost 100% near 0.");
        System.out.println("3) Run           - leave the encounter.");

        // Tall grass, keeps going until you leave, run out of javaballs, or faint
        boolean exploring = true;
        while (exploring) {
            int k = (int) (Math.random() * SPECIES.length);
            int wildLevel = 3 + (int) (Math.random() * 4); // level 3 to 6
            Javamon wild = new Javamon(SPECIES[k], TYPES[k], wildLevel);

            System.out.println();
            System.out.println("You walk into the tall grass...");
            if (Math.random() < 0.2) {
                dex.addJavaballs(1);
                System.out.println("You found a Javaball on the ground!");
            }
            if (dex.hasSpecies(wild.getSpecies())) {
                System.out.println("A wild " + wild.getSpecies() + " (Lv" + wild.getLevel() + ") appeared! You've caught one before.");
            } else {
                System.out.println("A wild " + wild.getSpecies() + " (Lv" + wild.getLevel() + ") appeared! New to your Javadex!");
            }
            encounter(input, starter, wild, dex);

            if (starter.isFainted()) {
                System.out.println("You rushed back to Prof. Broke's lab.");
                starter.restore();
                exploring = false;
            } else if (dex.getJavaballs() == 0) {
                System.out.println("You're out of Javaballs, so you head back to the lab.");
                exploring = false;
            } else {
                System.out.println("Keep exploring? (y/n)");
                String answer = input.nextLine().trim();
                while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                    System.out.println("Just type y or n.");
                    answer = input.nextLine().trim();
                }
                exploring = answer.equalsIgnoreCase("y");
            }
        }

        // Shows the Javadex at the end
        System.out.println();
        System.out.println(dex);
        System.out.printf("Average level: %.1f%n", dex.averageLevel());
        System.out.println("Highest level: " + dex.highestLevel().getSpecies() + " (Lv" + dex.highestLevel().getLevel() + ")");
        System.out.println("Broke: Javadexes handed out so far: " + Javadex.getDexesIssued() + ". Good luck, " + dex.getOwner() + "!");
        
    }
    public static boolean battle(Scanner input, Javamon player, Javamon opponent) {
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
    // Wild encounter, ends when you catch it, it faints, you run, or you faint
        public static void encounter(Scanner input, Javamon player, Javamon wild, Javadex dex) {
        boolean over = false;
        while (!over) {
            System.out.println();
            printStatus(player);
            printStatus(wild);
            System.out.println("1) Attack   2) Throw Javaball (" + dex.getJavaballs() + " left)   3) Run");
            String action = input.nextLine().trim();

            if (action.equals("1")) {
                attack(player, wild);
                if (wild.isFainted()) {
                    System.out.println("The wild " + wild.getSpecies() + " fainted. Too weak to catch now!");
                    over = true;
                    continue;
                }
            } else if (action.equals("2")) {
                if (dex.getJavaballs() == 0) {
                    System.out.println("You're out of Javaballs!");
                    continue;   // turn not used
                } else if (dex.countCaught() == Javadex.CAPACITY) {
                    System.out.println("Your Javadex is full!");
                    continue;   // turn not used
                }
                System.out.println("You threw a Javaball...");
                if (dex.tryCatch(wild)) {
                    System.out.println("Gotcha! " + wild.getSpecies() + " was added to your Javadex!");
                    over = true;
                    continue;
                }
                System.out.println("Oh no! It broke free!");
            } else if (action.equals("3")) {
                System.out.println("You got away safely.");
                over = true;
                continue;
            } else {
                System.out.println("Type 1, 2, or 3.");
                continue;   // turn not used
            }

            // wild Javamon attacks back
            attack(wild, player);
            if (player.isFainted()) {
                System.out.println(player.getNickname() + " fainted!");
                over = true;
            }
        }
    }
    // One attack: deals damage and prints what happened
    public static void attack(Javamon attacker, Javamon defender) {
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
    public static void printStatus(Javamon m) {
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

