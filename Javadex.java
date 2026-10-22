// Javadex: keeps track of every Javamon you catch and how many Javaballs you have
public class Javadex {
    // static because it counts every dex ever made, not just one
    private static int dexesIssued = 0;

    // static because every dex has the same number of slots
    public static final int CAPACITY = 6;
    
    // static because the catch chance works the same for every dex
    private static final double CATCH_FLOOR = 0.2;
    private String owner;
    private Javamon[] caught;
    private int javaballs;
    // makes an empty dex
    // precondition: capacity > 0 and javaballs >= 0
    // postcondition: every slot is empty (null) and the dex has that many javaballs
    public Javadex(String owner, int capacity, int javaballs) {
        this.owner = owner;
        this.caught = new Javamon[capacity];
        this.javaballs = javaballs;
        dexesIssued++;
    }
    // normal dex with 6 slots
    public Javadex(String owner, int javaballs) {
        this(owner, CAPACITY, javaballs);
    }
    // how many dexes Broke has handed out. static since it only uses dexesIssued
    public static int getDexesIssued() {
        return dexesIssued;
    }
    // owner's name
    public String getOwner() {
        return owner;
    }
    // javaballs left
    public int getJavaballs() {
        return javaballs;
    }
    // adds javaballs, ignores negative numbers
    public void addJavaballs(int amount) {
        if (amount > 0) {
            javaballs += amount;
        }
    }
    // puts a Javamon in the first empty slot
    // precondition: j isn't null
    // postcondition: returns true if it was added, false if the dex is full (nothing changes)
    public boolean register(Javamon j) {
        for (int i = 0; i < caught.length; i++) {
            if (caught[i] == null) {
                caught[i] = j;
                return true;
            }
        }
        return false;
    }
    // throws a javaball at a wild Javamon
    // precondition: wild isn't null
    // postcondition: uses up 1 javaball, returns true and adds it to the dex if it was caught
    public boolean tryCatch(Javamon wild) {
        if (javaballs == 0 || wild.isFainted() || countCaught() == caught.length) {
            return false;
        }
        javaballs--;
        // lower hp = easier catch, 20% at full hp up to almost 100% near 0
        double chance = 1.0 - wild.hpFraction() * (1.0 - CATCH_FLOOR);
        if (Math.random() < chance) {
            return register(wild);
        }
        return false;
    }
    // how many Javamon are in the dex
    public int countCaught() {
        int count = 0;
        for (Javamon j : caught) {
            if (j != null) {
                count++;
            }
        }
        return count;
    }
    // true if you've already caught that species
    public boolean hasSpecies(String species) {
        for (Javamon j : caught) {
            if (j != null && j.getSpecies().equals(species)) {
                return true;
            }
        }
        return false;
    }
    // highest level Javamon in the dex, null if it's empty
    public Javamon highestLevel() {
        Javamon best = null;
        for (Javamon j : caught) {
            if (j != null && (best == null || j.getLevel() > best.getLevel())) {
                best = j;
            }
        }
        return best;
    }
       // average level of everything in the dex, 0 if empty
    public double averageLevel() {
        int[] levels = new int[countCaught()];
        int index = 0;
        for (Javamon j : caught) {
            if (j != null) {
                levels[index] = j.getLevel();
                index++;
            }
        }
        return average(levels);
    }
     // average of an int array. static since it only uses the array you pass in
    public static double average(int[] values) {
        if (values.length == 0) {
            return 0.0;
        }
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return (double) sum / values.length;
    }
    // prints the dex with every Javamon on its own line
    public String toString() {
        String result = owner + "'s Javadex (" + countCaught() + "/" + caught.length + " caught, "
                + javaballs + " Javaballs left)";
        for (int i = 0; i < caught.length; i++) {
            if (caught[i] != null) {
                result += "\n  #" + (i + 1) + " " + caught[i].getSpecies() + " (" + caught[i].getType()
                        + ") Lv" + caught[i].getLevel();
            }
        }
        return result;
    }
}
