package SnakePackage;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Game extends Application {
    private Random  rng = new Random();
    private Snake mySnake;
    private Pane root = new Pane();
    private Stage window;
    private double time = 0;
    private Field fruit;
    AnimationTimer timer;
    private long acc = 0;
    private long last = 0;

    private Parent createContent(){
        root.setPrefSize(Constans.RIGHT_BORDER,Constans.DOWN_BORDER);
        mySnake = new Snake(root);
        fruit = new Field(Constans.FRUIT_STARTING_X,Constans.FRUIT_STARTING_Y,Constans.FRUIT_TYPE, Color.BLUE);
        root.getChildren().add(fruit);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        return root;

    }
    private void createEndScreen(){
        /*
        Pane pane = new Pane();
        Label label = new Label("YOU LOST!");
        pane.getChildren().add(label);
        Scene scene = new Scene(pane,Constans.RIGHT_BORDER,Constans.DOWN_BORDER);
        window.setScene(scene);
        */
         window.setTitle("YOU LOST XDXDXDXDXDXD");
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake");
        Scene scene = new Scene(createContent());
        window = primaryStage;

        scene.setOnKeyPressed(key -> {
            switch (key.getCode()){
                case LEFT:
                    mySnake.setDirection(Snake.Direction.LEFT);
                    break;
                case RIGHT:
                    mySnake.setDirection(Snake.Direction.RIGHT);
                    break;
                case UP:
                    mySnake.setDirection(Snake.Direction.UP);
                    break;
                case DOWN:
                    mySnake.setDirection(Snake.Direction.DOWN);
                    break;
                    /*
                case SPACE:
                    mySnake.move();
                    break;
                    */
                case S:
                    moveFruit();
            }
        });


        window.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void update(){
        if(last == 0) last = System.currentTimeMillis();
        acc += System.currentTimeMillis() - last;
        while (acc >= 1000./15.) {
            acc -= 1000./15.;
            if(fruit.samePosition(mySnake.getNextCoordinates())){
                mySnake.grow();
                moveFruit();
            }
            else {
                mySnake.move();
            }
            if (mySnake.dead) {
                System.out.println("Ty Jebany Debilu!");
                timer.stop();
                createEndScreen();
                break;
            }
            if (mySnake.outOfBounds()) {
                mySnake.dead = true;
                timer.stop();
                createEndScreen();
                break;
            }
        }
        last = System.currentTimeMillis();

    }
    private void moveFruit(){
       int newX = rng.nextInt((int)Constans.RIGHT_BORDER / Constans.FIELD_WEIGHT) * Constans.FIELD_WEIGHT;
       int newY = rng.nextInt((int)Constans.DOWN_BORDER / Constans.FIELD_HEIGHT) * Constans.FIELD_HEIGHT;
       fruit.setTranslateX(newX);
       fruit.setTranslateY(newY);

    }
}
