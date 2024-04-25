package br.com.projetofinal.petconnet.core.security.service;

import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.service.UserLoginService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private UserLoginService userService;

    public CustomUserDetailsServiceImpl(UserLoginService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.geByUsername(username);
        return loadUserDetail(user);
    }

    public CustomUserDetails loadUserDetail(User user) {
        CustomUserDetails userDetails = null;
        if (user != null) {
            userDetails = new CustomUserDetails(user);
            userDetails.setAuthorities(AuthorityUtils.createAuthorityList());
        }
        return userDetails;
    }
}
