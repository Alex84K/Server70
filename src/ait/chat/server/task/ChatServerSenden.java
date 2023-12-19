package ait.chat.server.task;

import ait.chat.mediation.BlkQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatServerSenden implements Runnable{
    private Socket socket;
    BlkQueue blkQueue;
    List<PrintWriter> printWriters;
    public ChatServerSenden(Socket socket, BlkQueue blkQueue, List<PrintWriter> printWriters) {
        this.socket = socket;
        this.blkQueue = blkQueue;
        this.printWriters = printWriters;
    }

    @Override
    public void run() {
        printWriters = new ArrayList<>();
        try (Socket socket = this.socket) {
            OutputStream outputStream = socket.getOutputStream();
            Thread[] threads = new Thread[printWriters.size()];
            for (int i = 0; i < threads.length; i++) {
                PrintWriter p = new PrintWriter(outputStream);
                threads[i] = new Thread(p); // хотелось бы пораздавать адреса для каждого клиента...
                threads[i].start();
            }

            while (true) {

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
