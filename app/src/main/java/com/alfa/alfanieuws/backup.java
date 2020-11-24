//package com.alfa.alfanieuws;
//
//import android.graphics.Bitmap;
//import android.widget.Toast;
//
//import com.alfa.alfanieuws.Helpers.DbBitmapUtility;
//import com.alfa.alfanieuws.Helpers.NewsLoaderHelper;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class backup {
//
//    // JSON
//
//    private void loadNewsFromJSON() {
//
//
//        //creating a string request to send request to the url
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Load the NewsLoaderHelper we are gonna use to get the news and add new news news_items
//                        NewsLoaderHelper newsLoaderHelper = new NewsLoaderHelper(context);
//
//                        try {
//                            //getting the whole json object from the response
//                            JSONObject obj = new JSONObject(response);
//
//                            //we have the array named tutorial inside the object
//                            //so here we are getting that json array
//                            JSONArray newsArray = obj.getJSONArray("heroes");
//
//                            //now looping through all the elements of the json array
//                            for (int i = 0; i < newsArray.length(); i++) {
//                                //getting the json object of the particular index inside the array
//                                JSONObject newsObject = newsArray.getJSONObject(i);
//
//                                //creating a tutorial object and giving them the values from json object
//                                //creating a hero object and giving them the values from json object
//                                Bitmap imageurl = getBitmapFromURL(newsObject.getString("imageurl"));
//                                DbBitmapUtility bitmapUtility = new DbBitmapUtility();
//                                byte[] photo = bitmapUtility.getBytes(imageurl);
//                                newsLoaderHelper.add_news_message(newsObject.getString("name"), "text", "2020", photo);
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occur
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        //creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        //adding the string request to request queue
//        requestQueue.add(stringRequest);
//    }
//}
