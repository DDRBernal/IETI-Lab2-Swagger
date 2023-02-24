package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        URI createdUserUri = URI.create("");
        User user1 = new User(userDto);
        usersService.save(user1);
        return ResponseEntity.created(createdUserUri).body(user1);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users= usersService.all();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        Optional<User> op = usersService.findById(id);
        if (op.isEmpty()) throw new UserNotFoundException(id);
        return ResponseEntity.ok(op.get());
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        User user = new User(userDto);
        Optional<User> users = usersService.findById(id);
        if (users.isPresent()){
            usersService.update(user,id);
            usersService.save(users.get());
            return ResponseEntity.ok(user);
        }else{
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        if(usersService.findById(id).isEmpty()) throw new UserNotFoundException(id);
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
