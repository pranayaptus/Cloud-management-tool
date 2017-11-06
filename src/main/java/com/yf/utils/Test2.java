package com.yf.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test2 {
	public static void main(String[] args) {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJLVmN1enFBaWRPTHFXU2FvbDd3Z0ZSR0NZbyIsImtpZCI6IjJLVmN1enFBaWRPTHFXU2FvbDd3Z0ZSR0NZbyJ9.eyJhdWQiOiJodHRwczovL21hbmFnZW1lbnQuY29yZS53aW5kb3dzLm5ldC8iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yN2M5NTA0YS1jNDY3LTQzNzYtYjI2MS04OWIxNWU4ZWJiMTcvIiwiaWF0IjoxNTA5Nzc1MTAyLCJuYmYiOjE1MDk3NzUxMDIsImV4cCI6MTUwOTc3OTAwMiwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhHQUFBQTBNaUFCY3N0NWJDSFdWak9HZ3hXa3JRYVd0UkkwSXJxUE83THhySFI2cUU9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6ImIzMTQ0ZmUxLTYzMzgtNDNhMC04OTE4LWMwNTI5MWY1MTE3MCIsImFwcGlkYWNyIjoiMSIsImZhbWlseV9uYW1lIjoiSGV3aXR0IiwiZ2l2ZW5fbmFtZSI6Ikp1c3RpbiIsImdyb3VwcyI6WyJhYzc5Njc0MC1mZjVjLTQ3ZjctODlkZS1mZTkzM2JkZjUwOGQiXSwiaXBhZGRyIjoiMTA2LjIwMC4xOTkuMzMiLCJuYW1lIjoiSnVzdGluIEhld2l0dCIsIm9pZCI6IjVmODkwNmQxLWVlMmQtNDIxZC1hYTcxLTA2YTgxMDljMjEzMCIsInB1aWQiOiIxMDAzM0ZGRjhEMjcyNEFDIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoicHJZRGlfbC1HR2xPempfaUw2T2t6cEFLdEFvQ2RUZWxHMFZ1UnM5TlJtWSIsInRpZCI6IjI3Yzk1MDRhLWM0NjctNDM3Ni1iMjYxLTg5YjE1ZThlYmIxNyIsInVuaXF1ZV9uYW1lIjoianVzdGluLmhld2l0dEB5ZWxsb3dmaW4uYmkiLCJ1cG4iOiJqdXN0aW4uaGV3aXR0QHllbGxvd2Zpbi5iaSIsInV0aSI6IkxfX08wcklWZmtLU0l4R1dxc1FBQUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbIjYyZTkwMzk0LTY5ZjUtNDIzNy05MTkwLTAxMjE3NzE0NWUxMCJdfQ.GCdn63Zx_I-LUEhHgwHpxrBZwbIDLPzi4RckICvQMBIStjnawukHeVRwr7fg4x-R4YYAihWoPNyhz9y7KcUqp12wQ3hGkK4U2Cl9vcfrl-084QyH4JGxLSLrlTAH6O4Q7sWX8Rx_D4_Ibr7-4tEsVcJ_8qf-Ou-egXKJ7Dj7O33ElNWoNHRCRixvpaqL_bnEQ6Wqd_oSm-vzoF1q9_7xxnT-0MyJbDSGXD7_xMIfuhy0HeGJKJjSb9XDBbZiYyRg8cTRnpE3DYE3mEDKb2L6RuLooKupS-6T6PDUP42zv82CcYUapwHPpK6WOCX3lXApBmWpLTcaCqrvNL7hjLnGWg";
		String CONTENT = "application/json";
/*		String currency = "AUD";
		String Loc = "en-AU";
		String reg = "AU";
		String offer = "0003P";*/
		ArrayList<Integer> index = VirtualMachine.getIndex(token);
		JsonArray ja1 = new JsonArray();
		for (int i = 0; i < index.size(); i++) {
			String resid = Resources.getResid(token, ((Integer) index.get(i)).intValue());
			String tok = "Bearer " + token;

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://management.azure.com" + resid + "?api-version=2017-03-30&$expand=instanceView")
					.addHeader("Authorization", tok).addHeader("Content-type", CONTENT).build();
			try {
				Response response = client.newCall(request).execute();

				Gson gson = new GsonBuilder().create();

				JsonObject job = (JsonObject) gson.fromJson(response.body().string(), JsonObject.class);
				JsonObject obj = job.get("properties").getAsJsonObject();

				JsonObject obj3 = job.get("properties").getAsJsonObject().getAsJsonObject().get("storageProfile")
						.getAsJsonObject().get("osDisk").getAsJsonObject().get("managedDisk").getAsJsonObject();
				JsonObject obj1 = job.get("properties").getAsJsonObject().get("hardwareProfile").getAsJsonObject();

				JsonObject obj2 = job.get("properties").getAsJsonObject().getAsJsonObject().get("storageProfile")
						.getAsJsonObject().get("osDisk").getAsJsonObject();
				String stats = job.get("properties").getAsJsonObject().get("instanceView")
						.getAsJsonObject().get("statuses").getAsJsonArray().get(1).getAsJsonObject().get("displayStatus").getAsString();
				String vm = obj.get("vmId").getAsString();
				String vmSz = obj1.get("vmSize").getAsString();
				String os = obj2.get("osType").getAsString();
				String loc = job.get("location").getAsString();
				String str = obj3.get("id").getAsString();
				String name = job.get("name").getAsString();
				Pattern pat = Pattern.compile("resourceGroups/(.*?)/providers");
				Matcher m = pat.matcher(str);
				while (m.find()) {
					str = m.group(1);
				}
				String[] det = VirtualMachine.getvmLive(token, resid);
				JsonElement je = new JsonParser().parse(det[0]);
				JsonArray percentageCPU = je.getAsJsonArray();
				je = new JsonParser().parse(det[1]);
				JsonArray NetworkIN = je.getAsJsonArray();
				je = new JsonParser().parse(det[2]);
				JsonArray NetworkOut = je.getAsJsonArray();
				je = new JsonParser().parse(det[3]);
				JsonArray DiskReadBytes = je.getAsJsonArray();
				je = new JsonParser().parse(det[4]);
				JsonArray DiskWriteBytes = je.getAsJsonArray();
				je = new JsonParser().parse(det[5]);
				JsonArray DiskReadOp = je.getAsJsonArray();
				je = new JsonParser().parse(det[6]);
				JsonArray DiskWriteOp = je.getAsJsonArray();
				ArrayList<Integer> list = new ArrayList<Integer>();
				for (int j = 0; j < 24; j++) {
					String per = percentageCPU.get(j).getAsJsonObject().get("timeStamp").getAsString();
					
					DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

					Date date = null;
					try {
						date = utcFormat.parse(per);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					pstFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Timestamp ts = Timestamp.valueOf(pstFormat.format(date));
					LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
					Timestamp current = Timestamp.valueOf(ldt);
					System.out.println(ts+"    "+current);
					if (ts.before(current)) {
						list.add(Integer.valueOf(j));
					}
				}
				int size = list.size() - 1;
				JsonObject jo = new JsonObject();
				jo.addProperty("VmID", vm);
				jo.addProperty("Resource Type", str);
				jo.addProperty("OS type", os);
				jo.addProperty("VMSize", vmSz);
				jo.addProperty("Location", loc);
				jo.addProperty("Status", stats);
				jo.addProperty("Name", name);
				jo.addProperty("Timestamp", percentageCPU.get(size).getAsJsonObject().get("timeStamp").getAsString());
				try {
					jo.addProperty("Percentage CPU",
							percentageCPU.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("Percentage CPU", Integer.valueOf(0));
				}
				try {
					jo.addProperty("NetworkIN", NetworkIN.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("NetworkIN", Integer.valueOf(0));
				}
				try {
					jo.addProperty("NetworkOut",
							NetworkOut.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("NetworkOut", Integer.valueOf(0));
				}
				try {
					jo.addProperty("Disk Read Bytes",
							DiskReadBytes.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("Disk Read Bytes", Integer.valueOf(0));
				}
				try {
					jo.addProperty("Disk Write Bytes",
							DiskWriteBytes.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("Disk Write Bytes", Integer.valueOf(0));
				}
				try {
					jo.addProperty("Disk Read Operation",
							DiskReadOp.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("Disk Read Operation", Integer.valueOf(0));
				}
				try {
					jo.addProperty("Disk Write Operation",
							DiskWriteOp.get(size).getAsJsonObject().get("average").getAsBigDecimal());
				} catch (Exception e) {
					jo.addProperty("Disk Write Operation", Integer.valueOf(0));
				}
				ja1.add(jo);
			} catch (Exception e) {
				System.out.println("rrrr");
			}
		}
		//System.out.println(ja1.toString());
	}
}