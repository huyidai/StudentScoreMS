package com.jsu.hyd.User;

public class Student {
	private String no;
	private String name;
	private int asmScore, javaScore, netScore, osScore;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAsmScore() {
		return asmScore;
	}

	public void setAsmScore(int asmScore) {
		this.asmScore = asmScore;
	}

	public int getJavaScore() {
		return javaScore;
	}

	public void setJavaScore(int javaScore) {
		this.javaScore = javaScore;
	}

	public int getNetScore() {
		return netScore;
	}

	public void setNetScore(int netScore) {
		this.netScore = netScore;
	}

	public int getOsScore() {
		return osScore;
	}

	public void setOsScore(int osScore) {
		this.osScore = osScore;
	}

	@Override
	public String toString() {
		return  no + "," + name + "," + asmScore + "," + javaScore + "," + netScore + "," + osScore;
	}
	
	
}