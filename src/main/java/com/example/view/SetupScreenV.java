package com.example.view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;


public class SetupScreenV {

    @FXML
    private Canvas hexCanvas;

    @FXML
    private Label threeOption, fourOption;

    @FXML
    private VBox threeBox, fourBox;

    @FXML
    private HBox pFourBox;

    private double r = 75; // hex radius

    private static class Hex {
        double[] xPoints;
        double[] yPoints;
        double distanceToCenter;

        Hex(double x, double y, double r, double centerX, double centerY) {

            xPoints = new double[6];
            yPoints = new double[6];
            for (int i = 0; i < 6; i++) {
                double angleRad = Math.toRadians(60 * i);
                xPoints[i] = x + r * Math.cos(angleRad);
                yPoints[i] = y + r * Math.sin(angleRad);
            }
            double dx = x - centerX;
            double dy = y - centerY;
            distanceToCenter = Math.sqrt(dx * dx + dy * dy);
        }
    }
    private final List<Hex> hexes = new ArrayList<>();
    private WritableImage staticBackground;
        
    @FXML
    public void initialize() {
        createHexGrid();
        drawStaticBackground();
        startHexSpiralAnimation();

        // Set up click handlers for each option
        threeBox.setOnMouseClicked(event -> selectPlayers(3));
        fourBox.setOnMouseClicked(event -> selectPlayers(4));

        // Add Gradual Scaling to all Menu Options
            // Three
        applyHoverScaling(threeOption);
            // Four
        applyHoverScaling(fourOption);
    }

    // Layout of hex grid for the background
    private void createHexGrid() {
        double width = hexCanvas.getWidth();
        double height = hexCanvas.getHeight();

        double hexWidth = Math.sqrt(3) * r;
        double hexHeight = 2 * r;
        double horizSpacing = hexWidth;
        double vertSpacing = hexHeight;

        int cols = (int) Math.ceil(width / horizSpacing) + 2;
        int rows = (int) Math.ceil(height / vertSpacing) + 2;

        double centerX = width / 2;
        double centerY = height / 2;

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                double x = col * horizSpacing;
                double y = row * vertSpacing;
                if (col % 2 == 1) y += vertSpacing / 2;

                hexes.add(new Hex(x, y, r, centerX, centerY));
            }
        }
    }

    // Draw the laid out hexes
    private void drawStaticBackground() {
        double width = hexCanvas.getWidth();
        double height = hexCanvas.getHeight();

        // Draw once to a WritableImage
        staticBackground = new WritableImage((int) width, (int) height);
        GraphicsContext tempGC = new Canvas(width, height).getGraphicsContext2D();

        // Fill background black
        tempGC.setFill(Color.BLACK);
        tempGC.fillRect(0, 0, width, height);

        // Draw hex outlines (static)
        tempGC.setStroke(Color.web("#444"));
        tempGC.setLineWidth(3);
        for (Hex hex : hexes) {
            tempGC.strokePolygon(hex.xPoints, hex.yPoints, 6);
        }

        tempGC.getCanvas().snapshot(null, staticBackground);
    }

    // Gradual colour changes of all hexes
    private void startHexSpiralAnimation() {
        GraphicsContext gc = hexCanvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t = (now - startNanoTime) / 3e9; // seconds

                // Draw static background
                gc.drawImage(staticBackground, 0, 0);

                // Animate hex fill
                for (Hex hex : hexes) {
                    double opacity = 0.6 + 0.4 * Math.sin(hex.distanceToCenter / 80 - t * 0.5);
                    opacity = Math.max(0, Math.min(1, opacity));
                    gc.setFill(Color.rgb(85, 85, 85, opacity));
                    gc.fillPolygon(hex.xPoints, hex.yPoints, 6);
                }
            }
        }.start();
    }

    // Add Gradual Scaling to all Menu Options
    private void applyHoverScaling(javafx.scene.Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        node.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(0.95);
            st.setToY(0.95);
            st.play();
        });

        node.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(node.isHover() ? 1.1 : 1.0);
            st.setToY(node.isHover() ? 1.1 : 1.0);
            st.play();
        });
    }

    // Select player number
    private void selectPlayers(int number) {
        if (number == 3) {
            pFourBox.setVisible(false);
            pFourBox.setManaged(false); // removes it from layout flow
        } else if (number == 4) {
            pFourBox.setVisible(true);
            pFourBox.setManaged(true);
        }
    }

}
