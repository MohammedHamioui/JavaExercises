package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class ContactInfo extends AppCompatActivity {

    TextView tvChar, tvName;
    ImageView ivCall, ivMail, ivEdit, ivDelete;
    EditText etName, etMail, etTel;
    Button btnSubmit;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        tvChar = findViewById(R.id.tvchar);
        tvName = findViewById(R.id.tvname);

        ivCall = findViewById(R.id.ivCall);
        ivMail = findViewById(R.id.ivMail);
        ivEdit = findViewById(R.id.ivEdit);
        ivDelete = findViewById(R.id.ivDelete);

        etName = findViewById(R.id.etnameInfo);
        etMail = findViewById(R.id.etmailInfo);
        etTel = findViewById(R.id.etphoneInfo);

        btnSubmit = findViewById(R.id.btnSubmit);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etName.setVisibility(View.GONE);
        etMail.setVisibility(View.GONE);
        etTel.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);

        final int index = getIntent().getIntExtra("index", 0);

        etName.setText(ApplicationClass.contacts.get(index).getName());
        etMail.setText(ApplicationClass.contacts.get(index).getEmail());
        etTel.setText(ApplicationClass.contacts.get(index).getNumber());

        tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0)+"");
        tvName.setText(ApplicationClass.contacts.get(index).getName());

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "tel:" + ApplicationClass.contacts.get(index).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { ApplicationClass.contacts.get(index).getEmail() });
                startActivity(Intent.createChooser(intent, "Send Email to " + ApplicationClass.contacts.get(index).getName()));
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit = !edit;

                if (edit) {
                    etName.setVisibility(View.VISIBLE);
                    etMail.setVisibility(View.VISIBLE);
                    etTel.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                } else {
                    etName.setVisibility(View.GONE);
                    etMail.setVisibility(View.GONE);
                    etTel.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                }
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the contact to delete (assuming you have the contact object or its ID)
                Contact contact = ApplicationClass.contacts.get(index); // Replace with your logic to get the contact
                String objectId = contact.getObjectId(); // Assuming Contact class has a method to get the objectId

                // Show a confirmation dialog before deleting
                new AlertDialog.Builder(ContactInfo.this)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete this contact?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the contact from Backendless
                                Backendless.Persistence.of(Contact.class).remove(ApplicationClass.contacts.get(index), new AsyncCallback<Long>() {
                                    @Override
                                    public void handleResponse(Long response) {
                                        // Contact deleted successfully
                                        Toast.makeText(ContactInfo.this, "Contact deleted successfully!", Toast.LENGTH_SHORT).show();

                                        // Optionally, update the UI or navigate back
                                        ApplicationClass.contacts.remove(index); // Remove the contact from the local list
                                        finish(); // Close the current activity
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        // Handle the error
                                        Toast.makeText(ContactInfo.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null) // Do nothing if "No" is clicked
                        .show();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty() ||
                        etMail.getText().toString().isEmpty() ||
                        etTel.getText().toString().isEmpty()) {
                    Toast.makeText(ContactInfo.this, "Please enter all details!", Toast.LENGTH_SHORT).show();
                } else {
                    ApplicationClass.contacts.get(index).setName(etName.getText().toString().trim());
                    ApplicationClass.contacts.get(index).setNumber(etTel.getText().toString().trim());
                    ApplicationClass.contacts.get(index).setEmail(etMail.getText().toString().trim());

                    showProgress(true);
                    tvLoad.setText("Updating contact...please wait...");

                    Backendless.Persistence.save(ApplicationClass.contacts.get(index), new AsyncCallback<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            tvChar.setText(ApplicationClass.contacts.get(index).getName().toUpperCase().charAt(0) + "");
                            tvName.setText(ApplicationClass.contacts.get(index).getName());
                            Toast.makeText(ContactInfo.this, "Contact successfully updated!", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(ContactInfo.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



}