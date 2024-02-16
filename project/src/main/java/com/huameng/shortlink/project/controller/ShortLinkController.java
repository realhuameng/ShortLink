package com.huameng.shortlink.project.controller;

import com.huameng.shortlink.project.common.convention.result.Result;
import com.huameng.shortlink.project.common.convention.result.Results;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    /**
     * 创建短链接
     * @return
     */
    @PostMapping("/api/short-link/project/v1/create")
    public Result<ShortLinkCreateRespDto> createShortLink(@RequestBody ShortLinkCreateReqDto requestParam){
        return Results.success(shortLinkService.createShortLink(requestParam));

    }
}
