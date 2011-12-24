package com.mcbouncer.http.response;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.exception.ParserException;
import com.mcbouncer.util.node.JSONNode;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Response container class. Contains any headers that were
 * sent, cookies, content type, length, and a JSON object if
 * one requests it.
 * 
 */
public class Response {

    protected MCBouncer controller;
    protected List<Header> headers;
    protected HTTPCode code;
    protected String contentType;
    protected Long contentLength;
    protected String content;
    protected JSONNode jsonObject;
    protected List<Cookie> cookies;

    public Response(MCBouncer controller) {
        this.controller = controller;
    }

    /**
     * Parses the result as a JSON string. 
     * 
     * If it's not JSON-formatted, it will throw a ParserException.
     * Otherwise, it will return a JSONNode with the data.
     * 
     * @return 
     */
    @SuppressWarnings("unchecked")
    public JSONNode getJSONResult() {
        if (jsonObject == null) {
            try {
                jsonObject = new JSONNode(new JSONParser().parse(content));
            } catch (ParseException ex) {
                throw new ParserException("Could not parse JSON object (" + ex.getMessage() + ")");
            }
        }
        return jsonObject;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public HTTPCode getHTTPCode() {
        return code;
    }

    public void setHTTPCode(HTTPCode code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }
}
