package org.gradle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Power Estimation")
public class ComputePowerView {

	private String latitude;
	private String longitude;
	private String number;
	private String maxDistance;
	private String power;
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
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@XmlElement
	public String getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(String maxDistance) {
		this.maxDistance = maxDistance;
	}

	@XmlElement
	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	@XmlElement
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
