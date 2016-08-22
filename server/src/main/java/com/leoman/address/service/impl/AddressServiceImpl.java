package com.leoman.address.service.impl;

import com.leoman.address.service.AddressService;
import com.leoman.address.dao.AddressDao;
import com.leoman.address.entity.Address;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Service
public class AddressServiceImpl extends GenericManagerImpl<Address, AddressDao> implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findByUserId(Long userId) {
        return addressDao.findByUserId(userId);
    }
}
