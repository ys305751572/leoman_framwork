package com.leoman.address.dao;

import com.leoman.address.entity.Address;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface AddressDao extends IBaseJpaRepository<Address> {

    @Query("select a from Address a where a.userInfo.id = ?1")
    public List<Address> findByUserId(Long userId);

    @Query("select a from Address a where a.userInfo.id = ?1")
    public Page<Address> pageByUserId(Long userId, Pageable pageable);

    @Query("select a from Address a where a.userInfo.id = ?1 and a.isDefault = 1")
    public Address findDefaultByUserId(Long userId);
}
