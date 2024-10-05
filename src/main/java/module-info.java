module org.example.hexgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.hexgame to javafx.fxml;
    exports org.example.hexgame;
}