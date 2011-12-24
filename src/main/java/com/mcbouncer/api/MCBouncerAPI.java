package com.mcbouncer.api;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.http.Transport;
import com.mcbouncer.http.request.Request;
import com.mcbouncer.http.response.Response;
import com.mcbouncer.util.node.JSONNode;
import com.mcbouncer.util.node.MapNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to interact with the MCBouncer website
 * itself. Each method throws NetworkException and
 * APIException. 
 * 
 * APIException is thrown when there is a problem 
 * parsing the API result. NetworkException is thrown 
 * when there is a problem contacting the website.
 * 
 * All URLs that contain an API key are obfuscated with
 * /apiUNIQKey/ until they get sent to getURL(). This is 
 * to prevent debug messages unintentionally revealing
 * a server's API key. This also prevents stack traces
 * revealing the key.
 * 
 */
public class MCBouncerAPI {

    protected MCBouncer controller;

    public MCBouncerAPI(MCBouncer controller) {
        this.controller = controller;
    }

    protected Response getAPIURL(String url) throws NetworkException {
        controller.getLogger().debug("Getting URL - " + url);
        
        url = url.replaceAll("apiUNIQKey", controller.getConfiguration().getAPIKey()); //Replaces apiUNIQKey with the real key
        url = controller.getConfiguration().getWebsite() + "/api" + url; //Appends the website
        
        Transport transport = new Transport(controller);
        Request request = new Request(controller);
        request.setURL(url);
        transport.setRequest(request);

        return transport.sendURL();
    }

    protected Integer getTypeCount(String type, String user) throws NetworkException, APIException {
        String url = "/get" + type + "Count/apiUNIQKey/" + user;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getInteger("totalcount", 0);
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public Integer getBanCount(String user) throws NetworkException, APIException {
        return getTypeCount("Ban", user);
    }

    public Integer getIPBanCount(String user) throws NetworkException, APIException {
        return getTypeCount("IPBan", user);
    }

    public Integer getTotalBanCount(String user, String ip) throws NetworkException, APIException {
        return getTypeCount("Ban", user) + getTypeCount("IPBan", ip);
    }

    public Integer getNoteCount(String user) throws NetworkException, APIException {
        return getTypeCount("Note", user);
    }

    public List<UserBan> getBans(String user) throws NetworkException, APIException {
        String url = "/getBans/apiUNIQKey/" + user;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<UserBan> bans = new ArrayList<UserBan>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new UserBan(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public List<IPBan> getIPBans(String ip) throws NetworkException, APIException {
        String url = "/getIPBans/apiUNIQKey/" + ip;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<IPBan> bans = new ArrayList<IPBan>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new IPBan(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public List<UserNote> getNotes(String user) throws NetworkException, APIException {
        String url = "/getNotes/apiUNIQKey/" + user;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<UserNote> bans = new ArrayList<UserNote>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new UserNote(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public boolean updateUser(String user, String ip) throws NetworkException, APIException {
        String url = "/getBans/apiUNIQKey/" + user + "/" + ip;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            return json.getBoolean("success", false);
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    protected String getReason(String type, String user) throws NetworkException, APIException {
        String url = "/get" + type + "Reason/apiUNIQKey/" + user;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getString("reason");
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public String getBanReason(String user) throws NetworkException, APIException {
        return getReason("Ban", user);
    }

    public String getIPBanReason(String ip) throws NetworkException, APIException {
        return getReason("IPBan", ip);
    }

    protected boolean isBanned(String type, String user) throws NetworkException, APIException {
        String url = "/get" + type + "Reason/apiUNIQKey/" + user;
        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getBoolean("is_banned", false);
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public boolean isBanned(String user) throws NetworkException, APIException {
        return isBanned("Ban", user);
    }

    public boolean isIPBanned(String ip) throws NetworkException, APIException {
        return isBanned("IPBan", ip);
    }

    protected boolean removeSomething(String type, String first, String second) throws NetworkException, APIException {
        String url = "/remove" + type + "/apiUNIQKey/" + first;
        if (second != null) {
            url += "/" + second;
        }

        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            return json.getBoolean("success", false);
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public boolean removeBan(String user) throws NetworkException, APIException {
        return removeSomething("Ban", user, null);
    }

    public boolean removeIPBan(String ip) throws NetworkException, APIException {
        return removeSomething("IPBan", ip, null);
    }

    public boolean removeNote(Integer id, String issuer) throws NetworkException, APIException {
        return removeSomething("Note", issuer, id.toString());
    }

    protected boolean addSomething(String type, String issuer, String user, String reason) throws NetworkException, APIException {
        String url = "/add" + type + "/apiUNIQKey/" + issuer + "/" + user + "/" + reason;

        Response response = this.getAPIURL(url);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = response.getJSONResult();
            if (json == null) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            return json.getBoolean("success", false);
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    public boolean addBan(String issuer, String user, String reason) throws NetworkException, APIException {
        return addSomething("Ban", issuer, user, reason);
    }

    public boolean addIPBan(String issuer, String ip, String reason) throws NetworkException, APIException {
        return addSomething("IPBan", issuer, ip, reason);
    }

    public boolean addNote(String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething("Note", issuer, user, note);
    }

    public boolean addGlobalNote(String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething("GlobalNote", issuer, user, note);
    }
}
