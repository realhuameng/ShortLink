package com.huameng.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * 短链接分组请求参数
 */
@Data
public class ShortLinkPageReqDto extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private String gid;
}
