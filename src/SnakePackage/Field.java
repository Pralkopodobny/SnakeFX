package SnakePackage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Field extends Rectangle {
    public String type;
    Field(double x, double y, double w, double h, String type, Color color){
        super(w,h,color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }
    Field(double x, double y,String type, Color color){
        super(Constans.FIELD_WEIGHT,Constans.FIELD_HEIGHT,color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }
    boolean samePosition(Pair<Double,Double> coordinates){
        return coordinates.getKey() == getTranslateX() && coordinates.getValue() == getTranslateY();
    }
}
//Ziemniak
//develop