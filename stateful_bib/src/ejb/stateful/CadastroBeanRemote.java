/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateful;

import javax.ejb.Remote;

/**
 *
 * @author MASC
 */
@Remote
public interface CadastroBeanRemote {
    public void criarUsuario(String login, String senha);
    public void criarEnderecoUsuario(String cep, String logradouro, Integer numero);
    public void cadastrarUsuario();
    public void cancelar();
}
