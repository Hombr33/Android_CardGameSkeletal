package com.example.d1996e.tabs.Cards;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.example.d1996e.tabs.Zones.Zone;
import com.example.d1996e.tabs.Players.Player;
import java.io.Serializable;


/**
 *
 * @author D1996e
 */
public class Card implements Serializable{

    public Player playerOwner;
    public int cost;
    public int attackPow;
    public double ID;
    public String name;
    public String imgPath;

    private Card() {} //Used by Kryonet

    //backCard has no attributes
    public Card(String path)
    {
        this.imgPath = path;
    }
    //Constructor (keep as simple as possible)
    public Card(Player owner, int cost, int attackPow, String name, double ID, String path){
        this.playerOwner = owner;
        this.cost = cost;
        this.attackPow = attackPow;
        this.name = name;
        this.ID = ID;
        this.imgPath = path;
    }

    //Method to move object from zone to zone
    public void moveTo(Zone z1, Zone z2)
    {
        z1.getList().remove(this);
        z2.getList().add(this);
    }


}
