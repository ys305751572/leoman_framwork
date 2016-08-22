package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.PostComment;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PostCommentDao extends IBaseJpaRepository<PostComment> {

    @Query("select a from PostComment a where a.type = ?1 and a.postBase.id = ?2 and a.status = ?3 order by a.createDate desc")
    public List<PostComment> findList(Integer type, Long postId, Integer status);

    @Query("select a from PostComment a where a.type = ?1 and a.postBase.id = ?2 order by a.createDate desc")
    public List<PostComment> findList(Integer type, Long postId);

    @Query("select a from PostComment a where a.toPostComment.id = ?1 and a.status = 1")
    public List<PostComment> findList(Long postCommentId);

    @Query("select a from PostComment a where a.toUser.id = ?1 and a.status = 1")
    public List<PostComment> findListByToUserId(Long toUserId);

    @Query("select a from PostComment a where a.toUser.id = ?1 and a.status = 1")
    public Page<PostComment> pageByToUserId(Long toUserId, Pageable pageable);

    @Query("select a from PostComment a where a.fromUser.id = ?1 and a.status = 1")
    public List<PostComment> findListByFromUserId(Long fromUserId);

    @Query("select a from PostComment a where a.createDate > ?1 and a.status = ?2")
    public List<PostComment> findListNew(Long oldDate, Integer type);

    @Query("select a from PostComment a where a.postBase.id = ?1 and a.status = ?2 and a.fromUser.isCreator = ?3")
    public List<PostComment> findListByParams(Long postBaseId, Integer isComment, Integer isCreator);
}
