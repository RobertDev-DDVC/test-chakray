package com.test.chakray.service;

import com.test.chakray.dto.*;
import com.test.chakray.exception.NotFoundException;
import com.test.chakray.exception.TaxIdAlreadyExistsException;
import com.test.chakray.model.Address;
import com.test.chakray.model.User;
import com.test.chakray.utils.UserJsonStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {
    private final UserJsonStorage userStorage;

    private final AtomicInteger addressSeq;

    public UserService(UserJsonStorage storage) {
        this.userStorage = storage;
        this.addressSeq = new AtomicInteger(initAddressSeq(userStorage.loadUsers()));
    }

    public List<User> list() {
        return userStorage.loadUsers();
    }

    public User get(UUID id) {
        return userStorage.loadUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    public User create(UserRequestDTO req) {
        List<User> users = userStorage.loadUsers();

        ensureEmailUnique(users, req.getEmail(), null);
        ensureTaxIdUnique(users, req.gettaxId(), null);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setPassword(req.getPassword());
        user.setTaxId(req.gettaxId());
        user.setCreatedAt(req.getcreatedAt() != null ? req.getcreatedAt() :
                LocalDateTime.now(ZoneId.of("Indian/Antananarivo")));
        user.setAddresses(mapAddresses(req.getAddresses(), null));

        users.add(user);
        userStorage.saveUsers(users);
        return user;
    }

    public User update(UUID id, UserUpdateRequestDTO req) {
        if (req == null || isUpdateRequestEmpty(req)) {
            throw new IllegalArgumentException("No data provided for update. At least one field must be specified.");
        }

        List<User> users = userStorage.loadUsers();
        int idx = indexOf(users, id);
        if (idx == -1)
            throw new NoSuchElementException("User not found");

        User existing = users.get(idx);

        if (req.getEmail() != null && !req.getEmail().equalsIgnoreCase(existing.getEmail())) {
            ensureEmailUnique(users, req.getEmail(), id);
            existing.setEmail(req.getEmail());
        }

        if (req.gettaxId() != null && !req.gettaxId().equalsIgnoreCase(existing.getTaxId())) {
            ensureTaxIdUnique(users, req.gettaxId(), id);
            existing.setTaxId(req.gettaxId());
        }

        if (req.getName() != null)
            existing.setName(req.getName());
        if (req.getPhone() != null)
            existing.setPhone(req.getPhone());
        if (req.getPassword() != null)
            existing.setPassword(req.getPassword());
        if (req.gettaxId() != null)
            existing.setTaxId(req.gettaxId());
        if (req.getcreatedAt() != null)
            existing.setCreatedAt(req.getcreatedAt());

        if (req.getAddresses() != null) {
            existing.setAddresses(mapAddresses(req.getAddresses(), existing.getAddresses()));
        }

        users.set(idx, existing);
        userStorage.saveUsers(users);
        return existing;
    }

    public void delete(UUID id) {
        List<User> users = userStorage.loadUsers();
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        if (!removed)
            throw new NoSuchElementException("User not found");
        userStorage.saveUsers(users);
    }

    private List<Address> mapAddresses(List<AddressRequestDTO> requests, List<Address> current) {
        List<Address> result = new ArrayList<>();
        Map<Integer, Address> currentById = new HashMap<>();

        if (current != null) {
            for (Address a : current)
                currentById.put(a.getId(), a);
        }

        for (AddressRequestDTO r : requests) {
            Address a = new Address();
            Integer id = r.getId() != null ? r.getId() : nextAddressId();
            a.setId(id);
            a.setName(r.getName());
            a.setStreet(r.getStreet());
            a.setCountryCode(r.getCountryCode());
            result.add(a);
        }
        return result;
    }

    private int nextAddressId() {
        return addressSeq.incrementAndGet();
    }

    private void ensureEmailUnique(List<User> users, String email, UUID ignoreId) {
        boolean exists = users.stream().anyMatch(u -> u.getEmail() != null
                && u.getEmail().equalsIgnoreCase(email)
                && (ignoreId == null || !u.getId().equals(ignoreId)));
        if (exists)
            throw new IllegalArgumentException("Email already exists");
    }

    private void ensureTaxIdUnique(List<User> users, String taxId, UUID ignoreId) {
        boolean exists = users.stream().anyMatch(u ->
                u.getTaxId() != null
                        && u.getTaxId().equalsIgnoreCase(taxId)
                        && (ignoreId == null || !u.getId().equals(ignoreId))
        );
        if (exists) {
            throw new TaxIdAlreadyExistsException("tax_id already exists");
        }
    }

    private int indexOf(List<User> users, UUID id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    private int initAddressSeq(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(User::getAddresses)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(Address::getId)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    private boolean isUpdateRequestEmpty(UserUpdateRequestDTO req) {
        return req.getEmail() == null
                && req.getName() == null
                && req.getPhone() == null
                && req.getPassword() == null
                && req.gettaxId() == null
                && req.getcreatedAt() == null
                && req.getAddresses() == null;
    }
}
