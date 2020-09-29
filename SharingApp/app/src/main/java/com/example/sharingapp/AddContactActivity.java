package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Add a new contact
 */
public class AddContactActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;

    private ContactList contact_list = new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        context = getApplicationContext();
        contact_list_controller.loadContacts(context);
    }

    public void saveContact(View view) {

        if (!validateInput()) {
            return;
        }

        String email_str = email.getText().toString();
        String username_str = username.getText().toString();

        if (!contact_list_controller.isUsernameAvailable(username_str)){
            username.setError("Username already taken!");
            return;
        }

        Contact contact = new Contact(username_str, email_str, null);

        boolean success = contact_list_controller.addContact(contact, context);
        if (!success){
            return;
        }

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
}
