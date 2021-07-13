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
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(1000000);
    public static String URL_LIST = "https://www\\.jszyfw\\.com\\/patent\\/(.*)\\.html";
    public static String URL_LIST_Detail= "https://www\\.jszyfw\\.com\\/detail\\/(.*)\\.html";
    public static String URL_LIST_ART= "https://www\\.jszyfw\\.com\\/art\\/(.*)\\.html";
    @SneakyThrows
    @Override
    public void process(Page page) {
        //匹配所有的技术需求
        page.addTargetRequests(page.getHtml().$("a.nav_sub_2_1","href").all());
        //匹配所有的分页url
        page.addTargetRequests(page.getHtml().$("ul.pageNumber").$("li").$("a","href").all());
        //匹配第一页的详情url
        page.addTargetRequests(page.getHtml().$("div.demand_bottom").$("ul.clearfix").$("li").$("a","href").all());
        //技术转让列表页
        if(page.getUrl().regex(URL_LIST).match()){
            //技术转让详情
        }else if(page.getUrl().regex(URL_LIST_Detail).match()){
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
                List<String> detailInfo=page.getHtml().$("ul.detail_info").$("li").$("span","text").all();
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
            //技术需求
        }else if(page.getUrl().regex(URL_LIST_ART).match()){
            //判断是否为列表页
                    if(page.getUrl().toString().indexOf("list")!=-1){
                        //否则为详情页
                    }else{
                        if(page.getHtml().$("div.details_top").$("h1","text").toString()!=null){
                            TJssp tJssp=new TJssp();
                            tJssp.setTitle(page.getHtml().$("div.details_top").$("h1","text").toString());
                            List<String> all=page.getHtml().$("div.details_top").$("dl").$("dd").$("i","text").all();
                            if(all!=null){
                                if(all.size()>0){
                                    tJssp.setXqlx(all.get(0));
                                    tJssp.setSplx(0);
                                    tJssp.setHylb(all.get(1));
                                    tJssp.setYxq(all.get(2));
                                    tJssp.setDwxz(all.get(3));
                                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                                    Date date=null;
                                    if(all.get(4).length()>0){
                                        date=simpleDateFormat.parse(all.get(4));
                                    }
                                    tJssp.setFbrq(date);
                                    tJssp.setLxr(all.get(5));
                                    tJssp.setJyjg(page.getHtml().$("div.details_price").$("p").$("i","text").toString());
                                    List<String> xqList=page.getHtml().$("div.details_bottom_left").$("ul").$("li").$("div.content").$("p","text").all();
                                    if(xqList!=null){
                                        if(xqList.size()>0){
                                            tJssp.setXqxq(xqList.get(0));
                                            tJssp.setNrmb(xqList.get(1));
                                            tJsspService.save(tJssp);
                                        }

                                    }
                                }
                            }
                        }
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
