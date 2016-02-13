package com.wxxiaomi.myschool;

public class ConstantValue {

	public static String ENCODING = "UTF-8";
	//记录服务器ip地址
	public static String LOTTERY_URI = "http://192.168.211.1:8080/MySchool";

	public static String NEWSGET = "/InformationServlet?action=getall";

	public static String NEWS = "/NewsServlet?action=getnews";
	
	public static String GETALLLOST = "/LostServlet?action=getalllost";

	public static String GETCOMMENT = "/CommentServlet?action=getcomment";

	public static String GETSOCIALLIST = "/SocialServlet?action=getsoiallist";

	public static String LOGIN = "/UserServlet?action=login";

	public static String SAVAUSERHEAD = "/UserServlet?action=updateuserhead";

	public static String UPDATEUSERINFO = "/UserServlet?action=updatecolumn";

	public static String ADDREPAIR = "/RepairServlet?action=addrepair";

	public static String GETSECONDHANDLIST = "/SecondHandServlet?action=getsecond";

	public static String INSERTLOST = "/LostItemServlet?action=insertitem";

	public static String JOINSOCIAL = "/SocialServlet?action=joinsocial";

	public static String PUBLISHSOCIAL = "/SocialServlet?action=publishsocial";

	public static String INSERTSECONDHAND = "/SecondHandServlet?action=insertsecondhand";

	public static String GETMYORIGINATESOCIALLIST = "/SocialServlet?action=getmyoriginatesociallist";

	public static String GETUSERSOCIAL = "/SocialServlet?action=getusersocial";

	public static String GETSOCIALUSERLIST = "/SocialServlet?action=getsocialuserlist";

	public static String GETSECONDHANDPUBLISHER = "/SecondHandServlet?action=getsecondhandpublisher";

	public static String GETSECONDHANDPLAYER = "/SecondHandServlet?action=getsecondhandplayer";

	public static String SENDSECONDHANDREPLY = "/SecondHandServlet?action=sendsecondhandreply";

	public static String UPDATESOCIAL = "/SocialServlet?action=updatesocial";

	public static String GETLOSTITEMLIST = "/LostItemServlet?action=getlostitemlist";

	public static String GETFOUNDITEMLIST = "/LostItemServlet?action=getfounditemlist";

	public static String GETLOSTFOUNDITEMLIST = "/LostItemServlet?action=getlostitemlist";

	public static String INSERTITEM = "/LostItemServlet?action=insertitem";

	public static String GETMYITEMLIST = "/LostItemServlet?action=getmyitemlist";

	public static String UPDATEMYITEM = "/LostItemServlet?action=updatemyitem";

	public static String REGISTERUSER = "/UserServlet?action=register";

	public static String GETTOPLINELIST = "/TopLineServlet?action=gettopline";

	public static String GETTOPLINE =  "/NewsServlet?action=gettopline";

	public static String TESTCONNECT = "/CommonServlet?action=testconnect";
	
	public static String GETLOSTITEMCOMMENTLIST = "/LostItemServlet?action=getItemComment";
	
	public static String INSERTLOSTITEMCOMMENT = "/LostItemServlet?action=insertLostItemComment";
	
	public static String DELETELOSTITEMCOMMENT = "/LostItemServlet?action=deleteLostItemComment";
	
	/**
	 * 正方系统登录页面的url
	 */
	public static String Host = "http://210.38.162.116/";
	public static String tempOfficeUrl;
	public static String GETSOCIALITEMCOMMENTLIST = "/SocialServlet?action=getsocialcommentlist";
	public static String LIBURL = "http://210.38.162.2/OPAC/login.aspx?ReturnUrl=/opac/user/userinfo.aspx";
	/**
	 * 标识图书馆账号是否已经登录
	 */
	public static boolean isLibLogin = false;
	
	/**
	 * 标识账号是否登录
	 */
	public static boolean isLogin = false;
//	public static String LibHost = "http://210.38.162.2/opac/user/";
	public static String LibUserHost = "http://210.38.162.2/opac/user/";
	public static String LibHost = "http://210.38.162.2/";
	/**
	 * 图书馆cookie过期标识
	 */
	public static int STATE_LIBOUTTIME = 123123;
	public static String LIBSEARCHURL = "http://210.38.162.2/OPAC/search.aspx";
	public static String LIBMAIN = "http://210.38.162.2/OPAC/";
	/**
	 * 图书馆搜索页面ajax地址
	 */
	public static String LIBASEARCHRESULTAJAX = "showpageforlucenesearchAjax.aspx";
	/**
	 * 正方系统未登录标识
	 */
	public static final int OfficeNoLogin = 226;
	/**
	 * 跳转到登录页面，然后onActivityResult的标识
	 */
	public static final int LOGINACTIVITY = 23;
	/**
	 * 图书馆账号为登录标识
	 */
	public static final int LIBNOLOGIN = 1111;
}
