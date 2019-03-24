package deserializer;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import domain.Contact;
import domain.ContactType;
import domain.Customer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeserializationTest {

    @Test
    public void csvTest() throws IOException {
        String csvFormattedCustomer = "Jan,Kowalski,12,Lublin,123123123,654 765 765,kowalski@gmail.com,jan@gmail.com\n";

        final Customer customer = new CsvMapper().readerWithTypedSchemaFor(Customer.class).readValue(csvFormattedCustomer);
        validateCustomer(customer);
    }

    @Test
    public void xmlTest() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        String xml = "<Contact>\n" +
                "    <name>Jan</name>\n" +
                "    <surname>Kowalski</surname>\n" +
                "    <age>12</age>\n" +
                "    <city>Lublin</city>\n" +
                "    <contacts>\n" +
                "        <phone>123123123</phone>\n" +
                "        <phone>654 765 765</phone>\n" +
                "        <email>kowalski@gmail.com</email>\n" +
                "        <email>jan@gmail.com</email>\n" +
                "    </contacts>\n" +
                "</Contact>";

        final Customer customer = xmlMapper.readValue(xml, Customer.class);

        validateCustomer(customer);
    }

    private void validateCustomer(final Customer customer) {
        assertEquals("Jan", customer.getName());
        assertEquals("Kowalski", customer.getSurname());
        assertEquals(Integer.valueOf(12), customer.getAge());
        assertEquals("Lublin", customer.getCity());

        final List<Contact> contacts = customer.getContacts();
        assertEquals(4, contacts.size());
        final Contact phone1 = contacts.get(0);
        final Contact phone2 = contacts.get(1);
        final Contact email1 = contacts.get(2);
        final Contact email2 = contacts.get(3);

        assertEquals("123123123", phone1.getPhone());
        assertEquals(ContactType.PHONE, phone1.getType());

        assertEquals("654 765 765", phone2.getPhone());
        assertEquals(ContactType.PHONE, phone2.getType());

        assertEquals("kowalski@gmail.com", email1.getEmail());
        assertEquals(ContactType.EMAIL, email1.getType());

        assertEquals("jan@gmail.com", email2.getEmail());
        assertEquals(ContactType.EMAIL, email2.getType());
    }
}
