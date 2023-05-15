package durgaapi;

import java.util.HashMap;

/**
 * Response
 * 
 * This class is used to represent a response.
 */
public class Response implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public boolean ok = false;
    public String detail = null;
    public HashMap<String, Object> data = null;

    public Response() {}

    /**
     * Constructor
     * 
     * @param ok
     * @param detail
     * @param data
     */
    public Response(boolean ok, String detail, HashMap<String, Object> data) {
        this.ok = ok;
        this.detail = detail;
        this.data = data;
    }

    /**
     * method to get the ok from the response
     * 
     * @return ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * method to get the detail from the response
     * 
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * method to get the data from the response
     * 
     * @return data
     */
    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * method to get full message from the response
     * 
     * @param ok
     */
    public String getMessage(){
        return "ok: " + ok + " detail: " + detail + " data: " + data;
    }

}
