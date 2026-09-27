package com.example.task_management.service.userservice;

import com.example.task_management.dto.TaskResponse;
import com.example.task_management.dto.User.LoginRequest;
import com.example.task_management.dto.User.UserRequest;
import com.example.task_management.dto.User.UserRequestCityNameDto;
import com.example.task_management.dto.User.UserResponse;
import com.example.task_management.dto.Weather.LocationCoordinatesDto;
import com.example.task_management.dto.Weather.WeatherResponse;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.Users;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.service.JwtService;
import com.example.task_management.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final WeatherService weatherService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    @Override
    public void createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Users user = modelMapper.map(userRequest, Users.class);
        user.setPassword(
                passwordEncoder.encode(userRequest.getPassword())
        );
        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(user);
    }

    @Override
    public List<TaskResponse> getUserTasks(Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(task->modelMapper.map(task, TaskResponse.class)).toList();
    }

    @Override
    public void updateUserCityName(UserRequestCityNameDto userRequestCityNameDto, Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setCity(userRequestCityNameDto.getCity());
        userRepository.save(user);
    }

    @Override
    public Mono<UserResponse> getUserInformation(Long userId) {

        return Mono.fromCallable(() ->
                        userRepository.findById(userId)
                                .orElseThrow(() ->
                                        new RuntimeException("User not found"))
                )
                .flatMap(user -> {

                    LocationCoordinatesDto coordinates =
                            weatherService.getCoordinatesByCity(user.getCity());

                    Mono<WeatherResponse> weatherMono =
                            weatherService.getWeather(
                                    coordinates.getLatitude(),
                                    coordinates.getLongitude()
                            );

                    Mono<List<TaskResponse>> tasksMono =
                            Mono.fromCallable(() -> getUserTasks(userId)
                            );

                    return Mono.zip(weatherMono, tasksMono)
                            .map(result -> {

                                WeatherResponse weather = result.getT1();
                                List<TaskResponse> tasks = result.getT2();

                                UserResponse response = new UserResponse();

                                response.setId(String.valueOf(user.getId()));
                                response.setUsername(user.getUsername());
                                response.setWeather(weather);
                                response.setTasks(tasks);

                                return response;
                            });
                });
    }
}
