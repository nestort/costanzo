package com.trinidad.costanzo.Actividades;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trinidad.costanzo.Carrito;

import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.R;
import com.trinidad.costanzo.util.Constants;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DescripcionComidaActivity extends AppCompatActivity implements Carrito.ActualizarCarriroListener {

    Producto item;
    Spinner qtySpinner;
    TextView itemNameTextView, itemDescTextView;
    ImageView itemImageView;

    Button addButton,removeButton,updateButton;

    boolean comingFromCart = false;

    ArrayList<Producto> items;
    int postion;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_comida);

        item = (Producto) getIntent().getSerializableExtra(Constants.SELECTED_ITEM_KEY);

        comingFromCart = getIntent().getBooleanExtra(Constants.FROM_CART_EXTRA_KEY,false);

        Carrito.getInstance().setActualizarCarriroListener(this);

        qtySpinner = (Spinner) findViewById(R.id.SpinnerCountProducto);
        itemNameTextView = (TextView) findViewById(R.id.TextViewNombreTextView);
        itemDescTextView = (TextView) findViewById(R.id.TextViewDescripcionProducto);
        itemImageView = (ImageView) findViewById(R.id.ImageViewComida);

        addButton = (Button) findViewById(R.id.BttAgregarProducto);
        removeButton = (Button) findViewById(R.id.BttEliminarProducto);
        updateButton = (Button) findViewById(R.id.updateCartButton);

        itemNameTextView.setText(item.getNombre());
        itemDescTextView.setText(item.getDescripcion());

        Integer qtySpinnerArr[];
        if (item.getCount() > 10) {
            qtySpinnerArr = new Integer[10];
            for (int i = 0; i < 10; i++){
                qtySpinnerArr[i]=i+1;
            }
        }else {
            qtySpinnerArr = new Integer[item.getCount()];
            for (int i = 0; i < item.getCount(); i++){
                qtySpinnerArr[i]=i+1;
            }
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,qtySpinnerArr);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        qtySpinner.setAdapter(arrayAdapter);

        Picasso.with(this).load(item.getImagen()).into(itemImageView);

        if (comingFromCart){
            addButton.setVisibility(View.GONE);
            removeButton.setVisibility(View.VISIBLE);
            qtySpinner.setSelection(item.getCount()-1);
            updateButton.setVisibility(View.VISIBLE);
            items = Carrito.getInstance().getCarritoLista();
            postion = getIntent().getIntExtra(Constants.SELECTED_ITEM_INDEX,-1);
        }else {
            removeButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
        }



    }

    public void addItemToCart(View view) {
        int qty = (int) qtySpinner.getSelectedItem();
        item.setCount(qty);
        Carrito.getInstance().AgregarACarrito(item);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.cart_menu,menu);
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
                    Toast.makeText(this, "El carrito esta vacio!", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void EliminarProductoCarrito(View view) {
        items.remove(postion);
        Carrito.getInstance().ActualizarCarrito(items);
        finish();
    }

    public void AutualizarProductoCarrito(View view) {
        item.setCount((int)qtySpinner.getSelectedItem());
        items.remove(postion);
        items.add(postion,item);
        Carrito.getInstance().ActualizarCarrito(items);
        finish();
    }

    @Override
    public void onUpdate(int count) {

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        Carrito.setBadgeCount(this, icon, count);
    }
}
