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
public class GraveZone extends Zone{

    //Constructor calls superClass constructor
    public GraveZone(Player allowedAccess)
    {
        super(allowedAccess);
    }

    @Override
    public void FromGraveToHand(Card card)
    {
        super.FromGraveToHand(card);
        card.moveTo(getAssigPlayer().getZone("graveZone"), getAssigPlayer().getZone("handZone"));
    }

    @Override
    public void FromGraveToDeck(Card card)
    {
        super.FromGraveToDeck(card);
        card.moveTo(getAssigPlayer().getZone("graveZone"), getAssigPlayer().getZone("library"));
    }

    @Override
    public void Revive(Card card)
    {
        super.Revive(card);
        card.moveTo(getAssigPlayer().getZone("graveZone"), getAssigPlayer().getZone("battleZone"));
    }
}
