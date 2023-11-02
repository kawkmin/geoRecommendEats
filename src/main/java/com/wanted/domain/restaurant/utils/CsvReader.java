package com.wanted.domain.restaurant.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CsvReader {

  private final CsvSaveDto csvSaveDto;

  public void reader(String path) throws IOException {
    //해당 파일을 읽어온다
    BufferedReader br = Files.newBufferedReader(Paths.get(path));
    try (br) {
      // 첫 번째 줄 (헤더) 건너뛰기
      br.readLine();
      String line;
      while ((line = br.readLine()) != null) {
        csvSaveDto.save(line); //각 행마다 데이터를 저장한다
      }
      log.info("csv 파일 적재 완료 {}", csvSaveDto);
    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }
}
