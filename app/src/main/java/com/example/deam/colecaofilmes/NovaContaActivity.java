package com.example.deam.colecaofilmes;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class NovaContaActivity extends AppCompatActivity {

    TextView loginCadastro;
    TextView senhaCadastro;

    private final String NAMESPACE = "http://ws/";

    private final String URL = "http://192.168.25.204:8080/Banco/CadastraUsuario";
    private final String SOAP_ACTION = "http://192.168.25.204:8080/Banco/CadastraUsuario/cadastraUsuario";
    private final String METHOD_NAME = "cadastraUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta);

        loginCadastro = (TextView) findViewById(R.id.loginCadastro);
        senhaCadastro = (TextView) findViewById(R.id.senhaCadastro);
    }

    public void cadastrarUsuario(View v) {
        NovaContaActivity.AsyncCallWS task = new NovaContaActivity.AsyncCallWS();
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
            Toast.makeText(NovaContaActivity.this, "Conta cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
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

            PropertyInfo login = new PropertyInfo();
            login.type = PropertyInfo.STRING_CLASS;
            login.setValue(loginCadastro.getText().toString());
            login.setName("login");
            login.setType((String.class));
            request.addProperty(login);

            PropertyInfo senha = new PropertyInfo();
            senha.type = PropertyInfo.STRING_CLASS;
            senha.setValue(senhaCadastro.getText().toString());
            senha.setName("senha");
            senha.setType((String.class));
            request.addProperty(senha);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                //SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //SoapObject result = (SoapObject)envelope.bodyIn;

            } catch (Exception e) {
                //e.printStackTrace();
                Log.w("myApp", e.getMessage());
                Log.w("myApp", e.getCause());
            }
        }
    }
}