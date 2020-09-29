package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public class EditContactActivity extends AppCompatActivity implements Observer {

    private ContactList contact_list = new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);
    private ContactController contact_controller;

    private Contact contact;
    private Context context;

    private EditText email;
    private EditText username;

    private boolean on_create_update = true;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        context = getApplicationContext();
        contact_list_controller.addObserver(this);
        contact_list_controller.loadContacts(context);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);

        contact = contact_list_controller.getContact(pos);

        contact_controller = new ContactController(contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        username.setText(contact.getUsername());
        email.setText(contact.getEmail());

        on_create_update = false;
    }

    public void saveContact(View view) {

        if (!validateInput()) {
            return;
        }

        String email_str = email.getText().toString();
        String username_str = username.getText().toString();

        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contact_list_controller.isUsernameAvailable(username_str) && !(contact.getUsername().equals(username_str))) {
            username.setError("Username already taken!");
            return;
        }

        String id = contact.getId(); // Reuse the contact id
        Contact updated_contact = new Contact(username_str, email_str, id);

        boolean success = contact_list_controller.editContact(contact, updated_contact, context);
        if (!success){
            return;
        }

        // End EditContactActivity
        contact_list_controller.removeObserver(this);
        finish();
    }

    public boolean validateInput() {
        // Input validation goes here...
        if (email.getText().toString().equals("")) {
            email.setError("Empty field!");
            return false;
        }

        if (!email.getText().toString().contains("@")){
            email.setError("Must be an email address!");
            return false;
        }

        if (username.getText().toString().equals("")) {
            username.setError("Empty field!");
            return false;
        }
        return true;
    }

    public void deleteContact(View view) {

        // Delete contact
        boolean success = contact_list_controller.deleteContact(contact, context);
        if (!success){
            return;
        }

        // End EditContactActivity
        contact_list_controller.removeObserver(this);
        finish();
    }

    @Override
    public void update() {
    }
}
