package durgaapi;

import java.util.HashMap;

public class Response implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public boolean ok = false;
    public String detail = null;
    public HashMap<String, Object> data = null;

    public Response() {
    }

    public Response(boolean ok, String detail, HashMap<String, Object> data) {
        this.ok = ok;
        this.detail = detail;
        this.data = data;
    }


    public boolean isOk() {
        return ok;
    }

    public String getDetail() {
        return detail;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public String getMessage(){
        return "ok: " + ok + " detail: " + detail + " data: " + data;
    }

}
