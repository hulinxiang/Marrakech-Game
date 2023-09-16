package comp1110.ass2.gui;

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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Canvas canvas = new Canvas(VIEWER_WIDTH, VIEWER_HEIGHT);
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        //The variable denotes where the board starts
        final int STARTX = 30;
        final int STARTY = 30;
        //Variables denote the number of rows and columns
        final int COLUMN = 7;
        final int ROW = 7;
        final int SQUAREWIDTH = 30;
        final int SQUAREHEIGHT = 30;
        //Clear all
        graphicsContext.clearRect(0, 0, VIEWER_WIDTH, VIEWER_HEIGHT);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        graphicsContext.setStroke(Color.BLACK);
        //Draw the basic board
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                graphicsContext.strokeRect(STARTX + i * SQUAREHEIGHT, STARTY + j * SQUAREWIDTH, SQUAREWIDTH, SQUAREHEIGHT);
            }
        }
        //A variable indicates where the board string is in the game state string
        int boardStringPosition = -1;
        int assamStringPosition = -1;
        //Record where the assam string and board string is
        for (int i = 0; i < state.length(); i++) {
            if ('A' == (state.charAt(i))) {
                assamStringPosition = i;
            }
            if ('B' == (state.charAt(i))) {
                boardStringPosition = i;
            }
        }
        //Draw the assam on the board
        String assamString = state.substring(assamStringPosition, boardStringPosition);


        // FIXME Task 5: implement the simple state viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
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

        root.getChildren().add(controls);
        root.getChildren().add(canvas);
        makeControls();
//        displayState(new String("asdafag"));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
