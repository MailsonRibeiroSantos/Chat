package chatpublico;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.MensegerConteudo;
import protocolo.MensegerConteudoPrivada;
import protocolo.MensegerConteudoPublica;
import protocolo.MensegerListarUsuarios;

public class Server {

    ServerSocket serverSocket;

    private int port;

    private ArrayList<ClientInputReader> clients = new ArrayList<>();

    private String enderecoIP;
    private MensegerListarUsuarios lista;
    private MensegerConteudo messagec;

    public Server(int port) {
        try {
            this.setPort(port);

            this.serverSocket = new ServerSocket(this.port);

            this.enderecoIP = InetAddress.getLocalHost().getHostAddress();

            System.out.println("Server created at " + this.getEnderecoIP() + ":" + this.getPort());
        } catch (IOException | InvalidPortNumberException e) {
            System.err.println(e);
        }
    }

    //Enviar para todos os clientes

    public void enviarTodos(Object message) {
        messagec = new MensegerConteudoPublica();
        this.messagec.setConteudo(message);
        //  System.out.println("tamaho"+clients.size());
        for (int i = 0; i < clients.size(); i++) {

            try {
                System.out.println("Tentando envia conteudo:" + messagec.toString());
                clients.get(i).getMessageWriter().writeObject(messagec);

            } catch (Exception e) {
                System.out.println("Erro:" + e);
            }

        }
    }
    public void enviarPrivado(Object message, String nome) {
        messagec = new MensegerConteudoPrivada();
        this.messagec.setConteudo(message);
        //  System.out.println("tamaho"+clients.size());
        for (int i = 0; i < clients.size(); i++) {
           if(clients.get(i).getNome().equals(nome))
           {
            try {
                System.out.println("Tentando envia conteudo:" + messagec.toString());
                clients.get(i).getMessageWriter().writeObject(messagec);

            } catch (Exception e) {
                System.out.println("Erro:" + e);
            }

        
           }
    }
    }

    public void enviarPessoasConectadas() {
        lista = new MensegerListarUsuarios();

        for (int i = 0; i < clients.size(); i++) {
            lista.adicionarOnlines(i, clients.get(i).getNome());
            System.out.println("Adicionando:" + clients.get(i).getNome());
        }
        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).getMessageWriter().writeObject(lista);
            } catch (Exception e) {
                System.out.println("Erro:" + e);
            }
        }

    }

    public void acceptCoonections() {
        System.out.println("Accepting new connections...");

        while (true) {
            try {
                ClientInputReader cir;

                Socket client = this.serverSocket.accept();

                System.out.println(client.getInetAddress().getHostAddress() + " is now online.");

                cir = new ClientInputReader(client, this);

                this.clients.add(cir);

                Thread t = new Thread(cir);

                t.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPort(int port) throws InvalidPortNumberException {
        if (port < 1024 && port > 65535) {
            throw new InvalidPortNumberException();
        } else {
            this.port = port;
        }
    }

    public ArrayList<ClientInputReader> getClients() {
        return clients;
    }

    public String getEnderecoIP() {
        return enderecoIP;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
