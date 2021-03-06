package ricelit.ricelit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CrawlPlanning extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawl_planning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Parsing Crawl constructing object
        String jsonString = getIntent().getStringExtra("crawlInstance");
        MainMenu.Crawl crawl =  (new Gson()).fromJson(jsonString, MainMenu.Crawl.class);

        setTitle(crawl.name);

        // Stop ListView
        ListView stopListView = (ListView) findViewById(R.id.stop_list_view);
        final ArrayList<Stop> arrayList = crawl.stopList;
        final StopAdapter adapter = new StopAdapter(this, arrayList);
        stopListView.setAdapter(adapter);

        stopListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                AlertDialog alert = new AlertDialog.Builder(CrawlPlanning.this)
                        .setTitle("Delete Stop")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        }).create();
                alert.show();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // text inputs for each member variable of Stop class
                final EditText location = new EditText(CrawlPlanning.this);
                final EditText host = new EditText(CrawlPlanning.this);
                final EditText sustenance = new EditText(CrawlPlanning.this);
                location.setHint("Location");
                host.setHint("Host Name");
                sustenance.setHint("Food and Drinks Served");

                // alert with text inputs
                AlertDialog alert = new AlertDialog.Builder(CrawlPlanning.this)
                        .setTitle("Create Stop")

                        // do nothing on Cancel
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        // add inputs if valid on Done
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // get text from all inputs
                                String locationText = location.getText().toString();
                                String hostText = host.getText().toString();
                                String sustenanceText = sustenance.getText().toString();

                                // add to arrayList if inputs are valid
                                if (Ext.isValid(locationText)
                                        && Ext.isValid(hostText)
                                        && Ext.isValid(sustenanceText)) {
                                    Stop stop = new Stop(locationText, hostText, sustenanceText);

                                    arrayList.add(stop);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).create();

                LinearLayout layout = new LinearLayout(CrawlPlanning.this);
                layout.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                layout.addView(location);
                layout.addView(host);
                layout.addView(sustenance);
                alert.setView(layout);
                alert.show();
            }
        });
    }

    public class Stop {
        private String location;
        private String host;
        private String sustenance;

        Stop(String location, String host, String sustenance) {
            this.location = location;
            this.host = host;
            this.sustenance = sustenance;
        }
    }

    private static class StopAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<Stop> source;

        StopAdapter(Context context, ArrayList<Stop> source) {
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
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_stop, parent, false);
            }
            Stop stop = source.get(position);
            ((TextView)view.findViewById(R.id.location)).setText(stop.location);
            ((TextView)view.findViewById(R.id.host)).setText(stop.host);
            ((TextView)view.findViewById(R.id.sustenance)).setText(stop.sustenance);
            return view;
        }
    }
}
