package com.yf.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Billing {
	static Logger LOGGER = Logger.getLogger(Billing.class.getName());

	public static String getCard(String token) {
		String CONTENT = "application/json";
		JsonArray ja = new JsonArray();
		String id = Subscriptions.getId(token);
		String tok = "Bearer " + token;
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://management.azure.com" + id
						+ "/providers/Microsoft.Commerce/RateCard?api-version=2016-08-31-preview&$filter=OfferDurableId eq 'MS-AZR-0003p' and Currency eq 'AUD' and Locale eq 'en-AU' and RegionInfo eq 'AU'")
				.addHeader("Authorization", tok).addHeader("Content-type", CONTENT).build();
		try {
			Response response = client.newCall(request).execute();
			JsonElement je = new JsonParser().parse(response.body().string());
			JsonObject jo = je.getAsJsonObject();
			JsonArray ja1 = jo.getAsJsonArray("Meters");
			String currency = jo.get("Currency").getAsString();
			for (int j = 0; j < ja1.size(); j++) {
				JsonObject jo1 = new JsonObject();
				jo1.addProperty("MeterId", ja1.get(j).getAsJsonObject().get("MeterId").getAsString());
				jo1.addProperty("Metername", ja1.get(j).getAsJsonObject().get("MeterName").getAsString());
				try {
					jo1.addProperty("MeterRates",
							ja1.get(j).getAsJsonObject().get("MeterRates").getAsJsonObject().get("0").getAsFloat());
				} catch (Exception e) {
					jo1.addProperty("MeterRates", Integer.valueOf(0));
				}
				ja.add(jo1);
			}

		} catch (Exception e) {
			return null;
		}
		return ja.toString();
	}

	public static String getBillingLive(String token) {
		BigDecimal rate = new BigDecimal(123);
		String CONTENT = "application/json";
		JsonArray ja = new JsonArray();
		String id = Subscriptions.getId(token);
		String tok = "Bearer " + token;
		OkHttpClient client = new OkHttpClient();
		LocalDate localDate = LocalDate.now();
		LocalDate ago = LocalDate.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		Request request = new Request.Builder()
				.url("https://management.azure.com" + id
						+ "/providers/Microsoft.Commerce/UsageAggregates?api-version=2015-06-01-preview&reportedStartTime="
						+ ago.format(formatter) + "&reportedEndTime=" + localDate.format(formatter)
						+ "&aggregationGranularity=Hourly&showDetails=True")
				.addHeader("Authorization", tok).addHeader("Content-type", CONTENT).build();
		try {
			Response response = client.newCall(request).execute();
			JsonElement je = new JsonParser().parse(response.body().string());
			JsonObject jo = je.getAsJsonObject();
			JsonArray ja1 = jo.getAsJsonArray("value");
			for (int j = 0; j < ja1.size(); j++) {
				JsonObject jo1 = new JsonObject();
				String resp = getCard(token);
				JsonElement match = new JsonParser().parse(resp);
				JsonArray ja2 = match.getAsJsonArray();
				String i = ja1.get(j).getAsJsonObject().get("meterId").getAsString();
				for (int k = 0; k < ja2.size(); k++) {
					String temp = ja2.get(k).getAsJsonObject().get("MeterId").getAsString();
					if (i.equals(temp)) {
						rate = ja2.get(k).getAsJsonObject().get("MeterRates").getAsBigDecimal();
					}
				}
				jo1.addProperty("id", ja1.get(j).getAsJsonObject().get("id").getAsString());
				jo1.addProperty("SubscriptionId", ja1.get(j).getAsJsonObject().get("properties").getAsJsonObject()
						.get("subscriptionId").getAsString());
				BigDecimal quantity = ja1.get(j).getAsJsonObject().get("properties").getAsJsonObject().get("quantity")
						.getAsBigDecimal();
				jo1.addProperty("BillAmount(USD)", rate.multiply(quantity));
				ja.add(jo1);
			}

		} catch (Exception e) {
			return null;
		}
		return ja.toString();
	}

	public static String getBilling(String token) {
		String CONTENT = "application/json";
		JsonArray ja = new JsonArray();
		String id = Subscriptions.getId(token);
		String tok = "Bearer " + token;
		OkHttpClient client = new OkHttpClient();
		LocalDate localDate = LocalDate.now();
		LocalDate ago = LocalDate.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		Request request = new Request.Builder()
				.url("https://management.azure.com" + id
						+ "/providers/Microsoft.Commerce/UsageAggregates?api-version=2015-06-01-preview&reportedStartTime="
						+ ago.format(formatter) + "&reportedEndTime=" + localDate.format(formatter)
						+ "&aggregationGranularity=Hourly&showDetails=True")
				.addHeader("Authorization", tok).addHeader("Content-type", CONTENT).build();
		try {
			Response response = client.newCall(request).execute();
			JsonElement je = new JsonParser().parse(response.body().string());
			JsonObject jo = je.getAsJsonObject();
			JsonArray ja1 = jo.getAsJsonArray("value");
			Map<String, BigDecimal> hm = new HashMap<String, BigDecimal>();
			String resp = getCard(token);
			JsonElement match = new JsonParser().parse(resp);
			JsonArray ja2 = match.getAsJsonArray();
			for (int j = 0; j < ja1.size(); j++) {
				String i = ja1.get(j).getAsJsonObject().get("properties").getAsJsonObject().get("meterId")
						.getAsString();
				BigDecimal MeterQ = ja1.get(j).getAsJsonObject().get("properties").getAsJsonObject().get("quantity")
						.getAsBigDecimal();

				BigDecimal rates = (BigDecimal) (hm.containsKey(i) ? hm.get(i) : BigDecimal.ZERO);
				rates = rates.add(MeterQ);
				hm.put(i, rates);
			}
			String sr = "";
			BigDecimal re = BigDecimal.ZERO;
			for (Map.Entry<String, BigDecimal> entry : hm.entrySet()) {
				JsonObject jo1 = new JsonObject();
				jo1.addProperty("MeterId", entry.getKey());
				for (int j = 0; j < ja2.size(); j++) {
					String ko = entry.getKey();
					String mo = ja2.get(j).getAsJsonObject().get("MeterId").getAsString();
					if (ko.equals(mo)) {
						BigDecimal po = ja2.get(j).getAsJsonObject().get("MeterRates").getAsBigDecimal();
						BigDecimal lo = entry.getValue();
						re = po.multiply(lo);
						sr = ja2.get(j).getAsJsonObject().get("Metername").getAsString();
					}
				}
				jo1.addProperty("Name", sr);
				jo1.addProperty("MeterRates", re);
				ja.add(jo1);
			}
		} catch (Exception e) {
			return null;
		}
		return ja.toString();
	}
}
