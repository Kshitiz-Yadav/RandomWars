// Class to implement the joysticks used for moving and shooting

package com.example.randomwars.gamePanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private final int outerCircleCenterPositionX;
    private final int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;

    private final int outerCircleRadius;
    private final int innerCircleRadius;

    private final Paint innerCirclePaint;
    private final Paint outerCirclePaint;

    private boolean isPressed = false;
    private double actuatorX;
    private double actuatorY;

    // Defining the positions, radius and paint of both inner and outer circle of the joysticks
    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.DKGRAY);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    // Drawing the joysticks on the screen
    public void draw(Canvas canvas) {
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    // Method to call the implementation of updating the position the inner circle position when it is moved
    public void update(){updateInnerCirclePosition();}

    // Method implementing the updating of position of the inner circle
    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    /*
        Getting the actuator in according to the touch of the joystick
        The actuator is a value between zero and one in the direction of the touch, i.e. a vector
        Multiplying this value with speed will give us the velocity in that direction
    */
    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    // Method to check if the joystick is pressed or not so that the actuator can be updated if it is
    public boolean isPressed(double touchPositionX, double touchPositionY) {
        double joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    // Method to return isPressed
    public boolean getIsPressed(){return isPressed;}

    // Method to set isPressed
    public void setIsPressed(boolean isPressed){this.isPressed = isPressed;}

    // Method to get x-axis value of actuator
    public double getActuatorX(){return actuatorX;}

    // MEthod to get y-axis value of actuator
    public double getActuatorY(){return actuatorY;}

    // Method to reset the actuator to default position once the touch event has ended
    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }
}