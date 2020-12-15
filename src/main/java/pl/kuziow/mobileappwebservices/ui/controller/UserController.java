package pl.kuziow.mobileappwebservices.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kuziow.mobileappwebservices.service.UserService;
import pl.kuziow.mobileappwebservices.shared.dto.UserDto;
import pl.kuziow.mobileappwebservices.ui.model.request.UserDetailsRequestModel;
import pl.kuziow.mobileappwebservices.ui.model.response.UserRest;
import pl.kuziow.mobileappwebservices.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
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
