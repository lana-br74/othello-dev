package se.kth.sda.othello.imp;

/**
 * Created by Shuai on 3/22/17.
 */

/**
 * player's discs
 */
public class Statistic {

    private int P1Discs;
    private int P2Discs;

    public Statistic(int P1Discs, int P2Discs) {
        this.P1Discs = P1Discs;
        this.P2Discs = P2Discs;
    }

    public int getP1Discs()
    { return P1Discs;}

    public int getP2Discs()
    { return P2Discs;}
}