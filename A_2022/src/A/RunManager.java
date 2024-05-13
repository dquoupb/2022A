package A;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.Gson;


public class RunManager {

	public static void main(String[] args) throws Exception  {
		Gson gson = new Gson();
		Proxy proxy = gson.fromJson(new FileReader(args[0]), Proxy.class);
		MyServer myServer = new MyServer(proxy);
	}
	
	// path 아래에 있는 모든 파일/디렉토리 리스트로 얻기
	public static File[] getFolderFiles(String path) {
		File directory = new File(path);
		return directory.listFiles();
	}

	public static JsonObject getJsonObject(String path) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		return gson.fromJson(printFile(path), JsonObject.class);
	}
	
	// 텍스트 파일 read&print
	static String printFile(String fileName) throws IOException {
		return new String(Files.readAllBytes(new File(fileName).toPath()));
	}
}
