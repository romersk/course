package com.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream objectInputStream;

    public Client(InetAddress ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String message) throws IOException {
        writer.writeObject(message);
    }

    public void writeInt(int choice) throws IOException {
        writer.writeObject(choice);
    }

    public void writeObject(Object obj) throws IOException {
        writer.writeObject(obj);
    }

    public String readLine() throws IOException {
        return objectInputStream.readLine();
    }

    public Object getObject() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }
}
