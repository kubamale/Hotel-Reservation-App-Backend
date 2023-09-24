package com.example.HotelReservation.Configuration;

import com.example.HotelReservation.DTOs.SignUpDTO;
import com.example.HotelReservation.Exceptions.AppException;
import com.example.HotelReservation.Models.*;
import com.example.HotelReservation.Repositories.*;
import com.example.HotelReservation.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    AmenitiesReopsitory amenitiesReopsitory;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        SignUpDTO user = new SignUpDTO("User", "User", "user@ex.com", "password".toCharArray());
        userService.register(user);
        Set<Amenities> amenitiesSet = new HashSet<>(Arrays.asList(new Amenities("Swimming Pool", "assets/icons/swimming-pool.png"), new Amenities("Double Bed", "assets/icons/double-bed.png"),
                new Amenities("Kitchen", "assets/icons/kitchen.png"),new Amenities("Beach", "assets/icons/beach.png"),new Amenities("Single Bed", "assets/icons/single-bed.png"),new Amenities("Restaurant", "assets/icons/restaurant.png")));

        amenitiesReopsitory.saveAll(amenitiesSet);
        Optional<User> oUser = userRepository.findByLogin(user.login());
        if (oUser.isEmpty()){
            throw new AppException("Error initializing user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String desc = "W obiekcie Amicus przysługuje Ci zniżka Genius! Aby zaoszczędzić w tym obiekcie, wystarczy, że się zalogujesz.\n" +
                "\n" +
                "Usytuowany w spokojnej części Zakopanego obiekt Amicus oferuje ogród i plac zabaw dla dzieci w pobliżu głównych szlaków turystycznych.\n" +
                "\n" +
                "Pokoje są przestronne i jasne. Każdy z nich obejmuje łazienkę z prysznicem i wyposażony jest w telewizor z dostępem do kanałów telewizji satelitarnej. W pokojach można także korzystać z bezpłatnego bezprzewodowego dostępu do Internetu.\n" +
                "\n" +
                "Posiłki serwowane są w przestronnym i jasnym lokalu Grill Bar na parterze.\n" +
                "\n" +
                "Popularne Krupówki z barami i sklepami oddalone są od obiektu Amicus o 1,6 km, a wyciąg narciarski Pod Lipkami - o 1,7 km. Odległość od dworca kolejowego Zakopane wynosi 3,2 km. Na miejscu dostępny jest bezpłatny parking.\n" +
                "\n" +
                "Z autentycznych opinii naszych Gości wynika, że to ich ulubiona część miasta Zakopane.\n" +
                "\n" +
                "Parom bardzo się podoba ta lokalizacja – za pobyt dla 2 osób oceniają ją na 9,1";

        Hotel hotel1 = new Hotel("Poland", "Warsaw", "01-000", "Znana", "134a", "+48999999999",
                "exampleemail@exam.com","test1",desc ,
                new ArrayList<>(Arrays.asList("https://u.profitroom.pl/2018-binkowskihotel-pl/thumb/1200x630/uploads/binkowski_8_1.jpg",
                        "https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg",
                        "https://u.profitroom.pl/2018-binkowskihotel-pl/thumb/1200x630/uploads/binkowski_8_1.jpg",
                        "https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg",
                        "https://u.profitroom.pl/2018-binkowskihotel-pl/thumb/1200x630/uploads/binkowski_8_1.jpg",
                        "https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg",
                        "https://u.profitroom.pl/2018-binkowskihotel-pl/thumb/1200x630/uploads/binkowski_8_1.jpg",
                        "https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg",
                        "https://u.profitroom.pl/2018-binkowskihotel-pl/thumb/1200x630/uploads/binkowski_8_1.jpg",
                        "https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg")),amenitiesSet,oUser.get());
        Hotel hotel2 = new Hotel("Poland", "Warsaw", "01-000", "Znana", "134a",
                "+48999999999", "exampleemail@exam.com","test2", "test2 des2",
                new ArrayList<>(Arrays.asList("https://www.hotel.bialowieza.pl/_thumb/650x500x80/banery/OPT-POKOJE%20BUSSINESS/3q1a3748.jpg")),amenitiesSet, oUser.get());

        Room room1 = new Room( new ArrayList<>(Arrays.asList("equipment1", "equipment2", "equipment3")), 123.00, hotel1, 3);
        Room room2 = new Room( new ArrayList<>(Arrays.asList("equipment1", "equipment3")), 163.00, hotel2, 5);
        Reservation reservation = new Reservation("John", "Doe",Date.valueOf("2022-02-05"),Date.valueOf("2022-04-12"), 1232131, "test", room1);
        hotelRepository.save(hotel1);
        hotelRepository.save(hotel2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        reservationRepository.save(reservation);

    }
}
