package com.leoman.sohuapi.entity;

/**
 * 专辑详情信息
 * Created by yesong on 2016/8/15.
 */
public class AlbumFromSohu {

    private Long aid; // 专辑ID
    private String album_name;//专辑全名
    private String album_firstname; // 专辑正标题
    private String album_subname; // 专辑子标题
    private String album_desc; // 专辑描述
    private String recommend_tip; // 一句话点评
    private Integer ip_limit; //是否ip限制  1为是；0为否
    private Integer fee; // 是否收费 1为是；0为否
    private Integer is_sohu_own; // 是否为搜狐独家 1为是，0为否
    private Double is_original_code;// 是否为原画  1为是，0为否
    private Integer is_super_code; //  是否为超清  1为是，0为否
    private Integer is_trailer ; // 是否为片花 1为片花，0是正片
    private Integer is_over; // 是否完成更新  0为更新中.空则为已结束更新
    private String update_notification; // 更新提示
    private String ver_high_pic; //(竖)专辑高清图
    private String hor_high_pic; // (横)专辑高清图
    private String ver_original_pic; // (竖)专辑原图
    private String hor_original_pic; // (横)专辑原图
    private Integer total_video_count; // 总集数
    private Integer latest_video_count; // 目前更新到了多少集
    private Integer year; // 出品年份
    private Integer area_id; // 地区id 给映射
    private Integer language_id; // 语言id 给映射
    private String language;
    private Double score;

    private Integer play_count; //总播放数
    private Long cid;
    private String cate_code;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_firstname() {
        return album_firstname;
    }

    public void setAlbum_firstname(String album_firstname) {
        this.album_firstname = album_firstname;
    }

    public String getAlbum_subname() {
        return album_subname;
    }

    public void setAlbum_subname(String album_subname) {
        this.album_subname = album_subname;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public String getRecommend_tip() {
        return recommend_tip;
    }

    public void setRecommend_tip(String recommend_tip) {
        this.recommend_tip = recommend_tip;
    }

    public Integer getIp_limit() {
        return ip_limit;
    }

    public void setIp_limit(Integer ip_limit) {
        this.ip_limit = ip_limit;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getIs_sohu_own() {
        return is_sohu_own;
    }

    public void setIs_sohu_own(Integer is_sohu_own) {
        this.is_sohu_own = is_sohu_own;
    }

    public Double getIs_original_code() {
        return is_original_code;
    }

    public void setIs_original_code(Double is_original_code) {
        this.is_original_code = is_original_code;
    }

    public Integer getIs_super_code() {
        return is_super_code;
    }

    public void setIs_super_code(Integer is_super_code) {
        this.is_super_code = is_super_code;
    }

    public Integer getIs_trailer() {
        return is_trailer;
    }

    public void setIs_trailer(Integer is_trailer) {
        this.is_trailer = is_trailer;
    }

    public Integer getIs_over() {
        return is_over;
    }

    public void setIs_over(Integer is_over) {
        this.is_over = is_over;
    }

    public String getUpdate_notification() {
        return update_notification;
    }

    public void setUpdate_notification(String update_notification) {
        this.update_notification = update_notification;
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

    public String getVer_original_pic() {
        return ver_original_pic;
    }

    public void setVer_original_pic(String ver_original_pic) {
        this.ver_original_pic = ver_original_pic;
    }

    public String getHor_original_pic() {
        return hor_original_pic;
    }

    public void setHor_original_pic(String hor_original_pic) {
        this.hor_original_pic = hor_original_pic;
    }

    public Integer getTotal_video_count() {
        return total_video_count;
    }

    public void setTotal_video_count(Integer total_video_count) {
        this.total_video_count = total_video_count;
    }

    public Integer getLatest_video_count() {
        return latest_video_count;
    }

    public void setLatest_video_count(Integer latest_video_count) {
        this.latest_video_count = latest_video_count;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getArea_id() {
        return area_id;
    }

    public void setArea_id(Integer area_id) {
        this.area_id = area_id;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}