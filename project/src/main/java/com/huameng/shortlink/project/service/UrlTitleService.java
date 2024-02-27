package com.huameng.shortlink.project.service;

/**
 * URL获取标题接口层
 */
public interface UrlTitleService {

    /**
     * 根据URL获取链接标题
     * @param url
     * @return
     */
    String getTitleByUrl(String url);

}
