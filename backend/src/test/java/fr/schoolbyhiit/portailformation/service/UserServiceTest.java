package fr.schoolbyhiit.portailformation.service;

import fr.schoolbyhiit.portailformation.model.User;
import fr.schoolbyhiit.portailformation.model.Role;
import fr.schoolbyhiit.portailformation.model.dao.UserRepository;
import fr.schoolbyhiit.portailformation.model.dao.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("password", createdUser.getPassword());
        assertEquals("testuser@example.com", createdUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userDetails = new User();
        userDetails.setUsername("updateduser");
        userDetails.setPassword("newpassword");
        userDetails.setEmail("updateduser@example.com");

        User updatedUser = userService.updateUser(1L, userDetails);

        assertNotNull(updatedUser);
        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("newpassword", updatedUser.getPassword());
        assertEquals("updateduser@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());
        assertEquals("testuser@example.com", foundUser.getEmail());
    }

    @Test
    public void testFindAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser1");
        user1.setPassword("password1");
        user1.setEmail("testuser1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testuser2");
        user2.setPassword("password2");
        user2.setEmail("testuser2@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("testuser1", users.get(0).getUsername());
        assertEquals("testuser2", users.get(1).getUsername());
    }

    @Test
    public void testAssignRoleToUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setRoles(new HashSet<>());

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.assignRoleToUser(1L, "ROLE_USER");

        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
    }
}
