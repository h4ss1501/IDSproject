package sample;

public class Controller {

    //This controller is the event-handler for the GUI

    //variable
    UDPBroadcastServer udpBroadcastServer;
    UDPConnector udpConnector;

    //methods

    //initialize method
    public void initialize() {
        //this method is executed, when our GUI is ready.

        //instantiating the UDPBroadcastServer class
        udpBroadcastServer = new UDPBroadcastServer();

        //.start is important since it will be the method that starts a new thread.
        new Thread(udpBroadcastServer).start();

        //creating an instance of the UDPConnector
        udpConnector = new UDPConnector(this);

        //based on the above instance we create a new thread
        new Thread(udpConnector).start();



    }

    public void toggleButtonEchoServer(){

        System.out.println("Toggle Echo Server");

    }

    public void toggleButtonBroadcast(){

        System.out.println("Toggle broadcast");

    }

    public void clearTable(){

        System.out.println("Clear Table");

    }

    //method to handle messages
    public void handleMessage(){
        //executed by the echo server instance
        //whatever is put in this method will be updating the table in the GUI.
    }
}
