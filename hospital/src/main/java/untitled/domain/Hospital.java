package untitled.domain;

import untitled.HospitalApplication;
import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import lombok.Data;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;


@Entity
@Table(name="Hospital_table")
@Data

//<<< DDD / Aggregate Root
public class Hospital  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    private Long id;
    
    
    
    
    private String hospitalName;
    
    
    
    
    private Double lat;
    
    
    
    
    private Double lng;
    
    
    
    
    private Integer remain;
    
    
    
    
    private Integer totalBeds;
    
    
    
    
    private String hpid;

    @PostPersist
    public void onPostPersist(){
    
    }

    public static HospitalRepository repository(){
        HospitalRepository hospitalRepository = HospitalApplication.applicationContext.getBean(HospitalRepository.class);
        return hospitalRepository;
    }

    public void initializeBeds(){

        Map<String, List<String>> regionMap = sidoList();
        // 지역 데이터를 기반으로 API 호출 및 병원 데이터 저장
        for (Map.Entry<String, List<String>> entry : regionMap.entrySet()) {
            String sido = entry.getKey(); // 시도 (예: 서울특별시, 경기도)
            List<String> guList = entry.getValue(); // 구 리스트

            for (String gu : guList) {
                System.out.println("시도: " + sido + " 시군구: "+ gu);
                // 시도와 구를 기반으로 API 호출
                List<Hospital> hospitalDataList = remainBedsApiParseXml(sido, gu);

                // API로부터 데이터가 정상적으로 받아지면 저장
                if (hospitalDataList != null && !hospitalDataList.isEmpty()) {
                    HospitalRepository hospitalRepository = repository();

                    for (Hospital hospital : hospitalDataList) {
                        hospitalRepository.save(hospital); // 병원 데이터 저장
                    }

                    System.out.println(sido + " " + gu + ": " + hospitalDataList.size() + " hospitals saved.");
                } else {
                    System.out.println(sido + " " + gu + ": No data found from API or an error occurred.");
                }
            }
        }
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    public List<Hospital> remainBedsApiParseXml(String sido, String sigungu) {
        String xmlData = null;
        List<Hospital> bedsDtoList = new ArrayList<>();
        try {
            xmlData = getRemainbedsbySido(sido, sigungu);
            if (xmlData == null || xmlData.contains("<errMsg>") || xmlData.contains("<returnAuthMsg>")) {
                System.err.println("API 요청 중 오류 발생: " + xmlData);
                return null;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

            NodeList itemListNodes = doc.getElementsByTagName("item");

            for (int i = 0; i < itemListNodes.getLength(); i++) {
                Node itemListNode = itemListNodes.item(i);

                if (itemListNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemListElement = (Element) itemListNode;
                    Hospital bedsDto = new Hospital();
                    bedsDto.setHospitalName(getTagValue(itemListElement, "dutyName"));
                    bedsDto.setHpid(getTagValue(itemListElement, "hpid"));
                    // 임시로 일반 병상 가용 정보를 가져옴
                    int tempRemain = Integer.parseInt(getTagValue(itemListElement, "hvec"));
                    if (tempRemain <= 0) { bedsDto.setRemain(0); } else{ bedsDto.setRemain(tempRemain); }
                    bedsDto = getHospitalLocationByhpid(bedsDto);
                    
                    System.out.println(bedsDto);
                    bedsDtoList.add(bedsDto);
                } else {
                    System.out.println("[ExternalApiService - EmbedsApiParseXml] 예상 하지 못한 에러 발생.");
                }
            }
        } catch (Exception exception){
            System.out.println(exception);
        }
        return bedsDtoList;
    }

    private String getPotalXmlData(String baseUrl, Map<String, String> params) throws Exception {
        String serviceKey = "ZpJ0Lq1T10c%2B8TqXfNjrszx74eWsrqKsIvTLnSDckkTwyVxJmwF2hHH5LDuFhl%2FqgY9Y7H5Aez%2F4KFp3Ild2qA%3D%3D";
        String urlStr = baseUrl + "?serviceKey=" + serviceKey;

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        for (Map.Entry<String, String> param : params.entrySet()) {
            urlStr += "&" + URLEncoder.encode(param.getKey(), "UTF-8") + "=" + URLEncoder.encode(param.getValue(), "UTF-8");
        }

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        return sb.toString();
    }

    private Hospital getHospitalLocationByhpid(Hospital hospital) throws Exception {
        String API_URL = "https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire";
        Map<String, String> params = new HashMap<>();
        params.put("HPID", hospital.getHpid());

        String xmlData = getPotalXmlData(API_URL, params);

        if (xmlData == null || xmlData.contains("<errMsg>") || xmlData.contains("<returnAuthMsg>")) {
            System.err.println("API 요청 중 오류 발생: " + xmlData);
            return null;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

        NodeList itemListNodes = doc.getElementsByTagName("item");
        for (int i = 0; i < itemListNodes.getLength(); i++) {
            Node itemListNode = itemListNodes.item(i);

            if (itemListNode.getNodeType() == Node.ELEMENT_NODE) {
                Element itemListElement = (Element) itemListNode;
                hospital.setLat(Double.parseDouble(getTagValue(itemListElement, "wgs84Lat")));
                hospital.setLng(Double.parseDouble(getTagValue(itemListElement, "wgs84Lon")));
                hospital.setTotalBeds(Integer.parseInt(getTagValue(itemListElement, "hpbdn")));
            } else {
                System.out.println("[ExternalApiService - EmbedsApiParseXml] 예상 하지 못한 에러 발생.");
            }
        }

        return hospital;
    }

    private String getRemainbedsbySido(String sido, String sigungu) throws Exception {
        String API_URL = "https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire";
        Map<String, String> params = new HashMap<>();
        params.put("STAGE1", sido);
        params.put("STAGE2", sigungu);

        return getPotalXmlData(API_URL, params);
    }

    public static void bedsUpdate(HospitalizationApproved hospitalizationApproved){
        
        repository().findById(hospitalizationApproved.getBedsId()).ifPresent(beds->{
            
            if (beds.getRemain() > 0) {
                beds.setRemain(beds.getRemain()-1);
                repository().save(beds);
            } else {
                // 보상 트랜젝션을 구현해야할지
                System.out.println("남은 자리가 없습니다.");
            }

         });  
    }

    public static void bedsUpdate(Discharged discharged){
        
        repository().findById(discharged.getBedsId()).ifPresent(beds->{
            
            if (beds.getRemain()+1 > beds.getTotalBeds()){
                beds.setRemain(beds.getRemain()+1);
                repository().save(beds);
            } else {
                System.out.println("잘못된 데이터: 전체 병상보다 가용 병상이 많을 수 없음.");
                beds.setRemain(beds.getTotalBeds());
                repository().save(beds);
            }
         });
        
    }

    public Map<String, List<String>> sidoList() {
        // 데이터를 저장할 Map
        Map<String, List<String>> regionMap = new HashMap<>();

        // 서울특별시 구 리스트
        List<String> seoulDistricts = new ArrayList<>();
        seoulDistricts.add("종로구");
        seoulDistricts.add("중구");
        seoulDistricts.add("용산구");
        seoulDistricts.add("성동구");
        seoulDistricts.add("광진구");
        seoulDistricts.add("동대문구");
        seoulDistricts.add("중랑구");
        seoulDistricts.add("성북구");
        seoulDistricts.add("강북구");
        seoulDistricts.add("도봉구");
        seoulDistricts.add("노원구");
        seoulDistricts.add("은평구");
        seoulDistricts.add("서대문구");
        seoulDistricts.add("마포구");
        seoulDistricts.add("양천구");
        seoulDistricts.add("강서구");
        seoulDistricts.add("구로구");
        seoulDistricts.add("금천구");
        seoulDistricts.add("영등포구");
        seoulDistricts.add("동작구");
        seoulDistricts.add("관악구");
        seoulDistricts.add("서초구");
        seoulDistricts.add("강남구");
        seoulDistricts.add("송파구");
        seoulDistricts.add("강동구");

        // 성남시 구 리스트
        List<String> seongnamDistricts = new ArrayList<>();
        seongnamDistricts.add("수정구");
        seongnamDistricts.add("중원구");
        seongnamDistricts.add("분당구");

        // Map에 지역과 구 리스트를 저장
        regionMap.put("성남시", seongnamDistricts);
        regionMap.put("서울특별시", seoulDistricts);

        // 출력
        System.out.println("지역별 구 리스트:");
        for (Map.Entry<String, List<String>> entry : regionMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return regionMap;
    }

}
