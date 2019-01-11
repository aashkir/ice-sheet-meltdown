import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/******************************************
 * LUDUM DARE 42 [RUNNING OUT OF SPACE]   *
 * BY: KIRASH                             *
 ******************************************/

public class IceSheetMeltdownApp extends Application {
    private final IceField ice = new IceField();
    private Player player;
    private Bottle bottle;
    private GameView game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        player = new Player(0,0, ice.getSize());
        bottle = new Bottle((int)(Math.random() * ice.getSize()), (int)(Math.random() * ice.getSize()));
        game = new GameView(ice, player, bottle,1);
        primaryStage.setTitle("Ice Sheet Meltdown");
        primaryStage.setResizable(true);
        System.out.println(game.getPrefHeight());
        primaryStage.setScene(new Scene(game, game.getPrefWidth(), game.getPrefHeight()));
        primaryStage.show();
        // Events
        game.getCanvas().setFocusTraversable(true);
        game.getCanvas().setOnKeyPressed(e -> {handleKeyPressed(e);});
        game.getCanvas().setOnKeyReleased(e -> {handleKeyReleased(e);});

        gameLoop();
    }

    // Events
    public void handleKeyPressed(KeyEvent e) {
        if (player.isPaused() && e.getCode() != KeyCode.ESCAPE) {
            return;
        }
        if (player.isDead()) {
            player.reviveEntity();
            ice.reset();
        }
        switch (e.getCode()) {
            case DOWN:
                player.moveDown();
                break;
            case UP:
                player.moveUp();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case S:
                player.moveDown();
                break;
            case W:
                player.moveUp();
                break;
            case A:
                player.moveLeft();
                break;
            case D:
                player.moveRight();
                break;
            case SPACE:
                player.setBreathingIce(true);
                break;
            case ESCAPE:
                player.setPaused(!player.isPaused());
                break;
            default:
                break;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            player.setBreathingIce(false);
        }
    }

    private void gameLoop() {
        final long startNanoTime = System.nanoTime();
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if (!player.isPaused()) {
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                    updateWorld(t, ice, player, bottle);
                }
                game.render();
            }
        }.start();
    }

    public static void updateWorld(double deltaTime, IceField ice, Player player, Bottle bottle) {

        ice.update(deltaTime, player);
        if (player.getX() == bottle.getX() && player.getY() == bottle.getY()) {
            player.setScore(player.getScore() + bottle.getScore());
            bottle.relocateEntity((int)(Math.random() * ice.getSize()), (int)(Math.random() * ice.getSize()));
        }
        if (ice.getTile(player.getY(), player.getX()).getTemperature() == 1) {
            player.killEntity();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
