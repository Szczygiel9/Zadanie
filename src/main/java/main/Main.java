package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import db.DBConnection;
import domain.Customer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę użytkownika do DB: ");
        String dbUsername = scanner.nextLine();
        System.out.println("Podaj hasło użytkownika do DB: ");
        String dbPassword = scanner.nextLine();
        System.out.println("Wklej plik z danymi do głównego katalogu projektu i podaj jego nazwę wraz z rozszerzeniem: ");
        String filename = scanner.nextLine();

        File xmlFile = new File(filename);
        String fileExtension = getFileExtension(filename);
        ObjectMapper mapper = null;

        if (fileExtension.equals("xml")) {
            mapper = new XmlMapper();
        } else if (fileExtension.equals("csv")) {
            new CsvMapper().readerWithTypedSchemaFor(Customer.class);
        }

        final List<Customer> customers = Arrays.asList(mapper.readValue(xmlFile, Customer[].class));

        final DBConnection dbConnection = new DBConnection();
        dbConnection.connectAndInsert(dbUsername, dbPassword, customers);

        System.out.println("Zapisano.");
    }

    private static String getFileExtension(final String filename) {
        int i = filename.lastIndexOf('.');
        if (i >= 0) {
            return filename.substring(i + 1);
        }
        throw new IllegalStateException("File without extension.");
    }

}
