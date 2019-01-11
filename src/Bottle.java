public class Bottle extends Entity {
    private int score = 100;

    public Bottle(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void relocateEntity(int x, int y) {
        isDead = false;
        this.x = x;
        this.y = y;
    }

    public int getScore() {
        return score;
    }
}
