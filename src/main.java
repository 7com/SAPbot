
import Serial.MenuConfiguracion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fivc
 */
public class main {
    
    public static void main(String[] args){
        MenuConfiguracion m = new MenuConfiguracion();
        m.cargarPuertos();
        m.setVisible(true);
  }
    
}
