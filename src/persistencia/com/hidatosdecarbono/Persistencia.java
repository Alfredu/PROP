package com.hidatosdecarbono;


import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {


    /**
     * Guarda el contenido del primer parametro en un fichero situado en el path relativo persistencia/
     * con el nombre indicado en el segundo parametro. Si existe,concatena el nuevo contenido al existente
     * sino lo crea y añade el nuevo contenido.
     * @param json Un String que contiene la informacion a guardar de forma persistente
     * @param nombreFichero Un String que contiene el nombre del fichero de persistencia
     */
   public void guardaEnTxt(String json, String nombreFichero, boolean concatenar){
       try{
           File file =new File("persistencia/"+nombreFichero);
           if(!file.exists()){
               file.createNewFile();
           }
           FileWriter fw = new FileWriter(file,concatenar);
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write(json+"\n");
           bw.close();
       }catch(IOException e){
           e.printStackTrace();
       }
   }

    /**
     * Devuelve un ArrayList de JSONObject que representan cada linea del fichero ubicado en
     * el path relativo persistencia/ y con el nombre indicado por el parametro.
     * @param nombreFichero Un String que contiene el nombre del fichero a leer
     * @return ArrayList<JSONObjectA> o null si no existe el fichero indicado por parametro
     */
   protected ArrayList<JSONObject> obtenDeTxt(String nombreFichero){
       ArrayList<JSONObject> texto = new ArrayList<>();
       try {
           File file = new File("persistencia/"+nombreFichero);
           if(!file.exists()) return null;
           FileReader fileReader = new FileReader(file);
           BufferedReader bufferedReader = new BufferedReader(fileReader);
           String line;
           while ((line = bufferedReader.readLine()) != null) {
                JSONObject jsonObj = new JSONObject(line);
                texto.add(jsonObj);
           }
           fileReader.close();
           return texto;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

}
