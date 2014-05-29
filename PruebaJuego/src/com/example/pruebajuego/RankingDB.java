package com.example.pruebajuego;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RankingDB extends SQLiteOpenHelper{
	
	
	/*Tabla de Lugares */
	String sqlCreate1 = "CREATE TABLE Ranking (nombre     TEXT," +
											  "puntuacion TEXT)";	
	
	public RankingDB(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Ranking");
 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate1);
	}

	public void intPuntuacion(SQLiteDatabase db, String nombre, String puntuacion){
		
		ContentValues registro = new ContentValues();
	    registro.put("nombre", nombre);
	    registro.put("puntuacion", puntuacion);
	    if (db.insert("Ranking", null, registro) == -1){
	    	System.out.println("Error al insertar nuevo");
	    }
	}	
}