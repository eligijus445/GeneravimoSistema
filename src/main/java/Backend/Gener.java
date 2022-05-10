package Backend;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jsoup.Jsoup;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Date;


public class Gener {

    public User loggedIn = new User();
    ArrayList<User> users = new ArrayList<>();
    private Connection conn = null;

    public static boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isNumeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    public void conncetToDB() {
        try {
            String DB_URL = "jdbc:mysql://127.0.0.1/gener_db_new";
            String USER = "root";
            String PASS = "root";
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void disconnectDB() {
        try {
            this.conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*-------------------------------------- User -------------------------------------------*/
    public ArrayList upploadAllUsersFromDataBase() throws SQLException {
        conncetToDB();
        Statement statement = this.conn.createStatement();
        ResultSet rzUser = statement.executeQuery("SELECT * FROM gener_db_new.user");
        ArrayList users = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            User user = new User();
            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);
            users.add(user);
        }
        disconnectDB();
        return users;
    }

    public void upploadUserByUsername(String username) throws SQLException {
        int userId = getIDbyUsername(username);

        PreparedStatement Statement = this.conn.prepareStatement("SELECT * FROM user where User_Login_UserID = ?");
        Statement.setInt(1, userId);
        ResultSet rzUser = Statement.executeQuery();
        while (rzUser.next()) {
            String userFirst_name = rzUser.getString(2);
            String userLast_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            loggedIn.addUser(userId, userFirst_name, userLast_name, email, phone, active, role);
            users.add(loggedIn);
        }

    }

    public ArrayList getUserByNameAndSurname(String name, String surname) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where name = ? and surname =?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList users = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            User user = new User();
            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);
            users.add(user);
        }
        disconnectDB();

        return users;
    }

    public ArrayList getUserByName(String name) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where name = ?");
        preparedStatement.setString(1, name);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList users = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            User user = new User();
            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);
            users.add(user);

        }
        disconnectDB();

        return users;
    }

    public ArrayList getUserBySurname(String surname) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where surname = ?");
        preparedStatement.setString(1, surname);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList users = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            User user = new User();
            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);
            users.add(user);

        }
        disconnectDB();

        return users;
    }

    public ArrayList getUserBySearchText (String SearchText) throws SQLException {
        conncetToDB();
        SearchText = "SELECT * FROM gener_db_new.user where concat(User_Login_UserID,name,surname,email,phone) like '%" + SearchText + "%'";
        PreparedStatement preparedStatement = this.conn.prepareStatement(SearchText);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList users = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);
            User user = new User();
            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);
            users.add(user);
        }
        disconnectDB();
        return users;
    }

    public void updateUserPassword (User user,String password) throws SQLException {
        conncetToDB();
        String sql = "update gener_db_new.user_login set password =? where User_ID = ?";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
        preparedStatement.setString(1, encryption(password));
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
        disconnectDB();

    }


    public User getUserByID(int Userid) throws SQLException {
        conncetToDB();
        User user = new User();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where User_Login_UserID = ?");
        preparedStatement.setInt(1, Userid);
        ResultSet rzUser = preparedStatement.executeQuery();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String userFirst_name = rzUser.getString(2);
            String userLirst_name = rzUser.getString(3);
            String email = rzUser.getString(4);
            String phone = rzUser.getString(5);
            boolean active = rzUser.getBoolean(6);
            String role = rzUser.getString(7);

            user.addUser(id, userFirst_name, userLirst_name, email, phone, active, role);

        }
        disconnectDB();

        return user;
    }




    public void uploadUserdataToDB(int userId, String userFirst_name, String userLast_name, String email, String phone, boolean active, String role) throws SQLException {

conncetToDB();
        String sql = "update user set name = ?, surname= ?, email = ? , phone = ? , active=?, role=? where User_Login_UserID = ?";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
        preparedStatement.setString(1, userFirst_name);
        preparedStatement.setString(2, userLast_name);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone);
        preparedStatement.setBoolean(5, active);
        preparedStatement.setString(6, role);
        preparedStatement.setInt(7, userId);
        preparedStatement.executeUpdate();
        disconnectDB();

    }

    public boolean login(String username, String password) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user_login where username = ?");
        preparedStatement.setString(1, username);
        ResultSet rsl = preparedStatement.executeQuery();
        if (rsl.next() == false) {
            disconnectDB();
            return false;
        } else {
            int id = rsl.getInt(1);
            String enpassword = encryption(password);
            String usernameDB = rsl.getString(2);
            String passwordDB = rsl.getString(3);


            if (username.equals(usernameDB) && enpassword.equals(passwordDB) && isSameUsername(username) && getUserActiveStatusById(id)) {
                upploadUserByUsername(username);

            } else {
                return false;
            }
        }
        disconnectDB();
        return true;


    }
    public boolean getUserActiveStatusById (int Userid) throws SQLException {
        conncetToDB();
        boolean active = false;
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where User_Login_UserID = ?");
        preparedStatement.setInt(1, Userid);
        ResultSet rsl = preparedStatement.executeQuery();
        if (rsl.next() == true)
        active = rsl.getBoolean(6);

        return active;
    }
    public String getUserUsernameByUserID(int id) throws SQLException {
        conncetToDB();
        String usernameDB= null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user_login where User_ID = ?");
        preparedStatement.setInt(1, id);

        ResultSet rsl = preparedStatement.executeQuery();
        while (rsl.next())
        {
            usernameDB = rsl.getString(2);
        }

        disconnectDB();
        return usernameDB;

    }
    public boolean isSamePassword (int userID, String password) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user_login  where User_ID = ?");
        preparedStatement.setInt(1, userID);
        ResultSet rsl = preparedStatement.executeQuery();
        if (rsl.next() == false) {
            disconnectDB();
            return false;
        } else {
            String enpassword = encryption(password);
            String passwordDB = rsl.getString(3);

            if (enpassword.equals(passwordDB)) {
                disconnectDB();
                return true;

            } else {
                disconnectDB();
                return false;
            }
        }

    }

    public void deleteUser(int userId) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("delete from user where  User_Login_UserID = ?");
        try {
            preparedStatement.setInt(1, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public boolean isSameUsername(String username) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user_login where username = ?");
        preparedStatement.setString(1, username);
        ResultSet rsl = preparedStatement.executeQuery();
        return rsl.next() != false;
    }

    public boolean isSameEmail(String email) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where email = ?");
        preparedStatement.setString(1, email);
        ResultSet rsl = preparedStatement.executeQuery();

        return rsl.next() != false;
    }

    public boolean isSamamePhone(String phone) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from user where phone = ?");
        preparedStatement.setString(1, phone);
        ResultSet rsl = preparedStatement.executeQuery();

        return rsl.next() != false;

    }

    public boolean isValidUsername(String username) {
        if (username.equals(null)) return false;
        if (username.length() <= 3) return false;
        return username.length() <= 32;
    }

    public boolean isvalidPassword(String password) {
        if (password.length() <= 7) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (isNumeric(ch)) numCount++;
            else if (isLetter(ch)) charCount++;
            else return false;
        }
        return (charCount >= 2 && numCount >= 2);
    }

    public boolean isValidName(String name) {
        if (name.equals(null)) return false;
        if (name.length() <= 3) return false;
        return name.length() <= 32;

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidPhoneNumber(String phone) {
        if (phone.length() != 12) return false;
        else return phone.startsWith("+370");
    }

    public String encryption(String password) {

        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }

    public String generatePassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }


    public void createLogin(String username, String password) throws SQLException {
        int userID = 0;
        PreparedStatement preparedStatement = this.conn.prepareStatement("INSERT INTO user_login values (NULL,?,?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, encryption(password));
        preparedStatement.executeUpdate();
        userID = getIDbyUsername(username);
        System.out.println("Sukurtas naujas vartotojas su " + userID + " vartotojo id.");
        PreparedStatement ps = this.conn.prepareStatement("INSERT INTO user values (?,NULL,NULL,NULL,NULL,NULL,NULL)");
        ps.setInt(1, userID);
        ps.executeUpdate();
    }

    public void registration(String username, String password, String userFirst_name, String userLast_name, String email, String phone, String gruop) throws SQLException {

        if (isSameUsername(username)) {
            System.out.println("Vartotojas su tokiu vartotojo vardu jau egzistuoja");
            return;
        }
        if (!isValidEmailAddress(email)) {
            System.out.println("Netinkamas elektroninio pašto adresas");
            return;
        }
        if (!isValidPhoneNumber(phone)) {
            System.out.println("Bogai įvestas telefono numeris.");
            return;
        }
        conncetToDB();
        createLogin(username, password);

        uploadUserdataToDB(getIDbyUsername(username), userFirst_name, userLast_name, email, phone, true, gruop);
        System.out.println("Naujas vartotojas sukurtas");
        disconnectDB();

    }

    public int getIDbyUsername(String username) throws SQLException {

        int id = 0;
        PreparedStatement Statement = this.conn.prepareStatement("SELECT * FROM user_login where username = ?");
        Statement.setString(1, username);
        ResultSet rsl = Statement.executeQuery();
        while (rsl.next()) {
            id = rsl.getInt(1);
        }

        return id;
    }


    /*-------------------------------------- Material -------------------------------------------*/
    private void addMaterialToDB(String name, String qantity, double price) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("INSERT INTO material values (NULL,?,?,?)");
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant medžiagos pavadinimą į duomenų bazę");
        }
        try {
            preparedStatement.setString(2, qantity);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant medžiagos kiekį į duomenų bazę.");
        }
        try {
            preparedStatement.setDouble(3, price);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant medžiagos kainą į duomenų bazę.");
        }
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public void addMaterial(String name, String qantity, double price) {
        if (name.length() >= 100) {
            System.out.println("Medžiagos pavadinimas perilgas.");
            return;
        } else if (price < 0) {
            System.out.println("Medžiagos kaina negali būti mažiau 0");
            return;
        } else {
            try {
                addMaterialToDB(name, qantity, price);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Kalida įkeliant medžiagos duomenis į duomenų bazę.");
            }
        }
    }

    public int getLastMaterialID() throws SQLException {
        int id = 0;
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT materials_ID FROM gener_db_new.material where materials_ID = (SELECT MAX(materials_ID) FROM gener_db_new.material)");
        ResultSet rzUser = preparedStatement.executeQuery();

        while (rzUser.next()) {
            id = rzUser.getInt(1);
        }
        disconnectDB();


        return id;
    }

    private void deleteMaterialFromDB(int materialID) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("delete from material where materials_ID = ?");
        try {
            preparedStatement.setInt(1, materialID);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyo ištinti medžiagos iš duomenų bazės.");
        }
        System.out.println("Medžiaga su id" + materialID + " buvo ištrinta");
        preparedStatement.executeUpdate();
        disconnectDB();

    }

    public void deleteMaterial(int materialID) throws SQLException {

        if (materialID <= 0) {
            System.out.println("Tokia medžiaga nerasta");
            return;
        } else deleteMaterialFromDB(materialID);
    }

    public ArrayList findMaterialByName(String materialName) throws SQLException {
        conncetToDB();
        materialName = "%" + materialName + "%";
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.material where name like ?;");
        preparedStatement.setString(1, materialName);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList materials = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String name = rzUser.getString(2);
            String quantity = rzUser.getString(3);
            double price = rzUser.getDouble(4);

            Material material = new Material();
            material.addMaterial(id, name, quantity, price);

            materials.add(material);
        }
        disconnectDB();

        return materials;
    }

    public Material findMaterialByID(int materialID) throws SQLException {
        conncetToDB();
        Material material = new Material();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.material where materials_ID = ?;");
        preparedStatement.setInt(1, materialID);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList materials = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String name = rzUser.getString(2);
            String unit = rzUser.getString(3);
            double price = rzUser.getDouble(4);
            material.addMaterial(id, name, unit, price);
        }
        disconnectDB();
        return material;
    }


    public ArrayList uploadAllMaterials() throws SQLException {
        ArrayList materials = new ArrayList();

        conncetToDB();

        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.material;");
        ResultSet rzUser = preparedStatement.executeQuery();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String name = rzUser.getString(2);
            String unit = rzUser.getString(3);
            double price = rzUser.getDouble(4);

            Material material = new Material();

            material.addMaterial(id, name, unit, price);

            materials.add(material);
        }
        disconnectDB();

        return materials;
    }

    public void updateMaterial(int materialID, String materialname, String units, double materialprice) throws SQLException {
        conncetToDB();


        PreparedStatement preparedStatement = this.conn.prepareStatement("update  material set name = ?, unit=?,price=? where materials_ID =? ");

        try {
            preparedStatement.setString(1, materialname);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida atnaujinat medžiagų pavadinimą");
        }
        try {
            preparedStatement.setString(2, units);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida atnaujinat medžiagų kiekį.");
        }
        try {
            preparedStatement.setDouble(3, materialprice);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida atnaujinat medžiagų kainą.");
        }
        preparedStatement.setInt(4, materialID);
        preparedStatement.executeUpdate();
        disconnectDB();


    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    /*-------------------------------------- Client -------------------------------------------*/

    public ArrayList uploadClientFromDbByFirmID(int firmID) throws SQLException {
        ArrayList clients = new ArrayList();

        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.client where Firm_firm_ID = ?;");
        preparedStatement.setInt(1, firmID);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            String name = rzClient.getString(3);
            String surname = rzClient.getString(4);
            String email = rzClient.getString(5);
            String phone = rzClient.getString(6);


            Client client = new Client();
            client.addClient(id, firmID, name, surname, email, phone);

            clients.add(client);
        }
        disconnectDB();
        return clients;
    }

    public Client getClientByID(int clientId) throws SQLException {
        conncetToDB();
        Client client = new Client();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.client where client_ID = ?;");
        preparedStatement.setInt(1, clientId);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            int firmID = rzClient.getInt(2);
            String name = rzClient.getString(3);
            String surname = rzClient.getString(4);
            String email = rzClient.getString(5);
            String phone = rzClient.getString(6);
            client.addClient(id, firmID, name, surname, email, phone);
        }
        disconnectDB();
        return client;
    }


    public void uploadClientToDb(int firmId, String name, String surname, String email, String phone) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("insert into client values (Null,?,?,?,?,?)");
        try {
            preparedStatement.setInt(1, firmId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko įkelti į dumenų bazę įmonės id");
        }
        try {
            preparedStatement.setString(2, name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko įkelti į dumenų bazę kliento vardo");
        }
        try {
            preparedStatement.setString(3, surname);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko įkelti į dumenų bazę kliento pavardės");
        }
        try {
            preparedStatement.setString(4, email);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko įkelti į dumenų bazę kliento el. pašto");
        }
        try {
            preparedStatement.setString(5, phone);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko įkelti į dumenų bazę kliento telefono numerio.");
        }
        preparedStatement.executeUpdate();
        System.out.println("Naujas klientas įkeltas į duomenų bazę");
        disconnectDB();

    }



    public void deleteClientFromDb(int ClientId) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("delete from client where client_ID = ?");
        try {
            preparedStatement.setInt(1, ClientId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyo ištinti kliento iš duomenų bazės.");
        }
        System.out.println("Klientas su id" + ClientId + " buvo ištrintas");
        preparedStatement.executeUpdate();
        disconnectDB();

    }



   public void updtateClientInDb(int clientID, String name, String surname, String email, String phone) throws SQLException {
       conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("update client set name=? , surname=?, emal=? ,phone=? where client_ID=?");
        try {
            preparedStatement.setString(1, name);
            System.out.println("Klaida įrašant kliento vardą į duomenų bazę.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setString(2, surname);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant kliento pavardę į duomenų bazę.");
        }
        try {
            preparedStatement.setString(3, email);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant kliento el. paštą į duomenų bazę.");
        }
        try {
            preparedStatement.setString(4, phone);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida įrašant kliento telefono numerį į duomenų bazę.");
        }
        try {
            preparedStatement.setInt(5, clientID);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Klaida ieškant kliento pagal id duomenų bazėje.");
        }
        preparedStatement.executeUpdate();
      disconnectDB();
    }
    /*-------------------------------------- Firm -------------------------------------------*/

    public void createFirm(int vatNumber, String name, String adress, String formName, int state) throws SQLException {


    }


    public String findVatByFirmID(int firmNumber) throws IOException, InterruptedException {

        String link = "https://rekvizitai.vz.lt/pvm-kodai/1/?n=&cd=";
        final org.jsoup.nodes.Document document = Jsoup.connect(link + firmNumber).get();
        String vat = String.valueOf(document.select("#leftContainer > div:nth-child(1) > div > div:nth-child(3) > div.info > strong"));
        vat = vat.replace("<strong>", "");
        vat = vat.replace("</strong>", "");
        if (vat.length() > 0) return vat;
        System.out.println("Pvm mokėtojo kodas nerastas");
        return null;
    }

    public void updateVatNumber(int firmNumber) throws IOException, InterruptedException, SQLException {

        if (findVatByFirmID(firmNumber).length() < 1) {
            System.out.println("Nepavyko rasti PVM mokėtojo kodo");
            return;
        } else uploadVatNumberToDb(firmNumber, findVatByFirmID(firmNumber));


    }

    public String getVatNumberByFirmID(int firmNumber) throws SQLException {
        conncetToDB();
        String a;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT vat_number FROM gener_db_new.firm where firm_number = ?");
        preparedStatement.setInt(1, firmNumber);
        ResultSet rsl = preparedStatement.executeQuery();
        if (rsl.next() == false) {
            a = rsl.getString(1);
        } else {
            a = rsl.getString(1);
        }
        if (a != null && a.length() != 0 && a.length() == 14) {
            System.out.println(a.length());
            disconnectDB();
            return a;
        }
        disconnectDB();
        return a;
    }

    public Boolean isValidVatNumber(String vatNumber) throws IOException {
        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        URL url = new URL("http://www.apilayer.net/api/validate?access_key=19ad09452f950e054b0bb5a479ae10b0&vat_number=" + vatNumber + "&format=1");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        System.out.println(connection.getResponseCode());
        int status = connection.getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            return false;
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        if (responseContent.toString().substring(11, 15).equals("true")) return true;
        else return false;


    }

    public boolean isVatNumberInDb(int firmNumber) throws SQLException {
        conncetToDB();
        String a;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT vat_number FROM gener_db_new.firm where firm_number = ?");
        preparedStatement.setInt(1, firmNumber);
        ResultSet rsl = preparedStatement.executeQuery();
        if (rsl.next() == false) {
            a = rsl.getString(1);
        } else {
            a = rsl.getString(1);
        }
        if (a != null && a.length() != 0 && a.length() == 14) {
            System.out.println(a.length());
            disconnectDB();
            return true;
        }
        disconnectDB();
        return false;
    }

    private void uploadVatNumberToDb(int firmNumber, String vatNumber) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("update firm set vat_number = ? where firm_number = ?");
        try {
            preparedStatement.setString(1, vatNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko į duomenų bazę įkelti PVM mokėtojo kodo.");
        }
        try {
            preparedStatement.setInt(2, firmNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko nurodyti įmonės kodo.");
        }
        preparedStatement.executeUpdate();
        disconnectDB();
    }
    public void uploadFirmEmailToDb(int firmID, String email) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("update firm set email = ? where firm_ID = ?");
        try {
            preparedStatement.setInt(2, firmID);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko į duomenų bazę įkelti el. pašto adreso.");
        }
        try {
            preparedStatement.setString(1, email);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko nurodyti įmonės kodo.");
        }
        preparedStatement.executeUpdate();
        disconnectDB();
    }


    public ArrayList findFirmByNamefromDb(String name) throws SQLException {
        conncetToDB();
        name = "%" + name + "%";
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.firm where name like ?;");
        preparedStatement.setString(1, name);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList firms = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            int firmNumber = rzUser.getInt(2);
            String vatNumber = rzUser.getString(3);
            name = rzUser.getString(4);
            String adress = rzUser.getString(5);
            String formName = rzUser.getString(6);
            int state = rzUser.getInt(7);
            Firm firm = new Firm();
            String email = rzUser.getString(8);
            firm.addFirm(id, firmNumber, vatNumber, name, adress, formName, state, email);
            firms.add(firm);
        }
        disconnectDB();

        return firms;
    }

    public ArrayList findFirmByfirmNumberfromDb(int firmNumber) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.firm where firm_number = ?;");
        preparedStatement.setInt(1, firmNumber);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList firms = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            firmNumber = rzUser.getInt(2);
            String vatNumber = rzUser.getString(3);
            String name = rzUser.getString(4);
            String adress = rzUser.getString(5);
            String formName = rzUser.getString(6);
            int state = rzUser.getInt(7);
            Firm firm = new Firm();
            String email = rzUser.getString(8);
            firm.addFirm(id, firmNumber, vatNumber, name, adress, formName, state, email);
            firms.add(firm);
        }
        disconnectDB();

        return firms;
    }

    public Firm getFirmByFirmId(int firmId) throws SQLException {
        conncetToDB();
        Firm firm = new Firm();

        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.firm where firm_ID = ?;");
        preparedStatement.setInt(1, firmId);
        ResultSet rzUser = preparedStatement.executeQuery();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);

            int firmNumber = rzUser.getInt(2);
            String vatNumber = rzUser.getString(3);
            String name = rzUser.getString(4);
            String adress = rzUser.getString(5);
            String formName = rzUser.getString(6);
            int state = rzUser.getInt(7);
            String email = rzUser.getString(8);
            firm.addFirm(id, firmNumber, vatNumber, name, adress, formName, state, email);
        }
        disconnectDB();

        return firm;


    }


    public Boolean isFirmNameValid(String firmName) {
        if (firmName.equals("")) {
            return false;
        } else if (firmName.isEmpty()) {
            return false;
        } else return !firmName.trim().isEmpty();
    }

    public Boolean isFirmNumberValid(String firmNumber) {
        if (firmNumber.equals("")) {
            return false;
        } else if (firmNumber.equals(null)) {
            return false;
        } else if (firmNumber.trim().isEmpty()) {

            return false;
        } else if (firmNumber.isEmpty()) {

            return false;
        } else if (firmNumber.length() != 9) {
            return false;
        } else return firmNumber.matches("[0-9]+");
    }

    /*-------------------------------------- Used MAterials-------------------------------------------*/

    public void uploadUsedMaterialsToDb(double quantity, int workActId, int materialID) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("insert into used_material values (Null,?,?,?)");
        preparedStatement.setDouble(1, quantity);
        preparedStatement.setInt(2, workActId);
        preparedStatement.setInt(3, materialID);
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public ArrayList uploadUsedMaterialsFromDBByWorkActNr(String workActNr) throws SQLException {


        ArrayList usedMaterials = new ArrayList();
        WorkAct workAct = uploadWorkActFromDbByWorkActNumber(workActNr);
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.used_material where work_act_Work_act_ID = ?;");
        preparedStatement.setInt(1, workAct.getId());
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            double quantity = rzClient.getDouble(2);
            int workActID = workAct.getId();
            int materialID = rzClient.getInt(4);
            UsedMaterial usedMaterial = new UsedMaterial();
            Material material = findMaterialByID(materialID);

            usedMaterial.addUsedMaterial(id, materialID, workActID, material.getName(), material.getUnit(), quantity, material.getPrice());
            usedMaterials.add(usedMaterial);
        }

        disconnectDB();
        return usedMaterials;


    }


    public void deleteAllUsedMaterialsFromDBByWorkActID(int workActID) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("delete from used_material where work_act_Work_act_ID =?");

        preparedStatement.setInt(1, workActID);

        preparedStatement.executeUpdate();
        disconnectDB();


    }

    public ArrayList getUsedMaterialsFromDBByWorkActID(int workActID) throws SQLException {
        ArrayList usedMaterials = new ArrayList();
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.used_material where work_act_Work_act_ID = ?;");
        preparedStatement.setInt(1, workActID);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            double quantity = rzClient.getDouble(2);
            int materialID = rzClient.getInt(4);
            UsedMaterial usedMaterial = new UsedMaterial();
            Material material = findMaterialByID(materialID);
            usedMaterial.addUsedMaterial(id, materialID, workActID, material.getName(), material.getUnit(), quantity, material.getPrice());
            usedMaterials.add(usedMaterial);
        }
        disconnectDB();
        return usedMaterials;

    }
    public boolean isValidTimeFormat(String time) {

        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }

    }

    public int isValidDateAndTime(String startTime, LocalDate startDate, String endTime, LocalDate endDate) throws ParseException {
        String startD = startDate + " " + startTime;
        String endD = endDate + " " + endTime;


        try {
            java.util.Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(startD);
            Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(endD);
            /*
            0 startas > pabaiga
            2 startas = pabaiga
            1 startas < pabaiga
            3 kalida
             */


            if (start.compareTo(end) > 0) {
                return 0;
            } else if (start.compareTo(end) < 0) {
                return 1;
            } else if (start.compareTo(end) == 0) {
                return 2;
            } else {
                return 3;
            }
        } catch (ParseException e) {
            //e.printStackTrace();
            return 3;
        }

    }


    public boolean isValidDescription(String description) {

        if (description.length() > 5000) {
            return false;
        } else if (description.length() < 1) {
            return false;
        } else return description.length() > 1 && description.length() < 5000;
    }

    public String getUsedMaterialPriceWhitoutTAX(ArrayList usedMaterialList) {
        int a = 0;
        double price = 0;
        try {
            while (usedMaterialList.get(a) != null) {

                UsedMaterial usedMaterial = (UsedMaterial) usedMaterialList.get(a);
                price += usedMaterial.getPrice() * usedMaterial.getQuantity();
                a++;
            }

        } catch (Exception e) {
        }
        return priceRound(price);
    }

    public String getUsedMaterialTAXprice(ArrayList usedMaterialList, int vat) {
        int a = 0;
        double price = 0;
        try {
            while (usedMaterialList.get(a) != null) {

                UsedMaterial usedMaterial = (UsedMaterial) usedMaterialList.get(a);
                price += usedMaterial.getPrice() * usedMaterial.getQuantity();
                a++;
            }

        } catch (Exception e) {
        }
        price = price * (double) vat/100;
        return priceRound(price);
    }

    public String getUsedMaterialPriceWhitTAX(ArrayList usedMaterialList, int vat) {
        int a = 0;
        double price = 0;
        try {
            while (usedMaterialList.get(a) != null) {

                UsedMaterial usedMaterial = (UsedMaterial) usedMaterialList.get(a);
                price += usedMaterial.getPrice() * usedMaterial.getQuantity();

                a++;
            }

        } catch (Exception e) {
        }
        price = price * ((double) vat/100+1);
        return priceRound(price);
    }

    public void createWorkActPDF(WorkAct workAct) throws DocumentException, IOException, SQLException {


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(getWorkActFilePath() + "/" + workAct.getNumber() + ".pdf"));
        ArrayList usedMaterialList = uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber());
        document.open();


        document.add(TitlePDF());
        document.add(AfterTitlePDF(workAct.getNumber(), workAct.getCreatedTime()));
        document.add(workActFirm(workAct));
        document.add(workStartEndTimeWritePDF(workAct.getStartTimeDate(), workAct.getEndTimeDate()));
        document.add(workActDescriptionPDF(workAct.getDescription()));
        document.add(workActSpace(20));
        document.add(afterDescriptionPDF());
        document.add(workActSpace(10));
        document.add(workActUsedMaterialTablePDF("ID", "Pavadinimas", "Vienetai", "Kiekis", "Kaina"));

        int a = 0;
        try {
            while (usedMaterialList.get(a) != null) {

                UsedMaterial usedMaterial = (UsedMaterial) usedMaterialList.get(a);
                document.add(workActUsedMaterialTablePDF(String.valueOf(usedMaterial.getId()), usedMaterial.getName(), usedMaterial.getUnit(), String.valueOf(usedMaterial.getQuantity()), priceRound(usedMaterial.getPrice() * usedMaterial.getQuantity())));
                a++;
            }

        } catch (Exception e) {
        }

        document.add(workActPrices(usedMaterialList));
        document.add(workActSpace(20));
        document.add(workActPeople(workAct));


        document.close();
    }

    private Element workActFirm(WorkAct workAct) throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);

        Paragraph FirmInfo = new Paragraph();
        FirmInfo.setFont(font);
        FirmInfo.setAlignment(Element.ALIGN_LEFT);
        Firm firm = getFirmByFirmId(workAct.getFirmId());
        FirmInfo.add("Užsakovo informacija: \n" + "Įmonės kodas: " + firm.getFirmNumber() + "\n" + "Pavadinimas: " + firm.getName() + "\n" + "Adresas: " + firm.getAdress() + "\n");

        return FirmInfo;


    }

    private Element workActSpace(int height) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(1); // 3 columns.
        table.setWidthPercentage(120);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);


        Paragraph text = new Paragraph();
        PdfPCell cell1 = new PdfPCell();
        text.add(" ");

        cell1.addElement(text);
        cell1.setBorder(0);
        cell1.setFixedHeight(height);


        table.addCell(cell1);


        return table;


    }

    private Element workActPeople(WorkAct workAct) throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(4); // 3 columns.
        table.setWidthPercentage(120);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph userPara = new Paragraph();
        Paragraph ClientPara = new Paragraph();

        Paragraph text = new Paragraph();


        userPara.setFont(font);
        ClientPara.setFont(font);

        text.setFont(font);
        User user = getUserByID(workAct.getUserID());
        Client client = getClientByID(workAct.getClientId());


        userPara.add(user.getRole() + " " + user.getName() + " " + user.getSurname());
        ClientPara.add(client.getName() + " " + client.getSurname());


        PdfPCell usercell = new PdfPCell();
        PdfPCell clientcell = new PdfPCell();
        PdfPCell cell1 = new PdfPCell();
        PdfPCell cell2 = new PdfPCell();


        usercell.addElement(userPara);
        clientcell.addElement(ClientPara);

        text.setAlignment(2);
        text.add("Darbus atliko: ");
        cell1.addElement(text);
        cell1.setBorder(0);
        cell2.setBorder(0);
        table.addCell(cell1);
        usercell.setUseVariableBorders(true);
        usercell.setBorderColorLeft(BaseColor.WHITE);
        usercell.setBorderColorRight(BaseColor.WHITE);
        usercell.setBorderColorTop(BaseColor.WHITE);

        table.addCell(usercell);
        text.clear();
        text.add("Darbus priėmė: ");
        cell2.addElement(text);

        table.addCell(cell2);
        clientcell.setUseVariableBorders(true);
        clientcell.setBorderColorLeft(BaseColor.WHITE);
        clientcell.setBorderColorRight(BaseColor.WHITE);
        clientcell.setBorderColorTop(BaseColor.WHITE);
        table.addCell(clientcell);
        text.clear();


        return table;

    }

    private Element workActPrices(ArrayList usedMaterials) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(2); // 3 columns.
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph pricePara = new Paragraph();
        Paragraph vatPara = new Paragraph();
        Paragraph sumPara = new Paragraph();
        Paragraph text = new Paragraph();


        pricePara.setFont(font);
        vatPara.setFont(font);
        sumPara.setFont(font);
        text.setFont(font);


        pricePara.add(getUsedMaterialPriceWhitoutTAX(usedMaterials));
        vatPara.add(getUsedMaterialTAXprice(usedMaterials,21));
        sumPara.add(getUsedMaterialPriceWhitTAX(usedMaterials,21));


        PdfPCell pricecell = new PdfPCell();
        PdfPCell vatcell = new PdfPCell();
        PdfPCell sumcell = new PdfPCell();
        PdfPCell cell1 = new PdfPCell();
        PdfPCell cell2 = new PdfPCell();
        PdfPCell cell3 = new PdfPCell();


        pricecell.addElement(pricePara);
        vatcell.addElement(vatPara);
        sumcell.addElement(sumPara);
        text.setAlignment(2);
        text.add("Suma be PVM: ");
        cell1.addElement(text);

        cell1.setBorder(0);
        cell2.setBorder(0);
        cell3.setBorder(0);


        table.addCell(cell1);
        table.addCell(pricecell);

        text.clear();

        text.add("PVM suma (21%): ");
        cell2.addElement(text);

        table.addCell(cell2);
        table.addCell(vatcell);
        text.clear();

        text.add("Iš viso: ");
        cell3.addElement(text);

        table.addCell(cell3);
        table.addCell(sumcell);


        return table;
    }

    private Element workActUsedMaterialTablePDF(String id, String name, String units, String quantity, String price) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(5); // 3 columns.
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        Paragraph idPara = new Paragraph();
        Paragraph namePara = new Paragraph();
        Paragraph unitsPara = new Paragraph();
        Paragraph qunatityPara = new Paragraph();
        Paragraph pricePara = new Paragraph();

        idPara.setFont(font);
        namePara.setFont(font);
        unitsPara.setFont(font);
        qunatityPara.setFont(font);
        pricePara.setFont(font);

        idPara.add(id);
        namePara.add(name);
        unitsPara.add(units);
        qunatityPara.add(quantity);
        pricePara.add(price);


        PdfPCell idcell = new PdfPCell();
        PdfPCell namecell = new PdfPCell();
        PdfPCell unitscell = new PdfPCell();
        PdfPCell quantitycell = new PdfPCell();
        PdfPCell pricecell = new PdfPCell();

        idcell.addElement(idPara);
        namecell.addElement(namePara);
        unitscell.addElement(unitsPara);
        quantitycell.addElement(qunatityPara);
        pricecell.addElement(pricePara);

        table.addCell(idcell);
        table.addCell(namecell);
        table.addCell(unitscell);
        table.addCell(quantitycell);
        table.addCell(pricecell);


        return table;


    }

    private Element afterDescriptionPDF() throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 12);
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setFont(font);
        paragraph.add("Panaudotų medžiagų sąrašas:\n");

        return paragraph;
    }


    private Element workActDescriptionPDF(String description) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(1); // 3 columns.
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        Paragraph descriptionPara = new Paragraph();
        Paragraph descriptionNamePara = new Paragraph();

        descriptionPara.setFont(font);

        descriptionNamePara.setFont(font);


        descriptionNamePara.add("Atliktų darbų aprašymas:\n\n");
        descriptionPara.add(description);
        PdfPCell cell1 = new PdfPCell();
        PdfPCell cell2 = new PdfPCell();
        font.setSize(12);
        cell1.addElement(descriptionNamePara);
        font.setSize(9);
        cell2.addElement(descriptionPara);
        cell1.setPadding(0);
        cell1.setBorder(0);
        cell1.setFixedHeight(20);
        cell2.setFixedHeight(120);

        table.addCell(cell1);
        table.addCell(cell2);

        return table;
    }


    private Element workStartEndTimeWritePDF(String startTime, String endTime) throws DocumentException, IOException {

        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font endStratTimeFont = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(1); // 3 columns.
        table.setWidthPercentage(20);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph startTimePara = new Paragraph();
        Paragraph endTimePara = new Paragraph();
        startTimePara.setFont(endStratTimeFont);
        endTimePara.setFont(endStratTimeFont);
        startTimePara.add("Darbų pradžios laikas:\n" + startTime);
        endTimePara.add("Darbų pabaigos laikas:\n" + endTime);
        PdfPCell cell1 = new PdfPCell();
        PdfPCell cell2 = new PdfPCell();
        cell1.addElement(startTimePara);
        cell2.addElement(endTimePara);


        table.addCell(cell1);
        table.addCell(cell2);

        return table;
    }

    private PdfPCell getCellPDF(String text, int aligment, int visable) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);

        Paragraph paragraph = new Paragraph();
        paragraph.setFont(font);
        paragraph.add(text);
        PdfPCell cell = new PdfPCell();
        cell.addElement(paragraph);
        cell.setPadding(visable);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(visable);
        return cell;
    }

    private Paragraph TitlePDF() throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 15);

        Paragraph Title = new Paragraph();
        Title.setFont(font);
        Title.setAlignment(Element.ALIGN_CENTER);
        Title.add("Darbų priėmimo ir perdavimo aktas");

        return Title;
    }

    private PdfPTable AfterTitlePDF(String actNumber, String createDate) throws DocumentException, IOException {
        PdfPTable afterTitle = new PdfPTable(2);
        afterTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
        afterTitle.setWidthPercentage(165);
        afterTitle.addCell(getCellPDF("Patvirtinta: gen. direktoriaus\nįsakymu Nr.To-1045984", 2, 0));
        afterTitle.addCell(getCellPDF("Darbų aktas Nr." + actNumber + "\nData " + createDate + "\n\n", 0, 0));


        return afterTitle;

    }

    public void openWorkActPDF(String workActNumber) throws SQLException {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(getWorkActFilePath() + "/" + workActNumber + ".pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
            }
        }
    }

    public void openInvoicePDF(String invoiceNumber) throws SQLException {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(getInvoicePath() + "/" + invoiceNumber + ".pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
            }
        }
    }

    public boolean isSameInvoiceNumberInDB(String number) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from invoice where Invoice_nr = ?");
        preparedStatement.setString(1, number);
        ResultSet rsl = preparedStatement.executeQuery();
        return rsl.next() != false;
    }


    /*-------------------------------------- Work act -------------------------------------------*/

    public WorkAct uploadWorkActFromDbByWorkActNumber(String workActNumber) throws SQLException {

        conncetToDB();
        WorkAct workAct = new WorkAct();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.work_act where Work_act_nr = ?;");
        preparedStatement.setString(1, workActNumber);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            String description = rzClient.getString(3);
            String startTime = rzClient.getString(4);
            String endTime = rzClient.getString(5);
            String date = rzClient.getString(6);
            String file = rzClient.getString(7);
            int firmID = rzClient.getInt(8);
            int clientID = rzClient.getInt(9);
            int UserID = rzClient.getInt(10);
            workAct.addWorkAct(id, workActNumber, description, startTime, endTime, date, file, firmID, clientID, UserID);

        }
        disconnectDB();
        return workAct;
    }

    public WorkAct uploadWorkActFromDbByID(int workActID) throws SQLException {

        conncetToDB();
        WorkAct workAct = new WorkAct();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.work_act where Work_act_ID = ?;");
        preparedStatement.setInt(1, workActID);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            String workActNumber = rzClient.getString(2);
            String description = rzClient.getString(3);
            String startTime = rzClient.getString(4);
            String endTime = rzClient.getString(5);
            String date = rzClient.getString(6);
            String file = rzClient.getString(7);
            int firmID = rzClient.getInt(8);
            int clientID = rzClient.getInt(9);
            int UserID = rzClient.getInt(10);
            workAct.addWorkAct(workActID, workActNumber, description, startTime, endTime, date, file, firmID, clientID, UserID);
        }
        disconnectDB();
        return workAct;
    }


    public void uploadWorkActToDB(String number, String description, String startTimeDate, String endTimeDate, String createdTime, String fileLocation, int firmId, int clientId, int userID) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("insert into gener_db_new.work_act values (Null,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, number);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, startTimeDate);
        preparedStatement.setString(4, endTimeDate);
        preparedStatement.setString(5, createdTime);
        preparedStatement.setString(6, fileLocation);
        preparedStatement.setInt(7, firmId);
        preparedStatement.setInt(8, clientId);
        preparedStatement.setInt(9, userID);
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public void updateWorkActToDB(int workActID, String number, String description, String startTimeDate, String endTimeDate, String createdTime, String fileLocation, int firmId, int clientId, int userID) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("UPDATE `gener_db_new`.`work_act` SET `Work_act_nr` = ?, `description` = ?, `start_time` = ?, `end_time` = ?, `date` = ?, `file` = ?, `Firm_firm_ID` = ?, `client_client_ID` = ?, `User_User_Login_UserID` = ? WHERE (`Work_act_ID` = ?);");
        preparedStatement.setInt(10, workActID);
        preparedStatement.setString(1, number);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, startTimeDate);
        preparedStatement.setString(4, endTimeDate);
        preparedStatement.setString(5, createdTime);
        preparedStatement.setString(6, fileLocation);
        preparedStatement.setInt(7, firmId);
        preparedStatement.setInt(8, clientId);
        preparedStatement.setInt(9, userID);
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public String generateWorkActNumber(String index) {
        Random rnd = new Random();
        int n = 10000000 + rnd.nextInt(90000000);
        return index += n;
    }

    public String generateInvoiceNumber(String index) {
        Random rnd = new Random();
        int n = 1000000 + rnd.nextInt(9000000);
        return index += n;
    }

    public boolean isWorkActNumberInDB(String workActNumber) throws SQLException {
        uploadWorkActFromDbByWorkActNumber(workActNumber);
        if (uploadWorkActFromDbByWorkActNumber(workActNumber).getNumber() != null) return true;
        return false;
    }

    public int getWorkActIDFromDbByWorkActNumber(String workActNumber) throws SQLException {

        conncetToDB();
        int id = 0;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.work_act where Work_act_nr = ?;");
        preparedStatement.setString(1, workActNumber);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            id = rzClient.getInt(1);
        }
        disconnectDB();
        return id;
    }

    public String getWorkActIndexFromDB() throws SQLException {
        conncetToDB();
        WorkAct workAct = new WorkAct();
        String index = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            index = rzClient.getString(3);
        }
        disconnectDB();
        return index;
    }

    public String getWorkActFilePath() throws SQLException {
        conncetToDB();
        WorkAct workAct = new WorkAct();
        String path = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            path = rzClient.getString(4);
        }
        disconnectDB();
        return path;

    }


    public String getDateAndTime() {
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        String date = String.valueOf(createDate).replace("T", " ");
        date = date.substring(0, date.length() - 3);

        return date;
    }

    public String priceRound(double price) {
        return String.format("%.2f", price);

    }

    public ArrayList getAllWorkActsForTable() throws SQLException {
        conncetToDB();
        ArrayList workActs = new ArrayList();

        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.work_act ;");
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            String workActNumber = rzClient.getString(2);
            String description = rzClient.getString(3);
            String startTime = rzClient.getString(4);
            String endTime = rzClient.getString(5);
            String date = rzClient.getString(6);
            String file = rzClient.getString(7);
            int firmID = rzClient.getInt(8);
            int clientID = rzClient.getInt(9);
            int UserID = rzClient.getInt(10);
            WorkAct workAct = new WorkAct();
            workAct.addWorkAct(id, workActNumber, description, startTime, endTime, date, file, firmID, clientID, UserID);

            workActs.add(workAct);
        }
        disconnectDB();
        return workActs;

    }

    public ArrayList getWorkActsFilteredByDate(LocalDate startDate, LocalDate endDate) throws SQLException, ParseException {

        ArrayList workActs = getAllWorkActsForTable();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList filteredWorkActs = new ArrayList();
        startDate = startDate.minusDays(1);
        endDate = endDate.plusDays(1);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date StartDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
        Date EndDate = Date.from(endDate.atStartOfDay(defaultZoneId).toInstant());
        int a = 0;
        try {
            while (workActs.get(a) != null) {

                WorkAct workAct = (WorkAct) workActs.get(a);
                Date workActCreationDate = formatter.parse(workAct.getCreatedTime());

                if (workActCreationDate.after(StartDate)  && workActCreationDate.before(EndDate))
                    filteredWorkActs.add(workAct);
                a++;
            }

        } catch (Exception e) {

        }

        return filteredWorkActs;
    }
    public int[] getLast6MonthsWorkactNumbers() throws SQLException {
        int intArray[] = new int[7];
        ArrayList workActs = getAllWorkActsForTable();

        int a = 0;
        int b=0;
        for(int i =5 ;i!=-1;i--)
        {

            LocalDate startMonth = LocalDate.now();
            startMonth = startMonth.minusDays(startMonth.getDayOfMonth()-1);

            try {

                while (workActs.get(a) != null) {

                    WorkAct workAct = (WorkAct) workActs.get(a);
                    LocalDate workActCreationDate = LocalDate.parse(workAct.getCreatedTime().substring(0,8)+"01");
                    if( workActCreationDate.isAfter(startMonth.minusMonths(i+1)) && workActCreationDate.isBefore(startMonth.minusMonths(i-1))) {
                        b++;
                    }
                    a++;
                }

            } catch (Exception e) {}
            a=0;
            intArray[i]= b;
            b=0;
        }

        return intArray;
    }
    public double[] getLast6MonthsWorkactWorkHours() throws SQLException {
        double intArray[] = new double[7];
        ArrayList workActs = getAllWorkActsForTable();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        int a = 0;
        double b=0;

        for(int i =5 ;i!=-1;i--)
        {

            LocalDate startMonth = LocalDate.now();
            startMonth = startMonth.minusDays(startMonth.getDayOfMonth()-1);

            try {

                while (workActs.get(a) != null) {



                    WorkAct workAct = (WorkAct) workActs.get(a);
                    LocalDateTime starttime= LocalDateTime.parse(workAct.getStartTimeDate(),df);
                    LocalDateTime endtime= LocalDateTime.parse(workAct.getEndTimeDate(),df);
                    LocalDate workActCreationDate = LocalDate.parse(workAct.getCreatedTime().substring(0,8)+"01");
                    if( workActCreationDate.isAfter(startMonth.minusMonths(i+1)) && workActCreationDate.isBefore(startMonth.minusMonths(i-1))) {
                        Duration duration = Duration.between(starttime,endtime);
                        b+=duration.toMinutes() ;
                    }
                    a++;
                }

            } catch (Exception e) {}
            a=0;
            intArray[i]= b/60;
            b=0;
        }

        return intArray;
    }


    public String[] getLast6MonthsinWords ()
    {
        String[] months = {"Sausis","Vasaris","Kovas","Balandis","Gegužė","Birželis","Liepa","Rugpjūtis", "Rugsėjis","Spalis","Lapkritis","Gruodis"};
        String [] lastMonths = new String[6];
        LocalDate currentDate = LocalDate.now();
        for (int i =5; i>-1;i--)
            lastMonths[i] = currentDate.minusMonths(i).getYear()+" " +months[currentDate.minusMonths(i).getMonthValue()-1];
return lastMonths;
    }





    public ArrayList searchWorkActsForTableByText(String text) throws SQLException {

        ArrayList workActs = new ArrayList();
        ArrayList firms=getFirmBySearchtext(text);
        conncetToDB();
        text = "SELECT * FROM gener_db_new.work_act where concat(Work_act_nr, Work_act_ID,Firm_firm_ID,User_User_Login_UserID) like '%" + text + "%'";
        PreparedStatement preparedStatement = this.conn.prepareStatement(text);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            int id = rzClient.getInt(1);
            String workActNumber = rzClient.getString(2);
            String description = rzClient.getString(3);
            String startTime = rzClient.getString(4);
            String endTime = rzClient.getString(5);
            String date = rzClient.getString(6);
            String file = rzClient.getString(7);
            int firmID = rzClient.getInt(8);
            int clientID = rzClient.getInt(9);
            int UserID = rzClient.getInt(10);
            WorkAct workAct = new WorkAct();
            workAct.addWorkAct(id, workActNumber, description, startTime, endTime, date, file, firmID, clientID, UserID);
            workActs.add(workAct);

        }
        disconnectDB();
 ;
        ArrayList allWorkActs = getAllWorkActsForTable();
        int a = 0;
        int i =0;
        int b=0;
        ArrayList beforefilterWorkActs = new ArrayList<>();

        try
        {
            while (firms.get(i)!=null)
            {
                Firm firm = (Firm) firms.get(i);
                while (allWorkActs.get(a)!=null)
                {
                    WorkAct workAct = (WorkAct) allWorkActs.get(a);
                    if(workAct.getFirmId() == firm.getId())
                    {
                        beforefilterWorkActs.add(workAct);
                    }
                    a++;
                }
                a=0;
                i++;

            }


        }catch (Exception e) {}
        workActs=deleteSameWorkactsFromArraylist(workActs,beforefilterWorkActs);




        return workActs;

    }

    private ArrayList deleteSameWorkactsFromArraylist(ArrayList workActs1, ArrayList workActs2)
    {
        int a=0;
        int b=0;
        try
        {
            while(workActs1.get(a)!=null)
            {
                WorkAct workAct1 = (WorkAct) workActs1.get(a);
                while(workActs2.get(b)!=null)
                {
                    WorkAct workAct2 = (WorkAct) workActs2.get(b);
                    if(workAct1 ==  workAct2 )
                    {
                        workActs2.remove(workActs2.get(b));
                    }
                    b++;
                }
                b=0;
                a++;
            }

        }catch (Exception e) {}

return workActs2;
    }

    public WorkAct getWorkActByID(int id) throws SQLException {
        conncetToDB();
        WorkAct workAct = new WorkAct();
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.work_act where Work_act_ID = ?;");
        preparedStatement.setInt(1, id);
        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            String workActNumber = rzClient.getString(2);
            String description = rzClient.getString(3);
            String startTime = rzClient.getString(4);
            String endTime = rzClient.getString(5);
            String date = rzClient.getString(6);
            String file = rzClient.getString(7);
            int firmID = rzClient.getInt(8);
            int clientID = rzClient.getInt(9);
            int UserID = rzClient.getInt(10);
            workAct.addWorkAct(id, workActNumber, description, startTime, endTime, date, file, firmID, clientID, UserID);
        }
        disconnectDB();
        return workAct;


    }
    public ArrayList getAllInvoicesFromDB() throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from invoice");
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList invoices = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String invoiceNumber = rzUser.getString(2);
            String date = rzUser.getString(3);
            int discount = rzUser.getInt(4);
            boolean discountBeforeTax = rzUser.getBoolean(5);
            int workActID = rzUser.getInt(6);
            int userID = rzUser.getInt(7);
            int vat = rzUser.getInt(8);
            Invoice invoice = new Invoice();
            invoice.addInvoice(id, invoiceNumber, date, discount, discountBeforeTax, workActID, userID,vat);
            invoices.add(invoice);
        }
        disconnectDB();
        return invoices;
    }
    public  ArrayList getFilteredInvoicesFromDBByDate(LocalDate startDate, LocalDate endDate) throws SQLException {


        ArrayList invoices= getAllInvoicesFromDB();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList filteredInvoices = new ArrayList();
        startDate = startDate.minusDays(1);
        endDate = endDate.plusDays(1);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date StartDate = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
        Date EndDate = Date.from(endDate.atStartOfDay(defaultZoneId).toInstant());

        int a = 0;
        try {
            while (invoices.get(a) != null) {


                Invoice invoice = (Invoice) invoices.get(a);
                Date InvoiceCreationDate = formatter.parse(invoice.getDate());

                if (InvoiceCreationDate.after(StartDate)  && InvoiceCreationDate.before(EndDate))
                    filteredInvoices.add(invoice);
                a++;
            }

        } catch (Exception e) {

        }

        return filteredInvoices;
    }
    public double[] getLast6MonthsInvoicePriceSum() throws SQLException {
        double[] price = new double[6];
        ArrayList invoices= getAllInvoicesFromDB();

        int a = 0;
        double b=0;
        for(int i =5 ;i!=-1;i--)
        {
            LocalDate startMonth = LocalDate.now();
            startMonth = startMonth.minusDays(startMonth.getDayOfMonth()-1);

            try {

                while (invoices.get(a) != null) {
                    Invoice invoice = (Invoice) invoices.get(a);

                    LocalDate invoiceCreationDate = LocalDate.parse(invoice.getDate().substring(0,8)+"01");

                    if( invoiceCreationDate.isAfter(startMonth.minusMonths(i+1)) && invoiceCreationDate.isBefore(startMonth.minusMonths(i-1))) {

                        WorkAct workAct =getWorkActByID(invoice.getWorkActID());
                        ArrayList usedMaterials = uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber());

                        double priceWhitoutTax = getDiscuontedPrice(
                                Double.parseDouble(getUsedMaterialPriceWhitoutTAX(usedMaterials)) ,invoice.getDiscount());
                        b+=priceWhitoutTax;
                    }
                    a++;
                }

            } catch (Exception e) { }
            a=0;
            price[i]= b;

            b=0;
        }

        return price;


    }
    public Invoice getInvoiceFromDBByID(int id) throws SQLException {
        conncetToDB();

        Invoice invoice = new Invoice();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from invoice where Invoice_ID= ?");
        preparedStatement.setInt(1, id);
        ResultSet rzUser = preparedStatement.executeQuery();
        while (rzUser.next()) {

            String invoiceNumber = rzUser.getString(2);
            String date = rzUser.getString(3);
            int discount = rzUser.getInt(4);
            boolean discountBeforeTax = rzUser.getBoolean(5);
            int workActID = rzUser.getInt(6);
            int userID = rzUser.getInt(7);
            int vat = rzUser.getInt(8);
            invoice.addInvoice(id, invoiceNumber, date, discount, discountBeforeTax, workActID, userID,vat);
        }
        disconnectDB();

        return invoice;
    }

    public Invoice getInvoiceFromDBByWorkActID(int WorkActid) throws SQLException {
        conncetToDB();

        Invoice invoice = new Invoice();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from invoice where Work_Act_ID= ?");
        preparedStatement.setInt(1, WorkActid);
        ResultSet rzUser = preparedStatement.executeQuery();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            String invoiceNumber = rzUser.getString(2);
            String date = rzUser.getString(3);
            int discount = rzUser.getInt(4);
            boolean discountBeforeTax = rzUser.getBoolean(5);
            int userID = rzUser.getInt(7);
            int vat = rzUser.getInt(8);
            invoice.addInvoice(id, invoiceNumber, date, discount, discountBeforeTax, WorkActid, userID,vat);
        }
        disconnectDB();
        return invoice;
    }

    public void uploadinvoiceToDB(String number, String date, int discount, boolean discountBeforeTax, int workActID, int userID,int vat) throws SQLException {
        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("INSERT INTO invoice values (NULL,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, number);
        preparedStatement.setString(2, date);
        preparedStatement.setInt(3, discount);
        preparedStatement.setBoolean(4, discountBeforeTax);
        preparedStatement.setInt(5, workActID);
        preparedStatement.setInt(6, userID);
        preparedStatement.setInt(6, userID);
        preparedStatement.setInt(7, vat);
        preparedStatement.executeUpdate();
        disconnectDB();
    }

    public String getInvoicePath() throws SQLException {
        conncetToDB();
        String path = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            path = rzClient.getString(6);
        }
        disconnectDB();
        return path;

    }
    public void setCompanyinfo(String workActSeries,String workActPath, String invoiceSeries,String invoicePath,
                                 String bankName, String iban, String email) throws SQLException {
        conncetToDB();
        String sql = "update gener_db_new.company set  work_act_index= ?, work_act_path = ? , invoice_series = ? , invoice_path=?, bank_name=? , iban=?,email=?  where company_ID = 1";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
        preparedStatement.setString(1, workActSeries);
        preparedStatement.setString(2, workActPath);
        preparedStatement.setString(3, invoiceSeries);
        preparedStatement.setString(4, invoicePath);
        preparedStatement.setString(5, bankName);
        preparedStatement.setString(6, iban);
        preparedStatement.setString(7, email);
        preparedStatement.executeUpdate();
        disconnectDB();

    }
    public void setCompanyEmailPassword(String password) throws SQLException {
        conncetToDB();
        String sql = "update gener_db_new.company set  emailPassword=?  where company_ID = 1";
        PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
        preparedStatement.setString(1,password);
        preparedStatement.executeUpdate();
        disconnectDB();

    }

    public String getInvoiceSeries() throws SQLException {
        conncetToDB();
        String series = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            series = rzClient.getString(5);
        }
        disconnectDB();
        return series;
    }

    public int getCompanyFirmID() throws SQLException {
        conncetToDB();
        int companyID = 0;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            companyID = rzClient.getInt(2);
        }
        disconnectDB();
        return companyID;
    }

    public String getCompanyBankName() throws SQLException {
        conncetToDB();
        String bankName = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            bankName = rzClient.getString(7);
        }
        disconnectDB();
        return bankName;

    }

    public String getCompanyiBan() throws SQLException {
        conncetToDB();
        String iBan = null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            iBan = rzClient.getString(8);
        }
        disconnectDB();
        return iBan;

    }

    public Boolean isCreatedInvoiceForWorkAct(int WorkActID) throws SQLException {

        conncetToDB();
        PreparedStatement preparedStatement = this.conn.prepareStatement("select * from invoice where Work_Act_ID = ?");
        preparedStatement.setInt(1, WorkActID);
        ResultSet rsl = preparedStatement.executeQuery();
        return rsl.next() != false;

    }


    public double getDiscuontedPrice(double price, int discount) {
        return ((100 - discount) * price) / 100;
    }


    public void createInvoicePDF(Invoice invoice) throws DocumentException, IOException, SQLException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(getInvoicePath() + "/" + invoice.getNumber() + ".pdf"));
        ArrayList usedMaterialList = getUsedMaterialsFromDBByWorkActID(invoice.getWorkActID());
        document.open();


        document.add(invoiceTitlePDF(invoice));
        document.add(invoiceCreateDatePDF(invoice));
        document.add(workActSpace(15));
        document.add(invoiceFirmsDetailsPDF(invoice));
        document.add(workActSpace(15));
        document.add(invoiceUsedMaterialTablePDF("Eil. nr.", "Pavadinimas", "Vienetai", "Kiekis", "Kaina"));
        int a = 0;
        try {
            while (usedMaterialList.get(a) != null) {

                UsedMaterial usedMaterial = (UsedMaterial) usedMaterialList.get(a);
                document.add(invoiceUsedMaterialTablePDF(String.valueOf(a + 1), usedMaterial.getName(), usedMaterial.getUnit(), String.valueOf(usedMaterial.getQuantity()), priceRound(usedMaterial.getPrice() * usedMaterial.getQuantity())));
                a++;
            }

        } catch (Exception e) {
        }
        document.add(invoicePricesPDF(usedMaterialList, invoice));
        document.add(invoiceUserSigniturePDF(invoice));
        document.add(workActSpace(30));
        document.add(invoiceBankInfoPDF());


        document.close();


    }

    private Paragraph invoiceTitlePDF(Invoice invoice) throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 14);

        Paragraph Title = new Paragraph();
        Title.setFont(font);
        Title.setAlignment(Element.ALIGN_LEFT);
        Title.add("PVM SĄSAKITA-FAKTŪRA Serija " + invoice.getNumber().substring(0, 3) + " Nr." + invoice.getNumber().substring(3));
        return Title;
    }

    private Paragraph invoiceCreateDatePDF(Invoice invoice) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        Paragraph creationDate = new Paragraph();
        creationDate.setFont(font);
        creationDate.setAlignment(Element.ALIGN_LEFT);
        creationDate.add("Išrašymo data:  " + invoice.getDate());
        return creationDate;
    }

    private Element invoiceFirmsDetailsPDF(Invoice invoice) throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 10);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(120);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph ClientFirmInfo = new Paragraph();
        Paragraph CompanyFirmInfo = new Paragraph();

        ClientFirmInfo.setFont(font);
        CompanyFirmInfo.setFont(font);

        WorkAct workAct = getWorkActByID(invoice.getWorkActID());
        Firm clientFirm = getFirmByFirmId(workAct.getFirmId());
        Firm coapany = getFirmByFirmId(getCompanyFirmID());

        if(clientFirm.getVatNumber() == null)
        {
            ClientFirmInfo.add("Pirkėjas: \n" + clientFirm.getName() + "\n" + clientFirm.getAdress() + "\nĮmonės kodas: " + clientFirm.getFirmNumber() +  "\n");
            CompanyFirmInfo.add("                   Pardavėjas: \n                   " + coapany.getName() + "\n                   " + coapany.getAdress() + "\n                   Įmonės kodas: " + coapany.getFirmNumber() + "\n                   PVM mokėtojo kodas: " + coapany.getVatNumber() + "\n");

        }
        else
        {
            ClientFirmInfo.add("Pirkėjas: \n" + clientFirm.getName() + "\n" + clientFirm.getAdress() + "\nĮmonės kodas: " + clientFirm.getFirmNumber() + "\nPVM mokėtojo kodas: " + clientFirm.getVatNumber() + "\n");
            CompanyFirmInfo.add("                   Pardavėjas: \n                   " + coapany.getName() + "\n                   " + coapany.getAdress() + "\n                   Įmonės kodas: " + coapany.getFirmNumber() + "\n                   PVM mokėtojo kodas: " + coapany.getVatNumber() + "\n");
        }


        PdfPCell clientInfoCell = new PdfPCell();
        PdfPCell companyInfoCell = new PdfPCell();

        clientInfoCell.addElement(ClientFirmInfo);
        companyInfoCell.addElement(CompanyFirmInfo);
        clientInfoCell.setUseVariableBorders(true);
        clientInfoCell.setBorderColorLeft(BaseColor.WHITE);
        clientInfoCell.setBorderColorRight(BaseColor.WHITE);
        clientInfoCell.setBorderColorBottom(BaseColor.WHITE);
        companyInfoCell.setUseVariableBorders(true);
        companyInfoCell.setBorderColorLeft(BaseColor.WHITE);
        companyInfoCell.setBorderColorRight(BaseColor.WHITE);
        companyInfoCell.setBorderColorBottom(BaseColor.WHITE);

        table.addCell(clientInfoCell);

        table.addCell(companyInfoCell);
        table.setWidthPercentage(100);

        return table;
    }

    private Element invoiceUsedMaterialTablePDF(String id, String name, String units, String quantity, String price) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 9);
        PdfPTable table = new PdfPTable(5); // 3 columns.
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        Paragraph idPara = new Paragraph();
        Paragraph namePara = new Paragraph();
        Paragraph unitsPara = new Paragraph();
        Paragraph qunatityPara = new Paragraph();
        Paragraph pricePara = new Paragraph();
        pricePara.setAlignment(Element.ALIGN_RIGHT);
        qunatityPara.setAlignment(Element.ALIGN_RIGHT);

        idPara.setFont(font);
        namePara.setFont(font);
        unitsPara.setFont(font);
        qunatityPara.setFont(font);
        pricePara.setFont(font);

        idPara.add(id);
        namePara.add(name);
        unitsPara.add(units);
        qunatityPara.add(quantity);
        pricePara.add(price);


        PdfPCell idcell = new PdfPCell();
        PdfPCell namecell = new PdfPCell();
        PdfPCell unitscell = new PdfPCell();
        PdfPCell quantitycell = new PdfPCell();
        PdfPCell pricecell = new PdfPCell();

        idcell.addElement(idPara);
        namecell.addElement(namePara);
        unitscell.addElement(unitsPara);
        quantitycell.addElement(qunatityPara);
        pricecell.addElement(pricePara);

        table.addCell(idcell);
        table.addCell(namecell);
        table.addCell(unitscell);
        table.addCell(quantitycell);
        table.addCell(pricecell);


        return table;


    }

    private Element invoicePricesPDF(ArrayList usedMaterials, Invoice invoice) throws DocumentException, IOException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 11);
        PdfPTable table = new PdfPTable(2); // 3 columns.
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        Paragraph pricePara = new Paragraph();
        Paragraph vatPara = new Paragraph();
        Paragraph sumPara = new Paragraph();
        Paragraph discountPara = new Paragraph();
        Paragraph text = new Paragraph();
        pricePara.setAlignment(Element.ALIGN_RIGHT);
        vatPara.setAlignment(Element.ALIGN_RIGHT);
        sumPara.setAlignment(Element.ALIGN_RIGHT);
        discountPara.setAlignment(Element.ALIGN_RIGHT);


        pricePara.setFont(font);
        vatPara.setFont(font);
        sumPara.setFont(font);
        discountPara.setFont(font);
        text.setFont(font);
        text.setAlignment(2);


        PdfPCell pricecell = new PdfPCell();
        PdfPCell vatcell = new PdfPCell();
        PdfPCell sumcell = new PdfPCell();
        PdfPCell discountcell = new PdfPCell();
        PdfPCell cell1 = new PdfPCell();
        PdfPCell cell2 = new PdfPCell();
        PdfPCell cell3 = new PdfPCell();
        PdfPCell cell4 = new PdfPCell();


        if (invoice.getDiscount() > 0) {
            if (invoice.isDiscountBeforeTax()) {
                double priceWhitoutTax = getDiscuontedPrice(Double.valueOf(getUsedMaterialPriceWhitoutTAX(usedMaterials)), invoice.getDiscount());
                double taxPrice = getDiscuontedPrice(priceWhitoutTax * 0.21, invoice.getDiscount());
                double priceWhitTax = priceWhitoutTax + taxPrice;

                pricePara.add(priceRound(priceWhitoutTax));
                vatPara.add(priceRound(taxPrice));
                discountPara.add("-" + invoice.getDiscount() + "%");
                sumPara.add(priceRound(priceWhitTax));

            } else {
                System.out.println("False");
                double priceWhitoutTax = Double.parseDouble(getUsedMaterialPriceWhitoutTAX(usedMaterials));
                double taxPrice = priceWhitoutTax * ((double) invoice.getVat()/100);
                double priceWhitTax = getDiscuontedPrice(priceWhitoutTax + taxPrice, invoice.getDiscount());

                pricePara.add(priceRound(priceWhitoutTax));
                vatPara.add(priceRound(taxPrice));
                discountPara.add("-" + invoice.getDiscount() + "%");
                sumPara.add(priceRound(priceWhitTax));


            }


        } else {
            double priceWhitoutTax = Double.parseDouble(getUsedMaterialPriceWhitoutTAX(usedMaterials));
            double taxPrice = priceWhitoutTax * ((double) invoice.getVat()/100);
            double priceWhitTax = priceWhitoutTax + taxPrice;

            pricePara.add(priceRound(priceWhitoutTax));
            vatPara.add(priceRound(taxPrice));
            discountPara.add("-" + invoice.getDiscount() + "%");
            sumPara.add(priceRound(priceWhitTax));


        }
        pricecell.addElement(pricePara);
        vatcell.addElement(vatPara);
        sumcell.addElement(sumPara);
        discountcell.addElement(discountPara);


        text.clear();
        text.add("Suma be PVM: ");
        cell1.addElement(text);
        text.clear();
        text.add("PVM suma ("+invoice.getVat()+"%): ");
        cell2.addElement(text);
        text.clear();
        text.add("Nuolaida: ");
        cell4.addElement(text);
        text.clear();
        text.add("Iš viso: ");
        cell3.addElement(text);


        cell1.setBorder(0);
        cell2.setBorder(0);
        cell3.setBorder(0);
        cell4.setBorder(0);


        table.addCell(cell1);
        table.addCell(pricecell);
        table.addCell(cell2);
        table.addCell(vatcell);
        table.addCell(cell4);
        table.addCell(discountcell);
        table.addCell(cell3);
        table.addCell(sumcell);


        return table;
    }

    private Element invoiceUserSigniturePDF(Invoice invoice) throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 10);
        PdfPTable table = new PdfPTable(1); // 3 columns.
        table.setWidthPercentage(70);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        Paragraph userPara = new Paragraph();
        Paragraph ClientPara = new Paragraph();


        userPara.setFont(font);
        ClientPara.setFont(font);

        User user = getUserByID(invoice.getUserID());

        userPara.add("Sąskaitą išrašė: " + user.getRole() + " " + user.getName() + " " + user.getSurname());
        ClientPara.add("Sąskaitą gavo: ");

        PdfPCell usercell = new PdfPCell();
        PdfPCell clientcell = new PdfPCell();

        usercell.addElement(userPara);
        clientcell.addElement(ClientPara);


        usercell.setUseVariableBorders(true);
        usercell.setBorderColorLeft(BaseColor.WHITE);
        usercell.setBorderColorRight(BaseColor.WHITE);
        usercell.setBorderColorTop(BaseColor.WHITE);
        usercell.setFixedHeight(30);

        clientcell.setUseVariableBorders(true);
        clientcell.setBorderColorLeft(BaseColor.WHITE);
        clientcell.setBorderColorRight(BaseColor.WHITE);
        clientcell.setBorderColorTop(BaseColor.WHITE);
        clientcell.setFixedHeight(30);

        table.addCell(usercell);
        table.addCell(clientcell);

        return table;

    }

    private Paragraph invoiceBankInfoPDF() throws DocumentException, IOException, SQLException {
        BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bfComic, 8);

        Paragraph Title = new Paragraph();
        Title.setFont(font);
        Title.setAlignment(Element.ALIGN_LEFT);
        Title.add("Apmokėti sąskaitą galite:\n " + getCompanyBankName() + " " + getCompanyiBan());
        return Title;
    }

    public String getCompanyEmail() throws SQLException {
        conncetToDB();
        String email=null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            email = rzClient.getString(9);
        }
        disconnectDB();
        return email;

    }
    private String getCompanyEmailPassword() throws SQLException {
        conncetToDB();
        String password=null;
        PreparedStatement preparedStatement = this.conn.prepareStatement("SELECT * FROM gener_db_new.company;");

        ResultSet rzClient = preparedStatement.executeQuery();
        while (rzClient.next()) {
            password = rzClient.getString(10);
        }
        disconnectDB();
        return password;

    }

    public boolean isValidWorkActSeries (String series){

        if(series.length() != 2 )
        {
            return false;
        }
        if(isNumeric(series))
        {
            return false;
        }
        if(!series.equals(series.toUpperCase()))
        {
            return false;
        }
        return true;
    }
    public boolean isValidInvoiceSeries (String series){
        if(series.length() != 3 )
        {
            return false;
        }
        if(isNumeric(series))
        {
            return false;
        }
        if(!series.equals(series.toUpperCase()))
        {
            return false;
        }
        return true;

    }

    public boolean isValidIBan (String iban){

        if(iban.length() != 20)
        {
            return false;
        }
        if (!isNumeric(iban.substring(2,20)) )
        {
            return false;
        }
return true;
    }


    public void sendUserPassword ( String userEmail,String username ,String userPassword) throws IOException, SQLException {
        String to = userEmail;
        String from = getCompanyEmail();
        String emailPassword = getCompanyEmailPassword();
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, emailPassword);

            }

        });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Siunčiame jūsų prisijungimo duomenis: \n" +
                    "Vartotojo prisijungimo slapyvardis: "+username+"\n" +
                    "Vartotojo slaptažodis: "+ userPassword );

            emailContent.addBodyPart(textBodyPart);
            message.setContent(emailContent);
            message.setSubject("Prisijungimo prie Generavimo Sistemos duomenys");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    public void sendIvoicePDFviaEmail ( String userEmail, Invoice invoice) throws IOException, SQLException {
        String to = userEmail;
        String from = getCompanyEmail();
        String emailPassword = getCompanyEmailPassword();
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, emailPassword);

            }

        });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart emailContent = new MimeMultipart();

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(getInvoicePath() + "/" + invoice.getNumber() + ".pdf");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Laba diena, \nSiunčiame jūsų sąskaitą faktūrą.");

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);
            message.setContent(emailContent);
            message.setSubject("Sąskaita faktūra "+invoice.getNumber());
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
    public void sendWorkActPDFviaEmail (WorkAct workAct) throws IOException, SQLException {
        Client client = getClientByID(workAct.getClientId());
        String to = client.email;
        String from = getCompanyEmail();
        String emailPassword = getCompanyEmailPassword();
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, emailPassword);

            }

        });
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart emailContent = new MimeMultipart();

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(getWorkActFilePath() + "/" + workAct.getNumber() + ".pdf");

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Laba diena, \nSiunčiame darbų aktą.");

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);
            message.setContent(emailContent);
            message.setSubject("Sąskaita faktūra "+workAct.getNumber());
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    public ArrayList getFirmBySearchtext (String text) throws SQLException {
        conncetToDB();
        text = "SELECT * FROM gener_db_new.firm where concat(firm_number, vat_number,name,adress) like '%" + text + "%'";
        PreparedStatement preparedStatement = this.conn.prepareStatement(text);
        ResultSet rzUser = preparedStatement.executeQuery();
        ArrayList firms = new ArrayList();
        while (rzUser.next()) {
            int id = rzUser.getInt(1);
            int firmNumber = rzUser.getInt(2);
            String vatNumber = rzUser.getString(3);
            String name = rzUser.getString(4);
            String adress = rzUser.getString(5);
            String formName = rzUser.getString(6);
            int state = rzUser.getInt(7);
            Firm firm = new Firm();
            String email = rzUser.getString(8);
            firm.addFirm(id, firmNumber, vatNumber, name, adress, formName, state, email);

            firms.add(firm);

        }
        disconnectDB();

        return firms;
    }




}


