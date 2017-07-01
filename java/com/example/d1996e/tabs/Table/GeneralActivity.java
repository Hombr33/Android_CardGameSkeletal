package com.example.d1996e.tabs.Table;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.d1996e.tabs.Cards.Card;
import com.example.d1996e.tabs.Networking.GameClient;
import com.example.d1996e.tabs.Networking.GameServer;
import com.example.d1996e.tabs.Players.Player;
import com.example.d1996e.tabs.Players.StateContext;
import com.example.d1996e.tabs.R;
import com.example.d1996e.tabs.Zones.Zone;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by D1996e on 6/23/2017.
 */

public class GeneralActivity extends Activity
{
    private static Player me;

    public static void setPlayer(Player player)
    {
        me = player;
    }

    private boolean terminated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_game);

        Zone.setGUI(this);
        me.setState(new StateContext(me));


        final Button draw = (Button) findViewById(R.id.drawcard_button);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawCard(view);
            }
        });

        final Button loadDeck = (Button) findViewById(R.id.customdeck_button);
        loadDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeck(view);
            }
        });

        final Button endTurn = (Button) findViewById(R.id.endturn_button);
        endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTurn(view);
                endTurn.setClickable(false);
                endTurn.setAlpha(.5f);
                draw.setClickable(false);
                draw.setAlpha(.5f);
                loadDeck.setClickable(false);
                loadDeck.setAlpha(.5f);
            }
        });

        final Button endGame = (Button) findViewById(R.id.endgame_button);
        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminated = true;
                if(me.isServer)
                {
                    GameServer.disconnect();
                }
                else
                {
                    GameClient.disconnect();
                }
                finish();
            }
        });

        //Todo: Will need to uncomment this when finished developing the main GUI
//        if (!terminated) {
//            if (!me.isServer) {
//                endTurn.setClickable(false);
//                endTurn.setAlpha(.5f);
//                draw.setClickable(false);
//                draw.setAlpha(.5f);
//                loadDeck.setClickable(false);
//                loadDeck.setAlpha(.5f);
//            }
//        }
    }


    //GUI functions
    public void ToastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    public void setClickableButtons()
    {
        Button endturn_button = (Button) (this).findViewById(R.id.endturn_button);
        endturn_button.setClickable(true);
        endturn_button.setAlpha(1f);
        Button customdeck_button = (Button) (this).findViewById(R.id.customdeck_button);
        customdeck_button.setClickable(true);
        customdeck_button.setAlpha(1f);
        Button drawcard_button = (Button) (this).findViewById(R.id.drawcard_button);
        drawcard_button.setClickable(true);
        drawcard_button.setAlpha(1f);
    }


    public void summonCardGUI(Card card)
    {
        try
        {
            final double id = card.ID;
            final ImageView imageView = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.generalgame_layout);
            final int cardH = rl.getHeight()/5;
            final int cardW = rl.getWidth()/6;
            params.height = cardH;
            params.width = cardW;
            imageView.setLayoutParams(params);

            InputStream stream = getAssets().open(card.imgPath);
            Bitmap image = BitmapFactory.decodeStream(stream);
            imageView.setImageBitmap(image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (me.getState().myTurn()) {
                        PopupMenu popup = new PopupMenu(getApplicationContext(), imageView);
                        popup.getMenuInflater().inflate(R.menu.summon_popup_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Destroy Card")) {
                                    me.getZone("battleZone").Destroy(me.getZone("battleZone").ReturnIDCard(id));
                                    LinearLayout ll = (LinearLayout) findViewById(R.id.me_battlezone);
                                    ll.removeView(imageView);
                                    return true;
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                }
            });

            LinearLayout ll = (LinearLayout) findViewById(R.id.me_battlezone);
            ll.addView(imageView, params);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void drawCardGUI(Card card)
    {
        try
        {
            final double id = card.ID;
            final ImageView imageView = new ImageView(getApplicationContext());
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.generalgame_layout);
            final int cardH = rl.getHeight()/5;
            final int cardW = rl.getWidth()/6;
            params.height = cardH;
            params.width = cardW;
            imageView.setLayoutParams(params);

            InputStream stream = getAssets().open(card.imgPath);
            Bitmap image = BitmapFactory.decodeStream(stream);
            imageView.setImageBitmap(image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (me.getState().myTurn()) {
                        PopupMenu popup = new PopupMenu(getApplicationContext(), imageView);
                        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("Summon Card")) {
                                    me.getZone("handZone").Summon(me.getZone("handZone").ReturnIDCard(id));
                                    LinearLayout ll = (LinearLayout) findViewById(R.id.me_hand);
                                    ll.removeView(imageView);
                                    return true;
                                } else if (item.getTitle().equals("Discard Card")) {
                                    me.getZone("handZone").Discard(me.getZone("handZone").ReturnIDCard(id));
                                    LinearLayout ll = (LinearLayout) findViewById(R.id.me_hand);
                                    ll.removeView(imageView);
                                    return true;
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                }
            });


            LinearLayout ll = (LinearLayout) findViewById(R.id.me_hand);
            ll.addView(imageView, params);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void oPsummonCardGUI(Card card)
    {
        try
        {
            ImageView imageView = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.generalgame_layout);
            final int cardH = rl.getHeight()/5;
            final int cardW = rl.getWidth()/6;
            params.height = cardH;
            params.width = cardW;
            imageView.setLayoutParams(params);

            InputStream stream = getAssets().open(card.imgPath);
            Bitmap image = BitmapFactory.decodeStream(stream);
            imageView.setImageBitmap(image);

            LinearLayout ll = (LinearLayout) findViewById(R.id.opponent_battlezone);
            ll.addView(imageView, params);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void oPRemoveCardGUI()
    {
        try
        {
            LinearLayout ll = (LinearLayout) findViewById(R.id.opponent_hand);
            ll.removeViewAt(0);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void oPDrawCardGUI(Card card)
    {
        try
        {
            ImageView imageView = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.generalgame_layout);
            final int cardH = rl.getHeight()/5;
            final int cardW = rl.getWidth()/6;
            params.height = cardH;
            params.width = cardW;
            imageView.setLayoutParams(params);

            InputStream stream = getAssets().open(card.imgPath);
            Bitmap image = BitmapFactory.decodeStream(stream);
            imageView.setImageBitmap(image);

            LinearLayout ll = (LinearLayout) findViewById(R.id.opponent_hand);
            ll.addView(imageView, params);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }




    //functionality of buttons
    public void drawCard(View view)
    {
        try
        {
            Card card = me.getZone("library").ReturnNCard(0);
            me.getZone("library").DrawCard(card);
        } catch (NullPointerException e)
        {
            Toast.makeText(this, "Not enough cards", Toast.LENGTH_SHORT).show();
        }

    }


    //Todo: Need to update this function serialize a deck into a custom file format
    public void loadDeck(View view) {
        ArrayList<Card> deckToLoad = new ArrayList<Card>();
        int deckSize = 40;
        Random rng = new Random();
        for (int i = 0; i<deckSize; i++)
        {
            deckToLoad.add(new Card(me, 2, 2000, "test", rng.nextDouble(),"c-" + i + ".png"));
        }
        me.LoadDeck(deckToLoad);
        Table.setTable(me);
    }

    public void endTurn(View view)
    {
        me.getState().endTurn();
    }
}
