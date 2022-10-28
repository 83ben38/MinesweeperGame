import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.util.ArrayList;

public class Minesweeper extends GraphicsProgram {
    public  static int SIZE = 3;
    public  static int TILE_SIZE = 15;
    public  static int DIMENSIONS = 5;

    public static boolean playable = true;
    public static boolean first_picked =false;

    public final static ArrayList<Level> levels = new ArrayList<>();
    static{
        levels.add(new Level() {
            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimension1 - dimension2 == 1 || dimension1 - dimension2 == 0 || dimension1 - dimension2 == -1);
            }

            @Override
            public int mineChance() {
                return 9;
            }

            @Override
            public int size() {
                return 3;
            }

            @Override
            public int tileSize() {
                return 25;
            }

            @Override
            public int dimensions() {
                return 5;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[1] == dimensions2[1] && dimensions1[0] == dimensions2[0] && dimensions1[2] == dimensions2[2];
            }
        });
        levels.add(new Level() {
            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimensionNumber%2==0 ? Math.abs(dimension1-dimension2) < 2 : Math.abs(dimension1-dimension2) < 2 && dimension1-dimension2 != 0);
            }

            @Override
            public int mineChance() {
                return 7;
            }

            @Override
            public int size() {
                return 5;
            }

            @Override
            public int tileSize() {
                return 15;
            }

            @Override
            public int dimensions() {
                return 3;
            }
            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return false;
            }
        });
        levels.add(new Level() {
            @Override
            public int mineChance() {
                return 10;
            }

            @Override
            public int size() {
                return 15;
            }

            @Override
            public int tileSize() {
                return 15;
            }

            @Override
            public int dimensions() {
                return 2;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[0]/3 == dimensions2[0]/3 && dimensions1[1]/3 == dimensions2[1]/3;
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                if (dimension1[0] == dimension2[0] || dimension1[1] == dimension2[1]){
                    return true;
                }
                return dimension1[0]/3 == dimension2[0]/3 && dimension1[1]/3 == dimension2[1]/3;
            }
        });
        levels.add(new Level() {
            @Override
            public int mineChance() {
                return 7;
            }

            @Override
            public int size() {
                return 4;
            }

            @Override
            public int tileSize() {
                return 15;
            }

            @Override
            public int dimensions() {
                return 4;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[1] == dimensions2[1] && dimensions1[0] == dimensions2[0];
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                return dimension1[1]+dimension1[2]+dimension1[0]+dimension1[3] == dimension2[1]+dimension2[2]+dimension2[0]+dimension2[3] || dimension1[1]-dimension1[2]-dimension1[0]+dimension1[3] == dimension2[1]-dimension2[2]-dimension2[0]+dimension2[3];
            }
        });
    }
    public final static Level level = levels.get(Dialog.getInteger("What level would you like?"));

    public static Minesweeper minesweeper;
    public static Plane game;

    public static void main(String[] args) {
        new Minesweeper().start();
    }

    @Override
    public void run() {
        SIZE = level.size();
        TILE_SIZE = level.tileSize();
        DIMENSIONS = level.dimensions();
        minesweeper = this;
        game = new Plane(0,new int[0]);
        game.moveSquares(0,0);
        add(game);
        System.out.println(game.getAllSquares().size());
    }
}
