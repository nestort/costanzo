package com.trinidad.costanzo.Modelos;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.trinidad.costanzo.Carrito;

import com.trinidad.costanzo.util.RandomIdGenerator;

import java.util.ArrayList;


/**
 * Created by Trinidad on 16/10/17.
 */

public class Pedido {
    private Long FechaPedido;
    private Long FechaEntrega;
    private String Id;
    private String UId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String NoControl;
    private ArrayList<Producto> ListaPedido;
    private int NoFicha;
    private Double CostoTotal;
    private String Estado;//Realizado,Preparando,Para entrega,Entregado
    private Pedido.OnOrderCompleteListener onOrderCompleteListener;


    private void setOnOrderCompleteListener(Pedido.OnOrderCompleteListener onOrderCompleteListener) {
        this.onOrderCompleteListener = onOrderCompleteListener;
    }
    public Long getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(Long fechaPedido) {
        FechaPedido = fechaPedido;
    }

    public Long getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Long fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNoControl() {
        return NoControl;
    }

    public void setNoControl(String noControl) {
        NoControl = noControl;
    }

    public ArrayList<Producto> getListaPedido() {
        return ListaPedido;
    }

    public void setListaPedido(ArrayList<Producto> listaPedido) {
        ListaPedido = listaPedido;
    }

    public int getNoFicha() {
        return NoFicha;
    }

    public void setNoFicha(int noFicha) {
        NoFicha = noFicha;
    }

    public Double getCostoTotal() {
        return CostoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        CostoTotal = costoTotal;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    Pedido(){
        this.Id = RandomIdGenerator.newId();
        this.FechaPedido = System.currentTimeMillis();
    }

    public interface OnOrderCompleteListener {
        void onComplete();
        void onError(Pedido.OrderException e);
    }

    public static class OrderBuilder {

        Pedido pedido;

        public OrderBuilder() {
            pedido = new Pedido();
        }
        public Pedido build() {return pedido;}
        public Pedido buid() {return  pedido;}

        public Pedido.OrderBuilder setEstado(String estado) {
            pedido.setEstado(estado);
            return this;
        }
        public Pedido.OrderBuilder setFechaPedido(Long fecha){
            pedido.setFechaPedido(fecha);
            return this;
        }
        public Pedido.OrderBuilder setFechaEntrega(Long fecha){
            pedido.setFechaPedido(fecha);
            return this;
        }
        public Pedido.OrderBuilder setNoControl(String NoControl){
            pedido.setNoControl(NoControl);
            return this;
        }
        public Pedido.OrderBuilder setCostoTotal(double costototal){
            pedido.setCostoTotal(costototal);
            return this;
        }




        public Pedido.OrderBuilder setUserID(String uid) {
            pedido.setId(uid);
            return this;
        }




        public Pedido.OrderBuilder setOrderCompleteListener(Pedido.OnOrderCompleteListener onOrderCompleteListener) {
            pedido.setOnOrderCompleteListener(onOrderCompleteListener);
            return this;
        }


    }

    public class OrderException {
        private String message;
        OrderException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }




    public void place() {


        /*Todo validaciones
        if (uid.isEmpty()){
            onOrderCompleteListener.onError(new Order.OrderException("Invalid UID"));
            return;
        }
        if (modeOfPayment.isEmpty()){
            onOrderCompleteListener.onError(new Order.OrderException("Invalid Payment Mode"));
            return;
        }
        if (address.isEmpty()){
            onOrderCompleteListener.onError(new Order.OrderException("Address Empty"));
            return;
        }
        if (locality.isEmpty()){
            onOrderCompleteListener.onError(new Order.OrderException("Locality Empty"));
            return;
        }
        if (carrito == null){
            onOrderCompleteListener.onError(new Order.OrderException("Carrito is Empty"));
            return;
        }*/

        setFechaPedido( System.currentTimeMillis());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("PedidosHistorial").child(UId).child(FechaPedido+"");



        database.child("NoControl").setValue(NoControl);
        database.child("CostoTotal").setValue(CostoTotal);
        database.child("Estado").setValue(Estado);
        database.child("Ficha").setValue(RandomIdGenerator.newId(3));
        DatabaseReference ordersList = database.child("Orden");
        for (final Producto item : Carrito.getInstance().getCarritoLista()){//Producto producto:Carrito.getInstance().getCarritoLista()
            DatabaseReference thisItem = ordersList.child(RandomIdGenerator.newId());
            thisItem.child("Item").setValue(item.getNombre());
            thisItem.child("Categoria").setValue(item.getCategoria());
            thisItem.child("Fid").setValue(item.getFid());
            thisItem.child("Cantidad").setValue(item.getCount());

            FirebaseDatabase.getInstance().getReference().child("Productos")
                    .child(item.getCategoria())
                    .child(item.getFid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FirebaseDatabase.getInstance().getReference().child("Productos")
                                    .child(item.getCategoria())
                                    .child(item.getFid())
                                    .child("Count")
                                    .setValue((Integer.parseInt(dataSnapshot.child("Count").getValue().toString())) - item.getCount());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            ActualizarSaldo(getCostoTotal());
            //onOrderCompleteListener.onComplete();
        }

        onOrderCompleteListener.onComplete();
}
public void ActualizarSaldo(final double Saldo_A_Restar){

    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Saldo");

    db.addListenerForSingleValueEvent(new ValueEventListener(){

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            /*FirebaseDatabase.getInstance().getReference().child("Saldo")
                    .child(UId)
                    .setValue(Double.parseDouble(dataSnapshot.child(UId).getValue().toString()) - Saldo_A_Restar);
            Log.d("obtenido",dataSnapshot.child(UId).getValue().toString());
            return;*/

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}


}


