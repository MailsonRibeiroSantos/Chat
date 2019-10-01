package publicchatclient;

import protocolo.Mensenger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import protocolo.MensegerConteudo;
import protocolo.MensegerConteudoPrivada;
import protocolo.MensegerConteudoPublica;
import protocolo.MensegerListarUsuarios;

public class ClientInputReader implements Runnable
{
    private Socket client;
    private ObjectInputStream messageReader;
    private Object message;
    private Chat c;
    private MensegerListarUsuarios messagec;

    public ClientInputReader(Socket client, Chat c)
    {
        this.client = client;
        this.c=c;
    }
    
    @Override
    public void run() {
    
        try
        {
            messageReader = new ObjectInputStream(client.getInputStream());

            
            while(true)
            {
              
                 message = (Object) messageReader.readObject();
                 if ((message instanceof MensegerConteudoPublica) || (message instanceof MensegerConteudoPrivada)) 
                 {                 
                    c.inserir(message.toString());
                 }
                 if(message instanceof MensegerListarUsuarios)
                 {
                     messagec=(MensegerListarUsuarios) message;
                         c.adicionar(messagec); 
                 }  
            }
            
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("Erro. Is " + this.client.getInetAddress().getHostAddress() + " online? ");
        }
       
    }

   


    
}
