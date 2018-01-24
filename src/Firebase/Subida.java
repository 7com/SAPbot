package Firebase;

import Serial.Adquisicion;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.tasks.Task;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Subida implements Runnable {
    private String ruta, nombre, BD_URL, CREDENCIALES, fecha;
    private Adquisicion a;
    private File f;
    private boolean inicializado;

    public Subida(String s, String n, Adquisicion a) throws FileNotFoundException {
        this.inicializado = false;
        ruta=s;
        nombre=n;
        this.a=a;
        
        //Se cargan los datos para inicializar firebase en el sistema desde el
        //archivo config.cfg
        File archivo = new File("config.cfg");
        Scanner scan = new Scanner(archivo);
        BD_URL=scan.nextLine();
        CREDENCIALES=scan.nextLine();
        if (BD_URL == null || CREDENCIALES == null)
            throw new FileNotFoundException("Archivo config.cfg se encuentra incompleto./n"
                    + "Revisar archivo config.cfg");
    }

    
    public void Lectura () throws DatabaseException,IllegalStateException{
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
                JOptionPane.showMessageDialog(null,"El archivo "+CREDENCIALES+" no existe.\n"
                        + "Revisar archivo config.cfg","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        //Permite referenciar a la base de datos para poder enviar los datos
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReferenceFromUrl(BD_URL);
        
        f = new File(ruta);
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
        ArrayList<String> e7 = new ArrayList<String>();
        ArrayList<String> v1 = new ArrayList<String>();
        ArrayList<String> v2 = new ArrayList<String>();
        ArrayList<String> v3 = new ArrayList<String>();        
        ArrayList<String> v4 = new ArrayList<String>();
        ArrayList<String> v5 = new ArrayList<String>();
        ArrayList<String> v6 = new ArrayList<String>();
        ArrayList<String> v7 = new ArrayList<String>();
        ArrayList<String> t1 = new ArrayList<String>();
        ArrayList<String> t2 = new ArrayList<String>();
        ArrayList<String> t3 = new ArrayList<String>();        
        ArrayList<String> t4 = new ArrayList<String>();
        ArrayList<String> t5 = new ArrayList<String>();
        ArrayList<String> t6 = new ArrayList<String>();
        ArrayList<String> t7 = new ArrayList<String>();
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
                       if (i==0 && j==7){
                           //encoder motor 7
                           e7.add(tok3[j-1]);
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
                       if (i==1 && j==7){
                           //voltaje motor 7
                           v7.add(tok3[j-1]);
                       }
                       if (i==2 && j==1){
                           //frecuencia motor 1
                           t1.add(tok3[j-1]);
                       }
                       if (i==2 && j==2){
                           //frecuencia motor 2
                           t2.add(tok3[j-1]);
                       }
                       if (i==2 && j==3){
                           //frecuencia motor 3
                           t3.add(tok3[j-1]);
                       }
                       if (i==2 && j==4){
                           //frecuencia motor 4
                           t4.add(tok3[j-1]);
                       }
                       if (i==2 && j==5){
                           //frecuencia motor 5
                           t5.add(tok3[j-1]);
                       }
                       if (i==2 && j==6){
                           //frecuencia motor 6
                           t6.add(tok3[j-1]);
                       }
                       if (i==2 && j==7){
                           //frecuencia motor 7
                           t7.add(tok3[j-1]);
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
            String tem1 = String.join(", ", t1);
            String tem2 = String.join(", ", t2);
            String tem3 = String.join(", ", t3);
            String tem4 = String.join(", ", t4);
            String tem5 = String.join(", ", t5);
            String tem6 = String.join(", ", t6);
            String enco7="", vol7="", tem7="";
            
            if (a.slidebase){
                enco7 = String.join(", ", e7);
                vol7 = String.join(", ", v7);
                tem7 = String.join(", ", t7);
            }
            
            List<Task> tasks = new ArrayList<Task>();
            
            //Referencias generales de la base de datos
            Calendar c1 = GregorianCalendar.getInstance();
            String c2 = c1.getTime().toLocaleString();
            DatabaseReference re = ref.child(nombre);
            DatabaseReference re1 = re.child(a.fecha+" - "+c2);
            //Envio datos Motor 1
            DatabaseReference re2 = re1.child("Motor 1");
            DatabaseReference act = re2.child("Posicion");
            tasks.add(act.setValue(enco1));
            DatabaseReference act7 = re2.child("Voltaje");
            tasks.add(act7.setValue(vol1));
            DatabaseReference act19 = re2.child("Temperatura");
            tasks.add(act19.setValue(tem1));
            //Envio datos Motor 2
            DatabaseReference re3 = re1.child("Motor 2");
            DatabaseReference act2 = re3.child("Posicion");
            tasks.add(act2.setValue(enco2));
            DatabaseReference act8 = re3.child("Voltaje");
            tasks.add(act8.setValue(vol2));
            DatabaseReference act20 = re3.child("Temperatura");
            tasks.add(act20.setValue(tem2));
            //Envio datos Motor 3
            DatabaseReference re4 = re1.child("Motor 3");
            DatabaseReference act3 = re4.child("Posicion");
            tasks.add(act3.setValue(enco3));
            DatabaseReference act9 = re4.child("Voltaje");
            tasks.add(act9.setValue(vol3));
            DatabaseReference act21 = re4.child("Temperatura");
            tasks.add(act21.setValue(tem3));
            //Envio datos Motor 4
            DatabaseReference re5 = re1.child("Motor 4");
            DatabaseReference act4 = re5.child("Posicion");
            tasks.add(act4.setValue(enco4));
            DatabaseReference act10 = re5.child("Voltaje");
            tasks.add(act10.setValue(vol4));
            DatabaseReference act22 = re5.child("Temperatura");
            tasks.add(act22.setValue(tem4));
            //Envio datos Motor 5
            DatabaseReference re6 = re1.child("Motor 5");
            DatabaseReference act5 = re6.child("Posicion");
            tasks.add(act5.setValue(enco5));
            DatabaseReference act11 = re6.child("Voltaje");
            tasks.add(act11.setValue(vol5));
            DatabaseReference act23 = re6.child("Temperatura");
            tasks.add(act23.setValue(tem5));
            //Envio datos Motor 6
            DatabaseReference re7 = re1.child("Motor 6");
            DatabaseReference act6 = re7.child("Posicion");
            tasks.add(act6.setValue(enco6));
            DatabaseReference act12 = re7.child("Voltaje");
            tasks.add(act12.setValue(vol6));
            DatabaseReference act24 = re7.child("Temperatura");
            tasks.add(act24.setValue(tem6));
            //Envio datos Motor 7 (si corresponde)
            if (a.slidebase){
            DatabaseReference re8 = re1.child("Motor 7");
            DatabaseReference act25 = re8.child("Posicion");
            tasks.add(act25.setValue(enco7));
            DatabaseReference act26 = re8.child("Voltaje");
            tasks.add(act26.setValue(vol7));
            DatabaseReference act28 = re8.child("Temperatura");
            tasks.add(act28.setValue(tem7));  
            }
            a.consolaTXT("Subiendo prueba "+nombre+" a Firebase...");
            //Se verifican que todos los datos se han subido a la nube.
            for(int i=0;i<tasks.size();i++)
            {
                while(!tasks.get(i).isComplete())
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
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
        try
        {
        Lectura();
        }catch(DatabaseException ex){
            JOptionPane.showMessageDialog(null,"URL a Firebase invalida\n"
                    + "Revisar archivo config.cfg","Error",JOptionPane.ERROR_MESSAGE);
        }catch(IllegalStateException e){
            
        }
    }
}