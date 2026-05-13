import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void constructor_validInput_createsContact() {
        Contact contact = new Contact("ID123", "John", "Doe", "0123456789", "123 Main Street");

        assertEquals("ID123", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("0123456789", contact.getPhone());
        assertEquals("123 Main Street", contact.getAddress());
    }

    @Test
    void constructor_trimsTextFields() {
        Contact contact = new Contact(" ID123 ", " John ", " Doe ", "0123456789", " 123 Main ");

        assertEquals("ID123", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("123 Main", contact.getAddress());
    }

    @Test
    void contactId_nullBlankOrTooLong_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "John", "Doe", "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("   ", "John", "Doe", "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ABCDEFGHIJK", "John", "Doe", "0123456789", "123 Main"));
    }

    @Test
    void firstName_nullBlankOrTooLong_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", null, "Doe", "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "   ", "Doe", "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "Christopher", "Doe", "0123456789", "123 Main"));
    }

    @Test
    void lastName_nullBlankOrTooLong_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", null, "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "   ", "0123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "LongLastNam", "0123456789", "123 Main"));
    }

    @Test
    void phone_invalidValues_throwException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", null, "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "123456789", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "12345678901", "123 Main"));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "12345abcde", "123 Main"));
    }

    @Test
    void address_nullBlankOrTooLong_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "0123456789", null));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "0123456789", "   "));

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("ID1", "John", "Doe", "0123456789",
                        "1234567890123456789012345678901"));
    }

    @Test
    void setters_validInput_updatesFields() {
        Contact contact = new Contact("ID1", "John", "Doe", "0123456789", "123 Main");

        contact.setFirstName("Jane");
        contact.setLastName("Smith");
        contact.setPhone("1112223333");
        contact.setAddress("456 Oak Ave");

        assertEquals("Jane", contact.getFirstName());
        assertEquals("Smith", contact.getLastName());
        assertEquals("1112223333", contact.getPhone());
        assertEquals("456 Oak Ave", contact.getAddress());
    }

    @Test
    void setters_invalidInput_throwException() {
        Contact contact = new Contact("ID1", "John", "Doe", "0123456789", "123 Main");

        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("   "));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("badphone"));
        assertThrows(IllegalArgumentException.class,
                () -> contact.setAddress("1234567890123456789012345678901"));
    }

    @Test
    void contactId_isImmutable() {
        Contact contact = new Contact("LOCKED", "John", "Doe", "0123456789", "123 Main");

        assertEquals("LOCKED", contact.getContactId());
    }

    @Test
    void contactsWithSameId_areEqual() {
        Contact contactOne = new Contact("ID1", "John", "Doe", "0123456789", "123 Main");
        Contact contactTwo = new Contact("ID1", "Jane", "Smith", "1112223333", "456 Oak");

        assertEquals(contactOne, contactTwo);
        assertEquals(contactOne.hashCode(), contactTwo.hashCode());
    }
}