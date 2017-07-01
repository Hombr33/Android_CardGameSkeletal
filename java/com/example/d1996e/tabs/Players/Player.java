package com.example.d1996e.tabs.Players;

/**
 * Created by D1996e on 6/20/2017.
 */
import java.io.Serializable;
import java.util.ArrayList;
import com.example.d1996e.tabs.Zones.Zone;
import com.example.d1996e.tabs.Cards.Card;

/**
 *
 * @author D1996e
 */
public class Player implements Serializable{
    private int hp;
    public boolean isServer;
    public String name;
    private transient ArrayList deck;
    public transient Player opponent;
    private transient Zone handZone;
    private transient Zone library;
    private transient Zone graveZone;
    private transient Zone battleZone;
    private transient static StateContext sc;


    private Player() {} //Used by KryoNet


    public Player(String name, boolean role){
        this.name = name;
        this.hp = 30;
        this.isServer = role;
    }

    public Player(String name, int value){
        this.name = name;
        this.hp = value;
    }

    public void LoadDeck(ArrayList<Card> newDeck)
    {
        deck = newDeck;
    }

    //Getters and setters
    public ArrayList getDeck() { return deck; }
    public void setDeck(ArrayList newDeck) { deck = newDeck; }
    public ArrayList getHand() { return this.handZone.getList(); }
    public ArrayList getGrave() { return this.graveZone.getList(); }
    public ArrayList getBattle() { return this.battleZone.getList(); }
    public Zone getZone(String zone)
    {
        if (zone.equals("handZone")) { return handZone; }
        if (zone.equals("graveZone")){ return graveZone; }
        if (zone.equals("library")){ return library; }
        if (zone.equals("battleZone")){ return battleZone; }
        return null;
    }
    public StateContext getState() { return sc; }
    public void setState(StateContext s) { sc = s; }
    public void setHandZone(Zone instance) { this.handZone = instance; }
    public void setGraveZone(Zone instance) { this.graveZone = instance; }
    public void setLibrary(Zone instance) { this.library = instance; }
    public void setBattleZone(Zone instance) { this.battleZone = instance; }
    public String getName() { return this.name; }
    public int getHP() { return this.hp; }
}

