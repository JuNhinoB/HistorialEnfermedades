package com.ebm.historialenfermedades.VistaModelo;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoginEnabled = new MutableLiveData<>(false);
    private MutableLiveData<String> emailError = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<Boolean> getIsLoginEnabled() {
        return isLoginEnabled;
    }

    public void onEmailTextChanged(CharSequence text) {
        email.setValue(text.toString());
        checkLoginEnabled();
    }

    public void onPasswordTextChanged(CharSequence text) {
        password.setValue(text.toString());
        checkLoginEnabled();
    }

    private void checkLoginEnabled() {
        boolean isEnabled = !email.getValue().isEmpty() && !password.getValue().isEmpty();
        isLoginEnabled.setValue(isEnabled);
    }
    private void validateEmail(String emailValue) {
        if (isValidEmail(emailValue)) {
            emailError.setValue(null);
        } else {
            emailError.setValue("Ingrese un email válido");
        }
    }

    private boolean isValidEmail(String emailValue) {
        return Patterns.EMAIL_ADDRESS.matcher(emailValue).matches();
    }
}
