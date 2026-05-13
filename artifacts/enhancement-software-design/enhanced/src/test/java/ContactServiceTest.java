import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {
        private ContactService service;

        @BeforeEach
        void setUp() {
                service = new ContactService();
        }

        @Test
        void addContact_validFields_addsContact() {
                Contact contact = service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                assertNotNull(contact);
                assertEquals("C1", service.getContact("C1").getContactId());
                assertEquals(1, service.getContactCount());
        }

        @Test
        void addContact_validObject_addsContact() {
                Contact contact = new Contact("C1", "John", "Doe", "0123456789", "123 Main");

                service.addContact(contact);

                assertEquals(contact, service.getContact("C1"));
                assertEquals(1, service.getContactCount());
        }

        @Test
        void addContact_nullObject_throwsException() {
                assertThrows(IllegalArgumentException.class, () -> service.addContact((Contact) null));
        }

        @Test
        void addContact_duplicateId_throwsException() {
                service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                assertThrows(IllegalArgumentException.class,
                                () -> service.addContact("C1", "Jane", "Smith", "1112223333", "456 Oak"));
        }

        @Test
        void deleteContact_existingContact_returnsTrueAndRemovesContact() {
                service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                assertTrue(service.deleteContact("C1"));
                assertNull(service.getContact("C1"));
                assertEquals(0, service.getContactCount());
        }

        @Test
        void deleteContact_missingContact_returnsFalse() {
                assertFalse(service.deleteContact("NOPE"));
        }

        @Test
        void deleteContact_nullOrBlankId_throwsException() {
                assertThrows(IllegalArgumentException.class, () -> service.deleteContact(null));
                assertThrows(IllegalArgumentException.class, () -> service.deleteContact("   "));
        }

        @Test
        void updateContactFields_existingContact_updatesCorrectly() {
                service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                service.updateFirstName("C1", "Jane");
                service.updateLastName("C1", "Smith");
                service.updatePhone("C1", "1112223333");
                service.updateAddress("C1", "456 Oak");

                Contact contact = service.getContact("C1");

                assertEquals("Jane", contact.getFirstName());
                assertEquals("Smith", contact.getLastName());
                assertEquals("1112223333", contact.getPhone());
                assertEquals("456 Oak", contact.getAddress());
        }

        @Test
        void updateContactFields_missingContact_throwsException() {
                assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("NOPE", "Jane"));
                assertThrows(IllegalArgumentException.class, () -> service.updateLastName("NOPE", "Smith"));
                assertThrows(IllegalArgumentException.class, () -> service.updatePhone("NOPE", "1112223333"));
                assertThrows(IllegalArgumentException.class, () -> service.updateAddress("NOPE", "456 Oak"));
        }

        @Test
        void updateContactFields_invalidValues_throwsException() {
                service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("C1", "   "));
                assertThrows(IllegalArgumentException.class, () -> service.updateLastName("C1", "LongLastNam"));
                assertThrows(IllegalArgumentException.class, () -> service.updatePhone("C1", "badphone"));
                assertThrows(IllegalArgumentException.class,
                                () -> service.updateAddress("C1", "1234567890123456789012345678901"));
        }

        @Test
        void getContact_nullOrBlankId_returnsNull() {
                assertNull(service.getContact(null));
                assertNull(service.getContact("   "));
        }

        @Test
        void getAllContacts_returnsReadOnlyCopy() {
                service.addContact("C1", "John", "Doe", "0123456789", "123 Main");

                Map<String, Contact> contacts = service.getAllContacts();

                assertEquals(1, contacts.size());
                assertThrows(UnsupportedOperationException.class, () -> contacts.clear());
                assertEquals(1, service.getContactCount());
        }
}