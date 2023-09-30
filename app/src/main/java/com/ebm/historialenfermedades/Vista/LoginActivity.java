package com.ebm.historialenfermedades.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ebm.historialenfermedades.R;
import com.ebm.historialenfermedades.VistaModelo.LoginViewModel;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewEmailError;
    // Credenciales permitidas
    private final String validEmail = "jhon@mail.com";
    private final String validPassword = "77@1$";

    //  validar el formato de un email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.btnLogin);
       textViewEmailError = findViewById(R.id.textViewEmailError);

        // Deshabilitar el botón de inicio de sesión al inicio
        buttonLogin.setEnabled(false);

        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEmail();
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // verificar las credenciales ingresadas
                if (isValidEmail(email) &&  password.equals(validPassword)) {
                    Intent intent = new Intent(LoginActivity.this, ListadoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();



            // Habilitar el botón de inicio de sesión solo si las credenciales coinciden con las válidas
            if (isValidEmail(email) && password.equals(validPassword)) {
                buttonLogin.setEnabled(true);
            } else {
                buttonLogin.setEnabled(false);
            }
        }


    };

    private void validateEmail() {
        String email = editTextEmail.getText().toString();

        // muestra el mensaje de error si el email no es válido
        if (!isValidEmail(email)) {
            textViewEmailError.setText("Ingrese un email válido");
        } else {
            textViewEmailError.setText("");
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
    }
