package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    //The variable denotes where the board starts
    final int START_X = 30;
    final int START_Y = 30;
    //Variables denote the number of rows and columns
    final int COLUMN = 7;
    final int ROW = 7;
    //The information about squares which consist of the board
    final int SQUARE_WIDTH = 50;
    final int SQUARE_HEIGHT = 50;
    private final Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    /**
    Calculates the winner
     */
    public void calcWin(){


    }

    public class TileButton extends Button{
        double xLocation;
        double yLocation;

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

    public class MosaicTile extends Arc{
        double centerX;
        double centerY;
        double start;
        double end;

        MosaicTile(double centerX, double centerY, double start, double end){
            super();
            this.centerX = centerX;
            this.centerY = centerY;
            this.start = start; //Where to start tracing the arc.
            this.end = end; //Where to end the arc.


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



    @Override
    public void start(Stage stage) throws Exception {

        final int DRAW_START_X =100; //Where to start 'drawing'
        final int DRAW_START_Y=100;

//         FIXME Task 7 and 15
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
        this.root.getChildren().add(rectBorder);


        //BOTTOM LEFT MOSAIC TILE
        MosaicTile mosaicCorner = new MosaicTile(SQUARE_WIDTH+DRAW_START_X/2, SQUARE_HEIGHT*COLUMN+DRAW_START_Y, 90, 270);
        this.root.getChildren().add(mosaicCorner);

        //TOP RIGHT MOSAIC TILE
        MosaicTile otherCorner = new MosaicTile(SQUARE_WIDTH*ROW+DRAW_START_X, SQUARE_HEIGHT+DRAW_START_Y/2, 180, -270);
        this.root.getChildren().add(otherCorner);

        //CREATING THE BOARD
        for(int i = 0; i<ROW; i++){
            double x = SQUARE_WIDTH * i + DRAW_START_X;

            if(i%2==0 && i!=0){ //BOTTOM ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*i+DRAW_START_X, SQUARE_HEIGHT*ROW+DRAW_START_Y, 0, -180);
                this.root.getChildren().add(mosaic);
            }
            else if(i%2==1) { //TOP ROW MOSAIC TILES
                MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*i+DRAW_START_X, SQUARE_HEIGHT+DRAW_START_Y/2, 0, 180);
                this.root.getChildren().add(mosaic);
            }


            for(int j = 0; j<ROW; j++){
                if(j%2==0 && j!=0){ //RIGHT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH*COLUMN+DRAW_START_X, SQUARE_HEIGHT*j+DRAW_START_Y, 90, -180);
                    this.root.getChildren().add(mosaic);
                }
                else if(j%2==1) { //LEFT MOSAIC TILES
                    MosaicTile mosaic = new MosaicTile(SQUARE_WIDTH+DRAW_START_X/2, SQUARE_HEIGHT*j+DRAW_START_Y, 90, 180);
                    this.root.getChildren().add(mosaic);
                }

                double y = SQUARE_HEIGHT * j + 100;
                TileButton tile1 = new TileButton(x, y);
                this.root.getChildren().add(tile1);

            }

        }

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
