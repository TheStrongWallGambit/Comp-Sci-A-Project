public class javamon {
    private String species; // what kind it is
    private String nickname; // what the player names it
    private String type;// One of 9 java types
    private int level;
    private int maxHp;
    private int attack;
    private int hp;
    private int defense;
    private int speed;

    private boolean fainted;
    public javamon(String species, String type, int level) {   // layer 3: the constructor
        this.species = species;
        this.nickname = species;   // starts the same as species
        this.type = type;
        this.level = level;

        maxHp = 20 + level * 5;
        hp = maxHp;                // start at full health
        attack = 10 + level * 2;
        defense = 8 + level * 2;
        speed = 10 + level;
        fainted = false;           // a new Javamon isn't fainted
}
    public int getHp(){
        return hp;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isFainted(){
        return fainted
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

    public void setNickname(String newName) {
        nickname = newName;
    }
}
