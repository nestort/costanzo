package com.trinidad.costanzo.Actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.trinidad.costanzo.Carrito;
import com.trinidad.costanzo.Modelos.Pedido;
import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.R;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class ResumenActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView TWCosto;
    ListView lista;
    List<String> ListaResumenItems;
    //double saldoactual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        TWCosto = (TextView) findViewById(R.id.textViewCosto);
        TWCosto.setText("$" + (truncar(Carrito.getInstance().getTotal() + "", 2) + ""));
        resumen_productos();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Espere...");
        //saldo();
        //setTitle("Saldo actual : $" + saldoactual + "");
        progressDialog.setCancelable(false);


    }



    private void resumen_productos() {
        ListaResumenItems = new ArrayList<String>();

        for (Producto producto : Carrito.getInstance().getCarritoLista()) {
            double total = producto.getCosto() * producto.getCount();
            ListaResumenItems.add("Nombre: " + producto.getNombre() + " \nCantidad: " + producto.getCount() + "\nPrecio: $" + producto.getCosto() + "\nTotal: $" + total);

        }


        ArrayAdapter<String> adaptador;
        lista = (ListView) findViewById(R.id.listaview);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.test_list_item, ListaResumenItems);
        lista.setAdapter(adaptador);


    }

    public void TerminarPedido(View view) {
        Snackbar.make(view, "¡Pedido realizado!", Snackbar.LENGTH_LONG).show();
        Double costototal = (Carrito.getInstance().getTotal());
        //costototal <= saldoactual
        if (true) {
            //progressDialog.show();

            new Pedido.OrderBuilder()
                    .setFechaPedido(System.currentTimeMillis())
                    //.setCart(Carrito.getInstance())
                    .setFechaEntrega(null)
                    .setNoControl(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString())
                    .setCostoTotal(Carrito.getInstance().getTotal())
                    .setEstado("1.-Pedido")
                    .setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setOrderCompleteListener(new Pedido.OnOrderCompleteListener() {
                        @Override
                        public void onComplete() {
                           //Toast.makeText(ResumenActivity.this, "Pedido realizado con exito!", Toast.LENGTH_LONG).show();



                            //Toast toast = new Toast(ResumenActivity.this);
                            //usamos cualquier layout como Toast
                            //View toast_layout = getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.lytLayout));
                            //toast.setView(toast_layout);

                            //se podría definir el texto en el layout si es invariable pero lo hacemos programáticamente como ejemplo
                            //tenemos acceso a cualquier widget del layout del Toast
                            //TextView textView = (TextView) toast_layout.findViewById(R.id.toastMessage);
                            //textView.setText("Pedido realizado con exito!");
                            //toast.setDuration(Toast.LENGTH_LONG);
                            //toast.show();








                            progressDialog.cancel();
                            Carrito.getInstance().LimpiiarCarrito();
                            esperarYCerrar(3000);


                        }

                        @Override
                        public void onError(Pedido.OrderException e) {
                            Toast.makeText(ResumenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.cancel();
                        }
                    })
                    //.build()//public Pedido build() {return pedido;}
                    .build()//   public Pedido buid() {return  pedido;}

                    .place();


            //Snackbar.make(view, "Se puede hacer la compra", Snackbar.LENGTH_SHORT).show();

        } else {
            Snackbar.make(view, "No se puede hacer la compra\nSaldo insuficiente", Snackbar.LENGTH_LONG).show();

        }





        /*String address = addressET.getText().toString();
        if (address.isEmpty()){
            addressInputLayout.setError("Required Field");
            return;
        }

        String modeOfPayment = modeOfPayments[paymentsModeSpinner.getSelectedItemPosition()];

        progressDialog.show();

        new Order.OrderBuilder()
                .setLocality(localities[localitySpinner.getSelectedItemPosition()])
                .setAddress(address)
                .setCart(Carrito.getInstance())
                .setModeOfPayment(modeOfPayment)
                .setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setOrderCompleteListener(new Order.OnOrderCompleteListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(ResumenActivity.this, "Placed Your Order Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        Carrito.getInstance().LimpiiarCarrito();
                        finish();
                    }

                    @Override
                    public void onError(Order.OrderException e) {
                        Toast.makeText(ResumenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .place();*/
        //Toast.makeText(this, "Total = " + Carrito.getInstance().getTotal(), Toast.LENGTH_SHORT).show();

    }


    public String truncar(String valor, int cifras) {
        return new BigDecimal(valor)
                .setScale(cifras, RoundingMode.DOWN)
                .stripTrailingZeros()
                .toString();
    }
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                finish();
            }
        }, milisegundos);
    }

}
