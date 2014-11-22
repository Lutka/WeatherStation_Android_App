package com.lutka.weatherstation;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/** A generic class which allow to connect to the server and interpret
 * the response - json object and return it as a java object */
public class GsonRequest<T> extends Request<T>
{
    private final Class<T> type;
    public GsonRequest(String url, Response.ErrorListener listener, Class<T> type)
    {
        super(Method.GET, url, listener);
        this.type = type;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse)
    {
        try
        {
            InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(networkResponse.data),
                    HttpHeaderParser.parseCharset(networkResponse.headers));


            T response = new Gson().fromJson(inputStreamReader, type);
            inputStreamReader.close();
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
        }
        catch (Exception e)
        {
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    protected void deliverResponse(T response)
    {

    }
}
