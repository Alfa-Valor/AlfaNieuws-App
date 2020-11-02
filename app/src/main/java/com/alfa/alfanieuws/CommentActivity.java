package com.alfa.alfanieuws;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alfa.alfanieuws.Adapters.CommentAdapter;
import com.alfa.alfanieuws.Helpers.InputValidatorHelper;
import com.alfa.alfanieuws.Services.SqlLiteHelper;


import java.util.ArrayList;
import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

//TODO CODE COMMENTS

    String postId = "";
    SqlLiteHelper db;
    Context context = this;
    ListView listView;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        listView = (ListView) findViewById(R.id.listView);

        db = new SqlLiteHelper(context);

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

    public void fetchData() {
        Cursor res = db.getAllComments(postId);
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
            } while(res.moveToNext());
        }
        System.out.println(dataList);
        onPostProcess();
    }

    public void iconClicked(View view) {

        View dialogView = View.inflate(context, R.layout.comment_form, null);
        final EditText comment_name_box = dialogView.findViewById(R.id.comment_name_box);
        final EditText comment_text_box = dialogView.findViewById(R.id.comment_text_box);

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

                        InputValidatorHelper inputValidatorHelper = new InputValidatorHelper();
                        boolean allowSave = true;
                        String user_name = comment_name_box.getText().toString();
                        String comment = comment_text_box.getText().toString();

                        if (!inputValidatorHelper.isValidName(user_name) || inputValidatorHelper.isNullOrEmpty(user_name)) {
                            comment_name_box.setError("Een naam mag geen nummers bevatten, tenzij je Willem de 3e bent");
                            allowSave = false;
                        }

                        if (inputValidatorHelper.isNullOrEmpty(comment)) {
                            comment_text_box.setError("Een reactie zonder tekst.. Dat gaat zomaar niet.");
                            allowSave = false;
                        }

                        if (allowSave) {
                            //TODO API or JSON. For now SQLITE is used to add the comment.
                            //TODO ADD toast to let the user know the comment is placed..

                            db.addComment(user_name, postId, comment);
                            dialog.dismiss();
                            onPostProcess();

                        }
                    }
                });
            }
        });
        dialog.show();
    }

    public void onPostProcess() {
        CommentAdapter adapter = new CommentAdapter(context, listView, dataList);
        System.out.println("DATALIST ONPOST" + dataList);
        listView.setAdapter(adapter);
    }
}
