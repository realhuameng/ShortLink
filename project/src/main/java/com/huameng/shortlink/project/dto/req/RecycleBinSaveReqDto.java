package com.huameng.shortlink.project.dto.req;

import lombok.Data;

/**
 * 回收站保存功能
 */
@Data
public class RecycleBinSaveReqDto {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 完整链接
     */
    private String fullShortUrl;

}
