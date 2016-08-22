package com.leoman.userapi.service.impl;

import com.leoman.address.dao.AddressDao;
import com.leoman.address.entity.Address;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.exception.AddressErrorException;
import com.leoman.exception.UserAddressErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.userapi.service.AddressServiceApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class AddressServiceApiImpl extends GenericManagerImpl<Address, AddressDao> implements AddressServiceApi {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AddressDao addressDao;

    @Override
    public Page<Address> iPageByUserId(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException {
        UserLoginServiceApiImpl.getUserInfo(userId, userInfoDao);

        return addressDao.pageByUserId(userId, new PageRequest(pageNum - 1, pageSize));
    }

    @Override
    @Transactional
    public void iSaveInfo(Long userId, Long id, String name, String mobile, String addressInfo, Integer isDefault) throws UserNotFindException {
        UserInfo userInfo = UserLoginServiceApiImpl.getUserInfo(userId, userInfoDao);

        Address defaultAddress = addressDao.findDefaultByUserId(userId);
        Address address = null;
        if (null == id) {
            address = new Address();
        } else {
            address = addressDao.findOne(id);
        }

        address.setUserInfo(userInfo);

        if (StringUtils.isNotEmpty(name)) {
            address.setName(name);
        }

        if (StringUtils.isNotEmpty(mobile)) {
            address.setMobile(mobile);
        }

        if (StringUtils.isNotEmpty(addressInfo)) {
            address.setAddress(addressInfo);
        }

        if (null == defaultAddress) {
            address.setIsDefault(1);
        } else {
            if (null != isDefault && isDefault == 1) {
                defaultAddress.setIsDefault(0);
                addressDao.save(defaultAddress);

                address.setIsDefault(1);
            } else {
                address.setIsDefault(0);
            }
        }

        addressDao.save(address);
    }

    @Override
    public void iDelInfo(Long userId, Long id) throws AddressErrorException, UserNotFindException, UserAddressErrorException {
        UserLoginServiceApiImpl.getUserInfo(userId, userInfoDao);

        Address address = addressDao.findOne(id);
        if (null == address) {
            throw new AddressErrorException();
        }

        if (address.getUserInfo().getId() != userId) {
            throw new UserAddressErrorException();
        }

        addressDao.delete(address);
    }
}
