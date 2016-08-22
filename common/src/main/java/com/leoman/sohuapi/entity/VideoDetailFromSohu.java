package com.leoman.sohuapi.entity;

/**
 * Created by Administrator on 2016/8/15.
 */
public class VideoDetailFromSohu {
    private Long vid; // 视频id
    private Long aid; // 专辑id
    private Integer ip_limit; // 是否ip限制
    private Integer is_download; // 是否允许下载
    private Integer fee;

    private Integer video_length_type; // 0:长视频、1：短视频
    private Integer video_type; // 0：老数据、1：正片、2：预告片、3：幕后花絮、4：制作特辑、5：精彩片花、6：其他周边
    private String video_big_pic; // 视频大图
    private String ver_big_pic; // 大竖图
    private String hor_big_pic; // 大横图
    private String ver_high_pic; // 高清竖图
    private String hor_high_pic; // 高清横图
    private String small_pic; // 小图
    private String ver_small_pic; // 竖小图
    private String tip; //提示时长
    private String video_desc; // 视频描述
    private String video_name; // 视频标题
    private String video_sub_name; // 视频副标题
    private String keyword; // 关键字
    private String album_name; //专辑名
    private Integer is_original_code; // 是否原画
    private String url_html5; // h5 url分类id
    private Long cid; //
    private String cate_code;
    private String second_cate_name;
    private Integer year; // 上映年份
    private Double score; // album评分
    private Integer play_count; // video播放数
    private String director;
    private String main_actor; // 主演
    private String area; // 地域，比如中国
    private String language; // 语言，比如英语
    private String publish_time; // 发布日期
    private Integer video_order; // 该视频的集数
    private Integer latest_video_count; // 最新视频集数
    private Integer total_video_count; // 总集数
    private Long start_time; //片头时刻
    private Long end_time; //  片尾时刻
    private Integer period; // 期数
    private String video_first_name; //	 正标题
    private String video_short_name; // 短标题
    private String show_date;
    private Integer site; // 视频来源，1：搜狐;2：ugc
    private String total_duration; //视频时长，单位秒
    private String definition; // 视频清晰度

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getIp_limit() {
        return ip_limit;
    }

    public void setIp_limit(Integer ip_limit) {
        this.ip_limit = ip_limit;
    }

    public Integer getIs_download() {
        return is_download;
    }

    public void setIs_download(Integer is_download) {
        this.is_download = is_download;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getVideo_length_type() {
        return video_length_type;
    }

    public void setVideo_length_type(Integer video_length_type) {
        this.video_length_type = video_length_type;
    }

    public Integer getVideo_type() {
        return video_type;
    }

    public void setVideo_type(Integer video_type) {
        this.video_type = video_type;
    }

    public String getVideo_big_pic() {
        return video_big_pic;
    }

    public void setVideo_big_pic(String video_big_pic) {
        this.video_big_pic = video_big_pic;
    }

    public String getVer_big_pic() {
        return ver_big_pic;
    }

    public void setVer_big_pic(String ver_big_pic) {
        this.ver_big_pic = ver_big_pic;
    }

    public String getHor_big_pic() {
        return hor_big_pic;
    }

    public void setHor_big_pic(String hor_big_pic) {
        this.hor_big_pic = hor_big_pic;
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

    public String getSmall_pic() {
        return small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    public String getVer_small_pic() {
        return ver_small_pic;
    }

    public void setVer_small_pic(String ver_small_pic) {
        this.ver_small_pic = ver_small_pic;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_sub_name() {
        return video_sub_name;
    }

    public void setVideo_sub_name(String video_sub_name) {
        this.video_sub_name = video_sub_name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public Integer getIs_original_code() {
        return is_original_code;
    }

    public void setIs_original_code(Integer is_original_code) {
        this.is_original_code = is_original_code;
    }

    public String getUrl_html5() {
        return url_html5;
    }

    public void setUrl_html5(String url_html5) {
        this.url_html5 = url_html5;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCate_code() {
        return cate_code;
    }

    public void setCate_code(String cate_code) {
        this.cate_code = cate_code;
    }

    public String getSecond_cate_name() {
        return second_cate_name;
    }

    public void setSecond_cate_name(String second_cate_name) {
        this.second_cate_name = second_cate_name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getPlay_count() {
        return play_count;
    }

    public void setPlay_count(Integer play_count) {
        this.play_count = play_count;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMain_actor() {
        return main_actor;
    }

    public void setMain_actor(String main_actor) {
        this.main_actor = main_actor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public Integer getVideo_order() {
        return video_order;
    }

    public void setVideo_order(Integer video_order) {
        this.video_order = video_order;
    }

    public Integer getLatest_video_count() {
        return latest_video_count;
    }

    public void setLatest_video_count(Integer latest_video_count) {
        this.latest_video_count = latest_video_count;
    }

    public Integer getTotal_video_count() {
        return total_video_count;
    }

    public void setTotal_video_count(Integer total_video_count) {
        this.total_video_count = total_video_count;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getVideo_first_name() {
        return video_first_name;
    }

    public void setVideo_first_name(String video_first_name) {
        this.video_first_name = video_first_name;
    }

    public String getVideo_short_name() {
        return video_short_name;
    }

    public void setVideo_short_name(String video_short_name) {
        this.video_short_name = video_short_name;
    }

    public String getShow_date() {
        return show_date;
    }

    public void setShow_date(String show_date) {
        this.show_date = show_date;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public String getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(String total_duration) {
        this.total_duration = total_duration;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
