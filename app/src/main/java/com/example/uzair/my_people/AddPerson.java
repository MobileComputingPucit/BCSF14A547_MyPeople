package com.example.uzair.my_people;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPerson extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText gender;
    EditText id;

    DBHelper helper;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        helper = new DBHelper(this);

        id = (EditText) findViewById(R.id.id);
        id.setVisibility(View.VISIBLE);

        name = (EditText) findViewById(R.id.user_name);

        gender = (EditText) findViewById(R.id.gender);

        phone = (EditText) findViewById(R.id.user_phone);

        if(getIntent().getBooleanExtra("edit",false)){
            person = (Person) getIntent().getSerializableExtra("person");

            id.setVisibility(View.GONE);
            name.setText(person.name);
            gender.setText(person.gender);
            phone.setText(person.phone);
        }


        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Person p = new Person();
                p.name = name.getEditableText().toString();
                p.gender = gender.getEditableText().toString();
                p.phone = phone.getEditableText().toString();

                if(getIntent().getBooleanExtra("edit",false)){
                    p.id = person.id;
                    if(p.name.trim().length() == 0 || p.gender.trim().length() == 0 || p.phone.trim().length() == 0){
                        Toast.makeText(AddPerson.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        helper.updatePerson(p);
                    }

                }else {
                    if(p.name.trim().length() == 0 || p.gender.trim().length() == 0 || p.phone.trim().length() == 0){
                        Toast.makeText(AddPerson.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        helper.insertPerson(p);
                    }
                }

                finish();
            }
        });
    }
}
