package protocolo;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MTAuNi44LjEyOQ
 */
public class MensegerListarUsuarios extends MensegerControle{
    ArrayList<String> onlines = new ArrayList<>();
    public void adicionarOnlines(int i,String nome)
    {
        onlines.add(i,nome);
    }
    public int tamanho()
    {
        return onlines.size();
    }
    public String get(int i)
    {
        return onlines.get(i);
    }
}
