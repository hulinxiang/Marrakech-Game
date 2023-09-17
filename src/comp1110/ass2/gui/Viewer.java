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
import javafx.scene.input.KeyCode;
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
    private Button button;


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

        if (boardStringPosition == -1 || assamStringPosition == -1) {
            throw new RuntimeException("Invalid String Format");
        }
        String assamString = state.substring(assamStringPosition, boardStringPosition);
        if(assamString.length()!=4){
            throw new RuntimeException("Invalid String Format");
        }
        String boardString = state.substring(boardStringPosition);
        //Start with i=1.
        //Draw the color of the rug on the board.
        for(int i =1;i<boardString.length();i+=3){
            if(boardString.charAt(i)=='n'){
                continue;
            }
            switch (boardString.charAt(i)) {
                case 'c' -> graphicsContext.setFill(Color.CYAN);
                case 'y' -> graphicsContext.setFill(Color.YELLOW);
                case 'r' -> graphicsContext.setFill(Color.RED);
                case 'p' -> graphicsContext.setFill(Color.PURPLE);
                default -> throw new RuntimeException("Invalid game string format");
            }
            graphicsContext.fillRect(STARTX + SQUAREWIDTH * (boardString.charAt(i+2)-'0'),
                    STARTY + SQUAREWIDTH *(boardString.charAt(i+1)-'0'), SQUAREWIDTH, SQUAREHEIGHT);
        }

        //Draw the assam on the board
        //If assam heads to the west
        graphicsContext.setFill(Color.BLACK);
        if (assamString.charAt(3) == 'W') {
            double x1=STARTX+(assamString.charAt(2)-'0' + 1) * SQUAREWIDTH;
            double x2=x1;
            double x3=x1-SQUAREWIDTH;
            double y1=STARTY + (assamString.charAt(1)-'0') * SQUAREHEIGHT;
            double y2=STARTY + (assamString.charAt(1)-'0' + 1) * SQUAREHEIGHT;
            double y3=(y1+y2)/2;
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
        if(assamString.charAt(3)=='E'){
            double x1=STARTX+(assamString.charAt(2)-'0') * SQUAREWIDTH;
            double x2=x1;
            double x3=x1+SQUAREWIDTH;
            double y1=STARTY + (assamString.charAt(1)-'0') * SQUAREHEIGHT;
            double y2=STARTY + (assamString.charAt(1)-'0' + 1) * SQUAREHEIGHT;
            double y3=(y1+y2)/2;
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
        if(assamString.charAt(3)=='N'){
            double x1=STARTX+(assamString.charAt(2)-'0') * SQUAREWIDTH;
            double x2=x1+SQUAREWIDTH;
            double x3=(x1+x2)/2;
            double y1=STARTY + (assamString.charAt(1)-'0' + 1) * SQUAREHEIGHT;
            double y2=y1;
            double y3=y2-SQUAREHEIGHT;
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
        if(assamString.charAt(3)=='S'){
            double x1=STARTX+(assamString.charAt(2)-'0') * SQUAREWIDTH;
            double x2=x1+SQUAREWIDTH;
            double x3=(x1+x2)/2;
            double y1=STARTY + (assamString.charAt(1)-'0') * SQUAREHEIGHT;
            double y2=y1;
            double y3=y2+SQUAREHEIGHT;
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
        makeControls();
        root.getChildren().addAll(controls, canvas);
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode=event.getCode();
            if(keyCode.equals(KeyCode.ENTER)){
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
