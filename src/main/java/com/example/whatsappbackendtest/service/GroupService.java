package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.GroupRequest;
import com.example.whatsappbackendtest.domain.dto.UserGroupRequest;
import com.example.whatsappbackendtest.domain.exception.GroupNotFoundException;
import com.example.whatsappbackendtest.domain.model.Group;
import com.example.whatsappbackendtest.domain.model.User;
import com.example.whatsappbackendtest.domain.model.UserGroup;
import com.example.whatsappbackendtest.domain.model.UserGroupId;
import com.example.whatsappbackendtest.repository.GroupRepository;
import com.example.whatsappbackendtest.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.whatsappbackendtest.util.DecodeImageAsBase64.decodeAsByteArray;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repository;
    private final UserService userService;
    private final UserGroupRepository userGroupRepository;

    public Group createGroup(GroupRequest request) {
        Group group = saveGroup(request);
        request.getNumbers().forEach(number ->
                addUser(group.getId(), number));
        return group;
    }

    private Group saveGroup(GroupRequest request) {
        UUID newGroupId = UUID.randomUUID();
        Group group = new Group();
        group.setId(newGroupId);
        group.setName(request.getName());
        group.setPhoto(decodeAsByteArray(request.getPhoto()));
        return repository.save(group);
    }

    public Group findById(UUID groupId) {
        return repository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    public UserGroup addUser(UserGroupRequest request) {
        return addUser(
                request.getGroupId(),
                request.getUserNumber()
        );
    }

    private UserGroup addUser(UUID groupId, BigInteger userNumber) {
        Group group = findById(groupId);
        User user = userService.getUser(userNumber);
        return createUserGroup(group, user);
    }

    private UserGroup createUserGroup(Group group, User user) {
        UserGroupId id = new UserGroupId();
        id.setGroupId(group.getId());
        id.setUserNumber(user.getNumber());
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setUser(user);
        userGroup.setGroup(group);
        return userGroupRepository.save(userGroup);
    }

    public void uploadPhoto(UUID groupId, MultipartFile photo) throws IOException {
        Group group = findById(groupId);
        group.setPhoto(photo.getBytes());
        repository.save(group);
    }

    public List<Group> findForUser(BigInteger number) {
        return userGroupRepository.findAllByUserNumber(number)
                .stream().map(UserGroup::getGroup)
                .collect(Collectors.toList());
    }
}
