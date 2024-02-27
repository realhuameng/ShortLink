package com.huameng.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huameng.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

import java.util.List;

@Data
public class ShortLinkRecycleBinPageReqDto extends Page<ShortLinkDO> {

    /**
     * 分组标识集合
     */
    private List<String> gidList;
}
