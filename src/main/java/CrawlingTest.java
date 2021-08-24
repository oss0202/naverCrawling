import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlingTest {
    public static void main(String[] args) throws JsonProcessingException {
//        JSONObject jsonObject = exchange();
////        System.out.println(jsonObject);
//
//        HashMap<String, Object> map = (HashMap<String, Object>) new ObjectMapper().readValue(jsonObject.toString(), Map.class);
////        List<Object> list = map.keySet().stream().map(s -> map.get(s)).collect(Collectors.toList());
////        list.stream().forEach(System.out::println);
//        List<CrwalingDto> crwalingDtos = new ArrayList<>();
//        CrwalingDto crwalingDto;
//        map.entrySet().stream().forEach(entry -> {
//            crwalingDtos.add(new CrwalingDto(entry.getKey(), entry.getValue()));
//
//        });
//        list.get(1);

        List<CrwalingDto> crwalingDtos = listExchange();
        crwalingDtos.forEach(var -> {
            System.out.println("country : " + var.getCountry() + ", sale : " + var.getSale() + ", link : " + var.getLink());
        });

    }
    public static JSONObject exchange() {
        JSONObject result = new JSONObject();
        JSONArray arr = new JSONArray();

        // 네이버 환율정보 페이지
        String url = "https://finance.naver.com/marketindex/exchangeList.nhn";
        Document doc = null;

        try {
            // 환율정보 스크래핑
            doc = Jsoup.connect(url).get();
            // 국가명, 환율
            Elements country = doc.select(".tit");
            Elements sale = doc.select(".sale");

            for (int i = 0; i < country.size(); i++) {

                Element country_el = country.get(i);
                Element sale_el = sale.get(i);
                JSONObject obj = new JSONObject();

                obj.put("country", country_el.text());
                obj.put("sale", sale_el.text());
                arr.add(obj);

            }
//            result.put("result", "success");
            result.put("exchange", arr);

        } catch (IOException e) {

//            result.put("result", "fail");
            e.printStackTrace();

        }

        return result;
    }

    public static List<CrwalingDto> listExchange() {
        JSONObject result = new JSONObject();
        JSONArray arr = new JSONArray();
        List<CrwalingDto> results = new ArrayList<>();
        CrwalingDto crwalingDto;

        // 네이버 환율정보 페이지
        String url = "https://finance.naver.com/marketindex/exchangeList.nhn";
        Document doc = null;

        try {
            // 환율정보 스크래핑
            doc = Jsoup.connect(url).get();
            // 국가명, 환율
            Elements country = doc.select(".tit");
            Elements sale = doc.select(".sale");

            for (int i = 0; i < country.size(); i++) {

                Element country_el = country.get(i);
                Elements link = country_el.select("a[href]");
                Element sale_el = sale.get(i);
                JSONObject obj = new JSONObject();

                crwalingDto = new CrwalingDto(country.get(i).text(), sale.get(i).text(), link.attr("href"));

                obj.put("country", country_el.text());
                obj.put("sale", sale_el.text());

//                arr.add(obj);
                results.add(crwalingDto);

            }

        } catch (IOException e) {

//            result.put("result", "fail");
            e.printStackTrace();

        }

        return results;
    }
}
