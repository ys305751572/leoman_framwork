package com.leoman.userapi.service;

import com.leoman.address.entity.Address;
import com.leoman.common.service.GenericManager;
import com.leoman.exception.AddressErrorException;
import com.leoman.exception.UserAddressErrorException;
import com.leoman.exception.UserNotFindException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface AddressServiceApi extends GenericManager<Address> {

    public Page<Address> iPageByUserId(Long userId, Integer pageNum, Integer pageSize) throws UserNotFindException;

    public void iSaveInfo(Long userId, Long id, String name, String mobile, String address, Integer isDefault) throws UserNotFindException;

    public void iDelInfo(Long userId, Long id) throws UserNotFindException, AddressErrorException, UserAddressErrorException;
}
