package com.sardor.unsplash.annotations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.exeptions.ForBiddenEx;

@Component
@Aspect
public class CheckPermissionExecutor {
    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermissionMyMethod(CheckPermission checkPermission) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get current user
        boolean exist = false; // default
        for (GrantedAuthority authority : principal.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.value())) { // check permissions
                exist = true;
                break;
            }
        }
        if (!exist) throw new ForBiddenEx(checkPermission.value(), "Forbidden"); // 403
    }
}
