package amazon.sample.aws;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    TextView t;
    DateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      t=(TextView)findViewById(R.id.result);
        dateFormat=new SimpleDateFormat("h:mm:ss");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addAsong();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    void addAsong() throws Exception {
        t.setText("Started at : "+ dateFormat.format(new Date()));
        new selectBuilder(MainActivity.this)
                .Select("")
                .From("Categories")
                .build(new TextHttpResponseHandler() {

                    @Override
                    public void onRetry(int retryNo) {
                        super.onRetry(retryNo);
                        t.append("\n" + "Couldn't Connect Retry number " + retryNo + " : " + dateFormat.format(new Date()));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseBytes, throwable);
                        t.append("\n"+"Error Response at "+statusCode+" : " + dateFormat.format(new Date()));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("mysql", "server is currenetly down");
                        t.append("\n" + "Error Response at : " + dateFormat.format(new Date()));

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d("mysql", responseString);
                        t.append("\n"+"Response at : " + dateFormat.format(new Date()));
                        t.append("\n" + responseString);
                    }
                });

    }


    void test(){

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
