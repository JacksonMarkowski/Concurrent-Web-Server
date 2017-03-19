package http;

public enum HttpStatusCode {

    _200(200, "OK"),
    _404(404, "Not Found"),
    _500(500, "server.server Error");

    private final int statusCode;
    private final String statusMsg;

    HttpStatusCode(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
