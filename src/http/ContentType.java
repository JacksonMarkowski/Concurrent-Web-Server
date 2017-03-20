package http;

public enum ContentType {

    aac(".acc", "audio/acc"),
    bin(".bin", "application/octet-stream"),
    bmp(".bmp", "image/bmp"),
    csh(".csh", "application/x-csh"),
    css(".css", "text/css"),
    doc(".doc", "application/msword"),
    gif(".gif", "image/gif"),
    htm(".htm", "text/html"),
    html(".html", "text/html"),
    ico(".ico", "image/x-icon"),
    jar(".jar", "application/java-archive"),
    jpeg(".jpeg", "image/jpeg"),
    jpg(".jpg", "image/jpeg"),
    js(".js", "application/javascript"),
    json(".json", "application/json"),
    mpeg(".mpeg", "video/mpeg"),
    mp4(".mp4", "video/mp4"),
    pdf(".pdf", "application/pdf"),
    png(".png", "image/png"),
    tar(".tar", "application/x-tar"),
    txt(".txt", "text/plain"),
    webp(".webp", "image/webp"),
    xml(".xml", "application/xml");

    private String extension;
    private String MIMEType;

    ContentType(String extension, String MIMEType) {
        this.extension = extension;
        this.MIMEType = MIMEType;
    }

    public String getMIMEType() {
        return MIMEType;
    }



}
