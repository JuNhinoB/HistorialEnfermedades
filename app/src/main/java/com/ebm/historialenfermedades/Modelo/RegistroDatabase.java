package com.ebm.historialenfermedades.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegistroDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "registro.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "registros";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_PACIENTE = "paciente";
    public static final String COLUMN_DOCTOR = "doctor";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_MALESTAR = "malestar";
    public static final String COLUMN_IMAGEN = "imagen";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FECHA + " TEXT NOT NULL, " +
                    COLUMN_PACIENTE + " TEXT NOT NULL, " +
                    COLUMN_DOCTOR + " TEXT NOT NULL, " +
                    COLUMN_TELEFONO + " TEXT NOT NULL, " +
                    COLUMN_MALESTAR + " TEXT NOT NULL, " +
                    COLUMN_IMAGEN + " TEXT);";

    public RegistroDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
