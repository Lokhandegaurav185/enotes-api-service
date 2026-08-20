package com.code.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.code.entity.User;
import com.code.util.CommonGenericResponseUtil;

public class AuditAwareConfig implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		User loggedInUser = CommonGenericResponseUtil.getLoggedInUser();
		return Optional.of(loggedInUser.getId());
	}

}
