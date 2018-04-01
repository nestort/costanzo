package com.trinidad.costanzo.Modelos;


import java.util.ArrayList;

public class Historial {

    private double total;
    private String estado;
    private String ficha;
    private long fecha;

    private ArrayList<ProductoOrden> items;

    public Historial(double total, String estado, String ficha, ArrayList<ProductoOrden> items, long Fecha) {
        this.estado=estado;
        this.ficha=ficha;
        this.total = total;
        this.items = items;
        this.fecha=Fecha;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public void setItems(ArrayList<ProductoOrden> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<ProductoOrden> getItems() {
        return items;
    }
}
