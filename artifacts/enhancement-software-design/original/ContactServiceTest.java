import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {
    private ContactService service;

    @BeforeEach
    void setup() {
        service = new ContactService();
    }

    @Test
    void addContact_uniqueId_success() {
        Contact c = service.addContact("C1", "John", "Doe", "0123456789", "123 Main St");
        assertNotNull(c);
        assertEquals("C1", service.getContact("C1").getContactId());
    }

    @Test
    void addContact_duplicateId_throws() {
        service.addContact("C1", "John", "Doe", "0123456789", "123 Main St");
        assertThrows(IllegalArgumentException.class,
            () -> service.addContact("C1", "Jane", "Smith", "1112223333", "456 Oak Ave"));
    }

    @Test
    void deleteContact_existing_returnsTrue_andRemoves() {
        service.addContact("C1", "John", "Doe", "0123456789", "123 Main St");
        assertTrue(service.deleteContact("C1"));
        assertNull(service.getContact("C1"));
    }

    @Test
    void deleteContact_missing_returnsFalse() {
        assertFalse(service.deleteContact("NOPE"));
    }

    @Test
    void updateFirstName_lastName_phone_address_success() {
        service.addContact("C1", "John", "Doe", "0123456789", "123 Main St");
        service.updateFirstName("C1", "Jane");
        service.updateLastName("C1", "Smith");
        service.updatePhone("C1", "1112223333");
        service.updateAddress("C1", "456 Oak Ave");

        Contact c = service.getContact("C1");
        assertNotNull(c);
        assertEquals("Jane", c.getFirstName());
        assertEquals("Smith", c.getLastName());
        assertEquals("1112223333", c.getPhone());
        assertEquals("456 Oak Ave", c.getAddress());
    }

    @Test
    void update_missingContact_throws() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("NOPE", "Jane"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("NOPE", "Smith"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("NOPE", "1112223333"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("NOPE", "456 Oak Ave"));
    }

    @Test
    void update_invalidValues_throws() {
        service.addContact("C1", "John", "Doe", "0123456789", "123 Main St");
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("C1", null));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("C1", "ThisIsWayTooLong"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("C1", "bad"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("C1", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
    }
}
