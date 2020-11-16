package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticipantNode {
    public static HashMap<String, String> sideNodeHashMap = new HashMap<>();
    private static PrintWriter out;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String port = args[0];
        ServerSocket socket = new ServerSocket(Integer.parseInt(port));
        Socket connectMainNode = new Socket("127.0.0.1", 23422); //IP do Main Node e Port do Main Node
        out = new PrintWriter(connectMainNode.getOutputStream(), true);
        ObjectOutputStream outList = new ObjectOutputStream(connectMainNode.getOutputStream());
        outList.reset();
        outList.writeObject(port);
        listener(socket);
    }

    private static void listener(ServerSocket serverSocket) throws IOException, ClassNotFoundException {

        while(true){
            System.out.println("waiting...");
            Socket socket = serverSocket.accept();

            //isto esta horrivel :(

            out = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream inObj = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> input = (ArrayList<String>) inObj.readObject(); //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            //System.out.println("bbbbbbbreakpoint");
            String comando = input.get(0);
            String chave = input.get(1);
            String valor = null;
            if(input.size() > 2) {
                valor = input.get(2);
            }

            switch(comando){
                case "R":
                    if(sideNodeHashMap.containsKey(chave)){
                        out.println("B"); //B: já existe
                    }
                    try {
                        sideNodeHashMap.put(chave, valor);
                        //System.out.println("breakpoint");
                        out.println("A"); //A: OK
                    } catch (Exception e) {
                        out.println("C"); //C: Erro
                    }
                    break;
                case "C":
                    if(!(sideNodeHashMap.containsKey(chave))){
                        out.println("B"); //B: nao existe
                    }
                    try {
                        //System.out.println("another breakpoint");
                        out.println(sideNodeHashMap.get(chave));
                        break;
                    } catch (Exception e) {
                        out.println("C"); //C: Erro
                    }
                case "D":
                    if(!(sideNodeHashMap.containsKey(chave))){
                        out.println("B"); //B: nao existe
                    }
                    try {
                        sideNodeHashMap.remove(chave);
                        //System.out.println("for the horde");
                        out.println("A"); //A: OK
                    } catch (Exception e) {
                        out.println("C"); //C: Erro
                    }
                    break;
                case "L":
                    for (Map.Entry<String, String> entry : sideNodeHashMap.entrySet())
                        System.out.println("Chave: " + entry.getKey() + " Valor: " +  entry.getValue());
                    break;
                default:
                    break;
            }

        }
    }


}
