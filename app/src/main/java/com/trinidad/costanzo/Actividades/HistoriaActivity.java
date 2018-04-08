package com.trinidad.costanzo.Actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.trinidad.costanzo.Adaptadores.HistorialListAdapter;

import com.trinidad.costanzo.Modelos.Historial;

import com.trinidad.costanzo.Modelos.ProductoOrden;

import com.trinidad.costanzo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HistoriaActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_history);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        refresh();

    }

    private void refresh() {


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.historyList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buscando sus ordenes");
        progressDialog.setCancelable(false);
        progressDialog.show();
         final ArrayList<Historial> items = new ArrayList<>();;
        FirebaseDatabase.getInstance().getReference().child("PedidosHistorial").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(HistoriaActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();


                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            Long Fecha= Long.parseLong(child.getKey());
                            double Total= Double.parseDouble(child.child("CostoTotal").getValue().toString());
                            String Estado=child.child("Estado").getValue().toString();
                            String Ficha=child.child("Ficha").getValue().toString();
                            ArrayList<ProductoOrden> comidas = new ArrayList<>();
                            for (DataSnapshot ordenItems : child.child("Orden").getChildren()){
                                String categoria = ordenItems.child("Categoria").getValue().toString();
                                int cantidad= Integer.parseInt(ordenItems.child("Cantidad").getValue().toString());
                                String Fid=ordenItems.child("Fid").getValue().toString();
                                String item=ordenItems.child("Item").getValue().toString();
                                comidas.add(new ProductoOrden(cantidad,categoria,Fid,item));

                            }
                           // items.add(new Historial(Total,Estado,Ficha,comidas,Fecha));
                            items.add(new Historial(Total,Estado,Ficha,comidas,Fecha));

                            //Toast.makeText(HistoriaActivity.this, comidas.get(0).toString(), Toast.LENGTH_SHORT).show();

                           /* if (child.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                //String mode = child.child("mode_of_payment").getValue().toString();
                                long timeOfOrder = Long.parseLong(child.child("timestamp").getValue().toString());
                                double total = Double.parseDouble(child.child("order").child("total").getValue().toString());

                                ArrayList<Producto> comidas = new ArrayList<>();
                                for (DataSnapshot orderItems : child.child("order").getChildren()){
                                    if (!orderItems.getKey().equals("total")){
                                        String category = orderItems.child("Categoria").getValue().toString();
                                        String fid = orderItems.child("fid").getValue().toString();
                                        String name = orderItems.child("item").getValue().toString();
                                        int qty = Integer.parseInt(orderItems.child("quantity").getValue().toString());
                                        comidas.add(new Producto(fid,name,category,qty));
                                    }
                                }
                                items.add(new Historial(total, comidas));
                            }*/
                        }
                        /*progressDialog.setMessage("Fetching delivered orders...");
                        FirebaseDatabase.getInstance().getReference().child("processed_orders")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            for (DataSnapshot child : dataSnapshot.getChildren()){
                                                if (child.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                                    String mode = child.child("mode_of_payment").getValue().toString();
                                                    long timeOfOrder = Long.parseLong(
                                                            child.child("ordered_at").getValue().toString()
                                                    );
                                                    long timeOfDelivery = Long.parseLong(
                                                            child.child("delivered_at").getValue().toString()
                                                    );
                                                    double total = Double.parseDouble(
                                                            child.child("order").child("total").getValue().toString()
                                                    );
                                                    ArrayList<Producto> comidas = new ArrayList<>();
                                                    for (DataSnapshot orderItems : child.child("order").getChildren()){
                                                        if (!orderItems.getKey().equals("total")){
                                                            String category = orderItems.child("category").getValue().toString();
                                                            String fid = orderItems.child("fid").getValue().toString();
                                                            String name = orderItems.child("item").getValue().toString();
                                                            int qty = Integer.parseInt(
                                                                    orderItems.child("quantity").getValue().toString()
                                                            );
                                                            comidas.add(new Producto(fid,name,category,qty));
                                                        }
                                                    }
                                                    items.add(new Historial(timeOfOrder,timeOfDelivery,mode,total, comidas));
                                                }
                                            }
                                        }catch (NullPointerException ignored){}
                                        HistorialListAdapter adapter = new HistorialListAdapter(HistoriaActivity.this,items);
                                        recyclerView.swapAdapter(adapter,true);
                                        progressDialog.dismiss();
                                        swipeRefreshLayout.setRefreshing(false);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(HistoriaActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });*/

                        //ordenamiento por estados
                        Collections.sort(items, new Comparator<Historial>() {
                            @Override
                            public int compare(Historial historial, Historial t1) {
                                return historial.getEstado().compareTo(t1.getEstado());
                            }
                        });
                        HistorialListAdapter adapter = new HistorialListAdapter(HistoriaActivity.this,items);
                        recyclerView.swapAdapter(adapter,true);
                        progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);

                        //progressDialog.dismiss();


                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(HistoriaActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });

    }
}
