package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import com.apps.alvarobanofos.presentview.Models.Ranking;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 27/5/16.
 */
public class RankingResult {
    ArrayList<Ranking> rankings = new ArrayList<>();

    public ArrayList<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(ArrayList<Ranking> rankings) {
        this.rankings = rankings;
    }
}
