package com.example.d1996e.tabs.Networking;

//Game imports

import android.app.Activity;
import android.widget.Toast;

import com.example.d1996e.tabs.Zones.Zone;
import com.example.d1996e.tabs.Table.Table;
import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Cards.Card;
import java.util.ArrayList;

//Network imports
import java.io.IOException;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;
/**
 * Created by D1996e on 6/20/2017.
 */

public class GameServer extends Listener {
    private static Server server;
    private static int udpPort, tcpPort;
    private static Player me;
    private Activity activity;


    public GameServer(int port, String playerName, Activity activity) throws IOException, InterruptedException, ClassNotFoundException
    {
        udpPort = port;
        tcpPort = port;
        this.activity = activity;
        me = new Player(playerName, true);
        try { runServer(); }
        catch (Throwable e)
        {
            Toast.makeText(activity, "Error hosting the game! Please try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public static void disconnect()
    {
        server.close();
    }


    private void runServer() throws IOException, InterruptedException, ClassNotFoundException{
        System.out.println("Creating server");
        server = new Server();

        Zone.server = server;
        Zone.setPort(udpPort);

        server.addListener(
                new RunMethodListener<PacketMessage>(PacketMessage.class) {
                    @Override
                    public void run(Connection c, PacketMessage packet){
                        // op code:
                        //1 ---- Hellos/Turn End packets
                        //2 ---- Library Zone Methods
                        //3 ---- Hand Zone Methods
                        //4 ---- Battle Zone Methods
                        //5 ---- Grave Zone Methods
                        switch (packet.op_code)
                        {
                            case 1:
                                if (packet.message.equals("Hello"))
                                {
                                    PacketMessage HelloPacket = new PacketMessage();

                                    me.opponent = packet.playerRefference;
                                    Table.setTableGUI(me, activity);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, "Connected!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    //Possible Security Hole here
                                    HelloPacket.playerRefference = me;
                                    HelloPacket.op_code = 1;
                                    HelloPacket.message = "Hello";
                                    c.sendTCP(HelloPacket);
                                    //Respond to client with your refference

                                }
                                else if (packet.message.equals("Turn End"))
                                {
                                    me.getState().newTurn(server, null);


                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Zone.getGUI().setClickableButtons();
                                        }
                                    });
                                    //me.getZone("library").DrawCard(me.getZone("library").ReturnNCard(0));
                                    System.out.println("Client has ended his turn");
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
                        System.out.println("Client Connected on server");
                    }


                    //Todo: will need to check see why activity.finish() does not work
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


        server.getKryo().register(PacketMessage.class);
        server.getKryo().register(Card.class);
        server.getKryo().register(Player.class);
        server.getKryo().register(ArrayList.class);

        server.bind(tcpPort, udpPort);
        server.start();
//        server.bind(tcpPort, udpPort);
        System.out.println("Listening....");

    }




    public static Server getServer()
    {
        return server;
    }

    //Util Functions
    public void Verify()
    {

    }
}
