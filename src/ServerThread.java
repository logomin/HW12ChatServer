import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread {
    public Socket socket; // сокет, через который сервер общается с клиентом, кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    // Делаем конструктор
    // Передаем в качестве параметра сокет по которому каждый новый клиент общается с сервером

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (SocketException se) {
            ServerForChat.clientsList.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start(); // вызываем метод run() у нити(потока)
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = in.readLine();
                System.out.println(message);
                if (message.equals("quit")){
                    ServerForChat.clientsList.remove(this);
                    System.out.println("кто то отключился using quit");
                    break;
                }
                for (ServerThread serverThread : ServerForChat.clientsList) {
                    serverThread.sendToClient(message);
                    System.out.println("отправляю сообщение");
                }
            } catch (SocketException se) {
                //se.printStackTrace();
                ServerForChat.clientsList.remove(this);
                System.out.println("кто то отключился");
                break;
                //System.exit(-20);
            }
            catch (IOException e) {
                e.printStackTrace();
                //System.exit(-58);
                //ServerForChat.clientsList.remove();
            }
        }
    }

    public void sendToClient(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Исключение в sendToClient");
        }
    }
}
