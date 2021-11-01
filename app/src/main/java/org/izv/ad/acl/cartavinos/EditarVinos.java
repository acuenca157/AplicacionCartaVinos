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

public class EditarVinos extends AppCompatActivity {

    EditText etId, etNombre, etBodega, etColor, etOrigen, etGraduacion, etFecha;
    EditText[] campos;
    Vino vino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vinos);
        initialize();
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        vino = bundle.getParcelable("vino");

        //region EditTexts
        campos = new EditText[7];
        etId = findViewById(R.id.inputID);
        etId.setText(Long.toString(vino.getId()));
        etId.setEnabled(false);
        campos[0] = etId;
        etNombre = findViewById(R.id.inputNombre);
        etNombre.setText(vino.getNombre());
        campos[1] = etNombre;
        etBodega = findViewById(R.id.inputBodega);
        etBodega.setText(vino.getBodega());
        campos[2] = etBodega;
        etColor = findViewById(R.id.inputColor);
        etColor.setText(vino.getColor());
        campos[3] = etColor;
        etOrigen = findViewById(R.id.inputOrigen);
        etOrigen.setText(vino.getOrigen());
        campos[4] = etOrigen;
        etGraduacion = findViewById(R.id.inputGraduacion);
        etGraduacion.setText(Double.toString(vino.getGraduacion()));
        campos[5] = etGraduacion;
        etFecha = findViewById(R.id.inputFecha);
        etFecha.setText(Integer.toString(vino.getFecha()));
        campos[6] = etFecha;
        //endregion EditText

        //region Buttons
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Button btnEdit = findViewById(R.id.btnEditWine);
        btnEdit.setOnClickListener((View v) -> {
            if (EditarVino()){
                EditarRegistro();
            }
        });
        //endregion Buttons
    }

    private void EditarRegistro(){
        if (EditarVino()){
            if (sobreEscribirLinea()){
                Toast.makeText(this, "Se ha editado el vino", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else
                RationalDialog.showRationaleDialog(this, "Error", "No se ha podido insertar el registro");
        }
        else
        {
            RationalDialog.showRationaleDialog(this, "Error", "Algo ha ocurrido, intentelo más tarde");
        }
    }

    private boolean sobreEscribirLinea() {
        boolean hasRemoved = FileIO.deleteLine(getExternalFilesDir(null), getString(R.string.fileName), Long.toString(vino.getId()));
        if (hasRemoved){
            boolean hasWriten = FileIO.writeLine(getExternalFilesDir(null), getString(R.string.fileName),Csv.getCsv(vino));
            if (hasWriten)
                return true;
            else
                return false;
        } else
            return false;
    }

    private boolean EditarVino(){
        for (EditText et : campos) {
            if (et.getText().toString().isEmpty()){
                RationalDialog.showRationaleDialog(this, "Error", "Todos los campos deben de estar completos");
                return false;
            }
        }

        vino.setNombre(etNombre.getText().toString());
        vino.setBodega(etBodega.getText().toString());
        vino.setColor(etColor.getText().toString());
        vino.setOrigen(etOrigen.getText().toString());
        try {
            vino.setGraduacion(Double.parseDouble(etGraduacion.getText().toString()));
        }catch (NumberFormatException e){
            RationalDialog.showRationaleDialog(this, "Error", "La graduación debe ser un número decimal");
            return false;
        }

        try {
            vino.setFecha(Integer.parseInt(etFecha.getText().toString()));
        }catch (NumberFormatException e){
            RationalDialog.showRationaleDialog(this, "Error", "La graduación debe ser un número decimal");
            return false;
        }

        return true;
    }
}