package pl.coderslab.myFinance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.myFinance.model.UserProfile;
import pl.coderslab.myFinance.repository.UserProfileRepository;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Optional<UserProfile> findById(Integer profileId) {
        return userProfileRepository.findById(profileId);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public void deleteById(Integer profileId) {
        userProfileRepository.deleteById(profileId);
    }

    public UserProfile editUserProfile(UserProfile updatedProfile) {
        Optional<UserProfile> existingProfileOpt = userProfileRepository.findById(updatedProfile.getProfileId());

        if (existingProfileOpt.isPresent()) {
            UserProfile existingProfile = existingProfileOpt.get();

            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setLastName(updatedProfile.getLastName());
            existingProfile.setAddress(updatedProfile.getAddress());
            existingProfile.setPhoneNumber(updatedProfile.getPhoneNumber());

            return userProfileRepository.save(existingProfile);
        } else {
            throw new IllegalArgumentException("UserProfile not found with id: " + updatedProfile.getProfileId());
        }
    }
}
