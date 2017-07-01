package com.example.d1996e.tabs.Zones;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Cards.Card;

/**
 *
 * @author D1996e
 */
public class HandZone extends Zone{
    public int maxHandSize = 7; //Do something with those in future
    public int startHandSize = 5;

    //Constructor calls superClass constructor
    public HandZone(Player allowedAccess)
    {
        super(allowedAccess);
    }

    @Override
    public void PutOnTop(Card card)
    {
        super.PutOnTop(card);
        card.moveTo(getAssigPlayer().getZone("handZone"), getAssigPlayer().getZone("library"));
    }

    @Override
    public void Discard(Card card)
    {
        card.moveTo(getAssigPlayer().getZone("handZone"), getAssigPlayer().getZone("graveZone"));
        super.Discard(card);
    }

    @Override
    public void Summon(Card card)
    {
        super.Summon(card);
        card.moveTo(getAssigPlayer().getZone("handZone"), getAssigPlayer().getZone("battleZone"));
    }

    //Util functions

}
