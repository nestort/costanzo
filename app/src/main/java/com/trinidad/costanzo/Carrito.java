package com.trinidad.costanzo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;


import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.util.BadgeDrawable;


import java.util.ArrayList;


public class Carrito {

    private ArrayList<Producto> ListaCarrito;
    private static Carrito carrito;
    private ActualizarCarriroListener actualizarCarriroListener;
    private Carrito(){
        ListaCarrito = new ArrayList<>();
    }

    public ArrayList<Producto> getCarritoLista(){
        return ListaCarrito;
    }
    public void ActualizarCarrito(ArrayList<Producto> newList){
        ListaCarrito = newList;
        actualizarCarriroListener.onUpdate(ListaCarrito.size());
    }

    public void AgregarACarrito(Producto item){
        ListaCarrito.add(item);
        actualizarCarriroListener.onUpdate(ListaCarrito.size());
    }

    public static Carrito getInstance(){
        if(carrito == null){
            carrito = new Carrito();
        }
        return carrito;
    }

    public void setActualizarCarriroListener(ActualizarCarriroListener actualizarCarriroListener){
        this.actualizarCarriroListener = actualizarCarriroListener;
    }

    public double getTotal(){
        double total = 0;
        for (Producto item : ListaCarrito){
            total += item.getCosto() * item.getCount();
        }
        return total;
    }

    public void LimpiiarCarrito() {
        carrito = new Carrito();
        actualizarCarriroListener.onUpdate(ListaCarrito.size());
    }

    public boolean isEmpty() {
        return ListaCarrito.isEmpty();
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public interface ActualizarCarriroListener {
        void onUpdate(int count);
    }
}
