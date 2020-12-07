package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class GetAirCondition {
    private static ArrayList<AirCondition> airConditionArrayList = new ArrayList<AirCondition>();

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static ArrayList<AirCondition> getAirConditionArrayList(){
        return airConditionArrayList;
    }

    public static void getAirCondition()  {
        int page = 1;	// 페이지 초기값
        try{
            while(true){
                // parsing할 url 지정(API 키 포함해서)
                String url = "http://openAPI.seoul.go.kr:8088/4f50736349726c6133346f54427656/xml/ListAirQualityByDistrictService/1/25/";

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("row");

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = (Node) nList.item(temp);
                    AirCondition airCondition;
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){

                        Element eElement = (Element) nNode;
                        airCondition = new AirCondition(getTagValue("MSRSTENAME", eElement),
                                getTagValue("GRADE", eElement), getTagValue("OZONE", eElement),
                                getTagValue("CARBON", eElement), getTagValue("PM10", eElement),
                                getTagValue("PM25", eElement));

                        airConditionArrayList.add(airCondition);
                        /*
                        System.out.println("MSRADMCODE  : " + getTagValue("MSRADMCODE", eElement));
                        System.out.println("MSRADMCODE  : " + getTagValue("GRADE", eElement));
                        System.out.println("OZONE  : " + getTagValue("OZONE", eElement));
                        System.out.println("CARBON : " + getTagValue("CARBON", eElement));
                        System.out.println("PM10  : " + getTagValue("PM10", eElement));
                        System.out.println("PM25 : " + getTagValue("PM25", eElement));
                        */
                    }
                }

                page += 1;
                if(page > 25){
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
