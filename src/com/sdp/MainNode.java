package com.sdp;

import java.io.*;
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

    static String sendToNode(String command, String chave, String valor) throws IOException {
        ArrayList<String> envelope = new ArrayList<>();
        String response;

        int node = hash(chave, nodeList.size());

        if (node == 1){
            switch(command){
                case "R":
                    if(mainNodeHashMap.containsKey(chave)){
                        response = "B"; //B: já existe
                        break;
                    }
                    mainNodeHashMap.put(chave, valor);
                    response = "A";
                    break;
                case "C":
                    if(!(mainNodeHashMap.containsKey(chave))){
                        response = "B"; //B: nao existe
                        break;
                    }
                    return mainNodeHashMap.get(chave);
                case "D":
                    if(!(mainNodeHashMap.containsKey(chave))){
                        response = "B";
                        break;
                    }
                    mainNodeHashMap.remove(chave);
                    response = "A";
                    break;
                default:
                    response = "C";
                    break;
            }
        }
        else{
            Socket connectSideNode = new Socket(nodeList.get(node).get(0), Integer.parseInt(nodeList.get(node).get(1)));
            System.out.println("works!");
            ObjectOutputStream out = new ObjectOutputStream(connectSideNode.getOutputStream());
            envelope.add(command);
            envelope.add(chave);
            envelope.add(valor);
            out.reset();
            out.writeObject(envelope);
            BufferedReader in = new BufferedReader(new InputStreamReader(connectSideNode.getInputStream()));
            response = in.readLine();
            return response;
        }
        return response;
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

    public static int hash(String key, int nodeNumber){
        int objKey = Math.abs(key.hashCode());
        return (objKey % nodeNumber) + 1;
    }
}
