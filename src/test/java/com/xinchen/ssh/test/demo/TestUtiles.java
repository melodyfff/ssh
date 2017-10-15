package com.xinchen.ssh.test.demo;

import com.xinchen.ssh.core.utils.Crypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUtiles {

    public static void main(String[] args) {
        System.out.println(Crypt.encrypt("test123123123123"));
        System.out.println(Crypt.decrypt("EB1D050F7502E958"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("test"));
    }
}
