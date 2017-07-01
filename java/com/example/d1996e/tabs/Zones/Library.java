package com.example.d1996e.tabs.Zones;

/**
 * Created by D1996e on 6/20/2017.
 */

import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Cards.Card;
import com.example.d1996e.tabs.Cards.ShuffleCardList;

//Network imports
/**
 *
 * @author D1996e
 */
public class Library extends Zone{
    //Constructor calls superClass constructor
    public Library(Player allowedAccess)
    {
        super(allowedAccess);
        list = assignedPlayer.getDeck();
    }

    @Override
    public void Shuffle()
    {
        ShuffleCardList.shuffleList(list);
        assignedPlayer.setDeck(list);
        super.Shuffle();
    }

    //Implemented functions (overrided)
    @Override
    public void DrawCard(Card card)
    {
        //local code
        if (this.assignedPlayer.getZone("handZone").getList().size() < 6)
        {
            list.get(0).moveTo(getAssigPlayer().getZone("library"), getAssigPlayer().getZone("handZone"));
            super.DrawCard(card);
        }
        else
        {
            super.getGUI().ToastMessage("You already have the maximum cards in hand");
        }


    }

    @Override
    public void DrawNCards(int n)
    {
        for (int i = 0; i < n; i++)
        {
            list.get(i).moveTo(getAssigPlayer().getZone("library"), getAssigPlayer().getZone("graveZone"));
        }
        super.DrawNCards(n);
    }

    @Override
    public void MillCard()
    {
        list.get(0).moveTo(getAssigPlayer().getZone("library"), getAssigPlayer().getZone("graveZone"));
        super.MillCard();
    }

    @Override
    public void MillNCards(int n)
    {
        for (int i = 0; i < n; i++)
        {
            list.get(i).moveTo(getAssigPlayer().getZone("library"), getAssigPlayer().getZone("graveZone"));
        }
        super.MillNCards(n);
    }

}