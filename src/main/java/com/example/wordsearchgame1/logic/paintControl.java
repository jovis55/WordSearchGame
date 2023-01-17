/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.wordsearchgame1.logic;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class paintControl extends Thread {
    Label label;
    Color fill;
    Color border;
    public paintControl (Label label) throws IOException{
        if (label == null)throw new IOException();
        this.label=label;
    }
    
    @Override
    public void run()  {
        label.setBorder(new Border(new BorderStroke(border, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT)));
        label.setBackground(new Background(new BackgroundFill(fill, new CornerRadii(0), new Insets(0))));
        label.setTextFill(border);
        try {
            this.join();
        } catch (InterruptedException ex) {
        }
        //System.out.println("Hilos activos: "+Thread.activeCount()+"\n"+this.fill.toString()+" en  "+this.getName()+"\n");
    }
    
    public void paintGreen(){
        this.fill=Color.LIGHTGREEN;
        this.border=Color.DARKGREEN;
        run();
    }
    
    public void paintMaroon(){
        this.fill=Color.WHEAT;
        this.border=Color.MAROON;
        run();
    }
    
    public void paintWhite(){
        this.fill=Color.WHITE;
        this.border=Color.BLACK;
        run();
    }
    
    public void paintBlue(){
        this.fill=Color.LIGHTCYAN;
        this.border=Color.SKYBLUE;
        run();
    }
}
