package com.huameng.shortlink.admin.remote.dto.resp;

import lombok.Data;

@Data
public class ShortLinkGroupCountQueryRespDto {

    private String gid;

    private Integer shortLinkCount;
}
