import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GameView extends Pane {
    private IceField ice;
    private Player player;
    private Bottle bottle;
    private Image textures;
    final private Canvas canvas;

    private GraphicsContext gc;

    public GameView(IceField ice, Player player, Bottle bottle, int scale) {
        this.ice = ice;
        this.player = player;
        this.bottle = bottle;
        setPrefSize(ice.getSize() * 10 * scale, ice.getSize() * 10 * scale);
        canvas = new Canvas(getPrefWidth(), getPrefHeight());

        gc = canvas.getGraphicsContext2D();


        gc.scale(scale, scale);
        textures = new Image(getClass().getResourceAsStream("res/textures.png"));
        getChildren().addAll(canvas);
        render();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render ice field
        int size = 10;
        for (int row = 0; row < ice.getSize(); row++) {
            for (int col = 0; col < ice.getSize(); col++) {
                switch (ice.getTile(row, col).getTemperature()) {
                    case -4:
                        gc.drawImage(
                                textures, 0, 10, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    case -3:
                        gc.drawImage(
                                textures, 0, 20, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    case -2:
                        gc.drawImage(
                                textures, 0, 30, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    case -1:
                        gc.drawImage(
                                textures, 0, 40, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    case 0:
                        gc.drawImage(
                                textures, 0, 50, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    case 1:
                        // draw water
                        gc.drawImage(
                                textures, 10, 0, 10, 10, col * size, row * size, size, size
                        );
                        break;
                    default:
                        // draw ice
                        gc.drawImage(
                                textures, 0, 0, 10, 10, col * size, row * size, size, size
                        );
                        break;
                }
            }
        }
        // render bottle
        gc.drawImage(
                textures, 30, 0, 10, 10, bottle.getX() * size, bottle.getY() * size, size, size
        );
        // render player
        if (player.isDead()) {
            // do nothing
        } else if (player.isDown()) {
            if (!player.isBreathingIce()) {
                gc.drawImage(
                        textures, 20, 0, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            } else {
                gc.drawImage(
                        textures, 20, 10, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            }
        } else if (player.isRight()) {
            if (!player.isBreathingIce()) {
                gc.drawImage(
                        textures, 20, 20, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            } else {
                gc.drawImage(
                        textures, 20, 30, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            }
        } else if (player.isLeft()) {
            if (!player.isBreathingIce()) {
                gc.drawImage(
                        textures, 20, 40, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            } else {
                gc.drawImage(
                        textures, 20, 50, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            }
        } else if (player.isUp()) {
            if (!player.isBreathingIce()) {
                gc.drawImage(
                        textures, 20, 60, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            } else {
                gc.drawImage(
                        textures, 20, 70, 10, 10, player.getX() * size, player.getY() * size, size, size
                );
            }
        }
        // draw score
        gc.fillText("SCORE: " + player.getScore() + " HISCORE: " + player.getHighScore(), canvas.getWidth()/2 - 50, 20);

        // pause menu
        if (player.isPaused()) {
            gc.fillText("ICE SHEET MELTDOWN\n- WASD or Arrow keys to move, ESC to pause, space to breathe ice.\n" +
                    "- Collect the bottles to score!\n- Your body gives off heat!\n- Survive for as long as you can!",canvas.getWidth()/8,canvas.getHeight()/8);
        }
        gc.save();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
