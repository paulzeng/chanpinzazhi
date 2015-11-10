package com.chanpinzazhi.manager;

import android.content.Context;

import com.chanpinzazhi.R;

public class ConfigManager {
	public static final boolean DEVELOPER_MODE = false;

	public static String getSiteDomain(Context cx) {
		return cx.getString(R.string.siteDomain);
	}

	public static String getConfigs(Context cx, String str) {
		String baseUrl = getSiteDomain(cx);
		if (str.equals("siteID")) {
			str = cx.getString(R.string.siteID);
		} else if (str.equals("url_pro_type")) {
			str = baseUrl + "/Private/ProductImg/Types/";
		} else if (str.equals("url_pro_big")) {
			str = baseUrl + "/Private/ProductImg/Big/";
		} else if (str.equals("url_pro_other")) {
			str = baseUrl + "/Private/ProductImg/Other/";
		} else if (str.equals("url_wsdl")) {
			str = baseUrl + "/Admin/WebService/APINavigateData.asmx?wsdl";
		} else if (str.equals("cig_wsdl")) {
			str = baseUrl + "/Admin/WebService/APIOption.asmx?wsdl";
		} else if (str.equals("elmapp_wsdl")) {
			str = baseUrl + "/Admin/WebService/APIApp.asmx";
		}else if (str.equals("appname")) {
			str = cx.getString(R.string.appname);
		}
		
		else if (str.equals("phone")) {
			str = cx.getString(R.string.phone);
		} else {
			str = "error code";
		}
		return str;
	}
}
