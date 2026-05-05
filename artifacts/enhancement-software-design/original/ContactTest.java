import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void constructor_validatesAllFields_success() {
        Contact c = new Contact("ID123", "John", "Doe", "0123456789", "123 Main Street, Town");
        assertEquals("ID123", c.getContactId());
        assertEquals("John", c.getFirstName());
        assertEquals("Doe", c.getLastName());
        assertEquals("0123456789", c.getPhone());
        assertEquals("123 Main Street, Town", c.getAddress());
    }

    // contactId constraints
    @Test
    void contactId_null_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact(null, "John", "Doe", "0123456789", "123 Main St"));
    }

    @Test
    void contactId_tooLong_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ABCDEFGHIJK", "John", "Doe", "0123456789", "123 Main St")); // 11 chars
    }

    // firstName constraints
    @Test
    void firstName_null_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", null, "Doe", "0123456789", "123 Main St"));
    }

    @Test
    void firstName_tooLong_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "Christopher", "Doe", "0123456789", "123 Main St")); // 11 chars
    }

    // lastName constraints
    @Test
    void lastName_null_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", null, "0123456789", "123 Main St"));
    }

    @Test
    void lastName_tooLong_throws() {
        String tooLong = "A".repeat(11);   // 11 chars, guaranteed over the limit
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", tooLong, "0123456789", "123 Main St"));
    }

    // phone constraints
    @Test
    void phone_null_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", null, "123 Main St"));
    }

    @Test
    void phone_wrongLength_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", "123456789", "123 Main St"));
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", "12345678901", "123 Main St"));
    }

    @Test
    void phone_nonDigits_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", "12345abcde", "123 Main St"));
    }

    // address constraints
    @Test
    void address_null_throws() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", "0123456789", null));
    }

    @Test
    void address_tooLong_throws() {
        String tooLong = "1234567890123456789012345678901"; // 31
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("ID", "John", "Doe", "0123456789", tooLong));
    }

    // setters update allowed fields and still validate
    @Test
    void setters_updateAndValidate() {
        Contact c = new Contact("ID", "John", "Doe", "0123456789", "123 Main St");
        c.setFirstName("Jane");
        c.setLastName("Smith");
        c.setPhone("1112223333");
        c.setAddress("456 Oak Ave");

        assertEquals("Jane", c.getFirstName());
        assertEquals("Smith", c.getLastName());
        assertEquals("1112223333", c.getPhone());
        assertEquals("456 Oak Ave", c.getAddress());

        assertThrows(IllegalArgumentException.class, () -> c.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setLastName("ThisIsWayTooLong"));
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("badnumber"));
        assertThrows(IllegalArgumentException.class, () -> c.setAddress(new String(new char[31]).replace('\0','x')));
    }

    @Test
    void contactId_isImmutable() {
        Contact c = new Contact("LOCKED", "John", "Doe", "0123456789", "123 Main St");
        assertEquals("LOCKED", c.getContactId());
        // No setter exists for contactId; compile-time guarantee of immutability
    }
}
