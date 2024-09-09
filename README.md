Secure API
Overview
This project is a Spring Boot application demonstrating secure API endpoints using OAuth2 and JWT for authentication and authorization. It includes configurations for both OAuth2 Resource Server and OAuth2 Client, along with role-based access control.

Prerequisites
Java 17+: Ensure you have JDK 17 or later installed.
Maven: Used for building the project.
Setup
Clone the Repository
bash
Copy code
git clone https://github.com/your-repo/secure-api.git
cd secure-api
Update Configuration
Open src/main/resources/application.properties.
Update the following properties with your actual values:
properties
Copy code
spring.application.name=Secure API

# JWT Secret Key
jwt.secret=mysecretkey1234567890

# OAuth2 Resource Server Configuration
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://example.com/issuer
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://example.com/jwks

# OAuth2 Client Configuration
spring.security.oauth2.client.registration.myclient.client-id=my-client-id
spring.security.oauth2.client.registration.myclient.client-secret=my-client-secret
spring.security.oauth2.client.registration.myclient.scope=read,write
spring.security.oauth2.client.registration.myclient.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.myclient.redirect-uri=http://localhost:8080/login/oauth2/code/myclient

spring.security.oauth2.client.provider.myprovider.authorization-uri=https://example.com/oauth/authorize
spring.security.oauth2.client.provider.myprovider.token-uri=https://example.com/oauth/token
spring.security.oauth2.client.provider.myprovider.user-info-uri=https://example.com/userinfo
spring.security.oauth2.client.provider.myprovider.jwk-set-uri=https://example.com/oauth/jwks
spring.security.oauth2.client.provider.myprovider.user-name-attribute=sub

# Allow bean definition overriding for development
spring.main.allow-bean-definition-overriding=true
Build and Run
Build the Project

bash
Copy code
mvn clean install
Run the Application

bash
Copy code
mvn spring-boot:run
The application will start on http://localhost:8080.

Testing the APIs
Public Endpoints
GET /api/public/hello: Accessible without authentication.
bash
Copy code
curl http://localhost:8080/api/public/hello
Admin Endpoints
GET /api/admin/secure: Accessible only with ADMIN role.
bash
Copy code
curl -H "Authorization: Bearer <access_token>" http://localhost:8080/api/admin/secure
User Endpoints
GET /api/user/secure: Accessible only with USER role.
bash
Copy code
curl -H "Authorization: Bearer <access_token>" http://localhost:8080/api/user/secure
OAuth2 Authorization Code Flow
Authorize URL

Navigate to the authorization URL in your browser:

bash
Copy code
http://localhost:8080/oauth2/authorization/myclient
Exchange Authorization Code for Token

Use the authorization code received to obtain an access token. This typically involves sending a POST request to the token endpoint:

bash
Copy code
curl -X POST \
  -d "grant_type=authorization_code" \
  -d "code=<authorization_code>" \
  -d "redirect_uri=http://localhost:8080/login/oauth2/code/myclient" \
  -d "client_id=my-client-id" \
  -d "client_secret=my-client-secret" \
  https://example.com/oauth/token
Replace <authorization_code> with the code received from the authorization step.

Use the Access Token

Access protected endpoints using the obtained access token:

bash
Copy code
curl -H "Authorization: Bearer <access_token>" http://localhost:8080/api/user/secure
Additional Notes
JWT Secret Key: Make sure to use a strong and secure secret key for production environments.
OAuth2 Configuration: Ensure that the OAuth2 provider URLs and client credentials are accurate.
Troubleshooting
Bean Definition Issues: If you encounter bean definition conflicts, ensure that only one SecurityFilterChain bean is defined or enable bean definition overriding in application.properties.

OAuth2 Errors: Verify OAuth2 client registration and provider settings in application.properties to ensure correct configuration.
