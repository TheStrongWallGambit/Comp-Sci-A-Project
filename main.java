import java.util.Scanner;
public class main
{
    public static void main(String[]args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello there! Welcome to the world of Javamon!\n My name is Broke! People call me the Javamon Prof! This world is inhabited by creatures called Javamon!\n For some people, Javamon are coding slaves. Others use them for fights.\n Myself… I study Javamon as a profession. First, what is your name?");
        String name = input.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Come on, everyone has a name. What's yours?");
            name = input.nextLine().trim();
        }
        System.out.println(name+"? Thats a great name! Your very own Javamon legend is about to unfold!\n A world of dreams and adventures with Javamon awaits! Let’s go!");
    }
}