

import java.io.DataInputStream;
import java.net.Socket;
import java.util.Observable;

public class HiloServer extends Observable implements Runnable {
    String recibido;
    Socket socket;
    DataInputStream dinput;
    int jugadores;
    
    public HiloServer (DataInputStream dinput, Socket socket){
        this.socket = socket;
        this.dinput = dinput;
    }
    
    @Override
    public void run() {
        try{
           //while loop que escucha el cliente
           //¿qué hay que escuchar?: # de jugadores, quien agarró cual token, qué tipo de token, el valor del token 
           while (true){
                System.out.print("Listening to Client");
                try {
                    byte[] lenBytes = new byte[4];
                    dinput.read(lenBytes, 0, 4);
                    int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                              ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
                    byte[] receivedBytes = new byte[len];
                    dinput.read(receivedBytes, 0, len);
                    String received = new String(receivedBytes, 0, len);
                    this.setChanged();
                    this.notifyObservers(recibido);  
                    this.clearChanged();
                }
                catch (Exception e){
                    //Exceptions
                }
                              
            }  
        } catch (Exception e){
            
        }
    }
    
    public int set_jugadores(int players){
        jugadores = players;
        return jugadores;
    }
}
