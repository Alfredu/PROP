package com.hidatosdecarbono;


import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

   public void guardaEnTxt(String json, String nombreFichero){
       try{
           File file =new File("persistencia/"+nombreFichero);
           if(!file.exists()){
               file.createNewFile();
           }
           FileWriter fw = new FileWriter(file,true);
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write(json+"\n");
           bw.close();
       }catch(IOException e){
           e.printStackTrace();
       }
   }

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
