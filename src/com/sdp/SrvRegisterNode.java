package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SrvRegisterNode implements Runnable {
    private final ServerSocket nodeSocket;
    private static ObjectInputStream in;
    private ArrayList<String> buffer = new ArrayList<>();


    public SrvRegisterNode(ServerSocket nodeSocket) {
        this.nodeSocket = nodeSocket;

    }

    @Override
    public void run() {
        System.out.println("(Main Node) Aguardando ligação de nos participantes...");
        //Thread que regista novos nós participantes

        while(true) {
            try {
                Socket nSocket = nodeSocket.accept();
                System.out.println("(Main Node) Ligação a novo nó participante establecida!");
                in = new ObjectInputStream(nSocket.getInputStream());
                String participantNodePort = in.readObject().toString();
                String participantNodeIP = nSocket.getInetAddress().toString().split("/")[1];
                buffer.add(participantNodeIP);
                buffer.add(participantNodePort);
                MainNode.registerNode(buffer);
                nSocket.close();

                } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                }
        }

    }
}
