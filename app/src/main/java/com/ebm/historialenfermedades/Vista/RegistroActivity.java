package com.ebm.historialenfermedades.Vista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ebm.historialenfermedades.Modelo.Registro;
import com.ebm.historialenfermedades.R;
import com.ebm.historialenfermedades.VistaModelo.RegistroViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private RegistroViewModel viewModel;
    private EditText editTextFecha;
    private EditText editTextPaciente;
    private EditText editTextDoctor;
    private EditText editTextTelefono;
    private EditText editTextMalestar;
    private ImageView imageViewImagen;
    private Button buttonReceta;
    private Button buttonGuardar;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private static final int REQUEST_IMAGE_PICK = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        viewModel = new ViewModelProvider(this).get(RegistroViewModel.class);

        editTextFecha = findViewById(R.id.editTextFecha);
        editTextPaciente = findViewById(R.id.editTextPaciente);
        editTextDoctor = findViewById(R.id.editTextDoctor);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextMalestar = findViewById(R.id.editTextMalestar);
        imageViewImagen = findViewById(R.id.imagen);
        buttonReceta = findViewById(R.id.buttonReceta);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRegistro();
            }
        });
        // agrega TextWatchers para habilitar/deshabilitar el botón "guardar"
        addTextWatchers();
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        String selectedDate = dateFormat.format(calendar.getTime());
                        editTextFecha.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageViewImagen.setImageURI(selectedImageUri);
        }
    }
    private void saveRegistro() {
        String fecha = editTextFecha.getText().toString();
        String paciente = editTextPaciente.getText().toString();
        String doctor = editTextDoctor.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String malestar = editTextMalestar.getText().toString();

        if (isValidInput(fecha, paciente, doctor, telefono, malestar)) {
            String imagenUri = (selectedImageUri != null) ? selectedImageUri.toString() : "";

            Registro registro = new Registro(fecha, paciente, doctor, telefono, malestar, imagenUri);
            viewModel.insertRegistro(registro);

            Toast.makeText(this, "Registro guardado con exito", Toast.LENGTH_SHORT).show();

            // Redirigir a ListadoActivity
            Intent intent = new Intent(RegistroActivity.this, ListadoActivity.class);
            startActivity(intent);
            finish();
        }
    }



    private void addTextWatchers() {
        editTextFecha.addTextChangedListener(new MyTextWatcher(editTextFecha));
        editTextPaciente.addTextChangedListener(new MyTextWatcher(editTextPaciente));
        editTextDoctor.addTextChangedListener(new MyTextWatcher(editTextDoctor));
        editTextTelefono.addTextChangedListener(new MyTextWatcher(editTextTelefono));
        editTextMalestar.addTextChangedListener(new MyTextWatcher(editTextMalestar));
    }
    private boolean isValidInput(String fecha, String paciente, String doctor, String telefono, String malestar) {
        if (fecha.isEmpty() || paciente.isEmpty() || doctor.isEmpty() || telefono.isEmpty() || malestar.isEmpty()) {
           // Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return false;
        }

        
        return true;
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // habilita o deshabilita el botón "guardar" según la validez de los campos
            boolean isValid = isValidInput(
                    editTextFecha.getText().toString(),
                    editTextPaciente.getText().toString(),
                    editTextDoctor.getText().toString(),
                    editTextTelefono.getText().toString(),
                    editTextMalestar.getText().toString()
            );
            buttonGuardar.setEnabled(isValid);
        }
    }

    }
