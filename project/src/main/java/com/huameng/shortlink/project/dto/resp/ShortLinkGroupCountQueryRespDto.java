package com.huameng.shortlink.project.dto.resp;

import lombok.Data;

@Data
public class ShortLinkGroupCountQueryRespDto {

    private String gid;

    private Integer shortLinkCount;
}
