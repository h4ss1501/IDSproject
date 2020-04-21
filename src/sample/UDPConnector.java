package sample;

import java.io.IOException;
import java.net.*;

public class UDPConnector implements Runnable {

    //fields
    private DatagramSocket mySocket;

    //will be listening on port 7000
    private int udpPort = 7000;
    //echo will be on 7007
    private int udpPortEcho = 7007;

    //instance of our controller, since we would want to communicate with that.
    private Controller myController;

    //boolean that dictates whether we should or should not recieve messages.
    private boolean recievedMessage = true;


    //constructor
    /*when we create an instance of our UDPConnector, we send the Controller along so that it can be used
    to send back messages
    IF TWO CLASSES ARE SUPPOSED TO TALK TO EACHOTHER THEY SHOULD BE AWARE OF EACHOTHER
    */


    public UDPConnector(Controller myController) {

        this.myController = myController;
        setupSocket();

    }

    //setup the socket in this method
    public void setupSocket() {

        try {
            mySocket = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    //method to close socket, apposed to setupSocket
    public void closeSocket(){
        mySocket.close();
    }

    //echoserver
    public void echoServer(){
        do{
            UDPmessage message = receiveMessage();
            sendMessage( "received: "+ message.getMessage(),message.getIp());
        } while (recievedMessage);

    }

    //method that sends a message, sending a message requires creating a packet based on whatever we want to send

    public void sendMessage(byte [] bytes, InetAddress address)
    {
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,address,udpPortEcho);
            //will send from port 7000 --> to port 7007
        try {
            mySocket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //an overloaded method of sendMessage, that allows to send messages as a String
    public void sendMessage (String message, String ip){

        //calls the other sendMessage method, will wrap our String message into a byte array
        try {
            sendMessage(message.getBytes(),InetAddress.getByName(ip));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    //method that receives message, returns in the same format we declared in the UDPmessage class
    public UDPmessage receiveMessage() {

        //we need to prepare a packet, that we fill with the incoming information, stored in a byte array.

        //set byte to maximum amount allowed in UDP 256, if larger needs to be split into different packets.
        byte[] buffer = new byte[256];
        DatagramPacket myPacket = new DatagramPacket(buffer, buffer.length);
        try {
            mySocket.receive(myPacket); //blocking statement: means it will hold forever until we get a message
            //will create a UDPMessage, based on the packet we receive
            UDPmessage myUDPMessage = new UDPmessage(myPacket.getData(), myPacket.getLength(), myPacket.getAddress(), myPacket.getPort()); //
            System.out.println(myUDPMessage);

            return myUDPMessage;
        } catch (IOException e) {
            e.printStackTrace();

            //in case of anything we return null
            return null;
        }

        //when we trigger the receivedMessage method, and none is incoming it will be blocking until we receive a message.
    }

    @Override
    public void run() {
        while (true) {
            echoServer();
        }
    }
}