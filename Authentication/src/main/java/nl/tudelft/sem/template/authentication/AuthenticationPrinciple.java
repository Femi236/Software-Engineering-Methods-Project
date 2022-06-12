package nl.tudelft.sem.template.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthenticationPrinciple implements UserDetails {

    private transient BCryptPasswordEncoder encoder;

    private transient User user;

    private static final long serialVersionUID = 1;

    private transient HashSet<GrantedAuthority> authority;

    /**.
     * To give user authority
     *
     * @param user user with the correct credentials
     * @param encoder to encode the password
     */
    //continue working on the constructor;
    public  AuthenticationPrinciple(User user, BCryptPasswordEncoder encoder) {
        this.user = user;
        this.encoder = encoder;
        authority = new HashSet<GrantedAuthority>();
        authority.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "role " +  user.getRole();
                }
            });
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getNetId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
