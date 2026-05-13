import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages contact records in memory.
 * This service separates business logic from the Contact data model,
 * prevents duplicate records, and protects stored data from unsafe access.
 */
public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    public Contact addContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact contact = new Contact(contactId, firstName, lastName, phone, address);
        return addContact(contact);
    }

    public Contact addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }

        String contactId = contact.getContactId();

        if (contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Duplicate contact ID: " + contactId);
        }

        contacts.put(contactId, contact);
        return contact;
    }

    public boolean deleteContact(String contactId) {
        if (contactId == null || contactId.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact ID cannot be null or blank.");
        }

        return contacts.remove(contactId.trim()) != null;
    }

    public void updateFirstName(String contactId, String newFirstName) {
        getRequiredContact(contactId).setFirstName(newFirstName);
    }

    public void updateLastName(String contactId, String newLastName) {
        getRequiredContact(contactId).setLastName(newLastName);
    }

    public void updatePhone(String contactId, String newPhone) {
        getRequiredContact(contactId).setPhone(newPhone);
    }

    public void updateAddress(String contactId, String newAddress) {
        getRequiredContact(contactId).setAddress(newAddress);
    }

    public Contact getContact(String contactId) {
        if (contactId == null || contactId.trim().isEmpty()) {
            return null;
        }

        return contacts.get(contactId.trim());
    }

    public Map<String, Contact> getAllContacts() {
        return Collections.unmodifiableMap(new HashMap<>(contacts));
    }

    public int getContactCount() {
        return contacts.size();
    }

    private Contact getRequiredContact(String contactId) {
        Contact contact = getContact(contactId);

        if (contact == null) {
            throw new IllegalArgumentException("No contact found with ID: " + contactId);
        }

        return contact;
    }
}