/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serial;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JOptionPane;

/**
 *
 * @author fivc
 */
public class Terminal extends javax.swing.JFrame {

    public SerialPort scorbot;
    private String captura="";
    private char ser;
    private String[] enco,encoAnterior;     
    
    Thread lectura = new Thread() {
        InputStream in;
        public void run() {
           scorbot.openPort();
           in = scorbot.getInputStream();
           while (true){
               
               try 
               {
                    ser = (char)in.read();
                    while (ser != '\r')
                    {
                        captura=captura+ser;
                        ser = (char)in.read();
                    }
                    
                    enco=capturarEnco(captura);
                    if (esEnco(enco))
                    {
                        encoAnterior=enco;
                        for(int i=0; i<6; i++)
                        {
                            //System.out.print(enco[i]+" ");
                        }
                       // System.out.println();
                    }
                    
                    else
                    {
                        if(!captura.isEmpty())
                        {
                            String temp = captura.replaceAll("[0-9-]","");
                            jTextArea1.setText(jTextArea1.getText()+temp+"\n");
                            jTextArea1.setCaretPosition(jTextArea1.getText().length());
                            if(encoAnterior.length != 0){
                                for(int i=0; i<6; i++)
                                {
                                    //System.out.print(enco[i]+" ");
                                }
                               // System.out.println();
                            }
                        }
                    }
                    captura="";
                    
                    
               } catch (IOException ex) {
                   JOptionPane.showMessageDialog(null,"Se desconectó puerto serial.","Error",JOptionPane.ERROR_MESSAGE);
                   System.exit(1);
               }
           }
        }  
    };

    /**
     * Creates new form Terminal
     */
    
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
    
    public Terminal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setDragEnabled(true);
        jTextArea1.setDropMode(javax.swing.DropMode.INSERT);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jButton1.setText("CTRL+C");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            OutputStream out = scorbot.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            String enviar = jTextField1.getText()+" \r";
            printStream.print(enviar);
            printStream.close();
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        OutputStream out = scorbot.getOutputStream();
        PrintStream printStream = new PrintStream(out);
        char enviar = (char) 3;
        printStream.print(enviar);
        printStream.close();
    }//GEN-LAST:event_jButton1ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
