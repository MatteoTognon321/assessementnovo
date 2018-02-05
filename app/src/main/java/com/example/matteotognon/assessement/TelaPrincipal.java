package com.example.matteotognon.assessement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TelaPrincipal extends AppCompatActivity {

    private ListView listView;

    ArrayAdapter<Informaapi> adapter = null;

    private static final String TAG = TelaPrincipal.class.getSimpleName();
    public static final String BASE_URL = "http://infnet.educacao.ws/dadosAtividades.php/";
    private static Retrofit retrofit = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);


        listView = (ListView) findViewById(R.id.listView);
        conectarApi();
    }
            private void conectarApi(){
                if(retrofit==null){
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            }
                Api x = retrofit.create(Api.class);
                Call<AppResponse> call = x.getInformacao();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                        List<Informaapi> listagem = response.body().getInformacao();
                        adapter = new ArrayAdapter<Informaapi>(getApplicationContext(),android.R.layout.simple_list_item_1,listagem);
                        listView.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Call<AppResponse> call, Throwable t) {
                    }
                });
            }}