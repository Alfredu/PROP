package com.hidatosdecarbono;

public class RankingCTRL {

    private Ranking rankingFacil;
    private Ranking rankingMedio;
    private Ranking rankingDificil;

    public RankingCTRL() {
        this.rankingFacil = new Ranking();
        this.rankingMedio = new Ranking();
        this.rankingDificil = new Ranking();
    }

    public Ranking getRankingFacil() {
        return rankingFacil;
    }

    public void setRankingFacil(Ranking rankingFacil) {
        this.rankingFacil = rankingFacil;
    }

    public Ranking getRankingMedio() {
        return rankingMedio;
    }

    public void setRankingMedio(Ranking rankingMedio) {
        this.rankingMedio = rankingMedio;
    }

    public Ranking getRankingDificil() {
        return rankingDificil;
    }

    public void setRankingDificil(Ranking rankingDificil) {
        this.rankingDificil = rankingDificil;
    }
}
