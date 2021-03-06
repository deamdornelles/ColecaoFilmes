package com.example.deam.colecaofilmes;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends Activity {

    EditText loginCadastro;
    EditText senhaCadastro;

    int retorno = 0;

    private final String NAMESPACE = "http://ws/";

    private final String URL = "http://192.168.25.204:8080/Banco/CadastraUsuario";
    private final String SOAP_ACTION = "http://192.168.25.204:8080/Banco/CadastraUsuario/verificaUsuario";
    private final String METHOD_NAME = "verificaUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginCadastro = (EditText) findViewById(R.id.loginCadastro);
        senhaCadastro = (EditText) findViewById(R.id.senhaCadastro);
    }

    public void novaConta(View v) {
        Intent intent = new Intent(LoginActivity.this, NovaContaActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    public void entrar(View v) {
        LoginActivity.AsyncCallWS task = new LoginActivity.AsyncCallWS();
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
            if (retorno > 0) {
                Toast.makeText(LoginActivity.this, "Olá " + loginCadastro.getText().toString() + "!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Erro no login, tente novamente!!", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(LoginActivity.this, "Olá!!", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            //LoginActivity.this.startActivity(intent);
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
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                retorno = Integer.parseInt(response.toString());

            } catch (Exception e) {
                //Log.w("myApp", e.getMessage());
                //Log.w("myApp", e.getCause());
            }
        }
    }
}
