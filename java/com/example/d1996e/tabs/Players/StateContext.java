package com.example.d1996e.tabs.Players;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.example.d1996e.tabs.Networking.GameServer;
import com.example.d1996e.tabs.Networking.GameClient;
/**
 *
 * @author D1996e
 */
public class StateContext {
    private States myState;
    private static Server server;
    private static Client client;


    public StateContext(Player me) {
        if (me.isServer)
        {
            server = GameServer.getServer();
            setState(new StateTurnOn(server, null));
        }
        else
        {
            client = GameClient.getClient();
            setState(new StateTurnOff(null, client));
        }

    }

    void setState(final States newState) {
        myState = newState;
    }

    public boolean myTurn()
    {
        if (myState instanceof StateTurnOn)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void newTurn(Server server, Client client)
    {
        setState(new StateTurnOn(server, client));
    }


    public void endTurn()
    {
        myState.NextTurn(this);
    }
}

