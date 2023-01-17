package com.example.wordsearchgame1.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class lista {
    private  String type;
    private ArrayList<Element> lista = new ArrayList<>();

    public lista(String soup){
        this.type = soup;
        readDataBase();

    }

    public void readDataBase(){ //Lector de base de datos
        String fileName = "";

        switch (type){
            case "frutas":
                fileName = "C:\\Users\\DELL\\IdeaProjects\\SopaDeLetras\\src\\main\\java\\com\\example\\sopadeletras\\basesDeDatos\\frutas.txt";
                break;
            case "paises"  :
                fileName = "C:\\Users\\DELL\\IdeaProjects\\SopaDeLetras\\src\\main\\java\\com\\example\\sopadeletras\\basesDeDatos\\paises.txt";
                break;
            case "animales":
                fileName = "C:\\Users\\DELL\\IdeaProjects\\SopaDeLetras\\src\\main\\java\\com\\example\\sopadeletras\\basesDeDatos\\animales.txt";
                break;
            default:
                fileName = "";
        }

        try {
            File lista = new File(fileName);
            Scanner lector = new Scanner(lista);
            if(!fileName.equals("")){
                this.lista = new ArrayList<Element>();
                while(lector.hasNextLine()){
                    Element e=new Element(lector.nextLine());
                    if(e.name.length()<9){
                        this.lista.add(e);
                    }
                }
                lector.close();
                ArrayList<Element> listaTemporal = new ArrayList<Element>();

                for(int i = 0; i < 10; i++){
                    Element temp = this.lista.get(generateRandomNumbers(0, this.lista.size()-1));
                    listaTemporal.add(temp);
                    this.lista.remove(this.lista.indexOf(temp));
                }
                this.lista = listaTemporal;
            }
        }catch (FileNotFoundException e){
        }
    }

    public static int generateRandomNumbers(int min, int max){
        Random random = new Random();
        return  random.nextInt(max)+min;
    }

    public ArrayList<Element> getLista(){
        return this.lista;
    }

    public String getType(){
        return  this.type;
    }


}
