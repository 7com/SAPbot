package Serial;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.OutputStream;
import java.io.PrintStream;

public class Terminal extends javax.swing.JFrame {
    
    public SerialPort scorbot;
    
    //La función recibe un String s y agrega su texto al jTextArea1
    //sin perder el texto que se encuentre previamente. 
    public void recibir(String s)
    {
        jTextArea1.setText(jTextArea1.getText()+s+"\n");
        jTextArea1.setCaretPosition(jTextArea1.getText().length());
    }
    
    //La función recibe un char s y agrega al jTextArea1
    //sin perder el texto que se encuentre previamente. 
    public void recibir(char s)
    {
        jTextArea1.setText(jTextArea1.getText()+s);
        jTextArea1.setCaretPosition(jTextArea1.getText().length());
    }
    
    public Terminal() {
        initComponents();
        this.getContentPane().setBackground(Color.white);
    }
    
    //Función para configurar el ícono de la ventana.
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/icon.png"));


        return retValue;
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

        setTitle("Terminal SAPBot");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());
        setResizable(false);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Evento para el jTextField1. Su funcionalidad es recuperar el texto
    //en él y enviarlo hacia el controlador Scorbot al presionar la tecla Enter.
    //Una vez enviado el texto, el campo se vacía
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            OutputStream out = scorbot.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            String enviar = jTextField1.getText()+"\r";
            printStream.print(enviar);
            printStream.close();
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyReleased

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
