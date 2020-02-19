package com.us.skyguardian.translock;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class SingletonRequestQueue {

    private static SingletonRequestQueue instance;
    private RequestQueue queue;
    private static Context ctx;
    private LoadingDialog loadingDialog;

    private SingletonRequestQueue (Context context) {

        this.ctx = context;
        queue = getRequestQueue();

    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {

        if (instance == null) {

            instance = new SingletonRequestQueue(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue () {

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(ctx.getApplicationContext()), CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        if (this.queue == null) {

            queue = Volley.newRequestQueue(ctx.getApplicationContext());
            //queue.start();0
        }

        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.i("BLE", req.toString());
        getRequestQueue().add(req);

    }

}
