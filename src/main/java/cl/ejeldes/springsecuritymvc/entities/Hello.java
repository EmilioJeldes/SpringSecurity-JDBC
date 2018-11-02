package cl.ejeldes.springsecuritymvc.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Hello {

    private String message;
    private String from;
    private String to;
    private Date createAt;

    public Hello() {
        this.createAt = new Date();
    }
}
