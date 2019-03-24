package db;

import domain.Contact;
import domain.Customer;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class DBConnection {

    private Connection conn;

    @SneakyThrows
    public void connectAndInsert(String dbUsername, String dbPassword, List<Customer> customers) {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/zadanie";
        conn = DriverManager.getConnection(url, dbUsername, dbPassword);
        putCustomers(customers);
        conn.close();
    }

    private void putCustomers(List<Customer> customers) {

        customers.forEach(customer -> {
            final long customerId = insertCustomer(customer);

            List<Contact> contacts = customer.getContacts();
            contacts.forEach(contact -> insertContact(contact, customerId));
        });
    }


    @SneakyThrows
    private long insertCustomer(Customer customer) {
        String name = customer.getName();
        String surname = customer.getSurname();
        Integer age = customer.getAge();

        Statement st = conn.createStatement();
        st.executeQuery("INSERT INTO CUSTOMERS VALUES (" + name + ", " + surname + ", " + age + ")");
        ResultSet generatedKeys = st.getGeneratedKeys();
        st.close();

        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    @SneakyThrows
    private void insertContact(Contact contact, long customerId) {
        int contactTypeId = contact.getType().getContactTypeId();
        String content = contact.getContent();

        Statement st = conn.createStatement();
        st.executeQuery("INSERT INTO CONTACTS VALUES (" + customerId + ", " + contactTypeId + ", " + content + ")");
        st.close();
    }
}
