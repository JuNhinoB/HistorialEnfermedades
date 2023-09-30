package com.ebm.historialenfermedades.Modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.List;

public class RegistroRepository {
    private SQLiteDatabase database;
    private RegistroDatabase registroDatabase;

    public RegistroRepository(Context context) {
        registroDatabase = new RegistroDatabase(context);
        open(); // se abre la base de datos en el constructor
    }

    public void open() throws SQLException {
        database = registroDatabase.getWritableDatabase();
    }

    public void close() {
        registroDatabase.close();
    }

    public long insert(Registro registro) {
        // se abre la base de datos antes de insertar
        open();

        ContentValues values = new ContentValues();
        values.put(RegistroDatabase.COLUMN_FECHA, registro.getFecha());
        values.put(RegistroDatabase.COLUMN_PACIENTE, registro.getPaciente());
        values.put(RegistroDatabase.COLUMN_DOCTOR, registro.getDoctor());
        values.put(RegistroDatabase.COLUMN_TELEFONO, registro.getTelefono());
        values.put(RegistroDatabase.COLUMN_MALESTAR, registro.getMalestar());
        values.put(RegistroDatabase.COLUMN_IMAGEN, registro.getImagen());

        long insertedId = database.insert(RegistroDatabase.TABLE_NAME, null, values);

        // se cierra la base de datos después de insertar
        close();

        return insertedId;
    }

    public LiveData<List<Registro>> getAllRegistros() {
        List<Registro> registros = new ArrayList<>();
        Cursor cursor = database.query(
                RegistroDatabase.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Registro registro = cursorToRegistro(cursor);
                registros.add(registro);
                cursor.moveToNext();
            }
            cursor.close();
        }

        // se cierra la base de datos después de consultar
        close();

        // se transforma la lista en LiveData para que pueda ser observada por ViewModel
        return new LiveData<List<Registro>>() {
            @Override
            protected void onActive() {
                super.onActive();
                setValue(registros);
            }
        };
    }

    @SuppressLint("Range")
    private Registro cursorToRegistro(Cursor cursor) {
        Registro registro = new Registro();
        registro.setId(cursor.getLong(cursor.getColumnIndex(RegistroDatabase.COLUMN_ID)));
        registro.setFecha(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_FECHA)));
        registro.setPaciente(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_PACIENTE)));
        registro.setDoctor(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_DOCTOR)));
        registro.setTelefono(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_TELEFONO)));
        registro.setMalestar(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_MALESTAR)));
        registro.setImagen(cursor.getString(cursor.getColumnIndex(RegistroDatabase.COLUMN_IMAGEN)));
        return registro;
    }
}

