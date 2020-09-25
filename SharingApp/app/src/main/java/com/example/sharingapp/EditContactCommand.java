package com.example.sharingapp;

import android.content.Context;

public class EditContactCommand extends Command {

    private ContactList contactList;
    private Contact old_Contact;
    private Contact new_Contact;
    private Context context;

    public EditContactCommand(ContactList contactList, Contact old_Contact, Contact new_Contact, Context context) {
        this.contactList = contactList;
        this.old_Contact = old_Contact;
        this.new_Contact = new_Contact;
        this.context = context;
    }

    @Override
    public void execute() {
        contactList.deleteContact(old_Contact);
        contactList.addContact(new_Contact);
        setIsExecuted(contactList.saveContacts(context));
    }
}
