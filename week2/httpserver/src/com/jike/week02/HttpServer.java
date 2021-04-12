package com.jike.week02;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new  ServerSocket(8801);
        while (true){
            try {
                Socket socket=serverSocket.accept();
                httpServer(socket);
            }catch (IOException exception){
            }
        }
    }


    private static  void httpServer(Socket socket){
        try {
            PrintWriter writer=new PrintWriter(socket.getOutputStream(),true);
            writer.println("HTTP/1.1 200 ok");
            writer.println("Content-Type:text/html;charset=utf-8");
            String str="hello,socketServer!";
            writer.println("Content-Length:"+str.getBytes().length);
            writer.println();
            writer.write(str);
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
