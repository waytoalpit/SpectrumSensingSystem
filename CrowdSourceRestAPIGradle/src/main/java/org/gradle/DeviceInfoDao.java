package org.gradle;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import sun.jdbc.odbc.ee.PoolWorker;

public class DeviceInfoDao {

	Logger log = Logger.getLogger("DeviceInfoDao");
	Properties prop = null;

	DB db = null;
	List<Device> data = null;
	Connection conn = null;

	public DeviceInfoDao() {

		prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("config.properties"));
			conn = new Connection();
			db = conn.connectMongoDB();
			conn.authenticateDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Device> getAllDevices() {
		try {

			String deviceCollection = prop.getProperty("deviceCollection");
			DBCollection coll = db.getCollection(deviceCollection);
			log.info("Collection UE_Measurements selected successfully");

			DBCursor cursor = coll.find();
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setLast_scanned((String) o.get("last_scanned"));
				device.setMac((String) o.get("mac"));
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_channel_scanned((String) o.get("ue_channel_scanned"));
				device.setPilot((String) o.get("pilot"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				BasicDBList dbObjectFFT = (BasicDBList) o.get("fft");
				ListIterator<Object> fftList = dbObjectFFT.listIterator();
				String[] fftValues = new String[512];

				int i = 0;
				while (fftList.hasNext() && i < 512) {
					Object nextItem = fftList.next();
					fftValues[i++] = nextItem.toString();
				}
				device.setFft(fftValues);

				data.add(device);
			}

			log.info("data retrieved successfully");

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getAllOnlineDevices() {
		try {

			String onlineDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(onlineDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ue_status", "ONLINE");
			DBCursor cursor = coll.find(whereQuery);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getAllOfflineDevices() {
		try {

			String offlineDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(offlineDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ue_status", "OFFLINE");
			DBCursor cursor = coll.find(whereQuery);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getDevicesByRadius(String x, String y, String radius) {
		try {

			String rangeDeviceCollection = prop.getProperty("deviceCollection");
			DBCollection coll = db.getCollection(rangeDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBList geoCoord = new BasicDBList();
			geoCoord.add(Double.parseDouble(x));
			geoCoord.add(Double.parseDouble(y));

			BasicDBList geoParams = new BasicDBList();
			geoParams.add(geoCoord);
			geoParams.add(Double.parseDouble(radius) / 3963.2);

			BasicDBObject query = new BasicDBObject("loc",
					new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));

			DBCursor cursor = coll.find(query);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				/*
				 * device.setCount(o.get("count").toString());
				 * device.setUe_battery_power((String)
				 * o.get("ue_battery_power")); device.setUe_model((String)
				 * o.get("ue_model")); device.setUe_status((String)
				 * o.get("ue_status"));
				 */
				device.setTotalpower(Double.parseDouble(String.valueOf(o.get("totalpower"))));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public Device getLastSeenDevice() {

		Device device = new Device();
		try {

			String onlineDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(onlineDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			DBCursor cursor = coll.find().sort(new BasicDBObject("seen", 1));
			log.info("Retrieving data");

			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));
				device.setSeen((String) o.get("seen"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return device;
	}

	public ComputePowerView computerPowerIDW(String x, String y, String num, String max) {

		ComputePowerView data = new ComputePowerView();

		try {

			String rangeDeviceCollection = prop.getProperty("deviceCollection");
			DBCollection coll = db.getCollection(rangeDeviceCollection);
			log.info("Collection UE_Measurements selected successfully");

			BasicDBList geoCoord = new BasicDBList();
			geoCoord.add(Double.parseDouble(x));
			geoCoord.add(Double.parseDouble(y));
			BasicDBObject geoParams = new BasicDBObject("type", "Point").append("coordinates", geoCoord);

			BasicDBObject query = new BasicDBObject("$geoNear",
					new BasicDBObject("near", geoParams).append("distanceField", "calculated")
							.append("maxDistance", Double.parseDouble(max)).append("num", Integer.parseInt(num))
							.append("spherical", true));

			AggregationOutput cursor = coll.aggregate(query);
			log.info("Retrieving data");

			BigDecimal nume = new BigDecimal(0.0);
			BigDecimal deno = new BigDecimal(0.0);
			/*
			 * nume.setScale (3, BigDecimal.ROUND_CEILING); deno.setScale (3,
			 * BigDecimal.ROUND_CEILING);
			 */

			for (DBObject result : cursor.results()) {

				double power = Double.parseDouble(String.valueOf(result.get("totalpower")));
				double dist = Double.parseDouble(String.valueOf(result.get("calculated")));

				BigDecimal distSquare = new BigDecimal(dist * dist);
				BigDecimal bigPower = new BigDecimal(power);
				BigDecimal bigOne = new BigDecimal(1.0);
				BigDecimal calcNum = bigPower.divide(distSquare, 10, BigDecimal.ROUND_HALF_DOWN);
				BigDecimal calcden = bigOne.divide(distSquare, 10, BigDecimal.ROUND_HALF_DOWN);

				nume = nume.add(calcNum);
				deno = deno.add(calcden);

				// System.out.println(result);
			}

			BigDecimal power = nume.divide(deno, 10, BigDecimal.ROUND_HALF_DOWN);
			data.setLatitude(y);
			data.setLongitude(x);
			data.setNumber(num);
			data.setMaxDistance(max);
			data.setPower(power.toString());

			System.out.println("Estimated power: " + power);
		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;

	}

	public ComputeBestChannel computeBestChannel(String address, int radius) {

		ComputeBestChannel channelInfo = new ComputeBestChannel();
		try {

			String rangeDeviceCollection = prop.getProperty("deviceCollection");
			DBCollection coll = db.getCollection(rangeDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBList geoCoord = new BasicDBList();
			LatLng latLng = new LatLng();
			String[] add = latLng.getLatLongPositions(address);

			geoCoord.add(Double.parseDouble(add[1]));
			geoCoord.add(Double.parseDouble(add[0]));

			BasicDBList geoParams = new BasicDBList();
			geoParams.add(geoCoord);
			geoParams.add(radius / 3963.2);

			BasicDBObject query = new BasicDBObject("loc",
					new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));

			DBCursor cursor = coll.find(query);
			log.info("Retrieving data");

			String ch = "";
			Map<String, int[]> map = new HashMap<String, int[]>();

			while (cursor.hasNext()) {
				DBObject o = cursor.next();

				String channel = (String) o.get("ue_channel_scanned");
				String pilot = (String) o.get("pilot");

				// calculate best channel to use
				if (map.get(channel) == null) {

					int[] val = new int[2];
					if (pilot.equals("0"))
						val[0] = 1;
					else
						val[1] = 1;
					map.put(channel, val);
				} else {

					int[] val = map.get(channel);
					if (pilot.equals("0"))
						val[0] = val[0] + 1;
					else
						val[1] = val[1] + 1;
					map.put(channel, val);
				}
			}

			channelInfo.setLatitude(add[0]);
			channelInfo.setLongitude((add[1]));
			channelInfo.setAddress(address);
			channelInfo.setRadius(String.valueOf(radius));
			channelInfo.setChannelUnoccupied(map);

			double dominance = 0.0;
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				int[] val = (int[]) pair.getValue();
				double currDominance = (double) val[0] / (val[0] + val[1]);

				if (currDominance > dominance) {
					dominance = currDominance;
					ch = (String) pair.getKey();
				}
			}

			channelInfo.setRecordsScanned(String.valueOf(cursor.size()));
			channelInfo.setBestChannel(ch);
			System.out.println("Number of documents got scanned: " + cursor.size());
			System.out.println("Best Channel to Use: " + ch);
		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return channelInfo;
	}

}