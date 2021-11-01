package org.izv.ad.acl.cartavinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.izv.ad.acl.cartavinos.data.Vino;
import org.izv.ad.acl.cartavinos.util.Csv;
import org.izv.ad.acl.cartavinos.util.FileIO;
import org.izv.ad.acl.cartavinos.util.RationalDialog;

public class CrearVinoActivity extends AppCompatActivity {

    EditText etId, etNombre, etBodega, etColor, etOrigen, etGraduacion, etFecha;
    EditText[] campos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wine);
        initialize();
    }

    private void initialize() {
        //region EditText
        campos = new EditText[7];

        etId = findViewById(R.id.inputID);
        campos[0] = etId;
        etNombre = findViewById(R.id.inputNombre);
        campos[1] = etNombre;
        etBodega = findViewById(R.id.inputBodega);
        campos[2] = etBodega;
        etColor = findViewById(R.id.inputColor);
        campos[3] = etColor;
        etOrigen = findViewById(R.id.inputOrigen);
        campos[4] = etOrigen;
        etGraduacion = findViewById(R.id.inputGraduacion);
        campos[5] = etGraduacion;
        etFecha = findViewById(R.id.inputFecha);
        campos[6] = etFecha;
        //endregion EditText
        //region Buttons
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        Button btnCreate = findViewById(R.id.btnEditWine);
        btnCreate.setOnClickListener((View view) -> {
            CrearVino();
        });
        //endregion Buttons

    }

    private void CrearVino(){
        for (EditText et : campos) {
            if (et.getText().toString().isEmpty()){
                RationalDialog.showRationaleDialog(this, "Error", "Todos los campos deben estar completos");
                return;
            }
        }
        Vino vino = sacarVino();
        if (vino != null){
            if (!existeVino(vino)) {
                String lineaCSV = Csv.getCsv(vino);
                FileIO.writeLine(getExternalFilesDir(null), getString(R.string.fileName), lineaCSV);
                Toast.makeText(this, "Vino insertado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

    }

    private Vino sacarVino(){
        Vino vino = new Vino();
        vino.setId(Long.parseLong(etId.getText().toString()));
        vino.setNombre(etNombre.getText().toString());
        vino.setOrigen(etOrigen.getText().toString());
        vino.setBodega(etBodega.getText().toString());
        vino.setColor(etColor.getText().toString());
        try {
            vino.setGraduacion(Double.parseDouble(etGraduacion.getText().toString()));
        }
        catch (NumberFormatException e){
            RationalDialog.showRationaleDialog(this, "Error", "Se requiere un número decimal en la graduación");
            return null;
        }

        try {
            vino.setFecha(Integer.parseInt(etFecha.getText().toString()));
        }
        catch (NumberFormatException e){
            RationalDialog.showRationaleDialog(this, "Error", "Se requiere un número en el año");
            return null;
        }
        return vino;
    }

    private boolean existeVino(Vino vino){
        String[] lineas = FileIO.getFileLines(getExternalFilesDir(null), getString(R.string.fileName));
        if (lineas != null){
            for (String line : lineas) {
                Vino v = Csv.getVino(line);
                if (vino.equals(v)){
                    RationalDialog.showRationaleDialog(this, "Error", "Este vino ya existe");
                    return true;
                }
            }
            return false;
        }

        return false;
    }


}