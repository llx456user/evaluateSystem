package com.tf.base.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	    private static final String APPLICATION_JSON = "application/json";
	    
	    /**
	     * http get方法调用
	     * @param url
	     * @return
	     * @throws Exception
	     */
	    public static String executeGet(String url , int timeout) throws Exception {  
	        BufferedReader in = null;  
	  
	        String content = null;  
	        try {  
	            // 定义HttpClient  
	            HttpClient client = new DefaultHttpClient(); 
	            RequestConfig requestConfig = RequestConfig.custom()  
		                .setConnectTimeout(timeout).setConnectionRequestTimeout(1000)  
		                .setSocketTimeout(timeout).build();  
	            // 实例化HTTP方法  
	            HttpGet request = new HttpGet(); 
	            request.setConfig(requestConfig);
	            request.setURI(new URI(url));  
	            HttpResponse response = client.execute(request);
	  
	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));  
	            StringBuffer sb = new StringBuffer("");  
	            String line = "";  
	            String NL = System.getProperty("line.separator");  
	            while ((line = in.readLine()) != null) {  
	                sb.append(line + NL);  
	            }  
	            in.close();  
	            content = sb.toString();  
	        } finally {  
	            if (in != null) {  
	                try {  
	                    in.close();// 最后要关闭BufferedReader  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            return content;  
	        }  
	    }  
	    
	    
		public static String http(String url, Map<String, String> params) {

		URL u = null;

		HttpURLConnection con = null;

		//构建请求参数

		StringBuffer sb = new StringBuffer();

		if(params!=null){

		for (Entry<String, String> e : params.entrySet()) {

		sb.append(e.getKey());

		sb.append("=");

		sb.append(e.getValue());

		sb.append("&");

		}

		sb.substring(0, sb.length() - 1);

		}

		System.out.println("send_url:"+url);

		System.out.println("send_data:"+sb.toString());

		//尝试发送请求

		try {

		u = new URL(url);

		con = (HttpURLConnection) u.openConnection();

		con.setRequestMethod("POST");

		con.setDoOutput(true);

		con.setDoInput(true);

		con.setUseCaches(false);

		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

		osw.write(sb.toString());

		osw.flush();

		osw.close();

		} catch (Exception e) {

		e.printStackTrace();

		} finally {

		if (con != null) {

		con.disconnect();

		}

		}

		 

		//读取返回内容

		StringBuffer buffer = new StringBuffer();

		try {

		BufferedReader br = new BufferedReader(new InputStreamReader(con

		.getInputStream(), "UTF-8"));

		String temp;

		while ((temp = br.readLine()) != null) {

		buffer.append(temp);

		buffer.append("\n");

		}

		} catch (Exception e) {

		e.printStackTrace();

		}

		 

		return buffer.toString();

		}
		
		/**==================================一些发送http请求的通用方法===================================================**/
		/**=====================================================================================**/
		/**=====================================================================================**/
		/**=====================================================================================**/
		private static int debug = 0;
		
		/**
		 * 打印信息的简便方法，用于一些异常信息的输出
		 * @param str
		 * @return
		 */
		public static void err(Exception e){
			System.err.println("异常:"+e.getMessage());
			if(debug == 1){
				e.printStackTrace();
			}
			//写异常流水
		}	
		
		/**
		 * 请求转发并返回结果
		 * 
		 * @param response
		 * @param json
		 */
		public static String transferPost(String data,String serverUrl) {
			// 服务地址
			URL url;
			try {
				url = new URL(serverUrl);
			} catch (Exception e1) {
				e1.printStackTrace();
				err(e1);
				return getReturnMessage("fail","fail");
			}
			// 设定连接的相关参数
			HttpURLConnection connection = null;
			String strResponse = "";
			BufferedReader reader = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(30 * 1000);// 30秒
				connection.setReadTimeout(30 * 1000);// 30秒
				connection.setDoOutput(true);
				connection.setUseCaches(false); 
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Connection", "close");

				// 输出
				OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
				output.write(data);
				output.flush();
				output.close();

				// 获取服务端的反馈
				String strLine = "";
				InputStream in = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				while ((strLine = reader.readLine()) != null) {
					strResponse += new String(strLine.getBytes(),"utf-8") + "\n";
				}
			} catch (ProtocolException e) {
				e.printStackTrace();
				err(e);
			} catch (IOException e) {
				e.printStackTrace();
				err(e);
			} finally{
				try{
					if(reader != null){
						reader.close();
					}
				}catch(Exception e){}
				try{
					connection.disconnect();
				}catch(Exception e){
					err(e);
				}
			}
			if(strResponse == null || strResponse.equals(""))
				return getReturnMessage("fail","fail");
			return strResponse;
		}	
		
		/**
		 * http post请求
		 * @param url
		 * @param json
		 * @param timeOut
		 * @return
		 * @throws Exception
		 */
		public static String post(String url, String json) throws Exception {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = null;
			String result = null;
			try {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-Type", "text/plain");
				httpPost.setEntity(new StringEntity(json, "UTF-8"));
				httpResponse = httpClient.execute(httpPost);
				HttpEntity entity = httpResponse.getEntity();
				result = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(null != httpResponse) {
					httpResponse.close();
				}
				if(null != httpClient) {
					httpClient.close();
				}
			}
			return result;
		}
		
		
		
		
		public static String getReturnMessage(String status,String info){
			StringBuffer sbf = new StringBuffer();
			sbf.append("{ ");
			sbf.append("\"code\":\"" + status + "\",");
			sbf.append("\"meg\":\"" + info +"\",");
			sbf.append("\"status\":\"99\"");
			sbf.append(" }");
			return sbf.toString();
		}

	    public static String httpPostWithJSON(String url, String json) throws Exception {

	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        RequestConfig requestConfig = RequestConfig.custom()  
	                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)  
	                .setSocketTimeout(5000).build();  
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.setConfig(requestConfig); 
	        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        httpPost.addHeader("Accept-Encoding", "*"); //必须写
	        ContentType type = ContentType.create(APPLICATION_JSON,Charset.forName("UTF-8"));
			StringEntity reqEntity = new StringEntity(json, type);
			httpPost.setEntity(reqEntity);
	        CloseableHttpResponse response = null;
			try {
				// 执行请求
				response = httpclient.execute(httpPost);
				// 转换结果
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity, "UTF-8");
				// 消费掉内容
				EntityUtils.consume(entity);
				return html;
			} catch (IOException ex) {
				ex.printStackTrace();
				return "-1";
			} finally {
				if (response != null) {
					try {
						response.close();
					} catch (IOException e) {
					}
				}
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
	    }
	    public static String doPost(String url,Map<String,String> map,String charset){  
			CloseableHttpClient httpClient = null;
	        HttpPost httpPost = null;  
	        String result = null;  
	        try{  
	        	httpClient = HttpClients.createDefault();
	            httpPost = new HttpPost(url);  
	            //设置参数  
	            List<NameValuePair> list = new ArrayList<NameValuePair>();  
	            Iterator iterator = map.entrySet().iterator();  
	            while(iterator.hasNext()){  
	                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	            }  
	            if(list.size() > 0){  
	                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
	                httpPost.setEntity(entity);  
	            }  
	            HttpResponse response = httpClient.execute(httpPost);  
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,charset);  
	                }  
	            }  
	        }catch(Exception ex){  
	            ex.printStackTrace();  
	        }  
	        return result;  
	    }  
	    
	    public static void main(String[] args){
	    	
	    	try {
	    		
	    	 
	    		
	    		
	    		String url = "http://localhost:8080/ordersystem/refund/receiveTongyeRefundApply" ;
//	    		String res = executeGet(url, 5000);
//	    		if ("success".equalsIgnoreCase(res)) {
//	    			System.out.println("res=成功"+res);
//				}else{
//					System.out.println("res=失败"+res);
//				}
	    		
	    		
	    		String data = "{\"mainRetreat\":{\"createTime\":\"2016-12-0916:05:17.0\",\"originalPassengerNum\":\"7\",\"submitFinanceTime\":\"\",\"retreatPerson\":\"司瑞昶\",\"newPnr\":\"QAZWSX\",\"retreatType\":\"2\",\"channelOrderNo\":\"jp14799153212515496\",\"newOrderNo\":\"123456789\",\"passengerOffer\":\"500\",\"retreat_id\":\"lm_3204\",\"originalPnr\":\"8BI7PU\",\"interfacePwd\":\"X+zDWFhnblw=\",\"orderChannelTotalPrice\":\"4718\",\"interfaceUser\":\"longmao\",\"channelForeignCurrency\":null,\"channelForeignExchageRate\":\"0.8924\"},\"customerService\":{\"customerService\":\"司瑞昶\",\"requireType\":\"2\",\"description\":\"!!!!!!!!!!!!\",\"telephone\":\"18974552780\",\"contactPerson\":\"李显东\"},\"passengerList\":[{\"birthday\":\"1970-10-29\",\"exchangeRate\":\"\",\"supplierRefundOperTime\":\"2016-12-09\",\"newPnr\":\"QAZWSX\",\"supplierReturnAmt\":null,\"currency\":\"THB\",\"cardExpired\":\"2021-04-14\",\"pasType\":\"1\",\"name\":\"KUETCHE/ERNEST\",\"gender\":\"1\",\"operType\":\"1\",\"cardNum\":\"13fv29507\",\"serviceFee\":\"30.00\",\"supplierTax\":null,\"cardType\":\"2\",\"cardIssuePlace\":\"FR\",\"realityPrice\":\"\",\"supplierOffer\":\"100\",\"supplierPrice\":null,\"ticketNo\":\"8291135533756\",\"newTicketNo\":\"11111222223333\",\"nationality\":\"FR\",\"passengerOffer\":\"300.0\",\"originalPnr\":\"8BI7PU\",\"policyRebateAmt\":null},{\"birthday\":\"2006-11-26\",\"exchangeRate\":\"\",\"supplierRefundOperTime\":\"2016-12-09\",\"newPnr\":\"QAZWSX\",\"supplierReturnAmt\":null,\"currency\":\"THB\",\"cardExpired\":\"2021-04-11\",\"pasType\":\"2\",\"name\":\"KHENSOUS/ENZO\",\"gender\":\"1\",\"operType\":\"1\",\"cardNum\":\"16at24865\",\"serviceFee\":\"30.00\",\"supplierTax\":null,\"cardType\":\"2\",\"cardIssuePlace\":\"FR\",\"realityPrice\":\"\",\"supplierOffer\":\"70\",\"supplierPrice\":null,\"ticketNo\":\"8291135533759\",\"newTicketNo\":\"2589631471254\",\"nationality\":\"FR\",\"passengerOffer\":\"200.0\",\"originalPnr\":\"8BI7PU\",\"policyRebateAmt\":null}],\"segmentList\":[{\"depTime\":\"2016-12-2310:00:00\",\"retreatType\":\"2\",\"carrierName\":\"曼谷航空\",\"carrier\":\"PG\",\"arrTime\":\"2016-12-2311:20:00\",\"vtype\":\"1\",\"aircraftCode\":\"320\",\"flightNumber\":\"PG216\",\"cabinGrade\":\"V\"},{\"depTime\":\"2016-12-2313:20:00\",\"retreatType\":\"2\",\"carrierName\":\"曼谷航空\",\"carrier\":\"PG\",\"arrTime\":\"2016-12-2314:45:00\",\"vtype\":\"2\",\"aircraftCode\":\"320\",\"flightNumber\":\"PG267\",\"cabinGrade\":\"N\"},],\"retreatRescheduled\":{\"remitTime\":\"2016-12-1415:00:00.0\",\"changeType\":\"1\",\"remitter\":\"QADFDD\",\"collectionMode\":\"1\",\"rescheduledPerson\":\"2\"},\"retreatFinance\":{\"actualIncomeAmt\":\"500\"},\"retreatAttachment\":[{\"fileName\":\"\",\"image\":\"\",\"uploadTime\":\"\",\"operator\":\"\"}]}"; 
	    		try {
					String result = transferPostJson(data, url);
					
					System.out.println(result);
				} catch (Exception e) {
					// TODO: handle exception
				}

	    		
				
	    		
			} catch (Exception e) {
				System.out.println("e:" + e.getMessage());
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    /**
		 * 请求转发并返回结果
		 * 
		 * @param response
		 * @param json
		 */
		public static String transferPostJson(String data,String serverUrl) {
			// 服务地址
			URL url;
			try {
				url = new URL(serverUrl);
			} catch (Exception e1) {
				e1.printStackTrace();
				err(e1);
				return getReturnMessage("fail","fail");
			}
			// 设定连接的相关参数
			HttpURLConnection connection = null;
			String strResponse = "";
			BufferedReader reader = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(30 * 1000);// 30秒
				connection.setReadTimeout(300 * 1000);// 300秒
				connection.setDoOutput(true);
				connection.setUseCaches(false); 
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Connection", "close");

				// 输出
				OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
				output.write(data);
				output.flush();
				output.close();

				// 获取服务端的反馈
				String strLine = "";
				InputStream in = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				while ((strLine = reader.readLine()) != null) {
					strResponse += new String(strLine.getBytes(),"utf-8") + "\n";
				}
			} catch (ProtocolException e) {
				e.printStackTrace();
				err(e);
			} catch (IOException e) {
				e.printStackTrace();
				err(e);
			} finally{
				try{
					if(reader != null){
						reader.close();
					}
				}catch(Exception e){}
				try{
					connection.disconnect();
				}catch(Exception e){
					err(e);
				}
			}
			if(strResponse == null || strResponse.equals(""))
				return getReturnMessage("fail","fail");;
			return strResponse;
		}	
		
		/**
		 * 
		 * @param url
		 * @param content
		 * @param contentType
		 * @param charset
		 * @return
		 */
		public static String postToUrl(String url, String content,
				String contentType, String charset) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			// 设置内容
			ContentType type = ContentType.create(contentType,
					Charset.forName(charset));
			StringEntity reqEntity = new StringEntity(content, type);
			httpPost.setEntity(reqEntity);
			/*httpPost.addHeader("Host", "xxxx.com");
			httpPost.addHeader("Referer", "http://www.xxxx.com");*/
			httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE .0; Windows NT 6.1; Trident/4.0; SLCC2;)");
			httpPost.addHeader("Accept-Encoding", "*");
			
			// 超时设置
			// ConnectionRequestTimeout，从连接池获取连接的超时时间
			// ConnectTimeout，连接服务器的超时时间
			// SocketTimeout，传输数据的超时时间，是两个数据包之间的间隔时间，并非整体传输时间。如果一直有数据传输，不会触发此异常。
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(30000).setConnectTimeout(30000)
					.setSocketTimeout(300000).build();
			httpPost.setConfig(requestConfig);
			
			CloseableHttpResponse response = null;
			try {
				// 执行请求
				response = httpclient.execute(httpPost);

				// 转换结果
				HttpEntity entity1 = response.getEntity();
				String html = EntityUtils.toString(entity1, charset);

				// 消费掉内容
				EntityUtils.consume(entity1);
				return html;
			} catch (IOException ex) {
				ex.printStackTrace();
				JSONObject json = new JSONObject();
				json.put("resultCode", "-1");
				json.put("resultMsg", "网络异常");
				return json.toString();
			} finally {
				if (response != null) {
					try {
						response.close();
					} catch (IOException e) {
					}
				}
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
		}
		
		public static String postToUrl(String url, String content) {
			return postToUrl(url, content, "application/x-www-form-urlencoded",
					"UTF-8");
		}
}
