package ejbtestclient;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import test.HelloRemote;

/**
 *
 * @author MASC
 * @see https://blogs.oracle.com/MaheshKannan/entry/portable_global_jndi_names
 * @see http://wiki.netbeans.org/CreatingEJB3UsingNetbeansAndGlassfish
 * @see http://piotrnowicki.com/2013/03/defining-ejb-3-1-views-local-remote-no-interface/
 */
public class Client {
    private HelloRemote helloRemote;
    
    public Client() {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            props.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");      
            Context context = new InitialContext(props);
            helloRemote = (HelloRemote) context.lookup("java:global/ejbtest/HelloBean!test.HelloRemote");
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    public void sayHello() {        
        System.out.println(helloRemote.hello("Masc"));
    }

}
