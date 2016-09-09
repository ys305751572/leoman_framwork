package com.leoman.image.service.impl;

import com.leoman.common.service.AbstractUploadService;
import com.leoman.entity.Configue;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.image.entity.FileBo;
import com.leoman.image.entity.Image;
import com.leoman.image.service.ImageService;
import com.leoman.image.service.UploadImageService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangbin on 2014/12/7.
 */
@Service
@Transactional(readOnly = true)
public class UploadImageServiceImpl extends AbstractUploadService implements UploadImageService {

    @Autowired
    private ImageService imageService;

    @Override
    public FileBo saveImage(MultipartFile file) {
        if (!FileUtil.isImage(file.getOriginalFilename())) {
            GeneralExceptionHandler.handle("文件格式不正确，请上传图片!");
        }
        FileBo result = null;
        try {
            result = super.save(file);
        } catch (IOException e) {
            GeneralExceptionHandler.handle("上传失败,服务器繁忙!");
        }
        return result;
    }

    @Override
    public Image uploadImage(MultipartFile file, String... thumbSizes) {

        return null;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            FileBo fileBo = super.save(file);
            if (fileBo != null) {
                return fileBo.getPath();
            }
            return null;
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
        }
        return null;
    }

    @Override
    @Transactional
    public List<Image> uploadImages(MultipartFile[] files) {
        List<Image> images = new ArrayList<Image>();
        for (MultipartFile file : files) {
            Image image = uploadImage(file);
            imageService.create(image);
            images.add(image);
        }
        return images;
    }


}
