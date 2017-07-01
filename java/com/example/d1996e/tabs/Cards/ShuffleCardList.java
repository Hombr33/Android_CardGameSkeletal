package com.example.d1996e.tabs.Cards;

/**
 * Created by D1996e on 6/20/2017.
 */
import java.util.List;
import java.util.Random;

public class ShuffleCardList {
    public static void shuffleList(List<Card> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(List<Card> a, int i, int change) {
        Card helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }
}
