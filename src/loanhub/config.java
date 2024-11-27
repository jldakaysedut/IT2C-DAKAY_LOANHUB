package loanhub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class config {

    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:loan.db"); // Establish connection          
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public void addRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, values);
            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    

    
    

public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames, Object... params) {
    if (columnHeaders.length != columnNames.length) {
        System.out.println("Error: Mismatch between column headers and column names.");
        return;
    }

    try (Connection conn = this.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();

        // Collect data and calculate max length for each column
        List<Object[]> records = new ArrayList<>();
        int[] columnWidths = new int[columnHeaders.length];
        for (int i = 0; i < columnHeaders.length; i++) {
            columnWidths[i] = columnHeaders[i].length();
        }

        while (rs.next()) {
            Object[] row = new Object[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                row[i] = rs.getString(columnNames[i]);
                if (row[i] != null && row[i].toString().length() > columnWidths[i]) {
                    columnWidths[i] = row[i].toString().length();
                }
            }
            records.add(row);
        }

        // Generate the horizontal line dynamically
        StringBuilder horizontalLine = new StringBuilder();
        for (int width : columnWidths) {
            horizontalLine.append("+");
            for (int i = 0; i < width + 2; i++) { // Adding padding
                horizontalLine.append("-");
            }
        }
        horizontalLine.append("+");
        String line = horizontalLine.toString();

        // Print header
        System.out.println(line);
        for (int i = 0; i < columnHeaders.length; i++) {
            System.out.printf("| %-" + columnWidths[i] + "s ", columnHeaders[i]);
        }
        System.out.println("|");
        System.out.println(line);

        // Print rows
        for (Object[] row : records) {
            for (int i = 0; i < columnNames.length; i++) {
                System.out.printf("| %-" + columnWidths[i] + "s ", row[i] != null ? row[i] : "");
            }
            System.out.println("|");
        }
        System.out.println(line);

    } catch (SQLException e) {
        System.out.println("Error retrieving records: " + e.getMessage());
    }
}


    
    
    

    public void updateRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, values);
            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, values);
            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    private void setPreparedStatementValues(PreparedStatement pstmt, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]);
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]);
            } else if (values[i] instanceof Float) {
                pstmt.setFloat(i + 1, (Float) values[i]);
            } else if (values[i] instanceof Long) {
                pstmt.setLong(i + 1, (Long) values[i]);
            } else if (values[i] instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) values[i]);
            } else if (values[i] instanceof java.util.Date) {
                pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
            } else if (values[i] instanceof java.sql.Date) {
                pstmt.setDate(i + 1, (java.sql.Date) values[i]);
            } else if (values[i] instanceof java.sql.Timestamp) {
                pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
            } else {
                pstmt.setString(i + 1, values[i].toString());
            }
        }
    }

    //-----------------------------------------------
    // GET SINGLE VALUE METHOD
    //-----------------------------------------------
    public String getSingleValue(String sql, Object... params) {
    String result = null;
    try (Connection conn = connectDB(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = rs.getString(1);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving single value: " + e.getMessage());
    }
    return result;
}


    //-----------------------------------------------
    // GET SINGLE RECORD METHOD
    //-----------------------------------------------
    public Object[] getSingleRecord(String sql, Object... params) {
        Object[] result = null;
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            if (rs.next()) {
                result = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    result[i] = rs.getObject(i + 1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving single record: " + e.getMessage());
        }
        return result;
    }

    
    // Get the count of records
    public int getCount(String sql, Object... params) {
        int count = 0;
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving count: " + e.getMessage());
        }
        return count;
    }
    
    
    
    public double getTotal(String sql, Object... params) {
    double total = 0;
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            total = rs.getDouble(1);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving total: " + e.getMessage());
    }
    return total;
}

    
    
    
    
    
    
    
}
