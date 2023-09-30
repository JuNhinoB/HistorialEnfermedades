package com.ebm.historialenfermedades.VistaModelo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ebm.historialenfermedades.Modelo.Registro;
import com.ebm.historialenfermedades.Modelo.RegistroRepository;

import java.util.List;

public class RegistroViewModel extends AndroidViewModel {
    private RegistroRepository repository;
    private LiveData<List<Registro>> allRegistros;

    public RegistroViewModel(Application application) {
        super(application);
        repository = new RegistroRepository(application);
        allRegistros = repository.getAllRegistros();
    }

    public LiveData<List<Registro>> getAllRegistros() {
        return allRegistros;
    }

    public void insertRegistro(Registro registro) {
        repository.insert(registro);
    }
}
