package com.sdp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SrvRegisterNode implements Runnable {
    private final ServerSocket nodeSocket;
    private static ObjectInputStream in;


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
                String ParticipantNodePort = in.readObject().toString();
                InetAddress ParticipantNodeIP = nSocket.getInetAddress();

                if(MainNode.nodeList.size() < 10) {
                    MainNode.nodeList.put(Integer.parseInt(ParticipantNodePort), ParticipantNodeIP.toString());
                }
                else{
                    System.out.println("(Main Node) Numero máximo de nós participantes na rede atingido.");
                }

                System.out.println("(Main Node) Informação de nó participante registada.");
                nSocket.close();

                } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                }
        }

    }
}
