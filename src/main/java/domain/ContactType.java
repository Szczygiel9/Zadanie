package domain;

public enum ContactType {

    UNKNOWN(0),
    EMAIL(1),
    PHONE(2),
    JABBER(3);

    private int contactTypeId;

    ContactType(final int type) {
        this.contactTypeId = type;
    }

    public int getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(final int contactTypeId) {
        this.contactTypeId = contactTypeId;
    }
}
