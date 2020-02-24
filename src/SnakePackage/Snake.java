package SnakePackage;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.sql.Struct;
import java.util.LinkedList;

public class Snake {
    public boolean dead = false;
    private Field head;
    private LinkedList<Field> body;
    private Pane gameRoot;
    private Direction direction = Direction.NIL;
    private Direction lastMoveDirection = Direction.NIL;
    Snake(Pane root){
        gameRoot = root;
        body = new LinkedList<>();
        head = new Field(Constans.STARTING_X,Constans.STARTING_Y,Constans.SNAKE_TYPE, Color.GREEN);
        body.add(head);
        root.getChildren().add(head);
    }
    private void moveY(int value){
        Field temp = body.getFirst();
        temp.setTranslateY(head.getTranslateY() + value);
        temp.setTranslateX(head.getTranslateX());
        body.removeFirst();
        body.add(temp);
        head = temp;
    }
    private void moveX(int value){
        Field temp = body.getFirst();
        temp.setTranslateX(head.getTranslateX() + value);
        temp.setTranslateY(head.getTranslateY());
        body.removeFirst();
        body.add(temp);
        head = temp;
    }
    private void moveLeft(){
        moveX(-Constans.FIELD_WEIGHT);
        //System.out.println(getHeadCoordinates());
        lastMoveDirection = Direction.LEFT;
    }
    private void moveRight(){
        moveX(Constans.FIELD_WEIGHT);
        ///System.out.println(getHeadCoordinates());
        lastMoveDirection = Direction.RIGHT;
    }
    private void moveUp(){
        moveY(-Constans.FIELD_HEIGHT);
        //System.out.println(getHeadCoordinates());
        lastMoveDirection = Direction.UP;
    }
    private void moveDown(){
        moveY(Constans.FIELD_HEIGHT);
        //System.out.println(getHeadCoordinates());
        lastMoveDirection = Direction.DOWN;
    }
    public void move(){
        switch (direction) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case NIL:
                break;
        }
        eatenHimself();
    }
    public void setDirection(Direction direction){
        switch (direction) {
            case LEFT:
                if (lastMoveDirection != Direction.RIGHT) this.direction = Direction.LEFT;
                break;
            case RIGHT:
                if (lastMoveDirection != Direction.LEFT) this.direction = Direction.RIGHT;
                break;
            case UP:
                if (lastMoveDirection != Direction.DOWN) this.direction = Direction.UP;
                break;
            case DOWN:
                if (lastMoveDirection != Direction.UP) this.direction = Direction.DOWN;
                break;
            case NIL:
                throw new IllegalArgumentException("Impossible");
        }
    }
    public void grow(){
        if (direction == Direction.NIL) return;
        head = new Field(head.getTranslateX() + Direction.toXOffset(direction),head.getTranslateY() + Direction.toYOffset(direction),Constans.SNAKE_TYPE, Color.GREEN);
        body.add(head);
        gameRoot.getChildren().add(head);
        System.out.println("doopa");
    }


    public Pair<Double,Double> getNextCoordinates(){
        return new Pair<>(head.getTranslateX() + Direction.toXOffset(direction),head.getTranslateY() + Direction.toYOffset(direction));
    }
    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NIL;
        public static double toXOffset(Direction direction){
            switch (direction){
                case LEFT:
                    return -Constans.FIELD_WEIGHT;
                case RIGHT:
                    return Constans.FIELD_WEIGHT;
                default:
                    return 0;
            }
        }
        public static double toYOffset(Direction direction){
            switch (direction){
                case UP:
                    return -Constans.FIELD_HEIGHT;
                case DOWN:
                    return Constans.FIELD_HEIGHT;
                default:
                    return 0;
            }
        }
    }
    public boolean outOfBounds(){
        if(head.getTranslateX() < Constans.LEFT_BORDER || head.getTranslateX() > Constans.RIGHT_BORDER) return true;
        return head.getTranslateY() < Constans.UPPER_BORDER || head.getTranslateY() > Constans.DOWN_BORDER;
    }
    public boolean eatenHimself(){
        dead = false;
        body.forEach(element -> {
            if (element.getTranslateX() == head.getTranslateX() && element.getTranslateY() == head.getTranslateY() && element != head){
                dead = true;
                return;
            }
        });
        return dead;
    }
}