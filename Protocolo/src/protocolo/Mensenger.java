package protocolo;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MTAuNi44LjEyNw
 */
public class Mensenger implements  Serializable{
    protected String origem;
    protected String destino;
    protected String conteudo;

    public String getConteudo() {
        return conteudo;
    }
   

    public void setConteudo(Object conteudo) {
        this.conteudo = (String) conteudo;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getOrigem() {
        return origem;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDestino() {
        return destino;
    }
    
    @Override
    public String toString()
    {
       return conteudo; 
    }
    
    
}
