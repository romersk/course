package server;

import database.MyDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServer extends Thread{
    private Socket socket;
    private ObjectInputStream in;
    private MyDatabase database;
    private ObjectOutputStream out;

    public ThreadServer(Socket s) throws IOException, ClassNotFoundException{
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        try {
            database = new MyDatabase();
            System.out.println("Сервер соединен с базой данных");
        } catch (SQLException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        start();
    }

    public void run() {
        Integer idOperation = -1;
        ServerWork obj = new ServerWork(in, database, out);
        try {
            while (true) {
                idOperation = (Integer) in.readObject();
                obj.getId(idOperation);
            }
        }
        catch (IOException ex) {
            System.err.println("IO Exception");
            System.out.println("Клиент был отключен");
        } catch (SQLException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                database.close();
            }
            catch (IOException ex) {
                System.err.println("Socket not closed");
            }
        }
    }
}
