package com.example.pruebajuego;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

@SuppressLint("DrawAllocation")
public class Sprite {
	// direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 6;
    private GameView gameView;
    private Bitmap bmp;
    public int x = 0;
    public int y = 0;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;
   
    public Sprite(GameView gameView, Bitmap bmp) {
    	this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        //x = rnd.nextInt(gameView.getWidth() - width);
        y = gameView.getHeight()-height; 
        x = rnd.nextInt(gameView.getWidth() - width);
        
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;    
    }
    
    private void update() {
    	if (x >= gameView.getHeight() - height - xSpeed || x + xSpeed <= 0) {
    		Random rnd = new Random();
            x = gameView.getHeight()-height; 
            y = rnd.nextInt(gameView.getWidth() - width);
            
            xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
            ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
            //gameView.restaVida();
    	}

    	x = x + xSpeed;	 
    	
    	if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {	    
    		ySpeed = -ySpeed;	 
    	}	 
    	y = y + ySpeed;	
    	
    	/*if (x < gameView.getObjX()+gameView.getObjWidth() && y + height > gameView.getObjY())
    		gameView.finalizar();*/    	
    	
    	currentFrame = ++currentFrame % BMP_COLUMNS;
    }
   
    @SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
    	
    	//Comprobamos que no ha llegado al objetivo
    	update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
    
    private int getAnimationRow() {
    	double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }
    
    public boolean isCollision(float x2, float y2, int radio) {
        return x2 > x && x2 < x + width  + 20 + radio
             && y2 > y && y2 < y + height + 20 + radio;
  }
}  