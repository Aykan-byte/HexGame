package org.example.hexgame;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameController {

    //Terms
    @FXML
    Button btnClose;
    @FXML
    Button btn5x5;
    @FXML
    Button btn11x11;
    @FXML
    Button btn17x17;
    @FXML
    TextField txtPlayer1;
    @FXML
    TextField txtPlayer2;
    @FXML
    Label lblWarning;
    @FXML
    Label lblPlayer1;
    @FXML
    Label lblPlayer2;
    @FXML
    Label countRed;
    @FXML
    Label countBlue;
    @FXML
    Label lblWinner;
    @FXML
    private Pane gamePane;
    @FXML
    Label lblMove;

    private static int SIZE;
    private final int[][] board = new int[SIZE][SIZE];
    private boolean playerTurn = true;
    private final Map<Polyline, Integer[]> cellCoordinates = new HashMap<>();
    private int redPlayerMoves = 0;
    private int bluePlayerMoves = 0;
    private boolean gameEnded = false;
    private String Playername1;
    private String Playername2;


    public void initialize() {

        initializeCells();
        Animations.animationPolylines(cellCoordinates);
        Animations.rotatePolyline(cellCoordinates);
        lblWinner.setVisible(false);

    }
    //This method adds scaling animation to the buttons btn5x5, btn11x11, and btn17x17.
    @FXML
    private void callAnimation() {
        Animations.addScaleAnimation(btn5x5);
        Animations.addScaleAnimation(btn11x11);
        Animations.addScaleAnimation(btn17x17);
    }
    //This method retrieves player names from the text boxes txtPlayer1 and txtPlayer2 and assigns them to the variables Playername1 and Playername2.
    @FXML
    private void takePlayerName() {
        Playername1 = txtPlayer1.getText();
        Playername2 = txtPlayer2.getText();
    }

    protected void setPlayerNames(String Playername1, String Playername2) {
        this.Playername1 = Playername1;
        this.Playername2 = Playername2;
        setPlayerLabels();
    }

    private void setPlayerLabels() {
        lblPlayer1.setText(Playername1);
        lblPlayer2.setText(Playername2);
    }
    //This method closes the window of the current scene when the btnClose button is clicked.
    @FXML
    private void btnCloseOnAction() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    //This method uses the SceneSwitcher to transition to the given FXML file, passing along the variables Playername1 and Playername2, as well as the event during the transition process.
    private void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        SceneSwitcher.switchToScene(fxmlFile, Playername1, Playername2, event);
    }
    //These methods, when clicked respectively on btn5x5, btn11x11, and btn17x17 buttons, retrieve player names, check if they are valid.
    // If not, they display a warning message; if they are valid, they set the game board size, hide the winner label, and transition to the relevant game board scene.
    @FXML
    private void btn5x5OnAction(ActionEvent event) throws IOException {
        takePlayerName();
        if (Playername1 == null || Playername1.isBlank() || Playername2 == null || Playername2.isBlank()) {
            lblWarning.setText("Please Enter Player Names !!!");
        } else {
            SIZE = 6;
            switchToScene(event, "BoardFive.fxml");
        }
    }

    @FXML
    private void btn11x11OnAction(ActionEvent event) throws IOException {
        takePlayerName();
        if (Playername1 == null || Playername1.isBlank() || Playername2 == null || Playername2.isBlank()) {
            lblWarning.setText("Please Enter Player Names !!!");
        } else {
            SIZE = 12;
            switchToScene(event, "BoardEleven.fxml");
        }
    }

    @FXML
    private void btn17x17OnAction(ActionEvent event) throws IOException {
        takePlayerName();
        if (Playername1 == null || Playername1.isBlank() || Playername2 == null || Playername2.isBlank()) {
            lblWarning.setText("Please Enter Player Names !!!");
        } else {
            SIZE = 18;
            switchToScene(event, "BoardSevenTeen.fxml");
        }
    }

    @FXML
    private void goToBack(ActionEvent event) throws IOException {
        SceneSwitcher.switchToScene("MainMenu.fxml", Playername1, Playername2, event);
    }
    //This method initializes the cells on the game board. It handles the click action for each cell and retrieves the coordinates of the clicked cell.
    @FXML
    private void initializeCells() {
        gamePane.getChildren().forEach(node -> {
            if (node instanceof Polyline cell) {
                Integer[] coordinates = getCoordinatesFromCell(cell);
                if (coordinates != null) {
                    cellCoordinates.put(cell, coordinates);
                    cell.setOnMouseClicked(event -> handleCellClick(coordinates[0], coordinates[1]));
                }
            }
        });
    }
    //This method finds the cell with the given coordinates.
    private Integer[] getCoordinatesFromCell(Polyline cell) {
        String id = cell.getId();
        if (id != null && id.matches("cell(0[1-9]|1[0-7])_(0[1-9]|1[0-7])")) {
            int x = Integer.parseInt(id.split("_")[0].substring(4)) - 1;
            int y = Integer.parseInt(id.split("_")[1]) - 1;
            return new Integer[]{x, y};
        } else {
            return null;
        }
    }
    // Bu metot, verilen koordinatlara sahip hücreyi bulur.
    private Polyline getCellByCoordinates(int x, int y) {
        for (Map.Entry<Polyline, Integer[]> entry : cellCoordinates.entrySet()) {
            Integer[] coordinates = entry.getValue();
            if (coordinates[0] == x && coordinates[1] == y) {
                return entry.getKey();
            }
        }
        return null;
    }
    //This method manages the operations that occur when a cell is clicked.
    private void handleCellClick(int x, int y) {
        if (!gameEnded && board[x][y] == 0) {
            board[x][y] = playerTurn ? 2 : 1;
            Polyline cell = getCellByCoordinates(x, y);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), cell);
            scaleTransition.setToX(1.2);
            scaleTransition.setToY(1.2);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(2);

            FillTransition fillTransition = new FillTransition(Duration.millis(300), cell);
            fillTransition.setFromValue(playerTurn ? Color.RED : Color.BLUE);
            fillTransition.setToValue(playerTurn ? Color.RED : Color.BLUE);

            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fillTransition);
            parallelTransition.play();

            if (playerTurn) {
                redPlayerMoves++;
                countRed.setText(String.valueOf(redPlayerMoves));
                lblMove.setText("Move turn:"+Playername2);
            }
            else {
                bluePlayerMoves++;
                countBlue.setText(String.valueOf(bluePlayerMoves));
                lblMove.setText("Move turn:"+Playername1);
            }

            if (Algorithm.checkWin(board, playerTurn ? 2 : 1, SIZE)) {
                String winner = playerTurn ? lblPlayer1.getText() : lblPlayer2.getText();
                lblWinner.setText("Game Winner: "+ "\n" + winner + "\n" + "Congratulations!!");
                gamePane.getChildren().remove(lblWinner);
                gamePane.getChildren().add(lblWinner);
                lblWinner.setVisible(true);
                lblMove.setVisible(false);
                gameEnded = true;

                Animations.playFireworksAnimation(gamePane,playerTurn ? Color.RED : Color.BLUE);
            }
            playerTurn = !playerTurn;
        }
    }
}