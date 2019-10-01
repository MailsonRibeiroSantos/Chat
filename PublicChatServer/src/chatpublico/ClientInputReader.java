package chatpublico;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import protocolo.MensegerConteudo;
import protocolo.MensegerConteudoPrivada;
import protocolo.MensegerConteudoPublica;
import protocolo.MensegerNome;
import protocolo.Mensenger;

public class ClientInputReader implements Runnable {

    private Socket client;
    private ObjectInputStream messageReader;
    private ObjectOutputStream messageWriter;
    private Server server;
    private String Nome;
    private MensegerConteudoPrivada mensagemP;

    public ObjectOutputStream getMessageWriter() {
        return messageWriter;
    }

    public String getNome() {
        return Nome;
    }

    public Socket getClient() {
        return client;
    }

    public ClientInputReader(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            messageReader = new ObjectInputStream(client.getInputStream());
            messageWriter = new ObjectOutputStream(client.getOutputStream());

            while (true) {
                Object message = messageReader.readObject();

                if (message instanceof MensegerNome) {
                    System.out.println("Controle");
                    System.out.println(message.toString());
                    Nome = message.toString();
                    server.enviarPessoasConectadas();
                }
                if (message instanceof MensegerConteudoPublica) {
                    // System.out.println("Conteudo enviando");
                    server.enviarTodos(this.Nome + ":" + message);
                }
                if (message instanceof MensegerConteudoPrivada) {
                    
                   mensagemP=(MensegerConteudoPrivada) message;
                   server.enviarPrivado(this.Nome + ":" + message, mensagemP.getDestino());
                }

            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro. Is " + this.client.getInetAddress().getHostAddress() + " online? ");
        }
    }
}
