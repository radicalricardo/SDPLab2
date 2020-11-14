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
        while(true){
            System.out.println("(Main Node) Aguardando ligação de clientes...");

            try {
                Socket cSocket = clientSocket.accept();
                System.out.println("(Main Node) Cliente conectado!");
                in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                String input = in.readLine();
                String[] userInputArray = input.split(" ");
                String[] userValueArray = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
                String comando = userInputArray[0];
                String chave = userInputArray[1];
                StringBuilder valor = new StringBuilder();

                for (String s : userValueArray) {
                    valor.append(s);
                    valor.append(" ");
                }

                switch(comando){
                    case "R":
                        MainNode.registerKV(chave, valor.toString());
                        break;
                    case "C":
                        MainNode.searchKV(chave, valor.toString());
                        break;
                    case "D":
                        MainNode.deleteKV(chave, valor.toString());
                        break;
                    case "Q":
                        break;
                    default:
                        break;
                }






                //SrvUserHandler clientThread = new SrvUserHandler(cSocket);
                //clientsConnected.add(clientThread);
                //threadPool.execute(clientThread);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
