public class Javadex {
     private static int dexesIssued = 0;

     public static final int CAPACITY = 6;

     private static final double CATCH_FLOOR = 0.2;
     private String owner;
     private Javamon[] caught;
     private int javaballs;

    public Javadex(String owner, int capacity, int javaballs) {
        this.owner = owner;
        this.caught = new Javamon[capacity];
        this.javaballs = javaballs;
        dexesIssued++;
    }

    public Javadex(String owner, int javaballs) {
        this(owner, CAPACITY, javaballs);
    }

    public static int getDexesIssued() {
        return dexesIssued;
    }

    public String getOwner() {
        return owner;
    }

    public int getJavaballs() {
        return javaballs;
    }

    public void addJavaballs(int amount) {
        if (amount > 0) {
            javaballs += amount;
        }
    }

    public boolean register(Javamon j) {
        for (int i = 0; i < caught.length; i++) {
            if (caught[i] == null) {
                caught[i] = j;
                return true;
            }
        }
        return false;
    }

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

    public int countCaught() {
        int count = 0;
        for (Javamon j : caught) {
            if (j != null) {
                count++;
            }
        }
        return count;
    }

    public boolean hasSpecies(String species) {
        for (Javamon j : caught) {
            if (j != null && j.getSpecies().equals(species)) {
                return true;
            }
        }
        return false;
    }

    public Javamon highestLevel() {
        Javamon best = null;
        for (Javamon j : caught) {
            if (j != null && (best == null || j.getLevel() > best.getLevel())) {
                best = j;
            }
        }
        return best;
    }

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
