import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Plane extends GCompound {
    boolean mine;
    boolean flagged;
    boolean revealed;
    int dimensionAmount;
    int[] dimensions;
    Plane[] subPlanes;
    GObject[] objects;
    public Plane(int dimensionAmount, int[] dimensions){
        this.dimensionAmount = dimensionAmount;
        this.dimensions = dimensions;
        int dimensionsRemaining = Minesweeper.DIMENSIONS-dimensionAmount;
        if (dimensionsRemaining == 0){
            objects = new GObject[3];
            objects[0] = new GRect(Minesweeper.TILE_SIZE,Minesweeper.TILE_SIZE);
            ((GRect)objects[0]).setFillColor(Color.gray);
            ((GRect)objects[0]).setFilled(true);
            Minesweeper.minesweeper.add(objects[0],getX(),getY());
            objects[1] = new GRect(Minesweeper.TILE_SIZE,Minesweeper.TILE_SIZE);
            ((GRect)objects[1]).setFillColor(new Color(0,255,100,100));
            ((GRect)objects[1]).setFilled(true);
            add(objects[1]);
            objects[1].setVisible(false);
            Plane t = this;
            objects[0].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!Minesweeper.playable){
                        return;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (!Minesweeper.first_picked){
                            for (Plane p: Minesweeper.game.getAllSquares()) {
                                if (Minesweeper.level.startingCriteria(dimensions,p.dimensions)){
                                    p.mine = false;
                                    p.reveal();
                                }
                            }
                            mine = false;
                            reveal();
                            Minesweeper.first_picked = true;
                        }
                        else if (mine){
                            Minesweeper.playable = false;
                            for (Plane p: Minesweeper.game.getAllSquares()) {
                                p.reveal();
                            }
                        }
                        else{
                            reveal();
                        }
                    }
                    else{
                        flagged = !flagged;
                        ((GRect)objects[0]).setFillColor(flagged ? Color.red : Color.gray);
                        Minesweeper.game.resetTiles();
                        boolean done = true;
                        for (Plane p: Minesweeper.game.getAllSquares()) {
                            if (Minesweeper.game.getTotalMines(p) != 0){
                                done = false;
                                break;
                            }
                        }
                        if (done){
                            Dialog.showMessage("you win!");
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    for (Plane p: Minesweeper.game.getAllNextTo(t)) {
                        p.objects[1].setVisible(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    for (Plane p: Minesweeper.game.getAllNextTo(t)) {
                        p.objects[1].setVisible(false);
                    }
                }
            });
            objects[2] = new GLabel("");
            mine = randomInt(0,Minesweeper.level.mineChance()) == 0;
            flagged = false;
        }
        else {
            int moveAmount;
            subPlanes = new Plane[Minesweeper.SIZE];
            int[] newDimensions = new int[dimensions.length + 1];
            System.arraycopy(dimensions, 0, newDimensions, 0, dimensions.length);
            if (dimensionsRemaining % 2 == 0) {
                moveAmount = moveAmount(dimensionsRemaining / 2);
                for (int i = 0; i < Minesweeper.SIZE; i++) {
                    newDimensions[newDimensions.length - 1] = i;
                    subPlanes[i] = new Plane(dimensionAmount + 1, newDimensions.clone());
                    add(subPlanes[i], 0, moveAmount * Minesweeper.TILE_SIZE * i);
                }
            } else {
                moveAmount = moveAmount((dimensionsRemaining + 1) / 2);
                for (int i = 0; i < Minesweeper.SIZE; i++) {
                    newDimensions[newDimensions.length - 1] = i;
                    subPlanes[i] = new Plane(dimensionAmount + 1, newDimensions.clone());
                    add(subPlanes[i], moveAmount * Minesweeper.TILE_SIZE * i, 0);
                }
            }
        }
    }
    public static int moveAmount(int moveAmount){
        return moveAmount == 0 ? 0 : ((moveAmount(moveAmount-1)*Minesweeper.SIZE) + 1);
    }
    public ArrayList<Plane> getAllNextTo(Plane plane){
        ArrayList<Plane> returner = new ArrayList<>();
        if (Minesweeper.DIMENSIONS-dimensionAmount == 0){
            if (Minesweeper.level.isNextTo(dimensions, plane.dimensions)) {
                returner.add(this);
            }
            return returner;
        }
        for (int i = 0; i < Minesweeper.SIZE; i++) {
            if (Minesweeper.level.isNextTo(i,plane.dimensions[dimensionAmount],dimensionAmount)){
                returner.addAll(subPlanes[i].getAllNextTo(plane));
            }
        }
        return returner;
    }
    public ArrayList<Plane> getAllSquares(){
        ArrayList<Plane> planes = new ArrayList<>();
        if (dimensionAmount == Minesweeper.DIMENSIONS){
            planes.add(this);
            return planes;
        }
        for (Plane plane: subPlanes) {
            planes.addAll(plane.getAllSquares());
        }
        return planes;
    }

    public int getTotalMines(Plane plane){
        int total = 0;
        for (Plane p: getAllNextTo(plane)) {
            total += p.mine ? 1 : 0;
            total -= p.flagged ? 1 : 0;
        }
        return total;
    }

    public void resetTiles(){
        if (dimensionAmount == Minesweeper.DIMENSIONS){
            if (revealed){
                ((GLabel)objects[2]).setLabel(Minesweeper.game.getTotalMines(this) + "");
            }
        }
        else{
            for (Plane p: subPlanes) {
                p.resetTiles();
            }
        }
    }
    public void moveSquares(double x, double y){
        if (Minesweeper.DIMENSIONS == dimensionAmount){
            objects[0].setLocation(x+getX(),y+getY());
        }
        else{
            for (Plane p: subPlanes) {
                p.moveSquares(x+getX(),y+getY());
            }
        }
    }
    public static int randomInt(int from, int to){
        return (int) (Math.floor((Math.random()*(to-from+1))+from));
    }
    public void reveal(){
        revealed = true;
        ((GRect)objects[0]).setFillColor(flagged ? (mine ? Color.green : Color.blue) : (mine ? Color.yellow : Color.white));
        if (!mine) {
            ((GLabel)objects[2]).setLabel(Minesweeper.game.getTotalMines(this) + "");
            add(objects[2], 0, getHeight() / 2);
        }
        Minesweeper.game.resetTiles();
    }
}
