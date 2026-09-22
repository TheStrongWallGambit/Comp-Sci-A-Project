public class javamon {
    private static final double EFFECTIVE = 1.5; // Damage when type advantage
    private static final double INEFFECTIVE = 0.75; //Damage when type disadvantage
    private static final double CRIT_CHANCE = 0.1; // 10% chance of a cticial hit
    private static final double CRIT_BONUS = 1.5;

    private String species; // what kind it is
    private String nickname; // what the player names it
    private String type;// One of 3 java types
    private int level;
    private int maxHp;
    private int attack;
    private int hp;
    private int defense;
    private int speed;
    private boolean fainted;
    private boolean lastHitCritical; // Allows main to print "Critical hit"

    public javamon(String species, String type){
        this(species, type, 5);
    }
    public javamon(String species, String type, int level) {   
        this.species = species;
        this.nickname = species;   // starts the same as species
        this.type = type;
        this.level = level;

        maxHp = 20 + level * 5 + randomInRange(0,5);
        hp = maxHp;                // start at full health
        attack = 10 + level * 2 + randomInRange(0,3);
        defense = 8 + level * 2 + randomInRange(0,3);
        if (type.equals("boolean")) {
            speed = 14 + level + randomInRange(0, 3);
        } else if (type.equals("int")) {
            speed = 12 + level + randomInRange(0, 3);
        } else {
            speed = 10 + level + randomInRange(0, 3);
        }
        fainted = false;           // a new Javamon isn't fainted
        lastHitCritical = false;

    
}
    private static int randomInRange(int min, int max) {
        return min + (int) (Math.random()* (max - min + 1));
    }
    
    public String getSpecies(){
        return species;
    }

    public String getNickname() {
        return nickname;
    }

    public String getType() {
        return type;
    }

    public double hpFraction() {
        return(double) hp/maxHp;
    }

    public int getLevel() {
        return level;
    }

    public int getHp(){
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    

    public boolean isFainted(){
        return fainted;
    }

    public boolean wasLastHitCritical(){
        return lastHitCritical;
    }

    public void setNickname(String newName){
        nickname = newName;
    }
    // Hp never goes below 0; 0 hp = fainted
    public void takeDamage(int amount) {
        hp -= amount ;
        if (hp <= 0){
            hp =0;
            fainted = true;
        }
    }
    // hp never goes above maxHp
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
    //String > boolean > int > string
    public double typeMultiplier(String defenderType){
        if ((type.equals("String") && defenderType.equals("boolean"))
                || (type.equals("boolean") && defenderType.equals("int"))
                || (type.equals("int") && defenderType.equals("String"))) {
            return EFFECTIVE;
        } else if (type.equals(defenderType)) {
            return 1.0;
        } else {
            return INEFFECTIVE;
        }
    }

    // base damage, type, critical hit, random spread
    public int calculateDamage(javamon target) {
        double damage = attack - target.getDefense() / 2;
        damage *= typeMultiplier(target.getType());

        lastHitCritical = Math.random() < CRIT_CHANCE;
        if (lastHitCritical) {
            damage *= CRIT_BONUS;
        }
        damage *= 0.85 + Math.random() * 0.15;   // random spread: 85% to 100%

        int result = (int) damage;
        if (result < 1) {
            result = 1;
        }
        return result;
    }
}
