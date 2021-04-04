package com.programming.techie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactManagerTest {

    @Test
    public void shouldCreateContact() {
        ContactManager contactManager = new ContactManager();
        contactManager.addContact("Deepank", "Kartikey", "0851849428");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

}