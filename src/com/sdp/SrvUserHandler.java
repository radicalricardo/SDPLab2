package com.sdp;

import java.io.*;
import java.net.Socket;

public class SrvUserHandler extends Thread {
    public Socket cSocket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream outObject;

    public SrvUserHandler(Socket cSocket) throws IOException {
        this.cSocket = cSocket;
        in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
        out = new PrintWriter(cSocket.getOutputStream(), true);
        outObject = new ObjectOutputStream(cSocket.getOutputStream());
    }
    @Override
    public void run() {
        while(true){
            //interaccao com o client
            try {
                String message = in.readLine();
                System.out.println("client" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
