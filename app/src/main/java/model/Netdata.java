package model;

/**
 * Created by zchao on 2016/5/19.
 */
public class Netdata {

    /**
     * time : 2015-08-14 07:47
     * title : 女环卫工进酒店如厕 被保洁员以堵厕所为由打伤
     * description : 女环卫工进酒店如厕 被保洁员以堵厕所为由打伤...
     * picUrl : http://photocdn.sohu.com/20150814/Img418837718_ss.jpg
     * url : http://news.sohu.com/20150814/n418837716.shtml
     */

    private String time;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
