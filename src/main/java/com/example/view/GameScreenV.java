package com.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GameScreenV 
{

    @FXML
    private Polygon mainPentagon;

    @FXML
    private Pane rootPane, catanBoardPane;

    @FXML
    private Label player1Display, player2Display, player3Display, player4Display;

    // Static holder for names before screen loads
    private static String[] playerNames;

    // Called from Setup screen before switching
    public static void setNextPlayerNames(String[] names) {
        playerNames = names;
    }

    @FXML
    public void initialize() 
    {
        mainPentagon.setTranslateX(-rootPane.getWidth()/2);
        mainPentagon.setTranslateY(-rootPane.getHeight()/2);

        System.out.println("GameScreenV initialized"); // Debug
        if (playerNames != null) {
            player1Display.setText(playerNames[0]);
            player2Display.setText(playerNames[1]);
            player3Display.setText(playerNames[2]);
            player4Display.setText(playerNames[3] != null ? playerNames[3] : "N/A");
            System.out.println("Player names set.");
            playerNames = null;
        } else {
            System.out.println("playerNames is null");
        }
        createCatanBoard(rootPane); // assuming rootPane is your Pane for the game board

    }

    private void createCatanBoard(Pane boardPane) 
    {
        double hexWidth = 120;   // larger width
        double hexHeight = 120;  // larger height
        double gap = 20;
        double rightShift = 120; // move everything right
        double totalHeight = 864; // screen height

        // Catan layout (rows 3-4-5-4-3)
        int[] rowHexCounts = {3, 4, 5, 4, 3};

        // Find index of the "center row" (3rd row)
        int centerRowIndex = 2;
        double centerRowY = totalHeight / 2;

        // Calculate vertical offset so the middle of the 3rd row is centered
        double middleHexY = centerRowIndex * (0.75 * hexHeight + gap) + (hexHeight / 2);
        double verticalOffset = centerRowY - middleHexY;

        for (int row = 0; row < rowHexCounts.length; row++) {
            int count = rowHexCounts[row];
            double y = verticalOffset + row * (0.75 * hexHeight + gap);

            for (int col = 0; col < count; col++) {
                double x = col * (hexWidth + gap);

                // Offset for odd rows
                if (row % 2 == 1) {
                    x += (hexWidth + gap) / 2;
                }

                // Additional horizontal shift for top and bottom rows
                if (row == 0 || row == rowHexCounts.length - 1) {
                    x += hexWidth + gap;  // move over by one tile
                }

                x += rightShift; // shift everything 200px to the right

                Polygon hex = createHex(hexWidth, hexHeight);
                hex.setLayoutX(x);
                hex.setLayoutY(y);

                hex.setFill(Color.SANDYBROWN);
                hex.setStroke(Color.BLACK);

                boardPane.getChildren().add(hex);
            }
        }
    }

    // Flat-top hex helper
    private Polygon createHex(double width, double height) {
        Polygon hex = new Polygon();
        double w = width;
        double h = height;
        hex.getPoints().addAll(
            w/2, 0.0,
            w, h/4,
            w, 3*h/4,
            w/2, h,
            0.0, 3*h/4,
            0.0, h/4
        );
        return hex;
    }




}
