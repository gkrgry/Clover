package com.daelim.clover.user.kakao;

import lombok.Data;

import java.util.HashMap;

@Data
public class KakaDTO extends HashMap<String, Object> {
    private long k_number;
    private String k_name;
    private String k_email;
    private String k_gender;
}
