package com.junaid.simpletext;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        createAndDisplayFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void createAndDisplayFragment(){

        Intent intent = getIntent();
        Fragment fragment = null;
        MainActivity.fragmentLaunch fragmenttolaunch = (MainActivity.fragmentLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_DISPLAY_EXTA);
        FragmentManager fm =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (fragmenttolaunch){
            case EDIT:
                fragment = new NoteEditFragment();
                fragmentTransaction.add(R.id.activity_note_detail,fragment,"NOTE_EDIT_FRAGMENT");
                break;
            case VIEW:
                fragment = new NoteViewFragment();
                fragmentTransaction.add(R.id.activity_note_detail,fragment,"NOTE_VIEW_FRAGMENT");
                break;
            case CREATE:
                fragment = new NoteEditFragment();
                fragmentTransaction.add(R.id.activity_note_detail,fragment,"NOTE_CREATE_FRAGMENT");
        }


        //fragmentTransaction.add(R.id.activity_note_detail,fragment,"NOTE_VIEW_FRAGMENT");
        fragmentTransaction.commit();
    }
}
