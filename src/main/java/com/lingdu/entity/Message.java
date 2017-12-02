package com.lingdu.entity;

public class Message {
	private int id;
	private int terminalid;
	private int longitude;
	private int latitude;
	private int direction;
	private int curspeed;
	private String speeds;
	private String imei;
	private int communiid;
	private String cellular;
	private String version;
	private String proxyid;
	private int temperature;
	private int oil;
	private int mileage;
	private int sensor;
	private int unixtimestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(int terminalid) {
		this.terminalid = terminalid;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getCurspeed() {
		return curspeed;
	}

	public void setCurspeed(int curspeed) {
		this.curspeed = curspeed;
	}

	public String getSpeeds() {
		return speeds;
	}

	public void setSpeeds(String speeds) {
		this.speeds = speeds;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getCommuniid() {
		return communiid;
	}

	public void setCommuniid(int communiid) {
		this.communiid = communiid;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProxyid() {
		return proxyid;
	}

	public void setProxyid(String proxyid) {
		this.proxyid = proxyid;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getOil() {
		return oil;
	}

	public void setOil(int oil) {
		this.oil = oil;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getSensor() {
		return sensor;
	}

	public void setSensor(int sensor) {
		this.sensor = sensor;
	}

	public int getUnixtimestamp() {
		return unixtimestamp;
	}

	public void setUnixtimestamp(int unixtimestamp) {
		this.unixtimestamp = unixtimestamp;
	}
	
	public  String ObjectToStr(Message ms){
		StringBuffer sbf = new StringBuffer();
		sbf.append(ms.getTerminalid());
		sbf.append(",");
		sbf.append(ms.getLongitude());
		sbf.append(",");
		sbf.append(ms.getLatitude());
		sbf.append(",");
		sbf.append(ms.getDirection());
		sbf.append(",");
		sbf.append(ms.getCurspeed());
		sbf.append(",'");
		sbf.append(ms.getSpeeds());
		sbf.append("','");
		sbf.append(ms.getImei());
		sbf.append("',");
		sbf.append(ms.getCommuniid());
		sbf.append(",'");
		sbf.append(ms.getCellular());
		sbf.append("','");
		sbf.append(ms.getVersion());
		sbf.append("','");
		sbf.append(ms.getProxyid());
		sbf.append("',");
		sbf.append(ms.getTemperature());
		sbf.append(",");
		sbf.append(ms.getOil());
		sbf.append(",");
		sbf.append(ms.getMileage());
		sbf.append(",");
		sbf.append(ms.getSensor());
		sbf.append(",");
		sbf.append(ms.getUnixtimestamp());
		return sbf.toString();
		
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", terminalid=" + terminalid
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", direction=" + direction + ", curspeed=" + curspeed
				+ ", speeds=" + speeds + ", imei=" + imei + ", communiid="
				+ communiid + ", cellular=" + cellular + ", version=" + version
				+ ", proxyid=" + proxyid + ", temperature=" + temperature
				+ ", oil=" + oil + ", mileage=" + mileage + ", sensor="
				+ sensor + ", unixtimestamp=" + unixtimestamp + "]";
	}
	

}
