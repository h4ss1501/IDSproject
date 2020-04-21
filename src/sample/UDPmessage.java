package sample;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPmessage {

    //fields
        //all of these fields represent information that will come with the sent packet
        private String time;
        private String message;
        private String ip;
        private int length;
        private int port;

        public UDPmessage (String message, String ip, int port) {
            this.message = message;
            this.ip = ip;
            this.port = port;
            this.length = message.length();

            //time formatting

            //we create a formatter variable to our desired format
            SimpleDateFormat myFormatter;
            myFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            //we get the  current time, and then format that to the desired format.
            Date time = new Date();
            this.time = myFormatter.format(time);
        }

        //similar constructor but will take a byte array instead.
        public UDPmessage(byte [] message, int length, InetAddress ip, int port)
        {
            //we convert it to what we need, and call the first constructor again
            this(new String(message, 0,length),ip.getHostAddress(), port);

        }

//getters
        public String getTime() {
            return time;
        }

        public String getMessage() {
            return message;
        }

        public String getIp() {
            return ip;
        }

        public int getLength() {
            return length;
        }

        public int getPort() {
            return port;
        }

        //toString method

    @Override
    public String toString() {
        return "UDPmessage{" +
                "time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", ip='" + ip + '\'' +
                ", length=" + length +
                ", port=" + port +
                '}';
    }
}
