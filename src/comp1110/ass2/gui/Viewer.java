package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Canvas canvas = new Canvas(VIEWER_WIDTH, VIEWER_HEIGHT);
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;
    private Button button;

    //The variable denotes where the board starts
    final int START_X = 30;
    final int START_Y = 30;
    //Variables denote the number of rows and columns
    final int COLUMN = 7;
    final int ROW = 7;
    final int SQUARE_WIDTH = 30;
    final int SQUARE_HEIGHT = 30;
    final int TEXT_START_X = 500;
    final int TEXT_START_Y = 30;

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        Marrakech marrakech = new Marrakech(state);
        IntPair assamPosition = marrakech.asam.getMerchantPosition();
        Direction assamDirection = marrakech.asam.getDirection();
        Player[] players = marrakech.players;
        //Clear all
        graphicsContext.clearRect(0, 0, VIEWER_WIDTH, VIEWER_HEIGHT);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        graphicsContext.setStroke(Color.BLACK);
        //Draw the basic board
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                graphicsContext.strokeRect(START_X + i * SQUARE_HEIGHT, START_Y + j * SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_HEIGHT);
            }
        }
        //Draw the player information
        for(int i=0;i<players.length;i++){
            Text text = new Text("Player 1");
            text.setLayoutX(TEXT_START_X);
            text.setLayoutY(TEXT_START_Y+i*SQUARE_HEIGHT);
            graphicsContext.setFill(Player.getColorFromString(players[i].getColour()));
            graphicsContext.strokeRect(TEXT_START_X+SQUARE_WIDTH,TEXT_START_Y+i*SQUARE_HEIGHT,SQUARE_WIDTH, SQUARE_HEIGHT);
            Text text1=new Text("Owned dirhams:"+players[i].getCoins());
            text1.setLayoutX(TEXT_START_X+SQUARE_WIDTH);
            text1.setLayoutY(TEXT_START_Y+i*SQUARE_HEIGHT);
            Text text2=new Text("Remained rugs:"+players[i].getRugs());
            text2.setLayoutX(TEXT_START_X+2*SQUARE_WIDTH);
            text1.setLayoutY(TEXT_START_Y+i*SQUARE_HEIGHT);

            if(players[i].getPlayerState()==1){
                Text text3=new Text("In the game");
                text2.setLayoutX(TEXT_START_X+3*SQUARE_WIDTH);
                text1.setLayoutY(TEXT_START_Y+i*SQUARE_HEIGHT);
            }else {
                Text text3=new Text("Out of the game");
                text2.setLayoutX(TEXT_START_X+3*SQUARE_WIDTH);
                text1.setLayoutY(TEXT_START_Y+i*SQUARE_HEIGHT);
            }

        }
        Tile[][] tiles = marrakech.board.tiles;
        //Draw the color of the rug on the board.
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                graphicsContext.setFill(Tile.getColorFromString(tiles[i][j].getColour()));
                graphicsContext.fillRect(START_X + i * SQUARE_HEIGHT, START_Y + j * SQUARE_WIDTH, SQUARE_WIDTH, SQUARE_HEIGHT);
            }
        }
        //Draw the assam on the board
        //If assam heads to the west
        graphicsContext.setFill(Color.BLACK);
        if (Direction.WEST.equals(assamDirection)) {
            double x1 = START_X + (assamPosition.getY() + 1) * SQUARE_WIDTH;
            double x2 = x1;
            double x3 = x1 - SQUARE_WIDTH;
            double y1 = START_Y + (assamPosition.getX()) * SQUARE_HEIGHT;
            double y2 = START_Y + (assamPosition.getX() + 1) * SQUARE_HEIGHT;
            double y3 = (y1 + y2) / 2;
            graphicsContext.fillPolygon(
                    new double[]{x1,
                            x2,
                            x3},
                    new double[]{y1,
                            y2,
                            y3},
                    3);
        }
        //If assam heads to the east
        if (Direction.EAST.equals(assamDirection)) {
            double x1 = START_X + (assamPosition.getY()) * SQUARE_WIDTH;
            double x2 = x1;
            double x3 = x1 + SQUARE_WIDTH;
            double y1 = START_Y + (assamPosition.getX()) * SQUARE_HEIGHT;
            double y2 = START_Y + (assamPosition.getX() + 1) * SQUARE_HEIGHT;
            double y3 = (y1 + y2) / 2;
            graphicsContext.fillPolygon(
                    new double[]{x1,
                            x2,
                            x3},
                    new double[]{y1,
                            y2,
                            y3},
                    3);
        }
        //If assam heads to the north
        if (Direction.NORTH.equals(assamDirection)) {
            double x1 = START_X + (assamPosition.getY()) * SQUARE_WIDTH;
            double x2 = x1 + SQUARE_WIDTH;
            double x3 = (x1 + x2) / 2;
            double y1 = START_Y + (assamPosition.getX() + 1) * SQUARE_HEIGHT;
            double y2 = y1;
            double y3 = y2 - SQUARE_HEIGHT;
            graphicsContext.fillPolygon(
                    new double[]{x1,
                            x2,
                            x3},
                    new double[]{y1,
                            y2,
                            y3},
                    3);
        }
        //If assam heads to the south
        if (Direction.SOUTH.equals(assamDirection)) {
            double x1 = START_X + (assamPosition.getY()) * SQUARE_WIDTH;
            double x2 = x1 + SQUARE_WIDTH;
            double x3 = (x1 + x2) / 2;
            double y1 = START_Y + (assamPosition.getX()) * SQUARE_HEIGHT;
            double y2 = y1;
            double y3 = y2 + SQUARE_HEIGHT;
            graphicsContext.fillPolygon(
                    new double[]{x1,
                            x2,
                            x3},
                    new double[]{y1,
                            y2,
                            y3},
                    3);
        }
        // FIXME Task 5: implement the simple state viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        button = new Button("Refresh");
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().addAll(canvas, controls);
        makeControls();
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                displayState(boardTextField.getText());
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(111);
                displayState(boardTextField.getText());
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
