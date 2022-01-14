package com.pany.core.dao;

import com.pany.core.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    public List<Person> findAllPerson() {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");
            PreparedStatement pst = con
                    .prepareStatement("Select * from kullanicilar");
            ResultSet resultSet = pst.executeQuery();
            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");

                Person person = new Person();
                person.setId(id);
                person.setName(name);
                person.setNumber(number);
                personList.add(person);
            }
            return personList;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean savePerson(Person person) {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");
            PreparedStatement pst = con
                    .prepareStatement("Insert into kullanicilar (id, name, number) values (?, ?, ?)");
            pst.setInt(1,person.getId());
            pst.setString(2, person.getName());
            pst.setString(3, person.getNumber());
            int insertedRows = pst.executeUpdate();
            if (insertedRows > 0) return true;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePerson(Person person) throws SQLException {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");
            PreparedStatement pst = con
                    .prepareStatement("UPDATE kullanicilar SET name =?, number =? WHERE id=?");
            pst.setString(1, person.getName());
            pst.setString(2, person.getNumber());
            pst.setInt(3, person.getId());
            int insertedRows = pst.executeUpdate();
            if (insertedRows > 0) return true;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*public boolean deletePerson(Person person) {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");
            PreparedStatement pst = con
                    .prepareStatement("DELETE FROM kullanicilar WHERE id = 1" );
            //pst.setString(1, person.getName());
            int insertedRows = pst.executeUpdate();
            if (insertedRows > 0)
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public boolean deleteEmployee(Person person) throws SQLException {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");

            String sql = "DELETE FROM kullanicilar WHERE name = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, person.getName());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Başarıyla silindi!");
                return true;
            }

        }catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Person findPersonById(Long id) {

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/uniyaz", "root", "root");
            PreparedStatement pst = con
                    .prepareStatement("Select * from kullanicilar WHERE  id="+id);
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            //pst.setString(1, );

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
