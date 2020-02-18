package tickets;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
  public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(55533);

        System.out.println("server将一直等待连接的到来");
        Socket socket = server.accept();

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Tickets tickets = (Tickets)ois.readObject();
        Server s1 = new Server(tickets);
        Thread t1 = new Thread(s1);
        t1.setName("Server");
        t1.start();

        inputStream.close();
        socket.close();
        server.close();
    }
}
