package http;

public enum HttpStatusCode {

    SC_200(200, "OK"),
    SC_400(400, "Bad Request"),
    SC_404(404, "Not Found"),
    SC_500(500, "Server Error");

    private final int statusCode;
    private final String statusMsg;

    HttpStatusCode(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public String toString() {
        return statusCode + " " +statusMsg;
    }
}
