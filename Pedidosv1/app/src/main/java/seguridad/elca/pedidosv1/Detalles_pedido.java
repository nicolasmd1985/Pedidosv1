package seguridad.elca.pedidosv1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detalles_pedido extends Activity {

    private TextView pasarNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pasarNombre = (TextView) findViewById(R.id.validar2);
        String cadena = getIntent().getStringExtra("cadena");
        pasarNombre.setText(cadena);

    }
}
