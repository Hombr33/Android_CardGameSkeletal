package com.example.d1996e.tabs.Players;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.example.d1996e.tabs.Networking.PacketMessage;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
/**
 *
 * @author D1996e
 */
public class StateTurnOff implements States{
    private static int count = 0;
    private Server server;
    private Client client;


    public StateTurnOff(Server server, Client client)
    {
        this.server = server;
        this.client = client;
    }

    @Override
    public void NextTurn(StateContext context)
    {
        count++;
        System.out.println("Your turn " + count);
        sendMsg();
        context.setState(new StateTurnOn(server, client));
    }



    private void sendMsg()
    {
        final PacketMessage packet = new PacketMessage();
        packet.op_code = 1;
        packet.message = "Turn End";
        if (client != null)
        {
            new Thread( new Runnable() {
                @Override
                public void run() {
                    client.sendTCP(packet);
                }
            }).start();
        }
        else if (server != null)
        {
            new Thread( new Runnable() {
                @Override
                public void run() {
                    server.sendToAllTCP(packet);
                }
            }).start();
        }
    }
}
