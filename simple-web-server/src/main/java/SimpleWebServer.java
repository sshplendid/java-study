import request.Request;
import response.Response;
import response.Response.Response;

import javax.annotation.PreDestroy;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;

public class SimpleWebServer {
    private ServerSocket serverSocket;
    private HashMap<String, String> resources;
    private final String resourcePath;

    public static void main(String[] args) throws IOException {
        SimpleWebServer server = new SimpleWebServer(80);
        server.open();
    }
    public SimpleWebServer(int port) throws IOException {
        resourcePath = "C:\\dev\\sample";
        this.loadResources(resourcePath);
        serverSocket = new ServerSocket(port);
        System.out.println("Server is running...");
    }

    public void open() throws IOException {
        while(true) {
            System.out.print("waiting... since " + LocalDateTime.now());
            Socket socket = serverSocket.accept();
            if(socket.isConnected()) {
                System.out.println(String.format(": connected from %s:%s", socket.getInetAddress(), socket.getPort()));
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println(".");
                int inputSize = dis.available();
                byte[] inputBytes = new byte[inputSize];
                dis.read(inputBytes);
                StringBuilder str = new StringBuilder();
                for(byte b : inputBytes) {
                    str.append(String.valueOf((char)b));
                }
                System.out.println("== request ==");
                System.out.println(str);
                if (str.toString().contains("HTTP")) {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.write(getDefaultResponseMessage().getBytes());
                    dos.flush();
                    dos.close();
                }
                dis.close();
            }
            if (!socket.isClosed())
                socket.close();
        }
    }

    private String getDefaultResponseMessage() {
        String body =  "<html><body><h1>Simple Web Server!</h1></body></html>";
        String response = "HTTP/1.1 200 OK\r\n"
                + "Server: SimpleWebServer\r\n"
                + "Content-Type: text/html;charset=utf-8\r\n"
                + "Content-Length: "+body.getBytes().length+"\r\n"
                + "\r\n"
                + body
                + "\r\n";

        return response;
    }

    private void loadResources(String resourcePath) throws FileNotFoundException {
        File root = new File(resourcePath);

        if(!root.isDirectory()) {
            throw new IllegalArgumentException("위치를 찾을 수 없습니다.: '" + resourcePath + "'");
        }

        this.resources = new HashMap<String, String>();
        readFile(root);
    }

    private void readFile(File root) throws FileNotFoundException {
        String path = root.getPath();

        if(root.isFile()) {
            BufferedReader br = new BufferedReader(new FileReader(root));
            String result = br.lines().reduce((a, b) -> a + "\n" + b).get();
            resources.put(getResourcePathKey(root.getPath()), result);
            System.out.println("File: " + getResourcePathKey(root.getPath()));
        } else if(root.isDirectory()){
            for(File f: root.listFiles()) {
                readFile(f);
            }
        }
    }

    private String getResourcePathKey(String path) {

        path = path.replace(this.resourcePath, "");
        path = path.replaceAll("\\\\", "/");

        return path;
    }

    private Response route(Request request) {
        String uri = request.getUri();
        if(this.resources.containsKey(uri)) {
            return Response.ok(this.resources.get(uri));
        }
        return Response.notFound();


    }

                           @PreDestroy
    void tearDown() throws IOException {
        if(serverSocket != null) serverSocket.close();
    }
}
