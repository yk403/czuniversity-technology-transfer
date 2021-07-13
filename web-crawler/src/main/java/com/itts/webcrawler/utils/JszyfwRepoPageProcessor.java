package com.itts.webcrawler.utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/*
 *@Auther: yukai
 *@Date: 2021/07/09/9:34
 *@Desription:
 */
public class JszyfwRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public static final String URL_LIST = "https://www\\.jszyfw\\.com\\/patent\\/(.*)\\.html";
    @Override
    public void process(Page page) {
        //匹配所有的分页url
        page.addTargetRequests(page.getHtml().$("ul.pageNumber").$("li").$("a","href").all());
        //匹配第一页的详情url
        page.addTargetRequests(page.getHtml().$("div.demand_bottom").$("ul.clearfix").$("li.hover").$("a","href").all());
        if(page.getUrl().regex(URL_LIST).match()){

        }else{
            //展品名称
            page.putField("title",page.getHtml().$("div.business_choose").$("h1","text").toString());
            //展品图片
            page.putField("zptp",page.getHtml().$("div.business_img").$("img","src").toString());
            //专利类型 有效期 发布日期 技术成熟度
            page.putField("sxmc",page.getHtml().$("ul.data-item").$("li","text").toString());
            page.putField("sxbody",page.getHtml().$("ul.data-item").$("li").$("i","text").toString());
            //交易价格
            page.putField("jyjg",page.getHtml().$("div.price_total").$("i","text").toString());
        }
        /*page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));*/
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new JszyfwRepoPageProcessor()).addUrl("https://www.jszyfw.com/patent/").thread(5).run();
    }
}
