package com.leoman.post.entity;

import com.leoman.category.entity.Category;
import com.leoman.post.entity.vo.PostCommentVo;
import com.leoman.post.entity.vo.PostImageVo;
import com.leoman.post.entity.vo.PostUserPlusVo;
import com.leoman.post.entity.vo.PostUserVo;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_post_base")
@Entity
public class PostBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "praise")
    private Integer praise = 0;

    @Column(name = "comment")
    private Integer comment = 0;

    @Column(name = "status")
    private Integer status;

    @Column(name = "essence")
    private Integer essence = 0;

    @Column(name = "stick")
    private Integer stick = 0;

    @Column(name = "create_date")
    private Long createDate;

    @Column(name = "modify_date")
    private Long updateDate;

    @Column(name = "avater")
    private String avater;

    @Column(name = "name")
    private String name = "";

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "end_date")
    private Long endDate;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "url")
    private String url;

    @Column(name = "length")
    private Integer length;

    @Transient
    private PostUserPlusVo user;

    @Transient
    private List<PostImageVo> imageList;

    @Transient
    private List<PostUserVo> userList;

    @Transient
    private List<PostCommentVo> commentList;

    @Transient
    private List<TpPostSub> tpPostSubList;

    @Transient
    private List<ZbPostInfo> zbSubList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEssence() {
        return essence;
    }

    public void setEssence(Integer essence) {
        this.essence = essence;
    }

    public Integer getStick() {
        return stick;
    }

    public void setStick(Integer stick) {
        this.stick = stick;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public PostUserPlusVo getUser() {
        return user;
    }

    public void setUser(PostUserPlusVo user) {
        this.user = user;
    }

    public List<PostImageVo> getImageList() {
        return imageList;
    }

    public void setImageList(List<PostImageVo> imageList) {
        this.imageList = imageList;
    }

    public List<PostUserVo> getUserList() {
        return userList;
    }

    public void setUserList(List<PostUserVo> userList) {
        this.userList = userList;
    }

    public List<PostCommentVo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<PostCommentVo> commentList) {
        this.commentList = commentList;
    }

    public List<TpPostSub> getTpPostSubList() {
        return tpPostSubList;
    }

    public void setTpPostSubList(List<TpPostSub> tpPostSubList) {
        this.tpPostSubList = tpPostSubList;
    }

    public List<ZbPostInfo> getZbSubList() {
        return zbSubList;
    }

    public void setZbSubList(List<ZbPostInfo> zbSubList) {
        this.zbSubList = zbSubList;
    }
}
