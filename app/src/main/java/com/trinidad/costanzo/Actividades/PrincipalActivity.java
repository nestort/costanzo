package com.trinidad.costanzo.Actividades;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trinidad.costanzo.Carrito;
import com.trinidad.costanzo.Fragmentos.TodosProductosFragment;
import com.trinidad.costanzo.R;
import com.trinidad.costanzo.Fragmentos.acercaFragment;
import com.trinidad.costanzo.Fragmentos.HomeFragment;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, acercaFragment.OnFragmentInteractionListener,HomeFragment.OnFragmentInteractionListener, TodosProductosFragment.OnFragmentInteractionListener {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;
    private ImageView fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        nameTextView=(TextView) header.findViewById(R.id.nombreTextView);
        emailTextView=(TextView) header.findViewById(R.id.correoMenuTextView);
        fotoPerfil=(ImageView) header.findViewById(R.id.imageView);

        //uidTextView=(TextView) findViewById(R.id.uidTextView);
        if(user!=null){
            String name=user.getDisplayName();
            String email=user.getEmail();
            String uid=user.getUid();
            Uri photoUri=user.getPhotoUrl();


            nameTextView.setText(name);
            emailTextView.setText(email);
            if(photoUri!=null){

                //Picasso.with(this).load(photoUri).into(fotoPerfil);
                //Picasso.with(this).load(photoUri).error(R.mipmap.ic_launcher_round).fit().into(fotoPerfil);
                Picasso.with(this).load(photoUri)
                        .resize(200, 200)
                        .into(fotoPerfil, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap imageBitmap = ((BitmapDrawable) fotoPerfil.getDrawable()).getBitmap();
                                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                imageDrawable.setCircular(true);
                                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                fotoPerfil.setImageDrawable(imageDrawable);
                            }
                            @Override
                            public void onError() {
                            }
                        });


            }

        }else{
            goLoginScreen();
        }

    }


    private void goLoginScreen() {
        Intent intent=new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Toast.makeText(this, "El carrito esta vacio", Toast.LENGTH_SHORT).show();
            return true;
        }*/
        //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        switch (item.getItemId()){
            case R.id.action_cart:
                if (!Carrito.getInstance().isEmpty())
                    startActivity(new Intent(this,CarroActivity.class));
                else
                    Toast.makeText(this, "El carrito esta vacio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                //return true;
                break;

        }
        return super.onOptionsItemSelected(item);
//        return true;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment=null;
        Boolean FragmentoSeleccionado=false;
        if (id == R.id.nav_inicio) {
            fragment=new HomeFragment();
            FragmentoSeleccionado=true;
        } else if (id == R.id.nav_productos) {
            fragment=new TodosProductosFragment();
            FragmentoSeleccionado=true;
        } else if (id == R.id.nav_populares) {
            Intent intent = new Intent(this,ItemsListActivity.class);
            intent.putExtra("categoria", "masVendidos");
            startActivity(intent);


        } else if (id == R.id.nav_acerca) {
            fragment=new acercaFragment();
            //launchAcerca(this.getCurrentFocus());
            FragmentoSeleccionado=true;

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "A más de 80 años endulzando el paladar de cuantos saborean los Chocolates y Dulces Costanzo, su calidad y lema se mantienen igual que antaño.\nDescarga ya la app de Costanzo:\nhttp://www.chocolatescostanzo.com");
            startActivity(Intent.createChooser(intent, "Share with"));

        } else if (id == R.id.nav_logout) {
            logout(this.getCurrentFocus());
        }

        //getFragmentManager().beginTransaction().replace(R.id.contenedor,fragment()).commit();
        if(FragmentoSeleccionado) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchAcerca (View view){
        startActivity(new Intent(this,AcercadeActivity.class));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}