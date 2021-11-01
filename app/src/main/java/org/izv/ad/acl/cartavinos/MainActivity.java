package org.izv.ad.acl.cartavinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.izv.ad.acl.cartavinos.data.Vino;
import org.izv.ad.acl.cartavinos.util.Csv;
import org.izv.ad.acl.cartavinos.util.FileIO;
import org.izv.ad.acl.cartavinos.util.VinoText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName() + "xyzyx";
    LinearLayout ly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        ly = findViewById(R.id.container);
        writeVinos();

        Button btnCrearVino = findViewById(R.id.btnCrearVino);
        btnCrearVino.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, CrearVinoActivity.class);
            startActivity(intent);
        });
    }

    private void writeVinos(){
        String[] vinos = FileIO.getFileLines(getExternalFilesDir(null), getString(R.string.fileName));
        if (vinos != null){
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                VinoText vt = new VinoText(this, vino);
                ly.addView(vt);
            }
        }
    }
}