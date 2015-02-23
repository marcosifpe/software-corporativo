package test;

import javax.ejb.Remote;

/**
 *
 * @author MASC
 */
@Remote
public interface HelloRemote {

    String hello(String name);
    
}
