import java.util.Objects;

public class Contact {
    private final String contactId;     // unique, non-null, <= 10, immutable
    private String firstName;           // non-null, <= 10
    private String lastName;            // non-null, <= 10
    private String phone;               // non-null, exactly 10 digits
    private String address;             // non-null, <= 30

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        validateId(contactId);
        this.contactId = contactId;

        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    private static void validateId(String contactId) {
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID must be non-null and at most 10 characters.");
        }
    }
    private static void validateFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("First name must be non-null and at most 10 characters.");
        }
    }
    private static void validateLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Last name must be non-null and at most 10 characters.");
        }
    }
    private static void validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits.");
        }
    }
    private static void validateAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Address must be non-null and at most 30 characters.");
        }
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public String getPhone()     { return phone;     }
    public String getAddress()   { return address;   }

    public void setFirstName(String firstName) { validateFirstName(firstName); this.firstName = firstName; }
    public void setLastName(String lastName)   { validateLastName(lastName);   this.lastName = lastName;   }
    public void setPhone(String phone)         { validatePhone(phone);         this.phone = phone;         }
    public void setAddress(String address)     { validateAddress(address);     this.address = address;     }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return contactId.equals(contact.contactId);
    }
    @Override public int hashCode() { return Objects.hash(contactId); }
}
