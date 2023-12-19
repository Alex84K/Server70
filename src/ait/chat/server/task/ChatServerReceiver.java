package ait.chat.server.task;

import ait.chat.mediation.BlkQueue;
import ait.chat.mediation.BlkQueueImpl;

import java.io.*;
import java.net.Socket;

public class ChatServerReceiver implements Runnable{
    private Socket socket;
    BlkQueue<String> blkQueue;

    public ChatServerReceiver(Socket socket, BlkQueue<String> blkQueue) {
        this.socket = socket;
        this.blkQueue = blkQueue;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            InputStream inputStream = socket.getInputStream();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));

        while (true) {
            String massage = socketReader.readLine();
            if(massage == null){
                break;
            }
            blkQueue.push(massage);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
