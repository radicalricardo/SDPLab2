package com.sdp;

import java.io.*;
import java.net.Socket;

public class MainNodeHandler implements Runnable {
    public Socket nSocket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream outObject;

    public MainNodeHandler(Socket nSocket) throws IOException {
        this.nSocket = nSocket;
        in = new BufferedReader(new InputStreamReader(this.nSocket.getInputStream()));
        out = new PrintWriter(this.nSocket.getOutputStream(), true);
        outObject = new ObjectOutputStream(this.nSocket.getOutputStream());
    }

    @Override
    public void run() {
        //implementacao da interaccao com a participant node
    }
}
