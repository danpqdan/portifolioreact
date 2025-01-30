package br.com.microservices.microservices.sendemail.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewerDTO {
    private String ip;
    private String userAgent;
    
}
