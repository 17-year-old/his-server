package com.fubailin.hisserver.data;

import com.fubailin.hisserver.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}
