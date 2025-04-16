package jmar.originaljava.egoliftuniversitybackend.repository;

import jmar.originaljava.egoliftuniversitybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
