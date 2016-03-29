package org.gradle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Devices")
public class Device {

	private Location loct;
	private String last_scanned;
	private String ue_battery_power;
	private String ue_channel_scanned;
	private String mac;
	private String pilot;
	private String[] fft;
	private String count;
	private String ue_model;
	private String ue_status;
	private String seen;
	private double totalpower; 
	private double distance;
	
	@XmlElement
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@XmlElement
	public String getUe_model() {
		return ue_model;
	}

	public void setUe_model(String ue_model) {
		this.ue_model = ue_model;
	}

	@XmlElement
	public String getUe_status() {
		return ue_status;
	}

	public void setUe_status(String ue_status) {
		this.ue_status = ue_status;
	}

	@XmlElement
	public String[] getFft() {
		return fft;
	}

	public void setFft(String[] fft) {
		this.fft = fft;
	}

	@XmlElement
	public String getPilot() {
		return pilot;
	}

	public void setPilot(String pilot) {
		this.pilot = pilot;
	}
	
	@XmlElement
	public String getLast_scanned() {
		return last_scanned;
	}

	public void setLast_scanned(String last_scanned) {
		this.last_scanned = last_scanned;
	}

	@XmlElement
	public String getUe_battery_power() {
		return ue_battery_power;
	}

	public void setUe_battery_power(String ue_battery_power) {
		this.ue_battery_power = ue_battery_power;
	}

	@XmlElement
	public String getUe_channel_scanned() {
		return ue_channel_scanned;
	}

	public void setUe_channel_scanned(String ue_channel_scanned) {
		this.ue_channel_scanned = ue_channel_scanned;
	}

	@XmlElement
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@XmlElement
	public Location getLoct() {
		return loct;
	}

	public void setLoct(Location loct) {
		this.loct = loct;
	}

	@XmlElement
	public String getSeen() {
		return seen;
	}

	public void setSeen(String seen) {
		this.seen = seen;
	}

	@XmlElement
	public double getTotalpower() {
		return totalpower;
	}

	public void setTotalpower(double totalpower) {
		this.totalpower = totalpower;
	}

	@XmlElement
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}