package com.example.d1996e.tabs.Networking;

import android.app.Activity;
import android.widget.Toast;

import com.example.d1996e.tabs.Zones.Zone;
import com.example.d1996e.tabs.Table.Table;
import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Cards.Card;
import java.util.ArrayList;

//Network imports
import java.io.IOException;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
/**
 * Created by D1996e on 6/20/2017.
 */

public class GameClient extends Listener {
    private static Client client;
    private static String ip;
    private static int udpPort, tcpPort;
    private static Player me;
    private Activity activity;

    private static boolean establisedConn;

    public GameClient(String _ip, int port, String playerName, Activity activity) throws IOException, InterruptedException, ClassNotFoundException
    {
        ip = _ip;
        udpPort = port;
        tcpPort = port;
        this.activity = activity;
        me = new Player(playerName, false);
        try { runClient(); }
        catch (Throwable e)
        {
            Toast.makeText(activity, "Error connecting to host! Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public static void disconnect()
    {
        client.close();
    }



    private void runClient() throws IOException, InterruptedException, ClassNotFoundException
    {
        client = new Client();
        client.start();

        Zone.client = client;
        Zone.setPort(udpPort);


        client.getKryo().register(PacketMessage.class);
        client.getKryo().register(Card.class);
        client.getKryo().register(Player.class);
        client.getKryo().register(ArrayList.class);


        client.addListener(
                new RunMethodListener<PacketMessage>(PacketMessage.class) {
                    @Override
                    public void run(Connection c, PacketMessage packet) {
                        // op code:
                        //1 ---- Hellos/Turn end Packets
                        //2 ---- Library Zone Methods
                        //3 ---- Hand Zone Methods
                        //4 ---- Battle Zone Methods
                        //5 ---- Grave Zone Methods
                        switch (packet.op_code)
                        {
                            case 1:
                                if (packet.message.equals("Hello"))
                                {
                                    establisedConn = true;
                                    //Update player class atributes and build game setup
                                    me.opponent = packet.playerRefference;
                                    Table.setTableGUI(me, activity);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "Connected!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else if (packet.message.equals("Turn End"))
                                {
                                    me.getState().newTurn(null, client);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Zone.getGUI().setClickableButtons();
                                        }
                                    });

                                    System.out.println("Server has ended his turn");
                                }
                                break;
                            case 2:
                                if (packet.message.contains("has drawn"))
                                {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Card card = new Card("back.png");
                                            Zone.getGUI().oPDrawCardGUI(card);
                                        }
                                    });
                                    //Does not contain card ref
                                    System.out.println(packet.message);
                                }
                                if (packet.message.contains("has milled"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("has shuffled"))
                                {
                                    System.out.println(packet.message);
                                    //Does no contain card ref
                                }
                                break;
                            case 3:
                                if (packet.message.contains("has discarded"))
                                {
                                    // GameServer.var.opponentHandCards--;
                                    Card card = packet.cardRefference;
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Zone.getGUI().oPRemoveCardGUI();
                                        }
                                    });
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("has put"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("has summoned"))
                                {
                                    final Card card = packet.cardRefference;

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Zone.getGUI().oPsummonCardGUI(card);
                                            Zone.getGUI().oPRemoveCardGUI();
                                        }
                                    });

                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                break;
                            case 4:
                                if (packet.message.contains("has destroyed"))
                                {
                                    Card card = packet.cardRefference;
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("to deck"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("to hand"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                break;
                            case 5:
                                if (packet.message.contains("has revived"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("to deck"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                if (packet.message.contains("to hand"))
                                {
                                    System.out.println(packet.message);
                                    //Contains card ref
                                }
                                break;
                            default:
                                break;
                        }

                    }

                    @Override
                    public void connected(Connection c)
                    {
                        //c.setIdleThreshold(5);
                        System.out.println("Connected to the server");
                    }


                    //Todo: will need to check see why activity.finish() does not works
                    @Override
                    public void disconnected(Connection c)
                    {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "The other player disconnected", Toast.LENGTH_SHORT).show();
                            }
                        });
                        activity.finish();
                        return;
                    }
                });


        new Thread("Connect") {
            public void run () {
                try {
                    client.connect(5000, ip, tcpPort, udpPort);
                    PacketMessage HelloPacket = new PacketMessage();
                    //Possible Security Hole here
                    HelloPacket.playerRefference = me;
                    HelloPacket.op_code = 1;
                    HelloPacket.message = "Hello";
                    client.sendTCP(HelloPacket);

                    // Server communication after connection can go here, or in Listener#connected().
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Error connecting to host! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }.start();
    }


    public static Client getClient()
    {
        return client;
    }
}
