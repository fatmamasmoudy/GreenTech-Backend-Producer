    package com.projet.fatma.models.dto;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserInfo {

        private String id;
        private String firstName;
        private String userName;
        private String lastName;
        private String email;
    }
