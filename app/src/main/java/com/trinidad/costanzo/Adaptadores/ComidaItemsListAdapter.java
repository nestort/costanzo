package com.trinidad.costanzo.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trinidad.costanzo.Actividades.DescripcionComidaActivity;
import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.R;
import com.trinidad.costanzo.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ComidaItemsListAdapter extends RecyclerView.Adapter<ComidaItemsListAdapter.ViewHolder>{

    private Context context;

    private ArrayList<Producto> comidas;

    public ComidaItemsListAdapter(Context context, ArrayList<Producto> comidas) {


        this.context = context;
        this.comidas = comidas;
    }

    @Override
    public ComidaItemsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.comida_items_list_item,parent,false);
        return new ComidaItemsListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ComidaItemsListAdapter.ViewHolder holder, int position) {


        holder.nombreTextView.setText(comidas.get(position).getNombre());
        holder.costoTextView.setText("$ "+ String.valueOf(comidas.get(position).getCosto()));

        Log.d("ComidaItemsListAdapter",comidas.get(holder.getAdapterPosition()).getCount()+"");
        if (comidas.get(holder.getAdapterPosition()).getCount()>0){
            holder.stockTextView.setText("Disponible");

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DescripcionComidaActivity.class);
                    intent.putExtra(Constants.SELECTED_ITEM_KEY, comidas.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }else {
            holder.stockTextView.setText("No disponible");
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Por el momento no disponible!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Picasso.with(context)
                .load(comidas.get(position).getImagen())
                .placeholder(ContextCompat.getDrawable(context,R.drawable.ic_hourglass_full_black_24dp))
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nombreTextView, costoTextView, stockTextView;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.ProductoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.TextViewNombreTextView);
            costoTextView = (TextView) itemView.findViewById(R.id.CostoTextView);
            stockTextView = (TextView) itemView.findViewById(R.id.StockTextView);
            rootView = itemView;

        }
    }
}
