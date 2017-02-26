package com.bhailal.sony.task8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        Intent intent =getIntent();
        String quote = intent.getStringExtra("myPosition");

        TextView t = (TextView)findViewById(R.id.shareText);
        t.setText(quote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();
        String quote = intent.getStringExtra("myPosition");

        switch (item.getItemId()){
            case R.id.share:

                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,quote);
                startActivity(Intent.createChooser(i,"SELECT"));

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
