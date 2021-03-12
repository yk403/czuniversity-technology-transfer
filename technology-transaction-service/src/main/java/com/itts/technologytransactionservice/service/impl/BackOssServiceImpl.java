package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.technologytransactionservice.mapper.BackOssMapper;
import com.itts.technologytransactionservice.model.BackOss;
import com.itts.technologytransactionservice.service.BackOssService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2020-06-19
 */
@Service
@Primary
public class BackOssServiceImpl extends ServiceImpl<BackOssMapper, BackOss> implements BackOssService {

}
