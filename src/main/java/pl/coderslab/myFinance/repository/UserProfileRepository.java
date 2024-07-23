package pl.coderslab.myFinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.myFinance.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

}
