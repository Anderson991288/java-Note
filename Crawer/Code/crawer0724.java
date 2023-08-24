import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;


public class crawer0724 {
    public static void main(String[] args) throws Exception {
    	
        // Create a TrustManager that accepts any SSL certificate
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
        //Install the all-trusting TrustManager to the SSL context
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create a HostnameVerifier that accepts any host name
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }  
        };
        // Install the all-trusting HostVerifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    
        String url = "http://www.moneydj.com/funddj/yb/YP301000.djhtm";
        Document doc = Jsoup.connect(url).get();
       /* Elements divElements = doc.body().select("div.InternalSearch");
        System.out.println(divElements.select("td.InternalSearch-Head").text());
        
        Elements linkElements = divElements.select("table.InternalSearch-TD").select("a");
        for(Element linkElement : linkElements) {
            System.out.println(linkElement.text());
        }*/
        
     // �ؤ@�ӦW��divElements��Elements��H�A�ӹ�H�]�tHTML���ɤ��Ҧ��a��"div.InternalSearch"��ܾ���div����
        Elements divElements = doc.body().select("div.InternalSearch");
        Elements tableElements = divElements.select("table.InternalSearch-TB1");

        for(Element table : tableElements) {
            String title = table.select("td.InternalSearch-Head").text();
            Elements links = table.select("table.InternalSearch-TD").select("a");

            StringBuilder output = new StringBuilder(title);
            
            for(Element link : links) {
                output.append("\n").append(link.text());
            }
            System.out.println(output.toString()+"\n");
            
         }
}
            
}

    

