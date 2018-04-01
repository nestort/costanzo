package com.trinidad.costanzo.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.trinidad.costanzo.Adaptadores.CarroItemsListAdapter;
import com.trinidad.costanzo.Carrito;
import com.trinidad.costanzo.R;
public class CarroActivity extends AppCompatActivity {
    RecyclerView carritoRecyclerView;
    CarroItemsListAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        carritoRecyclerView = (RecyclerView) findViewById(R.id.carritoRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        carritoRecyclerView.setLayoutManager(layoutManager);

        adaptador = new CarroItemsListAdapter(this, Carrito.getInstance().getCarritoLista());
        carritoRecyclerView.setAdapter(adaptador);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador.notifyDataSetChanged();
    }

    public void pedir(View view) {

        if (Carrito.getInstance().isEmpty()) {
            Snackbar.make(view, "El carrito esta vacio", Snackbar.LENGTH_SHORT).show();


        } else {
           // Toast.makeText(this, "Total = " + Carrito.getInstance().getTotal(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ResumenActivity.class));
            finish();
        }

    }
}
