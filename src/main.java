  /********************************************************************
   * SAPBot - Sistema de Adquisición de Parámetros Scorbot            *
   *                                                                  *
   * Desarrollado por:                                                *
   *                    - Hugo Ríos Fuentes                           *
   *                    - Felipe Valenzuela Cornejo                   *
   *                                                                  *
   *             Universidad del Bío-Bío 2017-2                       *
   *                                                                  *
   *  Memoria para optar al título de Ingeniero Civil en Informática  *
   ********************************************************************/
import Serial.MenuConfiguracion;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class main {
    
    public static void main(String[] args){
        File lock = new File("lock");
        try {
            if (lock.createNewFile()){
                lock.deleteOnExit();
                //Se crea menú de configuración y se cargan puertos Seriales
                MenuConfiguracion m = new MenuConfiguracion();
                m.cargarPuertos();
                m.setVisible(true);
            } else {
                System.exit(1);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
  }
    
}
