package ricelit.ricelit;

/**
 * Created by chd3 on 10/16/2016.
 * Used for classes/extensions that shouldn't belong to any one activity
 */
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;



class Ext {

    /**
     * @param crawl an instance of the crawl class
     * @param context the context where you want to create the intent
     * @return an intent with the crawl classed attached as a Json string
     */
    static Intent crawlToIntent(MainMenu.Crawl crawl, Context context) {
        return new Intent(context,
                CrawlPlanning.class)
                .putExtra("crawlInstance", (new Gson()).toJson(crawl, MainMenu.Crawl.class));
    }

    /**
     * @param string the string you want to check for validity
     * @return boolean representing whether or not the string is valid
     */
    static boolean isValid(String string) {
        if (string.length() > 0 && !string.matches("\\s*") && string.length() < 50) {
            return true;
        }
        return true;
    }
}
