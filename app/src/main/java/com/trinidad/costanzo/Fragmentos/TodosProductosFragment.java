package com.trinidad.costanzo.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.trinidad.costanzo.Actividades.ItemsListActivity;
import com.trinidad.costanzo.Modelos.Categoria;
import com.trinidad.costanzo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodosProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodosProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodosProductosFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private RecyclerView rvCategorias;
    private ArrayList <Categoria> categorias;
    GridLayout mainGrid;

    public TodosProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter aa.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodosProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodosProductosFragment newInstance(String param1, String param2) {
        TodosProductosFragment fragment = new TodosProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_todos_productos, container, false);

//       mainGrid = (GridLayout) v.indViewById(R.id.mainGrid);

        //Set Event
        //CardView button=(CardView) v.findViewById(R.id.Caramelos);


/*
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
//////////////////////
            Bundle data = new Bundle();//create bundle instance
            data.putString("key_value", "String to pass");//put string to pass with a key value
            this.setArguments(data);//Set bundle data to fragment

//////////7////////////77
            cardView.setOnClickListener(this);
            //setToggleEvent(mainGrid);
        }*/

       // Intent intent = new Intent(getActivity(),ItemsListActivity.class);
        //intent.putExtra("categoria", "masVendidos");
        //startActivity(intent);

        CardView b = (CardView) v.findViewById(R.id.caramelos);
        b.setOnClickListener(this);

        CardView chiclosos = (CardView) v.findViewById(R.id.chiclosos);
        chiclosos.setOnClickListener(this);

        CardView Chocolatesenvueltos = (CardView) v.findViewById(R.id.Chocolatesenvueltos);
        Chocolatesenvueltos.setOnClickListener(this);

        CardView chocolatessinenv = (CardView) v.findViewById(R.id.chocolatessinenvoltura);
        chocolatessinenv.setOnClickListener(this);

        CardView presentaciones = (CardView) v.findViewById(R.id.presentaciones);
        presentaciones.setOnClickListener(this);

        CardView temporalidades = (CardView) v.findViewById(R.id.temporalidades);
        temporalidades.setOnClickListener(this);

        CardView piezas = (CardView) v.findViewById(R.id.tablillas);
        piezas.setOnClickListener(this);

        CardView varios = (CardView) v.findViewById(R.id.varios);
        varios.setOnClickListener(this);


        CardView flores = (CardView) v.findViewById(R.id.flores);
        flores.setOnClickListener(this);




        return v;
    }


    private void inicializarAdaptadores() {



    }

    private void inicializarDatos() {
        categorias=new ArrayList<>();
        categorias.add(new Categoria(R.drawable.caramelos,"Caramelos"));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.getActivity(),ItemsListActivity.class);


        if (view.getId()==R.id.caramelos){

            intent.putExtra("categoria", "Caramelos");
            startActivity(intent);
        }else if(view.getId()==R.id.chiclosos){
            intent.putExtra("categoria", "Chiclosos");
            startActivity(intent);
        }else if(view.getId()==R.id.Chocolatesenvueltos){
            intent.putExtra("categoria", "Chocolatesenvueltos");
            startActivity(intent);
        }else if(view.getId()==R.id.chocolatessinenvoltura){
            intent.putExtra("categoria", "chocolatessinenvolver");
            startActivity(intent);
        }else if(view.getId()==R.id.presentaciones){
            intent.putExtra("categoria", "Presentaciones");
            startActivity(intent);
        }else if(view.getId()==R.id.temporalidades){
            intent.putExtra("categoria", "Temporalidades");
            startActivity(intent);
        }else if(view.getId()==R.id.tablillas){
            intent.putExtra("categoria", "piezas");
            startActivity(intent);
        }else if(view.getId()==R.id.flores){
            intent.putExtra("categoria", "Flores");
            startActivity(intent);
        }else if(view.getId()==R.id.varios){
            intent.putExtra("categoria", "Varios");
            startActivity(intent);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }

}
