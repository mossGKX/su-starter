package com.cntest.su.word.model;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;

import lombok.Data;

@Data
public class Software {
  private String name;
  private String word;
  private String time;
  private String what;
  private NumbericRenderData feature;
  @Name("solution_compare")
  private MiniTableRenderData solutionCompare;
  private PictureRenderData portrait;
  private String author;
  private String introduce;
  private String header;

  public Software() {
    header = "Deeply love what you love.";
    name = "Poi-tl";
    word = "模板引擎";
    time = "2018-06-20";
    what =
        "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.";
    author = "Sayi卅一";
    introduce = "http://www.deepoove.com";
    portrait = new PictureRenderData(60, 60, "src/test/resources/sayi.png");
    solutionCompare = genSolutionCompareData();
    feature = genFeatureData();
  }

  private MiniTableRenderData genSolutionCompareData() {
    List<TextRenderData> ths = new ArrayList<>();
    ths.add(new TextRenderData("FFFFFF", "Word处理解决方案"));
    ths.add(new TextRenderData("FFFFFF", "是否跨平台"));
    ths.add(new TextRenderData("FFFFFF", "易用性"));
    RowRenderData thead = new RowRenderData(ths, "ff9800");

    List<RowRenderData> rows = new ArrayList<>();
    rows.add(RowRenderData.build("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装"));
    rows.add(RowRenderData.build("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装"));
    rows.add(RowRenderData.build("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构"));
    rows.add(RowRenderData.build("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API"));
    rows.add(RowRenderData.build("Jacob、winlib", "Windows平台", "复杂，不推荐使用"));
    return new MiniTableRenderData(thead, rows);
  }

  private NumbericRenderData genFeatureData() {
    List<TextRenderData> datas = new ArrayList<TextRenderData>();
    datas.add(new TextRenderData("Plug-in grammar, add new grammar by yourself"));
    datas.add(new TextRenderData(
        "Supports word text, local pictures, web pictures, table, list, header, footer..."));
    datas.add(new TextRenderData("Templates, not just templates, but also style templates"));
    return new NumbericRenderData(datas);
  }
}
