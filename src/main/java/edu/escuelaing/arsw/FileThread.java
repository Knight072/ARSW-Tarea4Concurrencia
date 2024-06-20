package edu.escuelaing.arsw;

import java.io.*;
import java.net.*;

public class FileThread extends Thread{
    private int id;
    private ServerSocket serverSocket;
    public FileThread(int id, ServerSocket serverSocket){
        this.id = id;
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        System.out.println("Corrio el thread " + id);
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        try{
            returnPage(clientSocket);
        }catch (IOException e){
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
    }

    public void returnPage(Socket clientSocket) throws IOException{
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }

        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n" + "</head>"
                + "<body>"
                + "My Web Site"
                + "</body>"
                + "</html>" + inputLine;

        out.println(outputLine);

        out.close();

        in.close();

        System.out.println("Cerro el thread: "+ id);

        clientSocket.close();

    }
}
