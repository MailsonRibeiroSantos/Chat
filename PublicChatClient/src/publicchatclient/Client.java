package publicchatclient;

import protocolo.MensegerNome;
import protocolo.Mensenger;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import protocolo.MensegerConteudo;
import protocolo.MensegerConteudoPrivada;
import protocolo.MensegerConteudoPublica;


public class Client {
    
    String serverIPAddress;
    int port;
    ObjectOutputStream messageSender;
    Socket connection;
    ClientInputReader cir;
    Mensenger mensagem;

  
    
    public Client(String serverIPAddress, int port, Chat c, String nome)
    {
        this.port = port;
        
        this.serverIPAddress = serverIPAddress;
        
        try 
        {
            this.connection = new Socket(serverIPAddress, port);
        
            this.messageSender = new ObjectOutputStream(connection.getOutputStream());
            mensagem = new MensegerNome(nome);
            messageSender.writeObject(mensagem);
            showMessageDialog(null, "Conexão estabelecida!");
             cir = new ClientInputReader(connection,c);
               Thread t = new Thread(cir);
               t.start();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
         
    
    
    public  void chat(String t)
    {
       mensagem= new MensegerConteudoPublica();
       mensagem.setConteudo(t);
        try 
        {  
                messageSender.writeObject(mensagem);
                
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public  void chatPrivado(String t,String destino)
    {
       mensagem= new MensegerConteudoPrivada();
       mensagem.setConteudo(t);
       mensagem.setDestino(destino);
        try 
        {  
                messageSender.writeObject(mensagem);
                
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeConnectionAndQuit()
    {
        try 
        {
            this.messageSender.close();
        
            this.connection.close();
        
            System.exit(0);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        
            System.exit(-1);
        }
    }
}