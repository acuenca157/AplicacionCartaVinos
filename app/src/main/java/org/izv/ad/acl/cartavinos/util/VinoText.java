package org.izv.ad.acl.cartavinos.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.izv.ad.acl.cartavinos.EditarVinos;
import org.izv.ad.acl.cartavinos.data.Vino;

//Con esta clase a la hora de mostar los registros de vinos en pantalla,
// puedo tratarlos de una mejor manera
public class VinoText extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    public VinoText(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getFecha());
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { EditarVino(); }

    private void EditarVino(){
        Intent intent = new Intent(this.getContext(), EditarVinos.class);
        Bundle bundle = new Bundle();
        //bundle.putString("idVino", Long.toString(vino.getId()));
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }
}
