package Serial;

import Firebase.Subida;
import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

public class Adquisicion extends javax.swing.JFrame {

    public SerialPort scorbot,arduino;
    private String captura;
    private char ser;
    private String[] enco,encoAnterior;
    private String datosArduino;
    private String nombreP;
    private boolean capturar;
    private Terminal t;
    private String ruta;
    private final ExecutorService exec;
    
    //Hilo dedicado a la captura de datos del controlador Scorbot.
    //El hilo toma los datos enconder válidos y los guarda en un archivo .txt con el
    //nombre de la prueba previamente indicado por el usuario.
    //Los datos que no sean parte de los encoder son enviados a la terminal del sistema.
    Thread lectura = new Thread() {
        InputStream in,in2;
        @Override
        public void run() {
            in = scorbot.getInputStream(); //Obtiene canal de Entrada del Controlador Scorbot-
            in2 = arduino.getInputStream(); //Obtiene canal de Entrada del Arduino Mega.
            jTextArea1.setText("Listo.\n\n"); 
            while (true){                              
                try 
                {
                    //Recibe los datos por serial del controlador y los guarda
                    //en el string captura. Los datos son guardados en captura
                    //hasta que se reciba un retorno de carro (\r).
                    ser = (char)in.read();
                    while (ser != '\r')
                    {
                        captura=captura+ser;
                        ser = (char)in.read();
                    }
                    //Si se inició la prueba (capturar == true).
                    if (capturar)
                    {
                        //Se procesa el String para validar si es un valor de encoder válido.
                        enco=capturarEnco(captura);
                        if (esEnco(enco))
                        {
                            encoAnterior=enco.clone();
                            datosArduino=capturarArduino(in2);
                            if(!escribir(encoAnterior,datosArduino))
                                jTextArea1.setText(jTextArea1.getText()+"Error al Escribir Archivo: "+ruta+".txt/n");
                        }
                        
                        //Si el encoder no es válido, se trata de limpiar el string
                        //de cualquier carácter no valido para el encoder y se vuelve a probar.
                        //En caso de no ser válido, se utilizan los valores previamente validados.
                        else
                        {
                            if(!captura.isEmpty())
                            {
                                enco=capturarEnco(captura.replaceAll("[^0-9-]", ""));
                                if (esEnco(enco))
                                {
                                    encoAnterior=enco.clone();
                                    datosArduino=capturarArduino(in2);
                                    if(!escribir(encoAnterior,datosArduino))
                                        jTextArea1.setText(jTextArea1.getText()+"Error al Escribir Archivo: "+ruta+".txt/n");
                                }
                                //Se elimina los números y el signo - del string y el valor resultante se envía
                                //al terminal. Usualmente los mensajes enviados en esta etapa son respuestas de los
                                //comandos enviados por el usuario por medio de la terminal u avisos generados por
                                //el controlador (por ejemplo: en caso de alcanzar el límite máximo de movimiento del eje).
                                t.recibir(captura.replaceAll("[0-9-]",""));
                            }
                            else
                            {
                                if(encoAnterior != null)
                                {
                                    if(!escribir(encoAnterior,datosArduino))
                                        jTextArea1.setText(jTextArea1.getText()+"Error al Escribir Archivo: "+ruta+".txt/n");
                                }   
                            }
                            
                        }
                    }
                    //En caso de no estar capturando, todos los caracteres recibidos
                    //son enviados al terminal.
                    else
                    {
                        t.recibir(captura);
                    }
                    //Se limpia el string captura para reiniciar el proceso de captura.
                    captura="";
                    
                    
               } catch (IOException ex) {
                   JOptionPane.showMessageDialog(null,"Se desconectó puerto serial.","Error",JOptionPane.ERROR_MESSAGE);
                   System.exit(1);
               }
           }
        }  
    };

    //Función dedicada a crear el archivo de texto que contiene los datos de la prueba.
    private boolean escribir(String[] e,String a)
    {
        try
        {
            File archivo = new File(ruta);
            BufferedWriter bw;
            String s="";
            for (int i=0;i<6;i++)
            {
                if(i==5)
                    s=s+e[i]+";";
                else
                    s=s+e[i]+",";
            }
            s=s+a;
            if(archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write("\n"+s);
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(s);
            }
            bw.close();
            return true;
        } catch (IOException ex){
            return false;
        }
    }
    
    //Función dedicada a pedir y capturar los datos de los sensores conectados al
    //Arduino Mega.
    private String capturarArduino(InputStream in) throws IOException{
        String str="";
        OutputStream out = arduino.getOutputStream();
        PrintStream printStream = new PrintStream(out);
        printStream.print("\n");   
        char c = (char)in.read();
        while (c != '\r')
        {
            str=str+c;
            c = (char)in.read();
        }
        printStream.close();
        return str;
    }
    
    //Función para configurar el ícono de la ventana.
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/icon.png"));


        return retValue;
    }
    
    //Se crea evento exitListener para entregárselo a la ventana de Terminal.
    //Remplaza la función del botón cerrar para que vuelva a activar el botón "Iniciar Terminal"
    WindowListener exitListener = new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        jButton1.setEnabled(true);
    }
    };
    
    public Adquisicion() {
        this.captura = "";
        this.capturar = false;
        this.t = new Terminal();
        this.ruta = "";
        this.exec = Executors.newFixedThreadPool(5);
        initComponents();
        t.addWindowListener(exitListener); //Se agrega exitListener previamente creado a la ventana Terminal
        this.getContentPane().setBackground(Color.white);
        this.addWindowListener(cerrarSistema);
    }   
    
    //Se crea evento cerrarSistema para entregárselo a la ventana de Adquisición.
                    //Remplaza la función del botón cerrar para que vuelva a activar el botón "Iniciar Terminal"
                    WindowListener cerrarSistema = new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        jButton1.setEnabled(false);
                        jToggleButton1.setEnabled(false);
                        consolaTXT("Cerrando Sistema...");
                        consolaTXT("Finalizando Hilos de Subida");
                        exec.shutdown();
                        while (!exec.isTerminated()) {
                        }
                        System.exit(0);
                    }
                    };
    
    //Función para detectar si el texto ingresado es un dato encoder valido.
    private boolean esEnco(String[] enco)
    {
        if (enco.length != 6)
        {
            return false;
        }
        else
        {
            for (int i=0; i<6;i++){
                if (!esNumeroEnco(enco[i]))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    //Verifica si el número recibido es un número encoder valido
    private boolean esNumeroEnco(String str)
    {
        if (str.length() != 6){
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    
    //Prepara el String Captura para ser utilizado en la función esEnco()
    private String[] capturarEnco(String str)
    {
       
        String[] lista;
        try{
            if ( Character.isWhitespace(str.charAt(0)))
            {
                lista = str.substring(1).split("\\s+");
            }
            else
            {
                lista = str.split("\\s+");
            }
            return lista;
        }catch(StringIndexOutOfBoundsException siobe){
            return lista = str.split("\\s+");
        }
    }
    
    public void consolaTXT(String s)
    {
        jTextArea1.setText(jTextArea1.getText()+s+"\n");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Adquisición de Parámetros Scorbot");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
        setResizable(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("Consola de Mensajes:");

        jButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton1.setText("Iniciar Terminal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jToggleButton1.setText("Iniciar Captura");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Evento para iniciar o finalizar la captura de la prueba. Envía comandos al
    //controlador para iniciar el envío de los valores de los enconders o para finalizar
    //su envío.
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (!capturar)
        {
            String tmp="";
            //Dentro del do while, se crea una ventana emergente donde el usuario
            //puede indicar el nombre para la prueba. Si el nombre es válido,
            //se iniciara automáticamente la captura de datos.
            do
            {
                tmp=JOptionPane.showInputDialog("Nombre de la prueba");
                if (tmp == null)
                    tmp="null";
                if (!tmp.matches("^[A-Za-z0-9 _-]*$"))
                    JOptionPane.showMessageDialog(null,"El nombre solo puede poseer letras, números, espacios y guiones.","Error",JOptionPane.INFORMATION_MESSAGE);
            }while(!tmp.matches("^[A-Za-z0-9 _-]*$"));
            ruta=tmp+".txt";
            nombreP=tmp;
            if(!ruta.equals("null.txt"))
            {
                if (!ruta.equals(".txt"))
                {
                    capturar=true;
                    jToggleButton1.setSelected(true);
                    jToggleButton1.setText("Finalizar Captura");
                    OutputStream out = scorbot.getOutputStream();
                    PrintStream printStream = new PrintStream(out);
                    String enviar = "show enco\r";
                    printStream.print(enviar);
                    printStream.close();
                    jTextArea1.setText(jTextArea1.getText()+"Capturando prueba en: "+ruta+"\n");
                }
                else
                {
                    jToggleButton1.setSelected(false);
                    JOptionPane.showMessageDialog(null,"Se debe ingresar nombre de prueba.","Error",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
                jToggleButton1.setSelected(false);
        }
        else
        {
            capturar=false;
            jToggleButton1.setSelected(false);
            jToggleButton1.setText("Iniciar Captura");
            OutputStream out = scorbot.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            char enviar = (char) 3;
            printStream.print(enviar);
            printStream.close();   
            jTextArea1.setText(jTextArea1.getText()+"Prueba Finalizada."+"\n\n");
            exec.execute(new Subida(ruta,nombreP,this));
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    //Evento para mostrar el terminal al controlador Scorbot.
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        t.scorbot=scorbot;
        t.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
