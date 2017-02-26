package com.bhailal.sony.task8;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {

    Context context;


    int img[] = {
            R.drawable.life,
            R.drawable.valentinespecial,
            R.drawable.love,
            R.drawable.friendship,
            R.drawable.positive,
            R.drawable.funny,
            R.drawable.motivation
    };


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

//      boolean temp = CheckInternetConnection(context);
//        if(temp)
//            Toast.makeText(context, "no Internet connection ", Toast.LENGTH_SHORT).show();
//
        new GridViewClass().execute("http://rapidans.esy.es/test/getallcat.php");
    }

//    public static final boolean CheckInternetConnection(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
//            return true;
//
//        } else {
//
//            return false;
//        }
//    }





    class GridViewClass extends AsyncTask<String,Void,String>{

        private Exception exception;


        private  ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FirstActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection;
            try {


                URL url = new URL(params[0]);//new URL("https://jsonplaceholder.typicode.com/posts");
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String bufferString = buffer.toString();
                    return bufferString;

                } catch (Exception e) {
                    this.exception = e;
                }

            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ArrayList<Post> postArrayList = new ArrayList<>();
//            ArrayList<categories> cats = new ArrayList<>();
            try {
                JSONObject obj1 = new JSONObject(s);
                JSONArray jsonarray = obj1.getJSONArray("data");
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject obj2 = jsonarray.getJSONObject(i);

                    int id = obj2.getInt("id");
                    String name = obj2.getString("name");

                    Post post = new Post();
                    post.setId(id);
                    post.setName(name);
                    postArrayList.add(post);
                }


            } catch (Exception e) {
                this.exception = e;
                Toast.makeText(FirstActivity.this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
            }

            CustomGridAdapter adapter1 = new CustomGridAdapter(FirstActivity.this, postArrayList,img);
            GridView gridview = (GridView)findViewById(R.id.gridview);
            gridview.setAdapter(adapter1);




        }
    }

}
