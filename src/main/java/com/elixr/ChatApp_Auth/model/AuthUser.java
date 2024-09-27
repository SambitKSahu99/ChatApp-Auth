package com.elixr.ChatApp_Auth.model;

import com.elixr.ChatApp_Auth.contants.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = AuthConstants.USER_COLLECTION_NAME)
public class AuthUser {

    @Id
    private UUID id;
    private String userName;
    private String password;
}
