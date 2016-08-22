package com.leoman.sohuapi.api;

import com.leoman.sohuapi.entity.*;
import com.leoman.sohuapi.result.ResultConver;
import com.leoman.utils.http.WebserviceUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SohuApi {

    /**
     * 专辑详情信息
     */
    private static String ALBUM_DETAIL = "http://open.mb.hd.sohu.com/v4/album/info/{aid}.json";

    /**
     * 分集列表获取
     */
    private static String VIDEO_LIST = "http://open.mb.hd.sohu.com/v4/album/videos/{aid}.json";

    /**
     * 分集列表获取
     */
    private static String VIDEO_DETAIL = "http://open.mb.hd.sohu.com/v4/video/info/{vid}.json";

    /**
     *
     * @param vid 视频ID
     * @param aid 专辑
     * @return
     */
    public static VideoDetailFromSohu apiFromSohuVideoDetail(Long vid, Long aid) {
        if (vid == null) return null;
        String aidVar = aid == null ? "" : String.valueOf(aid);
        String url = "";
        url = VIDEO_DETAIL.replace("{vid}", String.valueOf(vid));
        url = url + "?site=2&api_key=7be8e26eb35b3af03825c2ab07c1a3a6&aid=" + aidVar;
        String result = WebserviceUtil.get(url);
        System.out.println(result);
        Result _r = ResultConver.jsonConverToEntity(result);
        if (!ResultConver.isOk(_r)) {
            return null;
        }
        return ResultConver.converVideoDetailFromData(_r);
    }

    /**
     * @param aid 专辑id
     * @return
     */
    public static AlbumFromSohu apiFromSohuAlibum(Long aid) {

        if (aid == null) return null;
        String url = "";
        url = ALBUM_DETAIL.replace("{aid}", String.valueOf(aid));
        url = url + "?partner=24101&api_key=7be8e26eb35b3af03825c2ab07c1a3a6";
        String result = WebserviceUtil.get(url);
        Result _r = ResultConver.jsonConverToEntity(result);
        if (!ResultConver.isOk(_r)) {
            return null;
        }
//        System.out.println(_r.getData().toString());
        AlbumFromSohu albumFromSohu = ResultConver.converAlbumFromData(_r);
        System.out.println(albumFromSohu.toString());
        return albumFromSohu;
    }

    /**
     * 分集列表获取
     *
     * @param aid      专辑id
     * @param page     当前页数
     * @param pageSize 当页条数，20~50.默认20
     * @param order    按剧集排序方式，1 正序，默认；2 倒序
     * @param vid      视频vid，当有vid时，定位到vid所在页的列表（仅获取自制剧时有效）
     * @param site     视频来源标识 (如果传vid参数时，必填)  2.sohu_ugc
     * @param version  1：高清、2：普清、21：超清、31：原画，默认出高清版本
     * @return
     */
    public static List<VideoFromSohu> apiFromSohuVideoList(
            Long aid, Integer page, Integer pageSize, Integer order, Long vid, Integer site, Integer version) {

        if (aid == null) return null;
//        String pageVar = (page != null ? String.valueOf(page) : "");
//        String pageSizeVar = (pageSize != null ? String.valueOf(pageSize) : "");
//        String orderVar = (order != null ? String.valueOf(order) : "");
//        String vidVar = (vid != null ? String.valueOf(vid) : "");
//        String siteVar = (site != null ? String.valueOf(site) : "");
//        String versionVar = (version != null ? String.valueOf(version) : "");
        String url = "";
        url = VIDEO_LIST.replace("{aid}", String.valueOf(aid));
        url = url + "?partner=24101&api_key=7be8e26eb35b3af03825c2ab07c1a3a6&page=" + page;
        String result = WebserviceUtil.get(url);
        Result _r = ResultConver.jsonConverToEntity(result);
        if (!ResultConver.isOk(_r)) {
            return Collections.emptyList();
        }
        VideoSubFromSohu videoSubFromSohu = ResultConver.converVideoFromSohuFromData(_r);
        return null;
    }

    /**
     * 分集列表获取
     *
     * @param aid  专辑id
     * @param page 当前页数
     * @return
     */
    public static VideoSubFromSohu apiFromSohuVideoList(
            Long aid, Integer page,Integer pagesize) {

        if (aid == null) return null;
//        String pageVar = (page != null ? String.valueOf(page) : "");
//        String pageSizeVar = (pageSize != null ? String.valueOf(pageSize) : "");
//        String orderVar = (order != null ? String.valueOf(order) : "");
//        String vidVar = (vid != null ? String.valueOf(vid) : "");
//        String siteVar = (site != null ? String.valueOf(site) : "");
//        String versionVar = (version != null ? String.valueOf(version) : "");

        pagesize = (pagesize == null ? 10 : pagesize);

        String url = "";
        url = VIDEO_LIST.replace("{aid}", String.valueOf(aid));
        url = url + "?api_key=7be8e26eb35b3af03825c2ab07c1a3a6&page=" + page + "&page_size=" + pagesize;
        String result = WebserviceUtil.get(url);
        Result _r = ResultConver.jsonConverToEntity(result);
        if (!ResultConver.isOk(_r)) {
            return new VideoSubFromSohu();
        }
        VideoSubFromSohu videoSubFromSohu = ResultConver.converVideoFromSohuFromData(_r);
        return videoSubFromSohu;
    }

    public static void main(String[] args) {
        apiFromSohuAlibum(6189118L);
        //apiFromSohuVideoList(6189118L, 3);
//        apiFromSohuVideoList(6189118L, 0);
//        apiFromSohuVideoDetail(84884578L,1000000571955L);
    }
}
