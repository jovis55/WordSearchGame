package com.example.wordsearchgame1.logic;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class label{
    private int tamanioGrid = 10;
    private Label matrizLabels [][] = new Label[tamanioGrid][tamanioGrid];
    private ArrayList<Element> arrayElements = new ArrayList<>();
    private ArrayList<Direction> direcciones = new ArrayList<>();
    private final ArrayList<Element>[][] logicGrid = new ArrayList[tamanioGrid][tamanioGrid];
    
    public  void inicializar_Tablero(){ //Inicializacion de las estructuras de datos
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Label label = new Label();
                label.setText("");
                matrizLabels[row][col] = label;
                logicGrid[row][col]=new ArrayList<>();
            }
        }
        for(Element e:this.arrayElements){
            e.added=false;
        }
    }

    public void setLista(ArrayList<Element> labels){ //Seteo de la lista de palabras
       this.arrayElements = labels;
    }
    
    private void llenar_Tablero(){ //Llena los espacios vacios del tablero
        for(int i=0; i<matrizLabels.length; i++){
            for(int c=0; c<matrizLabels[i].length;c++){
                if("".equals(matrizLabels[i][c].getText())){
                    Random rnd = new Random();
                    Label label = new Label();
                    label.setText(Character.toString((char) ('a' + rnd.nextInt(26))));
                    matrizLabels[i][c] = label;
                }
            }
        }
    }

    public boolean check_Lista() {//Revisa que no falten elementos por añadir
        boolean s=false;
        for(Element f: this.arrayElements){ //Iterador
            if(!f.added) s=true;
        }
        return s;
    }

    public Label [][] getMatriz(){ //Retorno de la estructura de datos
        return this.matrizLabels;
    }

    public Label getLabel(int row, int col){
        Label label = this.matrizLabels[row][col] ;
        return label;
    }

    public void crear_Tablero(){
        inicializar_Tablero();
        Element elemento_aleatorio;
        int columnas, filas, count=0;
        while(check_Lista()){ //Entra al ciclo cuando al menos 1 de los elementos de la lista no ha sido añadido
            
            elemento_aleatorio = this.arrayElements.get((int)Math.floor(Math.random()*(this.arrayElements.size())));//Selección de lista aleatorio
            if(!elemento_aleatorio.added){ //Continua si el lista seleccionado no ha sido añadido
                columnas = (int)Math.floor(Math.random()*(this.matrizLabels[0].length)); //Selección de columna aleatoria
                filas = (int)Math.floor(Math.random()*(this.matrizLabels.length)); //Selección de fila aleatoria
                int direction;

                validar_Direcciones(elemento_aleatorio.name, columnas, filas); //Adición de las direcciones válidas

                if(!direcciones.isEmpty()){ //Valida que haya direcciones disponibles
                    direction=(int)Math.floor(Math.random()*(direcciones.size())); //Selección de dirección valida aleatoria
                    añadir_Elemento(elemento_aleatorio.name, columnas, filas, direcciones.get(direction)); //Adición del lista a la sopa de letras
                    elemento_aleatorio.added=true;
                }else count ++;
                if (count >10000) break;//Despues de 10000 intentos fallidos de añadir palabras, se determina que no hay formas posibles de adicionar los elementos faltantes
            }
        }
        if(check_Lista()){ //Si no se pudieron añadir todos los elementos, se reinicia el proceso
            //System.out.println("...Reshuffling...");
            crear_Tablero();
        }
        else llenar_Tablero();
    }

    private void validar_Direcciones(String name, int columnas, int filas) {
        direcciones.clear(); //Resetea el arreglo auxiliar de direcciones
        int i, j;
        boolean valid;
        for(i=0;i<8;i++){//Evalua las 8 direcciones partiendo de la posicione (columnas, filas)
            Direction d=new Direction(i);
            j=0;
            valid=true;
            while(j<name.length() && valid){//Define se puede añadir en la direccion i, al evaluar que no haya colisiones, ni se salga del tablero
                try{
                    if(!this.matrizLabels[filas + (j*d.y)][columnas + (j*d.x)].getText().equals(Character.toString(name.charAt(j)))
                            && !this.matrizLabels[filas + (j*d.y)][columnas + (j*d.x)].getText().equals("")) valid=false;
                } catch(ArrayIndexOutOfBoundsException e){
                    valid=false;
                }
                j++;
            }
            if(valid)direcciones.add(d);//Añadade la direccion si es valida
        }
    }
    private void añadir_Elemento(String word, int col, int row, Direction direction) { //Añade un elemento en la direccion determinada
        matrizLabels[row][col].setText(Character.toString(word.charAt(0)));
        if(word.length()>1){
            añadir_Elemento(word.substring(1), col+(1*direction.x), row+(1*direction.y), direction);//Recorrido recursivo
        }
    }
    
    public ArrayList<Element>[][] getLogicTab(){ //Retorno de la estructura de datos
        return this.logicGrid;
    }
}
