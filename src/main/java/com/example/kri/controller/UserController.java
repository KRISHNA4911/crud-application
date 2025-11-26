package com.example.kri.controller;


import com.example.kri.entity.User;
import com.example.kri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Redirect to HTML form page
    @GetMapping("/form")
    public RedirectView showForm() {
        return new RedirectView("/users-form.html");
    }

    @GetMapping("/krishna")
    public RedirectView krishnaApp() {
        return new RedirectView("/krishna-app.html");
    }


    // for update data
    @PutMapping("/update-user")
    @CrossOrigin(origins = "*")
    public String updateUser(@RequestBody Map<String, String> payload) {
        String currentName = payload.get("currentName");
        int currentAge = Integer.parseInt(payload.get("currentAge"));
        String newName = payload.get("newName");
        int newAge = Integer.parseInt(payload.get("newAge"));
        // check if new name already exist you can skip this if dont want unique names
        List<User> allUsers= userRepository.findAll();
        for(User u : allUsers){
         if(u.getName().equalsIgnoreCase(newName))   {
           return "❌ New name already exists, choose another name! ";


         }




        }


        // Check if current record exists
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getName().equals(currentName) && user.getAge() == currentAge) {
                // Update record
                user.setName(newName);
                user.setAge(newAge);
                userRepository.save(user);
                return "✅ User updated successfully!";
            }
        }
        return "❌Current data is not matched, please fill correct data!";
    }
// for delete a data
@DeleteMapping("/delete-user")
public String deleteUser(@RequestBody User user) {
    Optional<User> optionalUser = userRepository.findAll().stream()
            .filter(u -> u.getName().equals(user.getName()) && u.getAge() == user.getAge())
            .findFirst();

    if(optionalUser.isPresent()){
        userRepository.delete(optionalUser.get());
        return "✅ User deleted successfully!";
    } else {
        return "❌ Data not found!";
    }
}

// Save user data from frontend
 /* @PostMapping("/users")
    @CrossOrigin(origins = "*")
    public String saveUser(@RequestBody User user) {
        userRepository.save(user);
        return "User saved successfully!";}*/
@PostMapping("/users")
@CrossOrigin(origins = "*")
public String saveUser(@RequestBody User user) {
    // Fetch all users from DB
    List<User> users = userRepository.findAll();

    // Check if name already exists
    for (User u : users) {
        if (u.getName().equalsIgnoreCase(user.getName())) {
            return "❌ User already exists! Please enter another username!" +
                    "";
        }
    }

    // If not exists, save the new user
    userRepository.save(user);
    return "✅ User saved successfully!";
}





    // for getting whole data
        @GetMapping("/users-data")
        @CrossOrigin(origins="*")
        public List<User> getAllUsers () {
            return userRepository.findAll();
            }

        @GetMapping("/users")
public RedirectView redirectToUserHtml(){
        return new RedirectView("/show-users.html");
        }

}