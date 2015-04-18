package app.beuniful.helow.com.volleyexample;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


import custom.volley.CustomVolleyRequest;
import custom.volley.VolleySingleton;
import volley.entities.WeatherEntity;


public class MainActivity extends ActionBarActivity {

    // Init. volley objects
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private  ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    // [1] Volley Lib. Simple Implementation {String Implementation}
    private void volleySimpleImplementation()
    {
        try
        {

            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this.mRequestQueue,
                        new LruBitmapCache());
            }

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading ...");
            pDialog.show();

            String url = "http://api.arabiaweather....";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    // Done
                    // Do what you want here ..
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Error
                }
            });

            stringRequest.setTag("volley_get_weather_data");
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 0));
            stringRequest.setShouldCache(false);

            mRequestQueue.add(stringRequest);

        }
        catch (Exception errVolley)
        {
            Log.e("get_weather_data",errVolley.getMessage());
        }
    }


    // [2] Volley Lib. Simple Implementation {Json Implementation}
    private void volleySimpleImplementation2()
    {
        try
        {

            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this.mRequestQueue,
                        new LruBitmapCache());
            }

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading ...");
            pDialog.show();

            String url = "http://api.arabiaweather?current=....";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Done
                    // Do what you want here ..

                    pDialog.dismiss();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    pDialog.dismiss();
                }
            });

            jsonObjectRequest.setTag("volley_get_current_weather_data");
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(2000,3,0));
            jsonObjectRequest.setShouldCache(false);

            mRequestQueue.add(jsonObjectRequest);

        }
        catch (Exception errVolley)
        {
            Log.e("get_current_weather",errVolley.getMessage());
        }
    }


    // [3] Volley Lib. Simple Implementation {JsonArray Implementation}
    private void volleySimpleImplementation3()
    {
        try
        {

            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this.mRequestQueue,
                        new LruBitmapCache());
            }

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading ...");
            pDialog.show();

            String url = "http://api.arabiaweather?td=....";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            jsonArrayRequest.setTag("volley_get_td_weather_data");
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(1000,1,0));
            jsonArrayRequest.setShouldCache(false);

            mRequestQueue.add(jsonArrayRequest);

        }
        catch (Exception errVolley)
        {
            Log.e("get_weather_data",errVolley.getMessage());
        }
    }

    // [4] Custom Implementation - Gson [Weather]
    private void volleyCustomImplementation()
    {
        String url = "http://188.226.210.162/demo.json";
        CustomVolleyRequest<WeatherEntity> customVolleyRequest = new CustomVolleyRequest<>(Request.Method.GET,url,WeatherEntity.class,new Response.Listener<WeatherEntity>() {
            @Override
            public void onResponse(WeatherEntity response) {
                // do what you want here
                // No need to loop through json/string and mapping json response with your entity
                ArrayAdapter<WeatherEntity.WeatherRow> adapter = new ArrayAdapter<WeatherEntity.WeatherRow>(getApplicationContext(),0);
                adapter.addAll(response.getResults());
                adapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
            }
        },this);

        // Use Singleton Object [Volley] :
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        customVolleyRequest.setPriority(Request.Priority.LOW);
        customVolleyRequest.setTag("td_weather");
        volleySingleton.getRequestQueue().add(customVolleyRequest);

    }

}
