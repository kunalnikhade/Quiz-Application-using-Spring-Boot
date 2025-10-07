package com.quizApp.model.auth;

import com.quizApp.enumeration.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Enter a Name")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Enter a Email")
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Enter a Username")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Enter a Password")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ForgotPasswordEntity forgotPassword;

    public UserEntity()
    {
    }

    public UserEntity(final Integer id, final String name, final String email, final String username, final String password, final UserRole role, final ForgotPasswordEntity forgotPassword)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.forgotPassword = forgotPassword;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(final UserRole role)
    {
        this.role = role;
    }

    public ForgotPasswordEntity getForgotPassword()
    {
        return forgotPassword;
    }

    public void setForgotPassword(final ForgotPasswordEntity forgotPassword)
    {
        this.forgotPassword = forgotPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
