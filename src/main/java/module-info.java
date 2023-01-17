module com.example.wordsearchgame1 {

    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordsearchgame1 to javafx.fxml;
    exports com.example.wordsearchgame1;
    exports com.example.wordsearchgame1.controller;
    opens com.example.wordsearchgame1.controller to javafx.fxml;
}