package ejb.teste;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MASC
 */
@Local
public interface EstadoBeanLocal {
    List consultarEstador();
}
