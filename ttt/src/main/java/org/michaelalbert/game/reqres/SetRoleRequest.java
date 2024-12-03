package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetRoleRequest {
    public static final String ACTION = "setrole";
    private String role;

    public SetRoleRequest(String role) {
        this.role = role;
    }

    public static SetRoleRequest fromString(String message) {
        String[] parts = message.split("#");
        return new SetRoleRequest(parts[1]);
    }

    @Override
    public String toString() {
        return ACTION + "#" + role;
    }

}
