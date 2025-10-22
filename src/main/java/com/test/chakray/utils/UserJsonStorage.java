package com.test.chakray.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.chakray.model.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserJsonStorage {
    private final ObjectMapper mapper;
    private final File file = new File ("src/main/resources/users.json");

    public UserJsonStorage(ObjectMapper mapper) {
        this.mapper = mapper.findAndRegisterModules();
    }

    public synchronized List<User> loadUsers() {
        try {
            if (!file.exists()) {
                return new ArrayList<>();
            }

            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public synchronized  void saveUsers(List<User> users) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
