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

public class main {
    
    public static void main(String[] args){
        //Se crea menú de configuración y se cargan puertos Seriales
        MenuConfiguracion m = new MenuConfiguracion();
        m.cargarPuertos();
        m.setVisible(true);
  }
    
}
