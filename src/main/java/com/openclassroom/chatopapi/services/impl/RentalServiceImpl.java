package com.openclassroom.chatopapi.services.impl;

import com.openclassroom.chatopapi.dto.CreateRentalDto;
import com.openclassroom.chatopapi.dto.RentalDto;
import com.openclassroom.chatopapi.dto.RentalListResponse;
import com.openclassroom.chatopapi.dto.UpdateRentalDto;
import com.openclassroom.chatopapi.model.Rental;
import com.openclassroom.chatopapi.model.User;
import com.openclassroom.chatopapi.record.RentalUpSertResponse;
import com.openclassroom.chatopapi.repository.RentalRepository;
import com.openclassroom.chatopapi.repository.UserRepository;
import com.openclassroom.chatopapi.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    public RentalListResponse getAll() {
       List<RentalDto> rentals = rentalRepository.findAll().stream().map( r -> {
                    RentalDto dto =  mapper.map(r, RentalDto.class);
                    dto.setOwnerId(r.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());

       return new RentalListResponse(rentals);
    }

    @Override
    public RentalDto findById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location non trouvé avec l'id: " + id));
        RentalDto dto =  mapper.map(rental, RentalDto.class);
        dto.setOwnerId(rental.getUser().getId());
        return dto;
    }

    @Override
    public RentalUpSertResponse create(CreateRentalDto dto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email: " + email));

        Rental rental = Rental.builder()
                .name(dto.getName())
                .surface(dto.getSurface())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .picture(
                        savePicture(dto.getName(), dto.getPicture())
                )

                .user(owner)
                .build();

        rentalRepository.save(rental);

        return new RentalUpSertResponse("Rental created !");
    }

    @Override
    public RentalUpSertResponse update(Integer id, UpdateRentalDto dto) {
        Rental rental = rentalRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Location non trouvé avec l'id " + id));

        rental.setName(dto.getName());

        rental.setSurface(dto.getSurface());

        rental.setPrice(dto.getPrice());

        rental.setDescription(dto.getDescription());

        rentalRepository.save(rental);



        return new RentalUpSertResponse(
                "Rental updated !"
        );
    }

    private String savePicture(String rentalName, MultipartFile picture) throws Exception {

        String fileName =  picture.getOriginalFilename();
        String relativePath = rentalName + "/" + fileName;
        Path fullPath = Paths.get(uploadDir, relativePath);

        try {
            Files.createDirectories(fullPath.getParent());
            picture.transferTo(fullPath.toFile());
        } catch (IOException e) {
            throw new Exception("Erreur lors de l'enregistrement de la photo", e);
        }
        return "/api/uploads/" + relativePath;
    }
}
