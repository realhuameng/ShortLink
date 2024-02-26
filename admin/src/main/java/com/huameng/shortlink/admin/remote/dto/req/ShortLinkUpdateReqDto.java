package com.huameng.shortlink.admin.remote.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 短链接创建请求实体对象
 */
@Data
public class ShortLinkUpdateReqDto {

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组ID
     */
    private String gid;

    /**
     * 完整链接
     */
    private String fullShortUrl;

    /**
     * 有效期类型 0：永久有效 1：自定义
     */
    private Integer validDateType;

    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss", timezone = "GMT+8")
    private Date validDate;

    /**
     * 描述
     */
    private String describe;
}
