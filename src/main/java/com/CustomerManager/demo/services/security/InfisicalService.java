package com.CustomerManager.demo.services.security;

import com.infisical.sdk.InfisicalClient;
import com.infisical.sdk.schema.*;
import org.springframework.stereotype.Service;

@Service
public class InfisicalService {

    InfisicalClient client;

    public InfisicalService() {
        ClientSettings settings = new ClientSettings();
        AuthenticationOptions authOptions = new AuthenticationOptions();
        UniversalAuthMethod authMethod = new UniversalAuthMethod();
        authMethod.setClientID(System.getenv("CM_INFISICAL_CLIENT_ID"));
        authMethod.setClientSecret(System.getenv("CM_INFISICAL_CLIENT_SECRET"));
        authOptions.setUniversalAuth(authMethod);
        settings.setAuth(authOptions);
        settings.setSiteURL(System.getenv("CM_INFISICAL_SITE_URL"));
        client = new InfisicalClient(settings);
    }

    public String getAdminEmail() {
        GetSecretOptions options = new GetSecretOptions();
        options.setSecretName("EMAIL");
        options.setProjectID(System.getenv("CM_INFISICAL_PROJECT_ID"));
        options.setEnvironment(System.getenv("CM_INFISICAL_ENVIRONMENT"));
        options.setPath("/admin_user");
        GetSecretResponseSecret secret = client.getSecret(options);
        return secret.getSecretValue();
    }

    public String getAdminPassword() {
        GetSecretOptions options = new GetSecretOptions();
        options.setSecretName("PASSWORD");
        options.setProjectID(System.getenv("CM_INFISICAL_PROJECT_ID"));
        options.setEnvironment(System.getenv("CM_INFISICAL_ENVIRONMENT"));
        options.setPath("/admin_user");
        GetSecretResponseSecret secret = client.getSecret(options);
        return secret.getSecretValue();
    }
}
