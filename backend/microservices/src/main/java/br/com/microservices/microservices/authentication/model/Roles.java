package br.com.microservices.microservices.authentication.model;

public enum Roles {

        DONO("ROLE_dono"),
        FUNCIONARIO("ROLE_funcionario"),
        USUARIO("ROLE_usuario");

        private String role;

        Roles(String role) {
                this.role = role;
        }

        public String getRole() {
                return role;
        }
}