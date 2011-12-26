package com.mcbouncer.http;

/**
 * Different types of HTTP request types. Only
 * GET and POST are supported. The others just
 * redirect to POST requests.
 * 
 */
public enum RequestType {

    GET,
    POST,
    PUT,
    HEAD,
    DELETE
}
