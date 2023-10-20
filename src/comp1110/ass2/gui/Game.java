package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.ai.AI;
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


/**
 * @author Lize
 */
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
    private String[] colourCodes = new String[]{"#AA05CB", "#069822", "#EC792B", "#D03B7F"};
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
    boolean opponentBoo; //Record if single player playing against computer.

    boolean intBoo; //Records if opponent is intelligent (True) or random (False).

    AI opponent; //Only used if one player in the game.


    /**
     * Class specifying the stylistic and location features of the rectangles
     * that make the tiles when the board is displayed.
     */
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

            //Set stylistic features.
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
         *
         * @param xLocation Specifies the x location of the tile-button in the window.
         * @param yLocation Specifies the y location of the tile-button in the window.
         */
        TileButton(double xLocation, double yLocation) {
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
                            "-fx-border-style: solid;" +             // Border style (solid line)
                            "-fx-background-color: #FFBB6E;"          // Fill colour
            );

            //SET EVENT LISTENER FOR EACH TILE BUTTON
            this.setOnAction(event -> {

                String borderColour = colourCodes[playerCounter - 1]; //Player who selected the square

                //CHECK IF THIS IS FIRST OR SECOND RUG SQUARE SELECTED
                if (firstBool) {
                    //Set style
                    this.setStyle("-fx-border-color: " + borderColour + ";" +
                            "-fx-border-width: 4px;" +
                            "-fx-background-color: transparent;");
                    //Remove all except current button from rugpane.
                    rugPane.getChildren().removeIf(node -> node instanceof Button && node != this);

                    //Remove the event listener
                    this.setDisable(true);
                    firstBool = false;
                    setMessage("Select 2nd square for rug.");
                    rugPlacement((int) Math.round(this.xLocation / SQUARE_WIDTH + 3), (int) Math.round(this.yLocation / SQUARE_HEIGHT + 3));
                } else {
                    this.setStyle("-fx-border-color: " + borderColour + ";" +
                            "-fx-border-width: 2px;" +
                            "-fx-background-color: transparent;");
                    //Remove all buttons except first and the selected button
                    rugPane.getChildren().removeIf(node
                            -> node instanceof Button && node != this && node != rugPane.getChildren().get(0));

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

    public class MosaicTile extends Arc {
        double centerX;
        double centerY;
        double start;
        double end;

        /**
         * Constructor for the mosaic tile graphics.
         *
         * @param centerX Specifies the center x location of the arc in the window.
         * @param centerY Specifies the center y location of the arc in the window.
         * @param start   Specifies where the 'tracing' of the arc starts (in terms of angle).
         * @param end     Specifies how big to trace the arc (e.g. 180 --> half circle).
         */
        MosaicTile(double centerX, double centerY, double start, double end) {
            super();
            this.centerX = centerX;
            this.centerY = centerY;
            this.start = start; //Where to start tracing the arc.
            this.end = end; //How big is the arc.


            //SETTING DIMENSIONS
            this.setCenterX(centerX);
            this.setCenterY(centerY);
            this.setRadiusX(SQUARE_WIDTH / 2);
            this.setRadiusY(SQUARE_HEIGHT / 2);
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
     * Class for triangle that serves as the symbol for asam.
     * Contains a constructor that specifies stylistic features and dimensions.
     */
    public class AsamSymbol extends Polygon {
        //Polygon triangle = new Polygon();
        double x;
        double y;
        double side = SQUARE_WIDTH;

        AsamSymbol(double x, double y) {
            super();
            this.x = x;
            this.y = y;

            this.getPoints().addAll(
                    0.0, -sqrtSide,
                    halfSide, sqrtSide,
                    -halfSide, sqrtSide
            );

            //SET STYLISTIC FEATURES
            this.setStroke(Color.web("#603300"));
            this.setFill(Color.web("#0099ff"));

            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
        }

        //SET DIMENSIONS
        double halfSide = SQUARE_WIDTH / 2.0;
        double sqrtSide = Math.sqrt(Math.pow(SQUARE_WIDTH, 2) - Math.pow(halfSide, 2)) / 2.0;

    }

    /**
     Calculates the winner of the game and calls the method to display the winner.
     */
    public void calcWin(){
        String tempString = theGame.generateGameString();
        String winnerStr = Character.toString(Marrakech.getWinner(tempString));

        if(winnerStr.equals("t")){ //Game is a tie
            setMessage("Game is a tie");
        }
        else{ //If not a tie display the winner.
            Integer indexWinner = colourLetters.indexOf(winnerStr)+1;
            winnerDisplay(indexWinner);
        }

    }

    /**
     * Displays the winner of the game by drawing gold border around their name.
     *
     * @param winIndex Number of the player who won.
     */
    public void winnerDisplay(int winIndex){
        String winnerName = nameArray.get(winIndex-1); //Fetch the name of the winner.
        setMessage(winnerName + " is the winner!");

        HBox backgroundStore = new HBox(100);
        Rectangle[] backgrounds = new Rectangle[numberPlayers];

        for (int i = 1; i <= numberPlayers; i++) {
            //Add background for each player using rectangle.
            backgrounds[i - 1] = new Rectangle(WINDOW_WIDTH / 7, WINDOW_HEIGHT / 7); // Specify width and height
            backgrounds[i - 1].setFill(Color.TRANSPARENT); // Set the fill color to white
            double cornerRadius = 20; // Radius for round edges of rectangle.
            backgrounds[i - 1].setArcWidth(cornerRadius);
            backgrounds[i - 1].setArcHeight(cornerRadius);
            backgroundStore.setAlignment(Pos.TOP_CENTER); //Setting alignment for rectangle.
            backgroundStore.getChildren().add(backgrounds[i - 1]);

            //CREATE GOLD BORDER IF WINNER
            if (i == winIndex) {
                backgrounds[i - 1].setStroke(Color.web("#f8a102")); //Set stroke colour to gold.
                backgrounds[i - 1].setStrokeWidth(5); //Set the stroke width to create a broad border
            }
        }

        //Add margin at top of rectangle to improve display.
        root.getChildren().add(backgroundStore);
        Insets margin = new Insets(30, 0, 0, 0); // Top, Right, Bottom, Left
        StackPane.setMargin(backgroundStore, margin);

    }

    /**
     * Adds city image that is used in some scenes. See reference for image in originality file.
     */
    public void backgroundCity() {
        ImageView viewCity = new ImageView();
        Image imageCity = new Image("file:./assets/city.png");
        viewCity.setImage(imageCity);
        StackPane.setAlignment(viewCity, Pos.BOTTOM_CENTER);
        root.getChildren().add(viewCity);
    }

    /**
     * Adds sky image that is used in some scenes. See reference for image in originality file.
     */
    public void backgroundSky() {
        //ADDING IMAGE
        ImageView viewSky = new ImageView();
        Image imageSky = new Image("file:./assets/moon.png");
        viewSky.setImage(imageSky);
        StackPane.setAlignment(viewSky, Pos.TOP_LEFT);
        root.getChildren().add(viewSky);
    }


    /**
     * This method specifies the graphics of the first screen and allows users to specify the number of players.
     * Method is mostly javaFX, with an emphasis on colour, style, and object alignment.
     */
    public void startScreen() {
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
            } else {
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
    public void assignOrder() {
        //Use shuffle to randomise order
        Collections.shuffle(nameArray);

        Text[] nameText = new Text[numberPlayers]; //Display text with names of players.
        StackPane[] namePane = new StackPane[numberPlayers]; //Stackpane to ensure proper display of text.
        HBox nameStore = new HBox(WINDOW_WIDTH / 10); //HBox to store stackpanes of names.

        //Iterate through number of players, displaying the name of each
        for (int i = 1; i <= numberPlayers; i++) {
            nameText[i - 1] = new Text();
            nameText[i - 1].setText(nameArray.get(i - 1));
            //Setting stylistic features of nameText:
            Font basicFont = new Font("Arial", 20);
            nameText[i - 1].setFont(basicFont);
            nameText[i - 1].setFill(Color.web("#000000"));

            namePane[i - 1] = new StackPane();
            namePane[i - 1].getChildren().add(nameText[i - 1]);

            StackPane.setAlignment(nameText[i - 1], Pos.CENTER);
            nameText[i - 1].setTextAlignment(TextAlignment.CENTER);
            StackPane.setMargin(nameText[i - 1], new Insets(110, 0, 0, 0));

            namePane[i - 1].setPrefWidth(150); // Preferred width
            namePane[i - 1].setMaxWidth(150);  // Maximum width
            StackPane.setAlignment(namePane[i - 1], Pos.CENTER);

            nameStore.getChildren().add(namePane[i - 1]);
            nameText[i - 1].setWrappingWidth(WINDOW_WIDTH / 10); // Set the wrapping width


        }
        nameStore.setAlignment(Pos.CENTER);
        root.getChildren().add(nameStore);

    }

    /**
     * Checks that names entered on the player screen is correct.
     *
     * @param nameField       textField that takes name input, parsed so that it may be disabled.
     * @param instructionText instruction text that changes depending on whether input correct or not.
     */
    public void checkNameRequirements(TextField nameField, Text instructionText) {
        //Checking that names are not repeated, and that number of names match number of players...
        if (nameArray.size() == numberPlayers) {
            //Checking that no repeats in set by putting into set and comparing sizes:
            HashSet<String> nameSet = new HashSet<>(nameArray);

            if (nameSet.size() != nameArray.size()) { //Less elements in the set means there are duplicates in the array
                //System.out.println("Duplicates");
                instructionText.setText("No duplicate names allowed...");
            } else {
                //System.out.println("Correct");
                boolean proceed = true;
                for (int i = 0; i < numberPlayers; i++) {
                    if (nameArray.get(i) == "") { //Check that each name at least 1 character long
                        proceed = false;
                    }
                }

                if (proceed) { //If proceed is true then can go to next stage of game.
                    nameField.setEditable(false); //Disable the textfield if correct input received
                    instructionText.setText("Press ENTER to start the game!");
                    assignOrder(); //Assign order of play.

                    //If enter is pressed game screen will appear.
                    nameField.setOnKeyPressed(event -> {
                        if (event.getCode().getName().equals("Enter")) {
                            if (numberPlayers == 1) {
                                opponentBoo = true;
                                opponentScreen();
                            } else {
                                opponentBoo = false;
                                changeSceneBoard(); //Changes scene to board display
                            }
                        }
                    });
                } else {
                    instructionText.setText("Names must be minimum one character.");
                }
            }

        } else {
            //System.out.println("Not same size.");
            instructionText.setText("Number of names must match number of players");
        }

    }

    /**
     * PLAYER SCREEN GRAPHICS.
     * This method creates the graphics for the screen following the welcome screen.
     * Allows users to specify the names of the players.
     */
    public void playerScreen() {

        //Setting up player section.
        HBox textStore = new HBox(170);
        Text[] playerText = new Text[numberPlayers];

        HBox backgroundStore = new HBox(70);
        Rectangle[] backgrounds = new Rectangle[numberPlayers];
        for (int i = 1; i <= numberPlayers; i++) {
            playerText[i - 1] = new Text();
            playerText[i - 1].setText("Player " + i);
            StackPane.setAlignment(playerText[i - 1], Pos.TOP_CENTER);
            Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
            playerText[i - 1].setFont(moroccanFont);

            //Set player colour:
            String colour = colourCodes[i - 1];
            playerText[i - 1].setFill(Color.web(colour));

            //Add background for each player using rectangle.
            backgrounds[i - 1] = new Rectangle(WINDOW_WIDTH / 6, WINDOW_HEIGHT / 5); // Specify width and height
            backgrounds[i - 1].setFill(Color.WHITE); // Set the fill color to white
            double cornerRadius = 20; // Radius for round edges of rectangle.
            backgrounds[i - 1].setArcWidth(cornerRadius);
            backgrounds[i - 1].setArcHeight(cornerRadius);

            backgroundStore.setAlignment(Pos.CENTER);
            backgroundStore.getChildren().add(backgrounds[i - 1]);

            textStore.setAlignment(Pos.CENTER);
            textStore.getChildren().add(playerText[i - 1]);
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
        nameField.setPrefWidth(WINDOW_WIDTH / 2.6);
        nameField.setMaxWidth(WINDOW_WIDTH / 2.6);
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
                    for (String name : names) {
                        nameArray.add(name.toUpperCase());
                    }
                    checkNameRequirements(nameField, instructionText); //Checking that valid names entered.
                }
            }
        });



    }


    /**
     * Start of game, game string generated to be translated in the Marrakech class.
     * @return Game string to be translated into Marrakech class.
     */
    public String initialStringGenerate() {
        String initialGameString = ""; //Game string starts wiht a P to denote player strings.
        for (int i = 0; i < numberPlayers; i++) {
            //Each player starts with 30 dirhams and 15 rugs. Initially all players are in the game.
            String individualString = "P" + colourLetters.get(i) + "030" + "15" + "i";
            initialGameString += individualString;
        }

        //Asam starts facing north in the middle of the board aka position (3,3)
        initialGameString += "A33NB";

        //Intially all the tiles are empty hence add 49 empty squares:
        for (int j = 0; j < 49; j++) {
            initialGameString += "n00";
        }
        return initialGameString;
    }

    /**
     * Displays the names of the players at the top of the main screen.
     */
    public void namesDisplay() {
        //Setting up player section.
        HBox namesStore = new HBox(100);
        Text[] nameText = new Text[numberPlayers];
        StackPane[] namePane = new StackPane[numberPlayers]; //Stackpane to ensure proper display of text.

        HBox backgroundStore = new HBox(100);
        Rectangle[] backgrounds = new Rectangle[numberPlayers];

        for (int i = 1; i <= numberPlayers; i++) {
            //Text that displays player name
            nameText[i - 1] = new Text();

            if (opponentBoo && i == 2) { //AI will always go second.
                nameText[i - 1].setText(i + ". COMPUTER"); //Display the names of players
            } else {
                nameText[i - 1].setText(i + ". " + nameArray.get(i - 1)); //Display the names of players
            }

            Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 25);
            nameText[i - 1].setFont(moroccanFont);
            String colour = colourCodes[i - 1]; //Display player colour
            nameText[i - 1].setFill(Color.web(colour));

            //Stackpane to organise alignment of text
            namePane[i - 1] = new StackPane();
            namePane[i - 1].getChildren().add(nameText[i - 1]);
            StackPane.setAlignment(nameText[i - 1], Pos.TOP_CENTER);
            nameText[i - 1].setTextAlignment(TextAlignment.CENTER);
            StackPane.setMargin(nameText[i - 1], new Insets(35, 0, 0, 0));
            nameText[i - 1].setWrappingWidth(WINDOW_WIDTH / 10); // Set the wrapping width
            namePane[i - 1].setPrefWidth(WINDOW_WIDTH / 7); // Preferred width
            namePane[i - 1].setMaxWidth(WINDOW_WIDTH / 7);  // Maximum width
            namesStore.setAlignment(Pos.CENTER);
            namesStore.getChildren().add(namePane[i - 1]);

            //Add background for each player using rectangle.
            backgrounds[i - 1] = new Rectangle(WINDOW_WIDTH / 7, WINDOW_HEIGHT / 7); // Specify width and height
            backgrounds[i - 1].setFill(Color.WHITE); // Set the fill color to white
            double cornerRadius = 20; // Radius for round edges of rectangle.
            backgrounds[i - 1].setArcWidth(cornerRadius);
            backgrounds[i - 1].setArcHeight(cornerRadius);
            backgroundStore.setAlignment(Pos.TOP_CENTER); //Setting alignment for rectangle.
            backgroundStore.getChildren().add(backgrounds[i - 1]);
        }

        //Add margin at top of rectangle to improve display.
        root.getChildren().add(backgroundStore);
        Insets margin = new Insets(30, 0, 0, 0); // Top, Right, Bottom, Left
        StackPane.setMargin(backgroundStore, margin);
        root.getChildren().add(namesStore);
        StackPane.setAlignment(namesStore, Pos.CENTER);


    }

    /**
     * Displays what round it is and which player's turn it is.
     * @param start Boolean which specifies whether it is the start of the game (true) or not (false).
     * @param currentPlayer Integer specifying who the current player string.
     */
    public void roundDisplay(boolean start, int currentPlayer) {
        //If at the start display the text, if not just update the text.
        if (start) {
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
        } else {
            roundText.setText(" Round " + roundCounter + "\n Player " + currentPlayer);
        }
    }

    /**
     * Sets the instruction for the messageText to display.This method called at different times throughout
     * the game.
     * @param instructionString String to be displayed.
     */
    public void setMessage(String instructionString) {
        messageText.setText(instructionString);
    }

    /**
     * Initially display the instruction message.
     * This text is altered in the setMessage method throughout the game depending on game state.
     */
    public void messageDisplay() {
        //Display the messageText (global variable)
        messageText.setText("Let us begin!");
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
        messageText.setFont(moroccanFont);
        messageText.setFill(Color.web("#ffffff"));
        messageText.setWrappingWidth(WINDOW_WIDTH / 6); // Set the wrapping width
        //Create vBox to ensure proper alignment
        VBox messageBox = new VBox(); //Container to display the text
        messageBox.setPrefWidth(WINDOW_WIDTH / 5); // Preferred width
        messageBox.setMaxWidth(WINDOW_WIDTH / 5);  // Maximum width
        messageBox.getChildren().add(messageText);
        messageBox.setAlignment(Pos.CENTER_LEFT);

        //Adding the messageBox to the root.
        StackPane.setAlignment(messageBox, Pos.CENTER_RIGHT);
        root.getChildren().add(messageBox);
    }

    /**
     * Displays asam's movement on the screen.
     *
     * @param directionChange Boolean value that indicates whether asam's direction has changed
     *                        during the movement.
     */
    public void movementDisplay(boolean directionChange) {
        //-3 since 3,3 on board is considered 0,0 on display.
        double newX = (theGame.asam.getX() - 3) * SQUARE_WIDTH;
        double newY = (theGame.asam.getY() - 3) * SQUARE_HEIGHT;

        asam.setTranslateX(newX);
        asam.setTranslateY(newY);

        //DETECT DIRECTION CHANGE
        if (directionChange) {
            asamRotateDisplay();
        }

    }
    
    /**
     *Method called by rugPlacement when it is a player's turn to choose the first square of their rug,
     * checks edge cases of rug placement.
     * @param coordinateBool Specifying whether buttons giving options to place the rug
     *                       LEFT, RIGHT, TOP, BOTTOM of asam should be created.
     * @param xRef The x location of asam relative to which the buttons are created.
     * @param yRef The y location of asam relative to which the buttons are created.
     * @return Upated boolean array specifying if buttons should be created (true) or not (False).
     */
    public boolean[] firstConditions(boolean[] coordinateBool, int xRef, int yRef){
        if(xRef==0){
            //Don't create left button
            coordinateBool[0] = false;
        } else if (xRef == 6) {
            //Don't create right button
            coordinateBool[1] = false;
        }

        if (yRef == 0) {
            //Don't create top button
            coordinateBool[2] = false;
        } else if (yRef == 6) {
            //Don't create bottom button
            coordinateBool[3] = false;
        }


        return coordinateBool;
    }

    /**
     * Method called by rugPlacement specifying additional conditions that check where asam is relative
     * to the potential rug when the second round of buttons are created.
     * @param coordinateBool Specifying whether buttons giving options to place the rug
     *      *                       LEFT, RIGHT, TOP, BOTTOM of the first square seleted should be created.
     * @param xRef The x location of the first square selected.
     * @param yRef The y location of the first square selected
     * @return Updated boolean array specifying which buttons should be created.
     */
    public boolean[] additionalConditions(boolean[] coordinateBool, int xRef, int yRef) {
        coordinateBool = firstConditions(coordinateBool, xRef, yRef);
        if (xRef - 1 == theGame.asam.getX()) { //Asam is to left
            coordinateBool[0] = false;
        } else if (xRef + 1 == theGame.asam.getX()) { //Asam is to right
            coordinateBool[1] = false;
        }

        if (yRef - 1 == theGame.asam.getY()) { //Asam is to the top
            coordinateBool[2] = false;
        } else if (yRef + 1 == theGame.asam.getY()) { //Asam is to the bottom
            coordinateBool[3] = false;
        }

        return coordinateBool;
    }

    /**
     * Method which initialises the stackpane in which the buttons that give the options of
     * where to place the rugs will be initialised. Also checks conditions for creating rug buttons.
     * @param xRef X location of object relative to which button must be created.
     * @param yRef Y location of object relative to which button must be created.
     */
    public void rugPlacement(int xRef, int yRef) {
        //Must set width of stackpane otherwise not able to click other buttons
        //Set width to the width of the board.
        rugPane.setPrefWidth(SQUARE_WIDTH * 7);
        rugPane.setMinWidth(SQUARE_WIDTH * 7);
        rugPane.setMaxWidth(SQUARE_WIDTH * 7);


        //Array of booleans - LEFT, RIGHT, TOP BOTTOM
        boolean[] coordinateBool = new boolean[4];

        for (int i = 0; i < 4; i++) {
            coordinateBool[i] = true;
        }

        if (!firstBool) {//Additional conditions to be checked if second button
            coordinateBool = additionalConditions(coordinateBool, xRef, yRef);
        } else {
            root.getChildren().add(rugPane);
            coordinateBool = firstConditions(coordinateBool, xRef, yRef);
        }

        makeRugButtons(coordinateBool, xRef, yRef);

    }

    /**
     * Speifies the graphics, locations and dimensions of the rug buttons.
     * @param coordinateBool Boolean array specifying which option buttons should be created.
     * @param xRef X location of object relative to which button created.
     * @param yRef Y location of object relative to which button created.
     */
    public void makeRugButtons(boolean[] coordinateBool, int xRef, int yRef) {
        //Make buttons and customise display
        String styleString = "-fx-border-width: 4px;" +
                "-fx-border-style: solid;" +
                "-fx-border-color: #ffffff;" +
                "-fx-background-color: transparent"; //Transparent since need to be able to see what is behind.

        //CREATING THE BUTTONS
        if (coordinateBool[0]) { //LEFT BUTTON
            TileButton buttonLeft = new TileButton((xRef - 4) * SQUARE_WIDTH, (yRef - 3) * SQUARE_HEIGHT);
            buttonLeft.setStyle(styleString);
            rugPane.getChildren().add(buttonLeft);
        }

        if (coordinateBool[1]) { //RIGHT BUTTON
            TileButton buttonRight = new TileButton((xRef - 2) * SQUARE_WIDTH, (yRef - 3) * SQUARE_HEIGHT);
            buttonRight.setStyle(styleString);
            rugPane.getChildren().add(buttonRight);
        }

        if (coordinateBool[2]) { //TOP BUTTON
            TileButton buttonTop = new TileButton((xRef - 3) * SQUARE_WIDTH, (yRef - 4) * SQUARE_HEIGHT);
            buttonTop.setStyle(styleString);
            rugPane.getChildren().add(buttonTop);
        }

        if (coordinateBool[3]) { //BOTTOM BUTTON
            TileButton buttonBot = new TileButton((xRef - 3) * SQUARE_WIDTH, (yRef - 2) * SQUARE_HEIGHT);
            buttonBot.setStyle(styleString);
            rugPane.getChildren().add(buttonBot);
        }

    }

    /**
     * Method triggering the start of the rugphase of ech player's round.
     * Calls the rug button methods above.
     */
    public void rugPhase() {
        //Once dice has been rolled and Asam has been moved, rug placement is next.
        //Wait one second then introduce rug placement
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            //CHECK IF COMPUTER OR PLAYER
            if (opponentBoo && playerCounter == 2) {

            } else {
                firstBool = true;
                //Call rug placement function with asam's coordinates as reference
                rugPlacement(theGame.asam.getX(), theGame.asam.getY());
                setMessage("Select 1st square for rug.");
            }

        });
        pause.play();
    }

    /**
     * Check if payment needs to be made after asam moves.
     * If it does call the processPayment method, otherwise proceed to the rug phase.
     * @param message String to which the payment notice is added and then diaplyed.
     */
    public void checkPayment(String message){
        //Check if asam landed on another player's rug
        int xCord = theGame.asam.getX();
        int yCord = theGame.asam.getY();
        String colourString = theGame.board.tiles[xCord][yCord].getColour().substring(0,1).toLowerCase();

        //Check if payment is needed
        if(colourString.equals("n")){ //Asam landed on empty tile
            message += "\nNo payment."; //Add to the message text
            if(opponentBoo && playerCounter==2){ //Check that player is not the computer.

            } else{
                rugPhase();
            }

        } else if (colourString.equals(colourLetters.get(playerCounter - 1))) { //Asam landed on current player's rug
            message += "\nNo payment."; //Add to the message text
            if (opponentBoo && playerCounter == 2) { //Check that player is not the computer.

            } else {
                rugPhase();
            }
        } else {
            message += "\nPayment made."; //Add to the message text
            //Generate game string
            String tempString = theGame.generateGameString();
            int payment = Marrakech.getPaymentAmount(tempString);
            int receiver = colourLetters.indexOf(colourString) + 1; //Get index of player whose rug landed on
            processPayment(payment, receiver);
        }
        setMessage(message);
    }

    /**
     * Process the payments between players if the player landed on another player's rug.
     */
    public void processPayment(int paymentAmt, int receiver){
        //CHECK IF THE PLAYER HAS COINS LEFT
        if (theGame.players[playerCounter - 1].dirhams - paymentAmt <= 0){ //Not enough coins
            theGame.players[receiver - 1].dirhams += theGame.players[playerCounter-1].dirhams;
            theGame.players[playerCounter - 1].dirhams = 0; //Pay what they have and then set number of coins to 0.

            theGame.players[playerCounter - 1].playerState = -1; //Player out of coins so out of game.

        } else { //Enough coins
            theGame.players[playerCounter - 1].dirhams -= paymentAmt; //Subtract from player paying
            theGame.players[receiver - 1].dirhams += paymentAmt; //Add to player receiving
            if (opponentBoo && playerCounter == 2) {

            } else {
                rugPhase();
            }
        }
        displayStats(false);//Display the payment

    }

    /**
     * Method called once dice has been rolled.
     *
     * @param number The result of the dice.
     */
    public void rolled(int number) {
        //Record Asam's previous direction
        Direction previousDirection = theGame.asam.getDirection();

        //MOVE ASAM IN THE DIRECTION SPECIFIED BY THE DICE
        String currentAsamString = theGame.asam.getString(); //Generate asam string
        String movedString = Marrakech.moveAssam(currentAsamString, number); //Move asam.
        theGame.asam.decodeAsamString(movedString);

        if (theGame.asam.getDirection().equals(previousDirection)) {
            movementDisplay(false); //Display does not need to account for change in direction
        } else {
            movementDisplay(true); //Display needs to account for change in direction.
        }

        //SPECIFY WHAT MESSAGE SHOULD SAY.
        String textInstructions = "";
        if (number == 1) {
            textInstructions = "Asam has moved " + number + " step.";
        } else {
            textInstructions = "Asam has moved " + number + " steps";
        }

        checkPayment(textInstructions); //Check if asam landed on other player's rug and if so process payment.

    }

    /**
     * Displays the dice button and calls the rollDie() method from the Marrakech class when button is pressed.
     */
    public void diceRoll() {
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

        diceBox.setPrefWidth(WINDOW_WIDTH / 5); // Preferred width
        diceBox.setMaxWidth(WINDOW_WIDTH / 5);  // Maximum width
        diceBox.getChildren().add(diceButton);
        diceBox.setAlignment(Pos.CENTER_RIGHT);

        StackPane.setAlignment(diceBox, Pos.CENTER_LEFT);
        root.getChildren().add(diceBox);

        //Setting up event handler for when button is clicked:
        diceButton.setOnAction(event -> {
            root.getChildren().remove(diceBox); //Immediately remove dice button from screen
            int rolledNumber = Marrakech.rollDie(); //Roll the dice.

            rolled(rolledNumber);
        });


    }

    /**
     * Initialises a new asam symbol and adds to the root.
     */
    public void asamDisplay() {
        asam = new AsamSymbol(0, 0); //Creating a new Asam symbol, initial rotation is 0, and position is in middle
        root.getChildren().add(asam);
    }

    /**
     * Rotates asam 90 degrees left or right from its current rotation
     */
    public void asamRotateDisplay() {

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

    /**
     * Checks that the rug placement is correct using the locations of the buttons clicked.
     * @param oneBut First button clicked.
     * @param twoBut Second button clicked.
     */
    public void checkPlacement(TileButton oneBut, TileButton twoBut) {
        //Create game string
        String gameString = theGame.generateGameString();

        int xFirst = (int) oneBut.xLocation / SQUARE_WIDTH + 3;
        int yFirst = (int) oneBut.yLocation / SQUARE_HEIGHT + 3;
        IntPair firstCoord = new IntPair(xFirst, yFirst);

        //x and y coordinate of second rug square selected
        int xSec = (int) twoBut.xLocation / SQUARE_WIDTH + 3;
        int ySec = (int) twoBut.yLocation / SQUARE_HEIGHT + 3;
        IntPair secondCoord = new IntPair(xSec, ySec);

        String rugString = theGame.generateRugString(colourLetters.get(playerCounter - 1), firstCoord, secondCoord);
        //DOUBLE CHECK THAT PLACEMENT IS VALID
        if (!Marrakech.isPlacementValid(gameString, rugString)) {
            setMessage("Invalid rug placement, try again");
            //Clear everything from rugpane:
            rugPane.getChildren().clear(); //Before displaying new buttons clear all previous
            root.getChildren().remove(rugPane); //Remove from root.
            firstBool = true;
            rugPlacement(theGame.asam.getX(), theGame.asam.getY()); //Give opportunity to try again
        } else {
            setMessage("Rug placement valid!");
            String newString = Marrakech.makePlacement(gameString, rugString);
            placeRug(newString);

        }


    }

    /**
     * Sets colours of tiles based on which rug is occupied.
     */
    public void setColour() {
        for (int i = 0; i < ROW; i++) { //Iterate through the columns
            for (int j = 0; j < ROW; j++) { //Iterate through the rows.
                String colourString = theGame.board.tiles[i][j].getColour().substring(0, 1).toLowerCase();
                if (!colourString.equals("n")) { //Checks that colour is not null and sets accordingly.
                    int index = colourLetters.indexOf(colourString);
                    tileRect[i][j].setFill(Color.web(colourCodes[index]));
                }

            }

        }
    }

    /**
     * Displays the board on the main screen using the TileRect and MosaicTile classes.
     * Through iteration calculates the location of each tile and mosaic piece.
     * @return Group to add to the root and thus be displayed.
     */
    public Group gameBoardDisplay() {
        backgroundCity(); //Add city in background of baord.

        Group group = new Group();
        final int DRAW_START_X = 100; //Where to start 'drawing'
        final int DRAW_START_Y = 100;

        //DRAW RECTANGLE TO SERVE AS BORDER:
        double margin = 10;
        Rectangle rectBorder = new Rectangle(SQUARE_WIDTH * COLUMN + margin, SQUARE_HEIGHT * ROW + margin); // Width and height of the rectangle
        // Set the position (X, Y) of the top-left corner of the rectangle
        rectBorder.setX(DRAW_START_X - margin / 2);
        rectBorder.setY(DRAW_START_Y - margin / 2);
        // Set the fill color of the rectangle
        rectBorder.setFill(Color.MOCCASIN);
        // Set the stroke color (border color) of the rectangle
        rectBorder.setStrokeWidth(3);
        rectBorder.setStroke(Color.web("#603300"));
        group.getChildren().add(rectBorder);


        //BOTTOM LEFT MOSAIC TILE
        MosaicTile mosaicCorner = new MosaicTile(SQUARE_WIDTH + DRAW_START_X / 2, SQUARE_HEIGHT * COLUMN + DRAW_START_Y, 90, 270);
        group.getChildren().add(mosaicCorner);

        //TOP RIGHT MOSAIC TILE
        MosaicTile otherCorner = new MosaicTile(SQUARE_WIDTH * ROW + DRAW_START_X, SQUARE_HEIGHT + DRAW_START_Y / 2, 180, -270);
        group.getChildren().add(otherCorner);

        //CREATING THE BOARD
        for (int i = 0; i < ROW; i++) { //Iterate through the columns.
            double x = SQUARE_WIDTH * i + DRAW_START_X; //Specifying the x location of the tile.

            if (i % 2 == 0 && i != 0) { //BOTTOM ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH * i + DRAW_START_X, SQUARE_HEIGHT * ROW + DRAW_START_Y, 0, -180);
                group.getChildren().add(mosaic);
            } else if (i % 2 == 1) { //TOP ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH * i + DRAW_START_X, SQUARE_HEIGHT + DRAW_START_Y / 2, 0, 180);
                group.getChildren().add(mosaic);
            }


            for (int j = 0; j < ROW; j++) { //Iterate through the rows.
                if (j % 2 == 0 && j != 0) { //RIGHT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH * COLUMN + DRAW_START_X, SQUARE_HEIGHT * j + DRAW_START_Y, 90, -180);
                    group.getChildren().add(mosaic);
                } else if (j % 2 == 1) { //LEFT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH + DRAW_START_X / 2, SQUARE_HEIGHT * j + DRAW_START_Y, 90, 180);
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
     * Place the rug onto the board and move to next round of he game.
     */
    public void placeRug(String gameString) {
        theGame.decodeMarrakech(gameString); //Decode string

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

    /**
     * Displays the player statistics such as the nummber of Dirhams and Rugs.
     *
     * @param initial If displaying for the first time then true, otherwise if just displaying updated sats then false.
     */
    public void displayStats(boolean initial) {
        //Similar to the player names, the dirham and rug display is organised using Hboxes and Stackpanes.
        HBox statStore = new HBox(100);
        StackPane[] statPane = new StackPane[numberPlayers];

        if (initial) { //If displaying for the first time, the visual aspects need to be set as well.
            for (int i = 1; i <= numberPlayers; i++) {
                //Text that displays player the statistics - dirhams and rugs.
                statText.add(new Text());

                //Getting the number of dirhams and rugs from the Marrakech class.
                int numberDirhams = theGame.getPlayers()[i - 1].dirhams;
                int numberRugs = theGame.getPlayers()[i - 1].rugs;

                statText.get(i - 1).setText(numberDirhams + " dirhams \n " + numberRugs + " rugs    "); //Display the names of players

                //Setting the visual aspect of the display
                Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 20);
                statText.get(i - 1).setFont(moroccanFont);
                String colour = colourCodes[i - 1]; //Display player colour
                statText.get(i - 1).setFill(Color.web(colour));

                //Stackpane to organise alignment of text
                statPane[i - 1] = new StackPane();
                statPane[i - 1].getChildren().add(statText.get(i - 1));
                StackPane.setAlignment(statText.get(i - 1), Pos.TOP_CENTER);
                statText.get(i - 1).setTextAlignment(TextAlignment.CENTER);
                StackPane.setMargin(statText.get(i - 1), new Insets(70, 0, 0, 0));
                statText.get(i - 1).setWrappingWidth(WINDOW_WIDTH / 10); // Set the wrapping width
                statPane[i - 1].setPrefWidth(WINDOW_WIDTH / 7); // Preferred width
                statPane[i - 1].setMaxWidth(WINDOW_WIDTH / 7);  // Maximum width
                statStore.setAlignment(Pos.CENTER);
                statStore.getChildren().add(statPane[i - 1]);
            }

            root.getChildren().add(statStore);
        } else { //Only update the text not the visual aspects.
            for (int j = 1; j <= numberPlayers; j++) {
                //Getting the number of dirhams and rugs from Marrakech class.
                int numberDirhams = theGame.getPlayers()[j - 1].dirhams;
                int numberRugs = theGame.getPlayers()[j - 1].rugs;
                if (theGame.players[j - 1].playerState == -1) { //Player is out of game
                    statText.get(j - 1).setText("OUT");
                    round(); //Move to next player
                } else {
                    //Display the number of coins and rugs
                    statText.get(j - 1).setText(numberDirhams + " dirhams \n " + numberRugs + " rugs    ");
                }

            }
        }
    }

    /**
     * Method which displays and specifies names of rotation buttons based on asam's current direction.
     * Add event listener to the button, and specifies the methods to be called when button clicked.
     */
    public void asamRotateButton() {
        //CREATING DICE BUTTON
        VBox directionBox = new VBox(); //Container to display dice button
        HBox horizontalBox = new HBox(10); //10 in between.

        Button[] directionButton = new Button[3]; //3 buttons in array 90 degrees left, right, or no rotation

        //Getting Asam's current direction
        Direction currentDirection = theGame.asam.getDirection();

        for (int i = 0; i < directionButton.length; i++) {
            //If i is 0 then left button, if 1 then no rotate button, if 2 then right rotate button
            //Find possible directions left and right to get button name.
            Direction buttonDirection = theGame.possibleDirections(currentDirection, i - 1);

            //ASSIGN BUTTON NAME
            directionButton[i] = new Button();
            switch (i) {
                case 0: //Left from Asam's current direction
                    directionButton[i].setText("LEFT");
                    break;
                case 1: //No direction change.
                    directionButton[i].setText("NONE");
                    break;
                case 2: //Right from Asam's current direction.
                    directionButton[i].setText("RIGHT");
                    break;
            }


            //Give the button an id so that it can be identified by the event listener.
            String id = Integer.toString(i - 1); //-1 if left, 0 if none, 1 if right
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
        directionBox.setPrefWidth(WINDOW_WIDTH / 5); // Preferred width
        directionBox.setMaxWidth(WINDOW_WIDTH / 5);  // Maximum width
        directionBox.setAlignment(Pos.CENTER_RIGHT);
        StackPane.setAlignment(directionBox, Pos.CENTER_LEFT);
        root.getChildren().add(directionBox);

        //Setting up event handler for when button is clicked:
        for (int j = 0; j < 3; j++) {
            directionButton[j].setOnAction(event -> {
                //REMOVE ALL BUTTONS ONCE CLICKED.
                horizontalBox.getChildren().clear();
                directionBox.getChildren().clear();
                root.getChildren().removeAll(directionBox);
                //-1 for right, 0 for middle, 1 for left.
                int rotationFactor = Integer.parseInt(((Button) event.getSource()).getId()); //Setting the message displayed to the id.

                //GENERATE ASAM STRING
                String currentAsamString = theGame.asam.getString();

                //Rotate and decode the new string.
                String newAsamString = Marrakech.rotateAssam(currentAsamString, rotationFactor * 90);
                theGame.asam.decodeAsamString(newAsamString);
                //Display this rotation
                asamRotateDisplay();
                //Once direction has been decided, display next stay of game: Rolling the dice
                diceRoll();
            });
        }

    }

    /**
     *If the player is playing against the computer, this method is called.
     * The computer goes thorugh its round of play, rotating asam, and placing the rug etc.
     */
    public void computerRound() {
        if (roundCounter == 1) {
            opponent = new AI(); //Initialise the opponent
        }

        //ROTATE ASAM
        String newAsamString = opponent.rotateAssamAI(theGame.asam.getString()); //First set direction of asam.
        theGame.asam.decodeAsamString(newAsamString);  //Decode Rotation
        asamRotateDisplay(); //Display rotation

        //Wait one second and then proceed.
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            computerNextPhase(); //Next phase called roling dice and placing rug.
        });
        pause.play();


    }

    /**
     * Go through next phase of opponent play ie. rolling the dice and then placing the rug.
     */

    public void computerNextPhase() {

        //ROLL THE DICE
        int rolledNum = opponent.rolling();
        rolled(rolledNum); //Display movement corresponding to rolling of dice.
        String gameString = theGame.generateGameString();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            //Generate rug string and place on board
            if (intBoo) { //Intelligent opponent rug placement
                String rug = opponent.smartPlace(gameString);
                String newString = AI.makePlacementAI(gameString, rug);
                placeRug(newString);
            } else { //Random opponent rug placement
                String rug = opponent.randomPlace(gameString);
                String newString = AI.makePlacementAI(gameString,rug);
                placeRug(newString);
            }
        });
        pause.play();

    }

    /**
     * Go through one player's turn (including rotation, movement, and placement phases.
     * Checks essential conditions that enable game to proceed.
     */
    public void round() {
        //CHECKS IF IT IS THE NEXT ROUND
        if (playerCounter == numberPlayers + 1) { //Back to first player
            roundCounter += 1;
            playerCounter = 1;
        }

        //CHECK THAT PLAYER IS NOT OUT OF GAME.
        if (theGame.players[playerCounter - 1].playerState == -1) {
            playerCounter += 1;  //Skip player if they are out of the game.
        }

        //DISPLAY ROUND AND WHICH PLAYER'S TURN IT IS.
        if (roundCounter == 1) {
            roundDisplay(true, playerCounter); //Start of game so visuals need to be set.
        } else {
            roundDisplay(false, playerCounter);
        }

        //MOVE THROUGH PHASES OF GAME
        String tempString = theGame.generateGameString();
        if (Marrakech.ifGameOver(tempString)) { //Game is over.
            setMessage("Game Over!");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                calcWin();
            });
            pause.play();

        } else { //Game is not over.
            setMessage("Set the direction of Asam");

            if (opponentBoo && playerCounter == 2) { //COMPUTER'S TURN
                computerRound();
            } else {
                asamRotateButton(); //Display Asam direction buttons.
            }
        }


    }

    /**
     * Decode the initial string to set the object values and the player 'stats',
     * the colours of the squares, and asam's position.
     */
    public void getInitial() {
        String initialString = initialStringGenerate();
        theGame = new Marrakech(initialString); //Initialise new game.
        displayStats(true);
        asamDisplay();
        round();

    }

    /**
     * Changes scene from start screen to player selection screen.
     */
    private void changeScenePlayer() {
        // Clear the screen by removing all child nodes from the root layout container
        root.getChildren().clear();
        backgroundSky();
        backgroundCity();
        playerScreen();

    }

    /**
     * Changes screen so that the one player can choose the type of computer opponent they are playing.
     */
    public void opponentScreen() {
        numberPlayers = 2; //TWO PLAYERS SINCE HUMAN AND AI

        root.getChildren().clear();
        backgroundCity();
        backgroundSky();

        //Adding text
        VBox wholeBox = new VBox(10);

        Text chooseText = new Text();
        chooseText.setText("Choose opponent type.");
        Font moroccanFont = Font.loadFont("file:./assets/King Malik Free Trial.ttf", 40);
        chooseText.setFont(moroccanFont);
        chooseText.setFill(Color.web("#ffffff"));
        wholeBox.getChildren().add(chooseText);
        wholeBox.setAlignment(javafx.geometry.Pos.CENTER);

        HBox oppBox = new HBox(10); //Container to display dice button
        Button randBut = new Button("RANDOM"); //Random opponent
        Button intBut = new Button("INTELLIGENT"); //Intelligent opponent

        oppBox.getChildren().addAll(randBut, intBut);
        oppBox.setAlignment(javafx.geometry.Pos.CENTER);

        String style = "-fx-font-size: 15px;" + "-fx-text-fill: white;" + "-fx-background-color: #f8a102;";
        randBut.setStyle(style);
        intBut.setStyle(style);

        //SETTING SIZE OF BUTTONS
        randBut.setPrefWidth(WINDOW_WIDTH / 6); // Preferred width
        randBut.setMaxWidth(WINDOW_WIDTH / 6);  // Maximum width
        intBut.setPrefWidth(WINDOW_WIDTH / 6); // Preferred width
        intBut.setMaxWidth(WINDOW_WIDTH / 6);  // Maximum width

        wholeBox.getChildren().add(oppBox);
        root.getChildren().add(wholeBox);

        //SET ON ACTION EVENTS FOR BUTTONS
        randBut.setOnAction(e -> {
            intBoo = false; //opponent is not intelligent (random).
            changeSceneBoard(); //move to stage of playing the game.
        });

        intBut.setOnAction(e -> {
            intBoo = true; //opponent is intelligent
            changeSceneBoard(); //move to stage of playing the game.
        });

    }

    /**
     * Changes scene to the main game after specifying number of players and the names of players.
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


    /**
     * Starts the game by displaying the visuals and the start screen and displaying the scene.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
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

    /**
     * Launch the game.
     */
    public static void main(String[] args){
        launch();
    }
}
