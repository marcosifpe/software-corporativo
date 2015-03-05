/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alo;

import javax.ejb.Remote;

/**
 *
 * @author MASC
 */
@Remote
public interface Alo {
    public String getMensagem(String nome);
}
