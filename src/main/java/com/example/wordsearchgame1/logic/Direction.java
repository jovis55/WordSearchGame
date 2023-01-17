package com.example.wordsearchgame1.logic;
/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class Direction {
    public int Direction;
    public int x;
    public int y;
    public Direction(int Dir){
        Direction=Dir;
        x=dirToX();
        y=dirToY();
        y=y*-1;
    }
    private int dirToY(){
        int y=0;
        switch (this.Direction){
            case 0 -> {
                y=0;
            }

            case 1 -> {
                y=-1;
            }

            case 2 -> {
                y=-1;
            }

            case 3 -> {
                y=-1;
            }

            case 4 -> {
                y=0;
            }

            case 5 -> {
                y=1;
            }

            case 6 -> {
                y=1;
            }

            case 7 -> {
                y=1;
            }
        }
        return y;
    }

    private int dirToX(){
        int x=0;
        switch (this.Direction){
            case 0 -> {
                x=1;
            }

            case 1 -> {
                x=1;
            }

            case 2 -> {
                x=0;
            }

            case 3 -> {
                x=-1;
            }

            case 4 -> {
                x=-1;
            }

            case 5 -> {
                x=-1;
            }

            case 6 -> {
                x=0;
            }

            case 7 -> {
                x=1;
            }
        }
        return x;
    }
}
