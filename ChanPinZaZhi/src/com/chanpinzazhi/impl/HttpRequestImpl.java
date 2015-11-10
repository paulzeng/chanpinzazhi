package com.chanpinzazhi.impl;

import android.app.Service;
import android.content.Context;
import android.os.Handler;

public interface HttpRequestImpl {
	/**
	 * 获取栏目数据
	 * 
	 * @param deviceID
	 *            设备 ID/设备标识
	 * @param siteId
	 *            站点 ID
	 * @param deviceType
	 *            设备类型，1(PC)、2(手机)、3(微信)、4(App)，值：4
	 * @param tableId
	 *            查询数据表的 ID，值为：8
	 * @param fields
	 *            返回字段列表，多个以“,”分隔注意区分大小写，例如：Id, App_Name, App_ Image
	 * @param cnd
	 *            查询条件，标准SQL条件，条件里面的字段名前后需加上中括号，例如：App_IsShow=1为空时所有栏目值：不能是什么条件，
	 *            条件里面都必须有App_IsShow=1这个条件
	 * @param order
	 *            排序，标准SQL排序，字段名前后需加上中括号
	 * @param rowIndex
	 *            从第几行开始，行索引从0开始算起
	 * @param pageSize
	 *            返回的总行数
	 * @param isRefreshCache
	 *            是否为刷新缓存数据，当取的是新数据时此参数为false，只为取变动的缓存数据时为true
	 * @param lastCacheTime
	 *            最后缓存数据的时间，最后一次获取数据返回的服务器时间（CurrentDateTime），只有
	 *            isRefreshCache为true时此参数才有效
	 * @param timestamp
	 *            时间戳，格式：yyyy-MM-dd HH:mm:ss
	 * @param sign
	 *            （Key+以上字段）.MD5
	 */
	public void getNavByTableId(int msgWhat, Context context, Handler handler,
			String deviceID, int deviceType, int tableId, String fields,
			String cnd, String order, int rowIndex, int pageSize,
			String filterHtmlescapeColumns, boolean isRefreshCache,
			String lastCacheTime);

	/**
	 * 获取某栏目下的类别
	 * 
	 * @param deviceID
	 * @param siteId
	 * @param deviceType
	 * @param navigateId
	 * @param source
	 * @param fields
	 * @param cnd
	 * @param order
	 * @param rowIndex
	 * @param pageSize
	 * @param isRefreshCache
	 * @param lastCacheTime
	 * @param timestamp
	 * @param sign
	 */
	public void GetByNavigateId(int msgWhat, Context context, Handler handler,
			String deviceID, int deviceType, int source, String fields,
			String cnd, String order, int rowIndex, int pageSize,
			String filterHtmlescapeColumns, boolean isRefreshCache,
			String lastCacheTime, boolean flag);

	/**
	 * 获取产品内容，带详情和推荐
	 * 
	 * @param msgWhat
	 * @param context
	 * @param handler
	 * @param deviceID
	 * @param siteId
	 * @param deviceType
	 * @param navigateId
	 * @param fields
	 * @param cnd
	 * @param order
	 * @param rowIndex
	 * @param pageSize
	 * @param filterHtmlescapeColumns
	 * @param isRefreshCache
	 * @param lastCacheTime
	 * @param flag
	 */
	public void GetContentWidthRelevantData(int msgWhat, Context context,
			Handler handler, String deviceID, int deviceType, String fields,
			String cnd, String order, int rowIndex, int pageSize,
			String filterHtmlescapeColumns, boolean isRefreshCache,
			String lastCacheTime, boolean flag);

	/**
	 * 意见反馈（提交数据）
	 * 
	 * @param deviceID
	 * @param siteId
	 * @param deviceType
	 * @param tableId
	 * @param typeId
	 * @param sumbitContent
	 * @param timestamp
	 * @param sign
	 */
	public void postFeedback(Context context, Handler handler, String deviceID,
			int deviceType, int tableId, int typeId, String sumbitContent);

	/**
	 * 消息推送接口
	 * 
	 * @param context
	 * @param handler
	 * @param deviceID
	 * @param SiteId
	 * @param deviceType
	 * @param tableIds
	 * @param lastCacheTime
	 */
	public void GetNewContents(int msgWhat, Service context, Handler handler,
			String deviceID, int deviceType, String tableIds,
			String lastCacheTime);

	/**
	 * 获取APP版本号
	 * 
	 * @param siteId
	 * @param configIds
	 * @param timestamp
	 * @param sign
	 */
	public void GetAPPConfig(int msgWhat, Context context, Handler handler,
			String configIds);


/**
 * 获取首页相册元素
 * @param deviceID
 * @param siteId
 * @param configIds
 * @param timestamp
 * @param sign
 */
public void GetAPPElements(String deviceID,int msgWhat, Context context, Handler handler,
		String configIds);
}





