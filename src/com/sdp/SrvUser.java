package com.sdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class SrvUser extends Thread {
    private final ServerSocket clientSocket;
    private BufferedReader in;

    public SrvUser(ServerSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("waiting for client...");
        Socket cSocket = null;
        try {
            cSocket = clientSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                assert cSocket != null; //impedir nullpointerexception
                in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
                String input = in.readLine();
                String[] userInputArray = input.split(" ");
                String comando = userInputArray[0];
                String chave = "";
                String valor = "";

                switch(comando){
                    case "R":
                        String[] userValueArray;

                        if(userInputArray.length < 3){
                            out.println("C"); //retorna erro ao cliente
                            break;
                        }

                        try {
                            chave = userInputArray[1];
                            userValueArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
                        } catch (Exception e) {
                            out.println("C"); //retorna erro ao cliente
                            break;
                        }

                        StringBuilder valorSB = new StringBuilder();
                        for (String s : userValueArray) {
                            valorSB.append(s);
                            valorSB.append(" ");
                        }
                        valor = valorSB.toString();
                        out.println(MainNode.sendToNode(comando, chave, valor));
                        break;
                    case "L":
                        MainNode.sendList();
                        break;
                    default:
                        try {
                            chave = userInputArray[1];
                        } catch (Exception e) {
                            out.println("C"); //retorna erro ao cliente
                            break;
                        }
                        out.println(MainNode.sendToNode(comando, chave, valor));
                        break;
                }

            } catch (IOException e) {
                System.out.println("Cliente desconectado.");
                break;
            }

        }
    }
}
