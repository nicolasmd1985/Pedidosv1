package seguridad.elca.pedidosv1;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.view.KeyEvent;


public class consultas extends Activity implements OnClickListener{

    JSONParser jsonParser = new JSONParser();


    private EditText user, pass;
    private Button  mRegister;

    // Progress Dialog
    private ProgressDialog pDialog;

    //private static String txtCambiado = "Nicolas";

    private static final String REGISTER_URL = "http://192.168.5.48:2122/nicolas/php_post_android/info.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event){
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(consultas.this, Login.class);
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);



    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        new CreateUser().execute();

    }





    class CreateUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(consultas.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }




        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // full json response
                Log.d("Registering attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }




        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(consultas.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }









}
