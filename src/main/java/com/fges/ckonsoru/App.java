/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;
import java.util.Properties;
import com.fges.ckonsoru.App_Modes.App_Mode_SQL;
import com.fges.ckonsoru.App_Modes.App_Mode_XML;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    public static void main(String args[]){
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();

        if(properties.getProperty("persistence").equals("XML")){
            App_Mode_XML app = new App_Mode_XML();
            app.lancerProgramme();
        } else {
            App_Mode_SQL app = new App_Mode_SQL();
            app.lancerProgramme();
        }
    }
}
