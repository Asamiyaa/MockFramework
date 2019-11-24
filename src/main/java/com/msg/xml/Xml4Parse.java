package com.msg.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *  @author  YangWenjun
 *  @date   2018年11月8日
 *  
 * **/
public class Xml4Parse {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String parseFile = "TestParse.xml";
		DomParse dp = new DomParse();
		dp.praseXmlForDom(parseFile);
	}
}



class DomParse{
	public  void praseXmlForDom(String fileName) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(fileName);
	
		NodeList users = document.getChildNodes(); 				 // org.w3c.dom.NodeList
		
		for (int i = 0; i < users.getLength(); i++) {  	 				 //userList - user
			Node user = users.item(i);                         				 // org.w3c.dom.Node
			NodeList userInfo = user.getChildNodes();
			System.out.println(user + "--" + userInfo);
			for (int j = 0; j < userInfo.getLength(); j++) { 		     // userlist <user
				Node node = userInfo.item(j); 
				NodeList userMeta = node.getChildNodes(); 
				System.out.println(node + "--" + userMeta);
				for (int k = 0; k < userMeta.getLength(); k++) {
					    if (userMeta.item(k).getNodeName() != "#text") {
						System.out.println(userMeta.item(k).getNodeName() + ":" + userMeta.item(k).getTextContent());
					}
				}
			}
		}
   }
}

/**
 * 四种方式均可以实现写入，详见：https://blog.csdn.net/DXZCZH/article/details/51783573
 *//*
// 修改xml中数据 详见：https://blog.csdn.net/dlutbrucezhang/article/details/9946733

// xml解析/////////////////////////////////////////////////////////
static void method17() throws ParserConfigurationException, SAXException, IOException {
	// praseXmlForDom("TestParse.xml");

	// JdomDemo jd = new Test1().new JdomDemo(); //内部类
	// jd.praseXmlForJdom("TestParse.xml");

	// new Test1().new JdomDemo().new
	// Dom4jDemo().praseXmlFordom4j("TestParse.xml"); //内部类
	new Test1().new JdomDemo().new SAXDemo().praseXmlForSax("TestParse.xml");

}

// Jdom解析
class JdomDemo {
	public void praseXmlForJdom(String fileName) {
		SAXBuilder builder = new SAXBuilder();
		try {
			org.jdom2.Document document = builder.build(fileName);
			Element users = document.getRootElement();
			List userList = users.getChildren("user");

			for (int i = 0; i < userList.size(); i++) { // 上面用到了length..
				Element user = (Element) userList.get(i);
				List userInfo = user.getChildren();

				for (int j = 0; j < userInfo.size(); j++) {
					System.out.println(
							((Element) userInfo.get(j)).getName() + ":" + ((Element) userInfo.get(j)).getValue());

					// 从java-xml 中写
					// System.out.println(((Element)userInfo.get(j)).getAttributeValue("tom")
					// );
				}
			}
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// dom4j 解析
	class Dom4jDemo {
		public void praseXmlFordom4j(String fileName) {
			File inputXml = new File(fileName);
			SAXReader reader = new SAXReader();

			try {
				org.dom4j.Document document = reader.read(inputXml);
				org.dom4j.Element users = document.getRootElement();
				for (Iterator i = users.elementIterator(); i.hasNext();) {
					org.dom4j.Element user = (org.dom4j.Element) i.next();
					for (Iterator j = user.elementIterator(); j.hasNext();) {
						org.dom4j.Element node = (org.dom4j.Element) j.next();
						System.out.println(node.getName() + ":" + node.getText());
					}
				}

			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// SAX解析 -- 和dom无需添加jar包
	class SAXDemo {
		public void praseXmlForSax(String fileName) {
			SAXParserFactory saxfac = SAXParserFactory.newInstance();
			SAXParser saxParser;
			try {
				saxParser = saxfac.newSAXParser();
				FileInputStream is = new FileInputStream(fileName);
				saxParser.parse(is, new SAXDemo().new MySAXHandler());
			} catch (ParserConfigurationException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		class MySAXHandler extends DefaultHandler {
			boolean hasAttribute = false;
			Attributes attributes = null;

			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("users")) {
					return;
				}

				if (qName.equals("user")) {
					return;
				}

				if (attributes.getLength() > 0) {
					this.attributes = attributes;
					this.hasAttribute = true;
				}
			}

			public void endElement(String uri, String localName, String qName) throws SAXException {
				if (hasAttribute && (attributes != null)) {
					for (int i = 0; i < attributes.getLength(); i++) {
						System.out.println(attributes.getQName(0) + ":" + attributes.getValue(0));
					}
				}
			}

			public void characters(char ch[], int start, int length) throws SAXException {
				System.out.println(new String(ch, start, length));
			}
		}
	}
}*/
