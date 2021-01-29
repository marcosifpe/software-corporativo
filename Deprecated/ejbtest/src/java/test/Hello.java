package test;

import javax.ejb.Stateless;

/**
 *
 * @author MASC
 */
@Stateless(name = "HelloBean")
public class Hello implements HelloRemote {

    @Override
    public String hello(String name) {
        return String.format("Hello %s welcome to EJB 3.1!",name);
    }    
}
