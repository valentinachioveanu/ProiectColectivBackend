package com.ebn.calendar.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ PersistenceConfiguration.class})
public class MainConfiguration {
}
