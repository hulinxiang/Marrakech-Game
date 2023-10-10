package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashSet;

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

    private int numberPlayers;

    //Purple, Cyan-Green, Yellow, Red-Pink
    private String[] colourCodes = new String[] {"#AA05CB", "#069822", "#EC792B", "#D03B7F"}; //String of colour codes for players.

    public static void generatePlayerScene(){
    }


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


    /**
     * Adds city image that is used in some scenes.
     */
    public void backgroundCity(){
        ImageView viewCity = new ImageView();
        Image imageCity = new Image("file:./assets/city.png");
        viewCity.setImage(imageCity);
        StackPane.setAlignment(viewCity, Pos.BOTTOM_CENTER);
        root.getChildren().add(viewCity);
    }

    /**
     * Adds sky image that is used in some scenes.
     */
    public void backgroundSky(){
        //ADDING IMAGE
        ImageView viewSky = new ImageView();
        Image imageSky = new Image("file:./assets/moon.png");
        viewSky.setImage(imageSky);
        StackPane.setAlignment(viewSky, Pos.TOP_LEFT);
        root.getChildren().add(viewSky);
    }


    /**
     * This method specifies the graphics of the first screen and allows users to specify the number of players.
     */
    public void startScreen(){
        //ADDING WELCOME TEXT
        Text welcomeText = new Text();
        welcomeText.setText("\n Welcome to Marrakech!");
        StackPane.setAlignment(welcomeText, Pos.TOP_CENTER);
        welcomeText.setFill(Color.WHITE);
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 80);
        welcomeText.setFont(moroccanFont);
        root.getChildren().add(welcomeText);

        //ADDING RADIO BUTTON
        VBox buttonBox = new VBox(); //Vertical container containing all buttons.
        ToggleGroup radioGroup = new ToggleGroup();

        RadioButton onePlayer = new RadioButton("1 Player");
        onePlayer.setId("1");
        onePlayer.setToggleGroup(radioGroup);

        RadioButton twoPlayer = new RadioButton("2 Player");
        twoPlayer.setId("2");
        twoPlayer.setToggleGroup(radioGroup);

        RadioButton threePlayer = new RadioButton("3 Player");
        threePlayer.setId("3");
        threePlayer.setToggleGroup(radioGroup);

        RadioButton fourPlayer = new RadioButton("4 Player");
        fourPlayer.setId("4");
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
        radioBox.setPrefHeight(100);
        buttonBox.getChildren().add(radioBox);

        //Text to prompt user to press button.
        Text testText = new Text();
        testText.setText("SELECT NUMBER OF PLAYERS");
        //Setting style of text
        testText.setFill(Color.web("#0099ff"));
        testText.setStyle("-fx-font-size: 12px; ");
        testText.setTranslateY(-10); // 20 pixels top padding
        buttonBox.getChildren().add(testText);

        //CREATING START BUTTON
        Button startButton = new Button("START GAME");
        //Setting the style of the start button.
        startButton.setStyle(
                "-fx-font-size: 23px;" +                    // Font size
                        "-fx-text-fill: white;" +                   // Text color
                        "-fx-background-color: #f8a102;"            // Blue background color
        );


        //Adding box to align button in center, below radio buttons
        buttonBox.getChildren().add(startButton);
        buttonBox.setAlignment(Pos.CENTER);


        //Set the event when 'Start' button is pressed.
        startButton.setOnAction(event -> {
            if (radioGroup.getSelectedToggle() != null) {
                RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                numberPlayers = Integer.parseInt(selectedRadioButton.getId()); //Record the number of players selected.
                changeScenePlayer();
            }
            else {
                //Making text visible
                testText.setFill(Color.WHITE);
            }

        });

        root.getChildren().add(buttonBox);         // Add the box to the StackPane
        root.setStyle("-fx-background-color: #0099ff;");



    }

    /**
     * Checks that names entered on the player screen is correct.
     * @param nameArray array of names entered into the text field (generated from splitting string input at comma).
     * @param nameField textField that takes name input, parsed so that it may be disabled.
     * @param instructionText instruction text that changes depending on whether input correct or not.
     */

    public void assignOrder(ArrayList<String> nameArray){
        for(int j=0;j<numberPlayers;j++){

        }
    }
    public void checkNameRequirements(ArrayList<String> nameArray, TextField nameField, Text instructionText){
        //Checking that names are not repeated, and that number of names match number of players...
        if(nameArray.size() == numberPlayers){
            //Checking that no repeats in set by putting into set and comparing sizes:
            HashSet<String> nameSet = new HashSet<>(nameArray);

            if(nameSet.size() != nameArray.size()){ //Less elements in the set means there are duplicates in the array
                //System.out.println("Duplicates");
                instructionText.setText("No duplicate names allowed...");
            }
            else{
                //System.out.println("Correct");
                nameField.setEditable(false); //Disable the textfield if correct input received
                instructionText.setText("Great! Your order and colours are assigned below.");
                assignOrder(nameArray); //Assign order of play.
            }

        }
        else{
            //System.out.println("Not same size.");
            instructionText.setText("Number of names must match number of players");
        }

    }

    /**
     * PLAYER SCREEN GRAPHICS.
     * This method creates the graphics for the screen following the welcome screen.
     * Allows users to specify the names of the players.
     */
    public void playerScreen(){

        //Setting up player section.
        HBox textStore = new HBox(170);
        Text[] playerText = new Text[numberPlayers];

        HBox backgroundStore = new HBox(70);
        Rectangle[] backgrounds = new Rectangle[numberPlayers];
        for(int i = 1; i<=numberPlayers; i++){
            playerText[i-1] = new Text();
            playerText[i-1].setText("Player " +i);
            StackPane.setAlignment(playerText[i-1], Pos.TOP_CENTER);
            Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
            playerText[i-1].setFont(moroccanFont);

            //Set player colour:
            String colour = colourCodes[i-1];
            playerText[i-1].setFill(Color.web(colour));

            //Add background for each player using rectangle.
            backgrounds[i-1] = new Rectangle(WINDOW_WIDTH/6, WINDOW_HEIGHT/5); // Specify width and height
            backgrounds[i-1].setFill(Color.WHITE); // Set the fill color to white
            double cornerRadius = 20; // Radius for round edges of rectangle.
            backgrounds[i-1].setArcWidth(cornerRadius);
            backgrounds[i-1].setArcHeight(cornerRadius);

            backgroundStore.setAlignment(Pos.CENTER);
            backgroundStore.getChildren().add(backgrounds[i-1]);

            textStore.setAlignment(Pos.CENTER);
            textStore.getChildren().add(playerText[i-1]);
        }

        //Add margin at top of rectangle to improve display.
        root.getChildren().add(backgroundStore);
        Insets margin = new Insets(70, 0, 0, 0); // Top, Right, Bottom, Left
        StackPane.setMargin(backgroundStore, margin);
        root.getChildren().add(textStore);

        //SETTING UP TEXT FIELD TO INPUT NAMES.
        VBox verticalBox = new VBox(12); //VBox to vertically align input with Player text.

        //Add instruction text and style it.
        Text instructionText = new Text();
        instructionText.setText("What are the names of the players?");
        verticalBox.getChildren().add(instructionText);
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
        instructionText.setFont(moroccanFont);
        instructionText.setFill(Color.web("#ffffff"));

        //Text field itself.
        TextField nameField = new TextField();//Names of player input prompt - textfield.
        nameField.setPromptText("Enter names seperated by comma... e.g. bob,jane");//Prompt text.
        nameField.setFocusTraversable(false);

        //Setting dimensions and font
        nameField.setPrefWidth(WINDOW_WIDTH/2.6);
        nameField.setMaxWidth(WINDOW_WIDTH/2.6);
        Font fieldFont = new Font("Arial", 16);
        nameField.setFont(fieldFont);

        //Set alignment so that displays properly.
        verticalBox.setAlignment(Pos.CENTER);
        verticalBox.getChildren().add(nameField);
        Insets inputMargin = new Insets(-200, 0, 0, 0); // Top, Right, Bottom, Left
        StackPane.setMargin(verticalBox, inputMargin);


        root.getChildren().add(verticalBox);

        //FETCHING NAMES OF PLAYERS FROM THE INPUT AND STORE IN ARRAY
        ArrayList<String> nameArray = new ArrayList<>();
        //When enter key is pressed, get names input.
        nameField.setOnKeyPressed(event -> {
            if (event.getCode().getName().equals("Enter")) {

                nameArray.clear(); //Must clear array so that it contains the current input only.
                String nameInput = nameField.getText(); //Getting names input.

                if (!nameInput.isEmpty()) {
                    // Add the input text to the ArrayList - delineated at comma.
                    String[] names = nameInput.split(",");
                    for (String name: names) {
                        nameArray.add(name);
                    }
                    checkNameRequirements(nameArray, nameField, instructionText); //Checking that valid names entered.
                }
            }
        });



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


    /**
     *  Changes scene from start screen to player selection screen.
     */
    private void changeScenePlayer() {
        // Clear the screen by removing all child nodes from the root layout container
        root.getChildren().clear();
        backgroundSky();
        backgroundCity();
        playerScreen();

    }

    private void changeSceneBoard() {
        // Clear the screen by removing all child nodes from the root layout container
        root.getChildren().clear();
        Group group = gameBasicsDisplay();
        root.getChildren().add(group);
    }

    @Override
    public void start(Stage stage) throws Exception {

//         FIXME Task 7 and 15

        backgroundCity();
        backgroundSky();
        startScreen();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
