package com.mauriciosilva.idbrss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private Button btnParse;
    private String link = "http://www.idownloadblog.com/feed/";
    private String xmlContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnParse = (Button) findViewById(R.id.btnParse);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        new DownloadData().execute(link);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseNews parseNews = new ParseNews(xmlContent);
                parseNews.process();
                ArrayAdapter<News> adapter = new ArrayAdapter<News>(
                        MainActivity.this, R.layout.list_item, parseNews.getNews()
                );
                list.setAdapter(adapter);

            }
        });
        list = (ListView) findViewById(R.id.data);



    }

    private class DownloadData extends AsyncTask<String, Void, String>{
        String content;

        @Override
        protected String doInBackground(String... params) {
            if(!link.equals("")){

                xmlContent = generateContents();

            } else {

                Toast.makeText(MainActivity.this, "Invalid string", Toast.LENGTH_SHORT).show();

            }

            return content;
        }

        private String generateContents() {
            StringBuilder mFileContents = new StringBuilder();
            try {

                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.d("DownloadData", "Response code was " + connection.getResponseCode());
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while(true) {

                    charRead = isr.read(inputBuffer);
                    if(charRead <= 0) {
                        break;
                    }
                    mFileContents.append(String.copyValueOf(inputBuffer, 0, charRead));

                }

            } catch (IOException e) {
                Log.d("DownloadData", "Error while conencting" + e.getMessage());
            } catch (SecurityException e) {
                Log.d("DownloadData", e.getMessage());
            }
            return mFileContents.toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
