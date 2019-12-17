import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerForChat {

    public static ArrayList<ServerThread> clientsList = new ArrayList<>();

    public static void main(String[] args) {
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(1025)) {
            socket = serverSocket.accept();
            clientsList.add(new ServerThread(socket)); // добавление в список нового подключения
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//public class ServerForChat {
//    public static void main(String[] args) {
//        System.out.println("Ждем подключения...");
//        Socket socket1;
//        try (ServerSocket serverSocket1 = new ServerSocket(1025)) {
//            // когда кто-то подключится к сокету serverSocket1 программа пойдет дальше
//            socket1 = serverSocket1.accept();
//            System.out.println("Кто-то подключился");
//            try (
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
//            ) {
//                while (true) {
//
//
//                    // Создаем поток для приема сообщений и для их отправки
//                    //while (true){
//                    String string1 = bufferedReader.readLine();
//                    System.out.println("Строка считалась");
//                    if (string1 == null) break;
//                    System.out.println(string1);
//                    bufferedWriter.write("лови обратно " + string1 + "\n");
//                    bufferedWriter.flush();
////            }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
