package com.junaid.simpletext;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private EditText mTitle,mBody;
   // private Button mSave;
    private long noteID;
    private String fragmentPurpose;
    private boolean saveButtonPressed = false;



    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View fragmentView = inflater.inflate(R.layout.fragment_note_edit,container,false);

        mTitle = (EditText) fragmentView.findViewById(R.id.note_edit_title_ID);
        mBody = (EditText) fragmentView.findViewById(R.id.note_edit_body_id);
      // mSave = (Button) fragmentView.findViewById(R.id.save_button);

        if(this.getTag() == "NOTE_EDIT_FRAGMENT"){
            fragmentPurpose = "UPDATE";
        }
        else
          fragmentPurpose = "NEW";



        setHasOptionsMenu(true);

        Intent intent = getActivity().getIntent();
        noteID = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA);
        mTitle.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA,""));
        mBody.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA,""));


       /* mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButtonPressed = true;
                updateOrInsert(fragmentPurpose);



                Log.d("BUG1","Before starting new activity");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            }
        });*/



        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_edit_fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_menu_save:

                //handle the click

                saveButtonPressed = true;
                updateOrInsert(fragmentPurpose);



                Log.d("BUG1","Before starting new activity");
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();



            if (saveButtonPressed == false) {
               // Toast.makeText(getActivity(),"This is from onPause()",Toast.LENGTH_SHORT).show();
                updateOrInsert(fragmentPurpose);
            }

    }

    void updateOrInsert(String s){


        Log.d("NullNoteBug","mTitle = " + mTitle.getText().toString());
        Log.d("NullNoteBug","mBody = " + mBody.getText().toString());
        if(mBody.getText().toString().equals("") && mTitle.getText().toString().equals(""))
            return;

        SimpleTextDbAdapter adapter = new SimpleTextDbAdapter(getActivity().getBaseContext());
        adapter.open();
        if(s == "NEW")
            adapter.insertNote(mTitle.getText().toString(),mBody.getText().toString());
        else
            adapter.updateNote(noteID,mTitle.getText().toString(),mBody.getText().toString());
        adapter.close();

        Toast.makeText(getActivity(),"Note Saved",Toast.LENGTH_SHORT).show();



    }









}
