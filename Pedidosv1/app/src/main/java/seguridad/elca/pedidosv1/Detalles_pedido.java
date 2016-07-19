package seguridad.elca.pedidosv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;


public class Detalles_pedido extends ActionBarActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    private TextView Emp;
    private TextView prob;
    private TextView cal;
    private TextView num;
    private TextView idn;
    private TextView ciu;
    private TextView prov;

    String cadena;


    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();



    // url to get all products list
    private static String detalles_pedido = "http://186.137.170.157:2122/nicolas/detalles_pedidov1/get_pedido.php";

    // JSON Node names
   // private static final String TAG_SUCCESS = "success";
   // private static final String TAG_PRODUCTS = "aux_pedido";
    private static final String TAG_EMPRESA = "Empresa";
    private static final String TAG_PROBLEMA = "Problema";
    private static final String TAG_CALLE = "Calle";
    private static final String TAG_NUMERO = "Numero";
    private static final String TAG_PISO = "Piso";
    private static final String TAG_CIUDAD = "Ciudad";
    private static final String TAG_PROVINCIA = "Provincia";

    String empresa,problema,calle,numero,piso,ciudad,provincia,tecnico;
    String idf;


    //****************ESTO ES PARA DEVOLVERSE*****************

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(Detalles_pedido.this, pedidos.class);
            i.putExtra("tecnico",tecnico.toString());
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    // public class Lead {}

    //*****************PRINCIPAL*********************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_pedido);

        tecnico = getIntent().getStringExtra("tecnico");

/*
        pasarNombre = (TextView) findViewById(R.id.validar2);
        String cadena = getIntent().getStringExtra("cadena");
        pasarNombre.setText(cadena);
        TextView output = (TextView) findViewById(R.id.validar3);

*/
        cadena = getIntent().getStringExtra("cadena");
        String CurrentString = cadena;
        String[] separated = CurrentString.split("Numero=");
        cadena= separated[1];
        String nuevo = cadena;
        String[] separated2 = nuevo.split(",");
        cadena= separated2[0];


        idn = (TextView) findViewById(R.id.id);

        idn.setText(cadena);

        // Cargar los productos en el Background Thread
        new detalles().execute();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    class detalles extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Detalles_pedido.this);
            pDialog.setMessage("Cargando Detalle. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }





        protected String doInBackground(String... args) {
            // Building Parameters


                List params = new ArrayList();

                params.add(new BasicNameValuePair("cadena", cadena));
            // getting JSON string from URL
            // JSONObject json = jParser.makeHttpRequest(url_all_empresas, "GET", params);
            try {

                JSONObject json2 = jsonParser.makeHttpRequest(detalles_pedido, "POST",
                        params);



            // Check your log cat for JSON reponse
            //Log.d("All Products: ", json.toString());


                // Checking for SUCCESS TAG
                Log.d("Login Successful!", json2.toString());

                //String success = json.getString(TAG_NUMERO);

                //String cadena = getIntent().getStringExtra("cadena");
                //pasarNombre.setText(cadena);


                    //JSONObject c = products.getJSONObject(i);


                empresa = json2.getString(TAG_EMPRESA);
                problema = json2.getString(TAG_PROBLEMA);
                calle = json2.getString(TAG_CALLE);
                numero = json2.getString(TAG_NUMERO);
                piso = json2.getString(TAG_PISO);
                ciudad = json2.getString(TAG_CIUDAD);
                provincia = json2.getString(TAG_PROVINCIA);

                    //finish();
                    //return json.getString(TAG_CALLE);





                    // products found
                    // Getting Array of Products
                   // products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    //for (int i = 0; i < products.length(); i++) {

                       //JSONObject c = products.getJSONObject(i);
/*
                        // Storing each json item in variable
                        //String id = c.getString(TAG_ID);
                        //String name = c.getString(TAG_NOMBRE);
                        //String idsop = c.getString(TAG_IDSOPORTE);
                        //String idpro = c.getString(TAG_PROBLEMA);
                        // creating new HashMap
                        //HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        //map.put(TAG_ID, id);
                        //map.put(TAG_NOMBRE, name);
                        ///map.put(TAG_IDSOPORTE, idsop);
                        //map.put(TAG_PROBLEMA, idpro);

                        //empresaList.add(map);
                    }
*/


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        // updating UI from Background Thread

        Emp = (TextView) findViewById(R.id.empresa);
        cal = (TextView) findViewById(R.id.calle);
        num = (TextView) findViewById(R.id.Numero);
        ciu = (TextView) findViewById(R.id.ciudad);
        prov = (TextView) findViewById(R.id.provincia);
        prob = (TextView) findViewById(R.id.problema);



            Emp.setText(empresa);
            cal.setText(calle);
            num.setText(numero);
            ciu.setText(ciudad);
            prov.setText(provincia);
            prob.setText(problema);




    }



    }

}


