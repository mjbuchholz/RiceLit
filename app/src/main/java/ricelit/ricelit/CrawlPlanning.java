package ricelit.ricelit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrawlPlanning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawl_planning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Stop ListView
        ListView stopListView = (ListView) findViewById(R.id.stop_list_view);
        final ArrayList<Stop> arrayList = new ArrayList<Stop>();
        final StopAdapter adapter = new StopAdapter(this, arrayList);
        stopListView.setAdapter(adapter);

        stopListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(MainMenu.this);
                //crawlListView.removeViewAt(pos);
                AlertDialog alert = new AlertDialog.Builder(CrawlPlanning.this)
                        .setTitle("Delete Stop")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class Stop {
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
            ((TextView)view.findViewById(R.id.title)).setText(source.get(position).location);
            return view;
        }
    }

    private static boolean isValid(android.text.Editable text) {
        if (text.length() > 0 && !text.toString().matches("\\s*") && text.length() < 50) {
            return true;
        }
        return true;
    }
}
