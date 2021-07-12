package com.itts.webcrawler.utils;

import com.itts.webcrawler.dao.JsspDao;
import com.itts.webcrawler.model.JsspEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JszyfwRepoPageProcessor implements PageProcessor {
    @Autowired
    private JsspDao jsspDao;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public static final String URL_LIST = "https://www\\.jszyfw\\.com\\/patent\\/(.*)\\.html";
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
                JsspEntity jsspEntity=new JsspEntity();
                //展品名称
                jsspEntity.setTitle(page.getHtml().$("div.business_choose").$("h1","text").toString());
                //展品图片
                jsspEntity.setZptp(page.getHtml().$("div.business_img").$("img","src").toString());
                //专利类型 有效期 发布日期 技术成熟度
                List<String> header=page.getHtml().$("ul.data-item").$("li", "text").all();
                List<String> body=page.getHtml().$("ul.data-item").$("li").$("i","text").all();
                //专利类型
                jsspEntity.setZllx(body.get(0));
                //有效期
                jsspEntity.setYxq(body.get(1));
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date date=null;
                if(body.get(2).length()>0){
                    date=simpleDateFormat.parse(body.get(2));
                }
                //发布日期
                jsspEntity.setFbrq(date);
                //技术成熟度
                jsspEntity.setJscsd(body.get(3));
                //交易价格
                if(page.getHtml().$("div.price_total").$("i","text").toString()!=null){
                    jsspEntity.setJyjg(page.getHtml().$("div.price_total").$("i","text").toString());
                }
                List<String> detailInfo=page.getHtml().$("ul.detail_info").$("li").$("span").all();
                jsspEntity.setSqh(detailInfo.get(1));
                jsspEntity.setDwmc(detailInfo.get(3));
                jsspEntity.setFmr(detailInfo.get(4));
                jsspEntity.setHylb(detailInfo.get(5));
                jsspEntity.setLxr(detailInfo.get(8));
                //jsspDao.saveJssp(jsspEntity);
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
