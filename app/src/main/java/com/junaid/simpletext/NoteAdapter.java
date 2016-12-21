package com.junaid.simpletext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Junaid on 14-12-2016.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private static class ViewHolder{  //hold contents of the view 
        TextView mTitle;
        TextView mMessage;
    }

    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context,0,notes);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get data item for this position
        Note note = getItem(position);

        ViewHolder viewHolder = null;

        //check if the view is already created, if not create it.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout,parent, false);

            //get hold of the contents of the view
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.ListItemNoteTitle);
            viewHolder.mMessage =  (TextView) convertView.findViewById(R.id.ListItemNoteBody);

            convertView.setTag(viewHolder);

        }
        else{

            viewHolder = (ViewHolder)convertView.getTag();
        }

        //change all the temporary values in the views
        viewHolder.mTitle.setText(note.getTitle());
        viewHolder.mMessage.setText(note.getMessage());

        return convertView;

    }
}
