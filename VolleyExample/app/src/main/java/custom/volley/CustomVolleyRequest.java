package custom.volley;

import android.content.Context;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomVolleyRequest<T> extends Request<T> {

    private final Gson mGson;
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private Priority priority = Priority.IMMEDIATE;
    private Context mContext;
    private Map<String, String> params = null;


    public CustomVolleyRequest(int method, String url, Class<T> clazz,
                       Listener<T> listener, ErrorListener errorListener, Context context) {
        super(method, url, errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        mGson = new Gson();
        mContext = context;
    }

    @Override
    protected void deliverResponse(T response) {

        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(response.data, "UTF-8");

            return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setPostParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() {
        return this.params;
    }


}