package com.chanpinzazhi.entity;

import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("Result")
public class Xiangce_result {
	@XStreamAlias("CurrentDateTime")
	@XStreamAsAttribute
	private String  CurrentDateTime; 
	@XStreamAsAttribute
	@XStreamAlias("Number")
	private int  Number; 
	@XStreamAsAttribute
	@XStreamAlias("Msg")
	private String  Msg; 
	@XStreamImplicit(itemFieldName="File")
	private List<String> files;

	@Override
	public String toString() {
		return "Xiangce_result [CurrentDateTime=" + CurrentDateTime
				+ ", Number=" + Number + ", Msg=" + Msg + ", files=" + files
				+ "]";
	}

	public String getCurrentDateTime() {
		return CurrentDateTime;
	}

	public void setCurrentDateTime(String currentDateTime) {
		CurrentDateTime = currentDateTime;
	}

	public int getNumber() {
		return Number;
	}

	public void setNumber(int number) {
		Number = number;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}



}
