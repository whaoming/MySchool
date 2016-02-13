package com.wxxiaomi.myschool.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Login;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Main;
import com.wxxiaomi.myschool.bean.webpage.page.OfficeElectiveCourse;
import com.wxxiaomi.myschool.bean.webpage.page.OfficeElectiveCourse.ElectiveCourseColumn;
import com.wxxiaomi.myschool.bean.webpage.page.Score;
import com.wxxiaomi.myschool.bean.webpage.page.Score.ScoreColumn;
import com.wxxiaomi.myschool.exception.OfficeException.OfficeOutTimeException;
import com.wxxiaomi.myschool.util.CommonUtil;

public class Html2ParsUtils {
	
	/**
	 * 把 选课情况 页面封装成bean
	 * @param html 选课页面html
	 * @return
	 * @throws OfficeOutTimeException
	 */
	public static OfficeElectiveCourse officeElectiveCourseHtml2Bean(String html)
			throws OfficeOutTimeException {
		OfficeElectiveCourse result = new OfficeElectiveCourse();
		Document doc = Jsoup.parse(html);
		Element tag = doc.getElementById("DBGrid");
		if (tag == null) {
			throw new OfficeOutTimeException("登录过期");
		} else {
			Elements trs = doc.getElementById("DBGrid").select("tr");
			for (int i = 1; i < trs.size(); i++) {
				Element tr = trs.get(i);
				ElectiveCourseColumn column = new ElectiveCourseColumn(tr
						.child(0).text(), tr.child(1).text(), tr.child(2)
						.child(0).text(), tr.child(3).text(), tr.child(4)
						.text(), tr.child(5).child(0).text(), tr.child(6)
						.text(), tr.child(7).text(), tr.child(8).child(0)
						.text(), tr.child(9).text(), tr.child(10).text(), tr
						.child(11).text(), tr.child(12).text(), tr.child(13)
						.text(), tr.child(14).text());
				result.getColumns().add(column);
			}
		}
		return result;
	}

	/**
	 * 把 历史成绩 页面封装成bean(bean对应score的list里面)
	 * @param score
	 * @param html
	 * @return
	 * @throws OfficeOutTimeException
	 */
	public static Score officeHistoryScoreHtml2Bean(Score score, String html)
			throws OfficeOutTimeException {
		Document doc = Jsoup.parse(html);
		Element loginTag = doc.getElementById("Datagrid1");
		if (loginTag == null) {
			throw new OfficeOutTimeException("登录过期");
		} else {
			Elements trs = doc.getElementById("Datagrid1").select("tr");
			if (trs != null) {
				for (int i = 1; i < trs.size(); i++) {
					Element element = trs.get(i);
					ScoreColumn column = new ScoreColumn(element.child(0)
							.text(), element.child(1).text(), element.child(2)
							.text(), element.child(3).text(), element.child(4)
							.text(), element.child(5).text(), element.child(6)
							.text(), element.child(7).text(), element.child(8)
							.text(), element.child(9).text(), element.child(10)
							.text(), element.child(11).text(), element
							.child(12).text(), element.child(13).text(),
							element.child(12).text());
					score.getColumns().add(column);
				}
			}
		}
		return score;
	}

	public static Score officeScoreHtmlToGetScore2Bean(String html)
			throws OfficeOutTimeException {
		Score result = new Score();
		Document doc = Jsoup.parse(html);
		/**
		 * __EVENTTARGET __EVENTARGUMENT __VIEWSTATE hidLanguage ddlXN ddlXQ
		 * ddl_kcxz btn_zcj
		 */
		Element first = doc
				.getElementsByAttributeValue("name", "__EVENTTARGET").first();
		if (first == null) {
			throw new OfficeOutTimeException("登录会话过时");
		} else{
//			String pars = "";
			try {
//				pars = "__EVENTTARGET="
//						+ doc.getElementsByAttributeValue("name",
//								"__EVENTTARGET").first().attr("value")
//						+ "&__EVENTARGUMENT="
//						+ doc.getElementsByAttributeValue("name",
//								"__EVENTARGUMENT").first().attr("value")
//						+ "&__VIEWSTATE="
//						+ URLEncoder.encode(doc.select("[name=__VIEWSTATE]")
//								.first().attr("value"), "gb2312")
//						+ "&hidLanguage="
//						+ "&ddlXN="
//						+ "&ddlXQ="
//						+ "&ddl_kcxz="
//						+ "&btn_zcj="
//						+ CommonUtil.getGBKUrl(doc.getElementById("btn_zcj")
//								.attr("value"));
				result.getGetHistoryScorePars().put(
						 "__EVENTTARGET",
						 doc.getElementsByAttributeValue("name", "__EVENTTARGET")
						 .first().attr("value"));
						 result.getGetHistoryScorePars().put(
						 "__EVENTARGUMENT",
						 doc.getElementsByAttributeValue("name", "__EVENTARGUMENT")
						 .first().attr("value"));
						 result.getGetHistoryScorePars().put("__VIEWSTATE",
								 URLEncoder.encode(doc.select("[name=__VIEWSTATE]")
											.first().attr("value"), "gb2312"));
						 result.getGetHistoryScorePars().put("hidLanguage", "");
						 result.getGetHistoryScorePars().put("ddlXN", "");
						 result.getGetHistoryScorePars().put("ddlXQ", "");
						 result.getGetHistoryScorePars().put("ddl_kcxz", "");
						 result.getGetHistoryScorePars()
						 .put("btn_zcj",
						 CommonUtil.getGBKUrl(doc.getElementById("btn_zcj").attr(
						 "value")));
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			result.setPars(pars);

			// result.getGetHistoryScorePars().put(
			// "__EVENTTARGET",
			// doc.getElementsByAttributeValue("name", "__EVENTTARGET")
			// .first().attr("value"));
			// result.getGetHistoryScorePars().put(
			// "__EVENTARGUMENT",
			// doc.getElementsByAttributeValue("name", "__EVENTARGUMENT")
			// .first().attr("value"));
			// result.getGetHistoryScorePars().put("__VIEWSTATE",
			// (doc.select("[name=__VIEWSTATE]").first().attr("value")));
			// result.getGetHistoryScorePars().put("hidLanguage", "");
			// result.getGetHistoryScorePars().put("ddlXN", "");
			// result.getGetHistoryScorePars().put("ddlXQ", "");
			// result.getGetHistoryScorePars().put("ddl_kcxz", "");
			// result.getGetHistoryScorePars()
			// .put("btn_zcj",
			// CommonUtil.getGBKUrl(doc.getElementById("btn_zcj").attr(
			// "value")));
			// result.set__EVENTTARGET(doc.getElementsByAttributeValue("name",
			// "__EVENTTARGET").first().attr("value"));
			// result.set__EVENTARGUMENT(doc.getElementsByAttributeValue("name",
			// "__EVENTARGUMENT").first().attr("value"));
//			 result.set__VIEWSTATE( URLEncoder.encode(doc.select("[name=__VIEWSTATE]")
//			.first().attr("value"), "gb2312"));
			// result.setHidLanguage("");
			// result.setDdl_kcxz("");
			// result.setDdlXN("");
			// result.setDdlXQ("");
			// result.setBtn_zcj(CommUtils.getGBKUrl(doc.getElementById("btn_zcj").attr("value")));
		}

		return result;
	}

	public static Html_Main officeMainHtml2Bean(String html) {
		Html_Main main = new Html_Main();
		Document doc = Jsoup.parse(html);
		if (doc.getElementById("xhxm") == null) {
			// throw new LoginException("登录失败");
		} else {
			// 设置获取课表的url
			main.setClassFormUrl(ConstantValue.tempOfficeUrl
					+ CommonUtil.getGBKUrl(doc.select(":containsOwn(专业推荐课表查询)")
							.first().attr("href")));

			// 设置获取个人信息的url
			main.setPersonalInfoUrl(ConstantValue.tempOfficeUrl
					+ CommonUtil.getGBKUrl(doc.select(":containsOwn(个人信息)")
							.first().attr("href")));

			// 设置获取选课情况的url
			main.setElectiveCourseUrl(ConstantValue.tempOfficeUrl
					+ CommonUtil.getGBKUrl(doc.select(":containsOwn(学生选课情况查询)")
							.first().attr("href")));

			// 设置获取成绩的url
			main.setScoreUrl(ConstantValue.tempOfficeUrl
					+ CommonUtil.getGBKUrl(doc.select(":containsOwn(成绩查询)")
							.first().attr("href")));

			// 获取main首页的信息
			main.setNumberAndName(doc.getElementById("xhxm").text());
		}

		return main;
	}

	public static Html_Login officeLoginHtml2Bean(String html){
		Html_Login result = new Html_Login();
		Document doc = Jsoup.parse(html);
		if(doc!=null){
			if(doc.select("[name=form1]") != null){
				result.setLoginUrl(doc.select("[name=form1]").first().attr("action"));

				String __VIEWSTATE = doc.select("[name=__VIEWSTATE]").first()
						.attr("value");
				result.getLoginPars().put("__VIEWSTATE", __VIEWSTATE);
				result.getLoginPars().put("RadioButtonList1", "%D1%A7%C9%FA");
				result.getLoginPars().put("Button1", "");
				result.getLoginPars().put("lbLanguage", "");
				result.getLoginPars().put("hidPdrs", "");
				result.getLoginPars().put("hidsc", "");
				result.getLoginPars().put("txtSecretCode", "");
				return result;
			}
		}
		return null;
	}
}
