// Justin Do
public class Character {

    String name;
    int health;
    int row;
    int col;

    public Character(String name, int health, int row, int col) {
        this.name = name;
        this.health = health;
        this.row = row;
        this.col = col;
    }

    /*
     * This method is used to see if the character is still alive.
     */
    public boolean isAlive() {
        return health > 0;
    }

    /*
     * This method is used to see if the player has escaped.
     * It does so by seeing if their position is equal to the max row and max col size.
     */
    public boolean hasEscaped(int dungeonSize) {
        return row == dungeonSize && col == dungeonSize;
    }

    /*
     * This method is used to see if the player and the monster are in the same room.
     * It does so by comparing the monster's row and col position and seeing if they are the same as the player.
     */
    public boolean inSameRoom(Character other) {
        return other.getRow() == this.row && other.getCol() == this.col;
    }

    /*
     * This method is used to see if the monster is in the room north, south, east, or west of the player.
     */
    public boolean inAdjacentRoom(Character other) {
        if (other.getCol() == this.col && other.getRow() == this.row - 1) { // North
            return true;
        } else if (other.getCol() == this.col && other.getRow() == this.row + 1) { // South
            return true;
        } else // West
            if (other.getRow() == this.row && other.getCol() == this.col + 1) { // East
            return true;
        } else return other.getRow() == this.row && other.getCol() == this.col - 1;
    }

    /*
     * This method is used to fight the monsters.
     * The player can deal 1 - 10 damage.
     * The monster can deal 1 - 5 damage.
     * It prints out each time how much damage you deal and how much damage you receive.
     */
    public void fight(Character other) {
        System.out.println(this.name + " at " + this.row + ", " + this.col + " with " + this.health + " health versus Monster "
                + other.getName() + " at " + other.getRow() + ", " + other.getCol() + " with " + other.getHealth() + " health.");
        while (this.health > 0 && other.getHealth() > 0) {
            int damage = (int) (Math.random() * 10) + 1;
            other.setHealth(other.getHealth() - damage);
            System.out.println("You deal " + damage + " damage.");
            int damage2 = (int) (Math.random() * 5) + 1;
            this.health -= damage2;
            System.out.println("You receive " + damage2 + " damage.");
        }
    }

    /*
     * This method is used to determine if the player can move in a certain direction or not.
     */
    public boolean canMove(String direction, int dungeonSize) {
        if (direction.equalsIgnoreCase("north") && this.row == 0) {
            return false;
        } else if (direction.equalsIgnoreCase("east") && this.col == dungeonSize) {
            return false;
        } else if (direction.equalsIgnoreCase("south") && this.row == dungeonSize) {
            return false;
        } else if (direction.equalsIgnoreCase("west") && this.col == 0) {
            return false;
        }
        return direction.equalsIgnoreCase("west") || direction.equalsIgnoreCase("east")
                || direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("south");
    }

    /*
     * Below are the getter and setter methods
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
