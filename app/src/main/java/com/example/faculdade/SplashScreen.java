package com.example.faculdade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private TextView textView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //ActionBar actionBar = this.getActionBar(); /
        // /actionBar.hide();
        textView = (TextView) this.findViewById(R.id.textView);
        textView.setText("Carregando...");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONParse().execute();
            }
        }, SPLASH_TIME_OUT);
    }

    public class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SplashScreen.this);

            pDialog.setMessage("Obtendo Dados");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json = null;

            AutorDAO autorDAO = new AutorDAO(SplashScreen.this);
            autorDAO.dropAll();
            JSONArray link = null;
            json = Json();
            try {
                // Getting JSON Array
                link = json.getJSONArray("data");
                for (int i = 0; i < link.length(); i++) {
                    JSONObject c = link.getJSONObject(i);
                    JSONObject autor = c.getJSONObject("autor");
                    AutorValue autorValue = new AutorValue();
                    autorValue.setNome(autor.getString("nome"));
                    autorValue.setEndereco(autor.getString("endereco"));
                    autorDAO.salvar(autorValue);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                pDialog.dismiss();
                Intent i = new Intent(SplashScreen.this, ListaDisciplinas.class);
                startActivity(i);
                finish();
                // MainActivity.this.onResume();
            } catch (Exception e) {

            }
        }
    }

    public static JSONObject Json(){
        JSONObject json = null;
        String resp=null;
        try {
            URL url1 = new URL("http://192.168.0.13:3000/api/mobile/1");
            BufferedReader reader = null;
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

            conn.connect();

            InputStream stream = conn.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");

            }

            json = new JSONObject(buffer.toString());
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
