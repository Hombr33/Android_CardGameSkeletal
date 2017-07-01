package com.example.d1996e.tabs.Table;

/**
 * Created by D1996e on 6/20/2017.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.app.FragmentManager;

import java.util.ArrayList;

import com.example.d1996e.tabs.MainActivity;
import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Zones.HandZone;
import com.example.d1996e.tabs.Zones.BattleZone;
import com.example.d1996e.tabs.Zones.GraveZone;
import com.example.d1996e.tabs.Zones.Library;
import com.example.d1996e.tabs.Zones.Zone;


/**
 *
 * @author D1996e
 */

public class Table extends FragmentActivity{
    private static Table INSTANCE;
    private static ArrayList<Player> playerList;
    //Pass a player list to create zones for each player
    private Table() { }

    public static Table getInstance() {
        return INSTANCE;
    }

    public static void setTable(Player player)
    {
        player.setHandZone(new HandZone(player));
        player.setGraveZone(new GraveZone(player));
        player.setLibrary(new Library(player));
        player.setBattleZone(new BattleZone(player));

    }


    public static void setTableGUI(Player player, Activity activity)
    {
        GeneralActivity.setPlayer(player);
        Intent myIntent = new Intent(activity, GeneralActivity.class);
        myIntent.putExtra("GeneralActivity", 1);
        activity.startActivity(myIntent);

    }
}
