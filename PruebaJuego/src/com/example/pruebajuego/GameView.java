package com.example.pruebajuego;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
	
	private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    
    //private yo mipos;
    
    private Bitmap bmpBlood;
    private Bitmap bmpMart;
    private Bitmap bmpBillar;
    private Bitmap bmpActual;
    private Bitmap bmpAnd;
    
    private Juego context;
    private int radio=0;
    
    private int puntos = 0;
    private int ronda = 1;
    
    private boolean restaVida = false;
   
    public GameView(Context context) {    	
    	super(context);
    	this.context = (Juego) context;
    	
        gameLoopThread = new GameLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
        	
        	@Override
            public void surfaceDestroyed(SurfaceHolder holder) {
        		boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                       try {
                             gameLoopThread.join();
                             retry = false;
                       } catch (InterruptedException e) {}
                }
        	}
        	
        	@Override
            public void surfaceCreated(SurfaceHolder holder) {
        		createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();   
        	}
        	
        	@Override
            public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
        		        		
        	}
        });
        bmpBlood  = BitmapFactory.decodeResource(getResources(), R.drawable.blood);
        bmpMart   = BitmapFactory.decodeResource(getResources(), R.drawable.martillo);
        bmpBillar = BitmapFactory.decodeResource(getResources(), R.drawable.billar);
        bmpAnd    = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        
        bmpActual = bmpMart;
        
    }
    
    private void createSprites() {
    	sprites.add(createSprite(R.drawable.image1));
        sprites.add(createSprite(R.drawable.image2));
        sprites.add(createSprite(R.drawable.image3));
        sprites.add(createSprite(R.drawable.image4));
        /*sprites.add(createSprite(R.drawable.image5));
        sprites.add(createSprite(R.drawable.image6));
        sprites.add(createSprite(R.drawable.image7));
        sprites.add(createSprite(R.drawable.image8));
        sprites.add(createSprite(R.drawable.image9));
        sprites.add(createSprite(R.drawable.image1));
        sprites.add(createSprite(R.drawable.image2));
        sprites.add(createSprite(R.drawable.image3));
        sprites.add(createSprite(R.drawable.image4));
        sprites.add(createSprite(R.drawable.image5));
        sprites.add(createSprite(R.drawable.image6));
        sprites.add(createSprite(R.drawable.image7));
        sprites.add(createSprite(R.drawable.image8));
        sprites.add(createSprite(R.drawable.image9));
        sprites.add(createSprite(R.drawable.calvo));
        sprites.add(createSprite(R.drawable.calvo));*/
        //mipos = new yo(this, BitmapFactory.decodeResource(getResources(),R.drawable.yoimg));
        
    }
    
    private Sprite createSprite(int resouce) {
    	Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);    
    }
    
    public List<Sprite> GetSprites(){
    	return sprites;
    }
    
	@Override
    protected void onDraw(Canvas canvas) {
    	canvas.drawColor(Color.BLACK);
        for (int i = temps.size() - 1; i >= 0; i--) {
               temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
               sprite.onDraw(canvas);
        }
        //mipos.onDraw(canvas);
    }
    
    public void restaVida(){
    	gameLoopThread.restaVida();
    }
    
	public void restaImgVida() {
		context.restaVida();
	}
    
    /*public int getObjX(){
    	return mipos.getX(); 
    }
    
    public int getObjY(){
    	return mipos.getY(); 
    }
    
    public int getObjWidth(){
    	return mipos.getWidth(); 
    }
    
    public int getObjHeight(){
    	return mipos.getHeight(); 
    }*/
    
    public void cambiaGolpe (int golpe){
    	
    	radio = golpe;
    	
    	if (golpe == 0 )
    		bmpActual = bmpMart;
    	else if (golpe == 50)
    		bmpActual = bmpAnd;
    	else
    		bmpActual = bmpBillar;
    }
    
    public void finalizar(){
    	try {
    		for (int i=0; i<sprites.size(); i++) {
    			sprites.remove(sprites.get(i));
    		}
    		gameLoopThread.setRunning(false);
	        Intent resultData = new Intent();
	        resultData.putExtra("tiempo", context.finalizaCrono());
	        context.setResult(Activity.RESULT_OK, resultData);
	        context.finish();
		} catch (Exception e) {            	
	    }	
    }
    
    public GameLoopThread getGameLoopThread(){
    	return gameLoopThread;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	if (sprites.size() == 0){
    		finalizar();           
    	}    	
    	
    	if (System.currentTimeMillis() - lastClick > 50) {                 
    		lastClick = System.currentTimeMillis();                 
    		float x = event.getX();                 
    		float y = event.getY();
    		
    		synchronized (getHolder()) {                        
    			for (int i = sprites.size() - 1; i >= 0; i--) {                               
    				Sprite sprite = sprites.get(i);                               
    				if (sprite.isCollision(x, y, radio)) {
    					
    					puntos ++;
    					context.actualizaPuntos();
    					
    					temps.add(new TempSprite(temps, this, x, y, bmpActual,  15));
    					
    					temps.add(new TempSprite(temps, this, x, y, bmpBlood, 25));
    					
    					sprites.remove(sprite);
    					sprites.add(createSprite(R.drawable.image9));
    					
    					if (puntos % 10 == 1){
    						for (int j=0; j<ronda; j++){
    							sprites.add(createSprite(R.drawable.image9));    							
    						}
    						ronda ++;
    					}
    				}                        
    			}
    		}
    	}          
    	return true;    
    }
}