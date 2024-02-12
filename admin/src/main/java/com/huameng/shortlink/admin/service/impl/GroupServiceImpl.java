package com.huameng.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huameng.shortlink.admin.dao.entity.GroupDO;
import com.huameng.shortlink.admin.dao.mapper.GroupMapper;
import com.huameng.shortlink.admin.service.GroupService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短链接分组接口实现层
 */
@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

}
