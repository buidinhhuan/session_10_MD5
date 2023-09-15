package security.service;

import security.model.domain.Role;
import security.model.domain.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
