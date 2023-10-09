package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Game extends Application {

    private final StackPane root = new StackPane();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    //Variables denote the number of rows and columns
    final int COLUMN = 7;
    final int ROW = 7;
    //The information about squares which consist of the board
    final int SQUARE_WIDTH = 50;
    final int SQUARE_HEIGHT = 50;



    /**
    Calculates the winner
     */
    public void calcWin(){


    }

    /**
     * Class for button tiles.
     * Contains constructor that specifies stylistic features, dimensions, and functions.
     */
    public class TileButton extends Button{
        double xLocation;
        double yLocation;

        /**
         * Constructor for each tile
         * @param xLocation Specifies the x location of the tile-button in the window.
         * @param yLocation Specifies the y location of the tile-button in the window.
         */
        TileButton(double xLocation, double yLocation){
            super();
            this.xLocation = xLocation;
            this.yLocation = yLocation;

            this.setTranslateX(xLocation);
            this.setTranslateY(yLocation);

            this.setText("");

            // Set minimum width and height
            this.setMinWidth(SQUARE_WIDTH);
            this.setMinHeight(SQUARE_HEIGHT);

            // Set preferred width and height
            this.setPrefWidth(SQUARE_WIDTH);
            this.setPrefHeight(SQUARE_HEIGHT);

            this.setStyle("-fx-background-color: white;");

            // Set the border color, width, and style using CSS
            this.setStyle(
                    "-fx-border-color: #603300;" +            // Border
                    "-fx-border-width: 2px;" +              // Border width
                    "-fx-border-style: solid;"+             // Border style (solid line)
                    "-fx-background-color: #FFBB6E;"          // Fill colour
            );
        }


    }

    /**
     * Class for the half-circle mosaic tiles.
     * Contains a contructor that specifies its stylistic features and dimensions.
     */

    public class MosaicTile extends Arc{
        double centerX;
        double centerY;
        double start;
        double end;

        /**
         * Constructor for the mosaic tile graphics.
         * @param centerX Specifies the center x location of the arc in the window.
         * @param centerY Specifies the center y location of the arc in the window.
         * @param start Specifies where the 'tracing' of the arc starts (in terms of angle).
         * @param end Specifies how big to trace the arc (e.g. 180 --> half circle).
         */
        MosaicTile(double centerX, double centerY, double start, double end){
            super();
            this.centerX = centerX;
            this.centerY = centerY;
            this.start = start; //Where to start tracing the arc.
            this.end = end; //How big is the arc.


            //SETTING DIMENSIONS
            this.setCenterX(centerX);
            this.setCenterY(centerY);
            this.setRadiusX(SQUARE_WIDTH/2);
            this.setRadiusY(SQUARE_HEIGHT/2);
            this.setStartAngle(start);
            this.setLength(end);

            //SETTING STYLISTIC FEATURES
            this.setType(ArcType.OPEN);
            this.setStroke(Color.web("#603300"));
            this.setStrokeWidth(3);
            this.setFill(Color.MOCCASIN);

        }

    }

    public void startScreen(){

        //ADDING IMAGE
        ImageView viewSky = new ImageView();
        Image imageSky = new Image("file:./assets/moon.png");
        viewSky.setImage(imageSky);
        StackPane.setAlignment(viewSky, Pos.TOP_LEFT);
        root.getChildren().add(viewSky);

        ImageView viewCity = new ImageView();
        Image imageCity = new Image("file:./assets/city.png");
        viewCity.setImage(imageCity);
        StackPane.setAlignment(viewCity, Pos.BOTTOM_CENTER);
        root.getChildren().add(viewCity);


        //ADDING WELCOME TEXT
        Text welcomeText = new Text();
        welcomeText.setText("\n Welcome to Marrakech!");
        StackPane.setAlignment(welcomeText, Pos.TOP_CENTER);
        welcomeText.setFill(Color.WHITE);
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 80);
        welcomeText.setFont(moroccanFont);
        root.getChildren().add(welcomeText);

        //CREATING SUBMIT BUTTON

        Button startButton = new Button("START GAME");
        //Setting the style of the start button.
        startButton.setStyle(
                "-fx-font-size: 18px;" +                    // Font size
                        "-fx-text-fill: white;" +                   // Text color
                        "-fx-background-color: #f8a102;"          // Blue background color
        );


        //Adding box to align button in center, below radio buttons
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(startButton);
        buttonBox.setMargin(startButton, new Insets(120, 0, 0, 0)); //Top margin of 120, to be below middle line.
        buttonBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonBox);         // Add the box to the StackPane

        //ADDING RADIO BUTTON
        ToggleGroup radioGroup = new ToggleGroup();

        RadioButton onePlayer = new RadioButton("1 Player");
        onePlayer.setToggleGroup(radioGroup);

        RadioButton twoPlayer = new RadioButton("2 Player");
        twoPlayer.setToggleGroup(radioGroup);

        RadioButton threePlayer = new RadioButton("3 Player");
        threePlayer.setToggleGroup(radioGroup);

        RadioButton fourPlayer = new RadioButton("4 Player");
        fourPlayer.setToggleGroup(radioGroup);

        //Setting radio button style
        String styleString =
                "-fx-text-fill: #8C0B44; " +
                ("-fx-font-family: '" + moroccanFont.getName() + "'; -fx-font-size: 25px;") +
                "-fx-padding: 10; ";

        onePlayer.setStyle(styleString);
        twoPlayer.setStyle(styleString);
        threePlayer.setStyle(styleString);
        fourPlayer.setStyle(styleString);

        HBox radioBox = new HBox(10); //Spacing of 10px between radio buttons.
        radioBox.setAlignment(Pos.CENTER);
        radioBox.getChildren().addAll(onePlayer, twoPlayer, threePlayer, fourPlayer);

        root.getChildren().add(radioBox); //Add box containing radio button to stack pane

        // Set the action for the "Go" button

        startButton.setOnAction(event -> {
            if (radioGroup.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                Text testText = new Text();
                testText.setText("BOOOO");
                root.getChildren().add(testText);
                changeScene();
            } else {
                Text testText = new Text();
                testText.setText("Must select number of players");
                root.getChildren().add(testText);
            }

        });

        root.setStyle("-fx-background-color: #0099ff;");



    }

    public Group gameBasicsDisplay(){

        Group group = new Group();
        final int DRAW_START_X =100; //Where to start 'drawing'
        final int DRAW_START_Y=100;

        //DRAW RECTANGLE TO SERVE AS BORDER:
        double margin = 10;
        Rectangle rectBorder = new Rectangle(SQUARE_WIDTH*COLUMN+margin, SQUARE_HEIGHT*ROW+margin); // Width and height of the rectangle
        // Set the position (X, Y) of the top-left corner of the rectangle
        rectBorder.setX(DRAW_START_X-margin/2);
        rectBorder.setY(DRAW_START_Y-margin/2);
        // Set the fill color of the rectangle
        rectBorder.setFill(Color.MOCCASIN);
        // Set the stroke color (border color) of the rectangle
        rectBorder.setStrokeWidth(3);
        rectBorder.setStroke(Color.web("#603300"));
        group.getChildren().add(rectBorder);


        //BOTTOM LEFT MOSAIC TILE
        MosaicTile mosaicCorner = new MosaicTile(SQUARE_WIDTH+DRAW_START_X/2, SQUARE_HEIGHT*COLUMN+DRAW_START_Y, 90, 270);
        group.getChildren().add(mosaicCorner);

        //TOP RIGHT MOSAIC TILE
        MosaicTile otherCorner = new MosaicTile(SQUARE_WIDTH*ROW+DRAW_START_X, SQUARE_HEIGHT+DRAW_START_Y/2, 180, -270);
        group.getChildren().add(otherCorner);

        //CREATING THE BOARD
        for(int i = 0; i<ROW; i++){ //Iterate through the columns.
            double x = SQUARE_WIDTH * i + DRAW_START_X; //Specifying the x location of the tile.

            if(i%2==0 && i!=0){ //BOTTOM ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*i+DRAW_START_X, SQUARE_HEIGHT*ROW+DRAW_START_Y, 0, -180);
                group.getChildren().add(mosaic);
            }
            else if(i%2==1) { //TOP ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*i+DRAW_START_X, SQUARE_HEIGHT+DRAW_START_Y/2, 0, 180);
                group.getChildren().add(mosaic);
            }


            for(int j = 0; j<ROW; j++){ //Iterate through the rows.
                if(j%2==0 && j!=0){ //RIGHT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*COLUMN+DRAW_START_X, SQUARE_HEIGHT*j+DRAW_START_Y, 90, -180);
                    group.getChildren().add(mosaic);
                }
                else if(j%2==1) { //LEFT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH+DRAW_START_X/2, SQUARE_HEIGHT*j+DRAW_START_Y, 90, 180);
                    group.getChildren().add(mosaic);
                }

                double y = SQUARE_HEIGHT * j + 100; //Specifying the y location of the tile
                TileButton tile1 = new TileButton(x, y); //Creating the tile.
                group.getChildren().add(tile1);

            }

        }

        return group;

    }

    private void changeScene() {
        // Clear the screen by removing all child nodes from the root layout container
        root.getChildren().clear();
        Group group = gameBasicsDisplay();
        root.getChildren().add(group);
    }

    @Override
    public void start(Stage stage) throws Exception {

//         FIXME Task 7 and 15

        startScreen();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
