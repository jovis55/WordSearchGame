package com.example.wordsearchgame1.controller;



import com.example.wordsearchgame1.MainGame;
import com.example.wordsearchgame1.logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class SoupsController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button backSoup; //Grilla
    @FXML
    private ImageView imgFondo; //Grilla
    @FXML
    private Pane pane1;
    @FXML
    private GridPane gridSoup; //Grilla
    @FXML
    private Label typeSoup, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9; //Labels
    @FXML
    private CheckBox ClickMode, DragMode; //Checkbox para modo de juego
    private lista lista; //Administrador y constructor de lista de palabras
    private label labels = new label(); //Administrador y constructor de tablero
    private Direction direction; //Objeto auxiliar de direccion
    private boolean dragModeFlag = false; //Bandera de modo de juego
    private boolean clickMode_FirstClick = true; //Bandera auxiliar
    private boolean ended = false; //Bandera de finalizacion
    private int initial_X, initial_Y, current_X, current_Y; //Puntos vectoriales
    private int gridCellWidth, gridCellHeight; //Configuracion de casillas
    private int draggedVector_AngleSnap;//Angulo ajustado
    private String selection = ""; //Seleccion de palabras
    private ArrayList<Element>[][] logicGrid; //Grilla lógica
    private ArrayList<Element> elements = new ArrayList(); //Elementos en la sopa
    private final ArrayList<Label> titles = new ArrayList();
    private paintControl paintControl; //Control de pintado en Hilos

    public void setNames(String type) { //Seteo de Elementos en estructuras y pantalla
        this.lista = new lista(type); //Nueva lista de palabras
        this.elements = lista.getLista(); //Recuperacion de la lista
        typeSoup.setText(this.lista.getType()); //Identificador de tema de juego en pantalla
        if(this.lista.getType().equals("frutas")){
            Image img = new Image("C:\\Users\\DELL\\IdeaProjects\\WordSearchGame1\\resource\\img\\sopaFrut.png");
            imgFondo.setImage(img);
            pane1.setStyle("-fx-background-color: #F88E8E;");
            backSoup.setStyle("-fx-background-color: #28FF59;");

        }
        if(this.lista.getType().equals("animales")){
            Image img = new Image("C:\\Users\\DELL\\IdeaProjects\\WordSearchGame1\\resource\\img\\animales.png");
            imgFondo.setImage(img);
            pane1.setStyle("-fx-background-color: #37B1BC;");
            backSoup.setStyle("-fx-background-color: #FF4B4B;");
        }
        if(this.lista.getType().equals("paises")){
            Image img = new Image("C:\\Users\\DELL\\IdeaProjects\\WordSearchGame1\\resource\\img\\ciudades.png");
            imgFondo.setImage(img);
            pane1.setStyle("-fx-background-color: #BB515F;");
            backSoup.setStyle("-fx-background-color: #234AF1;");
        }
        //Asignacion en estructura
        titles.add(p0);
        titles.add(p1);
        titles.add(p2);
        titles.add(p3);
        titles.add(p4);
        titles.add(p5);
        titles.add(p6);
        titles.add(p7);
        titles.add(p8);
        titles.add(p9);
        for (int i = 0; i < elements.size(); i++) {
            titles.get(i).setText(elements.get(i).name); //Configuracion de texto
            titles.get(i).setAlignment(Pos.CENTER);
        }
        labels.setLista(elements);//Seteo de la lista en el gestor de tablero
        setClickMode();
    }

    public void showThemes(ActionEvent event) throws IOException { //Seteo de la app
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("soupType.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setGrid(String type) { //Seteo del tablero
        this.labels = new label(); //Nuevo gestor de tablero
        setNames(type); //Llamado al seteo de elementos
        labels.crear_Tablero(); //Creacion de tablero
        Label label = new Label();
        logicGrid = labels.getLogicTab(); //Recuperacion de estructura 3D inicializada
        String[] sizes = gridSoup.getId().split(",");
        for (int row = 0; row < 10; row++) { //Configuracion y adicion de labels
            for (int col = 0; col < 10; col++) {
                label = this.labels.getLabel(row, col);
                label.setMaxSize(Double.parseDouble(sizes[1]) / 10, Double.parseDouble(sizes[0]) / 10);
                label.setAlignment(Pos.CENTER);
                label.setId(row + "," + col);
                label.setFont(new Font("Arial", 20));
                gridSoup.add(label, col, row);
                gridSoup.setGridLinesVisible(true);
            }
        }
        gridCellWidth = (int) label.getMaxWidth(); //Recuperacion de propiedades
        gridCellHeight = (int) label.getMaxHeight(); //Recuperacion de propiedades
        gridSoup.setGridLinesVisible(false);
        this.refreshGrid(); //Refresco visual
        this.refreshElements(); //Refresco visual
    }

    private void paintGreen(Label label) { //Coloreado de label
        try {
            paintControl = new paintControl(label); //Nueva instancia de objeto con herencia de Thread
            paintControl.paintGreen(); //Inicio de hilo
        } catch (IOException ex) {
            System.out.println("Label no encontrado");
        }
    }

    private void paintMaroon(Label label) { //Coloreado de label
        try {
            paintControl = new paintControl(label); //Nueva instancia de objeto con herencia de Thread
            paintControl.paintMaroon(); //Inicio de hilo
        } catch (IOException ex) {
            System.out.println("Label no encontrado");
        }
    }

    private void paintWhite(Label label) { //Coloreado de label
        try {
            paintControl = new paintControl(label); //Nueva instancia de objeto con herencia de Thread
            paintControl.paintWhite(); //Inicio de hilo
        } catch (IOException ex) {
            System.out.println("Label no encontrado");
        }
    }

    private void paintBlue(Label label) { //Coloreado de label
        try {
            paintControl = new paintControl(label); //Nueva instancia de objeto con herencia de Thread
            paintControl.paintBlue(); //Inicio de hilo
        } catch (IOException ex) {
            System.out.println("Label no encontrado");
        }
    }

    @FXML
    private void grid_OnPressed(MouseEvent event) throws IOException { //Evento de mouse presionado

        if (this.dragModeFlag && !ended) { //Validacion
            refreshGrid(); //Refresco visual
            initial_X = (Math.floorDiv((int) event.getX(), gridCellWidth)); //Recuperacion de coordenadas X
            initial_Y = (Math.floorDiv((int) event.getY(), gridCellHeight)); //Recuperacion de coordenadas Y
            paintMaroon(getCell(initial_Y, initial_X));
        }

    }

    @FXML
    private void grid_OnClicked(MouseEvent event) throws IOException { //Evento de mouse clickeado

        if (!this.dragModeFlag && !ended) { //Validacion
            if (this.clickMode_FirstClick) { //Estado 1
                refreshGrid(); //Refresco visual
                initial_X = (Math.floorDiv((int) event.getX(), gridCellWidth)); //Recuperacion de coordenadas X
                initial_Y = (Math.floorDiv((int) event.getY(), gridCellHeight)); //Recuperacion de coordenadas Y
                paintMaroon(getCell(initial_Y, initial_X));
            } else { //Estado 2
                //Verificacion de seleccion
                verifySelection();
                verifyScore();
                refreshGrid();
                refreshElements();
                selection = "";
            }
            this.clickMode_FirstClick = !this.clickMode_FirstClick; //Conmutacion de estado
        }

    }

    @FXML
    private void grid_OnReleased(MouseEvent event) throws IOException { //Evento de mouse liberado

        if (this.dragModeFlag && !ended) { //Validacion
            //Verificacion de seleccion
            verifySelection();
            verifyScore();
            refreshGrid();
            refreshElements();
            selection = "";
        }

    }

    @FXML
    private void grid_OnDragged(MouseEvent event) throws IOException { //Evento de mouse arrastrado

        if (this.dragModeFlag && !ended) { //Validacion
            current_X = (Math.floorDiv((int) event.getX(), gridCellWidth)); //Recuperacion de coordenadas X
            current_Y = (Math.floorDiv((int) event.getY(), gridCellHeight)); //Recuperacion de coordenadas Y
            
            //Ajuste de rangos
            if (current_Y < 0) {
                current_Y = 1;
            }
            if (current_X < 0) {
                current_X = 1;
            }
            if (current_Y > 9) {
                current_Y = 9;
            }
            if (current_X > 9) {
                current_X = 9;
            }
            getSelection(); //Selector
        }

    }

    @FXML
    private void grid_OnMoved(MouseEvent event) throws IOException { //Evento de mouse movido

        if (!this.dragModeFlag && !ended && !this.clickMode_FirstClick) { //Validacion
            current_X = (Math.floorDiv((int) event.getX(), gridCellWidth)); //Recuperacion de coordenadas X
            current_Y = (Math.floorDiv((int) event.getY(), gridCellHeight)); //Recuperacion de coordenadas Y
            
            //Ajuste de rangos
            if (current_Y < 0) {
                current_Y = 1;
            }
            if (current_X < 0) {
                current_X = 1;
            }
            if (current_Y > 9) {
                current_Y = 9;
            }
            if (current_X > 9) {
                current_X = 9;
            }
            getSelection(); //Selector
        }

    }

    private Label getCell(int row, int col) { //Recuperacion de label de grilla por coordenadas
        Label label = new Label();
        for (Node node : gridSoup.getChildren()) { ///Iterador
            if (node.getId() != null) {
                if (node.getId().equalsIgnoreCase(row + "," + col)) { //Validacion por coordenadas encriptadas en ID
                    label = (Label) node;
                }
            }
        }
        return label; //Retorno del label
    }
    
    private Label getLabel(String text) { //Recuperacion de label de lista por texto
        Label label = new Label();
        for (Label node : titles) { //Iterador
            Label l = (Label) node;
            if (l.getText().equalsIgnoreCase(text)) { //Validacion por texto
                label = l;
            }
        }
        return label; //Retorno del label
    }

    private void refreshGrid() { //Refresco visual
        Boolean flag;
        //Recorrido de estructura 2D
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                flag = false;
                if (!logicGrid[row][col].isEmpty()) { //Validacion en esructura 3D
                    for (Element e : logicGrid[row][col]) {
                        if (e.founded) {
                            flag = true;
                        }
                    }
                }
                if (flag) paintGreen(getCell(row, col)); //Coloreado de casilla en verde 
                else paintWhite(getCell(row, col)); //Coloreado por defecto
            }
        }
    }

    private void refreshElements() { //Refresco visual
        for (Element e : elements) { //Iterador
            Label l = SoupsController.this.getLabel(e.name);
            if (e.founded) paintGreen(l); //Coloreado de casilla en verde
            else paintWhite(l); //Coloreado por defecto
        }
    }

    private void verifySelection() { //Verificacion de la seleccion por pantalla
        Boolean founded = false, marked = false; //Banderas booleanas
        Element addedElement = new Element(); //Elemento auxiliar
        for (Element el : elements) { //Iterador
            if (el.name.equalsIgnoreCase(selection) || el.name.equalsIgnoreCase(reverseSelection())) { //Validacion de elemento encontrado
                //Seteo de banderas
                addedElement.added = true;
                marked = el.founded;
                founded = true;
                el.added = true;
                addedElement = el;
                el.founded = true;

            }
        }
        if (founded) { //Validacion de elemento encontrado
            if (!marked) { //Validacion de elemento encontrado previamente
                addedElement.founded = true;
                //System.out.println("Palabra encontrada: " + addedElement.name.toUpperCase()); //Notificacion por consola
                for (int i = 0; i < selectionLength(); i++) {
                    logicGrid[initial_Y + (i * direction.y)][initial_X + (i * direction.x)].add(addedElement); //Adicion del elemento encontrado a la estructura 3D
                }
            }
        }
    }

    private String reverseSelection() { //Reverso del string de seleccion (selection=noitceles para la validacion)
        String reversed = "";
        char[] selected = selection.toCharArray();
        for (int i = selected.length - 1; i >= 0; i--) { //Ciclo de reverso
            reversed += selected[i];
        }
        return reversed;
    }

    private void verifyScore() { //Validacion del puntaje
        Boolean left = false;
        for (Element e : elements) { //Iterador
            if (!e.founded) { //Validacion de elementos encontrados
                left = true;
            }
        }
        if (!left) { //Validacion de juego finalizado
            //System.out.println("\nJuego terminado"); //Notificacion por consola
            //Recorrido de estructura 3D
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (logicGrid[row][col].isEmpty()) { //Validacion de casilla 'vacia'
                        getCell(row, col).setText(""); //Seteo de casilla en blanco
                    }
                }
            }
            ended = true; //Finalizacion del juego
        }
    }

    private void getSelection() { //Recuperacion de seleccion por pantalla
        vectorSnaper(); //Ajuste del angulo del vector selector
        refreshGrid(); //Refresco visual
        direction = new Direction(draggedVector_AngleSnap); //Instancia de vector selector
        selection = getCellSelection(direction.y, direction.y, selectionLength() - 1, ""); //Selector de casillas del vector
    }

    private String getCellSelection(int y, int x, int length, String text) { //Traverso de una matriz con recursividad
        String aux;
        Label label = getCell(initial_Y + (length * direction.y), initial_X + (length * direction.x)); //Recuperacion de casilla por coordenadas
        paintMaroon(label); //Coloreado marron en hilo
        if (length > 0) aux = getCellSelection(y, x, length - 1, label.getText() + text); //Caso recursivo
        else aux = label.getText() + text; //Caso base
        
        return aux; //Retorno de texto auxiliar
    }

    private void vectorSnaper() { //Ajuste de angulo
        int totX = current_X - initial_X; //Delta de X (diferencia)
        int totY = current_Y - initial_Y; //Delta de Y (diferencia)
        long theta = (long) Math.toDegrees(Math.atan2(totY, totX)); //Calculo del angulo original
        if (theta < 0) {
            theta = theta + 360; //Modulacion del angulo
        }
        draggedVector_AngleSnap = (((int) (theta + 45 / 2) / 45) % 8); //Ajuste modular del ángulo en 8 rectas
    }

    private int selectionLength() { //Recuperacion de la longitud de la seleccion
        int length;
        int iy = initial_Y, ix = initial_X; //Recuperacion de coordenadas iniciales
        int cy = current_Y, cx = current_X; //Recuperacion de coordenadas actuales
        int ly = Math.abs(iy - cy), lx = Math.abs(ix - cx); //Diferencia de coordenadas (delta)
        length = Math.max(ly, lx) + 1; //Obtencion de longitud nominal
        return length; //Retorno de longitud de seleccion
    }

    @FXML
    private void setDragMode() { //Evento de modo de juego de arrastre
        this.ClickMode.setSelected(false);
        this.DragMode.setSelected(true);
        this.dragModeFlag = true;
    }

    @FXML
    private void setClickMode() { //Evento de modo de juego de click
        this.ClickMode.setSelected(true);
        this.DragMode.setSelected(false);
        this.dragModeFlag = false;
    }

    @FXML
    private void elements_OnMoved(MouseEvent event) throws IOException { //Evento de mouse sobre palabra encontrada de la lista
        Label label = (Label) event.getSource();
        Element elemento = getElement(label.getText()); //Recuperacion de elemento por texto
        if (elemento != null) {
            if (elemento.founded) { //Validacion de elemento encontrado
                for (Label l : titles) { //Iterador de elementos
                    if (l.getText().equalsIgnoreCase(label.getText())) { //Validacion por texto
                        paintBlue(l); //Coloreado de azul en hilo
                    }
                }
                for (int i = 0; i < logicGrid.length; i++) { //Recorrido de estructura 3D
                    for (int j = 0; j < logicGrid[i].length; j++) {
                        if (logicGrid[i][j].contains(elemento)) { //Validacion de existencia del elemento en la grilla lógica en (i, j)
                            paintBlue(getCell(i, j)); //Coloreado de label con ID (i, j)
                        }
                    }
                }
            }
        }
    }

    private Element getElement(String name) { //Recuperacion de elemento por atributo
        Element e = null;
        for (Element elemento : elements) { //Iterador
            if (elemento.name.equalsIgnoreCase(name)) { //Validacion por atributo 'name'
                e = elemento;
            }
        }
        return e; //Retorno del elemento
    }

    @FXML
    private void elements_OnExited(MouseEvent event) throws IOException { //Evento de mouse saliendo de una palabra encontrada de la lista
        //Refresco visual
        refreshElements(); 
        refreshGrid();
    }
}
