package com.sdp;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;


public class MainNode {
    public static HashMap<Integer, String> nodeList = new HashMap<>();
    public static HashMap<Integer, String> mainNodeHashMap = new HashMap<Integer, String>();


    public static void main(String[] args) throws Exception {

        //BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket nodeSocket = new ServerSocket(23422);
        ServerSocket clientSocket = new ServerSocket(23423);
        System.out.println("(Main Node) Iniciada ligação.");
        nodeList.put(nodeSocket.getLocalPort(), nodeSocket.getInetAddress().toString()); //Main Node também é um nó participante
        Thread nodeRegisterThread = new Thread(new SrvRegisterNode(nodeSocket));
        nodeRegisterThread.start();
        Thread clientListenerThread = new Thread(new SrvUser(clientSocket));
        clientListenerThread.start();


        //cria uma thread para listen conexoes novas de clientes








    }

    public static void registerKV(String chave, String valor) {


    }

    public static void searchKV(String chave, String toString) {
    }

    public static void deleteKV(String chave, String toString) {
    }
}
