package org.izv.ad.acl.cartavinos.util;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.izv.ad.acl.cartavinos.MainActivity;
import org.izv.ad.acl.cartavinos.R;

import java.io.File;
import java.io.*;
import java.util.List;


//Con esta clase usamos estos metodos estaticos para no tener que estar
//Creando un objeto o repitiendo los procedimientos
public class FileIO extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    //Con este metodo leemos todas las lineas del proyecto
    public static String[] getFileLines(File file, String fileName){
        File f = new File(file, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            String cache = "";
            while ((linea = br.readLine()) != null) {
                cache += linea + "%";
            }
            br.close();
            return cache.split("%");
        } catch (IOException e) {
            Log.v(TAG, e.toString());
        }
        return null;
    }

    //Con este metodo escribimos 1 linea añadiendola a nuestro archivo
    public static boolean writeLine(File file, String filename, String line){
        File f = new File(file, filename);
        FileWriter fw;
        try {
            fw = new FileWriter(f, true);
            fw.write(line);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //Con este metodo eliminamos 1 linea de nuestro archivo
    //pasando todos los registros menos el seleccionado a un archivo temporal
    //una vez hecho, eliminamos el original y asignamos al temporal el nombre del original
    public static boolean deleteLine(File file, String fileName, String id){
        File f = new File(file, fileName);
        File f2 = new File(file, "temp.tmp");
        String str[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while((linea = br.readLine()) != null){
                str = linea.split(";");
                if(!id.equals(str[0])){
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);

            return true;
        } catch (Exception e){
            return false;
        }
    }
}
