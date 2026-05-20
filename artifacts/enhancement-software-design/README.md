# Enhancement 1: Software Design and Engineering (CS-320)

## Overview
This enhancement focuses on improving a Contact Service application originally developed in CS-320. The application manages contact records, including creating, updating, retrieving, and deleting contacts.

The enhanced version improves validation, structure, security, and overall reliability while maintaining the original functionality.

---

## Folder Structure

enhancement-software-design/
│
├── enhanced/
│ ├── src/
│ │ ├── main/java/
│ │ │ ├── Contact.java
│ │ │ └── ContactService.java
│ │ └── test/java/
│ │ ├── ContactTest.java
│ │ └── ContactServiceTest.java
│ ├── pom.xml
│ └── README.md
│
├── original/
│ ├── Contact.java
│ ├── ContactService.java
│ ├── ContactTest.java
│ ├── ContactServiceTest.java
│ └── README.md
│
├── screenshots/
│ ├── build_success.png
│ ├── tests_running.png
│ ├── validation_code_1.png
│ ├── validation_code_2.png
│ ├── validation_code_3.png
│ └── validation_code_4.png
│
└── Software Design and Engineering Enhancement.docx

---

## Original Version
The original version provides basic functionality for managing contacts.  
However, it has:
- Limited input validation
- Minimal error handling
- Basic structure
- Initial unit testing

This version is included for comparison purposes.

---

## Enhancements Implemented

### 1. Input Validation
- All fields validated for null/blank values
- Length constraints enforced
- Phone number must be exactly 10 digits

### 2. Data Integrity
- Duplicate contact IDs are prevented
- Contact ID is immutable after creation

### 3. Error Handling
- Clear and consistent `IllegalArgumentException` messages
- Safe handling of invalid operations

### 4. Separation of Concerns
- `Contact` class → handles data and validation
- `ContactService` class → handles business logic

### 5. Data Protection
- `getAllContacts()` returns an unmodifiable copy
- Prevents external modification of internal data

### 6. Performance Improvement
- Uses `HashMap<String, Contact>` for efficient lookup (O(1))

### 7. Unit Testing
- Expanded JUnit test coverage
- Includes:
  - Valid inputs
  - Invalid inputs
  - Edge cases

---

## Running the Project

To run tests:

```bash
mvn test
