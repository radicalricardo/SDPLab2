package com.sdp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static Socket connectionMainNode = null;
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {

        try {
            connectionMainNode = new Socket("127.0.0.1", 23423);
            out = new PrintWriter(connectionMainNode.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connectionMainNode.getInputStream()));
            System.out.println("(Cliente) Ligação establecida.");
        } catch (IOException e) {
            System.out.println("(Cliente) Ocorreu um erro.");
            System.exit(-1);
        }

        while(true){
            //lógica do client

            String userInput = keyboard.readLine();

            String[] userInputArray = userInput.split(" ");
            String comando = userInputArray[0];

            switch(comando){
                case "R":
                    out.println(userInput);
                    String rResponse = in.readLine();

                    if(rResponse.equals("A")){
                        System.out.println("Item registado com sucesso.");
                        break;
                    }
                    else if(rResponse.equals("B")){
                        System.out.println("Chave existente.");
                        break;
                    }
                    else{
                        System.out.println("Ocorreu um erro.");
                        break;
                    }
                case "C":
                    out.println(userInput);
                    String cResponse = in.readLine();
                    if(cResponse.length() > 1){
                        //se ainda tiver tempo tenho de fazer algo melhor que esta martelada
                        if(cResponse.equals("null")){
                            System.out.println("Ocorreu um erro");
                            break;
                        }
                        System.out.println(cResponse);
                        break;
                    }
                    if(cResponse.equals("B")){
                        System.out.println("Chave inexistente.");
                        break;
                    }
                    else{
                        System.out.println("Ocorreu um erro.");
                        break;
                    }
                case "D":
                    out.println(userInput);
                    String dResponse = in.readLine();
                    if(dResponse.equals("A")){
                        System.out.println("Item removido com sucesso.");
                        break;
                    }
                    if(dResponse.equals("B")){
                        System.out.println("Chave inexistente.");
                        break;
                    }
                    else{
                        System.out.println("Ocorreu um erro.");
                        break;
                    }
                case "Q":
                    System.exit(1);
                default:
                    System.out.println("Ocorreu um erro.");
                    break;

            }

        }

    }
}
