package com.proyectoap.elias.Dto;

import javax.validation.constraints.NotBlank;


public class DtoProyecto {
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    
    //Constructor
    public DtoProyecto() {
    }

    public DtoProyecto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    //Getters & Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}