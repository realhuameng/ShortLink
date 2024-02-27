package com.huameng.shortlink.project.controller;

import com.huameng.shortlink.project.common.convention.result.Result;
import com.huameng.shortlink.project.common.convention.result.Results;
import com.huameng.shortlink.project.dto.req.RecycleBinSaveReqDto;
import com.huameng.shortlink.project.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 保存回收站
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/project/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDto requestParam){
        recycleBinService.saveRecycleBin(requestParam);
        return Results.success();
    }
}
