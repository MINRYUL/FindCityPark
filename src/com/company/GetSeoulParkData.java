package com.company;

import com.sun.deploy.net.MessageHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class GetSeoulParkData{
    private static ArrayList<Park> parkArrayList = new ArrayList<Park>();

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static ArrayList<Park> getParkArrayList(){
        return parkArrayList;
    }

    public static void getParkData()  {
        int page = 1;	// 페이지 초기값
        try{
            while(true){
                // parsing할 url 지정(API 키 포함해서)
                String url = "http://openAPI.seoul.go.kr:8088/6d724a5a54726c61373854676b7a58/xml/SearchParkInfoService/1/150/"+page;

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("row");

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = (Node) nList.item(temp);
                    Park park;
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){

                        Element eElement = (Element) nNode;
                        park = new Park(Integer.parseInt(getTagValue("P_IDX", eElement)), getTagValue("P_PARK", eElement),
                                getTagValue("P_LIST_CONTENT", eElement), getTagValue("P_ADDR", eElement),
                                getTagValue("P_ZONE", eElement), getTagValue("P_ADMINTEL", eElement),
                                getTagValue("TEMPLATE_URL", eElement), getTagValue("MAIN_EQUIP", eElement),
                                getTagValue("P_NAME", eElement));

                        parkArrayList.add(park);
                        /*
                        System.out.println("공원번호  : " + getTagValue("P_IDX", eElement));
                        System.out.println("공원명  : " + getTagValue("P_PARK", eElement));
                        System.out.println("공원개요 : " + getTagValue("P_LIST_CONTENT", eElement));
                        System.out.println("공원주소  : " + getTagValue("P_ADDR", eElement));
                        System.out.println("지역 : " + getTagValue("P_ZONE", eElement));
                        System.out.println("전화번호 : " + getTagValue("P_ADMINTEL", eElement));
                        System.out.println("바로가기  : " + getTagValue("TEMPLATE_URL", eElement));
                        System.out.println("주요시설 : " + getTagValue("MAIN_EQUIP", eElement));
                        System.out.println("관리부서 : " + getTagValue("P_NAME", eElement));
                        */
                    }
                }

                page += 1;
                if(page > 132){
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
