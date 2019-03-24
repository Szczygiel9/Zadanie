package domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"name", "surname", "age", "city"})
public class Customer {

    private String name;
    private String surname;
    private Integer age;
    private String city;
    private List<Contact> contacts = new ArrayList<Contact>();

}
