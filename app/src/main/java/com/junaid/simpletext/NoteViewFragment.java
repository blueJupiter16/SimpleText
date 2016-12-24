package com.junaid.simpletext;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_note_view,container,false);

        final TextView mTitle = (TextView) fragmentView.findViewById(R.id.noteTitle);
        final TextView mBody = (TextView) fragmentView.findViewById(R.id.noteBody);

        Intent intent = getActivity().getIntent();

        //Log.v("message",intent.getStringExtra(MainActivity.NOTE_MESSAGE_EXTRA));
        mTitle.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA));
        mBody.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA));

        setHasOptionsMenu(true); //Menu items

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_view_fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.note_edit_edit:
                Fragment fragment = null;
                MainActivity.fragmentLaunch fragmenttolaunch = MainActivity.fragmentLaunch.EDIT;
                FragmentManager fm =  getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragment = new NoteEditFragment();
                fragmentTransaction.replace(R.id.activity_note_detail,fragment,"NOTE_EDIT_FRAGMENT");
                fragmentTransaction.commit();
                return true;

        }
        return super.onOptionsItemSelected(item);


    }
}
