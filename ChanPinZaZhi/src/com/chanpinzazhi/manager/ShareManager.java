package com.chanpinzazhi.manager;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMLWHandler;
import com.umeng.socialize.controller.UMLWService;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMYXHandler;
import com.umeng.socialize.media.CircleShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

public class ShareManager {
	private UMImage uiImage;
	private String title ;
	private String content;
	private String url;
	public ShareManager(UMImage uiImage,String tilte,String content,String url){
		this.uiImage = uiImage;
		this.title = tilte;
		this.content = content;
		this.url = url;
	}
	
	private static UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share", RequestType.SOCIAL);

	public void share(Activity activity) {
		// 基本设置(一)分享的图片设置 
		mController.setShareImage(uiImage);
		// 基本设置(二)分享的内容设置
		mController.setShareContent(content);

		// QQ好友分享
		mController.getConfig().supportQQPlatform(activity, "100424468",
				url);
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(content);
		qqShareContent.setTitle(title);
		qqShareContent.setShareImage(uiImage);
		qqShareContent.setTargetUrl(url);
		mController.setShareMedia(qqShareContent);

		// QQ空间分享
		QZoneSsoHandler mQZoneSsoHandler = new QZoneSsoHandler(activity,
				"100424468");
		mController.getConfig().setSsoHandler(mQZoneSsoHandler);
		QZoneShareContent qzone = new QZoneShareContent();
		qzone.setShareContent(content);
		qzone.setTargetUrl(url);
		qzone.setTitle(title);
		qzone.setShareImage(uiImage);
		mController.setShareMedia(qzone);

		// 易信分享
		UMYXHandler yixinHandler = new UMYXHandler(activity,
				"yxc0614e80c9304c11b0391514d09f13bf");
		// 关闭分享时的等待Dialog
		yixinHandler.enableLoadingDialog(false);
		// 把易信添加到SDK中
		yixinHandler.addToSocialSDK();
		// 易信朋友圈分享
		UMYXHandler yxCircleHandler = new UMYXHandler(activity,
				"yxc0614e80c9304c11b0391514d09f13bf", true);
		yxCircleHandler.addToSocialSDK();
		yxCircleHandler.setTargetUrl(url);

		// 微信分享 
		WeiXinShareContent weixinContent = new WeiXinShareContent(uiImage);
		weixinContent.setShareContent(content);
		mController.setShareMedia(weixinContent);

		String appID = "wx5e47d798587a5053";
		// 微信图文分享必须设置一个url 
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(
				activity, appID, url);
		wxHandler.setWXTitle(title);

		// 支持微信朋友圈
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(content);
		circleMedia.setTitle(title); 
		circleMedia.setShareImage(uiImage); 
		mController.setShareMedia(circleMedia);
		UMWXHandler circleHandler = mController.getConfig()
				.supportWXCirclePlatform(activity, appID, url); 

		// 来往分享
		UMLWHandler umlwDynamicHandler = UMLWService.supportLWPlatform(
				activity, "laiwangd497e70d4",
				"d497e70d4c3e4efeab1381476bac4c5e", url);
		umlwDynamicHandler.setTitle(title);
		umlwDynamicHandler.setMessageFrom("来往");

		// 来往朋友圈分享
		UMLWHandler umlwHandler = UMLWService.supportLWDynamicPlatform(
				activity, "laiwangd497e70d4",
				"d497e70d4c3e4efeab1381476bac4c5e", url);
		umlwHandler.setTitle(title);
		umlwHandler.setMessageFrom("分享");
		
		mController.getConfig().removePlatform(SHARE_MEDIA.EMAIL,
				SHARE_MEDIA.SMS);
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.TENCENT,
				SHARE_MEDIA.SINA, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
				SHARE_MEDIA.YIXIN, SHARE_MEDIA.YIXIN_CIRCLE,
				SHARE_MEDIA.LAIWANG, SHARE_MEDIA.LAIWANG_DYNAMIC);
		mController.openShare(activity, false);
	}
}
