package com.chanpinzazhi.impl;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.os.Handler;

public interface SoapDataImpl {
	SoapSerializationEnvelope initSoap(SoapObject rpc);
	String sendSoap(SoapObject rpc,Handler handler);
}
