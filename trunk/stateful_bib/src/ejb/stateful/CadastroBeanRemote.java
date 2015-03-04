package ejb.stateful;

import javax.ejb.Remote;

@Remote
public interface CadastroBeanRemote {
    public void criarUsuario(String login, String senha);
    public void criarEnderecoUsuario(String cep, String logradouro, Integer numero);
    public void cadastrarUsuario();
    public void cancelar();
}
