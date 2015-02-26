/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alo;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author MASC
 */
public class MainJNDI {
    @EJB(name = "AloMundoBean")
    private static Alo aloBean;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(MainJNDI.class.getResourceAsStream("jndi.properties"));
            Context context = new InitialContext(properties);
            aloBean = (Alo) context.lookup("java:global/alo_mundo_ejb_remoto/AloMundoBean!alo.ejb.Alo");
            System.out.println(aloBean.getMensagem("Teste"));
        } catch (NamingException | IOException ex) {
            Logger.getGlobal().log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
