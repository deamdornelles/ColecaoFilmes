package com.example.deam.colecaofilmes;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    private final String NAMESPACE = "http://ws/";

    private final String URL = "http://192.168.25.204:8080/Banco/BuscaFilme";
    private final String SOAP_ACTION = "http://192.168.25.204:8080/Banco/BuscaFilme/buscaFilme";
    private final String METHOD_NAME = "buscaFilme";
    private static String fahren;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.hello);
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            getResposta();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            tv.setText(fahren+ " !!!!");
        }

        @Override
        protected void onPreExecute() {
            tv.setText("Calculating...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            return true;
        }

        public void getResposta() {
            //Create request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            PropertyInfo celsiusPI = new PropertyInfo();
            celsiusPI.type = PropertyInfo.STRING_CLASS;
            celsiusPI.setName("Alo");
            //celsiusPI.setValue(celsius);
            //celsiusPI.setType(String.class);
            request.addProperty(celsiusPI);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                fahren = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
