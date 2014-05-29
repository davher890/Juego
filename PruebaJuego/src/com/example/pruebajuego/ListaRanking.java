package com.example.pruebajuego;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaRanking extends Activity {
	
	ArrayList<Rank> rnks = new ArrayList<Rank>();
	ListView lstOpciones;
	AdaptadorTitulares adaptador;
	Context contexto;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.lista);
	    contexto = this;
	    
	    lstOpciones = (ListView)findViewById(R.id.LstOpciones);
	    llena_lista(); 
	}
	
	private void llena_lista(){
		
		RankingDB usdbh = new RankingDB(this, "DBRanking", null, 1);
		SQLiteDatabase db = usdbh.getWritableDatabase();
		 
		String sql = "SELECT * FROM Ranking";
		Cursor fila = db.rawQuery(sql, null);
		rnks.clear();    
		//Nos aseguramos de que existe al menos un registro
		if (fila.moveToFirst()) {
		//Recorremos el cursor hasta que no haya m�s registros
			do {
				String nombre = fila.getString(0);
			    String puntuacion = fila.getString(1);
			    				    		
			    Rank s = new Rank(nombre, puntuacion);
			    rnks.add(s);
			    
			} while(fila.moveToNext());
		}
		else {
		   	System.out.println("Error cursor. No hay ranking");	    	
		}
		    
		db.close();
		fila.close();
		
	    adaptador = new AdaptadorTitulares(this, rnks);
	    lstOpciones.setAdapter(adaptador);		
	}
	
	
	class AdaptadorTitulares extends ArrayAdapter<Rank> {
	    	
	  	Activity context;
	  	ArrayList<Rank> rnks;
	    	
	   	AdaptadorTitulares(Activity context, ArrayList<Rank> rnks) {
	   		super(context, R.layout.elementolista, rnks);
	   		this.context = context;
	   		this.rnks = rnks;
	   	}
	    	
	   	public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.elementolista, null);
				
			TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
			lblTitulo.setText(rnks.get(position).getNombre());
			
			TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText(""+rnks.get(position).getPuntuacion());
				
			return(item);
		}	    	
	}
	
	public class Rank {
		
		private String nombre;
		private String puntuacion;
		
		public Rank(String nombre, String puntuacion){
			this.puntuacion = puntuacion;
			this.nombre = nombre;
		}
		
		public String getNombre(){
			return nombre;		
		}

		public String getPuntuacion(){
			return puntuacion;		
		}
	}
}
