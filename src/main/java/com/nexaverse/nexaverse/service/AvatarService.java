package com.nexaverse.nexaverse.service;

import com.nexaverse.nexaverse.dto.AvatarDTO;
import com.nexaverse.nexaverse.entity.HumanAvatar;
import com.nexaverse.nexaverse.entity.User;
import com.nexaverse.nexaverse.exception.DuplicateResourceException;
import com.nexaverse.nexaverse.exception.ResourceNotFoundException;
import com.nexaverse.nexaverse.repository.AvatarRepository;
import com.nexaverse.nexaverse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    public HumanAvatar createAvatar(AvatarDTO dto) {
        User owner = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getUserId()));

        if (avatarRepository.existsByNameAndUser_Id(dto.getName(), dto.getUserId())) {
            throw new DuplicateResourceException("Avatar name already exists for this user!");
        }

        HumanAvatar avatar = new HumanAvatar();
        avatar.setName(dto.getName());
        avatar.setAvatarType(dto.getAvatarType());
        avatar.setLevel(dto.getLevel());
        avatar.setPositionX(dto.getPositionX());
        avatar.setPositionY(dto.getPositionY());
        avatar.setUser(owner);
        avatar.setWeaponType("SWORD");

        return (HumanAvatar) avatarRepository.save(avatar);
    }

    public List<?> getAllAvatars() {
        return avatarRepository.findAll();
    }

    public List<?> getAvatarsByUser(Long userId) {
        return avatarRepository.findByUser_Id(userId);
    }

    public HumanAvatar moveAvatar(Long id, double x, double y) {
        HumanAvatar avatar = (HumanAvatar) avatarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar not found: " + id));
        avatar.moveToPosition(x, y);
        return (HumanAvatar) avatarRepository.save(avatar);
    }

    public void deleteAvatar(Long id) {
        avatarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avatar not found: " + id));
        avatarRepository.deleteById(id);
    }
}