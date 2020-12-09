package pl.kuziow.mobileappwebservices.ui.controller;

import org.springframework.web.bind.annotation.*;
import pl.kuziow.mobileappwebservices.ui.model.request.UserDetailsRequestModel;
import pl.kuziow.mobileappwebservices.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        return null;
    }

    @PutMapping
    public String updateUser(){
        return "updateuser was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }
}
