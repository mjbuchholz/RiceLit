package ricelit.ricelit;

import android.content.Context;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Crawl ListView
        ListView crawlListView = (ListView) findViewById(R.id.crawl_list_view);
        final ArrayList<String> arrayList = new ArrayList<String>();
        final CrawlAdapter adapter = new CrawlAdapter(this, arrayList);
        crawlListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(MainMenu.this);
                input.setHint("Crawl Name");
                AlertDialog alert = new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Create Crawl")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.text.Editable text = input.getText();
                                if (text.length() > 0
                                        && !text.toString().matches("\\s*")
                                        && text.length() < 50) {
                                    arrayList.add(0, input.getText().toString());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).create();

                LinearLayout layout = new LinearLayout(MainMenu.this);
                layout.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                layout.addView(input);
                alert.setView(layout);
                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    private static class CrawlAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<String> source;

        CrawlAdapter(Context context, ArrayList<String> source) {
            this.context = context;
            this.source = source;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return source.size();
        }

        @Override
        public Object getItem(int position) {
            return source.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.list_item_crawl, parent, false);
            ((TextView) view.findViewById(R.id.title)).setText(source.get(position));
            return view;
        }
    }
}
