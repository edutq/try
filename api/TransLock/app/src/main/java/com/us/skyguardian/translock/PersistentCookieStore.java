package com.us.skyguardian.translock;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class PersistentCookieStore implements CookieStore {

    private final static String PREF_DEFAULT_STRING = "";

    private final static String PREFS_NAME = "USERPREFERENCES";

    private final static String PREF_SESSION_COOKIE = "cookie";

    private CookieStore mStore;
    private Context mContext;

    public PersistentCookieStore(Context context) {
        // prevent context leaking by getting the application context
        mContext = context.getApplicationContext();

        // get the default in memory store and if there is a cookie stored in shared preferences,
        // we added it to the cookie store
        mStore = new CookieManager().getCookieStore();
        String jsonSessionCookie = getJsonSessionCookieString();
        if (!jsonSessionCookie.equals(PREF_DEFAULT_STRING)) {
            Gson gson = new Gson();
            HttpCookie cookie = gson.fromJson(jsonSessionCookie, HttpCookie.class);

            mStore.add(URI.create(cookie.getDomain()), cookie);
        }
    }

    private String getJsonSessionCookieString() {
        return getPrefs().getString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void saveSessionCookie(HttpCookie cookie) {
        Gson gson = new Gson();

        String jsonSessionCookieString = gson.toJson(cookie);
        SharedPreferences.Editor editor = getPrefs().edit();
        //Toast.makeText(mContext, "hola", Toast.LENGTH_LONG).show();
        editor.putString(PREF_SESSION_COOKIE, jsonSessionCookieString);
        editor.apply();
    }

    @Override
    public synchronized void add(URI uri, HttpCookie cookie) {


            // if the cookie that the cookie store attempt to add is a session cookie,
            // we remove the older cookie and save the new one in shared preferences

            remove(URI.create(cookie.getDomain()), cookie);
            saveSessionCookie(cookie);


            mStore.add(URI.create(cookie.getDomain()), cookie);



    }

    @Override
    public synchronized List<HttpCookie> get(URI uri) {

        return mStore.get(uri);
    }

    @Override
    public synchronized List<HttpCookie> getCookies() {
        return mStore.getCookies();
    }

    @Override
    public synchronized List<URI> getURIs() {
        return mStore.getURIs();
    }

    @Override
    public synchronized boolean remove(URI uri, HttpCookie cookie) {
        return mStore.remove(uri, cookie);
    }

    @Override
    public synchronized boolean removeAll() {
        return mStore.removeAll();
    }
}
