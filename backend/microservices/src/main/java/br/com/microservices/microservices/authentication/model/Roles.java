package br.com.microservices.microservices.authentication.model;

public enum Roles {
        ADMIN("role_admin"),
        USER("role_user");

        private String role;

        Roles(String role) {
                this.role = role;
        }

        public String getRole() {
                return role;
        }
}