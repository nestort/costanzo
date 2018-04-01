package com.trinidad.costanzo.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trinidad.costanzo.Actividades.DescripcionComidaActivity;
import com.trinidad.costanzo.Modelos.Producto;
import com.trinidad.costanzo.R;
import com.trinidad.costanzo.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CarroItemsListAdapter extends RecyclerView.Adapter<CarroItemsListAdapter.ViewHolder>{

    private Context context;

    private ArrayList<Producto> comidas;

    public CarroItemsListAdapter(Context context, ArrayList<Producto> comidas) {
        this.context = context;
        this.comidas = comidas;
    }

    @Override
    public CarroItemsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.carro_list_item,parent,false);
        return new CarroItemsListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CarroItemsListAdapter.ViewHolder holder, final int position) {


        holder.nameTextView.setText(comidas.get(position).getNombre());

        holder.priceTextView.setText(String.valueOf(comidas.get(position).getCosto()));

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescripcionComidaActivity.class);
                intent.putExtra(Constants.FROM_CART_EXTRA_KEY,true);
                intent.putExtra(Constants.SELECTED_ITEM_KEY, comidas.get(holder.getAdapterPosition()));
                intent.putExtra(Constants.SELECTED_ITEM_INDEX,position);
                context.startActivity(intent);
            }
        });

        holder.qtyTextView.setText(String.valueOf(comidas.get(position).getCount()));

        Picasso.with(context).load(comidas.get(position).getImagen()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTextView, priceTextView, qtyTextView;
        View rootView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.ProductoImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.TextViewNombreTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.CostoTextView);
            qtyTextView = (TextView) itemView.findViewById(R.id.qtyTextView);
            rootView = itemView;

        }
    }
}
