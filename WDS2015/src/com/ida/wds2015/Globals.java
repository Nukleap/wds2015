package com.ida.wds2015;

import java.util.ArrayList;

import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.FavoriteListItem;
import com.ida.wds2015.classes.Feedback;
import com.ida.wds2015.classes.News;
import com.ida.wds2015.classes.Poster;
import com.ida.wds2015.classes.ProgramCategory;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.Scan;
import com.ida.wds2015.classes.SelectedFee;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.TransactionRoot;
import com.ida.wds2015.classes.User;
import com.ida.wds2015.classes.UserFee;

public class Globals {
	
	public static ArrayList<Exhibitor> exlist = null;
	public static ArrayList<ProgramCategory> prCatlist = null;
	public static ArrayList<Scan> scanList = null;
	public static ArrayList<Poster> posterList = null;
	public static ArrayList<Programme> programmes = null;
	public static ArrayList<Speaker> speakers = null;
	public static boolean downloaded = false;
	public static Object clipobj = null;
	public static UserFee myfee = new UserFee();
	public static User user = new User();
	public static ArrayList<TransactionRoot> transactions = null;
	public static Poster poster = new Poster();
	public static ArrayList<UserFee> userfees;
	public static ArrayList<News> news = null;
	public static ArrayList<UserFee> selectedpackages = null;
	public static ArrayList<SelectedFee> payments = null;
	public static int currentuser=0;
	public static String CURRENT_PROGRAM = "";
	public static ArrayList<Feedback> feedbacklist = null;
}
