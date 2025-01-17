package com.utd.aos.project.two;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageListener extends Thread{
    private InputStream inStream;
    private ObjectInputStream objectInputStream;
    private ServerSocket tcpServersocket;
    private Socket socket;
    private final Node node;

    public MessageListener(Node node) {
        this.node = node;
        try {
            tcpServersocket = new ServerSocket(getNode().getPort());
        } catch (IOException e) {
            System.out.println("Error creating server socket: "+ e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run()
    {
        while (true)
        {
            try {
                // receive messages from other processes
                socket = tcpServersocket.accept();
                long rcvdTime = System.currentTimeMillis();
                inStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inStream);
                Object obj = objectInputStream.readObject();
                if(obj instanceof ArrayList) {
                    getNode().getMutexTest().receiveCSIntervals((List<long[]>) obj);
                } else {
                    Message msg = (Message)obj;
                    if(msg.getType().equals(Message.TYPE_REQUEST)) getNode().getMutexService().receiveRequestMessage(msg);
                    else if(msg.getType().equals(Message.TYPE_REPLY)) getNode().getMutexService().receiveReplyMessage(msg);
                    else if(msg.getType().equals(Message.TYPE_RELEASE)) getNode().getMutexService().receiveReleaseMessage(msg);
                    else if(msg.getType().equals(Message.TYPE_TIMESTAMP_REQUEST)) getNode().getMutexTest().receiveTimestampMessage(msg);
                    else if(msg.getType().equals(Message.TYPE_TIMESTAMP_RESPONSE)) getNode().getRequestGenerator().receiveTimestampMessage(msg,rcvdTime);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading message sent to :" + getNode().getID()+e.getMessage());
                System.out.println("Error reading message sent to :" + getNode().getID()+e.getStackTrace());
                try {
                    inStream.close();
                    objectInputStream.close();
                    socket.close();
                } catch (IOException e1) {
                    System.out.println("Error closing connections: "+ e1.getMessage());
                }
            }
        }
    }

    public Node getNode() {
        return node;
    }
}