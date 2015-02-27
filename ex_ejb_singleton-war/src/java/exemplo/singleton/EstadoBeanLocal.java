/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.singleton;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MASC
 */
@Local
public interface EstadoBeanLocal {
    public List<Estado> consultarEstados();
}
