import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Minesweeper extends GraphicsProgram {
    public  static int SIZE = 3;
    public  static int TILE_SIZE = 15;
    public  static int DIMENSIONS = 5;

    public static boolean playable = true;
    public static boolean first_picked =false;

    public static int difficulty = 0;

    public final static ArrayList<ArrayList<Level>> levels = new ArrayList<>();
    static{
        for (int i = 0; i < 5; i++) {
            levels.add(new ArrayList<>());
        }
        levels.get(0).add(new Level() {
            @Override
            public int mineChance() {
                return 8 - (difficulty);
            }

            @Override
            public int size() {
                return 10 + (difficulty*5);
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
                return false;
            }

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return Math.abs(dimension1-dimension2) < 2;
            }
        });
        levels.get(0).add(new Level() {
            @Override
            public int mineChance() {
                return 12-difficulty;
            }

            @Override
            public int size() {
                return 9 + (3*difficulty);
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
                return isNextTo(dimensions1,dimensions2);
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                if (dimension1[0] == dimension2[0] || dimension1[1] == dimension2[1]){
                    return true;
                }
                return dimension1[0]/3 == dimension2[0]/3 && dimension1[1]/3 == dimension2[1]/3;
            }
        });
        levels.get(0).add(new Level() {
            @Override
            public int mineChance() {
                return 9-difficulty;
            }

            @Override
            public int size() {
                return 13+difficulty;
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
                return false;
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                return (dimension1[0] == dimension2[0] && (dimension1[1]-dimension2[1] == 1 || dimension2[1]-dimension1[1] == 2)) || ((dimension2[1]-dimension1[1] == 1 || dimension1[1]-dimension2[1] == 2) && Math.abs(dimension1[0]-dimension2[0]) == 1);
            }
        });
        levels.get(0).add(new Level() {
            @Override
            public int mineChance() {
                return 9-difficulty;
            }

            @Override
            public int size() {
                return 13+difficulty;
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
                return false;
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                return (Math.abs(dimension1[0]-dimension2[0]) == 1 && Math.abs(dimension1[1]-dimension2[1]) == 2) || (Math.abs(dimension1[0]-dimension2[0]) == 2 && Math.abs(dimension1[1]-dimension2[1]) == 1);
            }
        });
        levels.get(1).add(new Level() {
            @Override
            public int mineChance() {
                return 13-difficulty;
            }

            @Override
            public int size() {
                return 3 + (difficulty*2);
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

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return Math.abs(dimension1-dimension2) < 2;
            }
        });
        levels.get(1).add(new Level() {
            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimensionNumber%2==0 ? Math.abs(dimension1-dimension2) < 2 : Math.abs(dimension1-dimension2) < 2 && dimension1-dimension2 != 0);
            }

            @Override
            public int mineChance() {
                return 9-difficulty;
            }

            @Override
            public int size() {
                return 3+difficulty;
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
        levels.get(1).add(new Level() {
            @Override
            public int mineChance() {
                return 10 - (difficulty);
            }

            @Override
            public int size() {
                return 4 + difficulty;
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

            @Override
            public boolean isNextTo(int[] dimensions1, int[] dimensions2) {
                boolean[] bools = new boolean[3];
                for (int i = 0; i < 3; i++) {
                    if (Math.abs(dimensions1[i]-dimensions2[i]) < 3){
                        bools[Math.abs(dimensions1[i]-dimensions2[i])] = true;
                    }
                }
                return bools[0] && bools[2] & bools[1];
            }
        });
        levels.get(1).add(new Level() {
            @Override
            public int mineChance() {
                return 9 - (difficulty);
            }

            @Override
            public int size() {
                return 7 - difficulty;
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

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return Math.abs(dimension1-dimension2) >= 2;
            }
        });
        levels.get(1).add(new Level() {
            @Override
            public int mineChance() {
                return 12-difficulty;
            }

            @Override
            public int size() {
                return 2 + (difficulty*2);
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
                return isNextTo(dimensions1,dimensions2);
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                int z = 0;
                if (dimension1[0] == dimension2[0]){
                    z++;
                }
                if (dimension1[1] == dimension2[1]){
                    z++;
                }
                if (dimension1[2] == dimension2[2]){
                    z++;
                }
                if (z >= 2){
                    return true;
                }
                return dimension1[0]/2 == dimension2[0]/2 && dimension1[1]/2 == dimension2[1]/2 && dimension2[2]/2 == dimension1[2]/2;
            }
        });
        levels.get(2).add(new Level() {
            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimension1 - dimension2 == 1 || dimension1 - dimension2 == 0 || dimension1 - dimension2 == -1);
            }

            @Override
            public int mineChance() {
                return difficulty == 2 ? 8 : 9;
            }

            @Override
            public int size() {
                return 4+(difficulty==3 ? 1:0);
            }

            @Override
            public int tileSize() {
                return difficulty == 3 ? 15 : 25;
            }

            @Override
            public int dimensions() {
                return 4;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[1] == dimensions2[1] && dimensions1[0] == dimensions2[0] && dimensions1[2] == dimensions2[2];
            }
        });
        levels.get(2).add(new Level() {
            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                return (((Math.abs(dimension1[0]-dimension2[0]) == 1 && Math.abs(dimension1[1]-dimension2[1]) == 2) || (Math.abs(dimension1[0]-dimension2[0]) == 2 && Math.abs(dimension1[1]-dimension2[1]) == 1)) && ((Math.abs(dimension1[2]-dimension2[2]) == 1 && Math.abs(dimension1[3]-dimension2[3]) == 2) || (Math.abs(dimension1[2]-dimension2[2]) == 2 && Math.abs(dimension1[3]-dimension2[3]) == 1))) || (dimension1[0] == dimension2[0] && dimension1[1] == dimension2[1] && Math.abs(dimension1[2]-dimension2[2]) < 2 && Math.abs(dimension1[3]-dimension2[3]) < 2);
            }

            @Override
            public int mineChance() {
                return difficulty == 2 ? 8 : 9;
            }

            @Override
            public int size() {
                return 4+(difficulty==3 ? 1:0);
            }

            @Override
            public int tileSize() {
                return difficulty == 3 ? 12 : 20;
            }

            @Override
            public int dimensions() {
                return 4;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[1] == dimensions2[1] && dimensions1[0] == dimensions2[0] && dimensions1[2] == dimensions2[2];
            }
        });
        levels.get(2).add(new Level() {
            @Override
            public int mineChance() {
                return 11-difficulty;
            }

            @Override
            public int size() {
                return 4+(difficulty/2);
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
                return false;
            }

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return ((dimension1/2)==(dimension2/3) || (dimension2/2) == (dimension1/3)) && Math.abs(dimension1-dimension2) < 2;
            }
        });
        levels.get(2).add(new Level() {
            @Override
            public int mineChance() {
                return 11-difficulty;
            }

            @Override
            public int size() {
                return 4 + difficulty/2;
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
                return false;
            }

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return Math.abs(dimension1-dimension2) < 2;
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                return (((dimension1[0] + dimension1[1])%2 == (dimension2[0] + dimension2[1])%2) == ((dimension1[2] + dimension1[3])%2 == (dimension2[2] + dimension2[3])%2)) || (dimension1[0] == dimension2[0] && dimension1[1] == dimension2[1]);

            }
        });
        levels.get(2).add(new Level() {
            @Override
            public int mineChance() {
                return 12-difficulty;
            }

            @Override
            public int size() {
                return 3 + (difficulty);
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
                return isNextTo(dimensions1,dimensions2);
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                int z = 0;
                if (dimension1[0] == dimension2[0]){
                    z++;
                }
                if (dimension1[1] == dimension2[1]){
                    z++;
                }
                if (dimension1[2] == dimension2[2]){
                    z++;
                }
                if (dimension1[3] == dimension2[3]){
                    z++;
                }
                if (z >= 3){
                    return true;
                }
                return dimension1[0]/2 == dimension2[0]/2 && dimension1[1]/2 == dimension2[1]/2 && dimension2[2]/2 == dimension1[2]/2 && dimension1[3]/2 == dimension2[3]/2;
            }
        });
        levels.get(3).add(new Level() {
            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimension1 - dimension2 == 1 || dimension1 - dimension2 == 0 || dimension1 - dimension2 == -1);
            }

            @Override
            public int mineChance() {
                return difficulty == 2 ? 8 : 9;
            }

            @Override
            public int size() {
                return 3+(difficulty==3 ? 1:0);
            }

            @Override
            public int tileSize() {
                return difficulty == 3 ? 15 : 25;
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
        levels.get(3).add(new Level() {
            @Override
            public int mineChance() {
                return 13 - (difficulty*2);
            }

            @Override
            public int size() {
                return 3;
            }

            @Override
            public int tileSize() {
                return 20;
            }

            @Override
            public int dimensions() {
                return 5;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return dimensions1[1] == dimensions2[1] && dimensions1[0] == dimensions2[0] && dimensions1[2] == dimensions2[2];
            }

            @Override
            public boolean isNextTo(int dimension1, int dimension2, int dimensionNumber) {
                return (dimensionNumber < 2 ? Math.abs(dimension1-dimension2) > 0 : Math.abs(dimension1-dimension2) < 2);
            }
        });
        levels.get(4).add(new Level() {
            @Override
            public int mineChance() {
                return 11-difficulty;
            }

            @Override
            public int size() {
                return 2 + (difficulty == 3 ? 1:0);
            }

            @Override
            public int tileSize() {
                return 10;
            }

            @Override
            public int dimensions() {
                return 6;
            }

            @Override
            public boolean startingCriteria(int[] dimensions1, int[] dimensions2) {
                return isNextTo(dimensions1,dimensions2);
            }

            @Override
            public boolean isNextTo(int[] dimension1, int[] dimension2) {
                int z = 0;
                boolean f = true;
                for (int i = 0; i < 6; i++) {
                    if (dimension1[i] == dimension2[i]){
                        z++;
                    }
                    if (Math.abs(dimension1[i]-dimension2[i]) > 1){
                        f = false;
                        break;
                    }
                }
                return z > 4 && f;
            }
        });
    }
    public static Level level;

    public static Minesweeper minesweeper;
    public static Plane game;

    public static void main(String[] args) {
        new Minesweeper().start();
    }

    @Override
    public void run() {
        removeAll();
        level = null;
        difficulty = 0;
        for (int i = 0; i < levels.size(); i++) {
            for (int j = 0; j < levels.get(i).size(); j++) {
                GRect levelButton = new GRect(50, 30);
                GLabel levelNumber = new GLabel( (i+2) + "-" + j);
                int finalI = i;
                int finalJ = j;
                levelButton.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        level = levels.get(finalI).get(finalJ);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                add(levelButton, 60 * (i+1), 33 * (j+1));
                add(levelNumber, levelButton.getX(), levelButton.getY() + levelNumber.getHeight() / 2);
            }
        }
        while (level == null){
            pause(10);
        }
        removeAll();
        String[] difficulties = new String[]{"Easy","Medium","Hard"};
        for (int i = 0; i < 3; i++) {
            GRect levelButton = new GRect(50, 30);
            GLabel levelNumber = new GLabel(difficulties[i]);
            int finalI = i+1;
            levelButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    difficulty = finalI;
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            add(levelButton,60*i,30);
            add(levelNumber,levelButton.getX(),levelButton.getY() + levelNumber.getHeight()/2);
        }
        while (difficulty == 0){
            pause(10);
        }
        removeAll();
        run2();
        GRect backButton = new GRect(50, 30);
        GLabel backLabel = new GLabel("back");
        Minesweeper that = this;
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(that).start();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(backButton,Plane.moveAmount(DIMENSIONS)*TILE_SIZE,0);
        add(backLabel,backButton.getX(),backButton.getY()+backLabel.getHeight()/2);
    }


    public void run2() {
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
//2-1
//3-3
