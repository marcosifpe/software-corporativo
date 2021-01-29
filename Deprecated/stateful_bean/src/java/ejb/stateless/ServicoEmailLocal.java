package ejb.stateless;

import java.util.concurrent.Future;
import javax.ejb.Local;

@Local
public interface ServicoEmailLocal {
    public void enviarMensagem(String para);
    public Future<Boolean> enviarEmail(String para);
}
