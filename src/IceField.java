import java.util.ArrayList;

public class IceField {
    private int size;
    private Tile[][] tiles;
    private double waterTimer = 0;
    private double fuelTimer = 0;
    private double globalWarmingTimer = 0;
    private double time = 0;
    private final double WATER_SPREAD_RATE = 0.6;
    private final double FUEL_FREEZE_RATE = 0.1;
    private final double GLOBAL_WARMING_RATE = 10;

    public IceField() {
        this(50);
    }

    public IceField(int size) {
        this.size = size;
        tiles = new Tile[size][size];

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    /* methods */
    private void primeWater() { //  prime current water
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if ((tiles[i][j].getTemperature() == 1) && (!tiles[i][j].isPrimed())){
                    tiles[i][j].setPrimed(true);
                }
            }
        }
    }

    private void waterInfluence() { // increase temp of anything touching primed water
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].isPrimed()) {
                    Tile[] neighbours = new Tile[4];
                    int c = 0;

                    if (i + 1 < tiles.length)
                        neighbours[c++] = tiles[i + 1][j]; // south
                    if (j + 1 < tiles.length)
                        neighbours[c++] = tiles[i][j + 1]; // east
                    if (i - 1 >= 0)
                        neighbours[c++] = tiles[i - 1][j]; // north
                    if (j - 1 >= 0)
                        neighbours[c++] = tiles[i][j - 1]; // west

                    for(int k = 0; k < c; k++) {
                        if ((int)(Math.random() * 10) >= 6)
                            neighbours[k].increaseTemperature();
                    }
                }
            }
        }
    }

    public void update(double time, Player player) {
        Tile playerTile = getTile(player.y, player.x);

        double deltaTime = (time - this.time);
        this.time = time;

        waterTimer += deltaTime;
        fuelTimer += deltaTime;
        globalWarmingTimer += deltaTime;

        if (waterTimer >= WATER_SPREAD_RATE) {
            primeWater();
            waterInfluence();
            if ((int) (Math.random() * 10) >= 8)
                playerTile.increaseTemperature();

            waterTimer = 0;
        }

        if (fuelTimer >= FUEL_FREEZE_RATE) {
            if (player.isBreathingIce() && !player.isDead()) {
                ArrayList<Tile> freezeRange = new ArrayList<Tile>();

                if (player.isLeft()) {
                    for(int i = 0; i < player.getX(); i++) {
                        freezeRange.add(getTile(player.getY(), i));
                    }
                } else if (player.isRight()) {
                    for(int i = player.maxBound - 1; i > player.getX(); i--) {
                        freezeRange.add(getTile(player.getY(), i));
                    }
                } else if (player.isUp()) {
                    for(int i = 0; i < player.getY(); i++) {
                        freezeRange.add(getTile(i, player.getX()));
                    }
                } else if (player.isDown()) {
                    for(int i = player.maxBound - 1; i > player.getY(); i--) {
                        freezeRange.add(getTile(i, player.getX()));
                    }
                }

                int maxRange = 5;
                if (freezeRange.size() <= 5) {
                    maxRange = freezeRange.size();
                }
                for(int i = freezeRange.size() - 1; i >= freezeRange.size() - maxRange; i--) {
                    freezeRange.get(i).decreaseTemperature();
                    freezeRange.get(i).setPrimed(false);
                }
            }
            fuelTimer = 0;
        }

        if (globalWarmingTimer >= GLOBAL_WARMING_RATE) { // 10s elapsed
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if ((int) (Math.random() * 10) >= 8)
                        tiles[i][j].increaseTemperature();
                }
            }

            globalWarmingTimer = 0;
        }
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void reset() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].setTemperature(-5);
                tiles[i][j].setPrimed(false);
            }
        }
    }

    @Override
    public String toString() {
        String content = "";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                content += String.format("%4d", tiles[i][j].getTemperature());
            }
            content += "\n";
        }
        return content;
    }
}
