package com.wxxiaomi.myschool.engine;

import java.net.URLEncoder;
import java.util.List;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.bean.office.ElectiveCourseColumn;
import com.wxxiaomi.myschool.bean.office.Score.ScoreColumn;
import com.wxxiaomi.myschool.bean.office.format.common.OfficeReceiveData;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Login;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Main;
import com.wxxiaomi.myschool.bean.webpage.page.OfficeElectiveCourse;
import com.wxxiaomi.myschool.bean.webpage.page.Score;
import com.wxxiaomi.myschool.bean.webpage.request.NetReceiverData;
import com.wxxiaomi.myschool.bean.webpage.request.NetSendData;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.exception.OfficeException.OfficeOutTimeException;
import com.wxxiaomi.myschool.net.Html2ParsUtils;
import com.wxxiaomi.myschool.net.HttpClientUtil;
import com.wxxiaomi.myschool.net.MyHttpWebUtil;

public class OfficeEngineImpl {
	
	public OfficeReceiveData<List<ElectiveCourseColumn>> getElective(){
		String url = ConstantValue.LOTTERY_URI
				+ "/OfficeServlet?action=getelective";
		String pars;
		try {
			pars = "&tempUrl=" + GlobalParams.gloUserInfo.tempUrl
					+ "&username="
					+ GlobalParams.gloUserInfo.userInfo.officeUserInfo.username
					+ "&password="
					+ GlobalParams.gloUserInfo.userInfo.officeUserInfo.password
					+ "&xm=" + URLEncoder.encode("王浩明", "utf-8");
			String json = HttpClientUtil.doPost(url, pars);
			Log.i("wang", "获取选课情况返回的json=" + json);
			// OfficeReceiveData<> d
			Gson gson = new Gson();
			OfficeReceiveData<List<ElectiveCourseColumn>> fromJson = gson.fromJson(json,
					new TypeToken<OfficeReceiveData<List<ElectiveCourseColumn>>>() {
					}.getType());
			return fromJson;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从服务器获取成绩
	 * @return
	 */
	public OfficeReceiveData<List<ScoreColumn>> getScoreFromServer() {
		// OfficeReceiveData<com.wxxiaomi.myschool.bean.office.Score> result =
		// new OfficeReceiveData<>();
		String url = ConstantValue.LOTTERY_URI
				+ "/OfficeServlet?action=getscore";
		String pars;
		try {
			pars = "&tempUrl=" + GlobalParams.gloUserInfo.tempUrl
					+ "&username="
					+ GlobalParams.gloUserInfo.userInfo.officeUserInfo.username
					+ "&password="
					+ GlobalParams.gloUserInfo.userInfo.officeUserInfo.password
					+ "&xm=" + URLEncoder.encode("王浩明", "utf-8");
			String json = HttpClientUtil.doPost(url, pars);
//			Log.i("wang", "获取成绩返回的json=" + json);
			// OfficeReceiveData<> d
			Gson gson = new Gson();
			OfficeReceiveData<List<ScoreColumn>> fromJson = gson.fromJson(json,
					new TypeToken<OfficeReceiveData<List<ScoreColumn>>>() {
					}.getType());
			return fromJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化';32
		return null;
	}
	
//	public OfficeReceiveData<List<ScoreColumn>>

	/**
	 * 获取正方系统主页面
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public ResponseData<Html_Main> getOfficeMainHtml2BeanByOne(String username,
			String password) {
		ResponseData<Html_Main> result = new ResponseData<Html_Main>();
		Html_Login officeLoginHtml2Bean = getOfficeLoginHtml2Bean();
		if (officeLoginHtml2Bean != null) {
			// 获取失败
			NetSendData firstData = new NetSendData();
			firstData.setUrl(officeLoginHtml2Bean.getLoginUrl());
			officeLoginHtml2Bean.getLoginPars().put("txtUserName", username);
			officeLoginHtml2Bean.getLoginPars().put("TextBox2", password);
			firstData.setParmars(officeLoginHtml2Bean.getLoginPars());
			firstData.getHeaders()
					.put("Referer", officeLoginHtml2Bean.getUrl());
			firstData.setHost(ConstantValue.Host);
			NetReceiverData sendPost = MyHttpWebUtil.sendPost(firstData);
			// Log.i("wang",
			// "getOfficeMainHtml2BeanByOne()中获取到正确的登录地址后，再封装参数，发送。获得的内容是"+sendPost.getContent2String());
			// officeMainHtml2Bean;
			// try {
			Html_Main officeMainHtml2Bean = Html2ParsUtils
					.officeMainHtml2Bean(sendPost.getContent2String());
			officeMainHtml2Bean.setUsername(username);
			officeMainHtml2Bean.setPassword(password);
			result.setObj(officeMainHtml2Bean);
			result.getObj().setFromUrl(sendPost.getFromUrl());
			result.setSuccess(true);
			// } catch (LoginException e) {
			// result.setSuccess(false);
			// result.setError(e.getMessage());
			// }
		} else {
			result.setSuccess(false);
		}

		return result;
	}

	//
	public ResponseData<Score> getScore2Bean(String url, String refererUrl) {
		ResponseData<Score> result = new ResponseData<Score>();
		NetSendData firstData = new NetSendData();
		firstData.setUrl(url);
		firstData.getHeaders().put("Referer", refererUrl);
		NetReceiverData sendGet = MyHttpWebUtil.sendGet(firstData);
		// Log.i("wang",
		// "获取成绩bean中,sendGet的content="+sendGet.getContent2String());
		try {
			Score officeScoreHtml2Bean = Html2ParsUtils
					.officeScoreHtmlToGetScore2Bean(sendGet.getContent2String());
			// 上面是第一次get取得的页面实体，里面封装有参数
			// ------------------------------------------------------
			NetSendData sencondData = new NetSendData();
			sencondData.setUrl(url);
			sencondData.setPars(officeScoreHtml2Bean.getPars());
			sencondData.getParmars().putAll(
					officeScoreHtml2Bean.getGetHistoryScorePars());
			// Log.i("wang", "获取历史成绩的url=" + url);
			sencondData.getHeaders().put("Referer", url);
			// sencondData.setPars("__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=dDw1OTcxMjMxNzI7dDxwPGw8U29ydEV4cHJlcztzZmRjYms7ZGczO2R5YnlzY2o7U29ydERpcmU7eGg7c3RyX3RhYl9iamc7Y2pjeF9sc2I7enhjamN4eHM7PjtsPGtjbWM7XGU7YmpnO1xlO2FzYzsxMzExMTAxOTk7emZfY3hjanRqXzEzMTExMDE5OTs7MTs%2BPjtsPGk8MT47PjtsPHQ8O2w8aTw0PjtpPDEwPjtpPDE5PjtpPDI0PjtpPDMyPjtpPDM0PjtpPDM2PjtpPDM4PjtpPDQwPjtpPDQyPjtpPDQ0PjtpPDQ2PjtpPDQ4PjtpPDUwPjs%2BO2w8dDx0PDt0PGk8MTY%2BO0A8XGU7MjAwMS0yMDAyOzIwMDItMjAwMzsyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2Oz4%2BOz47Oz47dDx0PHA8cDxsPERhdGFUZXh0RmllbGQ7RGF0YVZhbHVlRmllbGQ7PjtsPGtjeHptYztrY3h6ZG07Pj47Pjt0PGk8Nz47QDzlhazlhbHlv4Xkv6475LiT5Lia5b%2BF5L%2BuO%2BS4k%2BS4mumZkOmAiTvkuJPkuJrku7vpgIk75YWs5YWx6ZmQ6YCJO%2BWFrOWFseS7u%2BmAiTtcZTs%2BO0A8MDE7MDI7MDM7MDQ7MDU7MDY7XGU7Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPFxlOz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWtpuWPt%2B%2B8mjEzMTExMDE5OTtvPHQ%2BOz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWnk%2BWQje%2B8mueOi%2Ba1qeaYjjtvPHQ%2BOz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOWtpumZou%2B8muiuoeeul%2BacuuWtpumZojtvPHQ%2BOz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOS4k%2BS4mu%2B8mjtvPHQ%2BOz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOe9kee7nOW3peeoiztvPHQ%2BOz4%2BOz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrmlrnlkJE6Oz4%2BOz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOihjOaUv%2BePre%2B8muiuoeeul%2BacujEzMDXnj607bzx0Pjs%2BPjs%2BOzs%2BO3Q8O2w8aTwxPjtpPDM%2BO2k8NT47aTw3PjtpPDk%2BO2k8MTE%2BO2k8MTM%2BO2k8MTU%2BO2k8MTc%2BO2k8MTg%2BO2k8MTk%2BO2k8MjE%2BO2k8MjM%2BO2k8MjU%2BO2k8Mjc%2BO2k8Mjk%2BO2k8MzE%2BO2k8MzM%2BO2k8MzU%2BO2k8MzY%2BOz47bDx0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4%2BOzs7Ozs7Ozs7Oz47Oz47dDw7bDxpPDEzPjs%2BO2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs%2BPjt0PHA8cDxsPFRleHQ7VmlzaWJsZTs%2BO2w86Iez5LuK5pyq6YCa6L%2BH6K%2B%2B56iL5oiQ57up77yaO288dD47Pj47Pjs7Pjt0PEAwPHA8cDxsPFBhZ2VDb3VudDtfIUl0ZW1Db3VudDtfIURhdGFTb3VyY2VJdGVtQ291bnQ7RGF0YUtleXM7PjtsPGk8MT47aTwxPjtpPDE%2BO2w8Pjs%2BPjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6YmxvY2s7Pj4%2BOzs7Ozs7Ozs7Oz47bDxpPDA%2BOz47bDx0PDtsPGk8MT47PjtsPHQ8O2w8aTwwPjtpPDE%2BO2k8Mj47aTwzPjtpPDQ%2BO2k8NT47PjtsPHQ8cDxwPGw8VGV4dDs%2BO2w8ODgwMTIxODU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPFhIVE1M5YWl6Zeo5LiO5a6e5L6L5ryU57uDOz4%2BOz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlhazlhbHku7vpgIk7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIuMDs%2BPjs%2BOzs%2BO3Q8cDxwPGw8VGV4dDs%2BO2w8MzY7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiHqueEtuenkeWtpjs%2BPjs%2BOzs%2BOz4%2BOz4%2BOz4%2BO3Q8QDA8cDxwPGw8VmlzaWJsZTs%2BO2w8bzxmPjs%2BPjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs%2BPj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4%2BOzs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47cDxsPHN0eWxlOz47bDxESVNQTEFZOm5vbmU7Pj4%2BOzs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY%2BOz4%2BO3A8bDxzdHlsZTs%2BO2w8RElTUExBWTpub25lOz4%2BPjs7Ozs7Ozs7Ozs%2BOzs%2BO3Q8QDA8cDxwPGw8VmlzaWJsZTs%2BO2w8bzxmPjs%2BPjs%2BOzs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY%2BOz4%2BO3A8bDxzdHlsZTs%2BO2w8RElTUExBWTpub25lOz4%2BPjs7Ozs7Ozs7Ozs%2BOzs%2BO3Q8QDA8cDxwPGw8VmlzaWJsZTs%2BO2w8bzxmPjs%2BPjtwPGw8c3R5bGU7PjtsPERJU1BMQVk6bm9uZTs%2BPj47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDtAMDw7O0AwPHA8bDxIZWFkZXJUZXh0Oz47bDzliJvmlrDlhoXlrrk7Pj47Ozs7PjtAMDxwPGw8SGVhZGVyVGV4dDs%2BO2w85Yib5paw5a2m5YiGOz4%2BOzs7Oz47QDA8cDxsPEhlYWRlclRleHQ7PjtsPOWIm%2BaWsOasoeaVsDs%2BPjs7Ozs%2BOzs7Pjs7Ozs7Ozs7Oz47Oz47dDxwPHA8bDxUZXh0O1Zpc2libGU7PjtsPOacrOS4k%2BS4muWFsTk25Lq6O288Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPEpZVTs%2BPjs%2BOzs%2BO3Q8cDxwPGw8SW1hZ2VVcmw7PjtsPC4vZXhjZWwvMTMxMTEwMTk5LmpwZzs%2BPjs%2BOzs%2BOz4%2BO3Q8O2w8aTwzPjs%2BO2w8dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs%2BPjs%2BPjs%2BPjs%2BqOlDC2fB0QmSDzgokYxLzIUDJlg%3D&hidLanguage=&ddlXN=&ddlXQ=&ddl_kcxz=&btn_zcj=%C0%FA%C4%EA%B3%C9%BC%A8");

			NetReceiverData sendPost = MyHttpWebUtil.sendPost(sencondData);

			try {
				// Log.i("wang",
				// "sendPost.getContent2String()="
				// + sendPost.getContent2String());
				Score officeScoreHtml2Bean2 = Html2ParsUtils
						.officeHistoryScoreHtml2Bean(officeScoreHtml2Bean,
								sendPost.getContent2String());
				result.setObj(officeScoreHtml2Bean2);
				result.setSuccess(true);
			} catch (OfficeOutTimeException e) {
				// e.printStackTrace();
				result.setSuccess(false);
				result.setError(e.getMessage());
			}
		} catch (OfficeOutTimeException e1) {
			result.setError(e1.getMessage());
			result.setSuccess(false);
		}

		return result;
	}

	/**
	 * 获取正方系统登录页面html对应的bean
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Html_Login getOfficeLoginHtml2Bean() {
		NetSendData firstData = new NetSendData();
		firstData.setUrl(ConstantValue.Host);
		firstData.setHost(ConstantValue.Host);
		NetReceiverData sendPost = MyHttpWebUtil.sendPost(firstData);
		Html_Login item = Html2ParsUtils.officeLoginHtml2Bean(new String(
				sendPost.getContent()));
		item.setTempUrl(sendPost.getFromUrl()
				.replaceAll(item.getLoginUrl(), ""));
		item.setLoginUrl(item.getTempUrl() + item.getLoginUrl());
		ConstantValue.tempOfficeUrl = item.getTempUrl();
		if (item != null) {
			item.setUrl(sendPost.getFromUrl());
		}
		return item;
	}

	public ResponseData<OfficeElectiveCourse> getOfficeElectiveCourse2Bean(
			String electiveCourseUrl, String refererUrl) {
		ResponseData<OfficeElectiveCourse> result = new ResponseData<OfficeElectiveCourse>();
		NetSendData firstData = new NetSendData();
		firstData.setUrl(electiveCourseUrl);
		firstData.getHeaders().put("Referer", refererUrl);
		NetReceiverData sendGet = MyHttpWebUtil.sendGet(firstData);
		try {
			OfficeElectiveCourse officeElectiveCourseHtml2Bean = Html2ParsUtils
					.officeElectiveCourseHtml2Bean(sendGet.getContent2String());
			result.setObj(officeElectiveCourseHtml2Bean);
			result.setSuccess(true);
		} catch (OfficeOutTimeException e) {
			// 登录会话过期了
			// e.printStackTrace();
			// Log.i("wang", "e.getMessage()="+e.getMessage());
			result.setSuccess(false);
			result.setError(e.getMessage());
		}
		return result;
	}
}
