package alo;

import alo.ejb.Alo;
import javax.ejb.EJB;

/**
 *
 * @author MASC
 */
public class Main {
    @EJB(name = "AloMundoBean")
    private static Alo aloBean;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(aloBean.getMensagem("Marcos"));
    }
    
}
