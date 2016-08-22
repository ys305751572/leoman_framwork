package com.leoman.address.service;

import com.leoman.address.entity.Address;
import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface AddressService extends GenericManager<Address>{

    public List<Address> findByUserId(Long userId);
}
