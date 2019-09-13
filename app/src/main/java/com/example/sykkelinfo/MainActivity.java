package com.example.sykkelinfo;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sykkelinfo.Adapter.StasjonlisteAdapter;
import com.example.sykkelinfo.Map.Kart_aktivitet;
import com.example.sykkelinfo.Model.Sykkelstasjon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final String ENDEPUNKT = "https://gbfs.urbansharing.com/oslobysykkel.no/";
    final String stasjonInfo = "station_information.json";
    final String stasjonStatus = "station_status.json";

    private StasjonlisteAdapter adapter;

    ArrayList<Sykkelstasjon> nyListe = new ArrayList<Sykkelstasjon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readStationInfo();

        //Lytter etter trykk pa knapp, apner kart
        final Button button = findViewById(R.id.map_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Kart_aktivitet.class);

                i.putParcelableArrayListExtra("lista", nyListe);
                startActivity(i);
            }
        });

    }


    //Leser stasjonsinformasjon med Volley for asynkron henting av JSON-data.
    private void readStationInfo() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ENDEPUNKT + stasjonInfo, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arrData = response.getJSONObject("data");
                            JSONArray arrStasjoner = arrData.getJSONArray("stations");

                            for (int i = 0; i < arrStasjoner.length(); i++) {
                                JSONObject e = arrStasjoner.getJSONObject(i);
                                String id = e.optString("station_id");
                                String navn = e.optString("name");
                                double lat = e.optDouble("lat");
                                double lon = e.optDouble("lon");
                                Sykkelstasjon stasjonen = new Sykkelstasjon(id, navn, lat, lon);
                                nyListe.add(stasjonen);
                            }
                            Log.d("Tostring:", "LISTE NAVN OG ID" + nyListe.toString());
                            readStationStatus();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    displayToast("no connection");
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    displayToast("no access");
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    displayToast("server responded with error");
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    displayToast("network error");
                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                    displayToast("could not be parsed");
                }
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    //Leser stasjonstatus med Volley for asynkron henting av JSON-data.
    private void readStationStatus() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ENDEPUNKT + stasjonStatus, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject arrData = response.getJSONObject("data");
                            JSONArray arrStasjoner = arrData.getJSONArray("stations");

                            for (int i = 0; i < arrStasjoner.length(); i++) {
                                JSONObject e = arrStasjoner.getJSONObject(i);
                                String stasjonID = e.optString("station_id");
                                int antallSykler = e.getInt("num_bikes_available");
                                int antallDocks = e.getInt("num_docks_available");
                                if (nyListe.get(i).getStation_id().equals(stasjonID)) {
                                    nyListe.get(i).setNum_bikes_available(antallSykler);
                                    nyListe.get(i).setNum_docks_available(antallDocks);
                                }
                            }
                            setUpRecycler(nyListe);
                            Log.d("Tostring:", "NYLISTE" + nyListe.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    displayToast("no connection");
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    displayToast("no access");
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    displayToast("server responded with error");
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    displayToast("network error");
                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                    displayToast("could not be parsed");
                }
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    //Setter opp recyclerview
    private void setUpRecycler(ArrayList<Sykkelstasjon> stasjonsListe) {
        final RecyclerView stasjonRecyclerview = findViewById(R.id.stasjoner);

        adapter = new StasjonlisteAdapter(this, stasjonsListe);

        stasjonRecyclerview.setAdapter(adapter);

        stasjonRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Setter opp meny
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        //Setter opp sokefunksjon
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return true;
    }

    //Filtrerer recycleview med text fra sok
    private void filter(String text) {
        ArrayList<Sykkelstasjon> filteredList = new ArrayList<>();

        for (Sykkelstasjon item : nyListe) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getStation_id().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterlist(filteredList);
    }



}

