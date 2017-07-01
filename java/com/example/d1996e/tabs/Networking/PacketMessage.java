package com.example.d1996e.tabs.Networking;

/**
 * Created by D1996e on 6/20/2017.
 */

import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Cards.Card;
import java.io.Serializable;
/**
 *
 * @author D1996e
 */
public class PacketMessage implements Serializable{
    public Player playerRefference;
    public Card cardRefference;
    public int op_code;
    public String message;

    public PacketMessage() {} //Used by KryoNet
}
