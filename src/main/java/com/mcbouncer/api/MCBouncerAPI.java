package com.mcbouncer.api;

import com.mcbouncer.MCBouncer;
import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.exception.ParserException;
import com.mcbouncer.http.Transport;
import com.mcbouncer.http.request.Request;
import com.mcbouncer.http.response.Response;
import com.mcbouncer.util.HTTPUtils;
import com.mcbouncer.util.MiscUtils;
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
    protected String lastError = "";

    public MCBouncerAPI(MCBouncer controller) {
        this.controller = controller;
    }

    /**
     * Gets a MCBouncer API url with the given parameters.
     * 
     * This method is a varargs method, so in order to send 
     * a request to http://mcb.com/api/foo/Bar/baz, one would 
     * call the method as: getAPIURl("foo", "Bar", "baz")
     * 
     * @param params The parameters to send to the API.
     * @return
     * @throws NetworkException 
     */
    protected Response getAPIURL(String... params) throws NetworkException {
        String[] newParams = new String[params.length];
        int i = 0;
        for (String param : params) {
            newParams[i] = HTTPUtils.urlEncode(param);
            i++;
        }

        String url = controller.getConfiguration().getWebsite() + "/api/" + MiscUtils.join(newParams, "/"); //Appends the website
        controller.getLogger().debug("Getting URL - " + url);

        url = url.replaceAll("apiUNIQKey", controller.getConfiguration().getAPIKey()); //Replaces apiUNIQKey with the real key

        Transport transport = new Transport(controller);
        Request request = new Request(controller);
        request.setURL(url);
        transport.setRequest(request);

        return transport.sendURL();
    }

    /**
     * Gets the count of something. Type can be one of "Ban", "IPBan", or "Note"
     * 
     * @param type
     * @param user
     * @return
     * @throws NetworkException
     * @throws APIException 
     */
    protected Integer getTypeCount(String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("get" + type + "Count", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Returns the number of bans a user has on every server
     * 
     * @param user Username to check
     * @return Integer Number of bans
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getBanCount(String user) throws NetworkException, APIException {
        return getTypeCount("Ban", user);
    }

    /**
     * Returns the number of bans an IP has on every server
     * 
     * @param user IP to check
     * @return Integer Number of bans
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getIPBanCount(String user) throws NetworkException, APIException {
        return getTypeCount("IPBan", user);
    }

    /**
     * Returns the number of bans a user has on every server,
     * in addition to the number of bans the IP has on every
     * server. Used to get a total ban count.
     * 
     * @param user Username to check
     * @param ip IP address to check
     * @return Integer Number of banss
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getTotalBanCount(String user, String ip) throws NetworkException, APIException {
        return getTypeCount("Ban", user) + getTypeCount("IPBan", ip);
    }

    /**
     * Returns the number of notes a user has on every server
     * 
     * @param user Username to check
     * @return Integer Number of notes
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getNoteCount(String user) throws NetworkException, APIException {
        return getTypeCount("Note", user);
    }

    /**
     * Returns a list of all the bans that a user has
     * 
     * @param user Username to check
     * @return List of UserBan objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<UserBan> getBans(String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("getBans", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Returns a list of all the bans that an IP has
     * 
     * @param ip IP to check
     * @return List of IPBan objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<IPBan> getIPBans(String ip) throws NetworkException, APIException {
        Response response = this.getAPIURL("getIPBans", "apiUNIQKey", ip);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Returns a list of all the notes that a user has
     * 
     * @param user Username to check
     * @return List of UserNote objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<UserNote> getNotes(String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("getNotes", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Update the last login time of a username
     * 
     * @param user Username to update
     * @param ip IP address the user logged in with
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean updateUser(String user, String ip) throws NetworkException, APIException {
        Response response = this.getAPIURL("updateUser", "apiUNIQKey", user, ip);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Returns the reason for something on this server only
     * 
     * @param type Type of item to get: Ban, IPBan, or Note
     * @param user Username to check
     * @return Reason for item
     * @throws NetworkException
     * @throws APIException 
     */
    protected String getReason(String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("get" + type + "Reason", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Gets the reason for a user's ban on this server.
     * If the user is not banned, it throws an APIException
     * 
     * @param user Username to check
     * @return Ban reason
     * @throws NetworkException
     * @throws APIException 
     */
    public String getBanReason(String user) throws NetworkException, APIException {
        return getReason("Ban", user);
    }

    /**
     * Gets the reason for an IP's ban on this server.
     * If the IP is not banned, it throws an APIException
     * 
     * @param ip IP to check
     * @return Ban reason
     * @throws NetworkException
     * @throws APIException 
     */
    public String getIPBanReason(String ip) throws NetworkException, APIException {
        return getReason("IPBan", ip);
    }

    /**
     * Checks if the user/IP is banned.
     * 
     * @param type Type of ban
     * @param user Username/IP to check
     * @return Ban status
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean isBanned(String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("get" + type + "Reason", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
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

    /**
     * Checks if the username is banned on this server
     * 
     * @param user Username to check
     * @return Whether or not the user is banned
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean isBanned(String user) throws NetworkException, APIException {
        return isBanned("Ban", user);
    }

    /**
     * Checks if the IP is banned on this server
     * 
     * @param ip IP to check
     * @return Whether or not the user is banned
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean isIPBanned(String ip) throws NetworkException, APIException {
        return isBanned("IPBan", ip);
    }

    /**
     * Removes something from the server
     * 
     * @param type Type of thing to remove. Can be Ban, IPBan, Note
     * @param first User/IP to unban, or note issuer
     * @param second Note ID, if type == note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean removeSomething(String type, String first, String second) throws NetworkException, APIException {
        Response response = null;
        if (second != null) {
            response = this.getAPIURL("remove" + type, "apiUNIQKey", first, second);
        } else {
            response = this.getAPIURL("remove" + type, "apiUNIQKey", first);
        }

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Removes a ban for this user on this server
     * 
     * @param user Username to unban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeBan(String user) throws NetworkException, APIException {
        return removeSomething("Ban", user, null);
    }

    /**
     * Removes a ban for this IP on this server
     * 
     * @param ip IP to unban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeIPBan(String ip) throws NetworkException, APIException {
        return removeSomething("IPBan", ip, null);
    }

    /**
     * Removes a note with this NoteID by this issuer. If the
     * issuer is not the person that wrote the note, and they
     * do not have the edit_all_notes privilege on the website,
     * they will not be allowed to remove this note.
     * 
     * @param id Note ID to remove
     * @param issuer User who issued the removeNote command
     * @return Whether or not the request succeeded.
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeNote(Integer id, String issuer) throws NetworkException, APIException {
        return removeSomething("Note", issuer, id.toString());
    }

    /**
     * Adds something to the server
     * 
     * @param type Type of item to add. Ban, IPBan, GlobalNote, or Note
     * @param issuer User issuing the addition
     * @param user User to ban/note
     * @param reason Reason for the ban, or the text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean addSomething(String type, String issuer, String user, String reason) throws NetworkException, APIException {
        Response response = this.getAPIURL("add" + type, "apiUNIQKey", issuer, user, reason);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            }
            catch( ParserException e ) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Adds a ban for a username
     * 
     * @param issuer Person who issued the ban
     * @param user Username to ban
     * @param reason Reason for the ban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addBan(String issuer, String user, String reason) throws NetworkException, APIException {
        return addSomething("Ban", issuer, user, reason);
    }

    /**
     * Adds a ban for an IP
     * 
     * @param issuer Person who issued the ban
     * @param ip IP to ban
     * @param reason Reason for the ban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addIPBan(String issuer, String ip, String reason) throws NetworkException, APIException {
        return addSomething("IPBan", issuer, ip, reason);
    }

    /**
     * Adds a note for a username
     * 
     * @param issuer Person who issued the note
     * @param user Username to note
     * @param note Text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addNote(String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething("Note", issuer, user, note);
    }

    /**
     * Adds a global note for a username
     * 
     * @param issuer Person who issued the note
     * @param user Username to note
     * @param note Text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addGlobalNote(String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething("GlobalNote", issuer, user, note);
    }

    /**
     * Returns the last error that was received
     * from the API.
     * 
     */
    public String getLastError() {
        return lastError;
    }
}
