package ait.chat.server;

import ait.chat.mediation.BlkQueue;
import ait.chat.mediation.BlkQueueImpl;
import ait.chat.server.task.ChatServerSenden;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerAll {
    public static void main(String[] args) {
        int port = 9000;
        BlkQueue blkQueue = new BlkQueueImpl(300);
        List<PrintWriter> printWriters = new ArrayList<>();
        Socket socket = new Socket();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(port)){
            try {
                ChatServerSenden chatServerSenden = new ChatServerSenden(socket, blkQueue, printWriters);
                Thread daemonThread = new Thread(chatServerSenden);
                daemonThread.setDaemon(true);
                while (true) {
                    System.out.println("server wait...");
                    for (PrintWriter socketWriter: printWriters){
                        socket = serverSocket.accept();
                        System.out.println("Client host: " + socket.getInetAddress() + socket.getPort());
                        ChatServerSenden clientHandler = new ChatServerSenden(socket, blkQueue, printWriters);
                        executorService.execute(clientHandler);
                    }


                }
            } finally {
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
