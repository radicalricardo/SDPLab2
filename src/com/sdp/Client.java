package com.sdp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    private static Socket connectionMainNode = null;
    private static PrintWriter out;
    private static BufferedReader in;
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {


        while(true){
            //logica do client
            String userInput = keyboard.readLine();

            try {
                connectionMainNode = new Socket("127.0.0.1", 23423);
                out = new PrintWriter(connectionMainNode.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(connectionMainNode.getInputStream()));
                System.out.println("(Cliente) Ligação establecida.");
            } catch (IOException e) {
                System.out.println("(Cliente) Ocorreu um erro.");
                System.exit(-1);
            }
            
            String[] userInputArray = userInput.split(" ");
            String comando = userInputArray[0];

            switch(comando){
                case "R":
                    out.println(userInput);
                    break;
                case "C":
                    out.println(userInput);
                    break;
                case "D":
                    out.println(userInput);
                    break;
                case "Q":
                    out.println(userInput);
                    break;

            }

        }

    }
}
