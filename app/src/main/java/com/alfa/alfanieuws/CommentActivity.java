package com.alfa.alfanieuws;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alfa.alfanieuws.Adapters.CommentAdapter;
import com.alfa.alfanieuws.Helpers.CommentLoaderHelper;
import com.alfa.alfanieuws.Helpers.InputValidatorHelper;
import com.alfa.alfanieuws.Services.SqlLiteHelper;


import java.util.ArrayList;
import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    // We initialize the elements used in this activity here
    String postId = "";
    CommentLoaderHelper clh;
    Context context = this;
    ListView listView;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();


    // we set all the elements to use later here. And check for an intent called post_id.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listView = (ListView) findViewById(R.id.listView);
        clh = new CommentLoaderHelper(context);

        Intent intent = getIntent();
        if (intent.hasExtra("post_id")) {
            postId = intent.getStringExtra("post_id");
            fetchData();
        } else {
            finish();
            Toast.makeText(context, "It's weird that u are here..", Toast.LENGTH_LONG).show();
        }

    }
    //TODO fetch comments from the API? To fill the comments page with

    // in fetchData() we will get all the comments and put them in the dataList

    public void fetchData() {
        Cursor res = clh.getAllComments(postId);
        if (res == null) {
            return;
        }
        if (res.getCount() == 0) {
            Toast.makeText(context, "No comments found", Toast.LENGTH_LONG).show();
            return;
        }
        if (res.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", res.getString(res.getColumnIndex("name")));
                map.put("text", res.getString(res.getColumnIndex("text")));
                dataList.add(map);
            } while (res.moveToNext());
        }
        System.out.println(dataList);
        onPostProcess();
    }

    // iconClicked()
    // holds the dialog, layout comment_form
    public void iconClicked(View view) {

        View dialogView = View.inflate(context, R.layout.comment_form, null);
        final EditText comment_name_box = dialogView.findViewById(R.id.comment_name_box);
        final EditText comment_text_box = dialogView.findViewById(R.id.comment_text_box);
        comment_text_box.setFilters(new InputFilter[]{new InputFilter.LengthFilter(250)});

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Reactie plaatsen")
                .setNegativeButton("Annuleer", null)
                .setPositiveButton("Plaatsen", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // when we push the positive button we will check the data filled by the user with the InputValidatorHelper();
                        InputValidatorHelper inputValidatorHelper = new InputValidatorHelper();
                        boolean allowSave = true;
                        String user_name = comment_name_box.getText().toString();
                        String comment = comment_text_box.getText().toString();

                        // A name can't contain any numbers and may not be empty.
                        if (!inputValidatorHelper.isValidName(user_name) || inputValidatorHelper.isNullOrEmpty(user_name)) {
                            comment_name_box.setError("Uw naam is leeg of onjuist..");
                            allowSave = false;
                        }

                        // A comment without text isn't a comment right ...
                        if (inputValidatorHelper.isNullOrEmpty(comment)) {
                            comment_text_box.setError("U bent uw reactie vergeten..");
                            allowSave = false;
                        }

                        // if we allow to save the comment will be added to the database. We will hide the dialog and "refresh" the activity to add the comment directly cool ha?
                        if (allowSave) {
                            //TODO API or JSON. For now SQLITE is used to add the comment.
                            //TODO ADD toast to let the user know the comment is placed..
                            clh.addComment(user_name, postId, comment);
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    // Here is where the magic happens, we push the data to the adapter in order to get it on the screen.
    public void onPostProcess() {
        CommentAdapter adapter = new CommentAdapter(context, dataList);
        listView.setAdapter(adapter);
    }
}
