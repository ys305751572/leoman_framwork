package com.leoman.sohuapi.entity;

/**
 * 分集实体
 * Created by yesong on 2016/8/15.
 */
public class VideoFromSohu {

    private Long aid; // 专辑id
    private String cate_code; // 所属类型，一级分类id
    private Long cid; // 所属类型，二级分类code
    private Integer video_order; // 视频所在专辑的集数，编辑填的。（非电视剧、动漫类不建议直接展示或进行逻辑判断）
    private String clarity; // 视频清晰度，默认是高清
    private Long vid; // 视频vid
    private String video_name; //视频全标题
    private String video_firstname; // 视频正标题
    private String video_subname; // 视频副标题
    private String create_date; // 视频发布时间
    private Long time_length; // 单位是秒,每一集视频的时长
    private String ver_high_pic; // 视频竖高清图
    private String hor_high_pic; // 视频横高清图
    private String hor_original_pic; // 视频横原图
    private String url_PC; // PC播放地址
    private String url_html5; //H5播放地址
    private Integer site; // 分集来源，1：搜狐,4:优酷，5:爱奇艺，6:乐视，7:腾讯视频，8:土豆，10:pptv，12:cntv，11:pps

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getVideo_order() {
        return video_order;
    }

    public void setVideo_order(Integer video_order) {
        this.video_order = video_order;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_firstname() {
        return video_firstname;
    }

    public void setVideo_firstname(String video_firstname) {
        this.video_firstname = video_firstname;
    }

    public String getVideo_subname() {
        return video_subname;
    }

    public void setVideo_subname(String video_subname) {
        this.video_subname = video_subname;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Long getTime_length() {
        return time_length;
    }

    public void setTime_length(Long time_length) {
        this.time_length = time_length;
    }

    public String getVer_high_pic() {
        return ver_high_pic;
    }

    public void setVer_high_pic(String ver_high_pic) {
        this.ver_high_pic = ver_high_pic;
    }

    public String getHor_high_pic() {
        return hor_high_pic;
    }

    public void setHor_high_pic(String hor_high_pic) {
        this.hor_high_pic = hor_high_pic;
    }

    public String getHor_original_pic() {
        return hor_original_pic;
    }

    public void setHor_original_pic(String hor_original_pic) {
        this.hor_original_pic = hor_original_pic;
    }

    public String getUrl_PC() {
        return url_PC;
    }

    public void setUrl_PC(String url_PC) {
        this.url_PC = url_PC;
    }

    public String getUrl_html5() {
        return url_html5;
    }

    public void setUrl_html5(String url_html5) {
        this.url_html5 = url_html5;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }
}
