package com.huameng.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.project.common.convention.result.Result;
import com.huameng.shortlink.project.common.convention.result.Results;
import com.huameng.shortlink.project.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.project.dto.req.ShortLinkUpdateReqDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDto;
import com.huameng.shortlink.project.dto.resp.ShortLinkPageRespDto;
import com.huameng.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/project/v1/page")
    public Result<IPage<ShortLinkPageRespDto>> pageShortLink(ShortLinkPageReqDto requestParam){
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
     * 查询短链接分组内数量
     * @return
     */
    @GetMapping("/api/short-link/project/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDto>> listGroupShortLinkCount(List<String> requestParam){
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));

    }

    /**
     * 修改短链接相关信息
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/project/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDto requestParam){
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }
}
