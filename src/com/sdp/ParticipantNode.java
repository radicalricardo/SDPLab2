package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantNode {
    //public static ArrayList<Object> participantNodeID = new ArrayList<>();
    //public static HashMap<String, String> sideNodeHashMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        String port = "34532";
        ServerSocket socket = new ServerSocket(Integer.parseInt(port));
        Socket connectMainNode = new Socket("127.0.0.1", 23422); //args!
        PrintWriter out = new PrintWriter(connectMainNode.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(connectMainNode.getInputStream()));
        ObjectOutputStream outList = new ObjectOutputStream(connectMainNode.getOutputStream());
        outList.reset();
        outList.writeObject(port);
        //listener(socket);
    }

    private static void listener(ServerSocket socket) throws IOException {

        while(true){
            socket.accept();

        }
    }

}
