package com.example.d1996e.tabs.Core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.d1996e.tabs.Cards.Card;
import com.example.d1996e.tabs.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class DeckBuildActivity extends Activity {

    private ArrayList<Card> deckToLoad = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_build);

        final Button saveDeck = this.findViewById(R.id.deck_save_button);
        saveDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDeck(view);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.GridView);
        gridview.setAdapter(new ImageAdapter(this));

        //Todo: Need to update this function to add cards to a deck array
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DeckBuildActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(350, 466));
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_8, R.drawable.sample_9,
                R.drawable.sample_10, R.drawable.sample_11,
                R.drawable.sample_12, R.drawable.sample_13,
                R.drawable.sample_14, R.drawable.sample_15,
                R.drawable.sample_16, R.drawable.sample_17,
                R.drawable.sample_18, R.drawable.sample_19,
                R.drawable.sample_20, R.drawable.sample_21,
                R.drawable.sample_22, R.drawable.sample_23,
                R.drawable.sample_24, R.drawable.sample_25,
                R.drawable.sample_26, R.drawable.sample_27,
                R.drawable.sample_28, R.drawable.sample_29,
                R.drawable.sample_30, R.drawable.sample_31,

        };
    }


    //Todo: Don't forget to call activity.finish() on this function
    public void saveDeck(View view)
    {

    }





//    public void loadCards()
//    {
//
//        int deckSize = 40;
//        Random rng = new Random();
//        for (int i = 0; i<deckSize; i++)
//        {
//            try
//            {
//                ImageView imageView = new ImageView(this);
//
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                params.height = 300;
//                params.width = 150;
//                imageView.setLayoutParams(params);
//
//
//
//                InputStream stream = getAssets().open(i + ".png");
//                Bitmap image = BitmapFactory.decodeStream(stream);
//                imageView.setImageBitmap(image);
//
//
//                RelativeLayout rl = (RelativeLayout) findViewById(R.id.Cards_layout);
//                rl.addView(imageView, params);
//            }
//            catch (Throwable e)
//            {
//                System.out.println(e);
//            }
//
//
//        }
//
//    }
}
