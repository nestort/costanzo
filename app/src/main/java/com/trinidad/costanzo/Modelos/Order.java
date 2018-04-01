package com.trinidad.costanzo.Modelos;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.trinidad.costanzo.Carrito;
import com.trinidad.costanzo.util.RandomIdGenerator;



public class Order {

    private String locality = "";
    private String address = "";
    private String modeOfPayment = "";
    private Carrito carrito;
    private String uid = "";
    private OnOrderCompleteListener onOrderCompleteListener;
    private String orderId;
    private long timeStamp;

    public interface OnOrderCompleteListener {
        void onComplete();
        void onError(OrderException e);
    }

    private Order() {
        this.orderId = RandomIdGenerator.newId();
        this.timeStamp = System.currentTimeMillis();
    }

    private void setOnOrderCompleteListener(OnOrderCompleteListener onOrderCompleteListener) {
        this.onOrderCompleteListener = onOrderCompleteListener;
    }

    private void setLocality(String locality) {
        this.locality = locality;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    private void setCart(Carrito carrito) {
        this.carrito = carrito;
    }

    private void setUid(String uid) {
        this.uid = uid;
    }

    public void place() {
        if (uid.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Invalid UID"));
            return;
        }
        if (modeOfPayment.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Invalid Payment Mode"));
            return;
        }
        if (address.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Address Empty"));
            return;
        }
        if (locality.isEmpty()){
            onOrderCompleteListener.onError(new OrderException("Locality Empty"));
            return;
        }
        if (carrito == null){
            onOrderCompleteListener.onError(new OrderException("Carrito is Empty"));
            return;
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child("orders")
                .child(orderId);
        database.child("userId").setValue(uid);
        database.child("locality").setValue(locality);
        database.child("address").setValue(address);
        database.child("timestamp").setValue(timeStamp);
        database.child("mode_of_payment").setValue(modeOfPayment);

        DatabaseReference ordersList = database.child("order");
        for (final Producto item : carrito.getCarritoLista()){
            DatabaseReference thisItem = ordersList.child(RandomIdGenerator.newId());
            thisItem.child("item").setValue(item.getNombre());
            thisItem.child("category").setValue(item.getCategoria());
            thisItem.child("fid").setValue(item.getFid());
            thisItem.child("quantity").setValue(item.getCount());
            FirebaseDatabase.getInstance().getReference().child("items")
                    .child(item.getCategoria())
                    .child(item.getFid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseDatabase.getInstance().getReference().child("items")
                            .child(item.getCategoria())
                            .child(item.getFid())
                            .child("count")
                            .setValue((Integer.parseInt(dataSnapshot.child("count").getValue().toString())) - item.getCount());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        ordersList.child("total").setValue(carrito.getTotal());
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uid)
                .child("pending_orders")
                .child(orderId)
                .setValue(timeStamp);
        onOrderCompleteListener.onComplete();
    }

    public static class OrderBuilder {

        Order order;

        public OrderBuilder() {
            order = new Order();
        }

        public OrderBuilder setLocality(String locality) {
            order.setLocality(locality);
            return this;
        }

        public OrderBuilder setAddress(String address) {
            order.setAddress(address);
            return this;
        }

        public OrderBuilder setCart(Carrito carrito) {
            order.setCart(carrito);
            return this;
        }

        public OrderBuilder setModeOfPayment(String modeOfPayment) {
            order.setModeOfPayment(modeOfPayment);
            return this;
        }


        public OrderBuilder setUserID(String uid) {
            order.setUid(uid);
            return this;
        }

        public Order build() {
            return order;
        }

        public OrderBuilder setOrderCompleteListener(OnOrderCompleteListener onOrderCompleteListener) {
            order.setOnOrderCompleteListener(onOrderCompleteListener);
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
}
