package com.huameng.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class ShortLinkRecycleBinPageReqDto extends Page {

    /**
     * 分组标识集合
     */
    private List<String> gidList;
}
