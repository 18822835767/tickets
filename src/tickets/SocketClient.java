package tickets;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("127.0.0.1",55533);

        Tickets tickets = new Tickets();
        Client c1 = new Client(tickets);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        oos.writeObject(tickets);
        oos.flush();
        Thread t1 = new Thread(c1);
        t1.setName("小明");
        t1.start();

        outputStream.close();
        socket.close();
    }
}
