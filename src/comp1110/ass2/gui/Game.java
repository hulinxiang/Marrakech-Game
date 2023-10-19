package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Tile;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import comp1110.ass2.Direction;
import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collections;


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

    private ArrayList<String> nameArray = new ArrayList<>(); //Array with names of players

    //String array representing the order of colours of the players:
    private ArrayList<String> colourLetters = new ArrayList<String>(Arrays.asList("p", "c", "y", "r"));

    // String of colour codes for players... Purple, Cyan-Green, Yellow, Red-Pink
    private String[] colourCodes = new String[] {"#AA05CB", "#069822", "#EC792B", "#D03B7F"};
    Text messageText = new Text(); //Global since displayed throughout the game.

    Text roundText = new Text(); //Global since displayed throughout the game.
    ArrayList<Text> statText = new ArrayList<Text>(); //Global since updated throughout the game.
    StackPane rugPane = new StackPane(); //Stack pane that needs to be global because it is added/removed from root throughout

    AsamSymbol asam; //Global asam variable, called in different methods.
    TileRect[][] tileRect = new TileRect[ROW][COLUMN];  //49 tiles on the board. Global since called in differet methods

    Marrakech theGame; //New Marrakech class, initialised in getInitial() method.

    int roundCounter = 1; //Counts how many rounds the game has gone through
    int playerCounter = 1; //Counts which player's turn it is

    boolean firstBool; //Records which button has been selected.

    /**
    Calculates the winner
     */
    public void calcWin(){


    }

    public class TileRect extends Rectangle {
        double xLocation;
        double yLocation;


        /**
         * Constructor for each tile
         *
         * @param xLocation Specifies the x location of the tile in the window.
         * @param yLocation Specifies the y location of the tile in the window.
         */
        TileRect(double xLocation, double yLocation) {
            super();
            this.xLocation = xLocation;
            this.yLocation = yLocation;

            this.setTranslateX(xLocation);
            this.setTranslateY(yLocation);

            // Set width and height
            this.setWidth(SQUARE_WIDTH);
            this.setHeight(SQUARE_HEIGHT);

            this.setFill(Color.web("#FFBB6E")); //Set fill colour
            this.setStrokeWidth(2.0); //Set stroke width
            this.setStroke(Color.web("#603300"));

        }

        public void setColour(String colour){
            this.setFill(Color.web(colour)); //Set fill colour
        }
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


            // Set the border color, width, and style using CSS
            this.setStyle(
                    "-fx-border-color: #603300;" +            // Border
                    "-fx-border-width: 2px;" +              // Border width
                    "-fx-border-style: solid;"+             // Border style (solid line)
                    "-fx-background-color: #FFBB6E;"          // Fill colour
            );

            //SET EVENT LISTENER FOR EACH TILE BUTTON
            this.setOnAction(event -> {

                String borderColour = colourCodes[playerCounter-1]; //Player who selected the square

                //CHECK IF THIS IS FIRST OR SECOND RUG SQUARE SELECTED
                if(firstBool){
                    //Set style
                    this.setStyle("-fx-border-color: " + borderColour +";" +
                            "-fx-border-width: 4px;" +
                            "-fx-background-color: transparent;");
                    //Remove all except current button from rugpane.
                    rugPane.getChildren().removeIf(node -> node instanceof Button && node != this);

                    //Remove the event listener
                    this.setDisable(true);
                    firstBool = false;
                    setMessage("Select 2nd square for rug.");
                    rugPlacement((int) Math.round(this.xLocation/SQUARE_WIDTH +3), (int) Math.round(this.yLocation/SQUARE_HEIGHT +3));
                }
                else{
                    this.setStyle("-fx-border-color: " + borderColour +";" +
                            "-fx-border-width: 2px;" +
                            "-fx-background-color: transparent;");
                    //Remove all buttons except first and the selected button
                    rugPane.getChildren().removeIf(node
                            -> node instanceof Button && node != this && node != rugPane.getChildren().get(0) );

                    //Check requirements to make rug placement.
                    TileButton firstButton = (TileButton) rugPane.getChildren().get(0);
                    TileButton secondButton = this;
                    checkPlacement(firstButton, secondButton);
                }

            });
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

    public class AsamSymbol extends Polygon {
        //Polygon triangle = new Polygon();
        double x;
        double y;
        double side = SQUARE_WIDTH;
        AsamSymbol(double x, double y){
            super();
            this.x = x;
            this.y = y;

            this.getPoints().addAll(
                    0.0,-sqrtSide,
                    halfSide, sqrtSide,
                    -halfSide, sqrtSide
            );

            this.setStroke(Color.web("#603300"));
            this.setFill(Color.web("#0099ff"));

            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
        }

        double halfSide = SQUARE_WIDTH / 2.0;
        double sqrtSide = Math.sqrt(Math.pow(SQUARE_WIDTH, 2) - Math.pow(halfSide, 2))/2.0;

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
     * Stores the randomised order of the players in an ArrayList and displays accordingly.
     */
    public void assignOrder(){
        //Use shuffle to randomise order
        Collections.shuffle(nameArray);

        Text[] nameText = new Text[numberPlayers]; //Display text with names of players.
        StackPane[] namePane = new StackPane[numberPlayers]; //Stackpane to ensure proper display of text.
        HBox nameStore = new HBox(WINDOW_WIDTH/10); //HBox to store stackpanes of names.

        //Iterate through number of players, displaying the name of each
        for(int i = 1; i<=numberPlayers; i++) {
            nameText[i-1] = new Text();
            nameText[i-1].setText(nameArray.get(i-1));
            //Setting stylistic features of nameText:
            Font basicFont = new Font("Arial", 20);
            nameText[i-1].setFont(basicFont);
            nameText[i-1].setFill(Color.web("#000000"));

            namePane[i-1] = new StackPane();
            namePane[i-1].getChildren().add(nameText[i-1]);

            StackPane.setAlignment(nameText[i-1], Pos.CENTER);
            nameText[i-1].setTextAlignment(TextAlignment.CENTER);
            StackPane.setMargin(nameText[i-1], new Insets(110, 0, 0, 0));

            namePane[i-1].setPrefWidth(150); // Preferred width
            namePane[i-1].setMaxWidth(150);  // Maximum width
            StackPane.setAlignment(namePane[i-1], Pos.CENTER);

            nameStore.getChildren().add(namePane[i-1]);
            nameText[i-1].setWrappingWidth(WINDOW_WIDTH/10); // Set the wrapping width


        }
        nameStore.setAlignment(Pos.CENTER);
        root.getChildren().add(nameStore);

    }

    /**
     * Checks that names entered on the player screen is correct.
     * @param nameField textField that takes name input, parsed so that it may be disabled.
     * @param instructionText instruction text that changes depending on whether input correct or not.
     */
    public void checkNameRequirements(TextField nameField, Text instructionText){
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
                boolean proceed = true;
                for(int i=0; i<numberPlayers;i++){
                    if(nameArray.get(i)==""){ //Check that each name at least 1 character long
                        proceed = false;
                    }
                }

                if(proceed){ //If proceed is true then can go to next stage of game.
                    nameField.setEditable(false); //Disable the textfield if correct input received
                    instructionText.setText("Press ENTER to start the game!");
                    assignOrder(); //Assign order of play.

                    //If enter is pressed game screen will appear.
                    nameField.setOnKeyPressed(event -> {
                        if (event.getCode().getName().equals("Enter")) {
                            changeSceneBoard(); //Changes scene to board display
                        }
                    });
                }
                else{
                    instructionText.setText("Names must be minimum one character.");
                }
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
        //When enter key is pressed, get names input.
        nameField.setOnKeyPressed(event -> {
            if (event.getCode().getName().equals("Enter")) {

                nameArray.clear(); //Must clear array so that it contains the current input only.
                String nameInput = nameField.getText(); //Getting names input.

                if (!nameInput.isEmpty()) {
                    // Add the input text to the ArrayList - delineated at comma.
                    String[] names = nameInput.split(",");
                    for (String name: names) {
                        nameArray.add(name.toUpperCase());
                    }
                    checkNameRequirements(nameField, instructionText); //Checking that valid names entered.
                }
            }
        });



    }

    /**
     * Start of game, game string generated to be translated in the Marrakech class.
     */
    public String initialStringGenerate(){
        String initialGameString = ""; //Game string starts wiht a P to denote player strings.
        for(int i=0; i<numberPlayers;i++){
            //Each player starts with 30 dirhams and 15 rugs. Initially all players are in the game.
            String individualString = "P" + colourLetters.get(i) + "030" + "15" + "i";
            initialGameString += individualString;
        }

        //Asam starts facing north in the middle of the board aka position (3,3)
        initialGameString += "A33NB";

        //Intially all the tiles are empty hence add 49 empty squares:
        for(int j = 0; j<49; j++){
            initialGameString += "n00";
        }
        return initialGameString;
    }

    /**
     * Displays the names of the players at the top of the main screen.
     */
    public void namesDisplay(){
        //Setting up player section.
        HBox namesStore = new HBox(100);
        Text[] nameText = new Text[numberPlayers];
        StackPane[] namePane = new StackPane[numberPlayers]; //Stackpane to ensure proper display of text.

        HBox backgroundStore = new HBox(100);
        Rectangle[] backgrounds = new Rectangle[numberPlayers];

        for(int i = 1; i<=numberPlayers; i++){
            //Text that displays player name
            nameText[i-1] = new Text();
            nameText[i-1].setText(i + ". " + nameArray.get(i-1)); //Display the names of players
            Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 25);
            nameText[i-1].setFont(moroccanFont);
            String colour = colourCodes[i-1]; //Display player colour
            nameText[i-1].setFill(Color.web(colour));

            //Stackpane to organise alignment of text
            namePane[i-1] = new StackPane();
            namePane[i-1].getChildren().add(nameText[i-1]);
            StackPane.setAlignment(nameText[i-1], Pos.TOP_CENTER);
            nameText[i-1].setTextAlignment(TextAlignment.CENTER);
            StackPane.setMargin(nameText[i-1], new Insets(35, 0, 0, 0));
            nameText[i-1].setWrappingWidth(WINDOW_WIDTH/10); // Set the wrapping width
            namePane[i-1].setPrefWidth(WINDOW_WIDTH/7); // Preferred width
            namePane[i-1].setMaxWidth(WINDOW_WIDTH/7);  // Maximum width
            namesStore.setAlignment(Pos.CENTER);
            namesStore.getChildren().add(namePane[i-1]);

            //Add background for each player using rectangle.
            backgrounds[i-1] = new Rectangle(WINDOW_WIDTH/7, WINDOW_HEIGHT/7); // Specify width and height
            backgrounds[i-1].setFill(Color.WHITE); // Set the fill color to white
            double cornerRadius = 20; // Radius for round edges of rectangle.
            backgrounds[i-1].setArcWidth(cornerRadius);
            backgrounds[i-1].setArcHeight(cornerRadius);
            backgroundStore.setAlignment(Pos.TOP_CENTER); //Setting alignment for rectangle.
            backgroundStore.getChildren().add(backgrounds[i-1]);
        }

        //Add margin at top of rectangle to improve display.
        root.getChildren().add(backgroundStore);
        Insets margin = new Insets(30, 0, 0, 0); // Top, Right, Bottom, Left
        StackPane.setMargin(backgroundStore, margin);
        root.getChildren().add(namesStore);
        StackPane.setAlignment(namesStore, Pos.CENTER);


    }

    public void roundDisplay(boolean start, int currentPlayer){
        //If at the start display the text, if not just update the text.
        if(start) {
            roundText.setText(" Round " + roundCounter + "\n Player " + currentPlayer);
            Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 25);
            roundText.setFont(moroccanFont);
            roundText.setFill(Color.web("#ffffff"));

            //Create vBox to ensure proper alignment
            VBox roundBox = new VBox(); //Container to display the text
            roundBox.setPrefWidth(WINDOW_WIDTH / 5); // Preferred width
            roundBox.setMaxWidth(WINDOW_WIDTH / 5);  // Maximum width
            roundBox.getChildren().add(roundText);
            roundBox.setAlignment(Pos.TOP_LEFT);

            // Set the minimum height of the VBox
            roundBox.setMinHeight(100);

            // Set the preferred height of the VBox
            roundBox.setPrefHeight(100);

            // Set the maximum height of the VBox
            roundBox.setMaxHeight(100);


            //Adding the messageBox to the root.
            StackPane.setAlignment(roundBox, Pos.TOP_LEFT);
            root.getChildren().add(roundBox);
        }
        else{
            roundText.setText(" Round " + roundCounter + "\n Player " + currentPlayer);
        }
    }
    /**
     * Sets the instruction for the messageText to display.
     * @param instructionString String to be displayed.
     */
    public void setMessage(String instructionString){
        messageText.setText(instructionString);
    }

    /**
     * Displays the instruction message which is altered throughout the game depending on game state.
     */
    public void messageDisplay(){
        //Display the messageText (global variable)
        messageText.setText("Let us begin!");
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
        messageText.setFont(moroccanFont);
        messageText.setFill(Color.web("#ffffff"));
        messageText.setWrappingWidth(WINDOW_WIDTH/6); // Set the wrapping width
        //Create vBox to ensure proper alignment
        VBox messageBox = new VBox(); //Container to display the text
        messageBox.setPrefWidth(WINDOW_WIDTH/5); // Preferred width
        messageBox.setMaxWidth(WINDOW_WIDTH/5);  // Maximum width
        messageBox.getChildren().add(messageText);
        messageBox.setAlignment(Pos.CENTER_LEFT);

        //Adding the messageBox to the root.
        StackPane.setAlignment(messageBox, Pos.CENTER_RIGHT);
        root.getChildren().add(messageBox);
    }

    /**
     * Displays asam's movement on the screen.
     * @param directionChange Boolean value that indicates whether asam's direction has changed
     *                        during the movement.
     */
    public void movementDisplay(boolean directionChange){
        //-3 since 3,3 on board is considered 0,0 on display.
        double newX = (theGame.asam.getX() -3) * SQUARE_WIDTH;
        double newY = (theGame.asam.getY() -3) *SQUARE_HEIGHT;

        asam.setTranslateX(newX);
        asam.setTranslateY(newY);

        //DETECT DIRECTION CHANGE
        if(directionChange) {
            asamRotateDisplay();
        }

    }

    public boolean[] firstConditions(boolean[] coordinateBool, int xRef, int yRef){
        if(xRef==0){
            //Don't create left button
            coordinateBool[0] = false;
        }
        else if(xRef==6){
            //Don't create right button
            coordinateBool[1] = false;
        }

        if(yRef==0){
            //Don't create top button
            coordinateBool[2] = false;
        }
        else if(yRef==6){
            //Don't create bottom button
            coordinateBool[3] = false;
        }


        return coordinateBool;
    }
    public boolean[] additionalConditions(boolean[] coordinateBool, int xRef, int yRef){
        coordinateBool = firstConditions(coordinateBool, xRef,yRef);
        if(xRef-1 == theGame.asam.getX()){ //Asam is to left
            coordinateBool[0] = false;
        }
        else if(xRef+1 == theGame.asam.getX()){ //Asam is to right
            coordinateBool[1] = false;
        }

        if(yRef-1 == theGame.asam.getY()){ //Asam is to the top
            coordinateBool[2] = false;
        }
        else if(yRef+1 == theGame.asam.getY()){ //Asam is to the bottom
            coordinateBool[3] = false;
        }

        return coordinateBool;
    }
    public void rugPlacement(int xRef, int yRef){
        //Must set width of stackpane otherwise not able to click other buttons
        //Set width to the width of the board.
        rugPane.setPrefWidth(SQUARE_WIDTH*7);
        rugPane.setMinWidth(SQUARE_WIDTH*7);
        rugPane.setMaxWidth(SQUARE_WIDTH*7);


        //Array of booleans - LEFT, RIGHT, TOP BOTTOM
        boolean[] coordinateBool = new boolean[4];

        for(int i = 0; i<4; i++){
            coordinateBool[i] = true;
        }

        if(!firstBool) {//Additional conditions to be checked if second button
            coordinateBool = additionalConditions(coordinateBool, xRef,yRef);
        }
        else{
            root.getChildren().add(rugPane);
            coordinateBool = firstConditions(coordinateBool, xRef, yRef);
        }

        makeRugButtons(coordinateBool, xRef,yRef);

    }

    public void makeRugButtons(boolean[] coordinateBool, int xRef, int yRef){
        //Make buttons and customise display
        String styleString = "-fx-border-width: 4px;" +
                "-fx-border-style: solid;" +
                "-fx-border-color: #ffffff;" +
                "-fx-background-color: transparent"; //Transparent since need to be able to see what is behind.

        if(coordinateBool[0]){ //LEFT BUTTON
            TileButton buttonLeft = new TileButton((xRef-4) * SQUARE_WIDTH,(yRef-3)*SQUARE_HEIGHT);
            buttonLeft.setStyle(styleString);
            rugPane.getChildren().add(buttonLeft);
        }

        if(coordinateBool[1]){ //RIGHT BUTTON
            TileButton buttonRight = new TileButton((xRef-2) * SQUARE_WIDTH,(yRef-3)*SQUARE_HEIGHT);
            buttonRight.setStyle(styleString);
            rugPane.getChildren().add(buttonRight);
        }

        if(coordinateBool[2]){ //TOP BUTTON
            TileButton buttonTop = new TileButton((xRef-3) *SQUARE_WIDTH,(yRef-4) * SQUARE_HEIGHT);
            buttonTop.setStyle(styleString);
            rugPane.getChildren().add(buttonTop);
        }

        if(coordinateBool[3]){ //BOTTOM BUTTON
            TileButton buttonBot = new TileButton((xRef-3) * SQUARE_WIDTH, (yRef -2)* SQUARE_HEIGHT);
            buttonBot.setStyle(styleString);
            rugPane.getChildren().add(buttonBot);
        }

    }

    public void checkPayment(String message){
        //Check if asam landed on another player's rug
        int xCord = theGame.asam.getX();
        int yCord = theGame.asam.getY();
        String colourString = theGame.board.tiles[xCord][yCord].getColour().substring(0,1).toLowerCase();

        //Check wif payment is needed
        if(colourString.equals("n")){ //Asam landed on empty tile
            message += "\nNo payment."; //Add to the message text
        }
        else if(colourString.equals(colourLetters.get(playerCounter-1))){ //Asam landed on current player's rug
            message += "\nNo payment."; //Add to the message text
        }
        else{
            message += "\nPayment made."; //Add to the message text
            //Generate game string
            String tempString = theGame.generateGameString();
            int payment = Marrakech.getPaymentAmount(tempString);
            int receiver = colourLetters.indexOf(colourString) +1; //Get index of player whose rug landed on
            processPayment(payment, receiver);
        }
        setMessage(message);
    }
    /**
     * Process the payments between players.
     */
    public void processPayment(int paymentAmt, int receiver){
        theGame.players[playerCounter-1].dirhams -= paymentAmt; //Subtract from player paying
        theGame.players[receiver-1].dirhams += paymentAmt; //Add to player receiving
        displayStats(false);//Display the payment

    }
    /**
     * Displays the dice button and calls the rollDie() method from the Marrakech class when button is pressed.
     */
    public void diceRoll(){
        setMessage("Roll the die!");
        //CREATING DICE BUTTON
        VBox diceBox = new VBox(); //Container to display dice button
        Button diceButton = new Button("ROLL");
        //Setting the style of the start button.
        diceButton.setStyle(
                "-fx-font-size: 15px;" +                    //Font size
                "-fx-text-fill: white;" +                   //Text color
                "-fx-background-color: #f8a102;"            //Yellow background color
        );

        //SETTING SIZE OF BUTTON
        double buttonSize = 60; // Set the size of the square button
        diceButton.setPrefWidth(buttonSize);
        diceButton.setPrefHeight(buttonSize);

        diceBox.setPrefWidth(WINDOW_WIDTH/5); // Preferred width
        diceBox.setMaxWidth(WINDOW_WIDTH/5);  // Maximum width
        diceBox.getChildren().add(diceButton);
        diceBox.setAlignment(Pos.CENTER_RIGHT);

        StackPane.setAlignment(diceBox, Pos.CENTER_LEFT);
        root.getChildren().add(diceBox);

        //Setting up event handler for when button is clicked:
        diceButton.setOnAction(event -> {
            root.getChildren().remove(diceBox); //Immediately remove dice button from screen
            int rolledNumber = Marrakech.rollDie(); //Roll the dice.

            //Record Asam's previous direction
            Direction previousDirection = theGame.asam.getDirection();

            //MOVE ASAM IN THE DIRECTION SPECIFIED BY THE DICE
            String currentAsamString = theGame.asam.getString(); //Generate asam string
            String movedString = Marrakech.moveAssam(currentAsamString, rolledNumber); //Move asam.
            theGame.asam.decodeAsamString(movedString);

            if(theGame.asam.getDirection().equals(previousDirection)){
                movementDisplay(false); //Display does not need to account for change in direction
            }
            else{
                movementDisplay(true);
            }

            String textInstructions= "";
            if(rolledNumber==1){
                textInstructions = "Asam has moved " + rolledNumber + " step.";
            }
            else{
                textInstructions = "Asam has moved " + rolledNumber + " steps";
            }

            checkPayment(textInstructions); //Check if asam landed on other player's rug and if so process payment.

            //Once dice has been rolled and Asam has been moved, rug placement is next.
            //Wait one second then introduce rug placement
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                //Remove roll button from root
                firstBool = true;
                //Call rug placement function with asam's coordinates as reference
                rugPlacement(theGame.asam.getX(), theGame.asam.getY());
                setMessage("Select 1st square for rug.");
            });
            pause.play();
        });


    }

    /**
     * Initialises a new asam symbol and adds to the root.
     */
    public void asamDisplay(){
        asam = new AsamSymbol(0, 0); //Creating a new Asam symbol, initial rotation is 0, and position is in middle
        root.getChildren().add(asam);
    }

    /**
     *Rotates asam 90 degrees left or right from its current rotation
     */
    public void asamRotateDisplay(){

        //Switch case to check and display asam's rotation
        switch(theGame.asam.getDirection()){
            case NORTH:
                asam.setRotate(0);
                break;
            case EAST:
                asam.setRotate(90);
                break;
            case SOUTH:
                asam.setRotate(180);
                break;
            case WEST:
                asam.setRotate(270);
                break;

        }
        //asam.setRotate(asam.getRotate() +(factor*90)); //Rotate 90 degrees each time from current rotation

    }

    public void checkPlacement(TileButton oneBut, TileButton twoBut){
        //Create game string
        String gameString = theGame.generateGameString();

        int xFirst = (int) oneBut.xLocation/SQUARE_WIDTH +3;
        int yFirst = (int) oneBut.yLocation/SQUARE_HEIGHT +3;
        IntPair firstCoord = new IntPair(xFirst,yFirst);

        //x and y coordinate of second rug square selected
        int xSec = (int) twoBut.xLocation/SQUARE_WIDTH +3;
        int ySec = (int) twoBut.yLocation/SQUARE_HEIGHT +3;
        IntPair secondCoord = new IntPair(xSec,ySec);

        String rugString = theGame.generateRugString(colourLetters.get(playerCounter-1), firstCoord, secondCoord);
        //DOUBLE CHECK THAT PLACEMENT IS VALID
        if(!Marrakech.isPlacementValid(gameString, rugString)){
            setMessage("Invalid rug placement, try again");
            //Clear everything from rugpane:
            rugPane.getChildren().clear(); //Before displaying new buttons clear all previous
            root.getChildren().remove(rugPane); //Remove from root.
            firstBool = true;
            rugPlacement(theGame.asam.getX(), theGame.asam.getY()); //Give opportunity to try again
        }
        else{
            setMessage("Rug placement valid!");
            String newString = Marrakech.makePlacement(gameString, rugString);
            theGame.decodeMarrakech(newString); //Decode string
            //Clear everything from rugpane
            rugPane.getChildren().clear(); //Before displaying new buttons clear all previous
            root.getChildren().remove(rugPane); //To ensure that buttons at top, remove from root.

            setColour(); //Set colour of tiles
            displayStats(false);//Update player statistics.

            //Go to next player after 1 second pause.
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                playerCounter += 1;
                round();
            });
            pause.play();
        }


    }
    /**
     * Sets colours of tiles based on which rug is occupied.
     */
    public void setColour(){
        for(int i = 0; i<ROW; i++){ //Iterate through the columns
            for(int j = 0; j<ROW; j++){ //Iterate through the rows.
                String colourString = theGame.board.tiles[i][j].getColour().substring(0,1).toLowerCase();
                if(!colourString.equals("n")){
                    int index = colourLetters.indexOf(colourString);
                    tileRect[i][j].setFill(Color.web(colourCodes[index]));
                }

            }

        }
    }
    public Group gameBoardDisplay(){
        backgroundCity(); //Add city in background of baord.

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
                tileRect[i][j] = new TileRect(x, y); //Creating the tile.
                group.getChildren().add(tileRect[i][j]);

            }

        }
        return group;

    }

    /**
     * Displays the player statistics such as the nummber of Dirhams and Rugs.
     * @param initial If displaying for the first time then true, otherwise if just displaying updated sats then false.
     */
    public void displayStats(boolean initial){
        //Similar to the player names, the dirham and rug display is organised using Hboxes and Stackpanes.
        HBox statStore = new HBox(100);
        StackPane[] statPane = new StackPane[numberPlayers];

        if(initial) { //If displaying for the first time, the visual aspects need to be set as well.
            for (int i = 1; i <= numberPlayers; i++) {
                //Text that displays player the statistics - dirhams and rugs.
                statText.add(new Text());

                //Getting the number of dirhams and rugs from the Marrakech class.
                int numberDirhams = theGame.getPlayers()[i-1].dirhams;
                int numberRugs = theGame.getPlayers()[i-1].rugs;

                statText.get(i-1).setText(numberDirhams + " dirhams \n " + numberRugs + " rugs    "); //Display the names of players

                //Setting the visual aspect of the display
                Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 20);
                statText.get(i-1).setFont(moroccanFont);
                String colour = colourCodes[i - 1]; //Display player colour
                statText.get(i-1).setFill(Color.web(colour));

                //Stackpane to organise alignment of text
                statPane[i-1] = new StackPane();
                statPane[i-1].getChildren().add(statText.get(i-1));
                StackPane.setAlignment(statText.get(i-1), Pos.TOP_CENTER);
                statText.get(i-1).setTextAlignment(TextAlignment.CENTER);
                StackPane.setMargin(statText.get(i-1), new Insets(70, 0, 0, 0));
                statText.get(i-1).setWrappingWidth(WINDOW_WIDTH/10); // Set the wrapping width
                statPane[i-1].setPrefWidth(WINDOW_WIDTH/7); // Preferred width
                statPane[i-1].setMaxWidth(WINDOW_WIDTH/7);  // Maximum width
                statStore.setAlignment(Pos.CENTER);
                statStore.getChildren().add(statPane[i-1]);
            }

            root.getChildren().add(statStore);
        }
        else { //Only update the text not the visual aspects.
            for (int j = 1; j <= numberPlayers; j++) {
                //Getting the number of dirhams and rugs from Marrakech class.
                int numberDirhams = theGame.getPlayers()[j - 1].dirhams;
                int numberRugs = theGame.getPlayers()[j - 1].rugs;
                statText.get(j-1).setText(numberDirhams + " dirhams \n " + numberRugs + " rugs    "); //Display the
            }
        }
    }

    /**
     * Displays end specifies names of rotation buttons.
     * Add event listener to the button, and specifies the methods to be called when button clicked.
     */
    public void asamRotateButton(){
        //CREATING DICE BUTTON
        VBox directionBox = new VBox(); //Container to display dice button
        HBox horizontalBox = new HBox(10); //10 in between.

        Button[] directionButton = new Button[3]; //3 buttons in array 90 degrees left, right, or no rotation

        //Getting Asam's current direction
        Direction currentDirection = theGame.asam.getDirection();

        for(int i=0; i<directionButton.length; i++){
            //If i is 0 then left button, if 1 then no rotate button, if 2 then right rotate button
            //Find possible directions left and right to get button name.
            Direction buttonDirection = theGame.possibleDirections(currentDirection, i-1);
            directionButton[i] = new Button(buttonDirection.name());

            //Give the button an id so that it can be identified by the event listener.
            String id = Integer.toString(i-1); //-1 if left, 0 if none, 1 if right
            directionButton[i].setId(id);

            //SETTING THE STYLE OF THE BUTTON
            directionButton[i].setStyle(
                    "-fx-font-size: 10px;" +                    //Font size
                            "-fx-text-fill: white;" +                   //Text color
                            "-fx-background-color: #f8a102;"            //Yellow background color
            );

            //SETTING SIZE OF BUTTON
            double buttonSize = 60; // Set the size of the square button
            directionButton[i].setPrefWidth(buttonSize);
            directionButton[i].setPrefHeight(buttonSize);

            horizontalBox.getChildren().add(directionButton[i]);

        }

        horizontalBox.setAlignment(Pos.CENTER_RIGHT);
        directionBox.getChildren().add(horizontalBox);
        //Setting up alginment
        directionBox.setPrefWidth(WINDOW_WIDTH/5); // Preferred width
        directionBox.setMaxWidth(WINDOW_WIDTH/5);  // Maximum width
        directionBox.setAlignment(Pos.CENTER_RIGHT);
        StackPane.setAlignment(directionBox, Pos.CENTER_LEFT);
        root.getChildren().add(directionBox);

        //Setting up event handler for when button is clicked:
        for(int j=0; j<3; j++){
            directionButton[j].setOnAction(event -> {
                directionBox.getChildren().removeAll();
                root.getChildren().removeAll(directionBox);
                //-1 for right, 0 for middle, 1 for left.
                int rotationFactor = Integer.parseInt(((Button) event.getSource()).getId()); //Setting the message displayed to the id.

                //GENERATE ASAM STRING
                String currentAsamString = theGame.asam.getString();

                //Rotate and decode the new string.
                String newAsamString = Marrakech.rotateAssam(currentAsamString, rotationFactor*90);
                theGame.asam.decodeAsamString(newAsamString);
                //Display this rotation
                asamRotateDisplay();
                //Once direction has been decided, display next stay of game: Rolling the dice
                diceRoll();
            });
        }

    }

    /**
     * Go through one round of play
     */
    public void round(){
        if(playerCounter == numberPlayers+1){ //Back to first player
            roundCounter += 1;
            playerCounter = 1;
        }

        //Display the round and the player whose turn it is.
        if(roundCounter == 1){
            roundDisplay(true, playerCounter); //Start of game so visuals need to be set.
        }
        else{
            roundDisplay(false, playerCounter);
        }

        if(theGame.players[playerCounter-1].rugs ==0){
            System.out.println("Game Over!!");
        }
        else{
            setMessage("Set the direction of Asam");
            //display Asam direction buttons
            asamRotateButton();
        }


    }

    /**
     * Decode the initial string to set the player 'stats', the colours of the squares, and asam's position.
     */
    public void getInitial(){
        String initialString = initialStringGenerate();
        theGame = new Marrakech(initialString);
        displayStats(true);
        asamDisplay();
        round();

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

    /**
     * Changes scene to the main game.
     */
    private void changeSceneBoard() {
        //Clear the screen by removing all child nodes from the root layout container
        root.getChildren().clear();
        namesDisplay(); //Display names of players
        messageDisplay(); //Display the introduction message.
        Group group = gameBoardDisplay();
        root.getChildren().add(group);
        getInitial(); //Initialise instance of Marrakech class based on the game string generated:
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
    public static void main(String[] args){
        launch();
    }
}
