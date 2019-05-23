import request.Request;
import response.Response;

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
//        resourcePath = "C:\\dev\\sample";
        resourcePath = "/Users/sshplendid/IdeaProjects/java-study/simple-web-server/src/test/resources/sample";
        this.loadResources(resourcePath);
        serverSocket = new ServerSocket(port);
        System.out.println("Server is running...");
    }

    public void open() throws IOException {
        while(true) {
            System.out.print("waiting... since " + LocalDateTime.now());
            Socket socket = serverSocket.accept();
            DataOutputStream dos = null;
            DataInputStream dis = null;
            if(socket.isConnected()) {
                try {
                    System.out.println(String.format(": connected from %s:%s", socket.getInetAddress(), socket.getPort()));
                    dis = new DataInputStream(socket.getInputStream());
                    StringBuilder str = new StringBuilder();
                    byte b = 0;
                    while((b = (byte) dis.read()) != -1) {
                        str.append(String.valueOf((char) b));
                    }
                    System.out.println("== request:start ==");
                    System.out.println(str.toString());
                    System.out.println("== request:end ==");
                    Request req = Request.of(str.toString());
                    dos = new DataOutputStream(socket.getOutputStream());
                    if (this.resources.containsKey(req.getUri())) {
                        dos.write(route(req).toString().getBytes());
                        System.out.println("RESPONSE: OK");
                    } else {
                        dos.write(Response.notFound().toString().getBytes());
                        System.out.println("RESPONSE: NOT FOUND");
                    }
                } catch(Exception e) {
                    System.out.println("RESPONSE: Server Error: " + e.getMessage());
                    e.printStackTrace();
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.write(Response.internalServerError().toString().getBytes());
                } finally {
                    if(dis != null) {
                        dis.close();
                        dis = null;
                    }
                    if(dos != null) {
                        dos.flush();
                        dos.close();
                        dos = null;
                    }
                }
            }
            if (!socket.isClosed())
                socket.close();
        }
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
            try {
                if(br != null) {
                    br.close();
                }
            } catch (Exception e) {
                br = null;
            }
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
        System.out.println("Resource exists");
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
