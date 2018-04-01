package com.trinidad.costanzo.Modelos;

/**
 * Created by Trinidad on 18/11/17.
 * Resumen de producto, utilizada parael historial de pedidos
 *
 */

public class ProductoOrden {
    int Cantidad;
    String Categoria;
    String KeyProducto;
    String item;


    public ProductoOrden(int cantidad, String categoria, String keyProducto, String item) {
        Cantidad = cantidad;
        Categoria = categoria;
        KeyProducto = keyProducto;
        this.item = item;


    }



    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getKeyProducto() {
        return KeyProducto;
    }

    public void setKeyProducto(String keyProducto) {
        KeyProducto = keyProducto;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    public String toString(){

        return "Producto: "+item+"\nCantidad: "+Cantidad +"\nCategoria: "+Categoria+"\n";
    }
}
