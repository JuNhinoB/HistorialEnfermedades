package com.ebm.historialenfermedades.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ebm.historialenfermedades.Modelo.Registro;
import com.ebm.historialenfermedades.R;
import com.ebm.historialenfermedades.VistaModelo.RegistroViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private RegistroViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private RegistroAdapter adapter;
    private EditText editTextSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        viewModel = new ViewModelProvider(this).get(RegistroViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegistroAdapter();
        recyclerView.setAdapter(adapter);

        fabAdd = findViewById(R.id.fabAdd);
        editTextSearch = findViewById(R.id.editTextSearch);

        // se configura el TextWatcher para el campo de búsqueda
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });

        viewModel.getAllRegistros().observe(this, new Observer<List<Registro>>() {
            @Override
            public void onChanged(List<Registro> registros) {

                adapter.setRegistros(registros);
            }
        });

        // se configura un OnClickListener para el FloatingActionButton
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad RegistroActivity al hacer clic en el botón
                Intent intent = new Intent(ListadoActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    // Adaptador para mostrar la lista de registros
    private class RegistroAdapter extends RecyclerView.Adapter<RegistroViewHolder> {
        private List<Registro> registros = new ArrayList<>();
        private List<Registro> registrosCompletos = new ArrayList<>();

        @NonNull
        @Override
        public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_registro, parent, false);
            return new RegistroViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
            Registro registro = registros.get(position);
            holder.bind(registro);
        }

        @Override
        public int getItemCount() {
            return registros.size();
        }


    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
        this.registrosCompletos.clear();
        this.registrosCompletos.addAll(registros);
        notifyDataSetChanged();
    }
        public Filter getFilter() {
            return registroFilter;
        }
        private Filter registroFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase().trim();
                List<Registro> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    // Si el campo de búsqueda está vacío, muestra la lista completa
                    filteredList.addAll(registrosCompletos);
                } else {
                    // Filtra la lista por coincidencias y asea por doctor, paciente y malestar
                    for (Registro registro : registrosCompletos) {
                        if (registro.getPaciente().toLowerCase().contains(query) ||
                                registro.getDoctor().toLowerCase().contains(query) ||
                                registro.getMalestar().toLowerCase().contains(query)) {
                            filteredList.add(registro);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                registros.clear();
                registros.addAll((List<Registro>) filterResults.values);
                notifyDataSetChanged();
            }
        };
}

// ViewHolder para un elemento de registro
private class RegistroViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewPaciente;
    private TextView textViewDoctor;
    private TextView textViewTelefono;
    private TextView textViewMalestar;


    public RegistroViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewPaciente = itemView.findViewById(R.id.textViewPaciente);
        textViewDoctor = itemView.findViewById(R.id.textViewDoctor);
        textViewTelefono = itemView.findViewById(R.id.textViewTel);
        textViewMalestar = itemView.findViewById(R.id.textViewMalestar);

    }

    public void bind(Registro registro) {

        textViewPaciente.setText("Paciente: " + registro.getPaciente());
        textViewDoctor.setText("Doctor: " + registro.getDoctor());
        textViewMalestar.setText("Malestar: " + registro.getMalestar());
        textViewTelefono.setText("Teléfono: " + registro.getTelefono());



    }
}
}