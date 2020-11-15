package com.sdp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class MainNode {
    public static HashMap<Integer, ArrayList<String>> nodeList = new HashMap<>();
    public static HashMap<String, String> mainNodeHashMap = new HashMap<>();
    private static Integer nodeID = 0;


    public static void main(String[] args) throws Exception {

        //BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket nodeSocket = new ServerSocket(23422);
        ServerSocket clientSocket = new ServerSocket(23423);
        System.out.println("(Main Node) Iniciada ligação.");

        ArrayList<String> nodeInfo = new ArrayList<>();
        nodeInfo.add(nodeSocket.getInetAddress().toString());
        nodeInfo.add(Integer.toString(nodeSocket.getLocalPort()));
        registerNode(nodeInfo);
        Thread nodeRegisterThread = new Thread(new SrvRegisterNode(nodeSocket));
        nodeRegisterThread.start();
        //cria uma thread para listen conexoes novas de clientes
        Thread clientListenerThread = new Thread(new SrvUser(clientSocket));
        clientListenerThread.start();


        
    }

    public static void registerKV(String chave, String valor) throws IOException {
        int node = hash(chave, nodeList.size());
        sendToNode(node, chave, valor);
    }

    private static void sendToNode(int node, String chave, String valor) throws IOException {
        System.out.println("hi");
        if (node == 1){
            mainNodeHashMap.put(chave, valor);
        }
        else{
            Socket connectSideNode = new Socket(nodeList.get(node).get(0), Integer.parseInt(nodeList.get(node).get(1)));
        }
    }


    public static void searchKV(String chave, String valor) {
    }

    public static void deleteKV(String chave, String valor) {
    }

    public static void registerNode(ArrayList<String> nodeInfo) {
        nodeID++;
        if(MainNode.nodeList.size() < 10) {
            MainNode.nodeList.put(nodeID, nodeInfo);
            System.out.println("(Main Node) Informação de nó participante registada.");
        }
        else{
            System.out.println("(Main Node) Numero máximo de nós participantes na rede atingido.");
        }
    }

    public static int hash(Object key, int nodeNumber){
        int objKey = Math.abs(key.hashCode());
        return (objKey % nodeNumber) + 1;
    }
}
