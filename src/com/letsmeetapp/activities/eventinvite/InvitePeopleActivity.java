package com.letsmeetapp.activities.eventinvite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.letsmeetapp.R;
import com.letsmeetapp.model.Day;

import java.util.ArrayList;

/**
 *  It represents the view for adding new emails of people that are to be invited...
 */
public class InvitePeopleActivity extends Activity{

    private EditText newEmailInput;
    private ArrayList<String> emails = new ArrayList<String>();
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the default view (holds header with buttons, grid...)
        setContentView(R.layout.invite_ppl_activity);

        //Assign emails sent from the previous activity
        emails = (ArrayList<String>)getIntent().getExtras().get("emails");

        newEmailInput   = (EditText)findViewById(R.id.new_email);
        doneButton      = (Button)findViewById(R.id.done_adding_emails);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newEmailInput.getText().toString().length() > 0){
                    emails.add(newEmailInput.getText().toString());
                }


                Intent returnIntent = new Intent();
                returnIntent.putExtra("emails", InvitePeopleActivity.this.emails);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });



    }
}
