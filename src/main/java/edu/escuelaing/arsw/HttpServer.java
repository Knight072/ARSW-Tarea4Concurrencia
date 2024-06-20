package edu.escuelaing.arsw;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class HttpServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i<=3; i++){
            threads.add(new FileThread(i, serverSocket));
            threads.get(i).start();
        }
        for(int i = 0; i<=3; i++){
            threads.get(i).join();
        }
        serverSocket.close();
    }
}
