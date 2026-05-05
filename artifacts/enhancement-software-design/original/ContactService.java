package src.main.java;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    // Add by fields (handy for tests and usage)
    public Contact addContact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Duplicate contact ID: " + contactId);
        }
        Contact c = new Contact(contactId, firstName, lastName, phone, address);
        contacts.put(contactId, c);
        return c;
    }

    // Add by object (optional convenience)
    public Contact addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Duplicate contact ID: " + contact.getContactId());
        }
        contacts.put(contact.getContactId(), contact);
        return contact;
    }

    // Delete by ID
    public boolean deleteContact(String contactId) {
        return contacts.remove(contactId) != null;
    }

    // Update helpers (only specified fields are updatable)
    public void updateFirstName(String contactId, String newFirstName) {
        Contact c = getRequired(contactId);
        c.setFirstName(newFirstName);
    }

    public void updateLastName(String contactId, String newLastName) {
        Contact c = getRequired(contactId);
        c.setLastName(newLastName);
    }

    public void updatePhone(String contactId, String newPhone) {
        Contact c = getRequired(contactId);
        c.setPhone(newPhone);
    }

    public void updateAddress(String contactId, String newAddress) {
        Contact c = getRequired(contactId);
        c.setAddress(newAddress);
    }

    // Utility
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    public Map<String, Contact> getAll() {
        return Collections.unmodifiableMap(contacts);
    }

    private Contact getRequired(String contactId) {
        Contact c = contacts.get(contactId);
        if (c == null) throw new IllegalArgumentException("No contact with ID: " + contactId);
        return c;
    }
}
