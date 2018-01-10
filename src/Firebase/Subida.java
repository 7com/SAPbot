package Firebase;

import Serial.Adquisicion;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Subida implements Runnable {
    private String ruta, nombre;
    private Adquisicion a;
    private File f;
    private boolean inicializado;

    public Subida(String s, String n, Adquisicion a) {
        this.inicializado = false;
        ruta=s;
        nombre=n;
        this.a=a;
    }

    
    public void Lectura (){
        //Hace referencia a la base de datos y las credenciales de datos
        String BD_URL = "https://sapbot-001.firebaseio.com/";
        String CREDENCIALES = "Credencial SAPBot.json";
        try{
             FirebaseApp.getInstance();
        }catch (IllegalStateException ex)
        {
            try {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setServiceAccount(new FileInputStream(CREDENCIALES))
                        .setDatabaseUrl(BD_URL)
                        .build();
                FirebaseApp.initializeApp(options);
                //La siguiente linea muestra los errores por consola
                //FirebaseDatabase.getInstance().setLogLevel(com.google.firebase.database.Logger.Level.DEBUG);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        //Permite referenciar a la base de datos para poder enviar los datos
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReferenceFromUrl(BD_URL);
        
        f = new File(ruta);
        //File f = new File("C:/Users/Hugo/Desktop/SAPbot-master/prueba.txt");
        StringTokenizer tok1;
        Scanner entrada = null;
        String cadena, st1;
        String []tok2= new String[100];
        String []tok3= new String[100];
        ArrayList<String> e1 = new ArrayList<String>();
        ArrayList<String> e2 = new ArrayList<String>();
        ArrayList<String> e3 = new ArrayList<String>();
        ArrayList<String> e4 = new ArrayList<String>();
        ArrayList<String> e5 = new ArrayList<String>();
        ArrayList<String> e6 = new ArrayList<String>();        
        ArrayList<String> v1 = new ArrayList<String>();
        ArrayList<String> v2 = new ArrayList<String>();
        ArrayList<String> v3 = new ArrayList<String>();        
        ArrayList<String> v4 = new ArrayList<String>();
        ArrayList<String> v5 = new ArrayList<String>();
        ArrayList<String> v6 = new ArrayList<String>();
        ArrayList<String> f1 = new ArrayList<String>();
        ArrayList<String> f2 = new ArrayList<String>();
        ArrayList<String> f3 = new ArrayList<String>();
        ArrayList<String> f4 = new ArrayList<String>();
        ArrayList<String> f5 = new ArrayList<String>();
        ArrayList<String> f6 = new ArrayList<String>();        
        ArrayList<String> t1 = new ArrayList<String>();
        ArrayList<String> t2 = new ArrayList<String>();
        ArrayList<String> t3 = new ArrayList<String>();        
        ArrayList<String> t4 = new ArrayList<String>();
        ArrayList<String> t5 = new ArrayList<String>();
        ArrayList<String> t6 = new ArrayList<String>();
        try {
            entrada = new Scanner(f);
            while (entrada.hasNext()) {
                cadena = entrada.nextLine();
                tok1 = new StringTokenizer(cadena, "#");
                st1 = tok1.nextToken();
                tok2 = st1.split(";");
                for(int i=0; i<tok2.length; i++){
                    tok3 = tok2[i].split(",");
                   for (int j=1; j<=tok3.length; j++){
                       if (i==0 && j==1){
                           //encoder motor 1
                           e1.add(tok3[j-1]);
                       }
                       if (i==0 && j==2){
                           //encoder motor 2
                           e2.add(tok3[j-1]);
                       }
                       if (i==0 && j==3){
                           //encoder motor 3
                           e3.add(tok3[j-1]);
                       }
                       if (i==0 && j==4){
                           //encoder motor 4
                           e4.add(tok3[j-1]);
                       }
                       if (i==0 && j==5){
                           //encoder motor 5
                           e5.add(tok3[j-1]);
                       }
                       if (i==0 && j==6){
                           //encoder motor 6
                           e6.add(tok3[j-1]);
                       }
                       if (i==1 && j==1){
                           //voltaje motor 1
                           v1.add(tok3[j-1]);
                       }
                       if (i==1 && j==2){
                           //voltaje motor 2
                           v2.add(tok3[j-1]);
                       }
                       if (i==1 && j==3){
                           //voltaje motor 3
                           v3.add(tok3[j-1]);
                       }
                       if (i==1 && j==4){
                           //voltaje motor 4
                           v4.add(tok3[j-1]);
                       }
                       if (i==1 && j==5){
                           //voltaje motor 5
                           v5.add(tok3[j-1]);
                       }
                       if (i==1 && j==6){
                           //voltaje motor 6
                           v6.add(tok3[j-1]);
                       }
                       if (i==2 && j==1){
                           //frecuencia motor 1
                           f1.add(tok3[j-1]);
                       }
                       if (i==2 && j==2){
                           //frecuencia motor 2
                           f2.add(tok3[j-1]);
                       }
                       if (i==2 && j==3){
                           //frecuencia motor 3
                           f3.add(tok3[j-1]);
                       }
                       if (i==2 && j==4){
                           //frecuencia motor 4
                           f4.add(tok3[j-1]);
                       }
                       if (i==2 && j==5){
                           //frecuencia motor 5
                           f5.add(tok3[j-1]);
                       }
                       if (i==2 && j==6){
                           //frecuencia motor 6
                           f6.add(tok3[j-1]);
                       }
                       if (i==3 && j==1){
                           //temperatura motor 1
                           t1.add(tok3[j-1]);
                       }
                       if (i==3 && j==2){
                           //temperatura motor 2
                           t2.add(tok3[j-1]);
                       }
                       if (i==3 && j==3){
                           //temperatura motor 3
                           t3.add(tok3[j-1]);
                       }
                       if (i==3 && j==4){
                           //temperatura motor 4
                           t4.add(tok3[j-1]);
                       }
                       if (i==3 && j==5){
                           //temperatura motor 5
                           t5.add(tok3[j-1]);
                       }
                       if (i==3 && j==6){
                           //temperatura motor 6
                           t6.add(tok3[j-1]);
                       }
                   }
                }
            }
            //Conversion de elementos desde el arraylist a un String
            String enco1 = String.join(", ", e1);
            String enco2 = String.join(", ", e2);
            String enco3 = String.join(", ", e3);
            String enco4 = String.join(", ", e4);
            String enco5 = String.join(", ", e5);
            String enco6 = String.join(", ", e6);
            String vol1 = String.join(", ", v1);
            String vol2 = String.join(", ", v2);
            String vol3 = String.join(", ", v3);
            String vol4 = String.join(", ", v4);
            String vol5 = String.join(", ", v5);
            String vol6 = String.join(", ", v6);
            String fre1 = String.join(", ", f1);
            String fre2 = String.join(", ", f2);
            String fre3 = String.join(", ", f3);
            String fre4 = String.join(", ", f4);
            String fre5 = String.join(", ", f5);
            String fre6 = String.join(", ", f6);
            String tem1 = String.join(", ", t1);
            String tem2 = String.join(", ", t2);
            String tem3 = String.join(", ", t3);
            String tem4 = String.join(", ", t4);
            String tem5 = String.join(", ", t5);
            String tem6 = String.join(", ", t6);
            
            //Referencias generales de la base de datos
            Calendar c1 = GregorianCalendar.getInstance();
            String c2 = c1.getTime().toLocaleString();
            DatabaseReference re = ref.child(nombre);//Cambiar por nombre txt despues
            DatabaseReference re1 = re.child(c2);
            //Envio datos posicion
            DatabaseReference re2 = re1.child("Posicion");
            DatabaseReference act = re2.child("Motor 1");
            act.setValue(enco1);
            DatabaseReference act2 = re2.child("Motor 2");
            act2.setValue(enco2);
            DatabaseReference act3 = re2.child("Motor 3");
            act3.setValue(enco3);
            DatabaseReference act4 = re2.child("Motor 4");
            act4.setValue(enco4);
            DatabaseReference act5 = re2.child("Motor 5");
            act5.setValue(enco5);
            DatabaseReference act6 = re2.child("Motor 6");
            act6.setValue(enco6);
            //Envio datos Voltaje
            DatabaseReference re3 = re1.child("Voltaje");
            DatabaseReference act7 = re3.child("Motor 1");
            act7.setValue(vol1);
            DatabaseReference act8 = re3.child("Motor 2");
            act8.setValue(vol2);
            DatabaseReference act9 = re3.child("Motor 3");
            act9.setValue(vol3);
            DatabaseReference act10 = re3.child("Motor 4");
            act10.setValue(vol4);
            DatabaseReference act11 = re3.child("Motor 5");
            act11.setValue(vol5);
            DatabaseReference act12 = re3.child("Motor 6");
            act12.setValue(vol6);
            //Envio datos frecuencia
            DatabaseReference re4 = re1.child("Frecuencia");
            DatabaseReference act13 = re4.child("Motor 1");
            act13.setValue(fre1);
            DatabaseReference act14 = re4.child("Motor 2");
            act14.setValue(fre2);
            DatabaseReference act15 = re4.child("Motor 3");
            act15.setValue(fre3);
            DatabaseReference act16 = re4.child("Motor 4");
            act16.setValue(fre4);
            DatabaseReference act17 = re4.child("Motor 5");
            act17.setValue(fre5);
            DatabaseReference act18 = re4.child("Motor 6");
            act18.setValue(fre6);
            //Envio datos temperatura
            DatabaseReference re5 = re1.child("Temperatura");
            DatabaseReference act19 = re5.child("Motor 1");
            act19.setValue(tem1);
            DatabaseReference act20 = re5.child("Motor 2");
            act20.setValue(tem2);
            DatabaseReference act21 = re5.child("Motor 3");
            act21.setValue(tem3);
            DatabaseReference act22 = re5.child("Motor 4");
            act22.setValue(tem4);
            DatabaseReference act23 = re5.child("Motor 5");
            act23.setValue(tem5);
            DatabaseReference act24 = re5.child("Motor 6");
            act24.setValue(tem6);
            a.consolaTXT("La prueba "+nombre+" se ha subido a exitosamente a Firebase.");
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            } 
            finally {
                 entrada.close();
                 while(!f.delete())
                     System.out.println("Reintentando Borrar "+ruta);
            } 
    }
                    
    
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
        Lectura();
    }
    
}