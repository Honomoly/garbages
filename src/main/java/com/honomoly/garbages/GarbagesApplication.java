package com.honomoly.garbages;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GarbagesApplication {

	public static void main(String[] args) {
		// .env 환경변수 로드
		Dotenv dotenv = Dotenv.configure()
				.directory("./")  // JAR 파일과 같은 폴더에 있는 .env 파일 읽기
				.ignoreIfMissing()
				.load();

		// 전역 환경변수로 등록
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		// 서버 시간대 설정
		TimeZone.setDefault(TimeZone.getTimeZone(System.getProperty("TIMEZONE", "UTC")));

		// 앱 실행!
		SpringApplication.run(GarbagesApplication.class, args);
	}

}
