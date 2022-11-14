package com.ipcode.drugsfda.infrastructure.spring;

import com.ipcode.drugsfda.infrastructure.client.fda.FdaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!fda-client-mock")
@EnableFeignClients(clients = {FdaClient.class})
public class FeignConfiguration {

}
