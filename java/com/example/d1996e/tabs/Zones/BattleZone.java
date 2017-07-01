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
public class BattleZone extends Zone{

    public BattleZone(Player allowedAccess)
    {
        super(allowedAccess);
    }

    @Override
    public void Destroy(Card card)
    {
        super.Destroy(card);
        card.moveTo(getAssigPlayer().getZone("battleZone"), getAssigPlayer().getZone("graveZone"));
    }

    @Override
    public void FromBattleToHand(Card card)
    {
        super.FromBattleToHand(card);
        card.moveTo(getAssigPlayer().getZone("battleZone"), getAssigPlayer().getZone("handZone"));
    }

    @Override
    public void FromBattleToDeck(Card card)
    {
        super.FromBattleToDeck(card);
        card.moveTo(getAssigPlayer().getZone("battleZone"), getAssigPlayer().getZone("library"));
    }
}
