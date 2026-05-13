import java.util.Objects;

/**
 * Represents a contact record.
 * The contact ID is immutable after creation to protect record identity.
 * All editable fields are validated before being stored.
 */
public class Contact {
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int PHONE_LENGTH = 10;
    private static final int MAX_ADDRESS_LENGTH = 30;

    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        this.contactId = validateRequiredText(contactId, "Contact ID", MAX_ID_LENGTH);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    private static String validateRequiredText(String value, String fieldName, int maxLength) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank.");
        }

        String cleanedValue = value.trim();

        if (cleanedValue.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot exceed " + maxLength + " characters.");
        }

        return cleanedValue;
    }

    private static String validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{" + PHONE_LENGTH + "}")) {
            throw new IllegalArgumentException("Phone must contain exactly 10 digits.");
        }

        return phone;
    }

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = validateRequiredText(firstName, "First name", MAX_NAME_LENGTH);
    }

    public void setLastName(String lastName) {
        this.lastName = validateRequiredText(lastName, "Last name", MAX_NAME_LENGTH);
    }

    public void setPhone(String phone) {
        this.phone = validatePhone(phone);
    }

    public void setAddress(String address) {
        this.address = validateRequiredText(address, "Address", MAX_ADDRESS_LENGTH);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Contact)) {
            return false;
        }

        Contact contact = (Contact) object;
        return contactId.equals(contact.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }
}