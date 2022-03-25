// Justin Do

import java.util.ArrayList;
import java.util.Scanner;

public class DungeonEscape {

    public static final Scanner input = new Scanner(System.in);

    /*
     * The main method is where the game gets to be played
     * It makes the player then the dungeon with the specified parameters made by the user
     * It then makes the monsters
     * And then tells the user where they are, how much health they have, and how many monsters they smell nearby
     * It then loops through and keeps asking them which way they want to go
     * It also allows the user to fight the monster monsters
     * If they die it will end the game
     * If they are still alive and escape it ends the game and congratulates them.
     */
    public static void main(String[] args) {

        Character mainPlayer = makePlayer();
        int dungeonSize = getDungeonSize();
        ArrayList<Character> monsterList = makeMonsters(dungeonSize);
        System.out.println(mainPlayer.getName() + " at " + mainPlayer.getRow() + ", " + mainPlayer.getCol() + " with "
                + mainPlayer.getHealth() + " health.");
        System.out.println("You smell " + countNearbyMonsters(mainPlayer, monsterList) + " monsters nearby.");
        input.nextLine();
        while (!mainPlayer.hasEscaped(dungeonSize) && mainPlayer.isAlive()) {
            System.out.print("Which way do you want to go (north, south, east, west)? ");
            String moveInput = input.nextLine();
            move(mainPlayer, moveInput, monsterList, dungeonSize);
        }
        if (!mainPlayer.isAlive()) {
            System.out.println("You were too weak. Game over :(");
        }
        if (mainPlayer.isAlive() && mainPlayer.hasEscaped(dungeonSize)) {
            System.out.println("You have escaped the dungeon!");
        }
    }

    /*
     * This method is used to make a new character for the game
     */
    public static Character makePlayer() {
        Character one = new Character("Placeholder", 100, 0, 0);
        System.out.print("What is your name, heroic adventurer? ");
        one.setName(input.nextLine());
        return one;
    }

    /*
     * This method is used to ask the user how big of a dungeon size they want
     * It does not accept inputs that are not integers or integers that are less than 5 or greater than 10
     */
    public static int getDungeonSize() {
        int dungeonSize;

        do {
            System.out.print("How wide of a dungeon do you want to face (5-10)? ");

            while (!input.hasNextInt()) {
                input.next();
                System.out.println("That is not a valid dungeon size!");
                System.out.print("How wide of a dungeon do you want to face (5-10)? ");
            }

            dungeonSize = input.nextInt();

            if (dungeonSize < 5 || dungeonSize > 10) {
                System.out.println("That is not a valid dungeon size!");
            }
        } while (dungeonSize < 5 || dungeonSize > 10);
        return dungeonSize - 1;
    }

    /*
     * This method is used to determine the maximum amount of monsters that can be created.
     * It creates the monsters at random locations in the dungeon.
     */
    public static ArrayList<Character> makeMonsters(int dungeonSize) {
        int numberOfMonsters = ((dungeonSize + 1) * (dungeonSize + 1)) / 6;
        ArrayList<Character> Monsters = new ArrayList<>();
        for (int i = 1; i < (numberOfMonsters + 1); i++) {
            Character currentMonster = new Character(String.valueOf(i), 25, (int) (Math.random() * dungeonSize + 1), (int) (Math.random() * dungeonSize + 1));
            Monsters.add(currentMonster);
        }
        return Monsters;
    }

    /*
     * This method is used to fight multiple monsters if they are in the same room.
     */
    public static void fightMonsters(Character player, ArrayList<Character> monsters) {
        for (Character m : monsters) {
            if (player.inSameRoom(m) && m.isAlive()) {
                player.fight(m);
            }
        }
    }

    /*
     * This removes the dead monsters from the ArrayList after each fight
     */
    public static void removeDeadMonsters(ArrayList<Character> monsters) {
        ArrayList<Character> removeMons = new ArrayList<>();

        for (Character m : monsters) {
            if (!m.isAlive()) {
                removeMons.add(m);
            }
        }
        monsters.removeAll(removeMons);
    }

    /*
     * This method is used to count the number of monsters that are in nearby rooms around the player
     */
    public static int countNearbyMonsters(Character player, ArrayList<Character> monsters) {
        int count = 0;
        for (Character m : monsters) {
            if (player.inAdjacentRoom(m) && m.isAlive()) {
                count++;
            }
        }
        return count;
    }

    /*
     * This method is used to allow the player to move around the rooms.
     * The player will lose 2 HP every time they move.
     * It also tells them if they can't move in a certain way.
     */
    public static void move(Character player, String inputDirection, ArrayList<Character> monsters, int dungeonSize) {
        if (player.canMove(inputDirection, dungeonSize)) {
            if (inputDirection.equalsIgnoreCase("north")) {
                player.setRow(player.getRow() - 1);
            } else if (inputDirection.equalsIgnoreCase("east")) {
                player.setCol(player.getCol() + 1);
            } else if (inputDirection.equalsIgnoreCase("south")) {
                player.setRow(player.getRow() + 1);
            } else if (inputDirection.equalsIgnoreCase("west")) {
                player.setCol(player.getCol() - 1);
            }
            player.setHealth(player.getHealth() - 2);
            fightMonsters(player, monsters);
            removeDeadMonsters(monsters);
            if (player.isAlive() && !player.hasEscaped(dungeonSize)) {
                System.out.println(player.getName() + " at " + player.getRow() + ", " + player.getCol()
                        + " with " + player.getHealth() + " health.");
                System.out.println("You smell " + countNearbyMonsters(player, monsters) + " monsters nearby.");
            }
        } else {
            System.out.println("You can't move that way!");
        }
    }
}

