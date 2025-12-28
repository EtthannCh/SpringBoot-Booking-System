package com.example.booking_system.auth.role.model;

public class RoleEnum {

    public enum RoleCodeEnum {
        USER("USER"),
        ADM("ADM"),
        SYS_ADM("SYSADMIN");

        private final String roleString;

        RoleCodeEnum(String roleString) {
            this.roleString = roleString;
        }

        public String getRoleCode() {
            return roleString;
        }
    }
}
