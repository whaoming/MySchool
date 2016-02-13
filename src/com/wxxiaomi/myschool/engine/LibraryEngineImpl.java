package com.wxxiaomi.myschool.engine;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.bean.lib.LibInfomation;
import com.wxxiaomi.myschool.bean.lib.LibInfomation.Behave;
import com.wxxiaomi.myschool.bean.lib.format.R_LibBorrowState;
import com.wxxiaomi.myschool.bean.lib.format.R_LibLoginCode;
import com.wxxiaomi.myschool.bean.lib.format.R_LibMain;
import com.wxxiaomi.myschool.bean.lib.format.common.LibReceiverData;
import com.wxxiaomi.myschool.net.HttpClientUtil;


public class LibraryEngineImpl {
	
	public LibReceiverData<R_LibBorrowState> getBorrowStateByWeb(){
		String url= ConstantValue.LOTTERY_URI+"/LibServlet?action=getborrowstate";
		Behave behave = GlobalParams.libInfo.behaves.get("getborrowstate");
		String cookie = GlobalParams.libInfo.cookie;
		String url1 = behave.url;
		String pars = "url="+url1+"&cookie="+cookie;
		String json = HttpClientUtil.doPost(url,pars);
		try {
			Gson gson = new Gson();
			Log.i("wang", "获取BorrowState的json="+json);
			LibReceiverData<R_LibBorrowState> fromJson = gson.fromJson(json, new TypeToken<LibReceiverData<R_LibBorrowState>>(){}.getType());
			return fromJson;
		}catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
		
	}
	
	/**
	 * 登录操作，从web服务器接收回复的response
	 * @param username
	 * @param password
	 * @param input
	 * @param cookie
	 */
	public LibReceiverData<R_LibMain> LoginFromServer1(String username, String password, String input, String cookie){
		String url= ConstantValue.LOTTERY_URI+"/LibServlet?action=checkLib";
		String pars = "username="+username+"&password="+password+"&code="+input+"&cookie="+cookie;
		String json = HttpClientUtil.doPost(url, pars);
		try {
			Gson gson = new Gson();
			LibReceiverData<R_LibMain> fromJson = gson.fromJson(json,new TypeToken<LibReceiverData<R_LibMain>>(){}.getType());
			if(fromJson.state == 200){
				LibInfomation libInfo = new LibInfomation();
				libInfo.behaves = fromJson.infos.behaves;
				libInfo.cookie = fromJson.cookie;
				libInfo.username = username;
				libInfo.password = password;
				libInfo.userinfo = fromJson.infos.userinfo;
				GlobalParams.libInfo = libInfo;
			}
			return fromJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
	}
	/**
	 * 登录操作，从web服务器接收回复的response
	 * @param username
	 * @param password
	 * @param input
	 * @param cookie
	 */
	public R_LibMain LoginFromServer(String username, String password, String input, String cookie){
		String url= ConstantValue.LOTTERY_URI+"/LibServlet?action=checkLib";
		String pars = "username="+username+"&password="+password+"&code="+input+"&cookie="+cookie;
		String json = HttpClientUtil.doPost(url, pars);
		try {
			Gson gson = new Gson();
			R_LibMain fromJson = gson.fromJson(json, R_LibMain.class);
//			if(fromJson.success == 1){
				LibInfomation libInfo = new LibInfomation();
				libInfo.behaves = fromJson.behaves;
//				libInfo.cookie = fromJson.cookie;
				libInfo.username = username;
				libInfo.password = password;
				libInfo.userinfo = fromJson.userinfo;
				GlobalParams.libInfo = libInfo;
//			}
			return fromJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
	}
	
	/**
	 * 连接javaweb服务器获取验证码和cookie，封装成实体返回
	 * @return
	 */
	public LibReceiverData<String> getPicCodeAndCookieFromServer1(){
//		String url = ConstantValue.LOTTERY_URI
//				.concat(ConstantValue.GETLOSTFOUNDITEMLIST);
		String url= ConstantValue.LOTTERY_URI+"/LibServlet?action=getpiccode";
		String json = HttpClientUtil.doGet(url);
		try {
			Gson gson = new Gson();
//			Log.i("wang", "获取图片code的json="+json);
			LibReceiverData<String> result = gson.fromJson(json, new TypeToken<LibReceiverData<String>>(){}.getType());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
	}
	
	/**
	 * 连接javaweb服务器获取验证码和cookie，封装成实体返回
	 * @return
	 */
	public R_LibLoginCode getPicCodeAndCookieFromServer(){
//		String url = ConstantValue.LOTTERY_URI
//				.concat(ConstantValue.GETLOSTFOUNDITEMLIST);
		String url= ConstantValue.LOTTERY_URI+"/LibServlet?action=getpiccode";
		String json = HttpClientUtil.doGet(url);
		try {
			Gson gson = new Gson();
//			Log.i("wang", "获取图片code的json="+json);
			R_LibLoginCode r_libLoginCOde = gson.fromJson(json, R_LibLoginCode.class);
			return r_libLoginCOde;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
	}
	
//	public Html_Lib_BookInfoDetail BookInfo2Bean(String html){
//		Html_Lib_BookInfoDetail result = new Html_Lib_BookInfoDetail();
//		BookInfo info = new BookInfo();
//		Document doc = Jsoup.parse(html);
//		Element span = doc.getElementById("ctl00_ContentPlaceHolder1_bookcardinfolbl");
//		if(span != null){
//			info.setDetail(span.text());
//			Element tbody = doc.select("table.tb tbody").first();
//			if(tbody != null){
//				for(Element tr : tbody.children()){
//					CollectState location = new CollectState(tr.child(0).child(0).text()
//							, tr.child(1).text(), tr.child(2).text(), tr.child(3).text()
//							, tr.child(4).text(), tr.child(5).text(), tr.child(6).text()
//							);
//					info.getCollecLocations().add(location);
//				}	
//			}
//			result.setBookInfo(info);
//		}else{
//			Log.i("wang", "span == null");
//		}
//		
//		return result;
//	}
	
//	public ResponseData<Html_Lib_BookInfoDetail> getBookInfo(String url){
//		ResponseData<Html_Lib_BookInfoDetail> result = new ResponseData<Html_Lib_BookInfoDetail>();
//		NetSendData sendData = new NetSendData();
//		sendData.setUrl(url);
//		NetReceiverData data = MyHttpWebUtil.sendGet(sendData);
//		Html_Lib_BookInfoDetail bookInfo2Bean = BookInfo2Bean(new String(data.getContent()));
//		result.setObj(bookInfo2Bean);
//		result.setSuccess(true);
//		return result;
//	}
//	
//	public ResponseData<Html_Lib_Search_Result> getNextPage(String nexPageUrl,int currentPage, String refererUrl){
//		ResponseData<Html_Lib_Search_Result> result = new ResponseData<Html_Lib_Search_Result>();
//		@SuppressWarnings("deprecation")
//		String url = nexPageUrl+URLEncoder.encode(("&page="+(currentPage+1)))+"=";
//		Log.i("wang", "url="+url);
//		NetSendData sendData = new NetSendData();
//		sendData.setUrl(url);
//		sendData.getHeaders().put("Referer", refererUrl);
//		NetReceiverData sendGet = MyHttpWebUtil.sendGet(sendData);
////			Log.i("wang", "返回的结果是"+sendGet.getContent().length);
//		Html_Lib_Search_Result libSearchResultHtml2Bean = LibSearchResultHtml2Bean(new String(sendGet.getContent()),currentPage);
////		info.getColumns().addAll(libSearchResultHtml2Bean.getColumns());
////		info.setCurrentPage(info.getCurrentPage()+1+"");
//		result.setSuccess(true);
//		result.setObj(libSearchResultHtml2Bean);
//		return result;
//	}
//	
//	public Html_Lib_Search_Result LibSearchResultHtml2Bean(String html,int page){
//		Html_Lib_Search_Result result = new Html_Lib_Search_Result();
//		Document doc = Jsoup.parse(html);
//		Element table = doc.select("table.tb").first();
//		if(table != null){
//			Element tbody = table.select("tbody").first();
//			if(tbody.children().size()!=0){
//				for(int i = 0;i<tbody.children().size();i++){
//					Element tr = tbody.child(i);
//					BookInfo column = new BookInfo(tr.child(1).child(0).child(0).text()
//							, tr.child(2).text(), tr.child(3).text(), tr.child(4).text()
//							, tr.child(5).text(), tr.child(6).text(), tr.child(7).text());
//					column.setUrl(ConstantValue.LIBMAIN+tr.child(1).child(0).child(0).attr("href"));
//					result.getColumns().add(column);
//				}
//				if(page == 0){//说明是加载第一页结果
//					@SuppressWarnings("deprecation")
//					String pageUrl = ConstantValue.LIBMAIN+ConstantValue.LIBASEARCHRESULTAJAX+"?"+URLEncoder.encode(doc.getElementById("ctl00_ContentPlaceHolder1_thissearchhf").attr("value"));
//					result.setPageUrl(pageUrl);
//					result.setPageCount(doc.getElementById("ctl00_ContentPlaceHolder1_gplblfl1").text());
////					result.setCurrentPage(doc.getElementById("ctl00_ContentPlaceHolder1_dplblfl1").text());
//					result.setCurrentPage(1+"");
//				}
//			}
//		}else{
//			result.setMsg("无数据");
//		}
////		String value = ;
//		/**
//		 * 下一页的地址，就要图书馆服务器地址+ajax地址+参数+page
//		 */
//	
//		return result;
//	}
	
//	public ResponseData<Html_Lib_Search_Result> getSearchResult(Html_Lib_Search_Main main,String content){
////		Log.i("wang", "main.getSearchUrl()="+main.getSearchUrl());
//		ResponseData<Html_Lib_Search_Result> result = new ResponseData<Html_Lib_Search_Result>();
//		NetSendData sendData = new NetSendData();
//		sendData.setUrl(main.getSearchUrl());
//		main.getSearchPars().put("ctl00$ContentPlaceHolder1$keywordstb", content);
//		main.getSearchPars().put("ctl00$ContentPlaceHolder1$splb", "TITLEFORWARD");
//		main.getSearchPars().put("ctl00$ContentPlaceHolder1$deptddl", "ALL");
//		main.getSearchPars().put("ctl00$ContentPlaceHolder1$depthf", "ALL");
//		sendData.setParmars(main.getSearchPars());
//		sendData.setHost(ConstantValue.LibHost);
//		NetReceiverData sendPost = MyHttpWebUtil.sendPost(sendData);
//		result.setObj(LibSearchResultHtml2Bean(new String(sendPost.getContent()),0));
//		result.getObj().setUrl(sendPost.getFromUrl());
////		Log.i("wang", "sendPost.getFromUrl()="+sendPost.getFromUrl());
//		result.setSuccess(true);
//		return result;
//	}
//	
//	public ResponseData<Html_Lib_Search_Main> getLibSearchMain(){
//		ResponseData<Html_Lib_Search_Main> result = new ResponseData<Html_Lib_Search_Main>();
//		NetSendData sendData = new NetSendData();
//		sendData.setUrl(ConstantValue.LIBSEARCHURL);
//		NetReceiverData sendGet = MyHttpWebUtil.sendGet(sendData);
////		Log.i("wang", "sendGet.getHeaders().get(Cookie)="+sendGet.getHeaders().get("Cookie"));
//		result.setObj(LibSearchMainHtml2Bean(new String(sendGet.getContent())));
//		result.setSuccess(true);
//		return result;
//	}
//	
//	/**
//	 * 实现图书馆登录操作
//	 * @param login 获取登录页面bean的时候封装的bean
//	 * @param username 用户名
//	 * @param password 密码
//	 * @param code 验证码
//	 * @return
//	 */
//	public ResponseData<Html_Lib_Main> Login(Html_lib_Login login,String username,String password,String code){
//		ResponseData<Html_Lib_Main> result = new ResponseData<Html_Lib_Main>();
//		NetSendData loginToSendData = new NetSendData();
//		//1.设置登录的url
//		loginToSendData.setUrl(login.getLoginUrl());
//		//2.设置登录操作的一些头部
//		loginToSendData.getHeaders().put("Referer", login.getLoginUrl());
//		loginToSendData.getHeaders().put("Cookie", login.getCookie());
//		loginToSendData.setHost(ConstantValue.LibHost);
//		//3.设置参数
//		Map<String, String> libLoginParmars = login.getLoginPars();
//		libLoginParmars.put("ctl00$ContentPlaceHolder1$txtUsername_Lib", username);
//		libLoginParmars.put("ctl00$ContentPlaceHolder1$txtPas_Lib", password);
//		libLoginParmars.put("ctl00$ContentPlaceHolder1$txtCode", code);
//		loginToSendData.setParmars(libLoginParmars);
////		Log.i("wang", "engine中从登录页面bean中获取到的cookie="+login.getCookie());
////		Log.i("wang", "engine中从登录页面bean中获取到的loginurl="+loginToSendData.getUrl());
//		//4.发送请求，会先得到302转发再调用doGet得到200
//		NetReceiverData receiverData = MyHttpWebUtil.sendPost(loginToSendData);
////		Log.i("wang", "连接服务器验证返回的结果是:"+receiverData.getContent2String());
////		Log.i("wang", "连接服务器验证返回的结果是:"+new String(receiverData.getContent()));
//		//5.对得到html进行解析
//		try {
////			Log.i("wang", "回复 是="+new String(receiverData.getContent()));
//			Html_Lib_Main loginSuccess = isLoginSuccess(new String(receiverData.getContent()));
//			Log.i("wang", "执行登录操作后(302)之后，回复的receiverData中的包含的cookie头饰:"+receiverData.getHeaders().get("Cookie"));
//			loginSuccess.setCookie(receiverData.getHeaders().get("Cookie"));
//			loginSuccess.setUsername(username);
//			loginSuccess.setPassword(password);
//			result.setObj(loginSuccess);
//			result.setSuccess(true);
//			ConstantValue.isLibLogin = true;
//		} catch (OfficeOutTimeException e) {
//			result.setError(e.getMessage());
//			result.setSuccess(false);
//		}
//		return result;
//	}
//	
//	/**
//	 * 对登录操作得到的html进行解析：
//	 * 1.如果验证通过，得到用户主页面对应的bean
//	 * 2.如果验证不通过，则得到错误信息并抛异常
//	 * @param html
//	 * @return
//	 * @throws OfficeOutTimeException
//	 */
//	public  Html_Lib_Main isLoginSuccess(String html) throws OfficeOutTimeException{
//		Html_Lib_Main main = new Html_Lib_Main();
//		Document doc = Jsoup.parse(html);
//		Element userInfoContent = doc.getElementById("userInfoContent");
//		if(userInfoContent == null){
//			//验证失败
////			throw new OfficeOutTimeException(doc.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib").child(0).text());
//			throw new OfficeOutTimeException("未知错误");
//			//<span id="ctl00_ContentPlaceHolder1_lblErr_Lib"><font color="#ff0000">验证码错误</font>&nbsp;&nbsp;</span>
////			result.setError(doc.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib").child(0).text());
//		}else{
//			main.setBorrowHistoryUrl(ConstantValue.LibUserHost + doc.select(":containsOwn(我的借书历史)").first().attr("href"));
//			//获取参数,记得设置cookie
//			LibUserInfo userInfo = new LibUserInfo(userInfoContent.child(0).child(1).text(), userInfoContent.child(1).child(1).text()
//					, userInfoContent.child(2).child(1).text(), userInfoContent.child(3).child(1).text()
//					, userInfoContent.child(4).child(1).text(), userInfoContent.child(5).child(1).text()
//					, userInfoContent.child(6).child(1).text(), userInfoContent.child(7).child(1).text()
//					, userInfoContent.child(8).child(1).text(), userInfoContent.child(9).child(1).text());
//			main.setUserInfo(userInfo);
//			main.setBookBorrowedUrl(ConstantValue.LibUserHost + doc.select(":containsOwn(当前借阅情况和续借)").first().attr("href"));
//		}
//		return main;
//	}
//	
//	public ResponseData<Html_lib_Login> getLibLoginPageAndCodePic(){
//		ResponseData<Html_lib_Login> result = new ResponseData<Html_lib_Login>();
//		NetSendData send = new NetSendData();
//		send.setUrl(ConstantValue.LIBURL);
//		NetReceiverData data = MyHttpWebUtil.sendGet(send);
//		Html_lib_Login loginHtml2Bean = loginHtml2Bean(data);
//		String temlCookie = data.getHeaders().get("Cookie");
//		Log.i("wang", "temlCookie="+temlCookie);
//		loginHtml2Bean.setCookie(temlCookie);
//		
//		NetSendData getCode = new NetSendData();
//		getCode.setUrl(loginHtml2Bean.getCodePicUrl());
//		getCode.getHeaders().put("Cookie", temlCookie);
//		NetReceiverData receiverData = MyHttpWebUtil.sendGet(getCode);
//		InputStream is = null;
//		try {
//			is = new ByteArrayInputStream(receiverData.getContent());
//			loginHtml2Bean.setPicCode(BitmapFactory.decodeStream(is));
//			is.close();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		result.setObj(loginHtml2Bean);
//		result.setSuccess(true);
//		return result;
//	}
//	
////	public ResponseData<Html_lib_Login> getLibLoginPage2Bean(){
////		ResponseData<Html_lib_Login> result = new ResponseData<Html_lib_Login>();
////		NetSendData send = new NetSendData();
////		send.setUrl(ConstantValue.LIBURL);
////		NetReceiverData data = MyHttpWebUtil.sendGet(send);
////		result.setObj(loginHtml2Bean(data));
////		result.getObj().setCookie(data.getHeaders().get("Cookie"));
////		result.setSuccess(true);
////		return result;
////	}
//	
//	public  Html_lib_Login loginHtml2Bean(NetReceiverData data){
//		Html_lib_Login result = new Html_lib_Login();
//		Document doc = Jsoup.parse(data.getContent2String());
//		result.setLoginUrl("http://210.38.162.2/OPAC/login.aspx?ReturnUrl=/opac/user/userinfo.aspx");
//		result.setCodePicUrl("http://210.38.162.2/OPAC/"+doc.getElementById("ccodeimg").attr("src")+"?rd="+Math.random());
////		String __VIEWSTATE = CommonUtil.getGBKUrl(doc.getElementById("__VIEWSTATE").attr("value"));
//		try {
//			String __VIEWSTATE = URLEncoder.encode(doc.getElementById("__VIEWSTATE").attr("value"),"gb2312");
//			@SuppressWarnings("deprecation")
//			String ctl00_ContentPlaceHolder1_txtlogintype = URLEncoder.encode(doc.getElementById("ctl00_ContentPlaceHolder1_txtlogintype").attr("value"));
//			String ctl00_ContentPlaceHolder1_btnLogin_Lib="%E7%99%BB%E5%BD%95";
//			@SuppressWarnings("deprecation")
//			String __EVENTVALIDATION = URLEncoder.encode(doc.getElementById("__EVENTVALIDATION").attr("value")==""?"":doc.getElementById("__EVENTVALIDATION").attr("value"));
//			result.getLoginPars().put("__VIEWSTATE", __VIEWSTATE);
//			result.getLoginPars().put("__EVENTTARGET", "");
//			result.getLoginPars().put("__EVENTARGUMENT", "");
//			result.getLoginPars().put("ctl00$ContentPlaceHolder1$txtlogintype", ctl00_ContentPlaceHolder1_txtlogintype);
//			result.getLoginPars().put("ctl00$ContentPlaceHolder1$btnLogin_Lib", ctl00_ContentPlaceHolder1_btnLogin_Lib);
//			result.getLoginPars().put("__EVENTVALIDATION", __EVENTVALIDATION);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//
//	public ResponseData<Html_Lib_BorrowedState> getBorrowedState(String cookie,
//			String bookBorrowedUrl, String refererUrl) {
//		Log.i("wang", "获取借阅情况的方法中，参数："+cookie+"--"+bookBorrowedUrl+"--"+refererUrl);
//		ResponseData<Html_Lib_BorrowedState> result = new ResponseData<Html_Lib_BorrowedState>();
//		NetSendData sendData = new NetSendData();
//		sendData.setUrl(bookBorrowedUrl);
//		sendData.getHeaders().put("Cookie", cookie);
//		sendData.getHeaders().put("Referer", refererUrl);
//		NetReceiverData receiverData = MyHttpWebUtil.sendGet(sendData);
//		try { 
//			Html_Lib_BorrowedState borrowStateBean = getBorrowStateBean(new String(receiverData.getContent()));
//			result.setObj(borrowStateBean);
//			result.setSuccess(true);
//		} catch (OfficeOutTimeException e) {
//			result.setError(e.getMessage());
//			result.setSuccess(false);
//		}
//		return result;
//	}
//	
//	/**
//	 * 获取目前借书情况的bean
//	 * @param html
//	 * @return
//	 * @throws OfficeOutTimeException
//	 */
//	public Html_Lib_BorrowedState getBorrowStateBean(String html) throws OfficeOutTimeException{
//		Html_Lib_BorrowedState result = new Html_Lib_BorrowedState();
//		Document doc = Jsoup.parse(html);
//		Element div = doc.getElementById("borrowedcontent");
//		if(div == null){
//			throw new OfficeOutTimeException("登录过期");
//		}else{
//			Element tbody = div.select("tbody").first();
//			for(int i=0;i<tbody.children().size();i++){
////				Element tr = tbody.child(i);
////				BookBorrowedState column = new BookBorrowedState(tr.child(1).text()
////						, tr.child(2).child(0).text(), tr.child(3).text()
//////						, tr.child(4).text(), tr.child(5).text(), tr.child(6).text(),0);
////				BookBorrowedState column = new BookBorrowedState(tr.child(0).text()
////						, tr.child(1).text(), ConstantValue.LibHost + tr.child(2).child(0).attr("href")
////						,tr.child(2).child(0).text(), tr.child(3).text()
////						, tr.child(4).text());
////				BookBorrowedState column = new BookBorrowedState(tr.child(1).text()
////						,  tr.child(2).child(0).text()
////						,tr.child(3).text()
////						, tr.child(4).text()
////						, tr.child(5).text()
////						,tr.child(6).text()
////						,ConstantValue.LibHost + tr.child(2).child(0).attr("href")
////						);
////				result.getColumns().add(column);
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 把图书馆搜索页面转化成bean
//	 * @param html
//	 * @return
//	 */
//	@SuppressWarnings("deprecation")
//	public Html_Lib_Search_Main LibSearchMainHtml2Bean(String html){
//		Html_Lib_Search_Main result = new Html_Lib_Search_Main();
//		Document doc = Jsoup.parse(html);
//		result.getSearchPars().put("__VIEWSTATE", URLEncoder.encode(doc.getElementById("__VIEWSTATE").attr("value")));
//		result.getSearchPars().put("__EVENTVALIDATION", URLEncoder.encode(doc.getElementById("__EVENTVALIDATION").attr("value")));
//		result.getSearchPars().put("ctl00$ContentPlaceHolder1$searchbtn", URLEncoder.encode((doc.getElementsByAttributeValue("name", "ctl00$ContentPlaceHolder1$searchbtn").first().attr("value"))));
//		result.setSearchUrl(ConstantValue.LIBSEARCHURL);
//		return result;
//	}
}
