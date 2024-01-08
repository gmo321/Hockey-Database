package model;

public class TeamModel {
    private final String home_city;
    private final String name;
    private final float net_worth;
    private final int wins;
    private final int losses;
    private final int matches_played;

    public TeamModel(String home_city, String name, float net_worth, int wins, int losses, int matches_played) {
        this.home_city = home_city;
        this.name = name;
        this.net_worth = net_worth;
        this.wins = wins;
        this.losses = losses;
        this.matches_played = matches_played;
    }

    public String getHome_city() {
        return home_city;
    }

    public String getName() {
        return name;
    }

    public float getNet_worth() {
        return net_worth;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getMatches_played() {
        return matches_played;
    }
}
