import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket; // сокет, через который сервер общается с клиентом, кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    // Делаем конструктор
    // Передаем в качестве параметра сокет по которому каждый новый клиент общается с сервером

    public ServerThread(Socket socket){
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        start(); // вызываем метод run() у нити(потока)
    }

    @Override
    public void run() {
        String message;
        while (true){
            try {
                message = in.readLine();
                if (message.equals("quit")) break;
                for (ServerThread serverThread : ServerForChat.clientsList) {
                    serverThread.sendToClient(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(String message){
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
