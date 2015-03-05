package ejb.stateless;

import javax.ejb.Local;

@Local
public interface ServicoEmailLocal {
    public void enviarMensagem(String para);
}
