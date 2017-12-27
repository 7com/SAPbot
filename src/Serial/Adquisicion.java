/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serial;

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
import javax.swing.JOptionPane;

/**
 *
 * @author fivc
 */
public class Adquisicion extends javax.swing.JFrame {

    public SerialPort scorbot,arduino;
    private String captura="";
    private char ser;
    private String[] enco,encoAnterior;
    private String datosArduino;
    private boolean capturar=false;
    private Terminal t = new Terminal();
    private String ruta="";
    
    Thread lectura = new Thread() {
        InputStream in,in2;
        public void run() {
            in = scorbot.getInputStream();
            in2 = arduino.getInputStream();
            jTextArea1.setText("Listo.\n\n");
            while (true){                              
                try 
                {
                    ser = (char)in.read();
                    while (ser != '\r')
                    {
                        captura=captura+ser;
                        ser = (char)in.read();
                    }
                    if (capturar)
                    {
                        enco=capturarEnco(captura);
                        if (esEnco(enco))
                        {
                            encoAnterior=enco.clone();
                            datosArduino=capturarArduino(in2);
                            if(!escribir(encoAnterior,datosArduino))
                                jTextArea1.setText(jTextArea1.getText()+"Error al Escribir Archivo: "+ruta+".txt/n");
                        }

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
                    else
                    {
                        t.recibir(captura);
                    }
                    captura="";
                    
                    
               } catch (IOException ex) {
                   JOptionPane.showMessageDialog(null,"Se desconectó puerto serial.","Error",JOptionPane.ERROR_MESSAGE);
                   System.exit(1);
               }
           }
        }  
    };

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
    
    private String capturarArduino(InputStream in) throws IOException{
        //Capturar 3 valores, voltaje calculado, frequencia pwm, temperatura desde arduino
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
    
    WindowListener exitListener = new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        jButton1.setEnabled(true);
    }
    };
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/icon.png"));


        return retValue;
    }
    
    public Adquisicion() {
        initComponents();
        t.addWindowListener(exitListener);
        this.getContentPane().setBackground(Color.white);
    }   
    
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
    
    private boolean esNumeroEnco(String str)
    {
        if (str.length() != 6){
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    
    
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Adquisición de Parámetros Scorbot");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
        setResizable(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Consola de Mensajes:");

        jButton1.setText("Iniciar Terminal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (!capturar)
        {
            String tmp="";
            do
            {
                tmp=JOptionPane.showInputDialog("Nombre de la prueba");
                if (!tmp.matches("^[A-Za-z0-9 _-]*$"))
                    JOptionPane.showMessageDialog(null,"El nombre solo puede poseer letras, números, espacios y guiones.","Error",JOptionPane.INFORMATION_MESSAGE);
            }while(!tmp.matches("^[A-Za-z0-9 _-]*$"));
            ruta=tmp+".txt";
            if(!ruta.equals("null.txt"))
            {
                if (!ruta.equals(".txt"))
                {
                    capturar=true;
                    jToggleButton1.setSelected(true);
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
            OutputStream out = scorbot.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            char enviar = (char) 3;
            printStream.print(enviar);
            printStream.close();   
            jTextArea1.setText(jTextArea1.getText()+"Prueba Finalizada."+"\n\n");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
