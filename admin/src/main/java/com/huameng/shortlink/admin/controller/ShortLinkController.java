package com.huameng.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huameng.shortlink.admin.common.convention.result.Result;
import com.huameng.shortlink.admin.common.convention.result.Results;
import com.huameng.shortlink.admin.remote.ShortLinkRemoteService;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkCreateReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkPageReqDto;
import com.huameng.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDto;
import com.huameng.shortlink.admin.remote.dto.resp.ShortLinkPageRespDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
public class ShortLinkController {

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    /**
     * 创建短链接
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDto> createShortLink(@RequestBody ShortLinkCreateReqDto requestParam){
        return shortLinkRemoteService.createShortLink(requestParam);
    }


    /**
     * 短链接分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDto>> pageShortLink(ShortLinkPageReqDto requestParam){
        return shortLinkRemoteService.pageShortLink(requestParam);
    }

    /**
     * 查询短链接分组内链接数量
     * @param requestParam
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDto>> listGroupShortLinkCount(List<String> requestParam){
        return shortLinkRemoteService.listGroupShortLinkCount(requestParam);

    }

    /**
     * 修改短链接相关信息
     * @param requestParam
     * @return
     */
    @PutMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDto requestParam){
        shortLinkRemoteService.updateShortLink(requestParam);
        return Results.success();
    }
}
