package com.example.pruebajuego;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Juego extends Activity {
	
	static TextView cords;
	ImageView player1, player2, player3;
	Chronometer crono;
	GameView Gameview;
	
	private boolean started = false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //Nuevo FrameLayout donde ira GameView(SurfaceView)
        FrameLayout Game = new FrameLayout(this);
        
        //Nueva instancia de clase GameView (SurfaceView) [Mapa]
        Gameview = new GameView (this);
        
        //Nuevo linear layout para encuadrar la ImageView del personaje
        LinearLayout GameWidgets = new LinearLayout (this);
        GameWidgets.setOrientation(LinearLayout.VERTICAL);//Horizontal
        GameWidgets.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        GameWidgets.setGravity(Gravity.LEFT);//Gravedad centrada
                
        player1 = new ImageView(this);
        player1.setBackgroundResource(R.drawable.vida);
        player1.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        player2 = new ImageView(this);
        player2.setBackgroundResource(R.drawable.vida);
        player2.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        player3 = new ImageView(this);
        player3.setBackgroundResource(R.drawable.vida);
        player3.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        //Cronometro
        crono = new Chronometer(this);
        crono.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        crono.setTextColor(Color.WHITE);
        crono.setTextSize(50);        
        crono.start();
        
        //Nuevo textview para mostrar las cordenadas
        cords = new TextView(this);
        cords.setText("0");
        cords.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        cords.setTextSize(5);
        cords.setTextColor(Color.WHITE);
        cords.setGravity(Gravity.RIGHT);
        
        //A�adimos el ImageView '_player' al LinearLayout 'GameWidgets'
        GameWidgets.addView(player1);
        GameWidgets.addView(player2);
        GameWidgets.addView(player3);
        //Añadimos el cronometro
        GameWidgets.addView(crono);
        
        //A�adimos la vista SurfaceView al FrameLayout principal
        Game.addView(Gameview);
        
        //Añadimos el textview encima del SurfaceView
        Game.addView(cords);
        //Añadimos el LinearLayout encima del SurfaceView
        Game.addView(GameWidgets);
        
        //Mostramos el primer FrameLayout con las otras vista añadidas
        setContentView(Game);        
        
        /*player1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gameview.cambiaGolpe(0);				
			}
		});
        
        player2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gameview.cambiaGolpe(50);				
			}
		});
        
        player3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Gameview.cambiaGolpe(100);				
			}
		}); */       
	}
	
	@Override
	protected void onDestroy (){
		List<Sprite> sprites = Gameview.GetSprites();
		for (int i=0; i<sprites.size(); i++) {
			sprites.remove(sprites.get(i));
		}
		Gameview.getGameLoopThread().setRunning(false);
		Intent resultData = new Intent();
        resultData.putExtra("tiempo", this.finalizaCrono());
        this.setResult(Activity.RESULT_OK, resultData);
	}
	
	public void actualizaPuntos(){
		cords.setText(String.valueOf((Integer.parseInt(cords.getText().toString()))+1));
	}
	
	public void inciaCrono(){
		 crono.setBase(SystemClock.elapsedRealtime());
		 started = true;
	}
	
	public String finalizaCrono(){
		
		 String tiempo = crono.getText().toString();
		 // Paramos el cronómetro  
         crono.stop();
         return tiempo;
	}
	
	public void restaVida(){
		if (player1.getVisibility() == View.VISIBLE){
			player1.setVisibility(View.INVISIBLE);
		}
		else {
			if (player2.getVisibility() == View.VISIBLE){
				player2.setVisibility(View.INVISIBLE);
			}
			else {
				if (player3.getVisibility() == View.VISIBLE);
					player3.setVisibility(View.INVISIBLE);
			}
		}
	}
}