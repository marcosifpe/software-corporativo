package exemplo.singleton;

import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author MASC
 */
@Singleton
@LocalBean
public class ConfiguradorBean {

    public void algumMetodo() {
        Logger.getGlobal().info("Realizando alguma tarefa de inicialização do sistema...");
    }
}
