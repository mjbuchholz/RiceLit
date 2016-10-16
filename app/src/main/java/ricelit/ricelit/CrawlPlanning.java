package ricelit.ricelit;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ListView stopListview = (ListView) findViewById(R.id.stop_list_view);
        final ArrayList<Stop> arrayList = new ArrayList<Stop>();

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
        private ArrayList<String> source;

        StopAdapter(Context context, ArrayList<String> source) {
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
