package com.robindas.bloodbridge.Repositories;

import com.robindas.bloodbridge.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users getUserByUserName(String username);
}
