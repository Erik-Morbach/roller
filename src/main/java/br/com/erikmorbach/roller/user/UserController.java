package br.com.erikmorbach.roller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserController{

	@Autowired
	private IUserRepository repository;


	@PostMapping("/")
	public ResponseEntity create(@RequestBody UserModel userModel){
		var hashedPass = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		userModel.setPassword(hashedPass.toString());
		if(repository.existsByUsername(userModel.getUsername())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exist");
		}
		var createdUser = repository.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body(createdUser);
	}
}
