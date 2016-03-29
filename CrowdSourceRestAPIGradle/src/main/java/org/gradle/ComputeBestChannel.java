package org.gradle;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Best Channel Statistics")
public class ComputeBestChannel {

	private String latitude;
	private String longitude;
	private String address;
	private String radius;
	private Map<String, int[]> channelUnoccupied;
	private String recordsScanned;
	private String bestChannel;
	private String time;
	
	@XmlElement
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@XmlElement
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@XmlElement
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@XmlElement
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	
	@XmlElement
	public String getBestChannel() {
		return bestChannel;
	}
	public void setBestChannel(String bestChannel) {
		this.bestChannel = bestChannel;
	}
	
	@XmlElement
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@XmlElement
	public String getRecordsScanned() {
		return recordsScanned;
	}
	public void setRecordsScanned(String recordsScanned) {
		this.recordsScanned = recordsScanned;
	}
	
	@XmlElement
	public Map<String, int[]> getChannelUnoccupied() {
		return channelUnoccupied;
	}
	public void setChannelUnoccupied(Map<String, int[]> channelUnoccupied) {
		this.channelUnoccupied = channelUnoccupied;
	}
}
