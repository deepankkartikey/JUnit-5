package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    @DisplayName("Before All Tests")
    public static void initialSetUp() {
        System.out.println("This is executed before running tests.");
    }

    @BeforeEach
    @DisplayName("Before Each Test")
    public void initEachTest() {
        contactManager = new ContactManager();
    }

    @AfterEach
    @DisplayName("After Each Test")
    public void destroy() {
        contactManager = null;
    }

    @AfterAll
    @DisplayName("After All Tests")
    public static void destroyAll() {
        System.out.println("After all tests");
    }

    @Test
    @DisplayName("Check if Contact added or Not")
    public void shouldCreateContact() {
        contactManager.addContact("Deepank", "Kartikey", "0851849428");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase("deepank")
                        && contact.getLastName().equalsIgnoreCase("kartikey")
                        && contact.getPhoneNumber().equalsIgnoreCase("0851849428"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Firstname is NULL")
    public void shouldThrowRuntimeExceptionWhenFirstnameIsNullonMac() {
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Kartikey", "0123465879");
        });
    }

    @Test
    @DisabledOnOs(value = OS.MAC, disabledReason = "This is a Mac Machine")
    @DisplayName("Firstname is NULL, check on MAC disabled")
    public void shouldThrowRuntimeExceptionWhenFirstnameIsNull() {
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Kartikey", "0123465879");
        });
    }

    @DisplayName("Firstname is NULL, check on DEV machine Repeating Tests")
    @RepeatedTest(value = 3, name = "Repeating Name is null test {currentRepetition} of {totalRepetition}")
    public void shouldThrowRuntimeExceptionWhenFirstnameIsNullonDEV() {
        Assumptions.assumeTrue("DEV".equalsIgnoreCase(System.getProperty("ENV")));
        assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Kartikey", "0123465879");
        });
    }

    @DisplayName("Check Phone number Validity using VALUE_SOURCE")
    @ParameterizedTest
    @ValueSource(strings = {"0851849428", "0994107099"})
    public void shouldValidatePhoneNumber(String phoneNumbers) {
        contactManager.addContact("John", "Doe", phoneNumbers);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Check Phone number Validity using METHOD_SOURCE")
    @ParameterizedTest
    @MethodSource(value = "getPhoneNumbersToValidate")
    public void shouldValidatePhoneNumberUsingMethodSource(String phoneNumbers) {
        contactManager.addContact("John", "Doe", phoneNumbers);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    public static List<String> getPhoneNumbersToValidate() {
        return Arrays.asList("0851849428", "0994107099", "0994107099");
    }

    @DisplayName("Check Phone number Validity using CSV_SOURCE")
    @ParameterizedTest
    @CsvSource ({"0851849428", "0994107099"})
    public void shouldValidatePhoneNumber() {
        contactManager.addContact("John", "Doe", "0994107099");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }
}