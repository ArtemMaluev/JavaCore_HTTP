public class Post {

    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    public Post() {}

    public Post(String date, String explanation, String hdurl, String media_type, String service_version, String title, String url) {
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Post {" +
                "date = '" + date + '\'' +
                ", explanation = '" + explanation + '\'' +
                ", hdurl = '" + hdurl + '\'' +
                ", media_type = '" + media_type + '\'' +
                ", service_version = '" + service_version + '\'' +
                ", title = '" + title + '\'' +
                ", url = '" + url + '\'' +
                '}';
    }
}
