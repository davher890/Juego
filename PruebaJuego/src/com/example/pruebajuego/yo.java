package com.example.pruebajuego;

import java.util.Random;
import java.util.WeakHashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class yo {
	
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 3;
    private GameView gameView;
    private Bitmap bmp;
    private int x = 0;
    private int y = 0;
    private int currentFrame = 0;
    private int width;
    private int height;
   
    public yo(GameView gameView, Bitmap bmp) {
    	this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        x = 0; 
        y = gameView.getHeight() - height;  
    }
   
    @SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
        int srcX = currentFrame * width;
        int srcY = 0 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }
    
    public int getX(){
    	return x; 
    }
    
    public int getY(){
    	return y; 
    }
    
    public int getWidth(){
    	return width; 
    }
    
    public int getHeight(){
    	return height; 
    }
}  