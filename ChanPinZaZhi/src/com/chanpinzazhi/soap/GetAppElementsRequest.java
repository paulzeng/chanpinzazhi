package com.chanpinzazhi.soap;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.chanpinzazhi.impl.SoapDataImpl;
import com.chanpinzazhi.manager.ConfigManager;
import com.chanpinzazhi.manager.UIManager;

public class GetAppElementsRequest extends AsyncTask<String, Integer, String>
		implements SoapDataImpl {
	private Context context;
	private Handler handler;
	private SoapObject rpc;
	private Dialog loadDialog;
	private boolean isListViewOnScroll;
	private int msgWhat;
	private String wsdl; 

	public GetAppElementsRequest(int msgWhat, Context context, Handler handler,
			SoapObject rpc, boolean isListViewOnScroll) {
		super();
		this.msgWhat = msgWhat;
		this.context = context;
		this.handler = handler;
		this.rpc = rpc;
		this.isListViewOnScroll = isListViewOnScroll;
		this.wsdl = ConfigManager.getConfigs(context, "elmapp_wsdl");//url_wsdl
	}

	public SoapSerializationEnvelope initSoap(SoapObject rpc) {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		return envelope;
	}

	@Override
	public String sendSoap(SoapObject rpc, Handler handler) {

		try {
			SoapSerializationEnvelope envelope = initSoap(rpc);
			HttpTransportSE httpTranstation = new HttpTransportSE(
					wsdl, SoapConstants.TIMEOUT);
			httpTranstation.debug = true;
			httpTranstation.call("http://eims.com.cn/GetAPPElements", envelope); 
			Log.d(null, "#######################GetAppElementsRequest----envelope.getResponse()="+envelope.getResponse());
			String result = envelope.getResponse().toString();
			return result;

		} catch (SocketTimeoutException e) {
			handler.sendEmptyMessage(SoapConstants.TIMEOUT_RESULT);
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			handler.sendEmptyMessage(SoapConstants.FAIL_RESULT);
			e.printStackTrace();
			return null;
		} catch (XmlPullParserException e) {
			handler.sendEmptyMessage(SoapConstants.FAIL_XML_RESULT);
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			handler.sendEmptyMessage(SoapConstants.FAIL_RESULT);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (!isListViewOnScroll) {
			loadDialog = UIManager.getLoadingDialog(context);
			loadDialog.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& event.getRepeatCount() == 0) {
						onCancelled();
						loadDialog.dismiss();
					}
					return false;
				}
			});
			loadDialog.show();
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = sendSoap(rpc, handler); 
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(result!=null){
			Message msg = new Message();
			msg.obj = result;
			msg.what = msgWhat;
			handler.sendMessage(msg);
		}else{
			handler.sendEmptyMessage(SoapConstants.NO_NET);
		}
		if (!isListViewOnScroll) {
			loadDialog.dismiss();
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		this.cancel(true);
	}
}
