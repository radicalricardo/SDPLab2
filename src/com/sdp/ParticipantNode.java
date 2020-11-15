package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantNode {
    public static HashMap<String, String> sideNodeHashMap = new HashMap<>();
    private static PrintWriter out;
    private static BufferedReader in; //não sei porque é que está cinzento

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String port = "34532";
        ServerSocket socket = new ServerSocket(Integer.parseInt(port));
        Socket connectMainNode = new Socket("127.0.0.1", 23422); //args!
        out = new PrintWriter(connectMainNode.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(connectMainNode.getInputStream()));
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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ArrayList<String> input = (ArrayList<String>) in.readObject(); //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            String comando = input.get(0);
            String chave = input.get(1);
            String valor = null;
            if(input.size() > 2) {
                valor = input.get(2);
            }

            switch(comando){
                case "R":
                    if(sideNodeHashMap.containsKey(chave)){
                        out.println("NOK");
                    }
                    sideNodeHashMap.put(chave, valor);
                    //System.out.println("breakpoint");
                    out.println("OK");
                    break;
                case "C":
                    if(!(sideNodeHashMap.containsKey(chave))){
                        out.println("NOK");
                    }
                    //System.out.println("another breakpoint");
                    out.println(sideNodeHashMap.get(chave));
                    break;
                case "D":
                    if(!(sideNodeHashMap.containsKey(chave))){
                        out.println("NOK");
                    }
                    sideNodeHashMap.remove(chave);
                    //System.out.println("for the horde");
                    out.println("OK");
                    break;
                case "L":
                    break;
                default:
                    break;
            }

            /*barbaridades abaixo pls ignore
            String[] userInputArray = input.split(" ");
            String[] userValueArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
            String comando = userInputArray[0];
            String chave = userInputArray[1];
            StringBuilder valor = new StringBuilder();

            for (String s : userValueArray) {
                valor.append(s);
                valor.append(" ");
            }*/

        }
    }

    //não funciona porque o servidor de registar novos nós apanha o OK primeiro :(
    /*private static void sendOK() throws IOException {
        Socket connectMainNode = new Socket("127.0.0.1", 23422); //args!
        PrintWriter out = new PrintWriter(connectMainNode.getOutputStream(), true);
        out.println("OK");
    }*/

}
