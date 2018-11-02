# Spring Security JDBC
Spring security demo using JDBC example

## Using JDBC
#### 1. Register Bcrypt password encoder as Spring Bean into the `MvcConfig` class 
````java
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }
    
}
````

#### 2. Inject the `DataSource` and `BCryptPasswordEncoder` into the `SpringSecurityConfuguration`  class
````java
...

private final BCryptPasswordEncoder passwordEncoder;
private final DataSource dataSource;

// Injection via constructor
@Autowired
public SpringSecurityConfig(BCryptPasswordEncoder passwordEncoder, DataSource dataSource) {
    this.passwordEncoder = passwordEncoder;
    this.dataSource = dataSource;
}
...

````

#### 3. Override the `configure()` method with the `AuthenticationManagerBuilder` and set up `jdbcAuthentication`
````java
...

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
            .dataSource(dataSource).passwordEncoder(passwordEncoder)
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
            .authoritiesByUsernameQuery(
                    "SELECT u.username, a.authority FROM authorities a INNER JOIN users u ON (a.user_id = u.id) WHERE u.username = ?");

}

...
````
* `.usersByUsernameQuery` native sql statement to find the `User`
* `.authoritiesByUsernameQuery` native sql statement to find the `Authorities` related to that user


### Database Scheema for testing
````sql
CREATE TABLE users
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    enabled tinyint(1) NOT NULL
);
CREATE UNIQUE INDEX users_username_uindex ON users (username);

CREATE TABLE authorities
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    authority varchar(45) NOT NULL,
    CONSTRAINT fk_authority_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX user_id_authority_unique ON authorities (user_id, authority);
````

### Data for testing
````sql
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$UjkBbFTTLtrVrPWKm4AmjufiyGGGprc04nxghBeWmWyP1o25lA.ka', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$U.kxzZsFe3.1Uw3qgVicXek9X8HeyRbVGMRsG3VeuoGWRXyV2zHF2', 1);

INSERT INTO authorities (user_id, authority) VALUES ('1', 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES ('2', 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES ('2', 'ROLE_ADMIN');
````