package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.ZbPostInfo;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PostBaseDao extends IBaseJpaRepository<PostBase> {

    @Query("select a from PostBase a where a.id = ?1 and a.type = ?2")
    public PostBase getPostInfo(Long postId, Integer type);

    @Query("select a from PostBase a where a.content like ?1 order by a.createDate,a.praise desc")
    public Page<PostBase> iPageDesc(String key, Pageable pageable);

    @Query("select a from PostBase a where a.type = 3 and a.content like ?1 and a.userInfo.id in (select b.sourceUser.id from UserAttention b where b.user.id = ?2)")
    public Page<PostBase> iPageAttention(String key, Long userId, Pageable pageable);

    @Query("select a from PostBase a where a.content like ?1 and a.category.id = ?2")
    public Page<PostBase> iPageByCategory(String key, Long category, Pageable pageable);

    @Query("select a from PostBase a where a.content like ?1 and a.userInfo.id = ?2 and a.type = 3")
    public Page<PostBase> iPageMyPost(String key, Long userId, Pageable pageable);

    @Query("select a from TpPostSub a where a.postBase.id = ?1")
    public List<TpPostSub> iFindListTP(Long postId);

    @Query("select a from ZbPostInfo a where a.postBase.id = ?1")
    public List<ZbPostInfo> iFindListZB(Long postId);

    @Query("select a from PostBase a where a.userInfo.id = ?2 and a.type = 3")
    public List<PostBase> iFindListByUserId(Long userId);

    @Query("select a from PostBase a where a.category.id = ?1 and a.type = ?2 order by a.updateDate desc ")
    public List<PostBase> findByCategoryId(Long categoryId, Integer type);

    @Query("select a from PostBase a where a.createDate > ?1 and a.type = ?2")
    public List<PostBase> findListNew(Long oldDate, Integer type);

    @Query("select a from PostBase a where a.type = ?1")
    public List<PostBase> findByType(Integer type);

    @Query("select a from PostBase a where a.essence = ?1")
    public List<PostBase> findByEssence(Integer essence);

    @Query("select a from PostBase a where a.status = ?1 and a.type = ?2")
    public List<PostBase> findByStatus(Integer status, Integer type);

    @Query("select a from PostBase a where a.id in (select b.sourceId from UserCollect b where b.userInfo.id = ?1 and b.type = 4)")
    public Page<PostBase> iPageByUserCollect(Long userId, Pageable pageable);

    @Query("select a from PostBase a where a.category.id = ?1 and a.type <> 3 order by a.updateDate desc ")
    public List<PostBase> findByCategoryId(Long categoryId);
}
