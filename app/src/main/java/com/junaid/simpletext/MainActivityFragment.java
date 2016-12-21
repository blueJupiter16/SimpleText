package com.junaid.simpletext;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends ListFragment {

    private ArrayList<Note> mNotes = new ArrayList<Note>();
    private NoteAdapter mNoteAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        /*Database Functions*/
        SimpleTextDbAdapter mDbAdapter = new SimpleTextDbAdapter(getActivity().getBaseContext());
        mDbAdapter.open();
        mNotes = mDbAdapter.getNotes();
        mDbAdapter.close();

        Log.d("Display Bug","insideOnActivtyCreated");

        mNoteAdapter = new NoteAdapter(getActivity(), mNotes);
        setListAdapter(mNoteAdapter);

         getListView().setDivider(null);
        //getListView().setDividerHeight(2);

        registerForContextMenu(getListView());

    }

    @Override
    public void onResume() {
        super.onResume();

        onActivityCreated(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchDetailActivity(position, MainActivity.fragmentLaunch.VIEW);
        Log.v("Position",new Integer(position).toString());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Note note = (Note)getListAdapter().getItem(info.position);
        switch (item.getItemId()){

            case R.id.menu_edit:
                launchDetailActivity(info.position, MainActivity.fragmentLaunch.EDIT);
                return true;
            case R.id.menu_delete:
                SimpleTextDbAdapter adapter = new SimpleTextDbAdapter(getActivity().getBaseContext());
                adapter.open();
                adapter.deleteNote(note.getID());
                mNotes.clear();
                mNotes.addAll(adapter.getNotes());
                mNoteAdapter.notifyDataSetChanged();
                adapter.close();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void launchDetailActivity(int position, MainActivity.fragmentLaunch f){

        Note note = (Note) getListAdapter().getItem(position);
        Intent i = new Intent(getActivity(), NoteDetailActivity.class);
        i.putExtra(MainActivity.NOTE_ID_EXTRA,note.getID());
        i.putExtra(MainActivity.NOTE_MESSAGE_EXTRA,note.getMessage());
        i.putExtra(MainActivity.NOTE_TITLE_EXTRA,note.getTitle());

        switch(f){
            case VIEW:
                i.putExtra(MainActivity.NOTE_FRAGMENT_DISPLAY_EXTA,MainActivity.fragmentLaunch.VIEW);
                break;
            case EDIT:
                i.putExtra(MainActivity.NOTE_FRAGMENT_DISPLAY_EXTA,MainActivity.fragmentLaunch.EDIT);
                break;

        }


        startActivity(i);
    }


}
