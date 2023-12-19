package ait.chat.server.task;

import ait.chat.mediation.BlkQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServerSenden implements Runnable{
    private Socket socket;
    BlkQueue<String> blkQueue;
    List<PrintWriter> printWriters;
    public ChatServerSenden(Socket socket, BlkQueue<String> blkQueue, List<PrintWriter> printWriters) {
        this.socket = socket;
        this.blkQueue = blkQueue;
        this.printWriters = printWriters;
    }

    @Override
    public void run() {
        printWriters = new ArrayList<>();
        OutputStream outputStream = null;
        try (Socket socket = this.socket) {
            outputStream = socket.getOutputStream();
            while (true) {
//                for (PrintWriter socketWriter: printWriters) {
//                    socketWriter = new PrintWriter(outputStream);
//                    socketWriter.println(blkQueue.pop());
//                    socketWriter.flush();
//                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<PrintWriter> addWriter(List<PrintWriter> printWriterList, PrintWriter writer) {
        printWriterList.add(writer);
        return printWriterList;
    }

    private List<PrintWriter> removeWriter(List<PrintWriter> printWriterList, PrintWriter writer) {
        printWriterList.remove(writer);
        return printWriterList;
    }
}
