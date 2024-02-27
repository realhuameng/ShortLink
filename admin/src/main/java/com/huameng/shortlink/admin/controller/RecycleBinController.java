package com.huameng.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.common.convention.result.Results;
import com.huameng.shortlink.admin.dto.req.RecycleBinSaveReqDto;
import com.huameng.shortlink.admin.remote.ShortLinkRemoteService;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkPageRespDto;
import com.huameng.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 保存回收站
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDto requestParam){
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDto>> pageShortLink(ShortLinkRecycleBinPageReqDto requestParam){
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }
}
