public class Player extends Entity {
    private int score = 0;
    private boolean isBreathingIce;
    private boolean isPaused;
    private int highScore;

    public Player(int x, int y, int maxBound) {
        this.x = x;
        this.y = y;
        score = 0;
        highScore = 0;
        this.maxBound = maxBound;
        isBreathingIce = false;
        isPaused = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isBreathingIce() {
        return isBreathingIce;
    }

    public void setBreathingIce(boolean isBreathingIce) {
        this.isBreathingIce = isBreathingIce;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getHighScore() {
        return highScore;
    }

    @Override
    public void killEntity() {
        isDead = true;
        isPaused = true;
    }
    @Override
    public void reviveEntity() {
        isDead = false;
        if (score > highScore)
            highScore = score;
        score = 0;
        x = 0;
        y = 0;
    }
}
