package uz.http;

import uz.http.exceptions.HttpRequestSyntaxException;
import uz.http.enums.Method;
import uz.http.exceptions.HttpMethodParseException;

import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private Method method;
    private String path;
    private String version;
    private HashMap<String, String> headers;
    private String body;

    public Request(byte[] requestBytes, int len) {
        String requestStr = new String(requestBytes, 0, len);
        String[] parts = requestStr.split("\\r\\n\\r\\n" ,2);
        String header = parts[0];
        String body = parts.length > 1 ? parts[1] : null;
        String[] split = requestStr.split("\r\n");

        this.parseMethodAndPathAndVersion(split[0]);
        this.parseHeaders(split);
//        System.out.println(body + "<-body" + header + "<-header");
        this.body = body;
        System.out.println(this);
    }

    private void parseHeaders(String[] headers) {
        HashMap<String, String> map = new HashMap<>();

        int i = 1;
        while (i < headers.length && !headers[i].isEmpty()){
            String[] keyAndVal = headers[i].split(":", 2);

            System.out.println(Arrays.toString(keyAndVal));

            if (keyAndVal.length != 2) {
                throw new HttpRequestSyntaxException("Header xato");
            }

            String key = keyAndVal[0].trim();
            String val = keyAndVal[1].trim();

            if (key.isEmpty()){
                throw new HttpRequestSyntaxException("Header key xato");
            }

            if (val.isEmpty()){
                throw new HttpRequestSyntaxException("Header val xato");
            }

            map.put(key.toLowerCase(), val);
            i++;
        }

        this.headers = map;
    }

    private void parseMethodAndPathAndVersion(String line) {
        System.out.println("Raw header line: " + line);
        String[] split = line.split(" ");

        if (split.length != 3){
            throw new HttpRequestSyntaxException("First line should contains to \"METHOD PATH HTTP_VERSION\"");
        }

        this.method = parseMethod(split[0]);
        this.path = split[1];
        this.version = parseVersion(split[2]);
    }

    private String parseVersion(String version){
        return version;
    }

    private Method parseMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (Exception e) {
            throw new HttpMethodParseException("Normalni jo'nat http methodni");
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", version=" + version +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
