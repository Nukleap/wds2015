package com.ida.wds2015.classes;

import com.ida.wds2015.R;

public class Constants {
	
	public static String[] menus = {"My Account","Exhibitor","Programme","Session","Speaker","Favorite","Poster","Floor Plan","Scan","Feedback"};

public static int[] imageList={R.drawable.ic_grid_menu_account,
	R.drawable.ic_grid_menu_exhibitors,R.drawable.ic_grid_menu_programme,
	R.drawable.ic_grid_menu_category,R.drawable.ic_grid_menu_speakar,
	R.drawable.ic_grid_menu_notes,R.drawable.ic_grid_menu_poster,	
	R.drawable.ic_grid_menu_floorplan,R.drawable.ic_grid_menu_scan,R.drawable.ic_grid_menu_feedback};
	
	public static long position=-1;
	public static final int INTERVAL = 400;
	public static final String REQUEST_TAG_GETEXHIBITOR = "request_tag_getexhibitor";
	public static final String REQUEST_TAG_GETPROGRAMCATEGORY = "request_tag_getprogramcategory";
	public static final String REQUEST_TAG_PROGRAMME = "request_tag_programme";
	public static final String REQUEST_TAG_SPEAKER = "request_tag_speaker";
	public static final String REQUEST_TAG_SCAN = "request_tag_scan";
	public static final String REQUEST_TAG_POSTER = "request_tag_poster";
	public static final String REQUEST_TAG_FEE_DETAILS = "request_tag_fee_details";
	public static final String REQUEST_TAG_FEEDBACK_DETAILS = "request_tag_feedback_details";

	public static final String REQUEST_TAG_NEWS_DETAILS = "request_tag_news_details";
	public static final String REQUEST_TAG_POST_USER_FEE = "request_tag_user_fee";
	public static final String REQUEST_TAG_POST_POSTER_VOTE = "request_tag_post_poster_vote";
	public static final String REQUEST_TAG_POST_SCAN_RESULT = "request_tag_post_scan_result";
	public static final String REQUEST_TAG_GET_TRANS_DETAIL = "request_tag_get_trans_detail";
	public static final String URL_PAYMENT = "http://wds.org.in/registration/HDFCGateway/Pay.aspx";
	public static final String URL_GETEXHIBITOR = "http://wds.org.in/service/OrderService.svc/Getexhibitor";
	public static final String URL_GETPROGRAMCATEGORY = "http://wds.org.in/service/OrderService.svc/Getprogramcategory";
	public static final String URL_GETPROGRAM = "http://wds.org.in/service/OrderService.svc/Getprogram";
	public static final String URL_GETALLSPEAKER = "http://wds.org.in/service/OrderService.svc/Getallspeaker";
	public static final String URL_SPEAKER_PROFILE_IMG = "http://speakerbank.ida.org.in/spkimg/";
	public static final String URL_POSTER_PROFILE_IMG = "http://postercompetition.org.in/Upload_Eposter/image-preview/uploads/";
	public static final String URL_GETALLSCAN = "http://wds.org.in/service/OrderService.svc/Getscanresult";
	public static final String URL_GET_TRANS_DETAIL = "http://wds.org.in/service/OrderService.svc/GetTRANS/";
	public static final String URL_FEE_REGISTRATION = "http://wds.org.in/service/OrderService.svc/Getallfees";
	public static final String URL_NEWS = "http://wds.org.in/service/OrderService.svc/Getnews";
	public static final String URL_POST_FEE_USER_SAVE ="http://wds.org.in/service/OrderService.svc/Placeprder";
	public static final String URL_POST_VOTE_POSTER_SAVE ="http://wds.org.in/service/OrderService.svc/Placvote";
	public static final String URL_POST_SCAN_RESULT ="http://wds.org.in/service/OrderService.svc/Placscan";
	public static final String URL_GETALLPOSTER="http://wds.org.in/service/OrderService.svc/Getposter";
	private static final String URL_BARCODE="http://wds.org.in/registration/BarCode_Image/";
	public static final String URL_POST_FEEDBACK ="http://wds.org.in/service/OrderService.svc/Placefeedback";
	public static String getBarcode(String delid){
		return URL_BARCODE+delid+".Jpeg";
	}
	
	public static final String BROADCAST_REQUEST_POSTER = "broadcast_request_poster";
	public static final String BROADCAST_REQUEST_ALL = "broadcast_request_all";
	public static final String BROADCAST_USER_FEE = "broadcast_user_fee";
	public static final String BROADCAST_POST_USER_FEE = "broadcast_post_user_fee";
	public static final String BROADCAST_POST_VOTE = "broadcast_post_vote";
	public static final String BROADCAST_POST_VOTE_DONE = "broadcast_post_vote_done";
	public static final String BROADCAST_PAYMENT_DONE = "broadcast_payment_done";
	public static final String BROADCAST_NEWS = "broadcast_news";
	public static final String BROADCAST_SCAN = "broadcast_scan";
	public static final String BROADCAST_TRANS_DETAIL = "broadcast_trans_detail";
	public static final String BROADCAST_POSTING_COMPLETE = "broadcast_posting_complete";
	
}
