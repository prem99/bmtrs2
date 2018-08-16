import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

class DatabaseInterfacer {
    private static DatabaseInterfacer singleton = new DatabaseInterfacer();

    private Connection conn;
    private ResultSet rs;
    private Statement stmt;

    private DatabaseInterfacer() {
        try {
            String url = "jdbc:postgresql://localhost/bmtrs?user=" + UserInformation.LOCAL_USER + "&password=" + UserInformation.LOCAL_PASS;
            conn = DriverManager.getConnection(url);
            System.out.println(conn.toString() +
                    " | SQL Connection established...");
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                    System.out.println(sqlEx.getErrorCode());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                    System.out.println(sqlEx.getErrorCode());
                }
            }
            stmt = null;
            rs = null;
        }
    }

    void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { }
        }
        stmt = null;
        rs = null;
        System.out.println("SQL Connection closed...");
    }

    static DatabaseInterfacer getInstance() {
        return singleton;
    }

    private ResultSet query(String sqlStatement) {
        try {
            rs = stmt.executeQuery(sqlStatement);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    private ResultSet query(PreparedStatement sqlStatement) {
        try {
            rs = sqlStatement.executeQuery();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    boolean execute(String sqlStatement) {
        try {
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }

    boolean execute(PreparedStatement sqlStatement) {
        try {
            sqlStatement.executeUpdate();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }

    boolean attemptLogin(String email, String password) {
        ResultSet set = query(
                "SELECT * " +
                "FROM UserInfo " +
                "WHERE Email='" + email + "' AND Password='" + password + "';");
        try {
            set.first();
            CurrentUserInfo.getInstance().setUser(set.getString(1));
            return true;
        } catch (SQLException e) {
            System.out.println("Could not load user " + e.getMessage());
        }
        return false;
    }

//    String[] currentMuseumList() {
//        ArrayList<String> museumList = new ArrayList<>();
//        ResultSet set = query("SELECT * FROM Museum");
//        try {
//            set.first();
//            museumList.add(set.getString(1));
//            while (set.next()) {
//                museumList.add(set.getString(1));
//            }
//        } catch (SQLException e) {
//            System.out.println("Closed Result Set " + e.getMessage());
//        }
//        return museumList.toArray(new String[museumList.size()]);
//    }

    ArrayList[] currentExhibitList(String mName) {
        ArrayList<String> exhibitList = new ArrayList<String>();
        ArrayList<Integer> yearList = new ArrayList<Integer>();
        ArrayList<String> urlList = new ArrayList<String>();
        ArrayList[] combined = new ArrayList[3];

        ResultSet set = query("SELECT Name, Year, URL FROM Exhibit WHERE mName = '" + mName + "' ORDER BY Year;");
        try {
            set.first();
            exhibitList.add(set.getString(1));
            yearList.add(set.getInt(2));
            urlList.add(set.getString(3));
            while (set.next()) {
                exhibitList.add(set.getString(1));
                yearList.add(set.getInt(2));
                urlList.add(set.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        combined[0] = exhibitList;
        combined[1] = yearList;
        combined[2] = urlList;
        return combined;
    }

    ArrayList[] currentReviewList(String mName) {
        ArrayList<String> reviewList = new ArrayList<String>();
        ArrayList<Integer> scoreList = new ArrayList<Integer>();
        ArrayList<Float> average = new ArrayList<Float>();
        ArrayList[] combined = new ArrayList[3];

        ResultSet set = query("SELECT Text, Score FROM Review WHERE mName = '" + mName + "';");
        try {
            set.first();
            reviewList.add(set.getString(1));
            scoreList.add(set.getInt(2));
            while (set.next()) {
                reviewList.add(set.getString(1));
                scoreList.add(set.getInt(2));
            }

        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        combined[0] = reviewList;
        combined[1] = scoreList;

        return combined;
    }

    ArrayList[] myTicketsList(String userName) {
        ArrayList<String> museumList = new ArrayList<>();
        ArrayList<Timestamp> timestampList = new ArrayList<>();
        ArrayList<BigDecimal> priceList = new ArrayList<>();
        ArrayList[] combined = new ArrayList[3];

        ResultSet set = query("SELECT MName, Timestamp, CostAtPurchase FROM Ticket WHERE VEmail = '" + userName + "' ORDER BY Timestamp;");
        try {
            set.first();
            museumList.add(set.getString(1));
            timestampList.add(set.getTimestamp(2));
            priceList.add(set.getBigDecimal(3));
            while (set.next()) {
                museumList.add(set.getString(1));
                timestampList.add(set.getTimestamp(2));
                priceList.add(set.getBigDecimal(3));
            }
        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        combined[0] = museumList;
        combined[1] = timestampList;
        combined[2] = priceList;
        return combined;
    }

    ArrayList[] myReviewsList(String userName) {
        ArrayList<String> museumList = new ArrayList<>();
        ArrayList<String> reviewList = new ArrayList<>();
        ArrayList<Integer> ratingList = new ArrayList<>();
        ArrayList[] combined = new ArrayList[3];

        ResultSet set = query("SELECT MName, Text, Score FROM Review WHERE VEmail = '" + userName + "' ORDER BY MName;");
        try {
            set.first();
            museumList.add(set.getString(1));
            reviewList.add(set.getString(2));
            ratingList.add(set.getInt(3));
            while (set.next()) {
                museumList.add(set.getString(1));
                reviewList.add(set.getString(2));
                ratingList.add(set.getInt(3));
            }
        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        combined[0] = museumList;
        combined[1] = reviewList;
        combined[2] = ratingList;
        return combined;
    }

    ArrayList[] activeRequestList() {
        ArrayList<String> visitor = new ArrayList<>();
        ArrayList<String> museum = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String>[] combined = new ArrayList[3];

        ResultSet set = query("SELECT VEmail, MName, Date FROM Curator_Request WHERE ApprovedFlag IS NULL AND " +
                "MName NOT IN (SELECT Name FROM Museum WHERE CEmail IS NOT NULL) ORDER BY VEmail;");
        try {
            set.first();
            visitor.add(set.getString(1));
            museum.add(set.getString(2));
            date.add(set.getDate(3).toString());
            while (set.next()) {
                visitor.add(set.getString(1));
                museum.add(set.getString(2));
                date.add(set.getDate(3).toString());
            }
        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        combined[0] = visitor;
        combined[1] = museum;
        combined[2] = date;
        return combined;
    }

    ArrayList<String> myMuseumsList(String userName) {
        ArrayList<String> museumList = new ArrayList<>();
        ResultSet set = query("SELECT Name FROM Museum WHERE CEmail = '" + userName + "' ORDER BY Name;");
        try {
            set.first();
            museumList.add(set.getString(1));
            while (set.next()) {
                museumList.add(set.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Closed Result Set " + e.getMessage());
        }
        return museumList;
    }

    int numExhibits(String mName) {
        ResultSet set = query("SELECT COUNT(*) FROM Exhibit WHERE MName = '" + mName + "';");
        try {
            set.first();
            return set.getInt(1);
        } catch (SQLException e) {
            System.out.println("num exhibits error " + e.getMessage());
            return 0;
        }
    }

    float averageScore(String mName) {

        float average = 0;
        ResultSet avScore = query("SELECT AVG(Score) FROM Review WHERE mName = '" + mName + "';");
        try {
            avScore.first();
            average = avScore.getFloat(1);
        } catch (SQLException e) {
            System.out.println("average score error " + e.getMessage());
        }
        return average;
    }



    double currentPrice(String mName) {
        double price = 0.00;
        try {
            ResultSet set2 = query("SELECT CurrentPrice " +
                    "FROM Museum WHERE Name='" + mName + "';");
            set2.first();
            price = set2.getBigDecimal(1).doubleValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    boolean createUser (String email, String password, long ccNum, int ccv,
                        int expYear, int expMonth) {

        email = email.trim();
        ResultSet set = query(
                "SELECT EXISTS " +
                        "(SELECT * " +
                        "FROM Visitor WHERE Email='" + email + "');");

        try {
            set.first();
            if (set.getInt(1) > 0) return false;
        } catch (SQLException e) {
            System.out.println("Vacancy error " + e.getMessage());
        }

        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Visitor " +
                    "(email, password, ccv, ccnum, expyear, expmonth) VALUES(?,?,?,?,?,?);");
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setInt(3, ccv);
            statement.setLong(4, ccNum);
            statement.setInt(5, expYear);
            statement.setInt(6, expMonth);
            execute(statement);

        } catch (SQLException e){
            System.out.println("Error happened " + e.getMessage());
        }
        return true;
    }

    boolean createUserOld (String email, String password, long ccNum, int ccv,
                        int expYear, int expMonth) {

        email = email.trim();
        ResultSet set = query(
                "SELECT EXISTS " +
                        "(SELECT * " +
                        "FROM User WHERE Email='" + email + "');");

        try {
            set.first();
            if (set.getInt(1) > 0) return false;
        } catch (SQLException e) {
            System.out.println("Vacancy error " + e.getMessage());
        }

        return execute("INSERT INTO Visitor " +
                "VALUES('" + email + "', '" + password + "', " + ccv + ", "
                + ccNum + ", " + expYear + ", " + expMonth + ");");
    }

    boolean createReview (String museum, int score, String text) {
        execute("DELETE FROM Review WHERE VEmail='"
                        + CurrentUserInfo.getInstance().getUser() + "' AND " +
                        "MName='" + museum + "';");
        execute("INSERT INTO Review " +
                "VALUES ('" + CurrentUserInfo.getInstance().getUser() +
                "', '" + museum + "', '" + ("".equals(text) ? null : text)
                + "', " + score + ");");
        return true;
    }

    Integer getReviewScore (String museum, String email) {
        ResultSet set = query(
                "SELECT Score FROM Review " +
                        "WHERE VEmail='"
                        + CurrentUserInfo.getInstance().getUser() + "' AND " +
                        "MName='" + museum + "';");
        try {
            set.first();
            return set.getInt(1);
        } catch (SQLException e) {
            System.out.println("Review error " + e.getMessage());
            return 5;
        }
    }

    String getReviewText (String museum, String email) {
        ResultSet set = query(
                "SELECT Text FROM Review " +
                        "WHERE VEmail='"
                        + CurrentUserInfo.getInstance().getUser() + "' AND " +
                        "MName='" + museum + "';");
        try {
            set.first();
            return set.getString(1);
        } catch (SQLException e) {
            System.out.println("Review error " + e.getMessage());
            return "";
        }
    }
    boolean checkAdminStatus(String email) {
        ResultSet set = query("SELECT EXISTS (SELECT * " +
                "FROM Administrator WHERE Email='" + email + "');");
        try {
            set.first();
            if (set.getInt(1) > 0) return true;
        } catch (SQLException e) {
            System.out.println("Admin status error " + e.getMessage());
        }
        return false;
    }
    boolean checkGeneralCuratorStatus(String email) {
        ResultSet set = query("SELECT EXISTS (SELECT * " +
                "FROM Museum WHERE CEmail='" + email + "');");
        try {
            set.first();
            if (set.getInt(1) > 0) return true;
        } catch (SQLException e) {
            System.out.println("General Curator Status error " + e.getMessage());
        }
        return false;
    }
    boolean checkCuratorStatus(String museum, String email) {
        ResultSet set = query("SELECT EXISTS (SELECT * " +
                "FROM Museum WHERE CEmail='" + email + "' AND Name='" +
                museum + "');");
        try {
            set.first();
            if (set.getInt(1) > 0) return true;
        } catch (SQLException e) {
            System.out.println("Vacancy error " + e.getMessage());
        }
        return false;
    }

    ArrayList<String> getAllMuseums() {
        ResultSet set = query(
                "SELECT Name FROM Museum ORDER BY Name;");
        ArrayList<String> museumList = new ArrayList<>();
        try {
            set.first();
            museumList.add(set.getString(1));
            while (set.next()) {
                museumList.add(set.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return museumList;
    }

    boolean removeExhibit(String museum, String exhibit) {
        return execute("DELETE FROM Exhibit WHERE MName='"
                + museum + "' AND " +
                "Name='" + exhibit + "';");
    }

    void deleteAccount(String userName) {
        execute("DELETE FROM Visitor WHERE Email = '"
                + userName + "';");
    }

    boolean addExhibit(String museum, String name, int year, String url) {
        name = name.trim();
        ResultSet set = query(
                "SELECT EXISTS " +
                        "(SELECT * " +
                        "FROM Exhibit WHERE Name='" + name + "' AND Mname='" +
                        museum + "');");

        try {
            set.first();
            if (set.getInt(1) > 0) return false;
        } catch (SQLException e) {
            System.out.println("Exhibit creation error " + e.getMessage());
        }

        return execute("INSERT INTO Exhibit " +
                "VALUES('" + name + "', '" + museum + "', " + year + ", '"
                + url + "');");
    }

    void deleteMuseum(String museumName) {
        System.out.println("museum is " + museumName);
        execute("DELETE FROM Museum " +
                "WHERE Name = '" + museumName + "';");
    }

    void deleteReview(String email, String museumName) {
        execute("DELETE FROM Review " +
                "WHERE MName = '" + museumName + "' AND VEmail = '" + email + "';");
    }

    boolean createCuratorRequest(String userName, String museumName) {
        if (curatorRequestValid(museumName)) {
            execute("INSERT INTO Curator_Request " +
                    "VALUES('" + userName + "', '" + museumName + "', CURRENT_DATE, NULL);");
            return true;
        }
        return false;
    }

    private boolean curatorRequestValid(String museumName) {
        ResultSet set = query(
                "SELECT CEmail FROM Museum WHERE Name = '" + museumName + "';");
        try {
            set.first();
            if (!(set.getString(1) == null)) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Curator Request Error " + e.getMessage());
        }
        return true;
    }

    void acceptRequest(String visitor, String museum) {
        execute("UPDATE Curator_request SET ApprovedFlag=TRUE " +
                " WHERE VEmail='" + visitor + "' AND MName='" + museum + "' AND ApprovedFlag IS NULL;");
        execute("UPDATE Museum SET CEmail='" + visitor + "' WHERE Name='" + museum + "';");
//        execute("UPDATE Curator_request SET ApprovedFlag=FALSE " +
//                " WHERE VEmail<>'" + visitor + "' AND MName='" + museum + "' AND ApprovedFlag IS NULL;");
    }

    void rejectRequest(String visitor, String museum) {
        execute("UPDATE Curator_request SET ApprovedFlag=TRUE " +
                " WHERE VEmail='" + visitor + "' AND MName='" + museum + "' AND ApprovedFlag IS NULL;");
    }

    boolean hasPurchasedTicket(String visitor, String museum) {
        ResultSet set = query(
                "SELECT EXISTS (SELECT * FROM Ticket WHERE VEmail='" + visitor + "' AND MName='" + museum +"');");
        try {
            set.first();
            if (set.getInt(1) > 0) return true;
        } catch (SQLException e) {
            System.out.println("Ticket error " + e.getMessage());
        }
        return false;
    }

    boolean addMuseum(String museumName, float amount) {
        museumName = museumName.trim();
        ResultSet set = query(
                "SELECT EXISTS (SELECT * FROM Museum WHERE Name='" + museumName + "');");
        try {
            set.first();
            System.out.println(set);
            int attempt = set.getInt(1);
            System.out.println(attempt);
            if (attempt == 1) {
                return false;
            } else {
                execute("INSERT INTO Museum VALUES('" + museumName +
                        "', NULL, " + amount + ");");
                System.out.println("put in musem");
            }
        } catch (SQLException e) {
            System.out.println("Museum error" + e.getMessage());
            return false;
        }
        return true;
    }

    boolean purchaseTicket(String mName) {
        ResultSet set = query(
                "SELECT EXISTS " +
                        "(SELECT VEmail " +
                        "FROM Ticket WHERE MName = '" + mName + "' AND VEmail = '"
                        + CurrentUserInfo.getInstance().getUser() + "');");
        try {
            set.first();
            if(set.getInt(1) == 1) {
                return false;
            } else {
                execute("INSERT INTO Ticket " +
                        "VALUES('" + CurrentUserInfo.getInstance().getUser() + "', '" + mName
                        + "', CURRENT_TIMESTAMP, '" + currentPrice(mName) + "');");
            }
        } catch (SQLException e) {
            System.out.println("Vacancy error " + e.getMessage());
        }
        return true;
    }

    boolean checkInjection(String element) {
        for (int i = 0; i < element.length(); i++) {
            if (element.charAt(i) ==  "'".charAt(0) || element.charAt(i) == ';') {
                return true;
            }
        }
        return false;
    }
}
