package com.trinidad.costanzo.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trinidad.costanzo.Adaptadores.ComidaItemsListAdapter;
import com.trinidad.costanzo.Carrito;
import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.R;
import java.util.ArrayList;


public class ItemsListActivity extends AppCompatActivity implements Carrito.ActualizarCarriroListener {

    String selectedCategory;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        selectedCategory = getIntent().getStringExtra("categoria");
        getSupportActionBar().setTitle(selectedCategory);
        Carrito.getInstance().setActualizarCarriroListener(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando productos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Productos").child(selectedCategory).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Producto> comidas = new ArrayList<>();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for(DataSnapshot child : iterable){
                    comidas.add(
                            new Producto(
                                    child.getKey(),
                                    child.child("Nombre").getValue().toString(),
                                    selectedCategory,
                                    Double.parseDouble(child.child("Costo").getValue().toString()),
                                    child.child("Imagen").getValue().toString(),
                                    child.child("Descripcion").getValue().toString(),
                                    Integer.parseInt(child.child("Count").getValue().toString())

                            )
                    );

                    Log.d(child.getKey(),child.getValue().toString());
                }
                recyclerView.setAdapter(new ComidaItemsListAdapter(ItemsListActivity.this, comidas));
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            onUpdate(Carrito.getInstance().getCarritoLista().size());
        }catch (NullPointerException ignored){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal,menu);
        this.menu = menu;
        onUpdate(Carrito.getInstance().getCarritoLista().size());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cart:
                if (!Carrito.getInstance().isEmpty())
                    startActivity(new Intent(this,CarroActivity.class));
                else
                    Toast.makeText(this, "Carrito esta vacio", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    @Override
    public void onUpdate(int count) {

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        Carrito.setBadgeCount(this, icon, count);
    }
}
