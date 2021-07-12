package com.itts.webcrawler.utils;

import com.itts.webcrawler.dao.JsspDao;
import com.itts.webcrawler.model.JsspEntity;
import com.itts.webcrawler.model.TJssp;
import com.itts.webcrawler.service.TJsspService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 *@Auther: yukai
 *@Date: 2021/07/09/9:34
 *@Desription:
 */
@Component
public class JszyfwRepoPageProcessor implements PageProcessor {
    @Autowired
    private TJsspService tJsspService;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public static String URL_LIST = "https://www\\.jszyfw\\.com\\/patent\\/(.*)\\.html";
    @SneakyThrows
    @Override
    public void process(Page page) {
        //匹配所有的分页url
        //page.addTargetRequests(page.getHtml().$("ul.pageNumber").$("li").$("a","href").all());
        //匹配第一页的详情url
        page.addTargetRequests(page.getHtml().$("div.demand_bottom").$("ul.clearfix").$("li.hover").$("a","href").all());
        if(page.getUrl().regex(URL_LIST).match()){

        }else{
            if(page.getHtml().$("div.business_choose").$("h1","text").toString()!=null){
                TJssp tJssp=new TJssp();
                //展品名称
                tJssp.setTitle(page.getHtml().$("div.business_choose").$("h1","text").toString());
                //展品图片
                tJssp.setSptp(page.getHtml().$("div.business_img").$("img","src").toString());
                //专利类型 有效期 发布日期 技术成熟度
                List<String> header=page.getHtml().$("ul.data-item").$("li", "text").all();
                List<String> body=page.getHtml().$("ul.data-item").$("li").$("i","text").all();
                //专利类型
                tJssp.setZllx(body.get(0));
                //有效期
                tJssp.setYxq(body.get(1));
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date date=null;
                if(body.get(2).length()>0){
                    date=simpleDateFormat.parse(body.get(2));
                }
                //发布日期
                tJssp.setFbrq(date);
                //技术成熟度
                tJssp.setJscsd(body.get(3));
                //交易价格
                if(page.getHtml().$("div.price_total").$("i","text").toString()!=null){
                    tJssp.setJyjg(page.getHtml().$("div.price_total").$("i","text").toString());
                }
                List<String> detailInfo=page.getHtml().$("ul.detail_info").$("li").$("span").all();
                tJssp.setSqh(detailInfo.get(1));
                tJssp.setDwmc(detailInfo.get(3));
                tJssp.setFmr(detailInfo.get(4));
                tJssp.setHylb(detailInfo.get(5));
                tJssp.setLxr(detailInfo.get(8));
                tJssp.setSplx(1);
                //jsspDao.saveJssp(jsspEntity);
                tJsspService.save(tJssp);

                System.out.println(detailInfo);
            }
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
