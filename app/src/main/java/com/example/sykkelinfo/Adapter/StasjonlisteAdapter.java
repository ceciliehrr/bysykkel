package com.example.sykkelinfo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sykkelinfo.Model.Sykkelstasjon;
import com.example.sykkelinfo.R;

import java.util.ArrayList;
import java.util.List;

public class StasjonlisteAdapter extends RecyclerView.Adapter<StasjonlisteAdapter.StasjonViewHolder> {
    private List<Sykkelstasjon> stasjonList;
    private LayoutInflater inflater;

    private static final String TAG = StasjonlisteAdapter.class.getSimpleName();

    //Konstruktør
    public StasjonlisteAdapter(Context context, List<Sykkelstasjon> stasjonList) {
        this.inflater = LayoutInflater.from(context);
        this.stasjonList = stasjonList;
    }

    //Lager ny viewholder
    @NonNull
    @Override
    public StasjonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Log.d(TAG, "onCreateViewHolder");
        View itemView = inflater.inflate(R.layout.stasjon_liste_item, parent, false);

        return new StasjonViewHolder(itemView);
    }

    // Om ny data, lag legg inn i viewholder
    @Override
    public void onBindViewHolder(@NonNull StasjonViewHolder stasjonViewHolder, int position) {
        Sykkelstasjon stasjonToDisplay = stasjonList.get(position);
        Log.d(TAG, "onBindViewHolder" + stasjonToDisplay.getName() + " - " + position + stasjonToDisplay.getNum_bikes_available());
        stasjonViewHolder.setSykkelstasjon(stasjonToDisplay);
    }

    //Hvor mange stasjoner skal vises
    @Override
    public int getItemCount() {
        return stasjonList.size();
    }

    //Plasserer dataene på riktig plass i viewet
    public class StasjonViewHolder extends RecyclerView.ViewHolder {
        private TextView navnTextView;
        private TextView antallLocksView;
        private TextView antallSyklerView;

        public StasjonViewHolder(@NonNull View itemView) {
            super(itemView);
            navnTextView = itemView.findViewById(R.id.stasjonsnavn_txt_id);
            antallLocksView = itemView.findViewById(R.id.antall_ledige_locks);
            antallSyklerView = itemView.findViewById(R.id.ledige_sykler_antall);
        }

        public void setSykkelstasjon(Sykkelstasjon stasjonToDisplay) {
            navnTextView.setText(stasjonToDisplay.getName());
            antallLocksView.setText(String.valueOf(stasjonToDisplay.getNum_docks_available()));
            antallSyklerView.setText(String.valueOf(stasjonToDisplay.getNum_bikes_available()));
        }
    }
    //Sier ifra nar lista er oppdatert etter sok
    public void filterlist(ArrayList<Sykkelstasjon> filteredList) {
        stasjonList = filteredList;
        notifyDataSetChanged();
    }

}
