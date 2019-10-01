package chatpublico;

public class PublicChat {

    public static void main(String[] args)
    {
        Server s = new Server(1024);
        
        s.acceptCoonections();
    }
}
