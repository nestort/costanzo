package com.trinidad.costanzo.Modelos;

import java.io.Serializable;

/**
 * Created by Trinidad on 01/04/18.
 */

public class Producto implements Serializable {
    private String Nombre;
    private String Categoria;
    private String Descripcion;
    private String Imagen;
    private double Costo;
    private int Disponiblidad;
    private String Fid;
    private int Count;

    public Producto(String key, String nombre, String selectedCategory, double costo, String imagen, String descripcion, int count) {
        Fid=key;
        Nombre=nombre;
        Categoria=selectedCategory;
        Costo=costo;
        Imagen=imagen;
        Descripcion=descripcion;
        Count=count;
    }

    public Producto(String fid, String name, String category, int qty) {
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double costo) {
        Costo = costo;
    }

    public int getDisponiblidad() {
        return Disponiblidad;
    }

    public void setDisponiblidad(int disponiblidad) {
        Disponiblidad = disponiblidad;
    }

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
