package com.example.d1996e.tabs.Zones;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.example.d1996e.tabs.Cards.Card;
import com.example.d1996e.tabs.Players.Player;
import java.util.ArrayList;

//Network imports
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.example.d1996e.tabs.Networking.PacketMessage;
import com.example.d1996e.tabs.Table.GeneralActivity;

import java.io.IOException;

/**
 *
 * @author D1996e
 */
public abstract class Zone{
    protected final Player assignedPlayer;
    protected ArrayList<Card> list;
    public static Client client = null;
    public static Server server = null;
    private static int tcpPort;

    private static GeneralActivity gui;

    //Constructor initializes array (cards space for each zone) and ownerPlayer
    public Zone(Player allowedAccess)
    {
        this.list = new ArrayList<>();
        this.assignedPlayer = allowedAccess;
    }

    //Util Function
    public void PrintList()
    {
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).ID + "owner: " + list.get(i).playerOwner);
        }
    }



    private static void SendThrough(Card cardRefference, int op_code, String message) throws IOException
    {
        final PacketMessage packet = new PacketMessage();
        packet.cardRefference = cardRefference; //Possible Security Hole here
        packet.op_code = op_code;
        packet.message = message;
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
        else { System.out.println("local -> Nothing sent"); } //debugging purposes
    }


    public Card ReturnNCard(int n)
    {
        return list.get(n);
    }

    public Card ReturnIDCard(double id)
    {
        for (Card card : list)
        {
            if (card.ID == id)
            {
                return card;
            }
        }
        return null;
    }

    //Getters and setters
    public ArrayList getList() { return list; }
    public Player getAssigPlayer() { return assignedPlayer; }
    public static  GeneralActivity getGUI() { return gui; }
    public void setList(ArrayList newArray) { this.list = newArray; }
    public static void setPort(int port) { tcpPort = port; }
    public static void setGUI(GeneralActivity activity) { gui = activity; }

    //Implementations of various functions
    //------------------------------Library--------------------
    public void DrawCard(Card card)
    {
        System.out.println("Drawn a card" );
        //Local User Interface code to be implemented here
        try { gui.drawCardGUI(card); }
        catch (NullPointerException e)
        {
            System.out.println("Not Enough Cards");
        }

        //net code
        try { SendThrough(null, 2, this.assignedPlayer.name + " has drawn a card"); } //Security hole if not null
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }


    }
    public void DrawNCards(int n)
    {
        System.out.println("Drawn " + n + " cards");
        //Local Interface code to be implemented here

        try { SendThrough(null, 2, this.assignedPlayer.name + " has drawn " + n + " cards"); }  //Security hole if not null
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void MillCard()
    {
        System.out.println("Milled" + list.get(0).name);
        //Local Interface code to be implemented here


        //net code
        try { SendThrough(list.get(0), 2, this.assignedPlayer.name + " has milled " + list.get(0).name); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void MillNCards(int n)
    {
        //Local Interface code to be implemented here

        for (int i = 0; i < n; i++)
        {
            System.out.println("Milled" + list.get(i).name);
            try { SendThrough(list.get(i), 2, this.assignedPlayer.name + " has milled " + list.get(i).name); }
            catch (IOException e)
            {
                System.out.println("TO BE IMPLEMENTED");

            }
        }
    }
    public void Shuffle()
    {
        System.out.println("Shuffled");

        //net code
        try { SendThrough(null, 2, this.assignedPlayer.name + " has shuffled his deck"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    //-----------------------------Hand Zone-----------------
    public void PutOnTop(Card card)
    {
        System.out.println("Put back on " + card.name);


        //net code
        try { SendThrough(card, 3, this.assignedPlayer.name + " has put " + card.name + " on top of his deck"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void Discard(Card card)
    {
        System.out.println("Discarded " + card.name);

        //net code
        try { SendThrough(card, 3, this.assignedPlayer.name + " has discarded " + card.name); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void Summon(Card card)
    {
        System.out.println("Summoned " + card.name);

        try { gui.summonCardGUI(card); }
        catch (NullPointerException e)
        {
            System.out.println("Error in Zone -> Summon -> gui.summoncardGUI");
        }

        //net code
        try { SendThrough(card, 3, this.assignedPlayer.name + " has summoned " + card.name); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");
        }
    }
    //------------------Grave Zone------------------------------------
    public void Revive(Card card)
    {
        System.out.println("Revived " + card.name);


        //net code
        try { SendThrough(card, 5, this.assignedPlayer.name + " has revived " + card.name); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void FromGraveToDeck(Card card)
    {
        System.out.println("Returned " + card.name + " to deck");


        //net code
        try { SendThrough(card, 5, this.assignedPlayer.name + " has returned " + card.name + " to deck"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void FromGraveToHand(Card card)
    {
        System.out.println("Returned " + card.name + " to hand");


        //net code
        try { SendThrough(card, 5, this.assignedPlayer.name + " has returned " + card.name + " to hand"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    //--------------------Battle Zone-----------------------
    public void Destroy(Card card)
    {
        System.out.println("Destroyed " + card.name);



        //net code
        try { SendThrough(card, 4, this.assignedPlayer.name + " has destroyed " + card.name); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void FromBattleToDeck(Card card)
    {
        System.out.println("Returned " + card.name + " to deck");


        //net code
        try { SendThrough(card, 4, this.assignedPlayer.name + " has returned " + card.name + " to deck"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }
    public void FromBattleToHand(Card card)
    {
        System.out.println("Returned " + card.name + " to hand");


        //net code
        try { SendThrough(card, 4, this.assignedPlayer.name + " has returned " + card.name + " to hand"); }
        catch (IOException e)
        {
            System.out.println("TO BE IMPLEMENTED");

        }
    }


}