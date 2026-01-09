package com.example.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Group;

public class GameScreenV 
{

    // Class-level variable for Oswald font
    private Font oswaldFont;

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

    // Vertex class
    class Vertex 
    {
        double x, y;        // absolute coordinates
        String id;          // unique identifier
        boolean occupied;
        boolean placed;     // has this vertex already been positioned
        Set<Hex> hexes;     // hexes sharing this vertex

        Vertex(double x, double y, String id) {
            this.x = x;
            this.y = y;
            this.id = id;
            this.occupied = false;
            this.placed = false;   // initialize as not placed
            this.hexes = new HashSet<>();
        }
    }

    // Hex class
    class Hex {
        int id;
        Polygon polygon;
        List<Vertex> vertices;
        int row, col;
        String resource;
        int diceNumber;

        Group numberToken;
        List<Circle> pipCircles; // <-- store pip dots

        Hex(int id, Polygon polygon, int row, int col, String resource, int diceNumber, Group numberToken, List<Circle> pipCircles) {
            this.id = id;
            this.polygon = polygon;
            this.row = row;
            this.col = col;
            this.vertices = new ArrayList<>();
            this.resource = resource;
            this.diceNumber = diceNumber;
            this.numberToken = numberToken;
            this.pipCircles = pipCircles;
        }
    }

    // Store all hexes and vertices
    private List<Hex> allHexes = new ArrayList<>();

    // Precomputed vertices for standard Catan layout (53 vertices)
    private Vertex[] vertices = new Vertex[54];

    // Each row corresponds to a hex ID (0..18)
    // Each element is the index of vertices[] that belongs to this hex
    private static final int[][] VERTICES = 
        {
        {1, 2, 10, 9, 8, 0},       // tile 0
        {3, 4, 12, 11, 10, 2},     // tile 1
        {5, 6, 14, 13, 12, 4},     // tile 2
        {8, 9, 19, 18, 17, 7},     // tile 3
        {10, 11, 21, 20, 19, 9},   // tile 4
        {12, 13, 23, 22, 21, 11},  // tile 5
        {14, 15, 25, 24, 23, 13},  // tile 6
        {17, 18, 29, 28, 27, 16},  // tile 7
        {19, 20, 31, 30, 29, 18},  // tile 8
        {21, 22, 33, 32, 31, 20},  // tile 9
        {23, 24, 35, 34, 33, 22},  // tile 10
        {25, 26, 37, 36, 35, 24},  // tile 11
        {29, 30, 40, 39, 38, 28},  // tile 12
        {31, 32, 42, 41, 40, 30},  // tile 13
        {33, 34, 44, 43, 42, 32},  // tile 14
        {35, 36, 46, 45, 44, 34},  // tile 15
        {40, 41, 49, 48, 47, 39},  // tile 16
        {42, 43, 51, 50, 49, 41},  // tile 17
        {44, 45, 53, 52, 51, 43}   // tile 18
    };

    private void assignResources() 
    {
        // Create a list of all 19 resources with correct counts
        List<String> resources = new ArrayList<>();
        resources.addAll(List.of("sheep","sheep","sheep","sheep"));   // 4
        resources.addAll(List.of("wheat","wheat","wheat","wheat"));   // 4
        resources.addAll(List.of("stone","stone","stone"));           // 3
        resources.addAll(List.of("wood","wood","wood","wood"));       // 4
        resources.addAll(List.of("brick","brick","brick"));           // 3
        resources.add("desert");                                      // 1

        // Shuffle to randomize
        java.util.Collections.shuffle(resources);

        // Assign to hexes in order
        for (int i = 0; i < allHexes.size(); i++) {
            Hex hex = allHexes.get(i);
            hex.resource = resources.get(i);

            // Optional: set fill color based on resource
            Color fillColor;
            switch (hex.resource) {
                case "sheep": fillColor = Color.LIGHTGREEN; break;
                case "wheat": fillColor = Color.YELLOW; break;
                case "stone": fillColor = Color.GREY; break;
                case "wood":  fillColor = Color.DARKGREEN; break;
                case "brick": fillColor = Color.SADDLEBROWN; break;
                case "desert": fillColor = Color.TAN; break;
                default: fillColor = Color.SANDYBROWN; break;
            }
            hex.polygon.setFill(fillColor);
        }

        System.out.println("Resources randomly assigned to all hexes.");
    }

    private int getPipCount(int diceNumber) {
        switch (diceNumber) {
            case 2: return 1;
            case 3: return 2;
            case 4: return 3;
            case 5: return 4;
            case 6: return 5;
            case 8: return 5;
            case 9: return 4;
            case 10: return 3;
            case 11: return 2;
            case 12: return 1;
            default: return 0; // desert or invalid
        }
    }


    private void assignNumberTokens() 
    {
        List<Integer> tokens = List.of(2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12);
        List<Integer> tokenList = new ArrayList<>(tokens);
        java.util.Collections.shuffle(tokenList);

        int tokenIndex = 0;

        for (Hex hex : allHexes) 
        {
            // Remove old number token group
            if (hex.numberToken != null) {
                ((Pane) hex.polygon.getParent()).getChildren().remove(hex.numberToken);
                hex.numberToken = null;
            }

            // Remove old pips
            for (Circle pip : hex.pipCircles) {
                ((Pane) hex.polygon.getParent()).getChildren().remove(pip);
            }
            hex.pipCircles.clear();

            if ("desert".equals(hex.resource)) {
                hex.diceNumber = 0;
                continue;
            }

            hex.diceNumber = tokenList.get(tokenIndex++);
            String text = String.valueOf(hex.diceNumber);

            // --- Determine color ---
            boolean isRedNumber = hex.diceNumber == 6 || hex.diceNumber == 8;
            Color numberColor = isRedNumber ? Color.TOMATO : Color.WHITE;
            Color pipInnerColor = isRedNumber ? Color.TOMATO : Color.WHITE;

            // Text setup
            Font tokenFont = Font.font(oswaldFont.getFamily(), 40);

            // Black outer outline
            Text outlineBlack = new Text(text);
            outlineBlack.setFont(tokenFont);
            outlineBlack.setFill(Color.TRANSPARENT);
            outlineBlack.setStroke(Color.BLACK);
            outlineBlack.setStrokeWidth(6);

            // Main text (white or red for 6/8)
            Text mainText = new Text(text);
            mainText.setFont(tokenFont);
            mainText.setFill(numberColor);
            mainText.setStroke(Color.BLACK);
            mainText.setStrokeWidth(1);

            Group tokenGroup = new Group(outlineBlack, mainText);
            tokenGroup.setRotate(5);
            tokenGroup.setMouseTransparent(true);

            // Force CSS before measuring
            tokenGroup.applyCss();
            Bounds tb = mainText.getBoundsInLocal();

            double centerX = hex.polygon.getLayoutX() + 60;
            double centerY = hex.polygon.getLayoutY() + 50;

            tokenGroup.setLayoutX(centerX - tb.getWidth() / 2);
            tokenGroup.setLayoutY(centerY + tb.getHeight() / 4);

            ((Pane) hex.polygon.getParent()).getChildren().add(tokenGroup);
            hex.numberToken = tokenGroup;

            // --- Add pips below number ---
            int pipCount = getPipCount(hex.diceNumber);
            double pipSpacing = 15;
            double dotRadius = 6;

            for (int i = 0; i < pipCount; i++) {
                // Outer black ring
                Circle outer = new Circle(centerX - (pipCount - 1) * pipSpacing / 2 + i * pipSpacing, centerY + 30, dotRadius, Color.BLACK);
                // Inner colored circle
                Circle inner = new Circle(centerX - (pipCount - 1) * pipSpacing / 2 + i * pipSpacing, centerY + 30, dotRadius - 3, pipInnerColor);

                outer.setMouseTransparent(true);
                inner.setMouseTransparent(true);

                ((Pane) hex.polygon.getParent()).getChildren().addAll(outer, inner);
                hex.pipCircles.add(outer);
                hex.pipCircles.add(inner);
            }
        }

        System.out.println("Number tokens and pips assigned to all non-desert hexes.");
    }

    private void reshuffleBoard() {
        assignResources();
        assignNumberTokens();
        System.out.println("Board reshuffled.");
    }


    private void setupVertices(Pane boardPane) 
    {

        // Step 1: Collect positions of each vertex
        Map<Integer, List<double[]>> positionBuckets = new HashMap<>();

        for (Hex hex : allHexes) {
            ObservableList<Double> pts = hex.polygon.getPoints();
            int[] vertexIds = VERTICES[hex.id];

            for (int i = 0; i < 6; i++) {
                int vertexId = vertexIds[i];
                double localX = pts.get(i * 2);
                double localY = pts.get(i * 2 + 1);

                double x = localX + hex.polygon.getLayoutX();
                double y = localY + hex.polygon.getLayoutY();

                positionBuckets
                    .computeIfAbsent(vertexId, k -> new ArrayList<>())
                    .add(new double[]{x, y});
            }
        }

        // Step 2: Canonicalize positions & push outer/edge vertices
        for (Map.Entry<Integer, List<double[]>> entry : positionBuckets.entrySet()) {

            int vertexId = entry.getKey();
            List<double[]> positions = entry.getValue();

            // Compute average position
            double avgX = 0;
            double avgY = 0;
            for (double[] p : positions) {
                avgX += p[0];
                avgY += p[1];
            }
            avgX /= positions.size();
            avgY /= positions.size();

            // Get or create Vertex
            Vertex v = vertices[vertexId];
            if (v == null) {
                v = new Vertex(avgX, avgY, "v" + vertexId);
                vertices[vertexId] = v;
            }

            // Only push outward if not yet placed
            // Only push outward if this vertex is shared by 1 or 2 hexes
            if (!v.placed && positions.size() < 3) {
                // Compute center of all hexes sharing this vertex
                double centerX = 0, centerY = 0;
                for (Hex h : allHexes) {
                    for (int vId : VERTICES[h.id]) {
                        if (vId == vertexId) {
                            Bounds hb = h.polygon.getBoundsInParent();
                            centerX += hb.getMinX() + hb.getWidth() / 2;
                            centerY += hb.getMinY() + hb.getHeight() / 2;
                            break;
                        }
                    }
                }
                centerX /= positions.size();
                centerY /= positions.size();

                // Vector from center to vertex
                double dx = avgX - centerX;
                double dy = avgY - centerY;
                double len = Math.sqrt(dx*dx + dy*dy);
                if (len > 0) {
                    dx /= len;
                    dy /= len;

                    avgX += dx * 10.0; // push outward
                    avgY += dy * 10.0;
                }

                v.x = avgX;
                v.y = avgY;
                v.placed = true;
            }


            // Draw vertex circle
            Circle c = new Circle(v.x, v.y, 6, Color.LIMEGREEN);
            c.setMouseTransparent(true);
            boardPane.getChildren().add(c);

            // Draw label
            Text label = new Text(v.id);
            label.setFont(Font.font(12));
            label.setFill(Color.WHITE);
            label.setLayoutX(v.x + 6);
            label.setLayoutY(v.y - 6);
            label.setMouseTransparent(true);
            boardPane.getChildren().add(label);
        }

        // Step 3: Bind vertices to hexes
        for (Hex hex : allHexes) {
            int[] vertexIds = VERTICES[hex.id];
            for (int vId : vertexIds) {
                Vertex v = vertices[vId];
                hex.vertices.add(v);
                v.hexes.add(hex);
            }
        }

        System.out.println("All vertices built, pushed, and rendered.");
    }

    @FXML
    public void initialize() 
    {
        // Load Oswald font from classpath
        InputStream fontStream = getClass().getResourceAsStream("/fonts/Oswald-Regular.ttf");
        if (fontStream != null) {
            oswaldFont = Font.loadFont(fontStream, 20); // set font size
            System.out.println("Oswald font loaded successfully.");
        } else {
            System.out.println("Failed to load Oswald font, using default.");
            oswaldFont = Font.font(20);
        }

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
        createCatanBoard(rootPane);
        assignResources();
        assignNumberTokens();

        Button reshuffleButton = new Button("Reshuffle Board");
        reshuffleButton.setOnAction(e -> reshuffleBoard()); 
        rootPane.getChildren().add(reshuffleButton);
    }

    private void createCatanBoard(Pane boardPane) 
    {
        double hexWidth = 120;   // hex width
        double hexHeight = 120;  // hex height
        double gap = 20;
        double rightShift = 120; // move everything right
        double totalHeight = 864; // screen height

        // Catan layout (rows 3-4-5-4-3)
        int[] rowHexCounts = {3, 4, 5, 4, 3};

        // Centering calculation
        int centerRowIndex = 2;
        double centerRowY = totalHeight / 2;
        double middleHexY = centerRowIndex * (0.75 * hexHeight + gap) + (hexHeight / 2);
        double verticalOffset = centerRowY - middleHexY;

        int id = 0;

        // Background hex
        Polygon background = createFlatTopHex(hexWidth * 6 + hexWidth, totalHeight - 185);
        background.setFill(Color.BLACK);
        background.setLayoutX(rightShift + 200);
        background.setLayoutY(0);
        boardPane.getChildren().add(background);

        // --- Prepare random resources ---
        List<String> resources = new ArrayList<>();
        resources.addAll(List.of("sheep","sheep","sheep","sheep"));   // 4
        resources.addAll(List.of("wheat","wheat","wheat","wheat"));   // 4
        resources.addAll(List.of("stone","stone","stone"));           // 3
        resources.addAll(List.of("wood","wood","wood","wood"));       // 4
        resources.addAll(List.of("brick","brick","brick"));           // 3
        resources.add("desert");                                      // 1
        java.util.Collections.shuffle(resources);

        // --- Prepare number tokens ---
        List<Integer> numberTokens = new ArrayList<>();
        numberTokens.add(2);
        numberTokens.add(3); numberTokens.add(3);
        numberTokens.add(4); numberTokens.add(4);
        numberTokens.add(5); numberTokens.add(5);
        numberTokens.add(6); numberTokens.add(6);
        numberTokens.add(8); numberTokens.add(8);
        numberTokens.add(9); numberTokens.add(9);
        numberTokens.add(10); numberTokens.add(10);
        numberTokens.add(11); numberTokens.add(11);
        numberTokens.add(12);
        java.util.Collections.shuffle(numberTokens);

        // --- Create hexes ---
        for (int row = 0; row < rowHexCounts.length; row++) 
        {
            int count = rowHexCounts[row];
            double y = verticalOffset + row * (0.75 * hexHeight + gap);

            for (int col = 0; col < count; col++) {
                double x = col * (hexWidth + gap);

                if (row % 2 == 1) {
                    x += (hexWidth + gap) / 2;
                }

                if (row == 0 || row == rowHexCounts.length - 1) {
                    x += hexWidth + gap;
                }

                x += rightShift;

                Polygon hex = createHex(hexWidth, hexHeight);
                hex.setId("hex" + id);

                // Assign resource
                String resource = resources.get(id);
                Color fillColor;
                switch (resource) {
                    case "sheep": fillColor = Color.LIGHTGREEN; break;
                    case "wheat": fillColor = Color.YELLOW; break;
                    case "stone": fillColor = Color.GREY; break;
                    case "wood":  fillColor = Color.DARKGREEN; break;
                    case "brick": fillColor = Color.SADDLEBROWN; break;
                    case "desert": fillColor = Color.TAN; break;
                    default: fillColor = Color.SANDYBROWN; break;
                }
                hex.setFill(fillColor);
                

                // Create Hex object (NO dice number assigned here)
                Hex hexObj = new Hex(id, hex, row, col, resource, 0, null, new ArrayList<>());
                allHexes.add(hexObj);

                // Add hex first
                boardPane.getChildren().add(hex);

                // Hex ID label (top-middle, small)
                Text idLabel = new Text(String.valueOf(id));
                idLabel.setMouseTransparent(true);
                idLabel.setFont(Font.font(14));
                idLabel.setFill(Color.BLACK);
                idLabel.setTextOrigin(VPos.CENTER);
                idLabel.setLayoutX(x + hexWidth / 2);
                idLabel.setLayoutY(y + 15); // 15 px from top
                boardPane.getChildren().add(idLabel);

                id++;
                hex.setLayoutX(x);
                hex.setLayoutY(y);
                hex.setStroke(Color.WHITE);
                hex.setStrokeWidth(2);            
            }
        }

        // Center background on hex9
        Polygon hex9 = (Polygon) boardPane.lookup("#hex9");
        Bounds hex9Bounds = hex9.getBoundsInParent();
        double centerX = hex9Bounds.getMinX() + hex9Bounds.getWidth() / 2;
        double centerY = hex9Bounds.getMinY() + hex9Bounds.getHeight() / 2;
        Bounds bgBounds = background.getBoundsInLocal();
        background.setLayoutX(centerX - bgBounds.getWidth() / 2);
        background.setLayoutY(centerY - bgBounds.getHeight() / 2);

        // Setup vertices
        Platform.runLater(() -> setupVertices(boardPane));

        System.out.println("Catan board created with resources and number tokens.");
    }

    private void createRoadLocations(Pane boardPane)
    {

    }

    // TELL WHAT ID FORM IT IS
    private void createNumTokens(Pane boardPane)
    {

    }

    
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

    private Polygon createFlatTopHex(double width, double height) {
    Polygon hex = new Polygon();
    hex.getPoints().addAll(
        width / 4, 0.0,         // top-left
        3 * width / 4, 0.0,     // top-right
        width, height / 2,      // right
        3 * width / 4, height,  // bottom-right
        width / 4, height,      // bottom-left
        0.0, height / 2         // left
    );
    return hex;
}
}
