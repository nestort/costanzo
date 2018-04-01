package com.trinidad.costanzo.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.trinidad.costanzo.Modelos.Historial;

import com.trinidad.costanzo.Modelos.ProductoOrden;

import com.trinidad.costanzo.R;

import java.util.ArrayList;
import java.util.Calendar;

public class HistorialListAdapter extends RecyclerView.Adapter<HistorialListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Historial> items;

    public HistorialListAdapter(Context context, ArrayList<Historial> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public HistorialListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.historial_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialListAdapter.ViewHolder holder, int position) {
        String EstadoPedido=items.get(position).getEstado();
        holder.fichaTV.setText("Ficha: "+items.get(position).getFicha());
        holder.estadoTV.setText("Estado: "+EstadoPedido);

        holder.fechaordenTV.setText(getDateStringFrom(items.get(position).getFecha())+" "+getTimeAsString(items.get(position).getFecha()));
        holder.totalTV.setText("Total del pedido= $" + String.valueOf(items.get(position).getTotal()));
        String listaproducto="";
        for (ProductoOrden item:items.get(position).getItems()){
            listaproducto=listaproducto+item.toString()+"\n";
        }
        holder.descripcionTV.setText(listaproducto);
        //// TODO: 19/11/17 Agregar a colors
        switch (EstadoPedido){
            case "1.-Pedido":
                holder.carta.setBackgroundColor(Color.rgb(22,226,222));
                break;
            case "2.-En preparacion":
                holder.carta.setBackgroundColor(Color.rgb(99,94,226));
                break;
            case "3.-Listo":
                holder.carta.setBackgroundColor(Color.rgb(161,228,163));
                break;
            case "4.-Entregado":
                holder.carta.setBackgroundColor(Color.rgb(178,94,226));
                break;

        }
        //holder.modeTV.setText(items.get(position).getMode());

        //long orderTimeInMillis = items.get(position).get;
        //long deliveryTimeInMillis = items.get(position).getDeliveryTimeStamp();

        //holder.orderTimeTV.setText(getTimeAsString(orderTimeInMillis));

        /*if (deliveryTimeInMillis != 0)
            holder.deliveryTimeTV.setText(String.format("Delivered at: %s", getTimeAsString(deliveryTimeInMillis)));
        else
            holder.deliveryTimeTV.setText(R.string.not_yet_delivered);

        holder.dateTV.setText(getDateStringFrom(orderTimeInMillis));*/

    }

    public String getDateStringFrom(long timeInMillis) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);



        String dayStr = String.valueOf(day);

        String Mes[] = {
                "Enero",
                "Ferero",
                "Marzo",
                "Abril",
                "MAyo",
                "Junio",
                "Julio",
                "Agosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
        };

        String monthStr = Mes[mes];



        if (dayStr.length() < 2){
            dayStr = "0".concat(dayStr);
        }

        return dayStr+" " + monthStr + " " + year;

    }

    private String getTimeAsString(long timeInMillis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String period, hourString, minuteString;

        if (hours == 0){
            hours = 12;
            period = "AM";
        }else if (hours > 12){
            hours = hours - 12;
            period = "PM";
        }else {
            period = "PM";
        }

        if (String.valueOf(hours).length()<2){
            hourString = "0" + hours;
        }else {
            hourString = String.valueOf(hours);
        }


        if (String.valueOf(minutes).length()<2){
            minuteString = "0" + minutes;
        }else {
            minuteString = String.valueOf(minutes);
        }

        return hourString + ":" + minuteString + " " + period;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fechaordenTV, descripcionTV, totalTV,fichaTV,estadoTV;
        View rootView;
        CardView carta;
        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            estadoTV=(TextView)itemView.findViewById(R.id.estado);
            fichaTV=(TextView)itemView.findViewById(R.id.ficha);
            fechaordenTV = (TextView) itemView.findViewById(R.id.fechaorden);
            descripcionTV= (TextView) itemView.findViewById(R.id.descripcion);
            totalTV = (TextView) itemView.findViewById(R.id.total);
            carta= (CardView) itemView.findViewById(R.id.CardviewProducto);

        }
    }
}
