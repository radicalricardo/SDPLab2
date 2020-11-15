package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class ParticipantNode {
    //public static ArrayList<Object> participantNodeID = new ArrayList<>();
    public static HashMap<String, String> sideNodeHashMap = new HashMap<>();
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {

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

    private static void listener(ServerSocket serverSocket) throws IOException {

        while(true){
            System.out.println("waiting...");
            Socket socket = serverSocket.accept();
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = in.readLine();
            System.out.println(input);
            String[] userInputArray = input.split(" ");
            String[] userValueArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
            String comando = userInputArray[0];
            String chave = userInputArray[1];
            StringBuilder valor = new StringBuilder();

            for (String s : userValueArray) {
                valor.append(s);
                valor.append(" ");
            }

            sideNodeHashMap.put(chave, valor.toString());
        }
    }

}
