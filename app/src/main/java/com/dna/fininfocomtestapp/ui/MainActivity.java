package com.dna.fininfocomtestapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Patterns;

import com.dna.fininfocomtestapp.adapter.DetailsAdapter;
import com.dna.fininfocomtestapp.databinding.ActivityMainBinding;
import com.dna.fininfocomtestapp.model.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Contact> contacts = new ArrayList<>();
    DetailsAdapter detailsAdapter;
    static final String PREF_CONTACT_LIST = "contact_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getContactsFromSharedPreferences();
        setupRecyclerView();

        binding.btSave.setOnClickListener(v -> {
            if(isValid())
                saveContact();
        });
    }

    private void setupRecyclerView() {
        // Setting up the Recyclerview
        detailsAdapter = new DetailsAdapter(contacts);
        binding.rvContacts.setAdapter(detailsAdapter);
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        TransitionManager.beginDelayedTransition(binding.getRoot());
        detailsAdapter.notifyDataSetChanged();
    }

    private void getContactsFromSharedPreferences() {
        // Getting contacts from Shared Preferences as json then converting into list using Gson.
        try{
            String serializedObject = PreferenceManager.getDefaultSharedPreferences(this).getString(PREF_CONTACT_LIST, "");
            if (serializedObject != null) {
                Gson gson = new Gson();
                Type type = TypeToken.getParameterized(ArrayList.class, Contact.class).getType();
                contacts = gson.fromJson(serializedObject, type);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // If Gson returns null
        if(contacts == null)
            contacts = new ArrayList<>();

    }

    private void saveContact() {
        if (binding.etEmail.getText()!=null && binding.etNumber.getText()!=null) {
            contacts.add(new Contact(binding.etEmail.getText().toString().trim(),
                    binding.etNumber.getText().toString().trim()));

            // Simple Animation using Constraint Layout
            TransitionManager.beginDelayedTransition(binding.getRoot());

            // Refreshing the List
            detailsAdapter.notifyDataSetChanged();

            // Updating the List in Shared Preferences as json
            Gson gson = new Gson();
            String json = gson.toJson(contacts);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString(PREF_CONTACT_LIST, json);
            editor.apply();
        }
    }

    private boolean isValid() {
        TransitionManager.beginDelayedTransition(binding.getRoot());
        // Validation
        if (binding.etEmail.getText() == null || TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            binding.emailLayout.setError("Please enter a valid email address");
            return false;
        } else if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches())) {
            binding.emailLayout.setError("Please enter a valid email address");
            return false;
        } else {
            binding.emailLayout.setErrorEnabled(false);
        }

        // Can be implemented with location based number format validation using a 3rd Party Library.
        // For now, using a simple default validation.
        if (binding.etNumber.getText() == null || TextUtils.isEmpty(binding.etNumber.getText().toString().trim())) {
            binding.numberLayout.setError("Please enter a valid phone number");
            return false;
        } else if (!(Patterns.PHONE.matcher(binding.etNumber.getText().toString()).matches())) {
            binding.numberLayout.setError("Please enter a valid Phone Number");
            return false;
        } else {
            binding.numberLayout.setErrorEnabled(false);
        }
        return true;
    }
}