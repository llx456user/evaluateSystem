/**
 * 
 */
package com.tf.base.common.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Binder.
 * 
 * 特别支持Root对象是List的情形.
 * 
 */

public class JaxbUtil {

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@SuppressWarnings("rawtypes")
		@XmlAnyElement
		protected Collection collection;
	}

	/**
	 * @param xml
	 * @param types
	 * @return
	 */
	public static <T> T fromXml(String xml, Class<?>... types) {
		JaxbUtil resultBinder = new JaxbUtil(types);
		return resultBinder.fromXml(xml);
	}

	public static void main(String[] args) {
		// 
		String xml = "<?xml version='1.0' encoding='utf-8' ?><returnsms><returnstatus>Success</returnstatus><message>ok</message><remainpoint>26259</remainpoint><taskID>1324029</taskID><successCounts>1</successCounts></returnsms>";
		System.out.println(xml);

//		SmsResponse response = fromXml(xml, SmsResponse.class);
//		JSONObject object = JSONObject.fromObject(response);
//		System.out.println(object.toString());
		
        
        //将xml字符串转换为java对象  
		SmsSendResponse hotelObj = fromXml(xml, SmsSendResponse.class,CollectionWrapper.class);
		JSONObject object = JSONObject.fromObject(hotelObj);
		System.out.println(object.toString());

		// 创建java对象

		// Request request = new Request();
		// request.setUserName("系统ZX1");
		// request.setUserPassword("c0402b4e72ec5d386f3a9bdc3fe99859");
		//
		// OpenIssueOrderListRequest orderList = new
		// OpenIssueOrderListRequest();
		// orderList.setOrderBeginDateTime("2015-10-23T01:00:00");
		// orderList.setOrderEndDateTime("2015-10-23T23:00:00");
		// request.setOpenIssueOrderListRequest(orderList);
		//
		// //将java对象转换为XML字符串
		// String retXml = toXml(request,
		// Request.class,CollectionWrapper.class);
		// System.out.println("xml:"+retXml);
		//
		// String respXml =
		// SoapUtil.sendCtripMessage("http://flights.ws.ctrip.com/Flight.Order.SupplierOpenAPI/OpenIssueOrderList.asmx?WSDL",
		// retXml);
		//
		// //将xml字符串转换为java对象
		// Response hotelObj = fromXml(respXml,
		// Response.class,CollectionWrapper.class);
		// System.out.println(JSONUtil.toJson(hotelObj));
	}

	/**
	 * @param root
	 * @param types
	 * @return
	 */
	public static String toXml(Object root, Class<?>... types) {
		JaxbUtil requestBinder = new JaxbUtil(types);
		return requestBinder.toXml(root);
	}

	// 多线程安全的Context.
	private JAXBContext jaxbContext;

	/**
	 * @param types
	 *            所有需要序列化的Root对象的类型.
	 */
	public JaxbUtil(Class<?>... types) {
		try {
			jaxbContext = JAXBContext.newInstance(types);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建Marshaller, 设定encoding(可为Null).
	 */
	public Marshaller createMarshaller(String encoding) {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			if (StringUtils.isNotBlank(encoding)) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建UnMarshaller.
	 */
	public Unmarshaller createUnmarshaller() {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java Object.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java Object, 支持大小写敏感或不敏感.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml, boolean caseSensitive) {
		try {
			String fromXml = xml;
			if (!caseSensitive)
				fromXml = xml.toLowerCase();
			StringReader reader = new StringReader(fromXml);
			return (T) createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java Object->Xml, 特别支持对Root Element是Collection的情形.
	 */
	@SuppressWarnings("rawtypes")
	public String toXml(Collection root, String rootName, String encoding) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			createMarshaller(encoding).marshal(wrapperElement, writer);

			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String toXml(Object root) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(null).marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java Object->Xml.
	 */
	public String toXml(Object root, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(encoding).marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
