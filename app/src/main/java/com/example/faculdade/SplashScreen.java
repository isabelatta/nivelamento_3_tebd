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

            DisciplinaDAO disciplinaDAO = new DisciplinaDAO(SplashScreen.this);
            disciplinaDAO.dropAll();
            JSONArray link = null;
            json = Json();
            int count = 0;
            try {
                // Getting JSON Array
                link = json.getJSONArray("Lista");
                for (int i = 0; i < link.length(); i++) {
                    JSONObject c = link.getJSONObject(i);
                    DisciplinaValue disciplinaValue = new DisciplinaValue();
                    disciplinaValue.setDisciplina(c.getString("disciplina"));
                    disciplinaDAO.salvar(disciplinaValue);
                    disciplinaDAO.close();
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
            // Create connection to send GCM Message request.
//            URL url1 = new URL("http://www.ictios.com.br/emjorge/appfaculdade/"+ "index1.php");
            URL url1 = new URL("https://api.myjson.com/bins/oywoa");
            BufferedReader reader = null;
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

            conn.connect();

            InputStream stream = conn.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            json = new JSONObject(buffer.toString());
/*            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            resp = IOUtils.toString(inputStream);
            json = new JSONObject(resp);

            Log.i("Teste", json.toString());*/
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
