import java.util.Scanner;
public class main

{
    public static void main(String[]args)
    {
        //Intro to the game 
        Scanner input = new Scanner(System.in);
        System.out.println("Hello there! Welcome to the world of Javamon!\n My name is Broke! People call me the Javamon Prof! This world is inhabited by creatures called Javamon!\n For some people, Javamon are used for codin. Others use them for fights.\n Myself… I study Javamon as a profession. First, what is your name?");
        // Player input's name
        String name = input.nextLine().trim();
        //Should the name be returned as empty it willrequest name again
        while (name.isEmpty()) {
            System.out.println("Come on, everyone has a name. What's yours?");
            name = input.nextLine().trim();
        }
        System.out.println(name+"? Thats a great name! Your very own Javamon legend is about to unfold!\n A world of dreams and adventures with Javamon awaits! Let’s go!");
        // Rival Intro
        String rivalName = "Null";
        System.out.println();
        System.out.println("Broke: Ah, and this is my grandson, " + rivalName+ ". He's been waiting all morning.");
        System.out.println(rivalName + ": Took you long enough, " +name+ ". I was starting to think you'd crashed.");
        // Starter Javamon selection
        System.out.println();
        System.out.println("Broke: Three Javamon, three types. Each one beats another, so choose carefully.");
        System.out.println(" 1) Strirpent - String type");
        System.out.println(" 2) Intsect - Int type");
        System.out.println(" 3) Baboolean - Boolean type");
        
        Strong choice = input.nextLine().trim();
        while (!choice.equals("1") && ! choice.equals("2") & !choice.equals("3")){
            System.out.printlin("Broke: Just ype 1, 2, or 3.";
            choice = input.nextLine().trim();
            )
        }
        // Rival always picks the Javamon that beats yours: String>Boolean>Int>String
        javamon starter;
        javamon rivalMon;
        if (choice.equals("1")){
            starter = new Javamon("Stirpent", "String", 5);
            rivalMon = new Javamon("Intsect", "Int", 5);
        }else if (choice.equals("1")){
            starter = new Javamon("Intsect", "Int", 5);
            rivalMon = new Javamon("Babooleam", "Boolean", 5);
           
        }if (choice.equals("1")){
            starter = new Javamon("Baboolena", "Boolen", 5);
            rivalMon = new Javamon("Stirpent", "5");
    }
        System.out.println("Give your " + starter.getNickname() + " a nickname? (press Enter to skip)");
        String nickname = input.nextLine().trim();
        if (!nickname.isEmpty()) {
            starter.setNickname(nickname);
        }

        System.out.println();
        System.out.println(name + " chose " + starter.getNickname() + "!");
        System.out.println(rivalName + ": Then I'll take " + rivalMon.getNickname() + ". Let's see what yours can do!");
}
}