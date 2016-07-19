package seguridad.elca.pedidosv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.TextView;

import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import java.util.Map;

public class pedidos extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
   // String nombre = getIntent().getStringExtra("nombre");

    private TextView pasarNombre;
   // private static final String tecnico = null;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> empresaList;


    // url to get all products list
    private static String url_all_empresas = "http://186.137.170.157:2122/nicolas/pedidostecv1/get_pedido.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "aux_pedido";
    private static final String TAG_ID = "idauxpedido";
    private static final String TAG_NOMBRE = "Empresa";
    private static final String TAG_IDSOPORTE = "Numero";
    private static final String TAG_PROBLEMA = "Problema";
    //private static final String TAG_SOPORTE = "idnumsoporte";

    // products JSONArray
    JSONArray products = null;

    ListView lista;



    String tecnico;

//****************ESTO ES PARA DEVOLVERSE*****************

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(pedidos.this, Login.class);
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }


   // public class Lead {}

    //*****************PRINCIPAL*********************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        if(getIntent().getStringExtra("nombre")==null) {
            tecnico = getIntent().getStringExtra("tecnico");
        }
        if (getIntent().getStringExtra("tecnico")==null){
            tecnico = getIntent().getStringExtra("nombre");
        }


/*
        String cadena = empresaList.get(i).toString();

        pasarNombre = (TextView) findViewById(R.id.validar);
        //String nombre = getIntent().getStringExtra("nombre");
        pasarNombre.setText(cadena);
*/


        // Hashmap para el ListView
        empresaList = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        lista = (ListView) findViewById(R.id.listAllProducts);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono " + i, Toast.LENGTH_SHORT).show();

                //enviar datos de una actividad a otra
                //empresaList.get(i);

                //String cadena = empresaList.get(i).toString();


               // String cadena = empresaList.get(i).toString();



                //pasarNombre = (TextView) findViewById(R.id.validar);
                //String nombre = getIntent().getStringExtra("nombre");
                //pasarNombre.setText(cadena);



                Intent x = new Intent(pedidos.this, Detalles_pedido.class);
                x.putExtra("cadena", empresaList.get(i).toString());
                x.putExtra("tecnico",tecnico.toString());
                startActivity(x);
            }
        });







        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono LARGO " + empresaList.get(i), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }//fin onCreate




//***************************OBTENCION DATOS DE LAS BD********************************

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(pedidos.this);
            pDialog.setMessage("Cargando Pedidos. Por favor espere...");
            //pDialog.setMessage(nombre);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();

            params.add(new BasicNameValuePair("username", tecnico));
            // getting JSON string from URL
           // JSONObject json = jParser.makeHttpRequest(url_all_empresas, "GET", params);

            JSONObject json = jParser.makeHttpRequest(url_all_empresas, "POST",
                    params);



            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String idsop = c.getString(TAG_IDSOPORTE);
                       // String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NOMBRE);
                       // String idsop = c.getString(TAG_IDSOPORTE);
                        String idpro = c.getString(TAG_PROBLEMA);
                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                       // map.put(TAG_ID, id);
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_IDSOPORTE, idsop);
                        map.put(TAG_PROBLEMA, idpro);

                        empresaList.add(map);

                    }
                }
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
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            pedidos.this,
                            empresaList,
                            R.layout.single_post,
                            new String[] {
                                    //TAG_ID,
                                    TAG_NOMBRE,
                                    //TAG_IDSOPORTE,
                                    TAG_PROBLEMA


                            },
                            new int[] {
                                   // R.id.single_post_tv_id,
                                    R.id.single_post_tv_nombre,
                                    R.id.single_post_tv_idsop,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);
                }
            });
        }
    }
}