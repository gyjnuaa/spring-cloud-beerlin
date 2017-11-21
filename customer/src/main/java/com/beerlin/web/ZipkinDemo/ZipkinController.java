package com.beerlin.web.ZipkinDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by GYJ on 2017-11-20.
 */
@RestController
@RequestMapping("/Zipkin")
public class ZipkinController {
    @RequestMapping(value = "/getPrint",method = RequestMethod.GET)
    public String  getPrint(){
        System.out.print("Hello ZipKin!!");
        return "Hello ZipKin!!";
    }
}
