package com.ebm.historialenfermedades.Modelo;

public class Registro {
    private long id;
    private String fecha;
    private String paciente;
    private String doctor;
    private String telefono;
    private String malestar;
    private String imagen;

    public Registro() {
        // Constructor vacío
    }

    public Registro(String fecha, String paciente, String doctor, String telefono, String malestar, String imagen) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.doctor = doctor;
        this.telefono = telefono;
        this.malestar = malestar;
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMalestar() {
        return malestar;
    }

    public void setMalestar(String malestar) {
        this.malestar = malestar;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}