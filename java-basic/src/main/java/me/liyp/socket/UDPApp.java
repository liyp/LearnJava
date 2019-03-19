package me.liyp.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPApp {

    static class Server implements Runnable {
        private DatagramSocket socket = new DatagramSocket(4445);

        private boolean running;

        private byte[] buf = new byte[256];

        Server() throws SocketException {
        }

        @Override
        public void run() {
            running = true;
            try {
                while (running) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("server: " + received);
                    if (received.equals("end")) {
                        running = false;
                        continue;
                    }
                    socket.send(packet);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Client {
        private DatagramSocket socket = new DatagramSocket();
        private InetAddress address = InetAddress.getByName("localhost");
        private byte[] buf;

        Client() throws SocketException, UnknownHostException {
        }

        public String sendEcho(String msg) {
            try {
                buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                socket.send(packet);
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("client: " + received);
                return received;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Client client = new Client();

        new Thread(server).start();

        client.sendEcho("1");
        client.sendEcho("2");
        client.sendEcho("3");
        client.sendEcho("end");
    }
}
