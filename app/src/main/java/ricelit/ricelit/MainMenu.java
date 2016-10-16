package ricelit.ricelit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.LinearLayout;
import com.google.gson.Gson;



public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Title
        setTitle("Your Crawls");

        // Crawl List View
        final ListView crawlListView = (ListView) findViewById(R.id.crawl_list_view);
        final ArrayList<Crawl> arrayList = new ArrayList<Crawl>();
        final CrawlAdapter adapter = new CrawlAdapter(this, arrayList);
        crawlListView.setAdapter(adapter);

        // Short click to go to crawl planning activity
        crawlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int pos, long id) {
                Crawl crawl = arrayList.get(pos);

                // create intent from crawl and start activity
                startActivity(Ext.crawlToIntent(crawl, getApplicationContext()));
            }
        });

        // FAB to add Crawl
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // text input for name variable of Crawl class
                final EditText input = new EditText(MainMenu.this);
                input.setHint("Crawl Name");

                // alert with text input
                AlertDialog alert = new AlertDialog.Builder(MainMenu.this)
                        .setTitle("Create Crawl")

                        // do nothing on Cancel
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        // add crawl to arrayList and start CrawlPlanning activity on Done
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = input.getText().toString();
                                if (name.length() > 0
                                        && !name.matches("\\s*")
                                        && name.length() < 50) {
                                    Crawl crawl = new Crawl(name);
                                    arrayList.add(0, crawl);
                                    adapter.notifyDataSetChanged();

                                    startActivity(Ext.crawlToIntent(
                                            crawl,
                                            getApplicationContext())
                                    );
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

        // Long click to delete crawl
        crawlListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                AlertDialog alert = new AlertDialog.Builder(MainMenu.this)
                    .setTitle("Delete Crawl")

                    // do nothing on Cancel
                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                        }
                    })

                    // delete Crawl on Delete
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

    public class Crawl {
        String name;
        ArrayList<CrawlPlanning.Stop> stopList;
        ArrayList contacts;

        Crawl(String name) {
            this.name = name;
            stopList = new ArrayList<CrawlPlanning.Stop>();
            contacts = new ArrayList();
        }

        Crawl(String name, ArrayList<CrawlPlanning.Stop> stopList) {
            this.name = name;
            this.stopList = stopList;
            contacts = new ArrayList();
        }

        Crawl(String name, ArrayList<CrawlPlanning.Stop> stopList, ArrayList contacts) {
            this.name = name;
            this.stopList = stopList;
            this.contacts = contacts;
        }

    }

    private static class CrawlAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<Crawl> source;

        CrawlAdapter(Context context, ArrayList<Crawl> source) {
            this.context = context;
            this.source = source;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() { return source.size(); }

        @Override
        public Object getItem(int position) { return source.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_item_crawl, parent, false);
            }
            ((TextView)view.findViewById(R.id.title)).setText(source.get(position).name);
            return view;
        }
    }
}
