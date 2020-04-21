package sample;

import java.io.IOException;
import java.net.*;

//our class implements an interface
public class UDPBroadcastServer implements Runnable{

    //this boolean will tell our class to broadcoast or not, will be toggled from the application.
    private boolean broadcast = true;

    /*socket, when doing UDP programming, we gonna need 2 kind of objects: A socket (can send and recieve)  &
    packets (those are the ones that are being send to and recieved )  */
    private DatagramSocket mySocket;


    //the port on which we will broadcast on
    private int broadcastPort = 7007;

   private String message = "Echo server listens on port 7007,answer on port 7007!";



    //getters and setters, to toggle back and forth
    public boolean isBroadcast() {
        return broadcast ;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    //method that can broadcast the message.
    private void broadcastMessage(String message) {

        //instantiating a datagram socket

        /*we need to use a try & catch exception handling, if it goes wrong,
         so the error is caught. (network might not be working etc.) */

        //method will try to create a socket
        try {
            mySocket = new DatagramSocket();

            //broadcasting
            mySocket.setBroadcast(true);

            //byte array containing a message
            byte[] buffer = message.getBytes();

            //creating packet
            DatagramPacket myPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), broadcastPort);
            mySocket.send(myPacket);
            mySocket.close();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //method that calls the other method every 3 seconds

        public void broadcastLoop() {

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //conditional
                if (broadcast) {
                    broadcastMessage(message);
                }
            }
        }




    @Override
    public void run() {
        //this method is what starts the thread!!!
        //when we start this thread we basically start the broadcast loop.

        broadcastLoop();

    }
}
