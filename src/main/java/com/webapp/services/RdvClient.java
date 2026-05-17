package com.webapp.services;

import com.webapp.models.RDV;
import com.webapp.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msRdv")
public interface RdvClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users", consumes = "application/json")
    User findUserByEmail(@RequestParam("email") String email);

    @RequestMapping(method = RequestMethod.POST, value = "/users/save", consumes = "application/json")
    User save(@RequestBody User user);

    @RequestMapping(method = RequestMethod.GET, value = "/rdvs/user/{email}", consumes = "application/json")
    List<RDV> getRdvByUser(@PathVariable String email);

}
