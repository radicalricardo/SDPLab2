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
        System.out.println("(Main Node) Aguardando ligação de clientes...");
        Socket cSocket = null;
        try {
            cSocket = clientSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                System.out.println("(Main Node) Cliente conectado!");
                in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                String input = in.readLine();
                String[] userInputArray = input.split(" ");
                String[] userValueArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
                String comando = userInputArray[0];
                String chave = userInputArray[1];
                StringBuilder valor = new StringBuilder();
                PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
                for (String s : userValueArray) {
                    valor.append(s);
                    valor.append(" ");
                }
                out.println(MainNode.sendToNode(comando, chave, valor.toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
