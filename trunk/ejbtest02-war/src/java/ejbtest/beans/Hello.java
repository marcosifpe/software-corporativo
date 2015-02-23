package ejbtest.beans;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
public class Hello {

    public String getMessage() {
        return "Hello World EJB!";
    }
}