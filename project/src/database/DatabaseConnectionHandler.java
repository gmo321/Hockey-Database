package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import model.TeamModel;
import util.PrintablePreparedStatement;

public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    //private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ORACLE_URL, "ora_asraut29", "a51475432");
            connection.setAutoCommit(false);
            System.out.println("\nConnected to Oracle!");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertTeam(TeamModel model) {
        try {
            String query = "INSERT INTO Teams VALUES (?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getHome_city());
            ps.setString(2, model.getName());
            ps.setFloat(3, model.getNet_worth());
            ps.setInt(4, model.getWins());
            ps.setInt(5, model.getLosses());
            ps.setInt(6, model.getMatches_played());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteTeam(String homeCity, String nameV) {
        try {
            String query = "DELETE FROM Teams WHERE home_city = '" + homeCity + "' AND name = '" + nameV + "'";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " ERROR IN DELETE ");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateTeams(String homeCity, String nameV, float netWorth, int winsV, int lossesV, int matchesPlayed) {
        try {
            String query = "UPDATE Teams SET net_worth = ?, wins = ?, losses = ?, matches_played = ? WHERE home_city = '" + homeCity + "' AND name = '" + nameV + "'";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setFloat(1, netWorth);
            ps.setInt(2, winsV);
            ps.setInt(3, lossesV);
            ps.setInt(4, matchesPlayed);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " ERROR IN UPDATE ");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ArrayList<TeamModel> selectionTeams(int selection, String homeCity, String nameV, float netWorth, int winsV, int lossesV, int matchesPlayed) {
        try {
            String homeCityF;
            String nameF;
            String netWorthF;
            String winsF;
            String lossesF;
            String matchesPlayedF;

            if ((selection & 1) != 0) {
                homeCityF = "'" + homeCity + "'";
            } else {
                homeCityF = "home_city";
            }
            if ((selection & 2) != 0) {
                nameF = "'" + nameV + "'";
            } else {
                nameF = "name";
            }
            if ((selection & 4) != 0) {
                netWorthF = "'" + netWorth + "'";
            } else {
                netWorthF = "net_worth";
            }
            if ((selection & 8) != 0) {
                winsF = "'" + winsV + "'";
            } else {
                winsF = "wins";
            }
            if ((selection & 16) != 0) {
                lossesF = "'" + lossesV + "'";
            } else {
                lossesF = "losses";
            }
            if ((selection & 32) != 0) {
                matchesPlayedF = "'" + matchesPlayed + "'";
            } else {
                matchesPlayedF = "matches_played";
            }

            String query = "SELECT * FROM Teams WHERE home_city = " + homeCityF + " AND name = " + nameF + " AND net_worth = " + netWorthF + " AND wins = " + winsF + " AND losses = " + lossesF + " AND matches_played = " + matchesPlayedF + "";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ResultSet rs = ps.executeQuery();
            ArrayList<TeamModel> retArray = new ArrayList<>();
            while (rs.next()) {
                retArray.add(new TeamModel(rs.getString("home_city").trim(), rs.getString("name").trim(), rs.getFloat("net_worth"), rs.getInt("wins"), rs.getInt("losses"), rs.getInt("matches_played")));
            }

            ps.close();

            return retArray;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return null;
    }

    public ArrayList<ArrayList<String>> projectionTeams() {
        try {
            String query = "SELECT home_city, name, net_worth FROM Teams";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ResultSet rs = ps.executeQuery();
            ArrayList<ArrayList<String>> retArray = new ArrayList<>();
            ArrayList<String> hcArray = new ArrayList<>();
            ArrayList<String> nameArray = new ArrayList<>();
            ArrayList<String> nwArray = new ArrayList<>();
            while (rs.next()) {
                hcArray.add(rs.getString("home_city").trim());
                nameArray.add(rs.getString("name").trim());
                nwArray.add(Float.toString(rs.getFloat("net_worth")));
            }
            retArray.add(hcArray);
            retArray.add(nameArray);
            retArray.add(nwArray);

            ps.close();

            return retArray;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return null;
    }

    public ArrayList<ArrayList<String>> joinTeamsPlays(int matchID) {
        try {
            String query = "SELECT t.home_city, t.name, t.wins, t.losses FROM Teams t, Play p WHERE t.home_city = p.home_city AND t.name = p.name AND p.match_id = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, matchID);
            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList<String>> retArray = new ArrayList<>();
            ArrayList<String> hcArr = new ArrayList<>();
            ArrayList<String> nameArr = new ArrayList<>();
            ArrayList<String> winArr = new ArrayList<>();
            ArrayList<String> loseArr = new ArrayList<>();

            while (rs.next()) {
                hcArr.add(rs.getString("home_city").trim());
                nameArr.add(rs.getString("name").trim());
                winArr.add(Integer.toString(rs.getInt("wins")));
                loseArr.add(Integer.toString(rs.getInt("losses")));
            }
            retArray.add(hcArr);
            retArray.add(nameArr);
            retArray.add(winArr);
            retArray.add(loseArr);

            return retArray;

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + "ERROR IN JOIN");
            rollbackConnection();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> aggregateGBTeams(String operator, String attribute) {
        try {
            String query = "SELECT home_city, " + operator + "(" + attribute + ") FROM Teams WHERE " + attribute + " > 0 GROUP BY home_city";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList<String>> retArray = new ArrayList<>();
            ArrayList<String> hcArr = new ArrayList<>();
            ArrayList<String> aArr = new ArrayList<>();
            String projectedAttribute = operator + "(" + attribute + ")";

            while (rs.next()) {
                hcArr.add(rs.getString("home_city").trim());
                aArr.add(rs.getString(projectedAttribute));
            }

            retArray.add(hcArr);
            retArray.add(aArr);

            return retArray;

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + "ERROR IN AGB");
            rollbackConnection();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> aggregateHavingTeams(String function, String attribute, String operator, String value) {
        try{
            String query = "SELECT home_city, " + function + "(" + attribute + ") FROM Teams GROUP BY home_city HAVING " + function + "(" + attribute + ")" + operator + value;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList<String>> retArray = new ArrayList<>();
            ArrayList<String> hcArr = new ArrayList<>();
            ArrayList<String> aArr = new ArrayList<>();
            String projectedAttribute = function + "(" + attribute + ")";

            while (rs.next()) {
                hcArr.add(rs.getString("home_city").trim());
                aArr.add(rs.getString(projectedAttribute));
            }

            retArray.add(hcArr);
            retArray.add(aArr);

            return retArray;

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + "ERROR IN AGB");
            rollbackConnection();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> aggregateNested() {
        try {

            //String query = "SELECT * FROM (SELECT home_city, MIN(wins) FROM Teams WHERE wins > 0 GROUP BY home_city)";
            //String query = "SELECT t1.home_city FROM (SELECT home_city, " + operator + "(" + attribute + ")" + " FROM Teams GROUP BY home_city)";
            //String query = "SELECT t1.name, t1.home_city FROM Teams t1 GROUP BY home_city HAVING MIN(t1.wins) <= ALL (SELECT MIN(t2.wins) FROM Teams t2 GROUP BY t1.home_city)";
            String query = "SELECT home_city, MIN(wins) FROM Teams t GROUP BY home_city HAVING MIN(wins) <= all (SELECT MIN(t.WINS) FROM Teams t GROUP BY t.home_city)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList<String>> retArray = new ArrayList<>();

            ArrayList<String> hcArr = new ArrayList<>();
            //ArrayList<String> nameArr = new ArrayList<>();
            ArrayList<String> winArr = new ArrayList<>();
            //ArrayList<String> aArr = new ArrayList<>();
            //String projectedAttribute = operator + "(" + attribute + ")";
            while (rs.next()) {
                hcArr.add(rs.getString("home_city").trim());
                winArr.add(Integer.toString(rs.getInt("MIN(wins)")));
                //aArr.add(rs.getString(projectedAttribute));
            }

            retArray.add(hcArr);
            retArray.add(winArr);
            //retArray.add(aArr);

            return retArray;

        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + "ERROR IN AGB");
            System.out.println(e);
            rollbackConnection();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> divisionSpectators() {
        try {
            String query = "SELECT sin, name FROM Spectators S WHERE NOT EXISTS (SELECT M.match_id FROM Matches M WHERE NOT EXISTS (SELECT W.sin FROM Watch W WHERE M.match_id=W.match_id AND W.sin=S.sin))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList<String>> retArray = new ArrayList<>();
            ArrayList<String> sinArray = new ArrayList<>();
            ArrayList<String> nameArray = new ArrayList<>();

            while (rs.next()) {
                sinArray.add(rs.getString("sin"));
                nameArray.add(rs.getString("name").trim());
            }
            retArray.add(sinArray);
            retArray.add(nameArray);

            return retArray;

        } catch (Exception e) {
            System.out.println("ERROR IN DIVISION");
        }
        return null;
    }

    public ArrayList<ArrayList> GetTeam() {
        try {
            String query = "SELECT * FROM Teams";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            ResultSet rs = ps.executeQuery();

            ArrayList<ArrayList> retArray = new ArrayList<>();
            ArrayList<String> hcArray = new ArrayList<>();
            ArrayList<String> nameArray = new ArrayList<>();
            ArrayList<String> nwArray = new ArrayList<>();
            ArrayList<String> winArray = new ArrayList<>();
            ArrayList<String> lossArray = new ArrayList<>();
            ArrayList<String> matchArray = new ArrayList<>();

            while (rs.next()) {
                hcArray.add(rs.getString("home_city").trim());
                nameArray.add(rs.getString("name").trim());
                nwArray.add(Float.toString(rs.getFloat("net_worth")));
                winArray.add(Integer.toString(rs.getInt("wins")));
                lossArray.add(Integer.toString(rs.getInt("losses")));
                matchArray.add(Integer.toString(rs.getInt("matches_played")));
            }
            retArray.add(hcArray);
            retArray.add(nameArray);
            retArray.add(nwArray);
            retArray.add(winArray);
            retArray.add(lossArray);
            retArray.add(matchArray);

            ps.close();

            return retArray;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return null;
    }

    public static void main(String[] args) {
        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();
        TeamModel tm1 = new TeamModel("Vancouver", "Canucks", 0f, 0, 9999, 9999);
        TeamModel tm2 = new TeamModel("d", "c", 1.5f, 1, 2, 3);
//        dch.insertTeam(tm1);
//        dch.insertTeam(tm2);
//        dch.deleteTeam(tm1.getHome_city(), tm1.getName());
//        dch.deleteTeam("Vancouver", "Canucks");
//        dch.updateTeams("Vancouver", "Canucks", 100000f, 9, 4, 13);
//        ArrayList<TeamModel> teamArray = dch.selectionTeams((17), "Montreal", "Canucks", 11,1111,8,21);
//        for (TeamModel team : teamArray) {
//            System.out.println(team.getHome_city() + " " + team.getName() + " " + team.getNet_worth() + " " + team.getWins() + " " + team.getLosses() + " " + team.getMatches_played());
//        }
//        ArrayList<ArrayList<String>> pArray = dch.projectionTeams();
//        for (int i = 0; i < pArray.get(0).size(); i++) {
//            System.out.println(pArray.get(0).get(i) + " " + pArray.get(1).get(i) + " " + pArray.get(2).get(i));
//        }
//        ArrayList<ArrayList<String>> pArray = dch.joinTeamsPlays(5);
//        for (int i = 0; i < pArray.get(0).size(); i++) {
//            System.out.println(pArray.get(0).get(i) + " " + pArray.get(1).get(i) + " " + pArray.get(2).get(i) + " " + pArray.get(3).get(i));
//        }
//        ArrayList<ArrayList<String>> AGBArray = dch.aggregateGBTeams("MIN", "matches_played");
//        for (int i = 0; i < AGBArray.get(0).size(); i++) {
//            System.out.println(AGBArray.get(0).get(i) + " " + AGBArray.get(1).get(i));
//        }
//
//        ArrayList<ArrayList<String>> AHArray = dch.aggregateHavingTeams("MIN","matches_played", ">", "10");
//        for (int i = 0; i < AHArray.get(0).size(); i++) {
//            System.out.println(AHArray.get(0).get(i) + " " + AHArray.get(1).get(i));
//        }

//        ArrayList<ArrayList<String>> divArray = dch.divisionSpectators();
//        for (int i = 0; i < divArray.get(0).size(); i++) {
//            System.out.println(divArray.get(0).get(i) + " " + divArray.get(1).get(i));
//        }

//        ArrayList<ArrayList<String>> ANArray = dch.aggregateNested();
//        for (int i = 0; i < ANArray.get(0).size(); i++) {
//            System.out.println(ANArray.get(0).get(i));
//        }
    }
}