package com.asset.reservation.asset.config;

import com.asset.reservation.asset.cli.lib.Reader;
import org.jline.reader.LineReader;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfig {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  Reader reader(LineReader lineReader) {
    return new Reader(lineReader, '*');
  }
}
