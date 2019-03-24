package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

    private String phone;
    private String email;
    private String jabber;
    private String unknown;
    @JsonIgnore
    private ContactType type;

    public Contact(final String contact) {
        if (contact.matches("^(.+)@(.+)$")) {
            this.email = contact;
            this.type = ContactType.EMAIL;
        } else if (contact.matches("^[0-9() ]+$")) {
            this.phone = contact;
            this.type = ContactType.PHONE;
        } else if (contact.equals("jbr")) {
            this.jabber = contact;
            this.type = ContactType.JABBER;
        } else {
            this.unknown = contact;
            this.type = ContactType.UNKNOWN;
        }
    }

    public String getContent() {
        switch (type) {
            case EMAIL:
                return this.email;
            case PHONE:
                return this.phone;
            case JABBER:
                return this.jabber;
            case UNKNOWN:
                return this.unknown;
        }
        throw new IllegalStateException("No contact content");
    }
}
