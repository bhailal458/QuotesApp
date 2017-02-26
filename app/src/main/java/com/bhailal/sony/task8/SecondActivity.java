package com.bhailal.sony.task8;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private ListView listView;
    private Exception exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int quotePosition = getIntent().getIntExtra("myKey", -1);
        new ListViewClass().execute("http://rapidans.esy.es/test/getquotes.php?cat_id=" + quotePosition);





    }
    class ListViewClass extends AsyncTask<String,Void,String>{
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SecondActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                URL url = new URL(params[0]);//new URL("http://rapidans.esy.es/test/getquotes.php?cat_id=1");
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

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            ArrayList<PostQuotesList> QArrayList = new ArrayList<>();
//            ArrayList<categories> cats = new ArrayList<>();
            try {
                JSONObject obj1 = new JSONObject(s);
                JSONArray jsonobj = obj1.getJSONArray("data");
                for (int i = 0; i < jsonobj.length(); i++) {
                    JSONObject obj2 = jsonobj.getJSONObject(i);

                    int id = obj2.getInt("id");
                    int catid = obj2.getInt("cat_id");
                    String qname = obj2.getString("quotes");

                    PostQuotesList p = new PostQuotesList();
                    p.setId(id);
                    p.setCat_id(catid);
                    p.setQuotes(qname);
                    QArrayList.add(p);
                }


            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            CustomListAdapter listadapter = new CustomListAdapter(SecondActivity.this, QArrayList);
            ListView listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(listadapter);

        }

    }
}
