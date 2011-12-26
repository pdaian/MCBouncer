package com.mcbouncer.util;

import com.mcbouncer.http.Request;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * Useful HTTP utilities. URL encoding/decoding, also makes a query
 * string out of a Request object
 * 
 */
public class HTTPUtils {

    /**
     * Combines the URL and parameters from a Request object into
     * one GET-formatted URL.
     * 
     * @param request
     * @return 
     */
    public static String parseParameters(Request request) {
        String url = request.getURL();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : request.getParameters().keySet()) {
            params.add(new BasicNameValuePair(key, request.getParameters().get(key)));
        }

        if (params.size() > 0) {
            url += "?" + URLEncodedUtils.format(params, "UTF-8");
        }

        return url;
    }

    /**
     * URL-Encodes the string in UTF-8
     * 
     * @param string
     * @return 
     */
    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF8 not found! What is wrong with your system?", ex);
        }
    }

    /**
     * URL-Decodes the string in UTF-8
     * 
     * @param string
     * @return 
     */
    public static String urlDecode(String string) {
        try {
            return URLDecoder.decode(string, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF8 not found! What is wrong with your system?", ex);
        }
    }
}
