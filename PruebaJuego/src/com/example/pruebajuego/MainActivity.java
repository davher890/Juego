package com.example.pruebajuego;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends Activity {
	
	private Context context;
	EditText nombre;
	String tiempo;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_juego);
		this.context = this;
		
		Button play = (Button) findViewById(R.id.buttonJugar);
		Button ranking = (Button) findViewById(R.id.buttonRanking);
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(context,Juego.class);
				startActivityForResult(i, 1);				
			}
		});
		
		ranking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ListaRanking.class);
				startActivity(i);				
			}	
		});
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		System.out.println("RQC: "+requestCode);
		System.out.println("RSC: "+resultCode);
		
		if (resultCode == Activity.RESULT_OK && requestCode == 1){
			tiempo = data.getStringExtra("tiempo");
			
			final Dialog dialog = new Dialog(this); 
	        dialog.setContentView(R.layout.dialog_tiempo);
	        dialog.setTitle("Tu Record");
	        
	        TextView text = (TextView) dialog.findViewById(R.id.textTiempo);
	        text.setText(tiempo);
	        
	        nombre = (EditText) dialog.findViewById(R.id.editNombre);
	        
	        Button bguardar = (Button) dialog.findViewById(R.id.buttonGuardar);
	        
	        dialog.show();
	        
	        bguardar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!nombre.getText().toString().equals("")){
						
						RankingDB usdbh = new RankingDB(context, "DBRanking", null, 1);
						SQLiteDatabase db = usdbh.getWritableDatabase();
						
						usdbh.intPuntuacion(db, nombre.getText().toString(), tiempo);
					}
					dialog.cancel();
				}
			});
		}
	}
}
