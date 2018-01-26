package com.example.uzair.my_people;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView people;
    PeopleAdapter adapter;

    List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personList = new DBHelper(this).getAllPersons();

        people = (RecyclerView) findViewById(R.id.people);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        people.setLayoutManager(manager);

        adapter = new PeopleAdapter(this,personList);

        people.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add){
            startActivity(new Intent(MainActivity.this,AddPerson.class));
        }
        else if(item.getItemId() == R.id.refresh){
            personList.clear();
            personList.addAll(new DBHelper(MainActivity.this).getAllPersons());
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
