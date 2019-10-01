package chatpublico;

public class InvalidPortNumberException extends Exception
{

    public InvalidPortNumberException() 
    {
        super("Invalid port number. Must be greatter than 1021 and less than 65535");
    }
    
}
