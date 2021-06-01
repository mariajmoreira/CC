public class HttpGw {

    public static void main(String[] args) {
        System.out.println("> AnonGW started");
        node = new Node();
        try {
            node.setupNode(args);
            node.printNodeInfo();
            System.out.println("> Launched TCPListener");
            node.startTCPListener();
            System.out.println("> Launched TCPSpeaker");
            node.startTCPSpeaker();
            System.out.println("> Launched UDPListener");
            node.startUDPListener();

        } catch (Exception e){
            System.out.println(e);
        }

    }
    }
}
