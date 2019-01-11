//import javafx.scene.image.Image;

public abstract class Entity {
    protected int x, y, maxBound;

    //private Image sprite;
    protected boolean isLeft, isRight, isUp, isDown = true, isDead = false;

    public void moveLeft() {
        if (!isDead) {
            if (x > 0)
                x--;
            isLeft = true;
            isRight = false;
            isUp = false;
            isDown = false;
        }
    }

    public void moveRight() {
        if (!isDead) {
            if (x < maxBound - 1)
                x++;
            isRight = true;
            isUp = false;
            isDown = false;
            isLeft = false;
        }
    }

    public void moveUp() {
        if (!isDead) {
            if (y > 0)
                y--;
            isUp = true;
            isRight = false;
            isDown = false;
            isLeft = false;
        }
    }

    public void moveDown() {
        if (!isDead) {
            if (y < maxBound - 1)
                y++;
            isDown = true;
            isUp = false;
            isRight = false;
            isLeft = false;
        }
    }

    public void killEntity() {
        isDead = true;
    }

    public void reviveEntity() {
        isDead = false;
        x = 0;
        y = 0;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public boolean isUp() {
        return isUp;
    }

    public boolean isDown() {
        return isDown;
    }
}
